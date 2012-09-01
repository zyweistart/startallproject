package org.framework.config;

import org.framework.utils.PropertiesUtils;

/**
 * 业务常量busines.properties中的键值对
 * @author Start
 */
public interface Business {
	/**
	 * 备份服务器的负载索引号
	 */
	final String BACKUP_BALANCED_WORKER_INDEX=PropertiesUtils.getBusines("BACKUP_BALANCED_WORKER_INDEX");
	/**
	 * 激活码有效限(单位:天)
	 */
	final Integer IDENTIFIER_INVALIDTIME_DAY=Integer.parseInt(PropertiesUtils.getBusines("IDENTIFIER_INVALIDTIME_DAY"));
	/**
	 * 管理员登陆活跃时间(单位:小时)
	 */
	final Integer MANAGERLOGINLOG_ACTIVATION_HOUR=Integer.parseInt(PropertiesUtils.getBusines("MANAGERLOGINLOG_ACTIVATION_HOUR"));
	/**
	 * 用户登陆活跃时间(单位:小时)
	 */
	final Integer USERLOGINLOG_ACTIVATION_HOUR=Integer.parseInt(PropertiesUtils.getBusines("USERLOGINLOG_ACTIVATION_HOUR"));
	/**
	 * 上传申请有效限(单位:分钟)
	 */
	final Integer UPLOADAPPLY_INVALIDTIME_MINUTE=Integer.parseInt(PropertiesUtils.getBusines("UPLOADAPPLY_INVALIDTIME_MINUTE"));
	/**
	 * 信息来源
	 */
	final String CONSTANTS_SOURCE=PropertiesUtils.getBusines("CONSTANTS_SOURCE");
	/**
	 * 传输方式
	 */
	final String CONSTANTS_TRANSPORTMODE=PropertiesUtils.getBusines("CONSTANTS_TRANSPORTMODE");
	/**
	 * 资讯页面类型
	 */
	final String PAGETYPE=PropertiesUtils.getBusines("PAGETYPE");
	
}