package com.gary.framework.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.gary.framework.service.ServiceManager;
import com.gary.framework.util.Paging;
import com.gary.framework.util.ResponseUtils;
import com.gary.framework.util.URLUtil;
import com.gary.framework.util.WebUtil;

/**
 * 公共Controller
 * @author gary
 *
 */
public class BaseController extends MultiActionController  {
	
	@Autowired
	private ServiceManager serviceManager;
	
	protected static final String PAGING_LIST = "pagingList";
	
	public void setServiceManager(ServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}

	public ServiceManager getServiceManager() {
		return serviceManager;
	}
	
	public Integer getId(HttpServletRequest request){
		String id = request.getParameter("id");
		if(StringUtils.isBlank(id)){
			return Integer.valueOf(0);
		}else{
			try{
				return Integer.parseInt(id);
			}catch(Exception e){
				logger.debug("parse2Int id faild,id:" + id, e);
				return Integer.valueOf(0);
			}
		}
	}
	
//	public Integer getId(){
//		return getId(WebUtil.getRequest());
//	}
	
	public String getStrId(HttpServletRequest request){
		String id = request.getParameter("id");
		if(StringUtils.isBlank(id)){
			return null;
		}else{
			return id;
		}
	}
	
	public String getStrId(){
		return getStrId(WebUtil.getRequest());
	}
	
	/**
	 * 获取除页号外的参数
	 * @return
	 */
	public String getParamStringWithoutPageNum(HttpServletRequest request) {
		Map<String, Object> m = new HashMap<String, Object>(); 
		m = URLUtil.getParamMap(request.getQueryString());
		if(!m.isEmpty()){
			m.remove("pageNum");
		}
		return URLUtil.getParamString2(m);
	}
    
	/**
	 * 设置分页数据
	 * @param pagingList
	 */
	public void setPagingList(Paging pagingList){
		HttpServletRequest request = WebUtil.getRequest();
		//设置分页数据
		request.setAttribute(PAGING_LIST, pagingList);
		//设置提示信息
		getReturnMsg();
	}
	

	/**
	 * 类型数据绑定
	 */
	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
    	binder.registerCustomEditor(Short.class, new CustomNumberEditor(Short.class, true));
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
        binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, true));
        binder.registerCustomEditor(Float.class, new CustomNumberEditor(Float.class, true));
        binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
        binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
        binder.registerCustomEditor(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));
        
		//日期类型必须自定义格式
		binder.registerCustomEditor(java.util.Date.class, 
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),true));
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}
	
	/**
	 * 返回JSON
	 * @author ztb
	 * 2014-2-23 下午3:14:39
	 * @param model
	 * @return
	 */
	protected ModelAndView returnJsonView(Map<String, ?> model){
		return new ModelAndView("jsonView", model);
	}
	
	/**
	 * 返回JSON,使用JACKSON
	 * @author ztb
	 * 2014-2-23 下午3:17:57
	 * @param response
	 * @param obj
	 */
	protected void responseJsonByJackson(HttpServletResponse response, Object obj){
		ResponseUtils.renderJsonObject(response, obj);
	}
	
	private static final String RETURN_MSG = "returnMsg";
	
	/**
	 * 设置返回提示信息
	 * @param msg
	 */
	public void addReturnMsg(String msg){
		WebUtil.getSession().setAttribute(RETURN_MSG, msg);
	}
	
	/**
	 * 获取返回提示信息
	 */
	public void getReturnMsg(){
		HttpSession httpSession = WebUtil.getSession();
		WebUtil.getRequest().setAttribute(RETURN_MSG, (String)httpSession.getAttribute(RETURN_MSG));
		httpSession.removeAttribute(RETURN_MSG);
	}
	
	/**
	 * 获取ID列表
	 * @return
	 */
	public Integer[] getIds() {
		return getIds(WebUtil.getRequest());
	}
	
	public Integer[] getIds(HttpServletRequest request) {
		String[] idsStr = request.getParameterValues("ids");
		Integer[] ids = null;
		if(idsStr != null){
			ids = new Integer[idsStr.length];
			for (int i = 0; i < idsStr.length; i++) {
				try {
					if(idsStr[i] == null){
						ids[i] = Integer.valueOf(0);
					}else{
						ids[i] = Integer.parseInt(idsStr[i]);
					}
				} catch (Exception nfe) {
					ids[i] = Integer.valueOf(0);
				}
			}
		}
		return ids;
	}

	public String[] getStrIds() {
		return getStrIds(WebUtil.getRequest());
	}
	
	public String[] getStrIds(HttpServletRequest request) {
		return request.getParameterValues("ids");
	}
	
	/**
	 * 获取绝对路径
	 * @param request
	 * @param path
	 * @return
	 */
	public String getRealPath(HttpServletRequest request, String path){
		return request.getSession().getServletContext().getRealPath(path);
	}
	
	/**
	 * 向下传值
	 * @param request
	 * @param paramName
	 */
	protected void passParamVal(HttpServletRequest request, String paramName){
		request.setAttribute(paramName, request.getParameter(paramName));
	}
	
	/**
	 * 前台页面操作结果提示信息
	 */
	//提示 添加成功
	protected static final String MESSAGE_ADD_SUCCESS = "添加成功";
	
	//提示 删除成功
	protected static final String MESSAGE_DELETE_SUCCESS = "删除成功";
	
	//提示 批量删除成功
	protected static final String MESSAGE_BATCH_DELETE_SUCCESS = "批量删除成功";
	
	//提示 修改成功
	protected static final String MESSAGE_UPDATE_SUCCESS = "修改成功";
}
