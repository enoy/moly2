<@p.header navTitle="角色管理 - 角色权限">
<@p.includeZTree/>

<script type="text/javascript">
var treeDivId = "menuTree";
function getZTree(){
	return $.fn.zTree.getZTreeObj(treeDivId);
}
//异步载入AJAX数据后回调
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	//展开所有
	getZTree().expandAll(true);
	
	//展开第一级
	/*
	var z = getZTree();
	var nodes = z.getNodes();
	if(nodes.length > 0){
		z.expandNode(nodes[0], true, false, false);
	}
	*/
	
	hideLoading();
};

//树设置
var setting = {
	view: {
		selectedMulti:true
	},
	check: {
		enable: true,
		chkStyle: "checkbox",
		chkboxType: { "Y": "ps", "N": "ps" }
	},
	data: {
		simpleData: {
			enable: true,
			rootPid: -1
		}
	},
	callback: {
		onAsyncSuccess: zTreeOnAsyncSuccess
	},
	async: { 
		enable: true,
		url:"${base}SysMenu/listData.html?roleId=${roleId}"
	}
};

$(document).ready(function(){
	$.fn.zTree.init($("#" + treeDivId), setting);
	
	showLoading();
});

//保存权限
function savePermission(){
	var zTree = getZTree();
	var nodes = zTree.getCheckedNodes(true);
	var ids = ""
	for (var i=0, l=nodes.length; i<l; i++) {
		if(nodes[i].level != 0){
			if(ids.length != 0){
				ids += ",";
			}
			ids += nodes[i].id;
		}
	}
	$("#menuIds").val(ids);
	
	showLoading();
	$("#listform").submit();
}

</script>
</@p.header>

<form action="${base}SysRole/savePermission.html" method="post" id="listform">

	<@ui.ctrlBar>
		<input type="button" value="保存" class="but" onclick="savePermission();"/>
		&nbsp;&nbsp;
		<input type="button" value="返回" class="but" onclick="gotoUrl('${base}SysRole/list.html')"/>
	</@ui.ctrlBar>

	<input type="hidden" name="roleId" id="roleId" value="${roleId}"/>
	<input type="hidden" name="menuIds" id="menuIds"/>

  <div>
	<ul id="menuTree" class="ztree"></ul>
  </div>

</form>

<@p.includeLoading/>

<@p.footer/>