<@p.header navTitle="网盘管理-修改文件"/>

<form action="${base}SysNetdisk/doUpdate.html" method="post">
<@form.hidden id="netdiskId" path="sysNetdisk.id" />
	<div class="singleTableContainer">
	  <table width="100%" cellpadding="0" cellspacing="1" class="singleTable">
		<tr>
		  <td width="15%" class="txtlabel"><span class="red12">*</span>文件名称：</td>
		  <td width="85%" class="txtcont">
		  	<@form.input path="sysNetdisk.fileName" id="fileName" reg="^.+$" tip="请输入文件名" class="textinput"/>
		  	<span id="msg"></span>
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">文件说明：</td>
		  <td class="txtcont">
		  	<@form.input path="sysNetdisk.fileDesc" id="fileDesc" class="textinput"/>
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