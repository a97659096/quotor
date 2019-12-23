package com.quotorcloud.quotor.common.core.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.constant.FileConstants;
import com.quotorcloud.quotor.common.core.constant.enums.ExceptionEnum;
import com.quotorcloud.quotor.common.core.exception.MyException;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author liugh
 * @since on 2018/5/8.
 */
public class FileUtil {

    //2M
    public static final int FILE_SIZE = 1000000;

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);


//    private static ResourceBundle bundle = ResourceBundle.getBundle("config/constants");



    /**
     * 判断当前文件是否是zip文件
     *
     * @param fileName
     *            文件名
     * @return true 是
     */
    public static boolean isZip(String fileName) {
        return fileName.toLowerCase().endsWith(FileConstants.FilePostFix.ZIP_FILE);
    }


    public static void removeDocument(String fileName){
        File file=new File(fileName);
        if(file.exists() && file.isFile()) {
            file.delete();
        }
        if(file.isDirectory()){
            delDir(fileName);
        }
        if (fileName.lastIndexOf(FileConstants.FilePostFix.ZIP_FILE) > 0) {
            delDir(fileName.substring(0,fileName.lastIndexOf(FileConstants.FilePostFix.ZIP_FILE))+"/");
        }

    }

    public static boolean checkZipFile(String sourcePath){
        System.setProperty("sun.zip.encoding", System.getProperty("sun.jnu.encoding"));
        ZipFile zipFile =null;
        try {
            File sourceFile = new File(sourcePath);
            zipFile = new ZipFile(sourcePath, "gbk");
            if ((!sourceFile.exists()) && (sourceFile.length() <= 0)) {
                throw new Exception("要解压的文件不存在!");
            }
            Enumeration<?> e = zipFile.getEntries();
            while (e.hasMoreElements()) {
                ZipEntry zipEnt = (ZipEntry) e.nextElement();
                if (zipEnt.isDirectory()) {
                    return false;
                }
                if(zipEnt.getName().endsWith(".shp")){
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                if(null!=zipFile){
                    zipFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static List<String> listFile = new ArrayList<>();

    /**
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下
     * (把指定文件夹下的所有文件目录和文件都压缩到指定文件夹下)
     * @param sourceFilePath
     *            :待压缩的文件路径
     * @param zipFilePath
     *            :压缩后存放路径
     * @param fileName
     *            :压缩后文件的名称
     * @return
     */
    public static boolean fileToZip(String sourceFilePath,String zipFilePath,String fileName)throws  Exception{
        boolean flag = false;
        FileOutputStream fos =null;
        ZipOutputStream zos =null;
        BufferedInputStream bis =null;
        FileInputStream  fis =null;
        BufferedOutputStream bufferedOutputStream =null;
        File sourceFile = new File(sourceFilePath);
        if(sourceFile.exists() == false){
            throw new Exception("待压缩的文件目录："+sourceFilePath+"不存在.");
        }else{
            try {
                File zipFile = new File(zipFilePath +fileName );
                if(zipFile.exists()){
                    throw new Exception(zipFilePath + "目录下存在名字为:" + fileName +FileConstants.FilePostFix.ZIP_FILE +"打包文件.");
                }else{
                    File[] sourceFiles = sourceFile.listFiles();
                    if(null == sourceFiles || sourceFiles.length<1){
                        throw new Exception("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
                    }else{
                        fos = new FileOutputStream(zipFile);
                        bufferedOutputStream = new BufferedOutputStream(fos);
                        zos = new ZipOutputStream(bufferedOutputStream);
                        byte[] bufs = new byte[1024*10];
                        for(int i=0;i<sourceFiles.length;i++){
                            //创建ZIP实体，并添加进压缩包
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                            zos.putNextEntry(zipEntry);
                            //读取待压缩的文件并写进压缩包里
                            fis = new FileInputStream(sourceFiles[i]);
                            bis = new BufferedInputStream(fis, FileConstants.BYTE_BUFFER *FileConstants.BUFFER_MULTIPLE);
                            int read;
                            while((read=bis.read(bufs, 0, FileConstants.BYTE_BUFFER *FileConstants.BUFFER_MULTIPLE)) != -1){
                                zos.write(bufs,0,read);
                            }
                            fis.close();
                            bis.close();
                        }
                        flag = true;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally{
                //关闭流
                try {
                    if (null != bis) {
                        bis.close();
                    }
                    if (null != zos) {
                        zos.close();
                    }
                    if (null != bufferedOutputStream) {
                        bufferedOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    public static void getFile(String path) {
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (File f : tempList) {
            if (f.isFile()) {
                listFile.add(f.getPath());
                System.out.println(f.getPath());
                continue;
            }
            if (f.isDirectory()) {
                getFile(f.getPath());
            }
        }

    }


    /**
     * 保存文件到临时目录
     * @param inputStream 文件输入流
     * @param fileName 文件名
     */
    public static void savePic(InputStream inputStream, String fileName) {
        OutputStream os = null;
        try {
            // 保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(FileConstants.FILE_FLODER);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 存储文件
     * @param file
     * @param url
     * @param name
     * @param tag
     * @return
     * @throws Exception
     */
    public static String saveFile(InputStream file, String url, String name, String tag) throws Exception {
        Date time = new Date();
        String fileName = fileName(time, url, name,tag);
        File newFile = getNewFile(fileName);
        File oldFile = createTemporaryFile(file, name);
        copyFile(newFile, new FileInputStream(oldFile));
        oldFile.delete();
        return fileName;
    }

    public static File createTemporaryFile(InputStream file, String name) throws Exception {
        File temp = new File(name);
        OutputStream out = new FileOutputStream(temp);
        try {
            int byteCount = 0;
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = file.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                byteCount += bytesRead;
            }
            out.flush();
        }
        finally {
            try {
                file.close();
            }
            catch (IOException ex) {
            }
            try {
                out.close();
            }
            catch (IOException ex) {
            }
        }
        return temp;
    }

    private static void copyFile(File newFile, FileInputStream file) throws Exception {
        FileOutputStream outFile = new FileOutputStream(newFile);
        FileChannel inC = file.getChannel();
        FileChannel outC = outFile.getChannel();
        int length = 2097152;
        while (true) {
            if (inC.position() == inC.size()) {
                inC.close();
                outC.close();
                outFile.close();
                file.close();
                return ;
            }
            if ((inC.size() - inC.position()) < 20971520) {
                length = (int) (inC.size() - inC.position());
            }else {
                length = 20971520;
            }
            inC.transferTo(inC.position(), length, outC);
            inC.position(inC.position() + length);
        }
    }

    public static File getNewFile(String fileName) throws IOException {
        String filePath = FileConstants.FILE_FLODER + fileName;
        File newFile = new File(filePath);
        File fileParent = newFile.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        if (!newFile.exists()){
            newFile.createNewFile();
        }
        return newFile;
    }

    /**
     * 文件保存路径
     * @param time
     * @param type
     * @param name
     * @return
     */
    private static String fileName(Date time, String url, String name,String tag) {
        StringBuffer str = new StringBuffer();
//        if (type== FileConstants.FileType.FILE_IMG) {
//            str.append(FileConstants.FileType.FILE_IMG_DIR);
//        }
//        if (type==FileConstants.FileType.FILE_ZIP) {
//            str.append(FileConstants.FileType.FILE_ZIP_DIR);
//        }
//        if (type==FileConstants.FileType.FILE_VEDIO) {
//            str.append(FileConstants.FileType.FILE_VEDIO_DIR);
//        }
//        if (type==FileConstants.FileType.FILE_APK) {
//            str.append(FileConstants.FileType.FILE_APK_DIR);
//        }
//        if (type==FileConstants.FileType.FIVE_OFFICE) {
//            str.append(FileConstants.FileType.FIVE_OFFICE_DIR);
//        }
        str.append(url);
        str.append(DateTimeUtil.formatDatetoString(time));
        str.append("/");
        str.append(System.currentTimeMillis());
        int random = new Random().nextInt(10000);
        str.append(random);
        if(!ComUtil.isEmpty(tag)) {
            str.append(tag);
        }
        str.append(name.substring(name.indexOf(".")));
        return str.toString();
    }
//    public static String getFileUrl(String fileDir){
//        return bundle.getString("file-upload.url") + fileDir;
//    }


    /**
     * 删除文件目录
     * @param path
     */
    private static void delDir(String path){
        File dir=new File(path);
        if(dir.exists()){
            File[] tmp=dir.listFiles();
            for(int i=0;i<tmp.length;i++){
                if(tmp[i].isDirectory()){
                    delDir(path+File.separator+tmp[i].getName());
                }else{
                    tmp[i].delete();
                }
            }
            dir.delete();
        }
    }

    /**
     *  截取文件排除后缀名
     * @param fileName 文件名
     * @return
     */
    public static String cutNameSuffix(String fileName) {
        String suffix = fileName.substring(0,fileName.lastIndexOf("."));
        return suffix;
    }


    /**
     * 解压zip格式的压缩文件到指定位置
     *
     * @param sourcePath
     *            压缩文件
     * @param targetPath
     *            解压目录
     * @throws Exception
     */
    public static boolean unZipFiles(String sourcePath, String targetPath) throws Exception {
        System.setProperty("sun.zip.encoding", System.getProperty("sun.jnu.encoding"));
        InputStream is =null;
        BufferedInputStream bis =null;
        try {
            (new File(targetPath)).mkdirs();
            File sourceFile = new File(sourcePath);
            // 处理中文文件名乱码的问题
            ZipFile zipFile = new ZipFile(sourcePath, "UTF-8");
            if ((!sourceFile.exists()) && (sourceFile.length() <= 0)) {
                throw new Exception("要解压的文件不存在!");
            }
            String strPath, gbkPath, strtemp;
            File tempFile = new File(targetPath);
            strPath = tempFile.getAbsolutePath();
            Enumeration<?> e = zipFile.getEntries();
            while (e.hasMoreElements()) {
                ZipEntry zipEnt = (ZipEntry) e.nextElement();
                gbkPath = zipEnt.getName();
                if (zipEnt.isDirectory()) {
                    strtemp = strPath + File.separator + gbkPath;
                    File dir = new File(strtemp);
                    dir.mkdirs();
                    continue;
                } else {
                    // 读写文件
                    is = zipFile.getInputStream((ZipEntry) zipEnt);
                    bis = new BufferedInputStream(is);
                    gbkPath = zipEnt.getName();
                    strtemp = strPath + File.separator + gbkPath;
                    // 建目录
                    String strsubdir = gbkPath;
                    for (int i = 0; i < strsubdir.length(); i++) {
                        if ("/".equalsIgnoreCase(strsubdir.substring(i, i + 1))) {
                            String temp = strPath + File.separator + strsubdir.substring(0, i);
                            File subdir = new File(temp);
                            if (!subdir.exists()) {
                                subdir.mkdir();
                            }
                        }
                    }
                    FileOutputStream fos = new FileOutputStream(strtemp);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    int c;
                    while ((c = bis.read()) != -1) {
                        bos.write((byte) c);
                    }
                    bos.flush();
                    fos.close();
                    bos.close();
                }
            }
            zipFile.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    public static boolean deleteUploadedFile(String fileName) {
//        String filePath = bundle.getString("file-upload.dir") + fileName;
//        File file =  new File(filePath);
//        if(file.exists()){
//            if(file.isFile()) {
//                file.delete();
//            }else{
//                removeDocument(fileName);
//            }
//            return true;
//        }else{
//            return false;
//        }
//    }

//    public static int getFileType(String originalFilename) {
//        String postFix = originalFilename.split("//.")[originalFilename.split("//.").length-1];
//        if(Arrays.asList(FileConstants.FilePostFix.IMAGES).contains(postFix)){
//            return FileConstants.FileType.FILE_IMG;
//        }
//        if(Arrays.asList(FileConstants.FilePostFix.ZIP).contains(postFix)){
//            return FileConstants.FileType.FILE_ZIP;
//        }
//        if(Arrays.asList(FileConstants.FilePostFix.VIDEO).contains(postFix)){
//            return FileConstants.FileType.FILE_VEDIO;
//        }
//        if(Arrays.asList(FileConstants.FilePostFix.APK).contains(postFix)){
//            return FileConstants.FileType.FILE_APK;
//        }
//        if(Arrays.asList(FileConstants.FilePostFix.OFFICE).contains(postFix)){
//            return FileConstants.FileType.FIVE_OFFICE;
//        }
//        return FileConstants.FileType.FILE_IMG;
//    }

    /**
     * 返回某目录下所有文件对象
     *
     * @param str
     * @return
     */
    public static File[] getFiles(String str) {
        File dir = new File(StringUtil.utf8Decoding(str));
        File[] result = null;
        if (dir.isDirectory()) {
            result = dir.listFiles();
        }

        return result;
    }

    /**
     * 返回某个类所在包最顶层文件夹
     *
     * @param clazz 类
     * @return 顶层文件夹路径
     */
    public static String getTopClassPath(Class<?> clazz) {
        String path = StringUtil.utf8Decoding(clazz.getResource("/").getPath());
        return path;
    }

    /**
     * get the jars path
     *
     * @return
     */
    public static String getJarPath() {
        return FileUtil.getParent(FileUtil.getTopClassPath(FileUtil.class), 1) + File.separator + "lib";
    }

    public static String getClassPath(String folderName) {
        return getJarPath().replace("lib", folderName);
    }

    /**
     * 获得类所在文件路径
     *
     * @param clazz
     * @return
     */
    public static String getCurrPath(Class<?> clazz) {
        return StringUtil.utf8Decoding(clazz.getResource("/").getPath() + clazz.getName().replace(".", File.separator));
    }

    /**
     * 创建一个文件夹
     *
     * @param path
     * @return
     */
    public static boolean createDir(String path) {
        boolean flag = false;
        File file = new File(StringUtil.utf8Decoding(path));
        if (!file.exists()) {
            if (!file.isDirectory()) {
                flag = file.mkdir();
            }
        }
        return flag;
    }

    /**
     * 创建一个文件
     *
     * @param path 文件路径
     * @return
     * @throws IOException
     */
    public static boolean createFile(String path) throws IOException {
        return createFile(path, false);
    }

    /**
     * 是否强制新建文件
     *
     * @param path     文件路径
     * @param isDelete 文件存在后是否删除标记
     * @return 文件创建是否成功标记
     * @throws IOException
     * @see 1.原文件存在的情况下会删除原来的文件，重新创建一个新的同名文件，本方法返回文件创建成功标记
     * @see 2.原文件存在但isDelete参数设置为false，表示不删除源文件，本方法返回文件创建失败标记
     */
    public static boolean createFile(String path, boolean isDelete) throws IOException {
        // 加载文件
        File file = new File(StringUtil.utf8Decoding(path));
        // 文件是否创建成功
        boolean flag = true;
        // 判断文件是否存在
        if (file.exists()) {
            if (isDelete) { // 文件存在后删除文件
                // 删除原文件
                file.delete();
                // 创建新文件
                file.createNewFile();
            } else {
                flag = false;
            }
        } else {
            file.createNewFile();
        }

        return flag;
    }

    /**
     * 将oldFile移动到指定目录
     *
     * @param oldFile
     * @param newDir
     * @return
     */
    public static boolean moveFileTo(File oldFile, String newDir) {
        StringBuilder sb = new StringBuilder(newDir);
        sb.append(File.separator).append(oldFile.getName());
        File toDir = new File(StringUtil.utf8Decoding(sb.toString()));
        boolean flag = false;
        if (!toDir.exists()) {
            flag = oldFile.renameTo(toDir);
        }
        return flag;
    }

    /**
     * 不使用renameTo,如果文件(isFile)不存在则不复制.
     *
     * @param sourceFile
     * @param target
     * @throws Exception
     */
    public static void moveFile(File sourceFile, String target) throws Exception {
        if (!sourceFile.exists() || !sourceFile.isFile()) {
            return;
        }
        InputStream inputStream = null;
        File targetFile = new File(target + File.separator + sourceFile.getName());
        OutputStream outputStream = null;
        inputStream = new FileInputStream(sourceFile);
        outputStream = new FileOutputStream(targetFile);
        int readBytes = 0;
        byte[] buffer = new byte[10000];
        while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {
            outputStream.write(buffer, 0, readBytes);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    /**
     * 返回当前文件的上层文件夹路径（第几层由参数floor决定）
     *
     * @param f
     * @param floor
     * @return
     */
    public static String getParent(File f, int floor) {
        String result = "";
        if (f != null && f.exists()) {
            for (int i = 0; i < floor; ++i) {
                f = f.getParentFile();
            }

            if (f != null && f.exists()) {
                result = f.getPath();
            }
        }

        return StringUtil.utf8Decoding(result) + File.separator;
    }

    public static String getParent(String path, int floor) {
        return getParent(new File(path), floor);
    }

    /**
     * 删除文件
     *
     * @param file
     * @return
     */
    public static boolean deleteFile(File file) {
        boolean flag = false;
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    deleteFile(f);
                }
            }
            flag = file.delete();
        }

        return flag;
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param sPath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean DeleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) { // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) { // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else { // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }


    /**
     * 压缩超过2m的图片
     * @param url
     * @return
     * @throws Exception
     */
    public static String savePreFile(String url) throws Exception {
        StringBuffer str = new StringBuffer();
        str.append("/img/");
        str.append(DateTimeUtil.formatDatetoString(new Date()));
        str.append("/pre/");
        str.append(url.substring(url.lastIndexOf("/")+1));
        String preUrl = FileConstants.FILE_FLODER + str.toString();
        File filePath = new File(StringUtil.utf8Decoding(preUrl.substring(0,preUrl.lastIndexOf("/"))));
        if(!filePath.exists()){
            filePath.mkdirs();
        }
        createFile(preUrl);
        //其中的scale是可以指定图片的大小，值在0到1之间，1f就是原图大小，0.5就是原图的一半大小，这里的大小是指图片的长宽。
        //而outputQuality是图片的质量，值也是在0到1，越接近于1质量越好，越接近于0质量越差。
        Thumbnails.of(FileConstants.FILE_FLODER+url)
                .scale(1f)
                .outputQuality(0.5f)
                .toFile(preUrl);
        return str.toString();
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } // 删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查文件名是否合法
     *
     * @param fileName
     * @return
     */
    public static boolean isValidFileName(String fileName) {
        if (fileName == null || fileName.length() > 255)
            return false;
        else {
            return fileName.matches(
                    "[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$");
        }
    }

    /**
     * 复制文件
     *
     * @param src
     * @param dst
     */
    public static void copy(File src, File dst) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dst);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len = -1;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return;
    }

    /**
     * 取指定文件的扩展名
     *
     * @param filePathName 文件路径
     * @return 扩展名
     */
    public static String getFileExt(String filePathName) {
        int pos = 0;
        pos = filePathName.lastIndexOf('.');
        if (pos != -1) {
            return filePathName.substring(pos + 1, filePathName.length());
        }
        else {
            return "";
        }
    }

    /**
     * 去掉文件扩展名
     *
     * @param filename
     * @return
     */
    public static String trimExtension(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf('.');
            if ((i > -1) && (i < (filename.length()))) {
                return filename.substring(0, i);
            }
        }
        return filename;
    }

    /**
     * 读取文件大小
     *
     * @param filename 指定文件路径
     * @return 文件大小
     */
    public static int getFileSize(String filename) {
        try {
            File fl = new File(filename);
            int length = (int) fl.length();
            return length;
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * 判断是否是图片
     *
     * @param file
     * @return
     */
    public static boolean isImage(File file) {
        boolean flag = false;
        try {
            ImageInputStream is = ImageIO.createImageInputStream(file);
            if (null == is) {
                return flag;
            }
            is.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * TODO. 读取文件内容
     *
     * @param file
     * @param fullFilePath
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unused")
    public static String readFileContent(File file, String fullFilePath) throws IOException {
        String returnStr = "";
        if (ComUtil.isEmpty(file) && ComUtil.isEmpty(fullFilePath)) {
            return "";
        }
        if (ComUtil.isEmpty(file)) {
            file = new File(fullFilePath);
        }
        FileInputStream in = null;

        try {
            in = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int len = -1;
            while ((len = in.read(buf)) > 0) {
                returnStr += new String(buf, "utf-8");
                buf = new byte[1024];
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage() + ";" + file.getPath(), e);
            throw e;
        } catch (IOException e) {
            logger.error(e.getMessage() + ";" + file.getPath(), e);
            throw e;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return returnStr;
    }

    public static boolean writeToFile(String content, File file, String fullFilePath) throws IOException {
        if ((ComUtil.isEmpty(file) && ComUtil.isEmpty(fullFilePath)) || ComUtil.isEmpty(content)) {
            return false;
        }
        if (ComUtil.isEmpty(file)) {
            file = new File(fullFilePath);
        }
        FileOutputStream out = null;

        try {

            out = new FileOutputStream(file);

            out.write(content.getBytes("utf-8"));
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage() + ";" + file.getPath(), e);
            throw e;
        } catch (IOException e) {
            logger.error(e.getMessage() + ";" + file.getPath(), e);
            throw e;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * 在指定的目录下创建文件
     *
     * @param directory
     * @param fileName
     * @param content
     * @throws Exception
     */
    public static String createFile(String directory, String fileName, InputStream content) throws Exception {
        File currentDir = new File(directory);
        if (!currentDir.exists()) {
            currentDir.mkdirs();
        }
        FileOutputStream fileOut = null;
        String fullFilePath = directory + File.separator + fileName;
        try {
            fileOut = new FileOutputStream(fullFilePath);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = content.read(buffer)) > 0) {
                fileOut.write(buffer);
            }
        } finally {
            if (content != null) {
                content.close();
            }
            if (fileOut != null) {
                fileOut.close();
            }
        }
        return fullFilePath;
    }

    public static File mkdir(String directory) {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static String readFileContent(File file, Charset charset) throws Exception {
        if (!file.exists() || file.isDirectory()) throw new Exception("file is not exists or is a directory");
        StringWriter out = new StringWriter();
        FileInputStream in = new FileInputStream(file);
        try {
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = in.read(buffer)) > 0) {
                out.write(new String(buffer, 0, length, charset));
            }
        } finally {
            if (in != null) in.close();
            if (out != null) out.close();
        }
        return out.toString();
    }


//    public static void main(String[] args) throws Exception {
//        //System.out.println(Jsoup.parse(new File("D:\\result.htm"),"GBK").outerHtml());
//        //System.out.println(readFileContent(new File("D:\\result.htm"),Charset.forName("GBK")));
//        //System.out.println(image2Base64String(new FileInputStream(new File("D://123.png"))));
//        System.out.println("data:image/png;base64,".replaceAll("data:image/(jpg|png|jpeg);base64,",""));
//    }
    /**
     * 创建文件路径(文件夹)
     * @param fileName
     */
    public  static void fileMkdir(String fileName) {
        File fileTotal=new File(fileName);
        if (!fileTotal.exists()) {
            fileTotal.mkdirs();
        }
    }

    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------
    //---------------------------------------------------------------

    /**
     * 批量set
     * @param o
     * @param clazz
     * @param dto
     * @param filed
     * @param folder
     */
    public static void saveFileAndField(Object o, Object dto, List<String> filed, String folder, String subfolder){
        for (String f : filed) {
            saveFileAndField(o, dto, f, folder, subfolder);
        }
    }

    /**
     *  存储文件 并set值进去
     * @param o DO实体类
     * @param clazz DO实体类class
     * @param dto DTO 实体类
     * @param filed 字段名
     * @param folder 文件夹
     */
    public static void saveFileAndField(Object o, Object dto, String filed, String folder, String subfolder){
        try {
            Object files = MethodUtil.getGetMethod(dto, filed);
            if(ComUtil.isEmpty(files)){
                return;
            }
            Object getMethod = MethodUtil.getGetMethod(o, filed);
            String fileAddress;
            if(files.getClass().isArray()) {
                fileAddress = FileUtil.saveFile((MultipartFile[]) files,folder, ComUtil.isEmpty(subfolder)?filed+"/":subfolder);
            }else {
                fileAddress = FileUtil.saveFile((MultipartFile) files, folder, ComUtil.isEmpty(subfolder)?filed+"/":subfolder);
            }
            //如果插入的新文件为空的话直接返回，不进行赋值处理
            if(ComUtil.isEmpty(fileAddress)){
                return;
            }
            MethodUtil.setValue(o,o.getClass(),filed,String.class,
                    FileUtil.saveNewImgToDatabase(
                            String.valueOf(ComUtil.isEmpty(getMethod)?"":getMethod),
                            fileAddress));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量处理
     * @param o do对象
     * @param dto dto对象
     */
    public static void removeFileAndField(Object o, Object dto, List<String> fileds, String folder){
        for (String filed: fileds) {
            removeFileAndField(o, dto, filed, folder);
        }
    }

    /**
     * 此方法是判断是否有删除的数据进行删除处理
     * @param o do对象
     * @param clazz DO类
     * @param dto dto对象
     * @param filed 字段名
     */
    public static void removeFileAndField(Object o, Object dto, String filed, String folder){
        try {
            //首先让字段名加上String，此字段是获取是否有删除数据的值
            if(!ComUtil.isEmpty(MethodUtil.getGetMethod(dto, filed+"String"))){
                String value;
                //如果不为空，则判断是List类型 还是 String类型，String类型的话需要转换成List类型，方便统一处理
                if(MethodUtil.getGetMethod(dto, filed+"String") instanceof List){
                    value = FileUtil.removeFile((List) MethodUtil.getGetMethod(dto, filed+"String"),
                            (String) MethodUtil.getGetMethod(o, filed), folder);
                }else {
                    value = FileUtil.removeFile(Lists.newArrayList((String) MethodUtil.getGetMethod(dto, filed+"String")),
                            (String) MethodUtil.getGetMethod(o, filed), folder);
                }
                //删除完成后把值set进DO
                MethodUtil.setValue(o, o.getClass(), filed, String.class, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 遍历并加上ip地址前缀返给前端，针对多张图片的，返回List<JSONObject>類型，單張圖片的另行處理
     * @param o 对象
     * @param clazz 类
     * @param filedList 字段名集合
     */
    public static void addBatchPrefix(Object o, Class<?> clazz, Object ovo, List<String> filedList){
        for (String field : filedList) {
            addPrefix(o, clazz, ovo, field);
        }
    }

    /**
     * 遍历并加上ip地址前缀返给前端,只针对返回多张图片
     * @param o 对象
     * @param clazz 类
     * @param field 字段名
     */
    public static void addPrefix(Object odo, Class<?> clazz, Object ovo, String field){
        try {
            if(!ComUtil.isEmpty(MethodUtil.getGetMethod(odo, field))){
                List<JSONObject> jsonObjects = getJsonObjects((String) Objects.requireNonNull(MethodUtil.getGetMethod(odo, field)));
                MethodUtil.setValue(ovo, clazz, field, List.class,
                        jsonObjects);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<JSONObject> getJsonObjects(String value) {
        if (ComUtil.isEmpty(value)){
            return null;
        }
        List<JSONObject> jsonObjects = new ArrayList<>();
        Splitter.on(CommonConstants.SEPARATOR).trimResults().splitToList(value)
                .forEach(envi -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", envi.substring(envi.lastIndexOf("/") + 1));
                    jsonObject.put("url", FileConstants.FILE_ADDRESS + envi);
                    jsonObjects.add(jsonObject);
                });
        return jsonObjects;
    }

    /**
     * 新增图片路径追加
     * @param databaseString 数据库值
     * @param newImgString 要追加的值
     * @return
     */
    public static String saveNewImgToDatabase(String databaseString, String newImgString){
        if(!ComUtil.isEmpty(databaseString) && !ComUtil.isEmpty(newImgString)){
            return databaseString + CommonConstants.SEPARATOR + newImgString;
        }else {
            return newImgString;
        }
    }

    /**
     * 删除文件
     * @param removeFileList 要删除的集合
     * @param dataBaseFileName 数据库中存的数据
     * @return
     */
    public static String removeFile(List<String> removeFileList, String dataBaseFileName, String folder) {
        if(ComUtil.isEmpty(dataBaseFileName)){
            throw new MyException(ExceptionEnum.DELETE_IMG_FAILED);
        }
        //获取数据库中的字段，分割成集合，判断是否大于1
        List<String> stringList = Lists.newLinkedList();
        List<String> databaseFileList = Splitter.on(CommonConstants.SEPARATOR)
                .splitToList(dataBaseFileName);

        stringList.addAll(databaseFileList);
         for(String removeFile:removeFileList){
             //如果有ip则把ip去掉，没有则直接引用
             String remove = FileUtil.wipeOffIp(removeFile, folder);
            //所删除的一定是数据库之前所含有的，如果没有and报错
            if(!stringList.contains(remove)){
                throw new MyException(ExceptionEnum.DELETE_IMG_FAILED);
            }
            //数据库字段移除
            stringList.remove(remove);
            //磁盘文件删除
            FileUtil.deleteFile(FileConstants.FILE_FLODER + remove);
        }
        if(!ComUtil.isEmpty(stringList)){
            return Joiner.on(CommonConstants.SEPARATOR).join(stringList);
        }
        return "";
    }

    /**
     * 把ip去除掉
     * @param address
     * @return
     */
    public static String wipeOffIp(String address, String folder){
        return address.substring(address.indexOf(folder));
    }

    /**
     * 对文件进行批量存储
     * @param environments 文件集合
     * @param type 文件类型
     * @return
     * @throws Exception
     */
    public static String saveFile(MultipartFile[] environments, String folder, String type) {
        List<String> fileNameList = Lists.newLinkedList();
        //批量插入
        if(!ComUtil.isEmpty(environments) && environments.length > 0){
            for (MultipartFile environment:environments){
                if(ComUtil.isEmpty(environment)){
                    continue;
                }
                //插入
                String address = FileUtil.saveFile(environment,
                        folder, type);
                //把名字add到一个集合里
                fileNameList.add(address);
            }
        }
        //把集合中的元素用,连接起来并返回
        return Joiner.on(CommonConstants.SEPARATOR).skipNulls().join(fileNameList);
    }

    /**
     * 单个文件存储
     * @param type 类型
     * @param environment 文件
     * @return
     * @throws Exception
     */
    public static String saveFile(MultipartFile environment, String folder, String type) {
        if(!ComUtil.isEmpty(environment)){
            try {
                return FileUtil.saveFile(environment.getInputStream(),
                        //图片存入地址
                        folder + type,
                        environment.getOriginalFilename(),
                        null);
            } catch (Exception e) {
                e.printStackTrace();
                throw new MyException(ExceptionEnum.SAVE_FILE_FAILED);
            }
        }
        return null;
    }
}
