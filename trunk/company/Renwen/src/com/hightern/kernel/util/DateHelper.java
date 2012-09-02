/**
 * Copyright (c) 2009. All rights reserved. 所属公司：Hightern 宁波海腾计算机有限公司
 */
package com.hightern.kernel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateHelper {
	
	static Logger log = Logger.getLogger(DateHelper.class);
	
	/**
	 * 取得当前日期
	 * 
	 * @return
	 */
	public static String getDate() {
		final SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		return date.format(new Date());
	}
	
	/**
	 * 取得完整的系统日期和时间
	 * 
	 * @return
	 */
	public static String getFullDate() {
		final SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return date.format(new Date());
	}
	
	/**
	 * 取得微秒
	 * 
	 * @return
	 */
	public static String getMillis() {
		final Long millis = System.currentTimeMillis();
		return millis.toString();
	}
	
	/**
	 * 取得当前时间
	 * 
	 * @return
	 */
	public static String getTime() {
		final SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
		return date.format(new Date());
	}
	
	/**
	 * 将字符串转换成Date
	 * 
	 * @param aMask
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);
		
		if (DateHelper.log.isDebugEnabled()) {
			DateHelper.log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
		}
		try {
			date = df.parse(strDate);
		} catch (final ParseException pe) {
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}
		
		return date;
	}
}
