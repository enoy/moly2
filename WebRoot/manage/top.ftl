<@p.header containerDiv=false>
<link href="${base}css/jquery.gritter.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="${base}js/jquery.gritter.js"></script>
<script language="javascript">
function alertMsg(msg){
	$.gritter.add({
		title	: '提示信息',
		text	:  msg,
		image	: '${base}images/success.png',
		sticky	: false, 
		time	: '1000'
	});
}
</script>
</@p.header>

<div id="header">
	<div class="tpic">&nbsp;</div>
	<div class="date">
		<script type="text/javascript">
		var enabled = 0; today = new Date();var day; var date;
		if(today.getDay()==0){day = "星期日"};
		if(today.getDay()==1){day = "星期一"};
		if(today.getDay()==2){day = "星期二"};
		if(today.getDay()==3){day = "星期三"};
		if(today.getDay()==4){day = "星期四"};
		if(today.getDay()==5){day = "星期五"};
		if(today.getDay()==6){day = "星期六"};
		date = " 今天是 " + (today.getFullYear()) + "年" + (today.getMonth() + 1 ) + "月" + today.getDate() + "日 " + day;
		document.write(date.fontsize(2));
		</script>
		&nbsp;&nbsp;
	</div>
</div>
<div id="nav">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="30" height="38" align="center"><img src="${base }pics/kj/kjst_r2_c4_s1.jpg" width="17" height="38" /></td>
      <td width="200" align="left">欢迎您 <span class="blue12">${LOGIN_USER.nickName }</span> 登录本系统！</td>
      <td>&nbsp;</td>
      <td width="30" align="center"><img src="${base }pics/kj/kjst_r2_c22_s1.jpg" width="20" height="38" /></td>
      <td width="50" align="left"><a href="${base }Manage/main.html" target="main">返回首页</a></td>
      <td width="30" align="center"><img src="${base }pics/kj/kjst_r2_c24_s1.jpg" width="20" height="38" /></td>
      <td width="50" align="left"><a href="${base }SysUser/toUpdatePass.html" target="main">修改密码</a></td>
      <td width="30" align="center"><img src="${base }pics/kj/kjst_r2_c26_s1.jpg" alt="" width="19" height="38" /></td>
      <td width="50" align="left"><a href="#" onclick="javascript:window.top.location='${base }logout.html';">退出系统</a></td>
      <td width="10">&nbsp;</td>
    </tr>
  </table>
</div>
<@p.footer containerDiv=false/>