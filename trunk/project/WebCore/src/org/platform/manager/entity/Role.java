package org.platform.manager.entity;

import java.util.List;

import org.framework.entity.Root;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.JoinTable;
import org.zyweistartframework.persistence.annotation.ManyToMany;
import org.zyweistartframework.persistence.annotation.Table;

/**
 * 角色表
 * @author Start
 */
@Entity("role")
@Table("MAN_ROLE")
public class Role extends Root {

	private static final long serialVersionUID = 1L;
	
	public Role(){}
	/**
	 * 角色名
	 */
	@Column(length=32,nullable=false,unique=true)
	private String name;
	/**
	 * 授权值(权限的总值)
	 */
	@Column(nullable=false)
	private Integer acl;
	
	@ManyToMany
	@JoinTable("MAN_MANAGER_ROLE")
	private List<Manager> managers;
	
	@ManyToMany
	private List<Permission> permissions;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAcl() {
		return acl;
	}

	public void setAcl(Integer acl) {
		this.acl = acl;
	}

	public List<Manager> getManagers() {
		return managers;
	}

	public void setManagers(List<Manager> managers) {
		this.managers = managers;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
}