package org.message;

/**
 * 全局消息
 * @author Start
 */
public interface IMsg {
	/**
	 * OK
	 */
	final int _200=200;
	/**
	 * 禁止访问
	 */
	final int _403=403;
	/**
	 * 无法访问
	 */
	final int _404=404;
	/**
	 * 当前请求方式无法访问
	 */
	final int _405=405;
	/**
	 * 请求主体内容不能为空
	 */
	final int _411=411;
	/**
	 * 请求主体内容长度超过限制 
	 */
	final int _413=413;
	/**
	 * 请求URL的参数长度超过限制
	 */
	final int _414=414;
	/**
	 * 不支持媒体类型 
	 */
	final int _415=415;
	/**
	 * 内部服务器出错
	 */
	final int _500=500;
	/**
	 * 系统维护中
	 */
	final int _993=993;
	/**
	 * 接口维护中
	 */
	final int _994=994;
	/**
	 * 该接口暂不开放
	 */
	final int _995=995;
	/**
	 * 未知错误 
	 */
	final int _996=996;
	/**
	 * 系统资源不足
	 */
	final int _997=997;
	/**
	 * 系统设置错误
	 */
	final int _998=998;
	/**
	 * 系统忙，请稍候再试
	 */
	final int _999=999;
	/**
	 * DES加密时发生异常
	 */
	final int _1001=1001;
	/**
	 * DES解密时发生异常
	 */
	final int _1002=1002;
	/**
	 * URL编码出错
	 */
	final int _1003=1003;
	/**
	 * URL解码出错
	 */
	final int _1004=1004;
	/**
	 * 数据出现异常
	 */
	final int _1005=1005;
	/**
	 * 日期时间格式有误
	 */
	final int _1006=1006;
	/**
	 * IP受限，禁止访问
	 */
	final int _2001=2001;
	/**
	 * 非法操作，禁止访问
	 */
	final int _2002=2002;
	/**
	 * 权限不足，禁止访问
	 */
	final int _2003=2003;
	/**
	 * 操作来源受限，禁止访问
	 */
	final int _2004=2004;
	/**
	 * 签名不能为空
	 */
	final int _2005=2005;
	/**
	 * 签名不匹配
	 */
	final int _2006=2006;
	/**
	 * 签名时发现异常
	 */
	final int _2007=2007;
	/**
	 * 请求时间不能为空
	 */
	final int _2008=2008;
	/**
	 * 请求时间格式不正确
	 */
	final int _2009=2009;
	/**
	 * 请求时间已失效
	 */
	final int _2010=2010;
	/**
	 * 请求数据异常
	 */
	final int _2011=2011;
	/**
	 * 请求的数据长度超过限制
	 */
	final int _2012=2012;
	/**
	 * 请求主体异常
	 */
	final int _2013=2013;
	/**
	 * 请求主体内容获取失败
	 */
	final int _2014=2014;
	/**
	 * IP地址不能为空
	 */
	final int _2015=2015;
	/**
	 * IP地址格式不正确
	 */
	final int _2016=2016;
}