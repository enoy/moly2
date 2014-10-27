package com.gary.framework.util.spring;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.gary.framework.util.RequestUtils;

public class CustomFreemarkerView extends FreeMarkerView {
	
	/**
	 * 部署路径属性名称
	 */
	public static final String CONTEXT_PATH = "base";

	/**
	 * 在model中增加部署路径base，方便处理部署路径问题。
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void exposeHelpers(Map model, HttpServletRequest request)
			throws Exception {
		super.exposeHelpers(model, request);
//		String basePath = (String) getServletContext().getAttribute(Constants.APPLICATION_BASE_PATH);
//		if(basePath == null){
//			StringBuffer basePathBuffer = new StringBuffer();
//			basePathBuffer.append(request.getScheme()).append("://").append(request.getServerName())
//				.append(":").append(request.getServerPort())
//				.append(request.getContextPath()).append("/");
//			basePath = basePathBuffer.toString();
//			getServletContext().setAttribute(Constants.APPLICATION_BASE_PATH, basePath);
//		}
		
		model.put(CONTEXT_PATH, RequestUtils.getBasePath(request));
		
//		model.put(CONTEXT_PATH, "http://localhost:8080/" + request.getContextPath() + "/");
	}
	
}
