package org.platform.file.action;

import org.busines.CommonBusinesUtils;
import org.busines.FileBusinesUtils;
import org.busines.UserBusinesUtils;
import org.framework.action.BaseUserAction;
import org.framework.config.ConfigParameter;
import org.framework.config.FieldConstants;
import org.framework.request.FileUploadHelper;
import org.framework.result.Download;
import org.framework.utils.StringCheck;
import org.framework.utils.StringUtils;
import org.message.IFile;
import org.message.IMsg;
import org.platform.file.entity.Record;
import org.platform.file.entity.Storage;
import org.platform.file.entity.UploadApply;
import org.platform.file.service.RecordService;
import org.platform.file.service.UploadApplyService;
import org.zyweistartframework.context.annotation.Controller;
import org.zyweistartframework.context.annotation.Resource;
import org.zyweistartframework.persistence.ds.Transaction;
import org.zyweistartframework.utils.StackTraceInfo;

@Controller("file")
public final class FileAction extends BaseUserAction {
	
	@Resource
	private RecordService recordService;
	@Resource
	private UploadApplyService uploadApplyService;
	/**
	 * 申请上传
	 */
	public void apply(){
		UploadApply uploadApply=getRequestUtil().getGetData(UploadApply.class);
		UserBusinesUtils.checkUserLoginlogSignature(getRequestUtil(),currentUserLoginLog());
		if(uploadApply.getApplySize()==null){
			//申请大小不能为空
			result(IFile._10303001);
		}else if(uploadApply.getApplySize()>ConfigParameter.MAX_APPLY_FILEUPLOADSIZE){
			//申请大小不能大于最大申请值
			result(IFile._10303002);
		}else{
			uploadApply.setGenerateIP(getRequestUtil().getRequestIP());
			uploadApply.setUser(currentUserLoginLog().getUser());
			uploadApply.setPasskey(StringUtils.random());
			uploadApplyService.save(uploadApply);
			result(IMsg._200,uploadApply);
		}
	}
	/**
	 * 申请上传
	 */
	public void applyUpload(){
		Storage storage=getRequestUtil().getApplyDataUpload(Storage.class);
		UserBusinesUtils.checkUserLoginlogSignature(getRequestUtil(), currentUserLoginLog());
		FileBusinesUtils.checkUploadInfo(storage);
		String passkey=getRequestUtil().getHeaderValue("passkey");
		if(StringUtils.isEmpty(passkey)){
			//申请密钥不能为空
			result(IFile._10303003);
		}else if(StringCheck.checkMd5(passkey)){
			//申请密钥格式不正确
			result(IFile._10303004);
		}else{
			UploadApply uploadApply=uploadApplyService.loadPassKeyByUploadApply(passkey);
			FileBusinesUtils.checkUploadApply(uploadApply);
			if(uploadApply.getApplySize()<storage.getOriginalSize()){
				//上传的文件大小大于申请的大小
				result(IFile._10303005);
			}else{
				//处理IP
				uploadApply.setHandleIP(getRequestUtil().getRequestIP());
				Transaction transaction=uploadApplyService.getTransient();
				try{
					transaction.beginTrans();
					uploadApplyService.applyUse(uploadApply);
					//文件上传
					Record fileRecord=FileUploadHelper.fileUpload(request, storage);
					if(fileRecord!=null){
						fileRecord.setAccessRights(FieldConstants.AccessRights_PUBLIC);
						recordService.save(fileRecord);
						transaction.commitTrans();
						result(IMsg._200);
					}else{
						transaction.rollbackTrans();
						//文件存储时出现异常
						result(IFile._10301010);
					}
				}catch(Exception e){
					result(CommonBusinesUtils.exception(transaction, e,StackTraceInfo.getTraceInfo()));
					return;
				}
			}
		}
	}
	/**
	 * 上传
	 */
	public void upload(){
		Storage storage=getRequestUtil().getDataUpload(Storage.class);
		UserBusinesUtils.checkUserLoginlogSignature(getRequestUtil(),currentUserLoginLog());
		FileBusinesUtils.checkUploadInfo(storage);
		Record record=FileUploadHelper.fileUpload(request, storage);
		if(record!=null){
			record.setAccessRights(FieldConstants.AccessRights_PUBLIC);
			recordService.save(record);
			result(IMsg._200);
		}else{
			//文件存储时出现异常
			result(IFile._10301010);
		}
	}
	/**
	 * 下载
	 */
	public void download(){
		getRequestUtil().getGetData(null);
		getRequestUtil().signaturesMD5();
		String filekey=getRequestUtil().getHeaderValue("filekey");
		if(StringUtils.isEmpty(filekey)){
			//文件提取码不能为空
			result(IFile._10302001);
		}else{
			Record record=recordService.lazyLoad(filekey);
			if(record!=null){
				result=new Download(record.getStorage());
			}else{
				//文件记录信息不存在
				result(IFile._10302002);
			}
		}
	}
}