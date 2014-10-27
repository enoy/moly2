package com.gary.framework.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.gary.framework.entity.SysMenu;

/**
 * 菜单Service
 * @author gary
 *
 */
@Service
public class SysMenuService extends BaseService {

	/**
	 * 保存菜单
	 * @param sysMenu
	 * @return
	 */
	public Integer save(SysMenu sysMenu) {
		setMenuOrder(sysMenu);
		//有效
		sysMenu.setEnable(Integer.valueOf(1));
		return getDaoManager().getSysMenuDAO().save(sysMenu);
	}

	/**
	 * 删除菜单及子菜单
	 * @param id
	 */
	public void deleteById(Integer id) {
		//删除目标
		executeDeleteById(id);
		//删除下级
		deleteSub(id);
	}
	
	/**
	 * 执行删除
	 * @param id
	 */
	private void executeDeleteById(Integer id){
		getDaoManager().getSysMenuDAO().deleteById(id);
	}
	
	/**
	 * 删除子菜单
	 * @param id
	 */
	private void deleteSub(Integer id){
		List<Object[]> list = getDaoManager().getSysMenuDAO().getMenuIdsByPId(id);
		for (Object[] data : list) {
			Integer typeId = (Integer)data[0];
			executeDeleteById(typeId);
			//继续递归,删除下级
			deleteSub(typeId);
		}
	}
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	public SysMenu getById(Integer id) {
		return getDaoManager().getSysMenuDAO().getById(id);
	}
	
	/**
	 * 设置排序字段
	 * @param sysMenu
	 */
	private void setMenuOrder(SysMenu sysMenu){
		//排序
		if(sysMenu.getMenuOrder() == null){
			Integer newMenuOrder = null;
			
			//先根据上级ID(MENU_PID)查询出该父级别下最大的排序序号
			Integer maxMenuOrder = getDaoManager().getSysMenuDAO().getMaxOrder(sysMenu.getMenuPid());
			if(maxMenuOrder == null){
				newMenuOrder = Integer.valueOf(1);
			}else{
				newMenuOrder = maxMenuOrder.intValue() + 1;
			}
			
			sysMenu.setMenuOrder(newMenuOrder);
		}
	}

	/**
	 * 更新
	 * @param sysMenu
	 */
	public void update(SysMenu sysMenu) {
		setMenuOrder(sysMenu);
		//有效
		sysMenu.setEnable(Integer.valueOf(1));
		
		getDaoManager().getSysMenuDAO().update(sysMenu);
	}

	/**
	 * 获取树菜单
	 * @return
	 */
	public List<Map<String, Object>> getTreeList(String roleId) {
		return getDaoManager().getSysMenuDAO().getTreeList(roleId);
	}

	/**
	 * 移动
	 * @param sourceIds
	 * @param targetId
	 * @param moveType
	 */
	public void move(String sourceIds, Integer targetId, String moveType) {
		getDaoManager().getSysMenuDAO().move(sourceIds, targetId, moveType);
	}

	/**
	 * 保存用户权限
	 * @param roleId
	 * @param menuIds
	 */
	public void saveRoleMenus(String roleId, String menuIds) {
		getDaoManager().getSysMenuDAO().removeAllMenusFromRole(roleId);
		if(StringUtils.isNotBlank(menuIds)){
			String[] menuIdArray = StringUtils.split(menuIds, ',');
			getDaoManager().getSysMenuDAO().saveRoleMenu(roleId, menuIdArray);
		}
	}

}
