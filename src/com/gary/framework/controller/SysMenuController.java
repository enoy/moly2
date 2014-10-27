package com.gary.framework.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.gary.framework.core.IsManageController;
import com.gary.framework.entity.SysMenu;
import com.gary.framework.util.IntegerUtils;

/**
 * 菜单管理
 * @author gary
 *
 */
@Controller
public class SysMenuController extends BaseController implements IsManageController {

	/**
	 * 树页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
			SysMenu sysMenu){
		ModelAndView mav = new ModelAndView("/manage/SysMenu/list", "sysMenu", sysMenu);
		return mav;
	}
	
	/**
	 * 树JSON
	 * @param request
	 * @param response
	 */
	public void listData(HttpServletRequest request, HttpServletResponse response){
		String roleId = request.getParameter("roleId");
		List<Map<String, Object>> list = getServiceManager().getSysMenuService().getTreeList(roleId);
		responseJsonByJackson(response, list);
	}
	
	/**
	 * 保存或插入
	 * @param request
	 * @param response
	 * @param sysMenu
	 */
	public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response,
			SysMenu sysMenu){
		//是保存
		if(sysMenu.getId() != null 
				&& sysMenu.getId().intValue() == -1){
			sysMenu.setId(null);
			Integer newId = getServiceManager().getSysMenuService().save(sysMenu);
			sysMenu.setId(newId);
		}else{
			getServiceManager().getSysMenuService().update(sysMenu);
		}
		//最新数据
//		sysMenu = getServiceManager().getSysMenuService().getById(sysMenu.getId());
		
		responseJsonByJackson(response, sysMenu);
	}
	
	/**
	 * 移动
	 * @param request
	 * @param response
	 */
	public void move(HttpServletRequest request, HttpServletResponse response){
		String sourceIds = request.getParameter("sourceIds");
		String targetIdStr = request.getParameter("targetId");
		Integer targetId = IntegerUtils.str2int(targetIdStr);
		String moveType = request.getParameter("moveType");
		getServiceManager().getSysMenuService().move(sourceIds, targetId, moveType);
	}
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response){
		getServiceManager().getSysMenuService().deleteById(getId(request));
	}
}
