package org.platform.manager.entity;

import java.util.List;

import org.framework.entity.Root;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.JoinTable;
import org.zyweistartframework.persistence.annotation.ManyToMany;
import org.zyweistartframework.persistence.annotation.Table;
/**
 * 权限值
 * @author Start
 */
@Entity("permission")
@Table("MAN_PERMISSION")
public class Permission extends Root {

	private static final long serialVersionUID = 1L;
	
	public Permission(){}
	/**
	 * 权限名称
	 */
	@Column(length=32,nullable=false)
	private String name;
	/**
	 * Action路径
	 */
	@Column(length=100,nullable=false,unique=true)
	private String action;
	/**
	 * 权限值
	 */
	@Column(nullable=false,unique=true)
	private Integer value;
	
	@ManyToMany
	@JoinTable("MAN_ROLE_PERMISSION")
	private List<Role> roles;
	
	@ManyToMany
	@JoinTable("MAN_MANAGER_PERMISSION")
	private List<Manager> managers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Manager> getManagers() {
		return managers;
	}

	public void setManagers(List<Manager> managers) {
		this.managers = managers;
	}

}