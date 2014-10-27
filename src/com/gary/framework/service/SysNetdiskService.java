package com.gary.framework.service;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.gary.framework.entity.SysNetdisk;
import com.gary.framework.util.Paging;
import com.gary.framework.util.WebUtil;

/**
 * 网盘service
 * @author gary
 */
@Service
public class SysNetdiskService extends BaseService {
	
	//网盘文件基础路径
	public static final String NET_DISK_BASE_PATH = "upload" + File.separator + "netdisk" + File.separator;

	/**
	 * 保存
	 * @param sysNetdisk
	 */
	public void save(SysNetdisk sysNetdisk){
		sysNetdisk.setEnable(Integer.valueOf(1));
		getDaoManager().getSysNetdiskDAO().save(sysNetdisk);
	}

	/**
	 * 禁用
	 * @param id
	 */
	public void deleteById(Integer id){
		getDaoManager().getSysNetdiskDAO().deleteById(id);
	}

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	public SysNetdisk getById(Integer id){
		return getDaoManager().getSysNetdiskDAO().getById(id);
	}
	
	/**
	 * 更新信息
	 * @param sysNetdisk
	 */
	public void updateInfo(SysNetdisk sysNetdisk){
		getDaoManager().getSysNetdiskDAO().updateInfo(sysNetdisk);
	}

	/**
	 * 更新
	 * @param sysNetdisk
	 */
	public void update(SysNetdisk sysNetdisk){
		sysNetdisk.setEnable(Integer.valueOf(1));
		getDaoManager().getSysNetdiskDAO().update(sysNetdisk);
	}

	/**
	 * 分页搜索
	 * @param fileName
	 * @param fileDesc
	 * @param fileOwnerNickName
	 * @param uploadTimeFrom
	 * @param uploadTimeTo
	 * @return
	 */
	public Paging getPaging(String fileName, String fileDesc, 
			String fileOwnerNickName, String uploadTimeFrom, String uploadTimeTo){
		return getDaoManager().getSysNetdiskDAO().getPaging(fileName, fileDesc,
				fileOwnerNickName, uploadTimeFrom, uploadTimeTo, 
				"1", getPageNum(), getPageSize());
	}
	
	/**
	 * 分页搜索回收站
	 * @param fileName
	 * @param fileDesc
	 * @param fileOwnerNickName
	 * @param uploadTimeFrom
	 * @param uploadTimeTo
	 * @return
	 */
	public Paging getRecyclePaging(String fileName, String fileDesc, 
			String fileOwnerNickName, String uploadTimeFrom, String uploadTimeTo){
		return getDaoManager().getSysNetdiskDAO().getPaging(fileName, fileDesc,
				fileOwnerNickName, uploadTimeFrom, uploadTimeTo, 
				"0", getPageNum(), getPageSize());
	}

	/**
	 * 更新下载次数
	 * @param id
	 */
	public void updateFileHit(Integer id){
		getDaoManager().getSysNetdiskDAO().updateFileHit(id);
	}

	/**
	 * 恢复删除文件
	 * @param id
	 */
	public void restore(Integer id){
		getDaoManager().getSysNetdiskDAO().restore(id);
	}

	/**
	 * 清空回收站,删除所有已删除文件
	 */
	public void emptyRecycle(){
		//删除硬盘上的文件
		String filePath = WebUtil.getRealPath(NET_DISK_BASE_PATH) + File.separator;

		List<Object[]> fileUrlList = getDaoManager().getSysNetdiskDAO().getRecycleFileList();
		for (Object[] data : fileUrlList) {
			String fileUrl = (String)data[0];
			deleteNetDiskFile(filePath, fileUrl);
		}
		//删除数据库
		getDaoManager().getSysNetdiskDAO().emptyRecycle();
	}

	/**
	 * 彻底删除文件
	 * @param id
	 */
	public void forceDeleteById(Integer id) {
		SysNetdisk sysNetdisk = getById(id);
		getDaoManager().getSysNetdiskDAO().forceDeleteById(id);
		deleteNetDiskFile(sysNetdisk.getFileUrl());
	}
	
	/**
	 * 删除硬盘上的文件
	 * @param filePath
	 * @param fileUrl
	 * @return
	 */
	private boolean deleteNetDiskFile(String filePath, String fileUrl){
		filePath = filePath + fileUrl;
		log.debug("deleteFile: " + filePath);
		return new File(filePath).delete();
	}
	
	/**
	 * 删除硬盘上的文件
	 * @param fileUrl
	 * @return
	 */
	private boolean deleteNetDiskFile(String fileUrl){
		String filePath = WebUtil.getRealPath(NET_DISK_BASE_PATH) + File.separator;
		return deleteNetDiskFile(filePath, fileUrl);
	}

	/**
	 * 清理多余文件
	 */
	public void cleanUpFile() {
		File dir = new File(WebUtil.getRealPath(NET_DISK_BASE_PATH));
		if(dir.exists()){
			File[] files = dir.listFiles();
			if(files != null && files.length != 0){
				for (File f : files) {
					if(!getDaoManager().getSysNetdiskDAO().fileExists(f.getName())){
						log.debug("cleanup file " + f.getAbsolutePath());
						FileUtils.deleteQuietly(f);
					}
				}
			}
		}
	}
}
