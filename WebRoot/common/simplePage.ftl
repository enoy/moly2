<#-- header -->
<#macro header title="管理系统" body=""
keywords="管理系统" description="管理系统" author="gary">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${title}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keywords" content="${keywords}"/>
	<meta name="description" content="${description}" /> 
	<meta name="author" content="${author}" />
	<meta name="robots" content="/robots.txt" />
	<link rel="shortcut icon" href="/favicon.ico" />
	<#nested>
</head>
<body ${body}>
</#macro>

<#-- footer -->
<#macro footer>
<#nested>
</body>
</html>
</#macro>