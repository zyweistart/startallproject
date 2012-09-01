package org.platform.wwwroot.entity;

import java.util.List;

import org.framework.entity.Root;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.OneToMany;
import org.zyweistartframework.persistence.annotation.Table;

@Entity("category")
@Table("WWW_CATEGORY")
public class Category extends Root {

	private static final long serialVersionUID = 1L;
	
	public Category(){}
	/**
	 * 名称
	 */
	@Column(length=50,nullable=false,unique=true)
	private String name;
	/**
	 * 页类型:
	 * 		1:父级栏目(有子栏目)
	 * 		2:资讯列表(无子栏目)
	 * 		3:图片列表(无子栏目)
	 * 		4:单级页面(无子栏目)
	 */
	@Column(nullable=false)
	private Integer pageType;
	/**
	 * 父级
	 */
	@Column(length=32)
	private String parent;
	
	@OneToMany
	private List<Information> informations;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPageType() {
		return pageType;
	}

	public void setPageType(Integer pageType) {
		this.pageType = pageType;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<Information> getInformations() {
		return informations;
	}

	public void setInformations(List<Information> informations) {
		this.informations = informations;
	}
	
}