package org.platform.manager.entity;

import java.util.List;

import org.framework.entity.Root;
import org.zyweistartframework.context.annotation.Entity;
import org.zyweistartframework.persistence.annotation.Column;
import org.zyweistartframework.persistence.annotation.ManyToMany;
import org.zyweistartframework.persistence.annotation.OneToMany;
import org.zyweistartframework.persistence.annotation.Table;
/**
 * 管理员
 * @author Start
 */
@Entity("manager")
@Table("MAN_MANAGER")
public class Manager extends Root {

	private static final long serialVersionUID = 1L;
	
	public Manager(){}
	/**
	 * 真实姓名
	 */
	@Column(length=32,nullable=false)
	private String name;
	/**
	 * 用户名
	 */
	@Column(length=32,nullable=false,unique=true)
	private String username;
	/**
	 * 密码(32位MD5值DES加密)
	 */
	@Column(length=128,nullable=false)
	private String password;
	/**
	 * 授权值(角色与权限的总值)
	 */
	@Column(nullable=false)
	private Integer acl;
	
	@ManyToMany
	private List<Role> roles;
	
	@ManyToMany
	private List<Permission> permissions;
	
	@OneToMany
	private List<ManagerLoginLog> managerLoginLogs;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAcl() {
		return acl;
	}

	public void setAcl(Integer acl) {
		this.acl = acl;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public List<ManagerLoginLog> getManagerLoginLogs() {
		return managerLoginLogs;
	}

	public void setManagerLoginLogs(List<ManagerLoginLog> managerLoginLogs) {
		this.managerLoginLogs = managerLoginLogs;
	}

}