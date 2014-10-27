package com.gary.framework.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.gary.framework.entity.SysNetdisk;
import com.gary.framework.util.Paging;

/**
 * 网盘DAO
 * @author gary
 */
@Service
public class SysNetdiskDAO extends BaseDAO {
	
	/**
	 * 保存
	 * @param sysNetdisk
	 */
	public void save(SysNetdisk sysNetdisk) {
		try {
			getQueryRunner().update("INSERT INTO SYS_NETDISK "
					+ "(FILE_URL,FILE_NAME,"
					+ "FILE_DESC,FILE_SIZE,"
					+ "FILE_HIT,FILE_OWNER,"
					+ "UPLOAD_TIME,ENABLE) VALUES "
					+ "(?,?,?,?,?,?,now(),1)", 
					new Object[]{sysNetdisk.getFileUrl(),sysNetdisk.getFileName(),
							sysNetdisk.getFileDesc(), sysNetdisk.getFileSize(),
							sysNetdisk.getFileHit(), sysNetdisk.getFileOwner()});
		} catch (SQLException e) {
			log.error("save netdisk faild,sysNetdisk:" + sysNetdisk, e);
		}
	}
	
	/**
	 * 禁用
	 * @param id
	 */
	public void deleteById(Integer id) {
		disableById(id, "SYS_NETDISK");
	}

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	public SysNetdisk getById(Integer id) {
		try {
			return getQueryRunner().query("SELECT ID,FILE_URL,FILE_NAME,FILE_DESC,"
					+ "FILE_SIZE,FILE_HIT,FILE_OWNER,UPLOAD_TIME,ENABLE "
					+ "FROM SYS_NETDISK WHERE ID=?", 
					new BeanHandler<SysNetdisk>(SysNetdisk.class), 
					id);
		} catch (SQLException e) {
			log.error("getByID faild,id:" + id, e);
			return null;
		}
	}
	
	/**
	 * 更新信息
	 * @param sysNetdisk
	 */
	public void updateInfo(SysNetdisk sysNetdisk) {
		try {
			getQueryRunner().update("UPDATE SYS_NETDISK "
					+ "SET FILE_NAME=?,FILE_DESC=? WHERE ID=?", 
					new Object[]{sysNetdisk.getFileName(),sysNetdisk.getFileDesc(),sysNetdisk.getId()});
		} catch (SQLException e) {
			log.error("updateInfo faild,sysNetdisk:" + sysNetdisk, e);
		}
	}
	
	/**
	 * 更新
	 * @param sysNetdisk
	 */
	public void update(SysNetdisk sysNetdisk) {
		try {
			getQueryRunner().update("UPDATE SYS_NETDISK SET FILE_URL=?,FILE_NAME=?,"
					+ "FILE_DESC=?,FILE_SIZE=?,FILE_HIT=?,"
					+ "FILE_OWNER=?,UPLOAD_TIME=?,"
					+ "ENABLE=? "
					+ "WHERE ID=?", 
					new Object[]{sysNetdisk.getFileUrl(),sysNetdisk.getFileName(),
							sysNetdisk.getFileDesc(),sysNetdisk.getFileSize(),sysNetdisk.getFileHit(),
							sysNetdisk.getFileOwner(),sysNetdisk.getUploadTime(),
							sysNetdisk.getEnable(),sysNetdisk.getId()});
		} catch (SQLException e) {
			log.error("update sysNetdisk faild,sysNetdisk:" + sysNetdisk, e);
		}
	}
	
	/**
	 * 分页搜索
	 * @param fileName
	 * @param fileDesc
	 * @param fileOwnerNickName
	 * @param uploadTimeFrom
	 * @param uploadTimeTo
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Paging getPaging(String fileName, String fileDesc, 
			String fileOwnerNickName, String uploadTimeFrom, String uploadTimeTo, 
			String enable,
			int pageNum, int pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT N.ID,N.FILE_URL,N.FILE_NAME,N.FILE_DESC,"
					+ "N.FILE_SIZE,N.FILE_HIT,N.FILE_OWNER,N.UPLOAD_TIME,"
					+ "U.NICK_NAME "
					+ "FROM SYS_NETDISK N,SYS_USER U "
					+ "WHERE N.FILE_OWNER=U.ID AND N.ENABLE=").append(enable);
		if(StringUtils.isNotBlank(fileName)){
			sb.append(" AND N.FILE_NAME LIKE '%").append(fileName).append("%'");
		}
		if(StringUtils.isNotBlank(fileDesc)){
			sb.append(" AND N.FILE_DESC LIKE '%").append(fileDesc).append("%'");
		}
		if(StringUtils.isNotBlank(fileOwnerNickName)){
			sb.append(" AND U.NICK_NAME LIKE '%").append(fileOwnerNickName).append("%'");
		}
		if(StringUtils.isNotBlank(uploadTimeFrom)){
			sb.append(" AND N.UPLOAD_TIME>='").append(uploadTimeFrom).append("'");
		}
		if(StringUtils.isNotBlank(uploadTimeTo)){
			sb.append(" AND N.UPLOAD_TIME<='").append(uploadTimeTo).append("'");
		}
		sb.append(" ORDER BY N.ID");
		
		return getPaging(sb.toString(), new MapListHandler(), 
				pageNum, pageSize);
	}
	
	/**
	 * 更新下载次数
	 * @param id
	 */
	public void updateFileHit(Integer id) {
		try {
			getQueryRunner().update("UPDATE SYS_NETDISK SET FILE_HIT=FILE_HIT+1 WHERE ID=?", 
					new Object[]{id});
		} catch (SQLException e) {
			log.error("update updateFileHit faild,id:" + id, e);
		}
	}
	
	/**
	 * 恢复删除文件
	 * @param id
	 */
	public void restore(Integer id) {
		try {
			getQueryRunner().update("UPDATE SYS_NETDISK SET ENABLE=1 WHERE ID=?", 
					new Object[]{id});
		} catch (SQLException e) {
			log.error("update restore faild,id:" + id, e);
		}
	}

	/**
	 * 清空回收站,删除所有已删除文件
	 */
	public void emptyRecycle(){
		try {
			getQueryRunner().update("DELETE FROM SYS_NETDISK WHERE ENABLE=0");
		} catch (SQLException e) {
			log.error("update emptyRecycle faild", e);
		}
	}

	/**
	 * 彻底删除文件
	 * @param id
	 */
	public void forceDeleteById(Integer id) {
		try {
			getQueryRunner().update("DELETE FROM SYS_NETDISK WHERE ID=?", 
					new Object[]{id});
		} catch (SQLException e) {
			log.error("update forceDeleteById faild,id:" + id, e);
		}
	}

	/**
	 * 查找所有回收站文件的fileUrl
	 * @return
	 */
	public List<Object[]> getRecycleFileList() {
		try {
			return getQueryRunner().query("SELECT FILE_URL FROM SYS_NETDISK WHERE ENABLE=0", new ArrayListHandler());
		} catch (SQLException e) {
			log.error("faild to getRecycleFileList", e);
			return new ArrayList<Object[]>();
		}
	}

	/**
	 * 判断文件是否存在
	 * @param fileUrl
	 * @return
	 */
	public boolean fileExists(String fileUrl) {
		try {
			long count = getQueryRunner().query("SELECT COUNT(*) from SYS_NETDISK WHERE FILE_URL=?", 
					new ScalarHandler<Long>(),
					fileUrl);
			return count != 0L;
		} catch (SQLException e) {
			log.error("fileExists faild,fileUrl:" + fileUrl, e);
			return true;
		} 
	}

}