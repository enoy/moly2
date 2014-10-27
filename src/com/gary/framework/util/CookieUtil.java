package com.gary.framework.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cookie工具
 * @author gary
 *
 */
public class CookieUtil {
	
	private static final Logger log = LoggerFactory.getLogger(CookieUtil.class);

	/**
	 * 获取Cookie
	 * @param key
	 * @return
	 */
	public static Cookie getCookie(String key) {
		return getCookieFromRequest(WebUtil.getRequest(), key);
	}

	/**
	 * 获取Cookie值
	 * @param key
	 * @return
	 */
	public static String getCookieValue(String key) {
		Cookie cookie = getCookie(key);
		return (cookie == null) ? null : cookie.getValue();
	}

	/**
	 * 从request获取cookie
	 * @param request
	 * @param key
	 * @return
	 */
	public static Cookie getCookieFromRequest(HttpServletRequest request,
			String key) {
		Cookie cookie = null;
		if (!StringUtils.isBlank(key)) {
			Cookie[] cookies = request.getCookies();
			if ((cookies != null) && (cookies.length > 0)) {
				for (int i = 0; i < cookies.length; i++) {
					cookie = cookies[i];
					if (cookie.getName().equals(key)) {
						return cookie;
					}else{
						cookie = null;
					}
				}
			}
		}
		log.debug("cookie [" + key + "] is null");
		return cookie;
	}
	
	/**
	 * 从request获取cookie值
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getCookieValueFromRequest(HttpServletRequest request,
			String key) {
		Cookie cookie = getCookieFromRequest(request, key);
		return (cookie == null) ? null : cookie.getValue();
	}

	/**
	 * 设置cookie
	 * @param key
	 * @param value
	 * @param expiry
	 */
	public static void setCookie(HttpServletResponse response, String key, String value, int expiry) {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		cookie.setMaxAge(expiry);
		response.addCookie(cookie);
	}

	/**
	 * 删除cookie
	 * @param key
	 * @return
	 */
	public static boolean deleteCookie(HttpServletResponse response, String key) {
		if (!StringUtils.isBlank(key)) {
			Cookie cookie = getCookie(key);
			if (cookie != null) {
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
				return true;
			}
		}
		log.debug("delete cookie faild");
		return false;
	}

}