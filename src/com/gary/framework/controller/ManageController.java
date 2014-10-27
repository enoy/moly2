package com.gary.framework.controller;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import com.gary.framework.core.IsManageController;
import com.gary.framework.dto.Menu;
import com.gary.framework.util.WebUtil;

@Controller
public class ManageController extends BaseController implements IsManageController{
	
	public String top(HttpServletRequest request, HttpServletResponse response) {
		return "/manage/top";
	}
	
	public String buttom(HttpServletRequest request, HttpServletResponse response) {
		return "/manage/buttom";
	}
	
	public String left(HttpServletRequest request, HttpServletResponse response) {
		int loginUserId = WebUtil.getLoginUserId();
		List<Menu> menuList = getServiceManager().getSysRoleService().getUserMenus(loginUserId);
		request.setAttribute("menuList", menuList);
		return "/manage/left";
	}
	
	public String main(HttpServletRequest request, HttpServletResponse response) {
		return "/manage/main";
	}
	
	/**
	 * 系统信息
	 * @param request
	 * @param response
	 * @return
	 * @throws NamingException
	 * @throws SQLException
	 */
	public String systemInfo(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("si", getServiceManager().getSystemService().getSystemInfo());
		request.setAttribute("mi", getServiceManager().getSystemService().getMemeryInfo());
		return "/manage/systemInfo";
	}
	
}
