package com.gary.framework.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 公用工具
 * @author gary
 *
 */
public class CommonUtil{
	
	/**
	 * 判断数组内有无重复元素
	 * @param args
	 * @return true 有重复 | false 无重复
	 */
	public static boolean hasRepeat(Object[] args){
		Set<Object> tempSet = new HashSet<Object>();
		for (int i = 0; i < args.length; i++) {
			tempSet.add(args[i]);
		}
		if(args.length == tempSet.size()){
			return false;
		}else{
			return true;
		}
	}
	

	/**
	 * 数组去空格
	 * @param array
	 * @return
	 */
	public static String[] trimArray(String[] array){
		for (int i = 1; i < array.length; i++) {
			array[i] = array[i].trim();
		}
		return array;
	}
	
	/**
	 * 使用nbtstat获取mac地址,仅限windows,在linux下用nmblookup命令代替
	 * @param ip
	 * @return
	 * @throws IOException
	 */
	public static String getMac(String ip){
		if("127.0.0.1".equals(ip)){
		InetAddress ads = null;
			try {
				ads = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return null;
			}
			ip=ads.getHostAddress();
		}
	    String str = "";   
	    String macAddress = "";   
		Process p = null;
		try {
			p = Runtime.getRuntime().exec("nbtstat -A " + ip);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}   
        InputStreamReader ir = new InputStreamReader(p.getInputStream());   
        LineNumberReader input = new LineNumberReader(ir);   
        for (int i = 1; i < 100; i++) {   
            try {
				str = input.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				try {
					ir.close();
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
            if (str != null) {   
                if (str.indexOf("MAC Address") > 1) {   
                    macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());   
                    break;   
                }   
            }   
        }
        return macAddress;
	}
	
	/**
	 * 生成ID
	 * @return
	 */
	public static String generateId(){
		return new Date().getTime()+getRandomString(5);
	}
	
	/**
	 * 生成随机字符串(数字+字母)
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(3);
			long result = 0;
			switch (number) {
			case 0:
				result = Math.round(Math.random() * 25 + 65);
				sb.append(String.valueOf((char) result));
				break;
			case 1:
				result = Math.round(Math.random() * 25 + 97);
				sb.append(String.valueOf((char) result));
				break;
			case 2:
				sb.append(String.valueOf(new Random().nextInt(10)));
				break;
			}
		}
		return sb.toString();
	}
	
	/**
	 * Map转Object[]
	 * @param param
	 * @param key
	 * @return
	 */
	public static Object[] getObjectArrayFromMap(Map<String, Object> param, String key) {
		String[] keys = key.split(",");
		Object[] objectArray = new Object[keys.length];
		for (int i = 0; i < keys.length; i++) {
			objectArray[i] = param.get(keys[i].trim());
		}
		return objectArray;
	}
	
	/**
	 * Map转Object[],Map的每个value都是数组
	 * @param param
	 * @param key
	 * @return
	 */
	public static Object[] getFirstObjectArrayFromMap(Map<String, Object> param, String key) {
		String[] keys = key.split(",");
		Object[] objectArray = new Object[keys.length];
		for (int i = 0; i < keys.length; i++) {
			Object[] tempArray = (Object[]) param.get(keys[i].trim());
			objectArray[i] = tempArray[0];
		}
		return objectArray;
	}
	
	/**
	 * 返回Map对应key的数组的第一个元素
	 * @param param
	 * @param key
	 * @return
	 */
	public static String getFirstObjectFromMap(Map<String, String[]> param, String key){
		if(param != null){
			String[] tempArray = (String[]) param.get(key);
			return tempArray[0];
		}else{
			return null;
		}
	}
	
	/**
	 * 判断是否为空
	 * @param object
	 * @return true非空 | false空
	 */
	public static boolean isNotNull(Object object){
		if(object == null){
			return false;
		}else if(object instanceof String){
			if("".equals(object)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 从Spring获取Bean,从XML读取
	 * @param beanName
	 * @return
	 */
	public static Object getBeanByXML(String beanName){
		ClassPathXmlApplicationContext cpac = new ClassPathXmlApplicationContext("spring/*.xml"); 
		Object bean = cpac.getBean(beanName);
		cpac.close();
		return bean;
	}
	
	/**
	 * ISO-8859-1转UTF-8
	 * @param str
	 * @return
	 */
	public static String en2utf8(String str){
		try {
			str = new String(str.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * ISO-8859-1转GBK
	 * @param str
	 * @return
	 */
	public static String en2gbk(String str){
		try {
			str = new String(str.getBytes("ISO-8859-1"),"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * ISO-8859-1转GB2312
	 * @param str
	 * @return
	 */
	public static String en2gb2312(String str){
		try {
			str = new String(str.getBytes("ISO-8859-1"),"GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 获取随机UUID
	 * @return
	 */
	public static String getRandomUUID(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 从Map中获取key并转换成String类型
	 * @param source
	 * @param key
	 * @return
	 */
	public static String getStr(Map<?, ?> source, String key){
		return (String) source.get(key);
	}
	
	/**
	 * 从Map中获取key,转换成String类型,并url decode
	 * @param source
	 * @param key
	 * @return
	 */
	public static String getStrAndDecode(Map<?, ?> source, String key){
		String str = getStr(source, key);
		return StringUtils.isBlank(str) ? str : URLUtil.decodeURL(str);
	}
	
}
