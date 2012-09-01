package org.framework.config;

import org.framework.utils.StringUtils;
import org.zyweistartframework.config.ConfigConstant;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.config.GlobalConfig;

/**
 * 资源路径
 * @author Start
 */
public interface SystemPath {
	/**
	 * 数据文件的路径
	 */
	final String DATA_PATH=ConfigConstant.ROOTPATH+Constants.FILESEPARATOR+"Data"+Constants.FILESEPARATOR;
	/**
	 * 日志文件路径
	 */
	final String LOG_PATH=ConfigConstant.ROOTPATH+Constants.FILESEPARATOR+"Logs"+Constants.FILESEPARATOR;
	/**
	 * Tomcat日志目录
	 */
	final String TOMCATLOG_PATH=StringUtils.nullToStrTrim(GlobalConfig.Constants.get("TOMCATLOG_PATH_"+ConfigParameter.BALANCED_WORKER_INDEX));
	
	/**
	 * 备份目录主路径
	 */
	final String BACKUP_PATH=ConfigConstant.ROOTPATH+Constants.FILESEPARATOR+"BackUp"+Constants.FILESEPARATOR;
	/**
	 * 数据文件备份路径
	 */
	final String DATABACKUP_PATH=BACKUP_PATH+"Data"+Constants.FILESEPARATOR;
	/**
	 * 程序级日志备份路径
	 */
	final String LOGBACKUP_PATH=BACKUP_PATH+"Logs"+Constants.FILESEPARATOR+"Program"+Constants.FILESEPARATOR;
	/**
	 * 系统级日志备份路径
	 */
	final String SYSTEMLOGBACKUP_PATH=BACKUP_PATH+"Logs"+Constants.FILESEPARATOR+"System"+Constants.FILESEPARATOR;
	/**
	 * 服务器日志备份路径
	 */
	final String TOMCATLOGBACKUP_PATH=BACKUP_PATH+"Logs"+Constants.FILESEPARATOR+"Tomcat"+Constants.FILESEPARATOR;
	/**
	 * 程序源文件备份路径
	 */
	final String PROGRAMBACKUP_PATH=BACKUP_PATH+"Program"+Constants.FILESEPARATOR;
	/**
	 * 数据库文件备份路径
	 */
	final String DATABASEBACKUP_PATH=BACKUP_PATH+"DataBase"+Constants.FILESEPARATOR;
	
	/**
	 * 邮件模版目录
	 */
	final String EMAILPATH = ConfigConstant.RESOURCEPATH + "data" + Constants.FILESEPARATOR  + "email" + Constants.FILESEPARATOR;
	/**
	 * DES签名密钥
	 */
	final String DESKEYKEY = ConfigConstant.RESOURCEPATH + "data" + Constants.FILESEPARATOR   + "key" + Constants.FILESEPARATOR +
			(ConfigParameter.SYSTEMFLAG ? "deskey" : "deskeytest") + "key.data";
}
