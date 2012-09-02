/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.kernel.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class GenerateCode {
	
	/**
	 * 根据日期生成相应的随机数
	 * 
	 * @return
	 */
	public static String genRandCode() {
		final StringBuilder sb = new StringBuilder(15);
		final SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
		sb.append(date.format(new Date()));
		return sb.toString();
	}
	
	/**
	 * 生成0-9，A-z组合成的随机数
	 * 
	 * @param length
	 * @return
	 */
	public static String getrandstring(int length) {
		final StringBuffer buffer = new StringBuffer("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		final StringBuffer SB = new StringBuffer();
		final Random rd = new Random();
		final int rand = buffer.length();
		
		for (int i = 0; i < length; i++) {
			SB.append(buffer.charAt(rd.nextInt(rand)));
		}
		return SB.toString();
	}
	/**
	 * 生成随机数
	 * 32位值：6fc089dfbbac4a76989e353d1397da3d
	 */
	public static String random(){
		Random ra=new Random();
		return MD5.calcMD5(UUID.randomUUID().toString() + System.currentTimeMillis() + ra.nextInt(999999999));
	}
}
