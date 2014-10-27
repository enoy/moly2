package com.gary.framework.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 * @author gary
 *
 */
public class Menu implements Serializable {

	private static final long serialVersionUID = 6917303952288405172L;

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
	 * 子菜单
	 */
	private List<Menu> subMenus;

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

	public List<Menu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<Menu> subMenus) {
		this.subMenus = subMenus;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Menu [id=");
		builder.append(id);
		builder.append(", menuName=");
		builder.append(menuName);
		builder.append(", menuUrl=");
		builder.append(menuUrl);
		builder.append(", menuOrder=");
		builder.append(menuOrder);
		builder.append(", menuPid=");
		builder.append(menuPid);
		builder.append("]");
		return builder.toString();
	}
	
}
