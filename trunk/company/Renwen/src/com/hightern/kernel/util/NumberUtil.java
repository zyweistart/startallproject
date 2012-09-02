/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.kernel.util;

import java.util.Date;

/**
 * 数字处理类
 * 
 * @author Trail
 */
public class NumberUtil {
	
	/**
	 * 将以数字衔接在一起的字符串转换成数字类型
	 * 
	 * @param numberString
	 * @return
	 */
	public static Integer convert(String numberString) {
		Integer integer = 0;
		try {
			integer = Integer.parseInt(numberString);
		} catch (final NumberFormatException e) {
			return 0;
		}
		return integer;
	}
	
	/**
	 * 判断某个对象是日期类型
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isDate(Object o) {
		if (o instanceof Date) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断某个对象是数字类型
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNumber(Object o) {
		if (o instanceof Byte || o instanceof Short || o instanceof Integer || o instanceof Long || o instanceof Double || o instanceof Float) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断一个变量是否是数字
	 * 
	 * @param numberString
	 * @return
	 */
	public static boolean isNumber(String numberString) {
		try {
			Integer.parseInt(numberString);
		} catch (final NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断某个对象是字符串类型
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isString(Object o) {
		if (o instanceof String) {
			return true;
		} else {
			return false;
		}
		
	}
}
