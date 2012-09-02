/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.kernel.util;

public class GetFirstLetter {
	
	// 国标码和区位码转换常量
	private static final int GB_SP_DIFF = 160;
	// 存放国标一级汉字不同读音的起始区位码
	private static final int[] secPosvalueList = { 1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858,
			4027, 4086, 4390, 4558, 4684, 4925, 5249, 5600 };
	// 存放国标一级汉字不同读音的起始区位码对应读音
	private static final char[] firstLetter = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'W',
			'X', 'Y', 'Z' };
	
	/**
	 * 获取一个汉字的拼音首字母。 GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码 例如汉字"你"的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n'
	 */
	private static char convert(byte[] bytes) {
		
		char result = '-';
		int secPosvalue = 0;
		int i;
		for (i = 0; i < bytes.length; i++) {
			bytes[i] -= GetFirstLetter.GB_SP_DIFF;
		}
		secPosvalue = bytes[0] * 100 + bytes[1];
		for (i = 0; i < 23; i++) {
			if (secPosvalue >= GetFirstLetter.secPosvalueList[i] && secPosvalue < GetFirstLetter.secPosvalueList[i + 1]) {
				result = GetFirstLetter.firstLetter[i];
				break;
			}
		}
		return result;
	}
	
	/**
	 * 获取一个字符串的拼音码
	 * 
	 * @param oriStr
	 * @return
	 */
	public static String getFirstLetter(String oriStr) {
		final String str = oriStr.toLowerCase();
		final StringBuffer buffer = new StringBuffer();
		char ch;
		char[] temp;
		for (int i = 0; i < str.length(); i++) { // 依次处理str中每个字符
			ch = str.charAt(i);
			temp = new char[] { ch };
			final byte[] uniCode = new String(temp).getBytes();
			if (uniCode[0] < 128 && uniCode[0] > 0) { // 非汉字
				buffer.append(temp);
			} else {
				buffer.append(GetFirstLetter.convert(uniCode));
			}
		}
		return buffer.toString();
	}
	public static void main(String[] args) {
		System.out.println(getFirstLetter("魏振耀"));
	}
}
