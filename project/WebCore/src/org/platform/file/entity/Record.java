package org.platform.file.entity;

import org.framework.entity.Root;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.ManyToOne;
import org.zyweistartframework.persistence.annotation.Table;
import org.zyweistartframework.persistence.annotation.Temporal;
import org.zyweistartframework.persistence.annotation.dt.CascadeType;
import org.zyweistartframework.persistence.annotation.dt.FetchType;
/**
 * 文件记录
 * @author Start
 */
@Entity("record")
@Table("FIL_RECORD")
public class Record extends Root {

	private static final long serialVersionUID = 1L;
	
	public Record(){}
	/**
	 * 存储时间
	 */
	@Temporal(nullable=false)
	private String storageTime;
	/**
	 * 上传开始时间毫秒
	 */
	@Column(nullable=false)
	private Long startUploadTime;
	/**
	 * 上传结束时间毫秒
	 */
	@Column(nullable=false)
	private Long endUploadTime;
	/**
	 * 访问权限
	 */
	@Column(nullable=false)
	private Integer accessRights;
	/**
	 * 最后修改时间
	 */
	@Temporal
	private String lastModifyTime;
	/**
	 * 备注信息
	 */
	@Column(length=200)
	private String remarks;
	/**
	 * 文件存储,立即加载状态
	 */
	@ManyToOne(cascade={CascadeType.REFRESH,CascadeType.PERSIST},fetch=FetchType.EAGER)
	private Storage storage;

	public String getStorageTime() {
		return storageTime;
	}

	public void setStorageTime(String storageTime) {
		this.storageTime = storageTime;
	}

	public Long getStartUploadTime() {
		return startUploadTime;
	}

	public void setStartUploadTime(Long startUploadTime) {
		this.startUploadTime = startUploadTime;
	}

	public Long getEndUploadTime() {
		return endUploadTime;
	}

	public void setEndUploadTime(Long endUploadTime) {
		this.endUploadTime = endUploadTime;
	}

	public Integer getAccessRights() {
		return accessRights;
	}

	public void setAccessRights(Integer accessRights) {
		this.accessRights = accessRights;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

}