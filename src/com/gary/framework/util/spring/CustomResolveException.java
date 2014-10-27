package com.gary.framework.util.spring;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.gary.framework.util.exception.NeedLoginException;
import com.gary.framework.util.exception.NoPermissionException;

/**
 * 自定义异常处理
 * @author gary
 *
 */
public class CustomResolveException extends SimpleMappingExceptionResolver {
	
	private Log logger = LogFactory.getLog(getClass());
	 
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception) {
		if(exception instanceof NeedLoginException){
			return new ModelAndView("redirect:/invalid.html");
		}else if(exception instanceof NoPermissionException){
			return new ModelAndView("redirect:/noPermission.html");
		}
		if(exception != null){
			logger.error("Handle exception: " + exception.getClass().getName());
			logger.error("exception detail:", exception);
		}else{
			logger.error("Handle unknown exception: " + exception);
		}
		
		ModelAndView mav = new ModelAndView("/manage/exception");
		StringWriter sw = new StringWriter();
		exception.printStackTrace(new PrintWriter(sw));
		mav.addObject("exceptionInfoDetail",sw.toString());
		
		if(exception instanceof NumberFormatException){
			mav.addObject("exceptionInfo", "数字格式错误");
		} else if (exception instanceof ArrayIndexOutOfBoundsException){
			mav.addObject("exceptionInfo", "数组下标越界");
		} else if (exception instanceof FileNotFoundException){
			mav.addObject("exceptionInfo", "找不到文件");
		} else if (exception instanceof ClassCastException){
			mav.addObject("exceptionInfo", "类型转换异常");
		} else if (exception instanceof SecurityException){
			mav.addObject("exceptionInfo", "安全异常");
		} else if (exception instanceof SQLException){
			mav.addObject("exceptionInfo", "数据库异常");
		} else if (exception instanceof IOException){
			mav.addObject("exceptionInfo", "输入输出异常");
		} else if (exception instanceof NullPointerException){
			mav.addObject("exceptionInfo", "空指针异常");
		} else if (exception instanceof NoSuchMethodException){
			mav.addObject("exceptionInfo", "未找到方法异常");
		} else {
			mav.addObject("exceptionInfo", "系统错误");
		}
		return mav;
	}
	
}
