<@p.header4content navTitle="角色管理 - 可添加到角色的用户列表" toggleSearch=true containerDiv=false>
<script type="text/javascript">
function confirmAddUser(btn){
	var selectedIdsArr = new Array();
	$("input[name = ids]:checkbox[checked]").each(function(i,e){
		selectedIdsArr.push($(e).val());	
	});
	
	if(selectedIdsArr.length == 0){
		Dialog.alert("请至少选择一个用户");
	}else{
		$(btn).attr("value","添加中...");
		var selectIds = selectedIdsArr.join(',');
		$.post("${base}SysRole/saveUserRole.html",
				{"roleId":${roleId},"userIds":selectIds},
				function(data){
				$("#listform").submit();
		});
	}
}
</script>
</@p.header4content>

<form action="${base}SysRole/availableUserList.html" id="listform" method="post">
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
	<input type="button" value="确认添加" class="but" onclick="confirmAddUser(this);"/>
	</@ui.ctrlBar>

	<table cellpadding="2" cellspacing="0" class="mytab">
	<tr>
		<th width="40">
			<input type="checkbox" name="selectAllCtrl" onclick="selectAll(this);"/>
		</th>
		<th>用户名称</th>
		<th>用户昵称</th>
		<th>操作</th>
	</tr>
	<#if (pagingList?exists) && (pagingList.list?exists)>
	<#list pagingList.list as u>
	<tr>
		<td>
			<input type="checkbox" name="ids" value="${u.ID}"/>
		</td>
		<td>${u.USER_NAME?default("无")}</td>
		<td>${u.NICK_NAME?default("无")}</td>
		<td>
			<a href = "#" title="添加到角色" 
				onclick="confirmDelete('${base}SysRole/saveUserRole.html?userIds=${u.ID}&roleId=${roleId}','确认要添加吗?')">添加到角色</a>
		</td>
	</tr>
	</#list>
	</#if>
	</table>
	
	<@p.paging pagingList/>
</form>
<@p.footer containerDiv=false/>