package com.quotorcloud.quotor.common.core.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
/**
 * @author liugh
 * @since on 2018/5/8.
 */
public class GenerationSequenceUtil {


    /**
     * 生成4位随机数
     * @param charCount
     * @return
     */
    public static String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }

    private static int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }

    /**
     * 生成UUID
     * @param prefix 前缀
     * @return
     */
    public static String generateUUID(String prefix){
        String uuid = (UUID.randomUUID().toString()).replaceAll("-","");
        if (!ComUtil.isEmpty(prefix)) {
            uuid = prefix + "-" + uuid;
        }
        return uuid;
    }



}
