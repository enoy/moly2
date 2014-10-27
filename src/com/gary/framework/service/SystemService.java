package com.gary.framework.service;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gary.framework.util.FileUtil;

/**
 * 系统Service
 * @author gary
 *
 */
@Service
public class SystemService {
	
	private Logger log = Logger.getLogger(this.getClass());

	public Map<String, Object> getMemeryInfo() {
		Map<String, Object> mi = new HashMap<String, Object>();

		Runtime r = Runtime.getRuntime();
		long freeMemory = r.freeMemory();
		mi.put("freeMemory", freeMemory);
		mi.put("freeMemoryFormat", FileUtil.getFileSize(freeMemory));
		long maxMemory = r.maxMemory();
		mi.put("maxMemory", maxMemory);
		mi.put("maxMemoryFormat", FileUtil.getFileSize(maxMemory));
		long totalMemory = r.totalMemory();
		mi.put("totalMemory", totalMemory);
		mi.put("totalMemoryFormat", FileUtil.getFileSize(totalMemory));
		
//		ManagementFactory.getMemoryPoolMXBeans()
		MemoryMXBean mxBean = ManagementFactory.getMemoryMXBean();
		MemoryUsage heapMU = mxBean.getHeapMemoryUsage();
		long heapInit = heapMU.getInit();
		mi.put("heapInit", heapInit);
		mi.put("heapInitFormat", FileUtil.getFileSize(heapInit));
		long heapCommitted = heapMU.getCommitted();
		mi.put("heapCommitted", heapCommitted);
		mi.put("heapCommittedFormat", FileUtil.getFileSize(heapCommitted));
		long heapMax = heapMU.getMax();
		mi.put("heapMax", heapMax);
		mi.put("heapMaxFormat", FileUtil.getFileSize(heapMax));
		long heapUsed = heapMU.getUsed();
		mi.put("heapUsed", heapUsed);
		mi.put("heapUsedFormat", FileUtil.getFileSize(heapUsed));
		
		MemoryUsage nonHeapMU = mxBean.getNonHeapMemoryUsage();
		long nonHeapInit = nonHeapMU.getInit();
		mi.put("nonHeapInit", nonHeapInit);
		mi.put("nonHeapInitFormat", FileUtil.getFileSize(nonHeapInit));
		long nonHeapCommitted = nonHeapMU.getCommitted();
		mi.put("nonHeapCommitted", nonHeapCommitted);
		mi.put("nonHeapCommittedFormat", FileUtil.getFileSize(nonHeapCommitted));
		long nonHeapMax = nonHeapMU.getMax();
		mi.put("nonHeapMax", nonHeapMax);
		mi.put("nonHeapMaxFormat", FileUtil.getFileSize(nonHeapMax));
		long nonHeapUsed = nonHeapMU.getUsed();
		mi.put("nonHeapUsed", nonHeapUsed);
		mi.put("nonHeapUsedFormat", FileUtil.getFileSize(nonHeapUsed));
		return mi;
	}
	
	public Map<String, String> getSystemInfo() {
		Map<String, String> si = new HashMap<String, String>();
		
//		System.out.println(System.getenv());
//		System.out.println(System.getProperties());
		si.put("osName", System.getProperty("os.name"));
		si.put("osVersion", System.getProperty("os.version"));
		si.put("osArch", System.getProperty("os.arch"));
		si.put("userDir", System.getProperty("user.dir"));
		si.put("jVer", System.getProperty("java.runtime.version"));
		si.put("javaHome", System.getProperty("java.home"));
		si.put("javaIOTmpdir", System.getProperty("java.io.tmpdir"));
		
		return si;
	}
	
	public void interruptThread(String threadIdStr) {
		if(StringUtils.isBlank(threadIdStr)){
			return;
		}
		long threadId = Long.parseLong(threadIdStr);
		int tc = Thread.activeCount();
	    Thread[] ts = new Thread[tc];
	    Thread.enumerate(ts);
	    for (Thread thread : ts) {
			if(thread.getId() == threadId){
				try{
					thread.interrupt();
				}catch(Exception e){
					log.error("interruptThread " + threadIdStr + " faild", e);
				}
			}
		}
	}

}
