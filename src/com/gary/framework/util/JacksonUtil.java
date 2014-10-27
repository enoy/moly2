package com.gary.framework.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json工具
 * Date		: 2012-11-15
 * Time 	: 上午10:40:29
 * @author  : 张桐搏
 * @version 1.0.0
 *
 */
public class JacksonUtil {
	
	static volatile ObjectMapper objectMapper = null;

	private JacksonUtil(){}

	public static ObjectMapper getObjectMapper(){
		if (objectMapper == null){
			synchronized (ObjectMapper.class) {
				if (objectMapper == null){
					objectMapper = new ObjectMapper();
				}
			}
		}
		return objectMapper;
	}
	
}
