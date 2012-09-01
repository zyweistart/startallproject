package org.platform.file.entity;

import org.framework.entity.Root;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.Table;
import org.zyweistartframework.persistence.annotation.Temporal;
/**
 * 目录
 * @author Start
 */
@Entity("directory")
@Table("FIL_DIRECTORY")
public class Directory extends Root {

	private static final long serialVersionUID = 1L;
	
	public Directory(){}
	/**
	 * 目录名称
	 */
	@Column(length=50,nullable=false)
	private String name;
	/**
	 * 目录级别:主目录用1，二级用2.......依次类推
	 */
	@Column(nullable=false)
	private Integer currentLevel;
	/**
	 * 父级目录的编号
	 */
	@Column(length=6,nullable=false)
	private String parentIDNo;
	/**
	 * 最多支持5级目录
	 */
	@Column(length=30)
	private String directoryIDNo;
	/**
	 * 创建时间
	 */
	@Temporal(nullable=false)
	private String createTime;
	/**
	 * 备注
	 */
	@Column(length=200)
	private String remark;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getCurrentLevel() {
		return currentLevel;
	}
	
	public void setCurrentLevel(Integer currentLevel) {
		this.currentLevel = currentLevel;
	}
	
	public String getParentIDNo() {
		return parentIDNo;
	}
	
	public void setParentIDNo(String parentIDNo) {
		this.parentIDNo = parentIDNo;
	}
	
	public String getDirectoryIDNo() {
		return directoryIDNo;
	}
	
	public void setDirectoryIDNo(String directoryIDNo) {
		this.directoryIDNo = directoryIDNo;
	}
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}