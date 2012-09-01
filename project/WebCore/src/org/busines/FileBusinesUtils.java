package org.busines;

import org.framework.config.FieldConstants;
import org.framework.exception.AppRuntimeException;
import org.framework.utils.StringCheck;
import org.framework.utils.StringUtils;
import org.framework.utils.TimeUtils;
import org.message.IFile;
import org.platform.file.entity.Storage;
import org.platform.file.entity.UploadApply;

public class FileBusinesUtils {
	/**
	 * 检测上传申请信息
	 */
	public static void checkUploadApply(UploadApply uploadApply){
		if(uploadApply==null){
			//申请信息不存在
			throw new AppRuntimeException(IFile._10303006);
		}else if(uploadApply.getUseStatus().equals(FieldConstants.UseStatus_OKUSE)){
			//申请信息已使用
			throw new AppRuntimeException(IFile._10303007);
		}else if(uploadApply.getUseStatus().equals(FieldConstants.UseStatus_INVALID)){
			//申请信息已失效
			throw new AppRuntimeException(IFile._10303008);
		}else if(TimeUtils.compare_date(uploadApply.getInvalidTime(),TimeUtils.getSysTime())==-1){
			//申请信息已过期
			throw new AppRuntimeException(IFile._10303009);
		}
	}
	/**
	 * 检测上传信息
	 */
	public static void checkUploadInfo(Storage storage){
		if(StringUtils.isEmpty(String.valueOf(storage.getTransportMode()))){
			//传输模式不能为空
			throw new AppRuntimeException(IFile._10301001);
		}else if(storage.getTransportSize()==null){
			//传输大小不能为空
			throw new AppRuntimeException(IFile._10301002);
		}else if(StringUtils.isEmpty(storage.getTransportMD5())){
			//传输校验码不能为空
			throw new AppRuntimeException(IFile._10301003);
		}else if(!StringCheck.checkMd5(storage.getTransportMD5())){
			//传输校验码格式不正确
			throw new AppRuntimeException(IFile._10301004);
		}else if(storage.getOriginalSize()==null){
			//原始大小不能为空
			throw new AppRuntimeException(IFile._10301005);
		}else if(storage.getOriginalSize()<storage.getTransportSize()){
			//传输大小不能大于原始大小
			throw new AppRuntimeException(IFile._10301006);
		}else if(StringUtils.isEmpty(storage.getOriginalMD5())){
			//原始校验码不能为空
			throw new AppRuntimeException(IFile._10301007);
		}else if(!StringCheck.checkMd5(storage.getOriginalMD5())){
			//原始校验码格式不正确
			throw new AppRuntimeException(IFile._10301008);
		}else if(StringUtils.isEmpty(storage.getFileName())){
			//文件名称不能为空
			throw new AppRuntimeException(IFile._10301009);
		}
	}
}
