package com.gary.framework.thread;

import com.gary.framework.service.SysNetdiskService;

/**
 * 清理多余文件线程
 * @author gary
 *
 */
public class CleanUpFileThread extends Thread {

	private SysNetdiskService sysNetdiskService;
	
	public CleanUpFileThread(SysNetdiskService sysNetdiskService){
		this.sysNetdiskService = sysNetdiskService;
	}
	
	@Override
	public void run() {
		super.run();
		sysNetdiskService.cleanUpFile();
	}

}
