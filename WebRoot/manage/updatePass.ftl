<@p.header navTitle="修改密码"/>
<script language="javascript">
	var flagA = false;
	var flagB = false;
	function checkPassA(){
		var passA = document.getElementById("passA").value;
		if(passA == ""){
			flagA = false;
			document.getElementById("passAMsg").innerHTML="新密码不能为空";
		}else{
			flagA = true;
			document.getElementById("passAMsg").innerHTML="";
		}
	}

	function checkPassB(){
		var passB = document.getElementById("passB").value;
		if(passB == ""){
			flagB = false;
			document.getElementById("passBMsg").innerHTML="确认密码不能为空";
		}else if(document.getElementById("passA").value != passB){
			flagB = false;
			document.getElementById("passBMsg").innerHTML="两次输入密码不一致";
		}else{
			flagB = true;
			document.getElementById("passBMsg").innerHTML="";
		}
	}
	function checkForm(){
		if(flagA && flagB){
			return true;
		}else{
			return false;
		}
	}
</script>
<form action="${base}SysUser/updatePass.html" id="listform" method="post" onsubmit="return checkForm()">
<@form.hidden path="sysUser.id" id="userId"/>
<div class="singleTableContainer">
  <table width="100%" cellpadding="0" cellspacing="1" class="singleTable">
	<tr>
	  <td width="15%" class="txtlabel"><span class="red12">*</span>请输入新密码：</td>
	  <td width="85%" class="txtcont">
		<@form.password id="passA" path="sysUser.password" class="textinput" onblur="checkPassA()"/>
		<div id="passAMsg" class="inputerror"></div>
	  </td>
	</tr>
	<tr>
	  <td class="txtlabel"><span class="red12">*</span>确认密码：</td>
	  <td class="txtcont">
	  	<input type="password" id="passB" name="passB" class="textinput" onblur="checkPassB()"/>
		<div id="passBMsg" class="inputerror"></div>
	  </td>
	</tr>
	<tr>
	  <td class="txtlabel">&nbsp;</td>
	  <td class="txtcontbtn">
		<input type="submit" value="修改" class="but_big"/>
		&nbsp;&nbsp;
		<input type="reset" value="重 置" class="but_big_cancel" onclick="goback();"/>
	  </td>
	</tr>
  </table>
</div>
</form>
<@p.footer/>