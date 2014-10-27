package com.gary.framework.core;

import com.alibaba.druid.util.JdbcConstants;

public class Constants {

	//basePath在application中的key
	public static final String APPLICATION_BASE_PATH = "base";
	//登录用户在session中的key
	public static final String SESSION_LOGIN_USER = "LOGIN_USER";
	//普通用户在session中的key
	public static final String SESSION_COMMON_USER = "COMMON_USER";
	
	//默认分页大小
	public static final int PAGE_SIZE = 10;
	//启用验证码
	public static final boolean ENABLE_CAPTCHA = true;
	//验证码区分大小写
	public static final boolean CAPTCHA_IGNORE_CASE = true;
	//启用登录拦截器
	public static final boolean ENABLE_LOGIN_INTERCEPTOR = true;
	
	//数据库类型
	public static final String DB_TYPE = JdbcConstants.MYSQL;
}
