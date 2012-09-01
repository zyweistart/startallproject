package org.framework.config;

import java.util.Date;

import org.framework.utils.TimeUtils;

/**
 * 全局常量
 * @author Start
 */
public interface Variable {
	/**
	 * 一秒=1000毫秒
	 */
	final Integer ONESECOND=1000;
	/**
	 * 十分钟的毫秒数
	 */
	final Integer TEN_MILLISECOND = 600000;
	/**
	 * 一个小时毫秒数
	 */
	final Long HOUR_MILLISECOND = 3600000L;
	/**
	 * 一天的毫秒数
	 */
	final Long DAY_MILLISECOND = 86400000L;
	/**
	 * 明天的凌晨00:30分钟
	 */
	final Date TOMORROWHOURTIME=TimeUtils.getTomorrowHourTime(0,30);
	/**
	 * 请求前缀标识
	 */
	final String RequestPrefix="x--";
	/**
	 * 角色
	 */
	final String ROLE="role";
	/**
	 * 权限
	 */
	final String PERMISSION="permission";
	
	final Integer Byte_Hex = 1024;
	/**
	 * 缓冲区大小，默认8KB
	 */
	final Integer BUFFER=Byte_Hex*8;
	/**
	 * 存储大小的单位
	 */
	final String[] SIZEUNITS = new String[] { "BYTE", "KB", "MB", "GB", "TB", "PB" };
	
}