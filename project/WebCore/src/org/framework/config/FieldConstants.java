package org.framework.config;
/**
 * 实体字段常量
 * @author Start
 */
public interface FieldConstants {
	/////信息来源
	/**
	 * 网页应用
	 */
	final Integer Source_WEB=1;
	/**
	 * 桌面应用
	 */
	final Integer Source_DESKTOP=2;
	/**
	 * 手机应用
	 */
	final Integer Source_PHONE=3;
	// 使用状态
	/**
	 * 1:未使用
	 */
	final Integer UseStatus_NOUSE=1;
	/**
	 * 2:已使用
	 */
	final Integer UseStatus_OKUSE=2;
	/**
	 * 3:已失效
	 */
	final Integer UseStatus_INVALID=3;
	//访问权限
	/**
	 * 公共均可读取操作
	 */
	final Integer AccessRights_PUBLIC=1;
	/**
	 * 公共可读取,只有创建者可操作
	 */
	final Integer AccessRights_PUBLICREAD=2;
	/**
	 * 私有,创建者可读取操作
	 */
	final Integer AccessRights_PRIVATE=3;
	// 数据的加密模式
	/**
	 * 不加密
	 */
	final Integer EncryptMode_NO=1;
	/**
	 * DES加密
	 */
	final Integer EncryptMode_DES=2;
	//存储模式
	/**
	 * 本地
	 */
	final Integer StorageMode_LOCAL=1;
	/**
	 * 阿里云
	 */
	final Integer StorageMode_ALIYUN=2;
	//传输方式
	/**
	 * 不压缩
	 */
	final Integer TransportMode_NO=1;
	/**
	 * GZIP压缩
	 */
	final Integer TransportMode_GZIP=2;
	//验证类别
	/**
	 * 1:邮箱验证(帐户激活);
	 */
	final Integer IdentifierType_EMAIL=1;
	/**
	 * 2:密码找回
	 */
	final Integer IdentifierType_PASSWORD=2;
	/**
	 * 3:手机验证
	 */
	final Integer IdentifierType_PHONE=3;
	//用户状态
	/**
	 * 正常
	 */
	final Integer UserStatus_Normal=1;
	/**
	 * 暂停
	 */
	final Integer UserStatus_Pause=2;
	/**
	 * 注销
	 */
	final Integer UserStatus_Logout=3;
	
}