package com.gary.framework.entity;

import java.io.Serializable;

/**
 * 菜单
 * @author gary
 *
 */
public class SysMenu implements Serializable {

	private static final long serialVersionUID = -5843201624209468998L;

	private Integer id;
	/**
	 * 菜单名称
	 */
	private String menuName;
	/**
	 * 菜单网址
	 */
	private String menuUrl;
	/**
	 * 菜单排序
	 */
	private Integer menuOrder;
	/**
	 * 上级菜单ID
	 */
	private Integer menuPid;
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
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public Integer getMenuOrder() {
		return menuOrder;
	}
	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}
	public Integer getMenuPid() {
		return menuPid;
	}
	public void setMenuPid(Integer menuPid) {
		this.menuPid = menuPid;
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
		builder.append("SysMenu [id=");
		builder.append(id);
		builder.append(", menuName=");
		builder.append(menuName);
		builder.append(", menuUrl=");
		builder.append(menuUrl);
		builder.append(", menuOrder=");
		builder.append(menuOrder);
		builder.append(", menuPid=");
		builder.append(menuPid);
		builder.append(", enable=");
		builder.append(enable);
		builder.append("]");
		return builder.toString();
	}
	
}
