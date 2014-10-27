package com.gary.framework.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.gary.framework.entity.SysMenu;
import com.gary.framework.util.IntegerUtils;
import com.gary.framework.util.Paging;

/**
 * 菜单
 * @author gary
 *
 */
@Service
public class SysMenuDAO extends BaseDAO {

	/**
	 * 保存
	 * @param sysMenu
	 */
	public Integer save(SysMenu sysMenu) {
		String insertSql = "INSERT INTO SYS_MENU "
				+ "(MENU_NAME,MENU_URL,"
				+ "MENU_ORDER,MENU_PID,ENABLE) VALUES " +
				"(?,?,?,?,1)";
		Object[] params = new Object[]{sysMenu.getMenuName(),sysMenu.getMenuUrl(),
				sysMenu.getMenuOrder(),sysMenu.getMenuPid()};
		return saveAndReturnId(insertSql, params);
	}
	
	/**
	 * 禁用
	 * @param id
	 */
	public void deleteById(Integer id) {
		disableById(id, "SYS_MENU");
	}

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	public SysMenu getById(Integer id) {
		try {
			return getQueryRunner().query("SELECT ID,MENU_NAME,MENU_URL,"
					+ "MENU_ORDER,MENU_PID,ENABLE "
					+ "FROM SYS_MENU WHERE ID=?", 
					new BeanHandler<SysMenu>(SysMenu.class), 
					id);
		} catch (SQLException e) {
			log.error("getById faild,id:" + id, e);
			return null;
		}
	}
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	public SysMenu getParentById(Integer id) {
		try {
			return getQueryRunner().query("SELECT ID,MENU_NAME,MENU_URL,"
					+ "MENU_ORDER,MENU_PID,ENABLE "
					+ "FROM SYS_MENU WHERE ID=(SELECT MENU_PID FROM SYS_MENU WHERE ID=?)", 
					new BeanHandler<SysMenu>(SysMenu.class), 
					id);
		} catch (SQLException e) {
			log.error("getById faild,id:" + id, e);
			return null;
		}
	}
	
	/**
	 * 更新
	 * @param sysMenu
	 */
	public void update(SysMenu sysMenu) {
		try {
			getQueryRunner().update("UPDATE SYS_MENU SET MENU_NAME=?,MENU_URL=?,MENU_ORDER=?,"
					+ "MENU_PID=?,ENABLE=? "
					+ "WHERE ID=?", 
					new Object[]{sysMenu.getMenuName(),sysMenu.getMenuUrl(),sysMenu.getMenuOrder(),
							sysMenu.getMenuPid(),sysMenu.getEnable(), 
							sysMenu.getId()});
		} catch (SQLException e) {
			log.error("update sysMenu faild,sysMenu:" + sysMenu, e);
		}
	}
	
	/**
	 * 分页搜索
	 * @param roleName
	 * @param roleDesc
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Paging getPaging(String menuName, int pageNum, int pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ID,MENU_NAME,MENU_URL,"
					+ "MENU_ORDER,MENU_PID,ENABLE "
					+ "FROM SYS_MENU WHERE ENABLE=1");
		if(StringUtils.isNotBlank(menuName)){
			sb.append(" AND MENU_NAME LIKE '%").append(menuName).append("%'");
		}
		sb.append(" ORDER BY ID");
		
		return getPaging(sb.toString(), new BeanListHandler<SysMenu>(SysMenu.class), 
				pageNum, pageSize);
	}
	
	/**
	 * 批量保存角色菜单
	 * @param roleId
	 * @param menuIds
	 */
	public int[] saveRoleMenu(String roleId, String[] menuIds){
		if(menuIds != null){
			Object[][] param = new Object[menuIds.length][];
			for (int i = 0; i < param.length; i++) {
				param[i] = new Object[]{roleId, menuIds[i]};
			}
			try {
				return getQueryRunner().batch("INSERT INTO SYS_ROLE_MENU (ROLE_ID,MENU_ID) VALUES (?,?)", param);
			} catch (SQLException e) {
				log.error("saveUserRole faild,roleId:" + roleId + 
						",menuIds:" + Arrays.toString(menuIds), e);
			}
		}
		return null;
	}
	
	/**
	 * 删除角色下所有菜单
	 * @param roleId
	 */
	public void removeAllMenusFromRole(String roleId){
		try {
			getQueryRunner().update("DELETE FROM SYS_ROLE_MENU WHERE ROLE_ID=" + roleId);
		} catch (SQLException e) {
			log.error("removeAllMenusFromRole faild, roleId:" + roleId, e);
		}
	}
	
	/**
	 * 将菜单从角色中删除
	 * @param userRoleId
	 */
	public void removeMenuFromRole(String roleMenuId){
		try {
			getQueryRunner().update("DELETE FROM SYS_ROLE_MENU WHERE ID=" + roleMenuId);
		} catch (SQLException e) {
			log.error("removeMenuFromRole faild, roleMenuId:" + roleMenuId, e);
		}
	}

	/**
	 * 根据上级ID(MENU_PID)查询出该父级别下最大的排序序号
	 * @param menuPid
	 * @return
	 */
	public Integer getMaxOrder(Integer menuPid) {
		try {
			return getQueryRunner().query("SELECT MAX(MENU_ORDER) FROM SYS_MENU WHERE MENU_PID=? AND ENABLE=1", 
					new ScalarHandler<Integer>(),menuPid);
		} catch (SQLException e) {
			log.error("getMaxOrder faild, menuPid:" + menuPid, e);
			return null;
		}
	}

	/**
	 * 根据上级菜单ID获取子菜单ID列表
	 * @param pid
	 * @return
	 */
	public List<Object[]> getMenuIdsByPId(Integer pid) {
		try {
			return getQueryRunner().query("SELECT ID FROM SYS_MENU WHERE MENU_PID=? AND ENABLE=1", new ArrayListHandler(), pid);
		} catch (SQLException e) {
			log.error("getMenuIdsByPId faild, id:" + pid, e);
			return new ArrayList<Object[]>();
		}
	}

	/**
	 * 获取菜单树
	 * @param allowChecked
	 * @return
	 */
	public List<Map<String, Object>> getTreeList(String roleId) {
		MapListHandler mlh = new MapListHandler();
		try {
			if(StringUtils.isNotBlank(roleId)){
				return getQueryRunner().query("SELECT 0 AS id,'菜单' AS name,'' AS MENU_URL,0 AS MENU_ORDER,-1 AS pId,1 AS ENABLE,'false' as checked "
						+ " union "
						+ "SELECT M.ID AS id,M.MENU_NAME AS name,M.MENU_URL,M.MENU_ORDER,M.MENU_PID as pId,M.ENABLE,"
						+ "IF(T.MENU_ID,'true','false') as checked "
						+ "FROM SYS_MENU M LEFT JOIN (SELECT MENU_ID FROM SYS_ROLE_MENU WHERE ROLE_ID=?) T ON M.ID=T.MENU_ID "
						+ "WHERE M.ENABLE=1 "
						+ "ORDER BY pId,MENU_ORDER", 
						mlh,
						IntegerUtils.str2int(roleId));
			}else{
				return getQueryRunner().query("SELECT 0 AS id,'菜单' AS name,'' AS MENU_URL,0 AS MENU_ORDER,-1 AS pId,1 AS ENABLE "
						+ " union "
						+ "SELECT M.ID AS id,M.MENU_NAME AS name,M.MENU_URL,M.MENU_ORDER,M.MENU_PID as pId,M.ENABLE "
						+ "FROM SYS_MENU M WHERE ENABLE=1 "
						+ "ORDER BY pId,MENU_ORDER",
						mlh);
			}
		} catch (SQLException e) {
			log.error("getTreeList faild", e);
			return new ArrayList<Map<String,Object>>();
		}
	}
	
	/**
	 * 更新本菜单下的菜单排序字段
	 * @param menuId
	 */
	public void updateSubMenuSort(Integer menuId){
		String sql = "UPDATE SYS_MENU M SET MENU_ORDER = (SELECT NEW_ORDER FROM "
				+ "  (SELECT @RN:=@RN+1 NEW_ORDER,ID FROM SYS_MENU,"
				+ "  (SELECT @RN := 0 FROM DUAL) R "
				+ "  WHERE MENU_PID=? AND ENABLE=1 ORDER BY MENU_ORDER) T "
				+ "WHERE T.ID=M.ID) WHERE MENU_PID=? AND ENABLE=1";
		try {
			getQueryRunner().update(sql, new Object[]{menuId, menuId});
		} catch (SQLException e) {
			log.error("updateSubMenuSort faild,menuId:" + menuId, e);
		}
	}

	/**
	 * 移动
	 * @param sourceIds
	 * @param targetId
	 * @param moveType
	 */
	public void move(String sourceIds, Integer targetId, String moveType) {
		//moveType:prev,inner,next
		//因数据量较小,直接采用查询后处理数据,再批量更新方式
		//为防止跨上级菜单移动,始终更新上级菜单ID

		//新顺序的ID列表
		List<Integer> newIdList = new ArrayList<Integer>();

		//移动的菜单数组
		String[] sourceIdArr = StringUtils.split(sourceIds, ',');
		
		//最后的批处理更新
		final String updateSql = "UPDATE SYS_MENU SET MENU_ORDER=?,MENU_PID=? WHERE ID=?";
		Object[][] params = null;
		
		if("inner".equals(moveType)){
			//移动到目标下,作为目标的子菜单,插入目标子菜单的最后
			String sql = "SELECT ID,MENU_PID,MENU_ORDER FROM SYS_MENU "
					+ "WHERE MENU_PID=" + targetId + " "
					+ "ORDER BY MENU_ORDER DESC "
					+ "LIMIT 1";
			try {
				//上级菜单ID
				Integer pid = null;
				//移动的菜单
				for (int i = 0; i < sourceIdArr.length; i++) {
					newIdList.add(IntegerUtils.str2int(sourceIdArr[i]));
				}
				
				Map<String, Object> data = getQueryRunner().query(sql, new MapHandler());
				Integer currentMaxMenuOrder = null;
				if(data != null){
					pid = (Integer) data.get("MENU_PID");
					currentMaxMenuOrder = (Integer)data.get("MENU_ORDER");
				}else{
					//目前在目标下无子菜单
					pid = targetId;
					//从1开始,后面会+1,因此当前设置成0
					currentMaxMenuOrder = Integer.valueOf(0);
				}
				params = new Object[newIdList.size()][];
				for (int i = 0; i < params.length; i++) {
					currentMaxMenuOrder++;
					params[i] = new Object[]{currentMaxMenuOrder, pid, newIdList.get(i)};
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			try {
				//上级菜单ID
				Integer pid = null;
				
				String idListSql = "SELECT ID,MENU_PID FROM SYS_MENU WHERE MENU_PID="
						+ "(SELECT MENU_PID FROM SYS_MENU WHERE ID=" + targetId + ") "
						+ "AND ID NOT IN (" + sourceIds + ") AND ENABLE=1 "
						+ "ORDER BY MENU_ORDER";
				List<Object[]> idList = getQueryRunner().query(idListSql, new ArrayListHandler());
				if(idList.size() != 0){
					//上级ID,用于后面批量更新
					pid = (Integer) idList.get(0)[1];
					
					for (Object[] data : idList) {
						Integer id = (Integer) data[0];
						if(id.compareTo(targetId) == 0){
							//是目标
							if("prev".equals(moveType)){
								//先插入移动的菜单
								for (int i = 0; i < sourceIdArr.length; i++) {
									newIdList.add(IntegerUtils.str2int(sourceIdArr[i]));
								}
								//再插入目标
								newIdList.add(id);
							}else{
								//next
								//先插入目标
								newIdList.add(id);
								//再插入移动的菜单
								for (int i = 0; i < sourceIdArr.length; i++) {
									newIdList.add(IntegerUtils.str2int(sourceIdArr[i]));
								}
							}
						}else{
							//其它菜单顺序不变
							newIdList.add(id);
						}
					}
					params = new Object[newIdList.size()][];
					for (int i = 0; i < params.length; i++) {
						params[i] = new Object[]{i + 1, pid, newIdList.get(i)};
					}
				}
			} catch (Exception e) {
				StringBuilder logBuilder = new StringBuilder();
				logBuilder.append("move faild,sourceIds:");
				logBuilder.append(sourceIds);
				logBuilder.append(",targetId:");
				logBuilder.append(targetId);
				logBuilder.append(",moveType:");
				logBuilder.append(moveType);
				log.error(logBuilder.toString(), e);
			}
		}
		
		//最后的批处理
		if(newIdList.size() != 0 && params != null){
			//批量更新
			try {
				getQueryRunner().batch(updateSql, params);
			} catch (SQLException e) {
				log.error("batch faild", e);
			}
		}
	}

}
