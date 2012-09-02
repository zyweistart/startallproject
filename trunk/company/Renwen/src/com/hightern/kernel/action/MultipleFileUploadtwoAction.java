package com.hightern.kernel.action;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.hightern.kernel.model.BaseModel;

/**
 * 文档上传基础控制器
 * 
 * @author Stone
 */
public class MultipleFileUploadtwoAction extends MultipleFileUploadAction<BaseModel> {
	/**
	 * 批量文件上传与下载
	 */
	private static final long serialVersionUID = -6162221695106932732L;
	public static final String DOWNLOAD = "download";
	// 批量上传用
	protected List<File> Filedata;
	protected List<String> FiledataFileName;
	protected List<String> FiledataContentType;
	// 文件下载用
	private String fileName;// 文件名称
	private String contentType; // 文件类型
	private InputStream downloadFile; // 文件下载的流

	public String getContentType() {
		return this.contentType;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public InputStream getDownloadFile() {
		return this.downloadFile;
	}

	public void setDownloadFile(InputStream downloadFile) {
		this.downloadFile = downloadFile;
	}

	public List<File> getFiledata() {
		return this.Filedata;
	}

	public void setFiledata(List<File> filedata) {
		this.Filedata = filedata;
	}

	public List<String> getFiledataFileName() {
		return this.FiledataFileName;
	}

	public void setFiledataFileName(List<String> filedataFileName) {
		this.FiledataFileName = filedataFileName;
	}

	public List<String> getFiledataContentType() {
		return this.FiledataContentType;
	}

	public void setFiledataContentType(List<String> filedataContentType) {
		this.FiledataContentType = filedataContentType;
	}
}
