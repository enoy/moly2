<@p.header4content navTitle="网盘管理" toggleSearch=true>
<@p.includeDatePicker/>
</@p.header4content>

<form action="${base}SysNetdisk/list.html" id="listform" method="post">
	<@ui.searchBar>
	<tr>
	  <td class="txtlabel" width="35%">
		文件名：
	  </td>
	  <td class="txtcont" width="65%">
		<input class="textinput" type="text" name="fileName" value="${fileName?default('') }"/>
	  </td>
	</tr>
	<tr>
	  <td class="txtlabel">
		文件说明：
	  </td>
	  <td class="txtcont">
		<input class="textinput" type="text" name="fileDesc" value="${fileDesc?default('') }"/>
	  </td>
	</tr>
	<tr>
	  <td class="txtlabel">
		上传人：
	  </td>
	  <td class="txtcont">
		<input class="textinput" type="text" name="fileOwnerNickName" value="${fileOwnerNickName?default('') }"/>
	  </td>
	</tr>
	<tr>
	  <td class="txtlabel">
		上传时间：
	  </td>
	  <td class="txtcont">
		<input class="Wdate" style="width:135px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  type="text" name="uploadTimeFrom" value="${uploadTimeFrom?default('') }"/>
		-
		<input class="Wdate" style="width:135px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" type="text" name="uploadTimeTo" value="${uploadTimeTo?default('') }"/>
	  </td>
	</tr>
	</@ui.searchBar>

	
	<@ui.ctrlBar>
	<input type="button" value="上传文件" class="but" onclick="gotoUrl('${base}SysNetdisk/save.html')"/>
	&nbsp;&nbsp;
	<input type="button" value="清理文件" class="but" onclick="gotoUrl('${base}SysNetdisk/cleanUp.html')"/>
	</@ui.ctrlBar>

	<table cellpadding="2" cellspacing="0" class="mytab">
	<tr>
		<th width="40">序号</th>
		<th>文件名</th>
		<th>文件大小</th>
		<th>下载次数</th>
		<th>上传人</th>
		<th>上传时间</th>
		<th>操作</th>
	</tr>
	<#if (pagingList?exists) && (pagingList.list?exists)>
	<#list pagingList.list as u>
	<tr>
		<td>${u_index + 1 + (pagingList.pageNum-1) * pagingList.pageSize}</td>
		<td>${u.FILE_NAME?default("无")}</td>
		<td>${u.FILE_SIZE?default("无")}</td>
		<td>${u.FILE_HIT?default("0")}</td>
		<td>${u.NICK_NAME?default("无")}</td>
		<td>${u.UPLOAD_TIME?datetime}</td>
		<td>
			<a href = "${base}SysNetdisk/download.html?id=${u.id}" title="下载">下载</a>
			&nbsp;|&nbsp;
			<a href = "${base}SysNetdisk/detail.html?id=${u.id}" title="详细">详细</a>
			&nbsp;|&nbsp;
			<a href = "${base}SysNetdisk/update.html?id=${u.id}" title="修改">修改</a>
			&nbsp;|&nbsp;
			<a href = "#" title="删除" onclick="confirmDelete('${base}SysNetdisk/delete.html?id=${u.id}')">删除</a>
		</td>
	</tr>
	</#list>
	</#if>
	</table>
	
	<@p.paging pagingList/>
</form>
<@p.footer/>