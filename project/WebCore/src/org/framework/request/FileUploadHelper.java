package org.framework.request;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.zip.GZIPInputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.servlet.http.HttpServletRequest;

import org.framework.config.ConfigParameter;
import org.framework.config.FieldConstants;
import org.framework.config.SystemPath;
import org.framework.config.Variable;
import org.framework.exception.AppRuntimeException;
import org.framework.utils.DES;
import org.framework.utils.FileUtils;
import org.framework.utils.LogUtils;
import org.framework.utils.MD5;
import org.framework.utils.StringUtils;
import org.framework.utils.TimeUtils;
import org.message.IFile;
import org.platform.file.entity.Record;
import org.platform.file.entity.Storage;
import org.zyweistartframework.config.ConfigConstant;
import org.zyweistartframework.config.Constants;
import org.zyweistartframework.utils.StackTraceInfo;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.ObjectMetadata;

/**
 * 文件上传业务工具类
 * @author Start
 */
public class FileUploadHelper {
	/**
	 * 文件上传必须要提交的值:传输模式,传输文件大小,传输校验码,原始文件大小,原始文件校验码,文件名称 
	 */
	public static Record fileUpload(HttpServletRequest request,Storage storage){
		if(storage.getTransportSize()==request.getContentLength()){
			Record record=new Record();
			//文件上传开始时间
			record.setStartUploadTime(System.currentTimeMillis());
			//临时保存时的文件名称
			String tmpStorageFileName=StringUtils.random(storage.getTransportMD5());
			try{
				//把输入流中的数据存储到本地的临时目录中返回该文件的MD5值
				if(storage.getTransportMD5().equals(FileUtils.generateMD5(ConfigConstant.TMPPATH,tmpStorageFileName,request.getInputStream()))){
					//文件上传结束时间
					record.setEndUploadTime(System.currentTimeMillis());
					//获取文件扩展名
					storage.setExtension(FileUtils.getExtension(storage.getFileName()));
					//设置上传的内容类型
					storage.setContentType(request.getContentType());
					//加密模式
					storage.setEncryptMode(ConfigParameter.DATA_ENCRYPT_MODE);
					//存储模式
					storage.setStorageMode(ConfigParameter.DATA_STORAGE_MODE);
					//随机生成一个保存的文件名称
					storage.setStorageName(StringUtils.random(storage.getOriginalMD5()));
					//存储空间默认格式为:yyyymmdd
					storage.setStorageSpace(TimeUtils.getSysdateInt());
					storage.setStorageDirectory(TimeUtils.getSysdateInt());
					String fullStoragePath=SystemPath.DATA_PATH+storage.getStorageDirectory()+Constants.FILESEPARATOR;
					if(storage.getTransportMode().equals(FieldConstants.TransportMode_NO)){
						//未压缩则传输MD5与原始MD5相同，大小也相同
						if(storage.getOriginalMD5().equals(storage.getTransportMD5())){
							if(!storage.getTransportSize().equals(storage.getOriginalSize())){
								//文件大小与原始文件不一致
								throw new AppRuntimeException(IFile._10301011);
							}
						}else{
							//传输文件与原始文件校验码不相同
							throw new AppRuntimeException(IFile._10301012);
						}
					}else if(storage.getTransportMode().equals(FieldConstants.TransportMode_GZIP)){
						String[] ms=FileUtils.decompressGZIP(ConfigConstant.TMPPATH+tmpStorageFileName);
						if(ms!=null){
							if(storage.getOriginalMD5().equals(ms[0])){
								if(!storage.getOriginalSize().equals(StringUtils.nullToIntZero(ms[1]))){
									//文件大小与原始文件不一致
									throw new AppRuntimeException(IFile._10301011);
								}
							}else{
								//文件校验码与原始文件不一致
								throw new AppRuntimeException(IFile._10301015);
							}
						}else{
							//GZIP解压时发生异常
							throw new AppRuntimeException(IFile._10301016);
						}
					}
					String[] ms=storage(ConfigConstant.TMPPATH+tmpStorageFileName,fullStoragePath, storage.getStorageName(),storage.getTransportMode());
					if(ms!=null){
						//存储文件的MD5
						storage.setStorageMD5(ms[0]);
						//存储文件的大小
						storage.setStorageSize(StringUtils.nullToIntZero(ms[1]));
						//正式系统中会根据具体的配置把文件存储到指定的地方
						if(ConfigParameter.SYSTEMFLAG){
							//文件存储到阿里云
							if(FieldConstants.StorageMode_ALIYUN.equals(storage.getStorageMode())){
								OSSClient oss = new OSSClient(ConfigParameter.ALIYUNACCESSID,ConfigParameter.ALIYUNACCESSKEY);
								ObjectMetadata objectMeta = new ObjectMetadata();
								//文件的附加信息不是必须
//								objectMeta.addUserMetadata("filename",storage.getFileName());
//						        objectMeta.addUserMetadata("encryptmode",String.valueOf(storage.getEncryptMode()));
//						        objectMeta.addUserMetadata("storagemode",String.valueOf(storage.getStorageMode()));
//						        objectMeta.addUserMetadata("originalsize",String.valueOf(storage.getOriginalSize()));
//						        objectMeta.addUserMetadata("originalmd5",storage.getOriginalMD5());
//						        objectMeta.addUserMetadata("storagesize",String.valueOf(storage.getStorageSize()));
//						        objectMeta.addUserMetadata("storagemd5",storage.getStorageMD5());
//						        objectMeta.addUserMetadata("storagedirectory",storage.getStorageDirectory());
//						        objectMeta.addUserMetadata("storagename",storage.getStorageName());
						        //内容大小
						        objectMeta.setContentLength(storage.getStorageSize());
						        //上传文件至阿里云
						        oss.putObject(ConfigParameter.ALIYUNBUCKETNAME,storage.getStorageName(), 
										new FileInputStream(fullStoragePath+storage.getStorageName()), objectMeta);
								//删除本地存储的文件释放空间,若不删除本地文件下载时会优先下载本地文件
//								FileUtils.deleteFile(fullStoragePath+storage.getStoragePath());
								//更新为阿里云的存储空间
								storage.setStorageSpace(ConfigParameter.ALIYUNBUCKETNAME);
							}
						}
						record.setStorageTime(TimeUtils.getSysTimeLong());
						record.setStorage(storage);
						return record;
					}
				}else{
					//文件校验码不正确
					throw new AppRuntimeException(IFile._10301017);
				}
			} catch (Exception e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
			}
			//文件存储时出现异常
			throw new AppRuntimeException(IFile._10301010);
		}else{
			//请求数据大小与传输文件大小不一致
			throw new AppRuntimeException(IFile._10301018);
		}
	}
	/**
	 * 文件存储
	 * @param oldFilePath
	 * 被处理的文件路径
	 * @param directory
	 * 处理后保存的文件目录
	 * @param fileName
	 * 处理后保存的文件名称
	 */
	private static String[] storage(String oldFilePath,String directory,String fileName,Integer transportMode){
		if(FileUtils.mkdirs(directory)){
			int len = -1;
			long fileLength = 0L;
			byte[] buffer = new byte[Variable.BUFFER];
			CipherInputStream cipherInputStream = null;
			BufferedInputStream bufferedInputStream=null;
			BufferedOutputStream bufferedOutputStream = null;
			try {
				bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(directory + fileName)));
				if(FieldConstants.TransportMode_NO.equals(transportMode)){
					bufferedInputStream =new BufferedInputStream(new FileInputStream(oldFilePath));
				}else if(FieldConstants.TransportMode_GZIP.equals(transportMode)){
					bufferedInputStream =new BufferedInputStream(new GZIPInputStream(new FileInputStream(oldFilePath)));
				}
				MessageDigest md = MessageDigest.getInstance(MD5.ALGORITHM);
				if(ConfigParameter.DATA_ENCRYPT_MODE.equals(FieldConstants.EncryptMode_NO)){
					while ((len= bufferedInputStream.read(buffer)) != -1) {
						md.update(buffer, 0, len);
						bufferedOutputStream.write(buffer, 0, len);
						fileLength = fileLength + len;
					}
				}else if(ConfigParameter.DATA_ENCRYPT_MODE.equals(FieldConstants.EncryptMode_DES)){
					Cipher cipher = Cipher.getInstance(DES.ALGORITHM);
					cipher.init(Cipher.ENCRYPT_MODE, DES.getKey(SystemPath.DESKEYKEY));
					cipherInputStream = new CipherInputStream(bufferedInputStream, cipher);
					while ((len= cipherInputStream.read(buffer)) != -1) {
						md.update(buffer, 0, len);
						bufferedOutputStream.write(buffer, 0, len);
						fileLength = fileLength + len;
					}
				}
				buffer=null;
				return new String[] {MD5.byte2hex(md.digest()), String.valueOf(fileLength)};
			} catch (Exception e) {
				LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
				return null;
			} finally {
				if(bufferedOutputStream!=null){
					try {
						bufferedOutputStream.flush();
					} catch (IOException e) {
						LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
						return null;
					}finally{
						try {
							bufferedOutputStream.close();
						} catch (IOException e) {
							LogUtils.logError(StackTraceInfo.getTraceInfo() + e.getMessage());
							return null;
						}finally{
							bufferedOutputStream=null;
						}
					}
				}
				if(bufferedInputStream!=null){
					try {
						bufferedInputStream.close();
					} catch (IOException e) {
						LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
						return null;
					}finally{
						bufferedInputStream=null;
						//删除临时文件释放空间
						if(!FileUtils.deleteFile(oldFilePath)){
							LogUtils.printError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim("not delete tempfile:"+oldFilePath));
						}
					}
				}
				if(cipherInputStream!=null){
					try {
						cipherInputStream.close();
					} catch (IOException e) {
						LogUtils.logError(StackTraceInfo.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
						return null;
					}finally{
						cipherInputStream=null;
					}
				}
			}
		}
		return null;
	}
	
}
