package com.quotorcloud.quotor.common.core.util;



import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liugh
 * @since on 2018/5/8.
 */
public class StringUtil {
//
//    public static String pin(String chinese) throws Exception {
//        String pinyin = "";
//        HanyuPinyinOutputFormat pinyinOutputFormat = new HanyuPinyinOutputFormat();
//        pinyinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
//        pinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        String[] pinyinArray = null;
//        for(char ch : chinese.toCharArray()){
//            pinyinArray = PinyinHelper.toHanyuPinyinStringArray(ch,pinyinOutputFormat);
//            pinyin += ComUtil.isEmpty(pinyinArray) ? ch : pinyinArray[0];
//        }
//        return pinyin;
//    }

    /**
     * 获取方法中指定注解的value值返回
     * @param method 方法名
     * @param validationParamValue 注解的类名
     * @return
     */
    public static String getMethodAnnotationOne(Method method, String validationParamValue) {
        String retParam =null;
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                String str = parameterAnnotations[i][j].toString();
                if(str.indexOf(validationParamValue) >0){
                    retParam = str.substring(str.indexOf("=")+1,str.indexOf(")"));
                }
            }
        }
        return retParam;
    }

    public static boolean isValidURLAddress(String url) {
        String pattern = "([h]|[H])([t]|[T])([t]|[T])([p]|[P])([s]|[S]){0,1}://([^:/]+)(:([0-9]+))?(/\\S*)*";
        return url.matches(pattern);
    }
    /**
     * 将utf-8编码的汉字转为中文
     * @author zhaoqiang
     * @param str
     * @return
     */
    public static String utf8Decoding(String str){
        String result = str;
        try
        {
            result = URLDecoder.decode(str, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean checkEmail(String email) {
        if (ComUtil.isEmpty(email)) {
            return false;
        }
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
    /**
     * 验证手机号码，11位数字，1开通，第二位数必须是3456789这些数字之一 *
     * @param mobileNumber
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            // Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Pattern regex = Pattern.compile("^1[345789]\\d{9}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 将字符串中被[]包住的字符全部返回
     * @param msg
     * @return
     */
    public static List<String> extractMessageByRegular(String msg){

        List<String> list=new ArrayList<String>();
        Pattern p = Pattern.compile("(\\[[^\\]]*\\])");
        Matcher m = p.matcher(msg);
        while(m.find()){
            list.add(m.group().substring(1, m.group().length()-1));
        }
        return list;
    }

    /**
     * 将字符串中的中文转化为拼音首字母
     */
    public static String getPingYinSuoXie(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        String output = "";
        if (inputString != null && inputString.length() > 0 && !"null".equals(inputString)) {
            char[] input = inputString.trim().toCharArray();
            try {
                for (int i = 0; i < input.length; i++) {
                    if (java.lang.Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {

                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                        output += temp[0].charAt(0);
                    } else {
                        output += java.lang.Character.toString(input[i]);
                    }
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        } else {
            return "*";
        }
        return output;
    }

    public static void main(String[] args) {
        String str = "{f.name} and {f.age}";
        String pattern = "\\{[^}]*\\}";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        while (m.find()) {
            String g = m.group();
            String target = g.substring(1, g.length() - 1);//去掉花括号
            str = str.replace(g,target);
        }

        System.out.println("str:" + str);
    }


}
