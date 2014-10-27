<@p.header4content navTitle="角色管理 - 角色内用户列表" toggleSearch=true>
<script type="text/javascript">
function addUser(){
	var diag = new Dialog();
	diag.Title = "选择用户";
	diag.URL = "${base}SysRole/availableUserList.html?roleId=${roleId}";
	diag.Width = 650;
	diag.Height = 400;
	diag.OKEvent = function(){
		diag.close();
		$("#listform").submit();
	};
	diag.show();
}
function removeAll(){
	Dialog.confirm("确认要删除所有用户吗?", function(){
		$("#listform").attr("action", "${base}SysRole/removeAllUsersFromRole.html?roleId=${roleId}").submit();
	});
}
function refreshList(){
	$("#listform").submit();
}
</script>
</@p.header4content>
<form action="${base}SysRole/userList.html" id="listform" method="post">
<input type="hidden" name="roleId" value="${roleId}"/>
	<@ui.searchBar>
	<tr>
	  <td class="txtlabel" width="45%">
		用户昵称：
	  </td>
	  <td class="txtcont" width="55%">
		<input class="textinput" type="text" name="nickName" value="${nickName?default('') }"/>
	  </td>
	</tr>
	</@ui.searchBar>

	
	<@ui.ctrlBar>
	<input type="button" value="返回" class="but" onclick="gotoUrl('${base}SysRole/list.html')"/>
	&nbsp;&nbsp;
	<input type="button" value="添加" class="but" onclick="addUser();"/>
	&nbsp;&nbsp;
	<input type="button" value="清空" class="but" onclick="removeAll();"/>
	&nbsp;&nbsp;
	<input type="button" value="刷新" class="but" onclick="refreshList();"/>
	</@ui.ctrlBar>

	<table cellpadding="2" cellspacing="0" class="mytab">
	<tr>
		<th width="40">序号</th>
		<th>用户名称</th>
		<th>用户昵称</th>
		<th>操作</th>
	</tr>
	<#if (pagingList?exists) && (pagingList.list?exists)>
	<#list pagingList.list as u>
	<tr>
		<td>${u_index + 1 + (pagingList.pageNum-1) * pagingList.pageSize}</td>
		<td>${u.USER_NAME?default("无")}</td>
		<td>${u.NICK_NAME?default("无")}</td>
		<td>
			<a href = "#" title="从角色中删除" onclick="confirmDelete('${base}SysRole/removeUserFromRole.html?id=${u.USER_ROLE_ID}&roleId=${roleId}')">从角色中删除</a>
		</td>
	</tr>
	</#list>
	</#if>
	</table>
	
	<@p.paging pagingList/>
</form>
<@p.footer/>