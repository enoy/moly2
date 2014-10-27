package com.gary.framework.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.gary.framework.core.Constants;
import com.gary.framework.dao.SysUserDAO;
import com.gary.framework.entity.SysUser;
import com.gary.framework.util.Paging;
import com.gary.framework.util.WebUtil;

/**
 * 系统用户service
 * @author gary
 *
 */
@Service
public class SysUserService extends BaseService{

	/**
	 * 验证码
	 * @return
	 */
	public boolean verifyCaptcha() {
		if(Constants.ENABLE_CAPTCHA){
			String sessionCaptcha = (String) WebUtil.getSession().getAttribute("frameworkCaptcha");
			String inputCaptcha = WebUtil.getRequest().getParameter("captcha");
			
			if(inputCaptcha == null || sessionCaptcha == null){
				return false;
			}
			
			if(Constants.CAPTCHA_IGNORE_CASE){
				return sessionCaptcha.equalsIgnoreCase(inputCaptcha);
			}else{
				return sessionCaptcha.equals(inputCaptcha);
			}
		}else{
			return true;
		}
	}

	/**
	 * 保存用户
	 * @param sysUser
	 */
	public void save(SysUser sysUser) {
		getDaoManager().getSysUserDAO().save(sysUser);
	}

	/**
	 * 删除用户
	 * @param id
	 */
	public void deleteById(Integer id) {
		getDaoManager().getSysUserDAO().deleteById(id);
	}

	/**
	 * 批量删除用户
	 * @param ids
	 */
	public void deleteById(Integer[] ids) {
		if(ids != null){
			SysUserDAO sysUserDAO = getDaoManager().getSysUserDAO();
			for (int i = 0; i < ids.length; i++) {
				sysUserDAO.deleteById(ids[i]);
			}
		}
	}

	/**
	 * 根据ID查找用户
	 * @param id
	 * @return
	 */
	public SysUser getById(Integer id) {
		return getDaoManager().getSysUserDAO().getById(id);
	}

	/**
	 * 修改用户
	 * @param sysUser
	 */
	public void update(SysUser sysUser) {
		if(StringUtils.isEmpty(sysUser.getLastLoginIp())){
			sysUser.setLastLoginIp(null);
		}
		if(StringUtils.isEmpty(sysUser.getLastLoginTime())){
			sysUser.setLastLoginTime(null);
		}
		getDaoManager().getSysUserDAO().update(sysUser);
	}

	/**
	 * 根据用户名查找用户
	 * @param userName
	 * @param exceptId
	 * @return
	 */
	public SysUser getUserByUserName(String userName, String exceptId) {
		return getDaoManager().getSysUserDAO().getUserByUserName(userName, exceptId);
	}

	/**
	 * 搜索用户
	 * @param userName
	 * @param nickName
	 * @return
	 */
	public Paging getPaging(String userName, String nickName) {
		return getDaoManager().getSysUserDAO().getPaging(userName, nickName, 
				getPageNum(), getPageSize());
	}

	/**
	 * 修改密码
	 * @param sysUser
	 */
	public void updatePass(SysUser sysUser) {
		getDaoManager().getSysUserDAO().updatePass(sysUser);
	}
	
}
