<@p.header navTitle="用户管理-新建用户">
<script type="text/javascript">
function verifyName(){
	var userName = $.trim($("#userName").val());
	if(userName == ""){
		$("#msg").html("<div class=\"inputerror\">用户名不能为空</div>");
	}else{
		var params = {
			"userName": userName
	 	};
    	$.getJSON("${base}SysUser/verifyName.html", params, function(data){
    		$("#msg").html(data.returnMsg);
    	});
	}
}
</script>
</@p.header>

<form action="${base}SysUser/doSave.html" method="post">
	<div class="singleTableContainer">
	  <table width="100%" cellpadding="0" cellspacing="1" class="singleTable">
		<tr>
		  <td width="15%" class="txtlabel"><span class="red12">*</span>登录名：</td>
		  <td width="85%" class="txtcont">
		  	<@form.input path="sysUser.userName" id="userName" reg="^.+$" tip="请输入登录名" class="textinput" onblur="verifyName()"/>
		  	<span id="msg"></span>
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel"><span class="red12">*</span>登录密码：</td>
		  <td class="txtcont">
		  	<@form.password path="sysUser.password" id="password" reg="^.+$" tip="请输入登录密码" class="textinput"/>
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel"><span class="red12">*</span>用户名：</td>
		  <td class="txtcont">
		  	<@form.input path="sysUser.nickName" id="nickName" reg="^.+$" tip="请输入用户名" class="textinput"/>
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">&nbsp;</td>
		  <td class="txtcontbtn">
			<input type="submit" value="保存" class="but_big"/>
			&nbsp;&nbsp;
			<input type="button" value="返回" class="but_big_cancel" onclick="goback();"/>
		  </td>
		</tr>
	  </table>
	</div>
	</form>
<@p.footer/>