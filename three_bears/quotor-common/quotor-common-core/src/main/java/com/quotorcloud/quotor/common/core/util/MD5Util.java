package com.quotorcloud.quotor.common.core.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;

public class MD5Util {
	
	public static String md5(String src) {
		return DigestUtils.md5Hex(src);
	}
	
	private static final String salt = "1a2b3c4d";
	
//	public static String inputPassToFormPass(String inputPass) {
//		String str = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);
//		System.out.println(str);
//		return md5(str);
//	}
	
	public static String formPassToDBPass(String formPass, String salt) {
		String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
		return md5(str);
	}

	/**
	 * md5常用工具类
	 * @param data
	 * @return
	 */
	public static String MD5(String data){
		try {

			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte [] array = md5.digest(data.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (byte item : array) {
				sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString().toUpperCase();

		}catch (Exception e){
			e.printStackTrace();
		}
		return null;

	}
//
//	public static String inputPassToDbPass(String inputPass, String saltDB) {
//		String formPass = inputPassToFormPass(inputPass);
//		String dbPass = formPassToDBPass(formPass, saltDB);
//		return dbPass;
//	}
	
	public static void main(String[] args) {
//		System.out.println(inputPassToFormPass("123456"));//d3b1294a61a07da9b49b6e22b2cbd7f9
//		System.out.println(formPassToDBPass(inputPassToFormPass("123456"), "1a2b3c4d"));
//		System.out.println(inputPassToDbPass("123456", "1a2b3c4d"));//b7797cce01b4b131b433b6acf4add449
	}
	
}
