package org.framework.config;

import org.framework.utils.StringUtils;
import org.zyweistartframework.config.ConfigConstant;
import org.zyweistartframework.config.GlobalConfig;
/**
 * 配置常量
 * @author Start
 */
public interface ConfigParameter {
	/**
	 * 系统状态
	 */
	final Boolean SYSTEMSTATUS = StringUtils.nullToBoolean(GlobalConfig.Constants.get("SYSTEMSTATUS"));
	/**
	 * 是否为正式环境
	 */
	final Boolean SYSTEMFLAG = StringUtils.nullToBoolean(GlobalConfig.Constants.get("SYSTEMFLAG"));
	/**
	 * 服务初始化
	 */
	final Boolean INITSTATUS = StringUtils.nullToBoolean(GlobalConfig.Constants.get("INITSTATUS"));
	/**
	 * 负载索引一般用于标明在哪台服务器上
	 */
	final String BALANCED_WORKER_INDEX=StringUtils.nullToStrTrim(GlobalConfig.Constants.get("BALANCED_WORKER_INDEX"));
	/**
	 * 系统名称
	 */
	final String SYSTEMNAME = StringUtils.nullToStrTrim(GlobalConfig.Constants.get(SYSTEMFLAG ?"SYSTEMNAME":"SYSTEMNAME_TEST"));
	/**
	 * 系统版本
	 */
	final String SYSTEMINFO = SYSTEMNAME + GlobalConfig.Constants.get("VERSION");
	/**
	 * 系统的完整信息
	 */
	final String SYSTEMINFO_FULL = SYSTEMINFO + "(" + BALANCED_WORKER_INDEX+")";
	
	/**
	 * 日志文件的扩展名
	 */
	final String LOGSUFFIX=StringUtils.nullToStrTrim(GlobalConfig.Constants.get("LOGSUFFIX"));
	/**
	 * 日志文件最大的文件大小
	 */
	final Long LOGMAXFILESIZE=StringUtils.convertSize(GlobalConfig.Constants.get("LOGMAXFILESIZE"));
	
	/**
	 * 电子邮件主机SMTP
	 */
	final String SMTPHOST=StringUtils.nullToStrTrim(GlobalConfig.Constants.get("SMTPHOST"));
	/**
	 * 电子邮件发送用户名
	 */
	final String SMTPUSERNAME=StringUtils.nullToStrTrim(GlobalConfig.Constants.get("SMTPUSERNAME"));
	/**
	 * 电子邮件发送登陆密码DES加密
	 */
	final String SMTPPASSWORD=StringUtils.doKeyDecrypt(StringUtils.nullToStrTrim(GlobalConfig.Constants.get("SMTPPASSWORD")));
	/**
	 * 电子邮件发送主题
	 */
	final String SMTPFROM=StringUtils.nullToStrTrim(GlobalConfig.Constants.get("SMTPFROM"));
	/**
	 * 系统管理员的电子邮件
	 */
	final String SYSTEMEMAIL=StringUtils.nullToStrTrim(GlobalConfig.Constants.get("SYSTEMEMAIL"));
	
	/**
	 * CPU报警值
	 */
	final Integer CPUALARMPERC=StringUtils.nullToIntZero(GlobalConfig.Constants.get("CPUALARMPERC"));
	/**
	 * 监控的磁盘
	 */
	final String MONITORDISK=StringUtils.nullToStrTrim(GlobalConfig.Constants.get("MONITORDISK"));
	/**
	 * 硬盘报警值
	 */
	final Long DISKALARMSIZE=StringUtils.convertSize(GlobalConfig.Constants.get("DISKALARMSIZE"));
	/**
	 * 内存报警值
	 */
	final Long MEMALARMSIZE=StringUtils.convertSize(GlobalConfig.Constants.get("MEMALARMSIZE"));
	/**
	 * 数据库大小
	 */
	final Long DBALARMSIZE=StringUtils.convertSize(GlobalConfig.Constants.get("DBALARMSIZE"));
	
	/**
	 * 数据加密模式
	 */
	final Integer DATA_ENCRYPT_MODE=Integer.parseInt(GlobalConfig.Constants.get("DATA_ENCRYPT_MODE"));
	/**
	 * 数据存储模式
	 */
	final Integer DATA_STORAGE_MODE=Integer.parseInt(GlobalConfig.Constants.get("DATA_STORAGE_MODE"));
	
	/**
	 * 阿里云AccessID
	 */
	final String ALIYUNACCESSID=GlobalConfig.Constants.get("ALIYUNACCESSID");
	/**
	 * 阿里云AccessKey
	 */
	final String ALIYUNACCESSKEY=GlobalConfig.Constants.get("ALIYUNACCESSKEY");
	/**
	 * 阿里云存储的bucket
	 */
	final String ALIYUNBUCKETNAME=GlobalConfig.Constants.get("ALIYUNBUCKETNAME");
	
	/**
	 * 文件上传大小
	 */
	public static final Long MAX_FILE_UPLOADSIZE = ConfigConstant.MAXUPLOADSIZE;
	/**
	 * 附属文件上传大小
	 */
	public static final Long MAX_SUBFILE_UPLOADSIZE = ConfigConstant.MAXUPLOADSIZE;
	/**
	 * 上传申请大小
	 */
	public static final Long MAX_APPLY_FILEUPLOADSIZE = ConfigConstant.MAXUPLOADSIZE;
	/**
	 * 文本数据上传大小
	 */
	public static final Long MAX_TEXT_UPLOADSIZE =(long) (Variable.Byte_Hex*Variable.Byte_Hex*8);
	/**
	 * 请求参数的长度
	 */
	public static final Integer MAX_REQUEST_QUERYSTRING_LENGTH =Variable.Byte_Hex;
	/**
	 * 请求主体内容大小
	 */
	public static final Long MAX_REQUEST_BODY_CONTENT_LENGTH =(long) (Variable.Byte_Hex*Variable.Byte_Hex*4);
	
}