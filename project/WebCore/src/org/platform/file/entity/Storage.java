package org.platform.file.entity;

import java.util.List;

import org.framework.entity.Root;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.OneToMany;
import org.zyweistartframework.persistence.annotation.Table;
import org.zyweistartframework.persistence.annotation.Transient;
/**
 * 文件存储
 * @author Start
 */
@Entity("storage")
@Table("FIL_STORAGE")
public class Storage extends Root {

	private static final long serialVersionUID = 1L;
	
	public Storage(){}
	/**
	 * 传输文件压缩方式(NO;GZIP)
	 */
	@Transient
	private Integer transportMode;
	/**
	 * 传输文件大小(Byte)
	 */
	@Transient
	private Integer transportSize;
	/**
	 * 传输文件MD5校验码
	 */
	@Transient
	private String transportMD5;
	/**
	 * 加密模式(NO;DES)
	 */
	@Column(nullable=false)
	private Integer encryptMode;
	/**
	 * 存储模式(LOCAL;ALIYUN)
	 */
	@Column(nullable=false)
	private Integer storageMode;
	/**
	 * 原始文件大小(Byte)
	 */
	@Column(nullable=false)
	private Integer originalSize;
	/**
	 * 原始文件MD5校验码
	 */
	@Column(length=32,nullable=false)
	private String originalMD5;
	/**
	 * 存储文件大小(Byte)
	 * 未加密则跟原始文件一样
	 */
	@Column(nullable=false)
	private Integer storageSize;
	/**
	 * 存储文件MD5校验码
	 * 未加密则跟原始文件一样
	 */
	@Column(length=32,nullable=false)
	private String storageMD5;
	/**
	 * 存储空间
	 */
	@Column(length=32,nullable=false)
	private String storageSpace;
	/**
	 * 存储目录
	 */
	@Column(length=32,nullable=false)
	private String storageDirectory;
	/**
	 * 目录:存储空间/存储目录/随机MD5形式的名称"
	 * 存储名称
	 */
	@Column(length=32,nullable=false)
	private String  storageName;
	/**
	 * 文件名称
	 * 符合正常文件命名规则
	 */
	@Column(length=128,nullable=false)
	private String fileName;
	/**
	 * 文件扩展名(最后一个点符后面的字符)
	 */
	@Column(length=16)
	private String extension;
	/**
	 * 内容类型
	 */
	@Column(length=50)
	private String contentType;
	
	@OneToMany
	private List<Record> records;

	public Integer getTransportMode() {
		return transportMode;
	}

	public void setTransportMode(Integer transportMode) {
		this.transportMode = transportMode;
	}

	public Integer getTransportSize() {
		return transportSize;
	}

	public void setTransportSize(Integer transportSize) {
		this.transportSize = transportSize;
	}

	public String getTransportMD5() {
		return transportMD5;
	}

	public void setTransportMD5(String transportMD5) {
		this.transportMD5 = transportMD5;
	}

	public Integer getEncryptMode() {
		return encryptMode;
	}

	public void setEncryptMode(Integer encryptMode) {
		this.encryptMode = encryptMode;
	}

	public Integer getStorageMode() {
		return storageMode;
	}

	public void setStorageMode(Integer storageMode) {
		this.storageMode = storageMode;
	}

	public Integer getOriginalSize() {
		return originalSize;
	}

	public void setOriginalSize(Integer originalSize) {
		this.originalSize = originalSize;
	}

	public String getOriginalMD5() {
		return originalMD5;
	}

	public void setOriginalMD5(String originalMD5) {
		this.originalMD5 = originalMD5;
	}

	public Integer getStorageSize() {
		return storageSize;
	}

	public void setStorageSize(Integer storageSize) {
		this.storageSize = storageSize;
	}

	public String getStorageMD5() {
		return storageMD5;
	}

	public void setStorageMD5(String storageMD5) {
		this.storageMD5 = storageMD5;
	}

	public String getStorageSpace() {
		return storageSpace;
	}

	public void setStorageSpace(String storageSpace) {
		this.storageSpace = storageSpace;
	}

	public String getStorageDirectory() {
		return storageDirectory;
	}

	public void setStorageDirectory(String storageDirectory) {
		this.storageDirectory = storageDirectory;
	}

	public String getStorageName() {
		return storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}
	
}