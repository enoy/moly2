package com.gary.framework.util.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gary.framework.core.Anonymous;
import com.gary.framework.core.Constants;
import com.gary.framework.entity.SysUser;
import com.gary.framework.util.exception.NeedLoginException;

/**
 * Spring MVC拦截器
 * @author ztb
 * 2014-2-11 下午4:10:08
 *
 */
public class CommonInterceptor extends HandlerInterceptorAdapter implements InitializingBean{
	
	private static Log logger = LogFactory.getLog(CommonInterceptor.class);
	
	/**
	 * 在系统启动时执行
	 */
	public void afterPropertiesSet() throws Exception {
		logger.debug("=======初始化CommonInterceptor拦截器=========");
	}
	
	/**
	 * 在Controller方法前进行拦截
	 * 如果返回false
	 * 		从当前拦截器往回执行所有拦截器的afterCompletion方法,再退出拦截器链.
	 * 如果返回true
	 * 		执行下一个拦截器,直到所有拦截器都执行完毕.
	 * 		再运行被拦截的Controller.
	 * 		然后进入拦截器链,从最后一个拦截器往回运行所有拦截器的postHandle方法.
	 * 		接着依旧是从最后一个拦截器往回执行所有拦截器的afterCompletion方法.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if(handler instanceof Anonymous){
			//匿名
			return true;
		}else{
			if(Constants.ENABLE_LOGIN_INTERCEPTOR){
				SysUser users = (SysUser) request.getSession().getAttribute(Constants.SESSION_LOGIN_USER); 
				if(users == null){
					if(logger.isDebugEnabled()){
						logger.debug("need login to visit " + handler.getClass());
					}
					throw new NeedLoginException();
				}
			}
			return true;
		}
	}
	
	/**
	 * 在Controller执行完毕后调用，此时Controller仅返回了ModelAndView对象，
	 * 还没有对视图进行渲染，在这个方法中有机会对ModelAndView进行修改
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}
	
	/**
	 * 在Controller方法后进行拦截
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有拦截器的afterCompletion方法
	 */
	@Override
	public void afterCompletion(HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse, Object obj,
			Exception exception) throws Exception {

	}

}
