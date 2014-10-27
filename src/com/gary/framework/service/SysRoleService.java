package com.gary.framework.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.gary.framework.dto.Menu;
import com.gary.framework.entity.SysMenu;
import com.gary.framework.entity.SysRole;
import com.gary.framework.util.Paging;

/**
 * 角色Service
 * @author gary
 *
 */
@Service
public class SysRoleService extends BaseService {

	/**
	 * 根据角色获取
	 * @param roleName
	 * @param exceptId
	 * @return
	 */
	public SysRole getRoleByRoleName(String roleName, String exceptId) {
		return getDaoManager().getSysRoleDAO().getRoleByRoleName(roleName, exceptId);
	}
	
	/**
	 * 保存
	 * @param sysRole
	 */
	public void save(SysRole sysRole) {
		getDaoManager().getSysRoleDAO().save(sysRole);
	}
	
	/**
	 * 禁用
	 * @param id
	 */
	public void deleteById(Integer id) {
		//禁用角色,暂不删除用户关联表中数据
		getDaoManager().getSysRoleDAO().deleteById(id);
	}

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	public SysRole getById(Integer id) {
		return getDaoManager().getSysRoleDAO().getById(id);
	}
	
	/**
	 * 更新
	 * @param sysRole
	 */
	public void update(SysRole sysRole) {
		getDaoManager().getSysRoleDAO().update(sysRole);
	}
	
	/**
	 * 分页搜索
	 * @param roleName
	 * @param roleDesc
	 * @return
	 */
	public Paging getPaging(String roleName, String roleDesc) {
		return getDaoManager().getSysRoleDAO().getPaging(roleName, roleDesc, getPageNum(), getPageSize());
	}
	
	/**
	 * 批量保存用户角色
	 * @param roleId
	 * @param userIds
	 */
	public void saveUserRole(String roleId, String[] userIds){
		getDaoManager().getSysRoleDAO().saveUserRole(roleId, userIds);
	}
	
	/**
	 * 将用户从角色中删除
	 * @param userRoleId
	 */
	public void removeUserFromRole(String userRoleId){
		getDaoManager().getSysRoleDAO().removeUserFromRole(userRoleId);
	}

	/**
	 * 角色中的用户列表
	 * @param roleId
	 * @param nickName
	 * @return
	 */
	public Paging getUserList(String roleId, String nickName) {
		return getDaoManager().getSysRoleDAO().getUserList(roleId, nickName, 
				getPageNum(), getPageSize());
	}

	/**
	 * 不在角色中的用户列表
	 * @param roleId
	 * @param nickName
	 * @return
	 */
	public Paging availableUserList(String roleId, String nickName) {
		return getDaoManager().getSysRoleDAO().availableUserList(roleId, nickName, 
				getPageNum(), getPageSize());
	}

	/**
	 * 删除角色下所有用户
	 * @param roleId
	 */
	public void removeAllUsersFromRole(String roleId) {
		getDaoManager().getSysRoleDAO().removeAllUsersFromRole(roleId);
	}
	
	/**
	 * 获取用户菜单
	 * @param userId
	 * @return
	 */
	public List<Menu> getUserMenus(Integer userId){
		List<Menu> menuList = new ArrayList<Menu>();

		List<SysMenu> list = getDaoManager().getSysRoleDAO().getUserMenus(userId);
		
		for (int i = 0; i < list.size(); i++) {
			//找出所有第一层的菜单
			SysMenu sysMenu = list.get(i);
			if(sysMenu.getMenuPid().intValue() == 0){
				Menu menu = new Menu();
				try {
					BeanUtils.copyProperties(menu, sysMenu);
					menuList.add(menu);
				} catch (Exception e) {
					log.error("copyProperties faild", e);
				}
			}
		}
		
		for (int i = 0; i < menuList.size(); i++) {
			//添加子菜单
			Menu menu = menuList.get(i);
			for (SysMenu sysMenu : list) {
				if(sysMenu.getMenuPid().compareTo(menu.getId()) == 0){
					List<Menu> subMenus = menu.getSubMenus();
					if(subMenus == null){
						subMenus = new ArrayList<Menu>();
						menu.setSubMenus(subMenus);
					}
					Menu m = new Menu();
					try {
						BeanUtils.copyProperties(m, sysMenu);
					} catch (Exception e) {
						log.error("copyProperties faild", e);
					}
					subMenus.add(m);
				}
			}
		}
		
		return menuList;
	}
}
