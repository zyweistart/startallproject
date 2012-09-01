package org.platform.user.service.impl;

import org.busines.CommonBusinesUtils;
import org.framework.config.FieldConstants;
import org.framework.exception.AppRuntimeException;
import org.framework.service.impl.RootServiceImpl;
import org.message.IMsg;
import org.platform.user.dao.UserDao;
import org.platform.user.entity.Identifier;
import org.platform.user.entity.User;
import org.platform.user.service.UserService;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;
import org.zyweistartframework.persistence.ds.Transaction;
import org.zyweistartframework.utils.StackTraceInfo;

@Service("userService")
public final class UserServiceImpl extends RootServiceImpl<User,String> 
implements UserService {

	private UserDao userDao;
	
	public UserServiceImpl(@Qualifier("userDao")UserDao userDao) {
		super(userDao);
		this.userDao=userDao;
	}
	
	private final static String SQL_isPresenceByEmail="SELECT COUNT(1) FROM USE_USER WHERE EMAIL=?";
	@Override
	public boolean isPresenceByEmail(String email) {
		return userDao.polymerizationQuery(SQL_isPresenceByEmail,email)!=0;
	}
	
	private final static String SQL_loadUserByEmail="SELECT * FROM USE_USER WHERE EMAIL=?";
	@Override
	public User loadUserByEmail(String email) {
		return createNativeSQLOnlySingle(SQL_loadUserByEmail,email);
	}
	
	private final static String SQL_modifyPassword_user="UPDATE USE_USER SET PASSWORD=? WHERE ID=?";
	private final static String SQL_modifyPassword_account="UPDATE SYS_ACCOUNT SET PWDVERIFYFLAG=? WHERE ID=? AND STATUS=?";
	@Override
	public void modifyPassword(User user) {
		Transaction transaction=userDao.getTransient();
		try {
			transaction.beginTrans();
			if(userDao.executeUpdate(SQL_modifyPassword_user,
					user.getPassword(),
					user.getId())==1&&
				userDao.executeUpdate(SQL_modifyPassword_account,
						Constants.TRUE,
						user.getAccount().getId(),
						FieldConstants.UserStatus_Normal)==1){
				transaction.commitTrans();
			}else{
				throw new AppRuntimeException(IMsg._1005);
			}
		} catch (Exception e) {
			throw new AppRuntimeException(CommonBusinesUtils.exception(transaction, e,StackTraceInfo.getTraceInfo()));
		}
	}
	
	private final static String SQL_resetPassword="UPDATE USE_IDENTIFIER SET USESTATUS=? " +
			"WHERE USERID=? AND PASSKEY=? AND USESTATUS=? AND TYPE=? AND INVALIDTIME>SYSDATE";
	@Override
	public void resetPassword(User user,Identifier identifer) {
		Transaction transaction=userDao.getTransient();
		try {
			transaction.beginTrans();
			if(userDao.executeUpdate(SQL_resetPassword,
					FieldConstants.UseStatus_OKUSE,
					user.getId(),
					identifer.getPasskey(),
					FieldConstants.UseStatus_NOUSE,
					identifer.getType())==1){
				modifyPassword(user);
				transaction.commitTrans();
			}else{
				throw new AppRuntimeException(IMsg._1005);
			}
		} catch (Exception e) {
			throw new AppRuntimeException(CommonBusinesUtils.exception(transaction, e,StackTraceInfo.getTraceInfo()));
		}
	}
	
}