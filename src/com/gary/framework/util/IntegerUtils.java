package com.gary.framework.util;

import org.apache.commons.lang3.StringUtils;


public class IntegerUtils {

	/**
	 * String转Integer,默认-1
	 * @author ztb
	 * 2014-1-21 下午12:33:32
	 * @param s
	 * @return
	 */
	public static Integer str2int(String s){
		return str2int(s, -1);
	}
	
	/**
	 * String转Integer
	 * @author ztb
	 * 2014-1-21 下午12:36:18
	 * @param s
	 * @param defaultNum
	 * @return
	 */
	public static Integer str2int(String s, Integer defaultNum){
		Integer num = defaultNum;
		try {
			if(StringUtils.isNotBlank(s)){
				num = Integer.valueOf(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
}
