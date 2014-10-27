<@p.header navTitle="角色管理-修改角色">
<script type="text/javascript">
function verifyName(){
	var roleName = $.trim($("#roleName").val());
	if(roleName == ""){
		$("#msg").html("<div class=\"inputerror\">角色名不能为空</div>");
	}else{
		var params = {
			"roleName": roleName,
			"exceptId": $("#roleId").val()
	 	};
    	$.getJSON("${base}SysRole/verifyName.html", params, function(data){
    		$("#msg").html(data.returnMsg);
    	});
	}
}
</script>
</@p.header>

<form action="${base}SysRole/doUpdate.html" method="post">
<@form.hidden id="roleId" path="sysRole.id" />
<@form.hidden id="enable" path="sysRole.enable" />
	<div class="singleTableContainer">
	  <table width="100%" cellpadding="0" cellspacing="1" class="singleTable">
		<tr>
		  <td width="15%" class="txtlabel"><span class="red12">*</span>角色名称：</td>
		  <td width="85%" class="txtcont">
		  	<@form.input path="sysRole.roleName" id="roleName" reg="^.+$" tip="请输入角色名称" class="textinput" onblur="verifyName()"/>
		  	<span id="msg"></span>
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel"><span class="red12">*</span>角色说明：</td>
		  <td class="txtcont">
		  	<@form.textarea path="sysRole.roleDesc" id="roleDesc" class="textinput"/>
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