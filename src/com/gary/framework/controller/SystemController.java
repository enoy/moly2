package com.gary.framework.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

/**
 * 系统
 * @author gary
 *
 */
@Controller
public class SystemController extends BaseController {

	/**
	 * 系统信息
	 * @param request
	 * @param response
	 * @return
	 */
	public String info(HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("si", getServiceManager().getSystemService().getSystemInfo());
		request.setAttribute("mi", getServiceManager().getSystemService().getMemeryInfo());
		return "/manage/systemInfo";
	}
	
}
