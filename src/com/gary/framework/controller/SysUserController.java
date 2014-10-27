package com.gary.framework.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.gary.framework.core.IsManageController;
import com.gary.framework.entity.SysUser;
import com.gary.framework.util.MD5;
import com.gary.framework.util.WebUtil;

/**
 * 用户管理
 * @author ztb
 * 2014-2-17 上午10:27:55
 *
 */
@Controller
public class SysUserController extends BaseController implements IsManageController {
	
	private static final String REDIRECT_TO_LIST = "redirect:/SysUser/list.html";
	
	/**
	 * 显示修改密码
	 * @param request
	 * @param response
	 * @param sysUser
	 * @return
	 */
	public ModelAndView toUpdatePass(HttpServletRequest request, 
			HttpServletResponse response, SysUser sysUser) {
		sysUser.setId(WebUtil.getLoginUserId());
		return new ModelAndView("/manage/updatePass", "sysUser", sysUser);
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @param response
	 * @param sysUser
	 * @return
	 */
	public String updatePass(HttpServletRequest request, 
			HttpServletResponse response, SysUser sysUser) {
		sysUser.setPassword(new MD5().getMD5(sysUser.getPassword()));
		getServiceManager().getSysUserService().updatePass(sysUser);
		return "redirect:/Manage/main.html";
	}
	
	/**
	 * 检查用户名userName
	 * @return
	 * @throws Exception
	 */
	public ModelAndView verifyName(HttpServletRequest request, 
			HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String exceptId = request.getParameter("exceptId");
		SysUser sysUser = getServiceManager().getSysUserService()
				.getUserByUserName(userName, exceptId);
		String returnMsg;
		if(sysUser == null){
			returnMsg = "<div class=\"inputcorrect\">用户名可以使用</div>";
		}else{
			returnMsg = "<div class=\"inputerror\">用户名已存在</div>";
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("returnMsg", returnMsg);
		return returnJsonView(m);
	}

	/**
	 * 用户列表
	 * @author ztb
	 * 2014-2-17 上午10:36:14
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/manage/SysUser/list");
		String userName = request.getParameter("userName");
		String nickName = request.getParameter("nickName");
		setPagingList(getServiceManager().getSysUserService().getPaging(userName, nickName));
		request.setAttribute("userName", userName);
		request.setAttribute("nickName", nickName);
		return mav;
	}

	/**
	 * 显示添加用户页面
	 * @author ztb
	 * 2014-2-17 上午10:36:08
	 * @param request
	 * @param response
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response, SysUser sysUser) {
		return new ModelAndView("/manage/SysUser/save", "sysUser", sysUser);
	}

	/**
	 * 添加用户
	 * @author ztb
	 * 2014-2-17 上午10:37:32
	 * @param request
	 * @param response
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	public ModelAndView doSave(HttpServletRequest request,
			HttpServletResponse response, SysUser sysUser) {
		SysUser dbSysUser = getServiceManager().getSysUserService()
				.getUserByUserName(sysUser.getUserName(), null);
		if(dbSysUser != null){
			addReturnMsg("操作失败,用户名已存在");
		}else{
			sysUser.setEnable(Integer.valueOf(1));
			sysUser.setPassword(new MD5().getMD5(sysUser.getPassword()));
			getServiceManager().getSysUserService().save(sysUser);
			addReturnMsg(MESSAGE_ADD_SUCCESS);
		}
		return new ModelAndView(REDIRECT_TO_LIST);
	}

	/**
	 * 删除用户
	 * @author ztb
	 * 2014-2-17 上午10:39:11
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {
		getServiceManager().getSysUserService().deleteById(getId(request));
		addReturnMsg(MESSAGE_DELETE_SUCCESS);
		return new ModelAndView(REDIRECT_TO_LIST);
	}
	
	/**
	 * 批量删除用户
	 * @author ztb
	 * 2014-2-17 上午10:39:24
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView batchDelete(HttpServletRequest request,
			HttpServletResponse response) {
		getServiceManager().getSysUserService().deleteById(getIds());
		addReturnMsg(MESSAGE_BATCH_DELETE_SUCCESS);
		return new ModelAndView(REDIRECT_TO_LIST);
	}

	/**
	 * 修改用户界面
	 * @author ztb
	 * 2014-2-17 上午10:39:40
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView update(HttpServletRequest request,
			HttpServletResponse response) {
		SysUser sysUser = getUserById(request);
		sysUser.setPassword(null);
		return new ModelAndView("/manage/SysUser/update", "sysUser", sysUser);
	}

	/**
	 * 修改用户
	 * @author ztb
	 * 2014-2-17 上午10:41:11
	 * @param request
	 * @param response
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	public ModelAndView doUpdate(HttpServletRequest request,
			HttpServletResponse response, SysUser sysUser) {
		SysUser dbSysUser = getServiceManager().getSysUserService()
				.getUserByUserName(sysUser.getUserName(), String.valueOf(sysUser.getId()));
		if(dbSysUser != null){
			addReturnMsg("操作失败,用户名已存在");
		}else{
			sysUser.setPassword(new MD5().getMD5(sysUser.getPassword()));
			getServiceManager().getSysUserService().update(sysUser);
			addReturnMsg(MESSAGE_UPDATE_SUCCESS);
		}
		return new ModelAndView(REDIRECT_TO_LIST);
	}
	
	/**
	 * 查看用户详细信息
	 * @author ztb
	 * 2014-2-17 上午10:43:28
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response) {
		SysUser sysUser = getUserById(request);
		ModelAndView mav = new ModelAndView("/manage/SysUser/detail");
		mav.addObject("sysUser", sysUser);
		return mav;
	}
	
	/**
	 * 根据ID参数获取用户
	 * @author ztb
	 * 2014-2-17 上午10:44:28
	 * @param request
	 * @return
	 */
	private SysUser getUserById(HttpServletRequest request){
		return getServiceManager().getSysUserService().getById(getId(request));
	}
}
