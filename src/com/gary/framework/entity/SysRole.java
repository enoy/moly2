package com.gary.framework.entity;

import java.io.Serializable;

/**
 * 角色
 * @author gary
 * 
 */
public class SysRole implements Serializable {
	
	private static final long serialVersionUID = 1352851955278607260L;

	private Integer id;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 角色说明
	 */
	private String roleDesc;
	/**
	 * 有效
	 */
	private Integer enable;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SysRole [id=");
		builder.append(id);
		builder.append(", roleName=");
		builder.append(roleName);
		builder.append(", roleDesc=");
		builder.append(roleDesc);
		builder.append(", enable=");
		builder.append(enable);
		builder.append("]");
		return builder.toString();
	}
	
}
