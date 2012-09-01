package org.platform.user.action;

import org.busines.CommonBusinesUtils;
import org.busines.EmailUtil;
import org.busines.UserBusinesUtils;
import org.framework.action.BaseUserAction;
import org.framework.config.FieldConstants;
import org.framework.exception.AppRuntimeException;
import org.framework.utils.StringCheck;
import org.framework.utils.StringUtils;
import org.framework.utils.TimeUtils;
import org.message.IMsg;
import org.message.IUser;
import org.platform.user.entity.Account;
import org.platform.user.entity.Identifier;
import org.platform.user.entity.User;
import org.platform.user.entity.UserLoginLog;
import org.platform.user.service.IdentifierService;
import org.platform.user.service.UserService;
import org.zyweistartframework.context.annotation.Controller;
import org.zyweistartframework.context.annotation.Resource;
import org.zyweistartframework.persistence.ds.Transaction;
import org.zyweistartframework.utils.StackTraceInfo;

@Controller("user")
public final class UserAction extends BaseUserAction {
	
	@Resource
	private UserService userService;
	@Resource
	private IdentifierService identifierService;
	/**
	 * 注册账户
	 */
	public void register(){
		User user=getRequestUtil().getPostData(User.class);
		getRequestUtil().signaturesMD5();
		if(user.getSource()==null){
			//注册来源不能为空
			result(IUser._10201002);
		}else if(user.getUserType()==null){
			//用户类型不能为空
			result(IUser._10201005);
		}else if(StringUtils.isEmpty(user.getEmail())){
			//电子邮件不能为空
			result(IUser._10201006);
		}else if(!StringCheck.checkEmail(user.getEmail())){
			//电子邮件格式不正确
			result(IUser._10201007);
		}else if(userService.isPresenceByEmail(user.getEmail())){
			//电子邮件已经存在
			result(IUser._10201008);
		}else if(StringUtils.isEmpty(user.getPassword())){
			//密码不能为空
			result(IUser._10201009);
		}else if(!StringCheck.checkMd5(user.getPassword())){
			//密码格式不正确
			result(IUser._10201010);
		}else if(StringUtils.isEmpty(user.getIP())){
			//IP地址不能为空
			result(IMsg._2015);
		}else if(!StringCheck.checkIp(user.getIP())){
			//IP地址格式不正确
			result(IMsg._2016);
		}else{
			//对密码再进行DES加密
			user.setPassword(StringUtils.doKeyEncrypt(user.getPassword()));
			
			Account account=new Account();
			account.setRegisterSource(user.getSource());
			account.setUserType(user.getUserType());
			account.setLoginErrorCount(0);
			account.setLoginCount(0);
			account.setRegisterIP(user.getIP());
			account.setRegisterTime(TimeUtils.getSysTimeLong());
			account.setPwdVerifyflag(true);
			account.setStatus(FieldConstants.UserStatus_Normal);
			user.setAccount(account);
			
			Identifier identifier=new Identifier();
			//随机生成一个标识码并进行DES加密
			identifier.setPasskey(StringUtils.doKeyEncrypt(StringUtils.random(user.getEmail())));
			identifier.setGenerateIP(user.getIP());
			identifier.setType(FieldConstants.IdentifierType_EMAIL);
			identifier.setUser(user);
			
			Transaction transaction=userService.getTransient();
			try {
				transaction.beginTrans();
				userService.save(user);
				identifierService.save(identifier);
				transaction.commitTrans();
			} catch (Exception e) {
				result(CommonBusinesUtils.exception(transaction, e,StackTraceInfo.getTraceInfo()));
				return;
			}
			EmailUtil.sendAccountRegister(user.getEmail(), identifier.getPasskey());
			result(IMsg._200);
		}
	}
	/**
	 * 用户登陆
	 */
	public void login(){
		User user=getRequestUtil().getGetData(User.class);
		if(user.getSource()==null){
			//登陆来源不能为空
			result(IUser._10201003);
		}else if(user.getLoginMode()==null){
			//登陆模式不能为空
			result(IUser._10201004);
		}else if(StringUtils.isEmpty(user.getEmail())){
			//电子邮件不能为空
			result(IUser._10201006);
		}else if(!StringCheck.checkEmail(user.getEmail())){
			//电子邮件格式不正确
			result(IUser._10201007);
		}else if(StringUtils.isEmpty(user.getIP())){
			//IP地址不能为空
			result(IMsg._2015);
		}else if(!StringCheck.checkIp(user.getIP())){
			//IP地址格式不正确
			result(IMsg._2016);
		}else{
			User u=userService.loadUserByEmail(user.getEmail());
			UserBusinesUtils.checkUserByAccount(u,true);
			try{
				//使用当前登录用户的密码进行签名
				getRequestUtil().signaturesHmacSHA1(StringUtils.doKeyDecrypt(u.getPassword()));
			}catch(AppRuntimeException e){
				userLoginLogService.login(u.getAccount(),false);
				//签名不正确或密码有误
				result(IUser._10201016);
				return;
			}
			userLoginLog=new UserLoginLog();
			userLoginLog.setId(StringUtils.random());
			//生成访问ID
			userLoginLog.setAccessId(StringUtils.random());
			//生成数据访问签名键
			userLoginLog.setAccessKey(StringUtils.random(userLoginLog.getAccessId()));
			userLoginLog.setSource(user.getSource());
			userLoginLog.setLoginMode(user.getLoginMode());
			userLoginLog.setLoginIP(user.getIP());
			userLoginLog.setUser(u);
			Transaction transaction=userService.getTransient();
			try{
				transaction.beginTrans();
				userLoginLogService.login(u.getAccount(), true);
				userLoginLogService.save(userLoginLog);
				transaction.commitTrans();
			}catch(Exception e){
				result(CommonBusinesUtils.exception(transaction, e,StackTraceInfo.getTraceInfo()));
				return;
			}
			result(userLoginLog);
		}
	}
	/**
	 * 退出登陆
	 */
	public void logout(){
		getRequestUtil().getGetData(null);
		UserBusinesUtils.checkUserLoginlogSignature(getRequestUtil(), currentUserLoginLog());
		currentUserLoginLog().setQuitRemark("退出登陆");
		userLoginLogService.logout(currentUserLoginLog());
		result(IMsg._200);
	}
	/**
	 * 重发激活邮件
	 */
	public void resendActivationEmail(){
		User user=getRequestUtil().getGetData(User.class);
		getRequestUtil().signaturesMD5();
		if(StringUtils.isEmpty(user.getEmail())){
			//电子邮件不能为空
			result(IUser._10201006);
		}else if(!StringCheck.checkEmail(user.getEmail())){
			//电子邮件格式不正确
			result(IUser._10201007);
		}else if(StringUtils.isEmpty(user.getIP())){
			//IP地址不能为空
			result(IMsg._2015);
		}else if(!StringCheck.checkIp(user.getIP())){
			//IP地址格式不正确
			result(IMsg._2016);
		}else{
			User u=userService.loadUserByEmail(user.getEmail());
			UserBusinesUtils.checkUserByAccount(u, false);
			if(u.getActiveStatus()){
				//账户已激活无须重新激活
				result(IUser._10201017);
			}else{
				Identifier identifier=new Identifier();
				//随机生成一个标识码并进行DES加密
				identifier.setPasskey(StringUtils.doKeyEncrypt(StringUtils.random(u.getEmail())));
				identifier.setGenerateIP(user.getIP());
				identifier.setType(FieldConstants.IdentifierType_EMAIL);
				identifier.setUser(u);
				identifierService.regenerate(identifier);
				EmailUtil.sendAccountRegister(user.getEmail(), identifier.getPasskey());
				result(identifier);
			}
		}
	}
	/**
	 * 激活账户
	 */
	public void activation(){
		Identifier identifier=getRequestUtil().getGetData(Identifier.class);
		getRequestUtil().signaturesMD5();
		if(StringUtils.isEmpty(identifier.getPasskey())){
			//标识码不能为空
			result(IUser._10202005);
		}else if(StringUtils.isEmpty(identifier.getHandleIP())){
			//IP地址不能为空
			result(IMsg._2015);
		}else if(!StringCheck.checkIp(identifier.getHandleIP())){
			//IP地址格式不正确
			result(IMsg._2016);
		}else{
			identifier=identifierService.loadIdentifierByPassKey(identifier.getPasskey());
			UserBusinesUtils.checkIdentifier(identifier);
			identifierService.activation(identifier);
			result(IMsg._200);
		}
	}
	/**
	 * 密码更改
	 */
	public void changePassword(){
		getRequestUtil().getPostData(null);
		UserBusinesUtils.checkUserLoginlogSignature(getRequestUtil(), currentUserLoginLog());
		User user=currentUserLoginLog().getUser();
		UserBusinesUtils.checkUserByAccount(user, false);
		String oldpassword=getRequestUtil().getHeaderValue("oldpassword");
		String newPassword=getRequestUtil().getHeaderValue("newpassword");
		if(StringUtils.isEmpty(oldpassword)){
			//密码不能为空
			result(IUser._10201009);
		}else if(!StringUtils.doKeyDecrypt(user.getPassword()).equals(oldpassword)){
			//密码错误
			result(IUser._10201011);
		}else if(StringUtils.isEmpty(newPassword)){
			//新密码不能为空
			result(IUser._10201012);
		}else if(!StringCheck.checkMd5(newPassword)){
			//新密码格式不正确
			result(IUser._10201013);
		}else if(oldpassword.equals(newPassword)){
			//旧密码与新密码相同
			result(IUser._10201015);
		}else{
			user.setPassword(StringUtils.doKeyEncrypt(newPassword));
			userService.modifyPassword(user);
			result(IMsg._200);
		}
	}
	/**
	 * 忘记密码
	 */
	public void forgetPassword(){
		User user=getRequestUtil().getPostData(User.class);
		getRequestUtil().signaturesMD5();
		if(StringUtils.isEmpty(user.getEmail())){
			//电子邮件不能为空
			result(IUser._10201006);
		}else if(!StringCheck.checkEmail(user.getEmail())){
			//电子邮件格式不正确
			result(IUser._10201007);
		}else{
			user.setIP(getRequestUtil().getRequestIP());
			User u=userService.loadUserByEmail(user.getEmail());
			UserBusinesUtils.checkUserByAccount(u, false);
			Identifier identifier=new Identifier();
			//随机生成一个标识码并进行DES加密
			identifier.setPasskey(StringUtils.doKeyEncrypt(StringUtils.random(user.getEmail())));
			identifier.setGenerateIP(user.getIP());
			identifier.setType(FieldConstants.IdentifierType_PASSWORD);
			identifier.setUser(u);
			identifierService.regenerate(identifier);
			EmailUtil.sendAccountForgetPassword(u.getEmail(), identifier.getPasskey());
			result(identifier);
		}
	}
	/**
	 * 重设密码
	 */
	public void resetPassword(){
		getRequestUtil().getPostData(null);
		getRequestUtil().signaturesMD5();
		String passkey=getRequestUtil().getHeaderValue("passkey");
		String newPassword=getRequestUtil().getHeaderValue("newpassword");
		if(StringUtils.isEmpty(passkey)){
			//标识码不能为空
			result(IUser._10202005);
		}else if(StringUtils.isEmpty(newPassword)){
			//密码不能为空
			result(IUser._10201009);
		}else if(!StringCheck.checkMd5(newPassword)){
			//密码格式不正确
			result(IUser._10201010);
		}else{
			Identifier identifier=identifierService.loadIdentifierByPassKey(passkey);
			UserBusinesUtils.checkIdentifier(identifier);
			UserBusinesUtils.checkUserByAccount(identifier.getUser(),false);
			identifier.getUser().setPassword(StringUtils.doKeyEncrypt(newPassword));
			userService.resetPassword(identifier.getUser(),identifier);
			result(IMsg._200);
		}
	}
	
}