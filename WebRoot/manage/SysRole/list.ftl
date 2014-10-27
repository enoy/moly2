<@p.header4content navTitle="角色管理" toggleSearch=true/>
<form action="${base}SysRole/list.html" id="listform" method="post">
	<@ui.searchBar>
	<tr>
	  <td class="txtlabel" width="45%">
		角色名称：
	  </td>
	  <td class="txtcont" width="55%">
		<input class="textinput" type="text" name="roleName" value="${roleName?default('') }"/>
	  </td>
	</tr>
	<tr>
	  <td class="txtlabel">
		角色说明：
	  </td>
	  <td class="txtcont">
		<input class="textinput" type="text" name="roleDesc" value="${roleDesc?default('') }"/>
	  </td>
	</tr>
	</@ui.searchBar>

	
	<@ui.ctrlBar>
	<input type="button" value="新建角色" class="but" onclick="gotoUrl('${base}SysRole/save.html')"/>
	</@ui.ctrlBar>

	<table cellpadding="2" cellspacing="0" class="mytab">
	<tr>
		<th width="40">序号</th>
		<th width="20%">角色名称</th>
		<th>角色说明</th>
		<th width="20%">操作</th>
	</tr>
	<#if (pagingList?exists) && (pagingList.list?exists)>
	<#list pagingList.list as u>
	<tr>
		<td>${u_index + 1 + (pagingList.pageNum-1) * pagingList.pageSize}</td>
		<td>${u.roleName?default("无")}</td>
		<td>${u.roleDesc?default("无")}</td>
		<td>
			<a href = "${base}SysRole/userList.html?roleId=${u.id}" title="用户">用户</a>
			&nbsp;|&nbsp;
			<a href = "${base}SysRole/permission.html?roleId=${u.id}" title="权限">权限</a>
			&nbsp;|&nbsp;
			<a href = "${base}SysRole/detail.html?id=${u.id}" title="详细">详细</a>
			&nbsp;|&nbsp;
			<a href = "${base}SysRole/update.html?id=${u.id}" title="修改">修改</a>
			&nbsp;|&nbsp;
			<a href = "#" title="删除" onclick="confirmDelete('${base}SysRole/delete.html?id=${u.id}')">删除</a>
		</td>
	</tr>
	</#list>
	</#if>
	</table>
	
	<@p.paging pagingList/>
</form>
<@p.footer/>