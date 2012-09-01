package org.busines;

import org.framework.config.FieldConstants;
import org.framework.exception.AppRuntimeException;
import org.framework.request.RequestUtil;
import org.framework.utils.TimeUtils;
import org.message.ISystem;
import org.message.IUser;
import org.platform.user.entity.Account;
import org.platform.user.entity.Identifier;
import org.platform.user.entity.User;
import org.platform.user.entity.UserLoginLog;


public class UserBusinesUtils {
	/**
	 * 检测账户登陆信息
	 */
	public static void checkUserLoginLog(UserLoginLog userLoginLog){
		if(userLoginLog==null){
			//登陆信息不存在，请重新登陆
			throw new AppRuntimeException(ISystem._10101001);
		}else if(userLoginLog.getOnlineStatus()){
			if(TimeUtils.compare_date(userLoginLog.getInvalidTime(), TimeUtils.getSysTimeS())==-1){
				//登陆超时，请重新登陆
				throw new AppRuntimeException(ISystem._10101002);
			}
		}else{
			//登陆信息异常，请重新登陆
			throw new AppRuntimeException(ISystem._10101003);
		}
	}
	/**
	 * 检测登陆日志并对数据进行签名
	 */
	public static void checkUserLoginlogSignature(RequestUtil reqeustUtil,UserLoginLog userLoginLog){
		if(userLoginLog!=null){
			reqeustUtil.signaturesHmacSHA1(userLoginLog.getAccessKey());
		}else{
			//登陆信息不存在，请重新登陆
			throw new AppRuntimeException(ISystem._10101001);
		}
	}
	/**
	 * 检测账户
	 */
	public static void checkAccount(Account account,Boolean pwdVerifyflag){
		if(account==null){
			//账户不存在
			throw new AppRuntimeException(ISystem._10102001);
		}else if(account.getStatus().equals(FieldConstants.UserStatus_Pause)){
			//账户已暂停使用
			throw new AppRuntimeException(ISystem._10102002);
		}else if(account.getStatus().equals(FieldConstants.UserStatus_Logout)){
			//账户已注销
			throw new AppRuntimeException(ISystem._10102003);
		}else if(pwdVerifyflag){
			if(!account.getPwdVerifyflag()){
				//为保证你账户的安全请先修改密码再进行相关操作
				throw new AppRuntimeException(ISystem._10102004);
			}
		}
	}
	/**
	 * 检测用户信息
	 * @param user
	 * @param pwdVerifyflag
	 * 密码修改检测
	 */
	public static void checkUserByAccount(User user,Boolean pwdVerifyflag){
		if(user==null){
			//用户不存在
			throw new AppRuntimeException(IUser._10201001);
		}
		UserBusinesUtils.checkAccount(user.getAccount(), pwdVerifyflag);
	}
	/**
	 * 检测用户识别信息
	 */
	public static void checkIdentifier(Identifier identifier){
		if(identifier==null){
			//识别码信息不存在
			throw new AppRuntimeException(IUser._10202001);
		}else if(identifier.getUseStatus().equals(FieldConstants.UseStatus_OKUSE)){
			//识别码信息已使用
			throw new AppRuntimeException(IUser._10202002);
		}else if(identifier.getUseStatus().equals(FieldConstants.UseStatus_INVALID)){
			//识别码信息已失效
			throw new AppRuntimeException(IUser._10202003);
		}else if(TimeUtils.compare_date(identifier.getInvalidTime(),TimeUtils.getSysTime())==-1){
			//识别码信息已过期
			throw new AppRuntimeException(IUser._10202004);
		}
	}
}
