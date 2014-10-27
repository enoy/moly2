package com.gary.framework.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.gary.framework.core.Constants;
import com.gary.framework.dao.DAOManager;
import com.gary.framework.util.IntegerUtils;
import com.gary.framework.util.WebUtil;

/**
 * 公用Service实现
 * @author gary
 *
 */
public class BaseService {
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private DAOManager daoManager;
	
	@Autowired
	private ServiceManager serviceManager;

	public DAOManager getDaoManager() {
		return daoManager;
	}
	
	public ServiceManager getServiceManager() {
		return serviceManager;
	}
	
	/**
	 * 当前页
	 * @return
	 */
	public int getPageNum(){
		String pageNum = WebUtil.getRequest().getParameter("pageNum");
		return IntegerUtils.str2int(pageNum, 1);
	}
	
	/**
	 * 分页大小
	 * @return
	 */
	public int getPageSize(){
		String pageSize = WebUtil.getRequest().getParameter("pageSize");
		return IntegerUtils.str2int(pageSize, Constants.PAGE_SIZE);
	}
}
