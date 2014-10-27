package com.gary.framework.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.gary.framework.core.Anonymous;
import com.gary.framework.core.Constants;
import com.gary.framework.entity.SysUser;
import com.gary.framework.util.DateUtil;
import com.gary.framework.util.MD5;
import com.gary.framework.util.RequestUtils;

@Controller
public class IndexController extends BaseController implements Anonymous{
	
	/**
	 * 显示登陆页
	 * @param request
	 * @param response
	 * @param sysUser
	 * @return
	 */
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, 
			SysUser sysUser) {
		getReturnMsg();
		ModelAndView mav = new ModelAndView("login", "sysUser", sysUser);
		mav.addObject("useCaptcha", Constants.ENABLE_CAPTCHA);
		return mav;
	}
	
	/**
	 * 登陆成功
	 * @param sysUser
	 */
	private void loginSuccess(HttpServletRequest request, HttpServletResponse response, 
			SysUser sysUser) {
		//更新登录IP和时间
		sysUser.setLastLoginIp(RequestUtils.getIpAddr(request));
		sysUser.setLastLoginTime(DateUtil.getCurrentDateStr());

		getServiceManager().getSysUserService().update(sysUser);
		
		request.getSession().setAttribute(Constants.SESSION_LOGIN_USER, sysUser);
	}
	

	/**
	 * 用户登录
	 * @return
	 * @throws Exception
	 */
	public ModelAndView doLogin(HttpServletRequest request, HttpServletResponse response, 
			SysUser sysUser) {
		ModelAndView errorView = new ModelAndView("redirect:/login.html", "sysUser", sysUser);
		
		if(!getServiceManager().getSysUserService().verifyCaptcha()){
			addReturnMsg("验证码错误");
			return errorView;
		}else{
			if(sysUser == null 
					|| StringUtils.isBlank(sysUser.getUserName()) 
					|| StringUtils.isBlank(sysUser.getPassword())){
				addReturnMsg("用户名或密码不能为空");
				return errorView;
			}
			
			sysUser.setPassword(new MD5().getMD5(sysUser.getPassword()));
			SysUser dbUsers = getServiceManager().getSysUserService().getUserByUserName(sysUser.getUserName(), null);
			
			if(dbUsers == null 
					|| !sysUser.getPassword().equals(dbUsers.getPassword())){
				addReturnMsg("用户名或密码错误");
				return errorView;
			}else{
				loginSuccess(request, response, dbUsers);
				return new ModelAndView("manage/index");
			}
		}
	}
	
	/**
	 * 用户登出
	 * @return
	 * @throws Exception
	 */
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, 
			SysUser sysUser) {
		HttpSession session = request.getSession();
		session.removeAttribute(Constants.SESSION_LOGIN_USER);
		session.invalidate();
		return new ModelAndView("redirect:/login.html", "sysUser", sysUser);
	}
	
	/**
	 * session过期
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String invalid(HttpServletRequest request, HttpServletResponse response){
		return "/manage/invalid";
	}
	
	/**
	 * 没有权限
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String noPermission(HttpServletRequest request, HttpServletResponse response){
		return "/manage/noPermission";
	}
}
