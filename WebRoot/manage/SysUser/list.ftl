<@p.header4content navTitle="用户管理" toggleSearch=true/>
<form action="${base}SysUser/list.html" id="listform" method="post">
	<@ui.searchBar>
	<tr>
	  <td class="txtlabel" width="45%">
		用户名：
	  </td>
	  <td class="txtcont" width="55%">
		<input class="textinput" type="text" name="userName" value="${userName?default('') }"/>
	  </td>
	</tr>
	<tr>
	  <td class="txtlabel">
		昵称：
	  </td>
	  <td class="txtcont">
		<input class="textinput" type="text" name="nickName" value="${nickName?default('') }"/>
	  </td>
	</tr>
	</@ui.searchBar>

	
	<@ui.ctrlBar>
	<input type="button" value="新建用户" class="but" onclick="gotoUrl('${base}SysUser/save.html')"/>
	</@ui.ctrlBar>

	<table cellpadding="2" cellspacing="0" class="mytab">
	<tr>
		<th width="40">序号</th>
		<th>用户名</th>
		<th>昵称</th>
		<th>最后登录IP</th>
		<th>最后登录时间</th>
		<th>操作</th>
	</tr>
	<#if (pagingList?exists) && (pagingList.list?exists)>
	<#list pagingList.list as u>
	<tr>
		<td>${u_index + 1 + (pagingList.pageNum-1) * pagingList.pageSize}</td>
		<td>${u.userName?default("无")}</td>
		<td>${u.nickName?default("无")}</td>
		<td>${u.lastLoginIp?default("无")}</td>
		<td>${u.lastLoginTime?default("无")}</td>
		<td>
			<a href = "${base}SysUser/detail.html?id=${u.id}" title="详细">详细</a>
			&nbsp;|&nbsp;
			<a href = "${base}SysUser/update.html?id=${u.id}" title="修改">修改</a>
			&nbsp;|&nbsp;
			<a href = "#" title="删除" onclick="confirmDelete('${base}SysUser/delete.html?id=${u.id}')">删除</a>
		</td>
	</tr>
	</#list>
	</#if>
	</table>
	
	<@p.paging pagingList/>
</form>
<@p.footer/>