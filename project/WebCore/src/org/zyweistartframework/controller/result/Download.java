package org.zyweistartframework.controller.result;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zyweistartframework.controller.IActionResult;
import org.zyweistartframework.utils.StackTraceInfo;


/**
 * 文件下载
 */
public final class Download  implements IActionResult{
	
	private final static Log log=LogFactory.getLog(Download.class);
	
	private String filePath;
	
	private String fileName;
	
	private String contentType;
	
	public Download(String filePath,String fileName,String encoding,String contentType){
		this.filePath=filePath;
		this.fileName=fileName;
		this.contentType=contentType;
	}
	
	@Override
	public void doExecute(ActionResultInvocation invocation) {
		HttpServletResponse response=invocation.getResponse();
		File tmpFile=new File(getFilePath());
		if(tmpFile.exists()){
			FileInputStream fileInputStream=null;
			BufferedOutputStream bufferedOutputStream=null;
			try {
				response.setHeader("Content-type:",getContentType());
				response.setHeader("Accept-Ranges:", "bytes");
				response.setHeader("Accept-Length:", Long.toString(tmpFile.length()));
				response.setHeader("Content-Disposition", "attachment; filename=" + new String(getFileName().getBytes(), "ISO8859-1"));
				fileInputStream = new FileInputStream(tmpFile);
				bufferedOutputStream=new BufferedOutputStream(response.getOutputStream());
				int i = -1;
				byte[] buffer = new byte[1024*4];
				while((i = fileInputStream.read(buffer))!=-1){
					bufferedOutputStream.write(buffer,0,i);
				}
				buffer=null;
			} catch (IOException e) {
				log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
			}finally {
				if(bufferedOutputStream!=null){
					try {
						bufferedOutputStream.flush();
					} catch (IOException e) {
						log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
					}finally{
						try {
							bufferedOutputStream.close();
						} catch (IOException e) {
							log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
						}finally{
							bufferedOutputStream=null;
						}
					}
				}
				if(fileInputStream!=null){
					try {
						fileInputStream.close();
					} catch (IOException e) {
						log.error(StackTraceInfo.getTraceInfo() + e.getMessage());
					}finally{
						fileInputStream=null;
					}
				}
			}
		}
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}