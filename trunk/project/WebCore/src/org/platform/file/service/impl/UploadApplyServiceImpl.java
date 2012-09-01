package org.platform.file.service.impl;

import org.framework.config.Business;
import org.framework.config.FieldConstants;
import org.framework.exception.AppRuntimeException;
import org.framework.service.impl.RootServiceImpl;
import org.framework.utils.StringUtils;
import org.message.IMsg;
import org.platform.file.dao.UploadApplyDao;
import org.platform.file.entity.UploadApply;
import org.platform.file.service.UploadApplyService;
import org.zyweistartframework.context.annotation.Qualifier;
import org.zyweistartframework.context.annotation.Service;

@Service("uploadApplyService")
public final class UploadApplyServiceImpl extends RootServiceImpl<UploadApply,String> 
implements UploadApplyService {

	private UploadApplyDao uploadApplyDao;
	
	public UploadApplyServiceImpl(@Qualifier("uploadApplyDao")UploadApplyDao uploadApplyDao) {
		super(uploadApplyDao);
		this.uploadApplyDao=uploadApplyDao;
	}
	
	private final static String SQL_uploadapply_save="INSERT INTO FIL_UPLOADAPPLY" +
			"(ID,APPLYSIZE,PASSKEY,USESTATUS,GENERATEIP,GENERATETIME,INVALIDTIME,USERID) " +
			"VALUES(?,?,?,?,?,SYSDATE,SYSDATE+?/24/60,?)";
	@Override
	public void save(UploadApply uploadApply) {
		if(uploadApplyDao.executeUpdate(SQL_uploadapply_save,
				StringUtils.random(),
				uploadApply.getApplySize(),
				uploadApply.getPasskey(),
				FieldConstants.UseStatus_NOUSE,
				uploadApply.getGenerateIP(),
				Business.UPLOADAPPLY_INVALIDTIME_MINUTE,
				uploadApply.getUser().getId())!=1){
			throw new AppRuntimeException(IMsg._1005);
		}
	}

	private final static String SQL_loadPassKeyByUploadApply="SELECT * FROM FIL_UPLOADAPPLY WHERE PASSKEY=?";
	@Override
	public UploadApply loadPassKeyByUploadApply(String passkey) {
		return createNativeSQLOnlySingle(SQL_loadPassKeyByUploadApply,passkey);
	}

	private final static String SQL_applyUse_success="UPDATE FIL_UPLOADAPPLY SET " +
			"USESTATUS=?,HANDLEIP=?,HANDLETIME=SYSDATE WHERE USERID=? AND  " +
			"PASSKEY=? AND USESTATUS=? AND INVALIDTIME>SYSDATE";
	@Override
	public void applyUse(UploadApply entity) {
		if(uploadApplyDao.executeUpdate(SQL_applyUse_success,
				FieldConstants.UseStatus_OKUSE,
				entity.getHandleIP(),
				entity.getUser().getId(),
				entity.getPasskey(),
				FieldConstants.UseStatus_NOUSE)!=1){
			throw new AppRuntimeException(IMsg._1005);
		}
	}
}
