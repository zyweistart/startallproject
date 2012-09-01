package org.platform.wwwroot.entity;

import java.util.List;

import org.zyweistartframework.context.annotation.Entity;
import org.framework.entity.Root;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.OneToMany;
import org.zyweistartframework.persistence.annotation.Table;

/**
 * 页模板
 * @author Start
 */
@Entity("template")
@Table("WWW_TEMPLATE")
public class Template extends Root {

	private static final long serialVersionUID = 1L;
	
	public Template(){}
	/**
	 * 模板名称
	 */
	@Column(length=50,nullable=false)
	private String name;
	/**
	 * 模板路径
	 */
	@Column(length=100,nullable=false,unique=true)
	private String path;
	
	@OneToMany
	private List<Information> informations;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<Information> getInformations() {
		return informations;
	}

	public void setInformations(List<Information> informations) {
		this.informations = informations;
	}
	
}