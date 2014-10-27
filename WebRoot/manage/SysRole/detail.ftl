<@p.header navTitle="角色管理-查看角色"/>
	<div class="singleTableContainer">
	  <table width="100%" cellpadding="0" cellspacing="1" class="singleTable">
		<tr>
		  <td width="15%" class="txtlabel"><span class="red12">*</span>角色名称：</td>
		  <td width="85%" class="txtcont">
		  	${sysRole.roleName?default("无")}
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">角色说明：</td>
		  <td class="txtcont">
		  	${sysRole.roleDesc?default("无")}
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