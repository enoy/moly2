package com.gary.framework.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.gary.framework.entity.SysUser;
import com.gary.framework.util.Paging;

/**
 * 用户DAO
 * @author gary
 *
 */
@Service
public class SysUserDAO extends BaseDAO {

	/**
	 * 根据用户名获取
	 * @param userName
	 * @param exceptId
	 * @return
	 */
	public SysUser getUserByUserName(String userName, String exceptId) {
		ResultSetHandler<SysUser> rsh = new BeanHandler<SysUser>(SysUser.class);
		try {
			SysUser users = null;
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("SELECT ID,USER_NAME,PASSWORD," +
					"NICK_NAME,LAST_LOGIN_IP,LAST_LOGIN_TIME,ENABLE FROM SYS_USER WHERE ENABLE=1 AND USER_NAME=?");
			if(StringUtils.isNotBlank(exceptId)){
				sqlBuilder.append(" AND ID!=").append(exceptId); 
			}
			users = getQueryRunner().query(sqlBuilder.toString(), rsh, userName);
			return users;
		} catch (SQLException e) {
			log.error("getUserByUserName faild", e);
			return null;
		}
	}
	
	/**
	 * 保存
	 * @param sysUser
	 */
	public void save(SysUser sysUser) {
		try {
			getQueryRunner().update("INSERT INTO SYS_USER "
					+ "(USER_NAME,PASSWORD,NICK_NAME,"
					+ "LAST_LOGIN_IP,LAST_LOGIN_TIME,ENABLE) VALUES " +
					"(?,?,?,?,?,1)", 
					new Object[]{sysUser.getUserName(), sysUser.getPassword(), sysUser.getNickName(), 
							sysUser.getLastLoginIp(), sysUser.getLastLoginTime()});
		} catch (SQLException e) {
			log.error("save sysUser faild,sysUser:" + sysUser, e);
		}
	}
	
	/**
	 * 禁用
	 * @param id
	 */
	public void deleteById(Integer id) {
		disableById(id, "SYS_USER");
	}

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	public SysUser getById(Integer id) {
		ResultSetHandler<SysUser> rsh = new BeanHandler<SysUser>(SysUser.class);
		try {
			return getQueryRunner().query("SELECT ID,USER_NAME,PASSWORD,NICK_NAME,"
					+ "LAST_LOGIN_IP,LAST_LOGIN_TIME,ENABLE "
					+ "FROM SYS_USER WHERE ID=?", 
					rsh, id);
		} catch (SQLException e) {
			log.error("getByID faild,id:" + id, e);
			return null;
		}
	}
	
	/**
	 * 更新
	 * @param sysUser
	 */
	public void update(SysUser sysUser) {
		try {
			getQueryRunner().update("UPDATE SYS_USER SET USER_NAME=?,PASSWORD=?,NICK_NAME=?,"
					+ "LAST_LOGIN_IP=?,LAST_LOGIN_TIME=?,ENABLE=? "
					+ "WHERE ID=?", 
					new Object[]{sysUser.getUserName(), sysUser.getPassword(), sysUser.getNickName(), 
							sysUser.getLastLoginIp(), sysUser.getLastLoginTime(), sysUser.getEnable(), 
							sysUser.getId()});
		} catch (SQLException e) {
			log.error("update sysUser faild,sysUser:" + sysUser, e);
		}
	}
	
	/**
	 * 分页搜索
	 * @param userName
	 * @param nickName
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Paging getPaging(String userName, String nickName, int pageNum, int pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ID,USER_NAME,PASSWORD,NICK_NAME,"
					+ "LAST_LOGIN_IP,LAST_LOGIN_TIME,ENABLE FROM SYS_USER WHERE ENABLE=1");
		if(StringUtils.isNotBlank(userName)){
			sb.append(" AND USER_NAME LIKE '%").append(userName).append("%'");
		}
		if(StringUtils.isNotBlank(nickName)){
			sb.append(" AND NICK_NAME LIKE '%").append(nickName).append("%'");
		}
		sb.append(" ORDER BY ID");
		
		return getPaging(sb.toString(), new BeanListHandler<SysUser>(SysUser.class), 
				pageNum, pageSize);
	}

	/**
	 * 修改密码
	 * @param sysUser
	 */
	public void updatePass(SysUser sysUser) {
		try {
			getQueryRunner().update("UPDATE SYS_USER SET PASSWORD=? WHERE ID=?",
					new Object[]{sysUser.getPassword(), sysUser.getId()});
		} catch (Exception e) {
			log.error("updatePass faild,sysUser:" + sysUser, e);
		}
	}

}
