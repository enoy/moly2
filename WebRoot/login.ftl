<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>管理系统</title>
	<meta name="robots" content="noindex,nofollow">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<link rel="shortcut icon" href="${base }favicon.ico" />
	<#if useCaptcha>
		<link href="${base }css/login.css" rel="stylesheet" type="text/css" />
	<#else>
		<link href="${base }css/login_noCaptcha.css" rel="stylesheet" type="text/css" />
	</#if>
	<link href="${base }css/easyvalidate.css" rel="stylesheet" type="text/css" />
	<link href="${base }css/jquery.gritter.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${base }js/jquery.js" ></script>
	<script type="text/javascript" src="${base }js/easyvalidator.js" ></script>
	<script language="javascript" src="${base }js/jquery.gritter.js"></script>

	<script type="text/javascript" language="javacript">
	<#if useCaptcha>
	function refreshCaptcha(){
		$("#captchaImg").attr("src","${base }captcha.jsp?t="+new Date().getTime());
	}
	function showCaptcha(){
		$("#login_captcha_img").css("display","block");
	}
	</#if>
	<#if (returnMsg?exists)>
		$(document).ready(function(){
			alertMsg('${returnMsg}');
		});
	</#if>
		function alertMsg(msg){
			$.gritter.add({
				title	: '提示信息',
				text	:  msg,
				image	: '${base }images/success.png',
				sticky	: false, 
				time	: '3000'
			});
		}
	</script>
</head>

<body>
<div id="main">
	<div id="login">
		<div id="login_form">
			<form name="LoginForm" id="LoginForm" action="${base }doLogin.html" method="post">
				<div id="login_errormsg"></div>
            	<div id="login_form_username">
                	用户名 
                	<#if useCaptcha>
	                	<@form.input path="sysUser.userName" id="userName" reg="^.+$" tip="请输入用户名" onfocus="showCaptcha();"/>
                	<#else>
	                	<@form.input path="sysUser.userName" id="userName" reg="^.+$" tip="请输入用户名"/>
                	</#if>
                </div>
                <div id="login_form_password">
                	密&nbsp;&nbsp;&nbsp;码 
                	<#if useCaptcha>
	                	<@form.password path="sysUser.password" id="password"  reg="^.+$" tip="请输入密码" 
	                		cssStyle="*width:150px;"  onfocus="showCaptcha();"/>
                	<#else>
	                	<@form.password path="sysUser.password" id="password"  reg="^.+$" tip="请输入密码" cssStyle="*width:150px;"/>
                	</#if>
                </div>
                <#if useCaptcha>
               	<div id="login_captcha_img">
               		<img id="captchaImg" src="${base }captcha.jsp" alt="点击更换" border="0px" onclick="refreshCaptcha()" />
               	</div>
                <div id="login_captcha">
                	验证码 <input type="text" id="captcha" name="captcha" reg="^.+$" tip="请输入验证码" style="width:60px;"/>
                </div>
                </#if>
                <div id="login_form_submit">
                	<input type="submit" id="submitButton" name="submitButton" value="登&nbsp;&nbsp;陆" class="button" />
                </div>
			</form>
        </div>
	</div>
	<div id="footer"></div>
</div>
</body>
</html>