package com.gary.framework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceManager {
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysNetdiskService sysNetdiskService;
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysMenuService sysMenuService;

	public SystemService getSystemService() {
		return systemService;
	}

	public SysUserService getSysUserService() {
		return sysUserService;
	}

	public SysNetdiskService getSysNetdiskService() {
		return sysNetdiskService;
	}

	public SysRoleService getSysRoleService() {
		return sysRoleService;
	}

	public SysMenuService getSysMenuService() {
		return sysMenuService;
	}

}
