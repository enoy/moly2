package com.gary.framework.util;

import java.util.ResourceBundle;
/**
 * 读取properties配置文件
 * @author gary
 *
 */
public class PropertiesReader {
	
	/**
	 * 读取指定配置文件的属性
	 * @param fileName 配置文件名
	 * @param key 属性名
	 * @return String
	 */
	public static String getStringFromFile(String fileName, String key){
		return ResourceBundle.getBundle(fileName).getString(key);
	}
	
	/**
	 * 读取指定配置文件的属性
	 * @param fileName 配置文件名
	 * @param key 属性名
	 * @return Integer
	 */
	public static Integer getIntegerFromFile(String fileName, String key){
		return Integer.parseInt(getStringFromFile(fileName,key));
	}
	
	/**
	 * 读取指定配置文件的属性
	 * @param fileName 配置文件名
	 * @param key 属性名
	 * @return Long
	 */
	public static Long getLongFromFile(String fileName, String key) {
		return Long.parseLong(getStringFromFile(fileName,key));
	}
	
	/**
	 * 读取指定配置文件的属性
	 * @param fileName 配置文件名
	 * @param key 属性名
	 * @return boolean
	 */
	public static boolean getBooleanFromFile(String fileName, String key){
		return Boolean.parseBoolean(getStringFromFile(fileName, key));
	}
	
}
