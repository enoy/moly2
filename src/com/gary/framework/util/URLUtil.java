package com.gary.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * URL工具
 * @author gary
 *
 */
public class URLUtil {

	/**
	 * 对url进行编码
	 * @param url
	 * @return
	 */
	public static String encodeURL(String url) {
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 对url进行解码
	 * @param url
	 * @return
	 */
	public static String decodeURL(String url){
		try {
			return URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 两次decode
	 * @param url
	 * @return
	 */
	public static String decodeURLTwice(String url){
		url = decodeURL(url);
		url = decodeURL(url);
		return url;
	}

	/**
	 * 判断URL地址是否存在
	 * @param url
	 * @return
	 */
	public static boolean isURLExist(String url) {
		try {
			URL u = new URL(url);
			HttpURLConnection urlconn = (HttpURLConnection) u.openConnection();
			int state = urlconn.getResponseCode();
			if (state == 200) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 将请求参数还原为key=value的形式
	 * @param params
	 * @return
	 */
	public static String getParamString2(Map<?, ?> params) {
		if(params.isEmpty()){
			return "";
		}else{
			StringBuffer queryString = new StringBuffer(256);
			Iterator<?> it = params.keySet().iterator();
			int count = 0;
			while (it.hasNext()) {
				String key = (String) it.next();
				String param = (String) params.get(key);
					if (count == 0) {
						count++;
					} else {
						queryString.append("&");
					}
					queryString.append(key);
					queryString.append("=");
					queryString.append((String) param);
			}
			return queryString.toString();
		}
	}
	
	/**
	 * 将请求参数还原为key=value的形式,每个参数为数组
	 * @param params
	 * @return
	 */
	public static String getParamString(Map<?, ?> params) {
		StringBuffer queryString = new StringBuffer(256);
		Iterator<?> it = params.keySet().iterator();
		int count = 0;
		while (it.hasNext()) {
			String key = (String) it.next();
			String[] param = (String[]) params.get(key);
			for (int i = 0; i < param.length; i++) {
				if (count == 0) {
					count++;
				} else {
					queryString.append("&");
				}
				queryString.append(key);
				queryString.append("=");
				try {
					queryString.append(URLEncoder.encode((String) param[i], "UTF-8"));
				} catch (UnsupportedEncodingException e) {
				}
			}
		}
		return queryString.toString();
	}


	/**
	 * 获得请求的路径及参数
	 * @param request
	 * @return
	 */
	public static String getRequestURL(HttpServletRequest request) {
		StringBuffer originalURL = new StringBuffer(request.getServletPath());
		Map<?,?> parameters = request.getParameterMap();
		if (parameters != null && parameters.size() > 0) {
			originalURL.append("?");
			originalURL.append(getParamString(parameters));
		}
		return originalURL.toString();
	}
	
	
	/**
	 * 将请求字符串转换成Map
	 * @param paramString
	 * @return
	 */
	public static Map<String, Object> getParamMap(String paramString){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//分割成数组,每个数组内key=value
		if(StringUtils.isNotBlank(paramString)){
			String[] params = StringUtils.split(paramString,'&');
			for (int i = 0; i < params.length; i++) {
				int indexOfEquals = params[i].indexOf('=');
				String key = params[i].substring(0, indexOfEquals);
				String value = "";
				if(indexOfEquals == params[i].length()){
					value = "";
				}else{
					value = params[i].substring(indexOfEquals + 1);
				}
				paramMap.put(key, value);
			}
		}
		return paramMap;
		
	}
	
	/**
	 * 将请求字符串转换成Map,除了pageNum
	 * @param paramString
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map getParamMapWithoutPageNum(String paramString){
		Map m = getParamMap(paramString);
		m.remove("pageNum");
		return m;
	}
	

	/**
	 * 抓取网页内容,自动识别编码
	 * @param urlString
	 * @return
	 */
	public static String url2Str(String urlString) {
		try {
			StringBuffer html = new StringBuffer();
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			URLConnection c = url.openConnection();
			c.connect();
			String contentType = c.getContentType();
			String characterEncoding = null;
			int index = contentType.indexOf("charset=");
			if(index == -1){
				characterEncoding = "UTF-8";
			}else{
				characterEncoding = contentType.substring(index + 8, contentType.length());
			}
	        InputStreamReader isr = new InputStreamReader(conn.getInputStream(), characterEncoding);
	        BufferedReader br = new BufferedReader(isr);
	        String temp;
	        while ((temp = br.readLine()) != null) {
	            html.append(temp).append("\n");
	        }
	        br.close();
	        isr.close();
	        return html.toString();
	     } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	     }
	 }
	
	/**
     * 连接
     * @param urlString
     * @throws MalformedURLException
     * @throws IOException 
     */
    public void connect(String urlString) throws MalformedURLException, IOException {
        URL url = new URL(urlString);
        URLConnection c = url.openConnection();
        c.connect();
    }
    
    /**
	 * 发送post请求
	 * @param url
	 * @param query
	 * @return
	 */
	public static String doPost(String url, String query) {
		String result = "";
		try {
			String param = query;
			URL u = new URL(url);
			HttpURLConnection huc = (HttpURLConnection) u.openConnection();
			huc.setDoOutput(true);
			huc.setRequestMethod("POST");
			huc.connect();
			OutputStream out = huc.getOutputStream();
			out.write(param.getBytes());
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(huc.getInputStream()));
			String line = "";
			while ((line = br.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
		 
    public static void main(String[] args) {
    	String content = URLUtil.url2Str("http://www.baidu.com");;
    	System.out.println(content);
    }
}
