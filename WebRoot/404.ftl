<@simple.header title="页面未找到" body="onload='count()'">
<script language="javascript">
	var bar = 0;
	var line = "||";
	var amount = "||";
	count();
	function count(){
		bar = bar + 2;
		amount = amount + line;
		document.loading.chart.value = amount;
		document.loading.percent.value = bar + "%";
		if (bar < 99){
			setTimeout("count()",100);
		}else {
			window.location = "/";
		}
	}
</script>
<style type="text/css">
<!--
body {
	font-size: 13px;
	filter: progid:DXImageTransform.Microsoft.Gradient(gradientType=0,startColorStr=#dbebfa,endColorStr=#f9fcfd);
	margin: 0px;
	font-family:"微软雅黑";
	color:#000;
}
a{
	color:#000;
}
.chart{
	font-weight:bolder; 
	background-color:white;
	padding:0px;
	border-style:none;
}
.percent{
	text-align:center;
	border-width:medium; 
	border-style:none;
}
</style>
</@simple.header>


<form name="loading">
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
<tr>
    <td align="center">
    	<img src="/images/404.jpg" alt="404"/>
    </td>
</tr>
<tr>
	<td align="center"><span style="font-size:20px;color:#03F"><b>您访问的页面不存在</b></span></td>
</tr>
<tr>
	<td align="center">稍后自动跳转到 <a href="/">主页</a>.......或 <a href="javascript:history.go(-1)">返回上一页</a></td>
</tr>
<tr>
	<td align="center">有问题请联系管理员</td>
</tr>
<tr>
	<td align="center"><input type="text" name="chart" size="50" class="chart" /></td>
</tr>
<tr>
	<td align="center"><input type=text name="percent" size="50" class="percent" /></td>
</tr>
</table>
</form>
<@simple.footer/>