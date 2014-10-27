<@simple.header title="系统异常"/>
<center>
<h1>系统错误</h1>
<div id="info">${exceptionInfo?default("未知错误")}</div>
</center>
<@simple.footer/>
<#--
<@simple.header title="系统异常" body="bgcolor='#E8E8E8'">
<script type="text/javascript" src="${base }js/jquery.js"></script>
<script type="text/javascript" src="${base }js/animatedcollapse.js"></script>
<style type="text/css">
	.detail{
		color:#344B50;
		display:none;
	}
	body{
		font-family:"微软雅黑";
	}
</style>
<script type="text/javascript">
	animatedcollapse.addDiv('detail', 'fade=1,speed=500')
	animatedcollapse.init()
</script>
﻿</@simple.header>
<center>
<h1>出错了</h1>
<div id="info">${exceptionInfo?default("未知错误")}</div>
<div id="btn" style="margin-top:5px;margin-bottom:5px;">
<a href="#" rel="toggle[detail]" data-openimage="${base }images/expand.gif" data-closedimage="${base }images/collapse.gif">
	<img src="${base }images/collapse.gif" style="border:0px;" /></a>
</div>
<div id="detail" class="detail"><textarea id="exceptionInfo" rows="15" cols="80">${exceptionInfoDetail?default("无")}</textarea></div>
</center>
<@simple.footer/>
-->