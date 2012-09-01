package org.framework.result;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.framework.config.ConfigParameter;
import org.framework.config.FieldConstants;
import org.framework.config.SystemPath;
import org.framework.config.Variable;
import org.framework.exception.AppRuntimeException;
import org.framework.utils.DES;
import org.framework.utils.LogUtils;
import org.framework.utils.StringUtils;
import org.message.IFile;
import org.platform.file.entity.Storage;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.controller.IActionResult;
import org.zyweistartframework.utils.StackTraceInfo;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.GetObjectRequest;
import com.aliyun.openservices.oss.model.OSSObject;

/**
 * 文件下载
 * @author Start
 */
public class Download  implements IActionResult {
	
	private final static String Contenttype="Content-type:";
	private final static String AcceptRanges="Accept-Ranges";
	private final static String AcceptLength="Accept-Length:";
	private final static String ContentDisposition="Content-Disposition";
	private final static String bytes="bytes";
	private final static String attachment="attachment;filename=\"";
	
	private Storage fileStorage;
	/**
	 * 响应头信息
	 */
	private Map<String,String> responseHeader=new HashMap<String,String>();
	
	public Download(Storage fileStorage){
		if(fileStorage!=null){
			this.fileStorage=fileStorage;
			//原始文件大小
			responseHeader.put("originalsize",String.valueOf(fileStorage.getOriginalSize()));
			//原始文件MD5
			responseHeader.put("originalmd5",fileStorage.getOriginalMD5());
			//文件名称
			responseHeader.put("filename",StringUtils.encode(fileStorage.getFileName()));
		}else{
			//文件存储信息不存在
			throw new AppRuntimeException(IFile._10301019);
		}
	}
	
	public Download(Storage fileStorage,Map<String,String> responseHeader){
		this.fileStorage=fileStorage;
		this.responseHeader.putAll(responseHeader);
	}
	
	@Override
	public void doExecute(ActionResultInvocation invocation) {
		HttpServletResponse response=invocation.getResponse();
		response.setContentLength(fileStorage.getStorageSize());
		if(!StringUtils.isEmpty(fileStorage.getContentType())){
			response.setContentType(fileStorage.getContentType());
			response.setHeader(Contenttype,fileStorage.getContentType());
		}
		response.setHeader(AcceptRanges,bytes);
//		response.setHeader("Content-Range", "bytes 0-120/120");
		response.setHeader(AcceptLength, String.valueOf(fileStorage.getOriginalSize()));
		response.setHeader(ContentDisposition, attachment+ StringUtils.encode(fileStorage.getFileName()) + "\"");
		//输出响应头信息
		if(responseHeader!=null){
			for(String key:responseHeader.keySet()){
				response.setHeader(key,responseHeader.get(key));
			}
		}
		CipherOutputStream cipherOutputStream = null;
		BufferedOutputStream bufferedOutputStream=null;
		BufferedInputStream bufferedInputStream = null;
		try {
			//不管文件存储在哪里都先从本地寻找找不到再到相应的存储区找
			File localFile=new File(SystemPath.DATA_PATH+fileStorage.getStorageSpace()+Constants.FILESEPARATOR+fileStorage.getStorageName());
			if(localFile.exists()){
				bufferedInputStream = new BufferedInputStream(new FileInputStream(localFile));
			}else{
				if(fileStorage.getStorageMode().equals(FieldConstants.StorageMode_ALIYUN)){
					OSSClient oss=new OSSClient(ConfigParameter.ALIYUNACCESSID,ConfigParameter.ALIYUNACCESSKEY);
					OSSObject ossObj=oss.getObject(new GetObjectRequest(ConfigParameter.ALIYUNBUCKETNAME, fileStorage.getStorageName()));
					if(ossObj!=null){
						bufferedInputStream = new BufferedInputStream(ossObj.getObjectContent());
					}
				}else{
					LogUtils.printError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim("文件不存在"));
					return;
				}
			}
			int i=-1;
			byte[] buffer=new byte[Variable.BUFFER];
			bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
			if(fileStorage.getEncryptMode().equals(FieldConstants.EncryptMode_NO)){
				//不加密直接输出
				while ((i = bufferedInputStream.read(buffer)) != -1) {
					bufferedOutputStream.write(buffer, 0, i);
					bufferedOutputStream.flush();
				}
			}else if(fileStorage.getEncryptMode().equals(FieldConstants.EncryptMode_DES)){
				//边解密连输出
				Cipher cipher = Cipher.getInstance(DES.ALGORITHM);
				cipher.init(Cipher.DECRYPT_MODE, DES.getKey(SystemPath.DESKEYKEY));
				cipherOutputStream = new CipherOutputStream(bufferedOutputStream, cipher);
				while ((i = bufferedInputStream.read(buffer)) != -1) {
					cipherOutputStream.write(buffer, 0, i);
					cipherOutputStream.flush();
				}
			}
			buffer=null;
			response.flushBuffer();
		} catch (Exception e) {
			LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
		} finally {
			if(bufferedOutputStream!=null){
				try {
					bufferedOutputStream.flush();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
				}finally{
					try {
						bufferedOutputStream.close();
					} catch (IOException e) {
						LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
					}finally{
						bufferedOutputStream=null;
					}
				}
			}
			if(cipherOutputStream!=null){
				try {
					cipherOutputStream.flush();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
				}finally{
					try {
						cipherOutputStream.close();
					} catch (IOException e) {
						LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
					}finally{
						cipherOutputStream=null;
					}
				}
			}
			if(bufferedInputStream!=null){
				try {
					bufferedInputStream.close();
				} catch (IOException e) {
					LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
				}finally{
					bufferedInputStream=null;
				}
			}
		}
	}
	
}