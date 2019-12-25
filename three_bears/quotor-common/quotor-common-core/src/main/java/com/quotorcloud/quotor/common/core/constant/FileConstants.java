package com.quotorcloud.quotor.common.core.constant;

import java.util.HashSet;
import java.util.Set;

public class FileConstants {

    public static final int BYTE_BUFFER = 1024;

    public static Set<String> METHOD_URL_SET = new HashSet<>();

//    图片地址
    public static String FILE_ADDRESS = "http://127.0.0.1:9001";

//    文件夹
    public static String FILE_FLODER = "C:/pic";

    /**
     * 用户注册默认角色
     */
    public static final int DEFAULT_REGISTER_ROLE = 5;

    public static final int BUFFER_MULTIPLE = 10;

    //验证码过期时间
    public static final Long PASS_TIME =  50000 * 60 *1000L;

    //根菜单节点
    public static final String ROOT_MENU = "0";

    //菜单类型，1：菜单  2：按钮操作
    public static final int TYPE_MENU = 1;

    //菜单类型，1：菜单  2：按钮操作
    public static final int TYPE_BUTTON = 2;

    public static Boolean isPass = false;

    //用户名登录
    public static final int LOGIN_USERNAME = 0;
    //手机登录
    public static final int LOGIN_MOBILE = 1;
    //邮箱登录
    public static final int LOGIN_EMAIL = 2;

    //启用
    public static final int ENABLE = 1;
    //禁用
    public static final int DISABLE = 0;

    public static class FilePostFix{
        public static final String ZIP_FILE =".zip";

        public static final String [] IMAGES ={"jpg", "jpeg",
                "JPG", "JPEG", "gif", "GIF", "bmp", "BMP", "png"};
        public static final String [] ZIP ={"ZIP","zip","rar","RAR"};
        public static final String [] VIDEO ={"mp4","MP4","mpg",
                "mpe","mpa","m15","m1v", "mp2","rmvb"};
        public static final String [] APK ={"apk","exe"};
        public static final String [] OFFICE ={"xls","xlsx",
                "docx","doc","ppt","pptx"};

    }
    public class FileType{
        public static final int TEACHER_ENVI = 1;
        public static final int TEACHER_PASSPORT = 2;
        public static final int FILE_ZIP = 2;
        public static final int FILE_VEDIO= 3;
        public static final int FILE_APK = 4;
        public static final int FIVE_OFFICE = 5;
        public static final String FILE_TEACHER_IMG_DIR= "/academy/teacher/";
        public static final String FILE_COURSE_IMG_DIR_= "/academy/course/";
        public static final String FILE_EMPLOYEE_IMG_DIR_= "/employee/";
        public static final String FILE_USER_IMG_DIR = "/sys/user/";
        public static final String FILE_ICON_IMG_DIR = "/sys/icon/";
        public static final String FILE_MENU_IMG_DIR = "/sys/menu/";
        public static final String FILE_DEPT_IMG_DIR = "/sys/dept/";
        public static final String FILE_INVITI_IMG_DIR = "/invite/";
        public static final String FILE_INN_IMG_DIR = "/inn/";
        public static final String FILE_MEMBER_LOG_IMG_DIR = "/member/log/";
        public static final String FILE_EXPEND_IMG_DIR = "/expend/";
        public static final String FILE_BANNER_IMG_DIR = "/banner/";
        public static final String FILE_MEMBER_IMG_DIR = "/member/";
        public static final String FILE_CONDITION_PRO_IMG_DIR = "/condition/";
        public static final String FILE_ZIP_DIR= "/zip/";
        public static final String FILE_VEDIO_DIR= "/video/";
        public static final String FILE_APK_DIR= "/apk/";
        public static final String FIVE_OFFICE_DIR= "/office/";
    }
}
