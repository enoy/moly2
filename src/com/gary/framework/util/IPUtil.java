package com.gary.framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * IP工具
 * Date		: 2012-12-4
 * Time 	: 上午08:21:16
 * Email	: tbzhang@tianyi-brew.com
 * @author  : 张桐搏
 * @version 1.0.0
 *
 */
public class IPUtil {

	private Pattern ipFormatPattern = null;
	
	public IPUtil(){
		String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." 
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." 
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." 
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		ipFormatPattern = Pattern.compile(regex);
	}
	
	/**
	 * 判断IP格式是否正确
	 * Date		: 2012-12-4
	 * Time 	: 上午08:21:22
	 * Email	: tbzhang@tianyi-brew.com
	 * @author  : 张桐搏
	 * @version 1.0.0
	 * @param ip
	 * @return
	 */
	public boolean isIP(String ip){
		if(StringUtils.isBlank(ip)){
			return false;
		}
		Matcher m = ipFormatPattern.matcher(ip);
		if(m.find()){
		    return true;
		}else{
			return false;
		}
	}
}
