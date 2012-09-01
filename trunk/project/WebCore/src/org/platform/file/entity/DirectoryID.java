package org.platform.file.entity;

import org.zyweistartframework.context.annotation.Entity;
import org.framework.entity.Root;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.Table;
/**
 * 目录ID表
 * 每个级别表示当前与历史总创建的总目录数
 * @author Start
 */
@Entity("directoryID")
@Table("FIL_DIRECTORYID")
public class DirectoryID extends Root {

	private static final long serialVersionUID = 1L;

	public DirectoryID(){}
	/**
	 * 当前目录层级的数量
	 */
	@Column(nullable=false)
	private Integer currentLevel=0;
	/**
	 * 当前级别中子目录的数量(包括已经创建并被删除的)
	 */
	@Column(nullable=false)
	private Integer childDirectoryNum=-1;

	public Integer getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(Integer currentLevel) {
		this.currentLevel = currentLevel;
	}

	public Integer getChildDirectoryNum() {
		return childDirectoryNum;
	}

	public void setChildDirectoryNum(Integer childDirectoryNum) {
		this.childDirectoryNum = childDirectoryNum;
	}

}