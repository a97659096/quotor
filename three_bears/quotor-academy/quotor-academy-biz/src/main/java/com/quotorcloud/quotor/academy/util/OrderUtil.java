package com.quotorcloud.quotor.academy.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.quotorcloud.quotor.common.core.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class OrderUtil {
    @Autowired
    private RedisUtil redisUtil;

    @Value("${spring.redis.key.prefix.orderId}")
    private String REDIS_KEY_PREFIX_ORDER_ID;

    @Value("${spring.redis.key.prefix.memberId}")
    private String REDIS_KEY_PREFIX_MEMBER_NUMBER_ID;

    /**
     * 生成18位订单编号:8位日期+4位随机数+6位以上自增id
     */
    public String generateOrderSn() {
        StringBuilder sb = new StringBuilder();
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        //按加盟商id 和 月做key值
        String key = REDIS_KEY_PREFIX_ORDER_ID + date;
        Long increment = redisUtil.incr(key, 1);
        sb.append(date);
        //4位随机数
        sb.append(String.format("%04d", new Random().nextInt(9999)));
        String incrementStr = increment.toString();
        //6位自增
        if (incrementStr.length() <= 6) {
            sb.append(String.format("%06d", increment));
        } else {
            sb.append(incrementStr);
        }
        return sb.toString();
    }

    /**
     * 会员卡号生成
     * @return
     */
    public String generateMemberNumber(){
        StringBuilder sb = new StringBuilder();
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        //按加盟商id 和 月做key值
        String key = REDIS_KEY_PREFIX_MEMBER_NUMBER_ID + date;
        Long increment = redisUtil.incr(key, 1);
        sb.append(date);
        //4位随机数
        String incrementStr = increment.toString();
        //6位自增
        if (incrementStr.length() <= 6) {
            sb.append(String.format("%04d", increment));
        } else {
            sb.append(incrementStr);
        }
        return sb.toString();
    }

    public void genertorQRCode(String codeUrl, HttpServletResponse response){
        //将codeurl生成二维码
        try{
            //生成二维码配置
            Map<EncodeHintType,Object> hints =  new HashMap<>();
            //设置纠错等级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            //编码类型
            hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");

            BitMatrix bitMatrix = new MultiFormatWriter().encode(codeUrl, BarcodeFormat.QR_CODE,400,400,hints);
            OutputStream out =  response.getOutputStream();

            MatrixToImageWriter.writeToStream(bitMatrix,"png",out);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
