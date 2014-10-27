<@p.header navTitle="网盘管理-查看文件"/>
	<div class="singleTableContainer">
	  <table width="100%" cellpadding="0" cellspacing="1" class="singleTable">
		<tr>
		  <td width="15%" class="txtlabel"><span class="red12">*</span>文件名：</td>
		  <td width="85%" class="txtcont">
		  	${sysNetdisk.fileName?default("无")}
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">文件说明：</td>
		  <td class="txtcont">
		  	${sysNetdisk.fileDesc?default("无")}
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">文件大小：</td>
		  <td class="txtcont">
		  	${sysNetdisk.fileSize?default("无")}
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">下载次数：</td>
		  <td class="txtcont">
		  	${sysNetdisk.fileHit?default("0")}
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">上传时间：</td>
		  <td class="txtcont">
		  	${sysNetdisk.uploadTime?datetime}
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