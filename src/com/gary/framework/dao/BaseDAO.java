package com.gary.framework.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.druid.sql.PagerUtils;
import com.gary.framework.core.Constants;
import com.gary.framework.util.Paging;

public class BaseDAO {
	
	protected final Logger log = Logger.getLogger(this.getClass());

	@Autowired
	protected DataSource dataSource = null;
	
	private QueryRunner qr = null;
	
	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			log.error("get connection faild", e);
			return null;
		}
	}
	
	public QueryRunner getQueryRunner(){
		if(qr == null){
			qr = new QueryRunner(dataSource);
		}
		return qr;
	}
	
	/**
	 * 保存并返回自动生成的ID
	 * @author ztb
	 * 2014-2-27 下午3:33:17
	 * @param insertSql
	 * @param params
	 * @return
	 */
	protected Integer saveAndReturnId(String insertSql, Object[] params){
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement(insertSql, 
					PreparedStatement.RETURN_GENERATED_KEYS);
			for(int i = 0 ; i < params.length ; i++){
				ps.setObject(i + 1, params[i]);
			}
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if(rs.next()){
				return rs.getInt(1);
			}
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("saveAndReturnId faild,sql:" + insertSql + 
					",params:" + Arrays.toString(params));
			System.exit(0);
		}finally{
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(ps);
			DbUtils.closeQuietly(con);
		}
		return -1;
	}
	
	/**
	 * 根据ID禁用,列名ENABLE
	 * @param id
	 * @param tableName
	 */
	protected void disableById(Integer id, String tableName) {
		disableById(id, tableName, "ID", "ENABLE");
	}
	
	/**
	 * 根据ID禁用
	 * @param id
	 * @param tableName
	 * @param idColumn
	 * @param enableColumn
	 */
	protected void disableById(Integer id, String tableName, String idColumn, String enableColumn) {
		try {
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("UPDATE ").append(tableName).append(" SET ");
			sqlBuilder.append(enableColumn).append("=0 WHERE ").append(idColumn).append("=?");
			getQueryRunner().update(sqlBuilder.toString(), id);
		} catch (SQLException e) {
			log.error("disableById faild,id:" + id + 
					",tableName:" + tableName + 
					",idColumn:" + idColumn + 
					",enableColumn:" + enableColumn, e);
		}
	}
	
	/**
	 * 根据查询列表语句生成count
	 * @param querySql
	 * @return
	 */
	protected String genCountSql(String querySql){
		return PagerUtils.count(querySql, Constants.DB_TYPE);
	}
	
	/**
	 * 查询count
	 * @param countSql
	 * @param params
	 * @return
	 */
	protected long getCount(String countSql, Object[] params){
		try {
			return ((Long) getQueryRunner().query(countSql, new ScalarHandler<Long>(), params)).longValue();
		} catch (SQLException e) {
			log.error("getCount faild,sql:" + countSql + 
					",params:" + Arrays.toString(params), e);
			return 0L;
		}
	}
	
	/**
	 * 根据查询列表语句生成分页查询sql
	 * @param querySql
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	protected String genPagingSql(String querySql, int pageNum, int pageSize){
		return PagerUtils.limit(querySql, Constants.DB_TYPE, (pageNum - 1) * pageSize, pageSize);
	}
	
	/**
	 * 查询分页
	 * @param querySql
	 * @param rsh
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Paging getPaging(String querySql, 
			ResultSetHandler rsh,
			int pageNum, int pageSize) {
		return getPaging(querySql, null, rsh, pageNum, pageSize);
	}
	
	/**
	 * 查询分页
	 * @param querySql
	 * @param params
	 * @param rsh
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Paging getPaging(String querySql, 
			Object[] params, ResultSetHandler rsh,
			int pageNum, int pageSize) {
		try {
			String listSql = genPagingSql(querySql, pageNum, pageSize);
			List<?> list = getQueryRunner().query(listSql, rsh, params);
			
			String countSql = genCountSql(querySql);
			long totalRows = getCount(countSql, params);
			
			return new Paging(list, totalRows, pageNum, pageSize);
		} catch (SQLException e) {
			log.error("getPaging faild, querySql:" + querySql + 
					",params:" + Arrays.toString(params) + 
					",pageNum:" + pageNum + 
					",pageSize:" + pageSize, 
					e);
			return new Paging();
		}
	}

}