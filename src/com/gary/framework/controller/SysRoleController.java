package com.gary.framework.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.gary.framework.core.IsManageController;
import com.gary.framework.entity.SysRole;

/**
 * 角色管理
 * @author gary
 *
 */
@Controller
public class SysRoleController extends BaseController implements IsManageController {
	
	private static final String REDIRECT_TO_LIST = "redirect:/SysRole/list.html";
	
	/**
	 * 检查角色名
	 * @return
	 * @throws Exception
	 */
	public ModelAndView verifyName(HttpServletRequest request, 
			HttpServletResponse response) {
		String roleName = request.getParameter("roleName");
		String exceptId = request.getParameter("exceptId");
		SysRole sysRole = getServiceManager().getSysRoleService()
				.getRoleByRoleName(roleName, exceptId);
		String returnMsg;
		if(sysRole == null){
			returnMsg = "<div class=\"inputcorrect\">角色名可以使用</div>";
		}else{
			returnMsg = "<div class=\"inputerror\">角色名已存在</div>";
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("returnMsg", returnMsg);
		return returnJsonView(m);
	}

	/**
	 * 角色列表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/manage/SysRole/list");
		String roleName = request.getParameter("roleName");
		String roleDesc = request.getParameter("roleDesc");
		setPagingList(getServiceManager().getSysRoleService().getPaging(roleName, roleDesc));
		request.setAttribute("roleName", roleName);
		request.setAttribute("roleDesc", roleDesc);
		return mav;
	}

	/**
	 * 添加角色页面
	 * @param request
	 * @param response
	 * @param sysRole
	 * @return
	 */
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response, SysRole sysRole) {
		return new ModelAndView("/manage/SysRole/save", "sysRole", sysRole);
	}

	/**
	 * 添加角色
	 * @param request
	 * @param response
	 * @param sysRole
	 * @return
	 */
	public ModelAndView doSave(HttpServletRequest request,
			HttpServletResponse response, SysRole sysRole) {
		SysRole dbSysRole = getServiceManager().getSysRoleService()
				.getRoleByRoleName(sysRole.getRoleName(), null);
		if(dbSysRole != null){
			addReturnMsg("操作失败,角色名已存在");
		}else{
			sysRole.setEnable(Integer.valueOf(1));
			getServiceManager().getSysRoleService().save(sysRole);
			addReturnMsg(MESSAGE_ADD_SUCCESS);
		}
		return new ModelAndView(REDIRECT_TO_LIST);
	}

	/**
	 * 删除角色
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {
		getServiceManager().getSysRoleService().deleteById(getId(request));
		addReturnMsg(MESSAGE_DELETE_SUCCESS);
		return new ModelAndView(REDIRECT_TO_LIST);
	}
	
	/**
	 * 修改角色页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView update(HttpServletRequest request,
			HttpServletResponse response) {
		SysRole sysRole = getRoleById(request);
		return new ModelAndView("/manage/SysRole/update", "sysRole", sysRole);
	}

	/**
	 * 修改角色
	 * @param request
	 * @param response
	 * @param sysRole
	 * @return
	 */
	public ModelAndView doUpdate(HttpServletRequest request,
			HttpServletResponse response, SysRole sysRole) {
		SysRole dbSysRole = getServiceManager().getSysRoleService()
				.getRoleByRoleName(sysRole.getRoleName(), String.valueOf(sysRole.getId()));
		if(dbSysRole != null){
			addReturnMsg("操作失败,角色名已存在");
		}else{
			getServiceManager().getSysRoleService().update(sysRole);
			addReturnMsg(MESSAGE_UPDATE_SUCCESS);
		}
		return new ModelAndView(REDIRECT_TO_LIST);
	}
	
	/**
	 * 查看角色信息
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response) {
		SysRole sysRole = getRoleById(request);
		ModelAndView mav = new ModelAndView("/manage/SysRole/detail");
		mav.addObject("sysRole", sysRole);
		return mav;
	}
	
	/**
	 * 根据ID参数获取角色
	 * @param request
	 * @return
	 */
	private SysRole getRoleById(HttpServletRequest request){
		return getServiceManager().getSysRoleService().getById(getId(request));
	}

	/**
	 * 角色中的用户列表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView userList(HttpServletRequest request,
			HttpServletResponse response) {
		String roleId = request.getParameter("roleId");
		String nickName = request.getParameter("nickName");
		
		setPagingList(getServiceManager().getSysRoleService().getUserList(roleId, nickName));
		
		request.setAttribute("roleId", roleId);
		request.setAttribute("nickName", nickName);
		
		return new ModelAndView("/manage/SysRole/userList");
	}
	
	/**
	 * 不在角色中的用户列表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView availableUserList(HttpServletRequest request,
			HttpServletResponse response) {
		String roleId = request.getParameter("roleId");
		String nickName = request.getParameter("nickName");
		
		setPagingList(getServiceManager().getSysRoleService().availableUserList(roleId, nickName));

		request.setAttribute("roleId", roleId);
		request.setAttribute("nickName", nickName);
		
		return new ModelAndView("/manage/SysRole/availableUserList");
	}
	
	/**
	 * 将用户添加到角色中
	 * @param request
	 * @param response
	 * @return
	 */
	public String saveUserRole(HttpServletRequest request,
			HttpServletResponse response) {
		String roleId = request.getParameter("roleId");
		String userIds = request.getParameter("userIds");
		if(StringUtils.isNotBlank(userIds)){
			String[] userIdsArr = StringUtils.split(userIds, ',');
			getServiceManager().getSysRoleService().saveUserRole(roleId, userIdsArr);
		}
		return "redirect:/SysRole/availableUserList.html?roleId=" + roleId;
	}
	
	/**
	 * 清空角色内用户
	 * @param request
	 * @param response
	 * @return
	 */
	public String removeAllUsersFromRole(HttpServletRequest request,
			HttpServletResponse response){
		String roleId = request.getParameter("roleId");
		getServiceManager().getSysRoleService().removeAllUsersFromRole(roleId);
		addReturnMsg("清空成功");
		return "redirect:/SysRole/userList.html?roleId=" + roleId;
	}
	
	/**
	 * 将用户从角色中删除
	 * @param request
	 * @param response
	 * @return
	 */
	public String removeUserFromRole(HttpServletRequest request,
			HttpServletResponse response){
		String roleId = request.getParameter("roleId");
		String id = request.getParameter("id");
		getServiceManager().getSysRoleService().removeUserFromRole(id);
		addReturnMsg("成功将用户从角色中删除");
		return "redirect:/SysRole/userList.html?roleId=" + roleId;
	}
	
	/**
	 * 权限
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView permission(HttpServletRequest request,
			HttpServletResponse response) {
		passParamVal(request, "roleId");
		return new ModelAndView("/manage/SysRole/permission");
	}
	
	/**
	 * 保存权限
	 * @param request
	 * @param response
	 * @return
	 */
	public String savePermission(HttpServletRequest request,
			HttpServletResponse response) {
		String roleId = request.getParameter("roleId");
		String menuIds = request.getParameter("menuIds");
		getServiceManager().getSysMenuService().saveRoleMenus(roleId, menuIds);
		return REDIRECT_TO_LIST;
	}
}
