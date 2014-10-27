<@p.header navTitle="用户管理-查看用户"/>
	<div class="singleTableContainer">
	  <table width="100%" cellpadding="0" cellspacing="1" class="singleTable">
		<tr>
		  <td width="15%" class="txtlabel"><span class="red12">*</span>登录名：</td>
		  <td width="85%" class="txtcont">
		  	${sysUser.userName?default("无")}
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel"><span class="red12">*</span>昵称：</td>
		  <td class="txtcont">
		  	${sysUser.nickName?default("无")}
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">最后登录IP：</td>
		  <td class="txtcont">
		  	${sysUser.lastLoginIp?default("无")}
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">最后登录时间：</td>
		  <td class="txtcont">
		  	${sysUser.lastLoginTime?default("无")}
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">&nbsp;</td>
		  <td class="txtcontbtn">
			<input type="button" value="返回" class="but_big_cancel" onclick="goback();"/>
		  </td>
		</tr>
	  </table>
	</div>
	</form>
<@p.footer/>