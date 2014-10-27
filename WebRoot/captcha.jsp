<%@ page contentType="image/jpeg"%>
<%@ page import="com.gary.framework.util.RandImgCreater"%>
<%
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);

	RandImgCreater rc = new RandImgCreater(response);
	String captcha = rc.createRandImage();
	session.setAttribute("frameworkCaptcha",captcha);
	
	response.flushBuffer();
	out.clear();
	out = pageContext.pushBody();
%>
