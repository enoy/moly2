package com.gary.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContext;

import com.gary.framework.core.Constants;
import com.gary.framework.entity.SysUser;

/**
 * Web工具
 * @author gary
 *
 */
public class WebUtil implements ServletContextAware, InitializingBean{
	
	private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);
	
	private static ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		WebUtil.servletContext = servletContext;
	}
	
	public static ServletContext getServletContext(){
		return servletContext;
	}
	
	/**
	 * 从Spring获取Bean
	 * @param beanName
	 * @param request
	 * @return
	 */
	public static Object getBean(String beanName){
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		return ctx.getBean(beanName);
	}
	
	/**
	 * 获取HttpServletRequest
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return sra == null ? null : sra.getRequest();
	}
	
	/**
	 * 获取HttpSession
	 * @return
	 */
	public static HttpSession getSession() {
		HttpServletRequest request = getRequest();
		return request == null ? null : request.getSession(true);
    }
	
	/**
	 * 从request获取参数
	 * @param key
	 * @return
	 */
	public static String getParamFromRequest(String key){
		return getRequest().getParameter(key);
	}
	
	/**
	 * 获取登陆用户
	 * @return
	 */
	public static SysUser getLoginSysUser(){
    	return (SysUser) getSession().getAttribute(Constants.SESSION_LOGIN_USER);
    }
    
    /**
     * 获取登陆用户Id
     * @return
     */
    public static int getLoginUserId(){
    	SysUser u = getLoginSysUser();
    	return u == null ? 0 : u.getId();
    }

	/**
	 * 获取项目根目录绝对路径
	 * @return
	 */
	public static String getProjectPath(){
		String root = RequestContext.class.getResource("/").getFile();
		try {
			root = URLDecoder.decode(root, "UTF-8");
			return new File(root).getParentFile().getParentFile().getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 获取绝对路径
	 * @param file
	 * @return
	 */
	public static String getRealPath(String file){
		return servletContext.getRealPath(file);
	}
	

	/**
	 * 文件下载
	 * @param request
	 * @param response
	 * @param file
	 * @param fileName
	 * @throws IOException
	 */
	public static void downloadFile(HttpServletRequest request,
			HttpServletResponse response, File file, String fileName) throws IOException{
		logger.debug("download file:" + file.getAbsolutePath());
		
		byte[] b = new byte[1024];
		int i = 0;
		FileInputStream fis = new FileInputStream(file);
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + 
				new String(fileName.getBytes("GBK"),"ISO-8859-1") + "\"");
		response.setHeader("Content-Length", "" + file.length());
		OutputStream os = response.getOutputStream();
		while ((i = fis.read(b)) != -1) {
			os.write(b, 0, i);
			i = 0;
		}
		os.close();
		fis.close();
	}
	

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug("webUtil init success");
	}
}
