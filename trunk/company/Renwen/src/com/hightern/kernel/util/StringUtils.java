/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.kernel.util;

import java.util.Random;

public class StringUtils {
	
	/**
	 * 将字符串的首字母大写
	 * 
	 * @param name
	 * @return String
	 */
	public static String changeFirstLetterUpperCase(String name) {
		final String[] strs = name.toLowerCase().split("_");
		String result = "";
		String preStr = "";
		for (final String element : strs) {
			if (preStr.length() == 1) {
				result += element;
			} else {
				result += StringUtils.capitalize(element);
			}
			preStr = element;
		}
		return result;
	}
	
	/**
	 * 替换字符串
	 * 
	 * @param inString
	 * @param oldPattern
	 * @param newPattern
	 * @return
	 */
	public static String replace(String inString, String oldPattern, String newPattern) {
		if (inString == null) { return null; }
		if (oldPattern == null || newPattern == null) { return inString; }
		
		final StringBuffer sbuf = new StringBuffer();
		int pos = 0;
		int index = inString.indexOf(oldPattern);
		final int patLen = oldPattern.length();
		while (index >= 0) {
			sbuf.append(inString.substring(pos, index));
			sbuf.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sbuf.append(inString.substring(pos));
		
		return sbuf.toString();
	}
	
	public static String capitalize(String str) {
		return StringUtils.changeFirstCharacterCase(str, true);
	}
	
	public static String uncapitalize(String str) {
		return StringUtils.changeFirstCharacterCase(str, false);
	}
	
	private static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (str == null || str.length() == 0) { return str; }
		final StringBuffer buf = new StringBuffer(str.length());
		if (capitalize) {
			buf.append(Character.toUpperCase(str.charAt(0)));
		} else {
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
		buf.append(str.substring(1));
		return buf.toString();
	}
	
	private static final Random RANDOM = new Random();
	
	public static String randomNumeric(int count) {
		return StringUtils.random(count, false, true);
	}
	
	public static String random(int count, boolean letters, boolean numbers) {
		return StringUtils.random(count, 0, 0, letters, numbers);
	}
	
	public static String random(int count, int start, int end, boolean letters, boolean numbers) {
		return StringUtils.random(count, start, end, letters, numbers, null, StringUtils.RANDOM);
	}
	
	public static String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars, Random random) {
		if (count == 0) {
			return "";
		} else if (count < 0) { throw new IllegalArgumentException("Requested random string length " + count + " is less than 0."); }
		if (start == 0 && end == 0) {
			end = 'z' + 1;
			start = ' ';
			if (!letters && !numbers) {
				start = 0;
				end = Integer.MAX_VALUE;
			}
		}
		
		final char[] buffer = new char[count];
		final int gap = end - start;
		
		while (count-- != 0) {
			char ch;
			if (chars == null) {
				ch = (char) (random.nextInt(gap) + start);
			} else {
				ch = chars[random.nextInt(gap) + start];
			}
			if (letters && Character.isLetter(ch) || numbers && Character.isDigit(ch) || !letters && !numbers) {
				if (ch >= 56320 && ch <= 57343) {
					if (count == 0) {
						count++;
					} else {
						buffer[count] = ch;
						count--;
						buffer[count] = (char) (55296 + random.nextInt(128));
					}
				} else if (ch >= 55296 && ch <= 56191) {
					if (count == 0) {
						count++;
					} else {
						buffer[count] = (char) (56320 + random.nextInt(128));
						count--;
						buffer[count] = ch;
					}
				} else if (ch >= 56192 && ch <= 56319) {
					count++;
				} else {
					buffer[count] = ch;
				}
			} else {
				count++;
			}
		}
		return new String(buffer);
	}
}
