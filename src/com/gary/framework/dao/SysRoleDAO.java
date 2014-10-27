package com.gary.framework.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.gary.framework.entity.SysMenu;
import com.gary.framework.entity.SysRole;
import com.gary.framework.util.Paging;

/**
 * 角色
 * @author gary
 *
 */
@Service
public class SysRoleDAO extends BaseDAO {

	/**
	 * 根据角色获取
	 * @param roleName
	 * @param exceptId
	 * @return
	 */
	public SysRole getRoleByRoleName(String roleName, String exceptId) {
		try {
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("SELECT ID,ROLE_NAME,ROLE_DESC,ENABLE "
					+ "FROM SYS_ROLE WHERE ENABLE=1 AND ROLE_NAME=?");
			if(StringUtils.isNotBlank(exceptId)){
				sqlBuilder.append(" AND ID!=").append(exceptId); 
			}
			return getQueryRunner().query(sqlBuilder.toString(), 
					new BeanHandler<SysRole>(SysRole.class), 
					roleName);
		} catch (SQLException e) {
			log.error("getRoleByRoleName faild,roleName:" + roleName + 
					",exceptId:" + exceptId, e);
			return null;
		}
	}
	
	/**
	 * 保存
	 * @param sysRole
	 */
	public void save(SysRole sysRole) {
		try {
			getQueryRunner().update("INSERT INTO SYS_ROLE "
					+ "(ROLE_NAME,ROLE_DESC,ENABLE) VALUES " +
					"(?,?,1)", 
					new Object[]{sysRole.getRoleName(),sysRole.getRoleDesc()});
		} catch (SQLException e) {
			log.error("save sysRole faild,sysRole:" + sysRole, e);
		}
	}
	
	/**
	 * 禁用
	 * @param id
	 */
	public void deleteById(Integer id) {
		disableById(id, "SYS_ROLE");
	}

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	public SysRole getById(Integer id) {
		try {
			return getQueryRunner().query("SELECT ID,ROLE_NAME,ROLE_DESC,ENABLE "
					+ "FROM SYS_ROLE WHERE ID=?", 
					new BeanHandler<SysRole>(SysRole.class), 
					id);
		} catch (SQLException e) {
			log.error("getByID faild,id:" + id, e);
			return null;
		}
	}
	
	/**
	 * 更新
	 * @param sysRole
	 */
	public void update(SysRole sysRole) {
		try {
			getQueryRunner().update("UPDATE SYS_ROLE SET ROLE_NAME=?,ROLE_DESC=?,ENABLE=? "
					+ "WHERE ID=?", 
					new Object[]{sysRole.getRoleName(),sysRole.getRoleDesc(), 
							sysRole.getEnable(), sysRole.getId()});
		} catch (SQLException e) {
			log.error("update sysRole faild,sysRole:" + sysRole, e);
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
	public Paging getPaging(String roleName, String roleDesc, int pageNum, int pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ID,ROLE_NAME,ROLE_DESC,ENABLE FROM SYS_ROLE WHERE ENABLE=1");
		if(StringUtils.isNotBlank(roleName)){
			sb.append(" AND ROLE_NAME LIKE '%").append(roleName).append("%'");
		}
		if(StringUtils.isNotBlank(roleDesc)){
			sb.append(" AND ROLE_DESC LIKE '%").append(roleDesc).append("%'");
		}
		sb.append(" ORDER BY ID");
		
		return getPaging(sb.toString(), new BeanListHandler<SysRole>(SysRole.class), 
				pageNum, pageSize);
	}
	
	/**
	 * 批量保存用户角色
	 * @param roleId
	 * @param userIds
	 */
	public int[] saveUserRole(String roleId, String[] userIds){
		if(userIds != null){
			Object[][] param = new Object[userIds.length][];
			for (int i = 0; i < param.length; i++) {
				param[i] = new Object[]{roleId, userIds[i]};
			}
			try {
				return getQueryRunner().batch("insert into SYS_USER_ROLE (ROLE_ID,USER_ID) values (?,?)", param);
			} catch (SQLException e) {
				log.error("saveUserRole faild,roleId:" + roleId + 
						",userIds:" + Arrays.toString(userIds), e);
			}
		}
		return null;
	}
	
	/**
	 * 删除角色下所有用户
	 * @param roleId
	 */
	public void removeAllUsersFromRole(String roleId){
		try {
			getQueryRunner().update("DELETE FROM SYS_USER_ROLE WHERE ROLE_ID=" + roleId);
		} catch (SQLException e) {
			log.error("removeAllUsersFromRole faild, roleId:" + roleId, e);
		}
	}
	
	/**
	 * 将用户从角色中删除
	 * @param userRoleId
	 */
	public void removeUserFromRole(String userRoleId){
		try {
			getQueryRunner().update("DELETE FROM SYS_USER_ROLE WHERE ID=" + userRoleId);
		} catch (SQLException e) {
			log.error("removeUserFromRole faild, userRoleId:" + userRoleId, e);
		}
	}

	/**
	 * 角色中的用户列表
	 * @param roleId
	 * @param nickName
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Paging getUserList(String roleId, String nickName,
			int pageNum, int pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT SUR.ID USER_ROLE_ID,U.ID,U.USER_NAME,U.NICK_NAME "
				+ "FROM SYS_USER U,SYS_USER_ROLE SUR "
				+ "WHERE U.ID=SUR.USER_ID AND SUR.ROLE_ID=? AND U.ENABLE=1");
		if(StringUtils.isNotBlank(nickName)){
			sb.append(" AND U.NICK_NAME LIKE '%").append(nickName).append("%'");
		}
		return getPaging(sb.toString(), new Object[]{roleId}, 
				new MapListHandler(), pageNum, pageSize);
	}
	
	/**
	 * 不在角色中的用户列表
	 * @param roleId
	 * @param nickName
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Paging availableUserList(String roleId, String nickName,
			int pageNum, int pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT U.ID, U.USER_NAME, U.NICK_NAME "
				+ "FROM SYS_USER U "
				+ "LEFT JOIN (SELECT USER_ID FROM SYS_USER_ROLE WHERE ROLE_ID = ?) SUR "
				+ "ON U.ID = SUR.USER_ID "
				+ "WHERE U.ENABLE = 1 "
				+ "AND SUR.user_id IS NULL");
		if(StringUtils.isNotBlank(nickName)){
			sb.append(" AND U.NICK_NAME LIKE '%").append(nickName).append("%'");
		}
		return getPaging(sb.toString(), new Object[]{roleId}, 
				new MapListHandler(), pageNum, pageSize);
	}
	
	/**
	 * 获取用户权限菜单
	 * @param userId
	 * @return
	 */
	public List<SysMenu> getUserMenus(Integer userId){
		String sql = "SELECT M.ID, " +
				 "       M.MENU_NAME, " +
				 "       M.MENU_URL, " +
				 "       M.MENU_ORDER, " +
				 "       M.MENU_PID " +
				 "FROM   SYS_MENU M, " +
				 "       (SELECT DISTINCT SRM.MENU_ID " +
				 "        FROM   SYS_USER_ROLE SUR, " +
				 "               SYS_ROLE_MENU SRM " +
				 "        WHERE  SUR.ROLE_ID = SRM.ROLE_ID " +
				 "               AND SUR.USER_ID = ?) T " +
				 "WHERE  M.ID = T.MENU_ID " +
				 "ORDER  BY MENU_PID, " +
				 "          MENU_ORDER ";
		try {
			return getQueryRunner().query(sql, new BeanListHandler(SysMenu.class), userId);
		} catch (SQLException e) {
			log.error("getUserMenus faild, userId:" + userId, e);
			return new ArrayList<SysMenu>();
		}
	}

}
