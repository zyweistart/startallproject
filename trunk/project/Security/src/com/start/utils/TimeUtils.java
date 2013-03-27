package com.start.utils;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

	/**
	 * 获取今天星期几的索引
	 * @return 1:星期日2：星期一3:星期二.....7:星期六
	 */
	public static int getWeekIndex(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		return c.get(Calendar.DAY_OF_WEEK);
	}
	
}