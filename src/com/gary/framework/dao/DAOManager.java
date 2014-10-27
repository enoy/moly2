package com.gary.framework.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("daoManager")
public class DAOManager{
	
	@Autowired
	private SysUserDAO sysUserDAO;

	@Autowired
	private SysNetdiskDAO sysNetdiskDAO;
	
	@Autowired
	private SysRoleDAO sysRoleDAO;
	
	@Autowired
	private SysMenuDAO sysMenuDAO;

	public SysUserDAO getSysUserDAO() {
		return sysUserDAO;
	}

	public SysNetdiskDAO getSysNetdiskDAO() {
		return sysNetdiskDAO;
	}

	public SysRoleDAO getSysRoleDAO() {
		return sysRoleDAO;
	}

	public SysMenuDAO getSysMenuDAO() {
		return sysMenuDAO;
	}

}
