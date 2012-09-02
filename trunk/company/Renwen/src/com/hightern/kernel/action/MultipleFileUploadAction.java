package com.hightern.kernel.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.hightern.kernel.model.BaseModel;

/**
 * 文档上传基础控制器
 * 
 * @author Stone
 * @param <T>
 */
public class MultipleFileUploadAction<T extends BaseModel> extends
		BaseAction<T> {

	/**
	 * 批量文件上传与下载
	 */
	private static final long serialVersionUID = -6162221695106932732L;
	public static final String DOWNLOAD = "download";
	private File[] uploads; // 文件实体
	private String[] uploadsContentType; // 文件类型
	private String[] uploadsFileName; // 文件名称
	private String[] captions; // 文件描述
	private String contentType; // 文件类型
	private InputStream downloadFile; // 文件下载的流
	private File upload;
	private String uploadFileName;
	private String uploadContentType;

	/**
	 * 将上传的文件流写入到服务器
	 * 
	 * @param src
	 * @param dst
	 */
	protected static void copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src));
				out = new BufferedOutputStream(new FileOutputStream(dst));
				final byte[] buffer = new byte[512 * 1024];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public String[] getUploadsContentType() {
		return uploadsContentType;
	}


	public void setUploadsContentType(String[] uploadsContentType) {
		this.uploadsContentType = uploadsContentType;
	}

	public String[] getUploadsFileName() {
		return uploadsFileName;
	}

	public void setUploadsFileName(String[] uploadsFileName) {
		this.uploadsFileName = uploadsFileName;
	}

	public String getExtention(String fileName) {
		final int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	public File[] getUploads() {
		return uploads;
	}


	public void setUploads(File[] uploads) {
		this.uploads = uploads;
	}

	public String[] getCaptions() {
		return captions;
	}

	public void setCaptions(String[] captions) {
		this.captions = captions;
	}

	public InputStream getDownloadFile() {
		return downloadFile;
	}

	public void setDownloadFile(InputStream downloadFile) {
		this.downloadFile = downloadFile;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
}
