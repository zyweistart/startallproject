package org.platform.user.service.impl;

import org.busines.CommonBusinesUtils;
import org.framework.config.Business;
import org.framework.config.FieldConstants;
import org.framework.exception.AppRuntimeException;
import org.framework.service.impl.RootServiceImpl;
import org.framework.utils.StringUtils;
import org.message.IMsg;
import org.platform.user.dao.IdentifierDao;
import org.platform.user.entity.Identifier;
import org.platform.user.service.IdentifierService;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;
import org.zyweistartframework.persistence.ds.Transaction;
import org.zyweistartframework.utils.StackTraceInfo;

@Service("identifierService")
public final class IdentifierServiceImpl extends RootServiceImpl<Identifier,String> 
implements IdentifierService {

	private IdentifierDao identifierDao;
	
	public IdentifierServiceImpl(@Qualifier("identifierDao")IdentifierDao identifierDao) {
		super(identifierDao);
		this.identifierDao=identifierDao;
	}
	
	private final static String SQL_loadIdentifierBypassKey="SELECT * FROM USE_IDENTIFIER WHERE PASSKEY=?";
	@Override
	public Identifier loadIdentifierByPassKey(String passKey) {
		return createNativeSQLOnlySingle(SQL_loadIdentifierBypassKey,passKey);
	}
	
	private final static String SQL_identifier_save="INSERT INTO USE_IDENTIFIER" +
			"(ID,TYPE,GENERATEIP,GENERATETIME,USERID,PASSKEY,INVALIDTIME,USESTATUS) " +
			"VALUES(?,?,?,SYSDATE,?,?,SYSDATE+?,?)";
	@Override
	public void save(Identifier identifier) {
		identifierDao.executeUpdate(SQL_identifier_save,
				StringUtils.random(),
				identifier.getType(),
				identifier.getGenerateIP(),
				identifier.getUser().getId(),
				identifier.getPasskey(),
				Business.IDENTIFIER_INVALIDTIME_DAY,
				FieldConstants.UseStatus_NOUSE);
	}
	
	private final static String SQL_regenerate="UPDATE USE_IDENTIFIER SET USESTATUS=? " +
			"WHERE USERID=? AND USESTATUS=? AND TYPE=? AND INVALIDTIME>SYSDATE";
	@Override
	public void regenerate(Identifier identifier) {
		Transaction transaction=identifierDao.getTransient();
		try {
			transaction.beginTrans();
			//把未使用并未过期的标识码设为失效状态
			identifierDao.executeUpdate(SQL_regenerate,
					FieldConstants.UseStatus_INVALID,
					identifier.getUser().getId(),
					FieldConstants.UseStatus_NOUSE,
					identifier.getType());
			save(identifier);
			transaction.commitTrans();
		} catch (Exception e) {
			throw new AppRuntimeException(CommonBusinesUtils.exception(transaction, e,StackTraceInfo.getTraceInfo()));
		}
	}
	
	private final static String SQL_activation_identifier="UPDATE USE_IDENTIFIER SET " +
			"USESTATUS=?,HANDLEIP=?,HANDLETIME=SYSDATE WHERE PASSKEY=? AND USESTATUS=? AND INVALIDTIME>SYSDATE";
	private final static String SQL_activation_user="UPDATE USE_USER SET ACTIVESTATUS=? WHERE ID=? AND ACTIVESTATUS=?";
	@Override
	public void activation(Identifier identifier) {
		Transaction transaction=identifierDao.getTransient();
		try {
			transaction.beginTrans();
			//把标识码设为已使用状态并
			if(identifierDao.executeUpdate(SQL_activation_identifier,
					FieldConstants.UseStatus_OKUSE,
					identifier.getHandleIP(),
					identifier.getPasskey(),
					FieldConstants.UseStatus_NOUSE)==1&&
				identifierDao.executeUpdate(SQL_activation_user,
						Constants.TRUE,
						identifier.getUser().getId(),
						Constants.FALSE)==1){
				transaction.commitTrans();
			}else{
				throw new AppRuntimeException(IMsg._1005);
			}
		} catch (Exception e) {
			throw new AppRuntimeException(CommonBusinesUtils.exception(transaction, e,StackTraceInfo.getTraceInfo()));
		}
	}

}