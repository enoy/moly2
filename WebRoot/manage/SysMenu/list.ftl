<@p.header navTitle="菜单管理">
<@p.includeZTree/>

<style type="text/css">
.ztree li span.button.detail {
	background-position:-110px -48px;
	vertical-align:top;
	*vertical-align:middle;
}

.singleTable .textinput {
	width:96%;
}
.menuTreeContainer{
	width:300px;
	float:left;
}
.menuDataContainer{
	width:400px;
	float:left;
	display:none;
}
</style>

<script type="text/javascript">
//显示编辑菜单详情
function showEditMenu(treeNode){
	$("#menuDataContainer").show();
	$("#menuId").val(treeNode.id);
	$("#menuName").val(treeNode.name);
	$("#menuPid").val(treeNode.pId);
	$("#menuUrl").val(treeNode.MENU_URL);
	$("#menuOrder").val(treeNode.MENU_ORDER);
	//根据 zTree 的唯一标识 tId 快速获取节点 JSON 数据对象
	$("#menuTreeTId").val(treeNode.tId);
}
//隐藏编辑菜单详情
function hideEditMenu(){
	$("#menuDataContainer").hide();
}
//修改菜单
function updateMenu() {
	showLoading();
	$.post("${base}SysMenu/saveOrUpdate.html",
			{
				"id":$("#menuId").val(),
				"menuName":$("#menuName").val(),
				"menuPid":$("#menuPid").val(),
				"menuUrl":$("#menuUrl").val(),
				"menuOrder":$("#menuOrder").val()
			},
			function(data){
				hideLoading();
				//刷新页面
				//window.location.href="${base}SysMenu/list.html";
				
				//单独更新
				var treeNode = getZTree().getNodeByTId($("#menuTreeTId").val());
				treeNode.id = data.id;
				treeNode.name = data.menuName;
				treeNode.pId = data.menuPid;
				treeNode.MENU_URL = data.menuUrl;
				treeNode.MENU_ORDER = data.menuOrder;
				
				getZTree().updateNode(treeNode);
				
				parentAlertMsg('修改成功');
				
				hideEditMenu();
	});
}


var treeDivId = "menuTree";
function getZTree(){
	return $.fn.zTree.getZTreeObj(treeDivId);
}

//添加按钮hover
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag ){
		return;
	}
	//根节点不许编辑
	if(treeNode.level != 0){
		if(!$("#detailBtn_"+treeNode.tId).length>0){
			var detailStr = "<span class='button detail' id='detailBtn_" + treeNode.tId
				+ "' title='编辑菜单' onfocus='this.blur();'></span>";
			sObj.after(detailStr);
			var btn = $("#detailBtn_"+treeNode.tId);
			if (btn) btn.bind("click", function(){
				showEditMenu(treeNode);
				return false;
			});
		}
	}
	
	if(!$("#addBtn_"+treeNode.tId).length>0){
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='添加菜单' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
			getZTree().addNodes(treeNode, {id:-1, pId:treeNode.id, name:"新建菜单"});
			return false;
		});
	}
};
//鼠标移开时删除添加按钮
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
	$("#detailBtn_"+treeNode.tId).unbind().remove();
};
//异步载入AJAX数据后回调
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	//展开所有
	//getZTree().expandAll(true);
	
	//展开第一级
	var z = getZTree();
	var nodes = z.getNodes();
	if(nodes.length > 0){
		z.expandNode(nodes[0], true, false, false);
	}
	
	hideLoading();
};
//删除前确认
function beforeRemove(treeId, treeNode) {
	if(treeNode.level == 0){
		Dialog.alert("根节点无法删除");
		return false;
	}else{
		getZTree().selectNode(treeNode);
		
		removeNode(treeNode);
		
		return false;
	}
}
//重命名前检查
function beforeRename(treeId, treeNode, newName) {
	if (newName.length == 0) {
		Dialog.alert("节点名称不能为空.");
		return false;
	}
	return true;
}
//执行删除
function removeNode(treeNode){
	Dialog.confirm("确认删除 菜单 : " + treeNode.name + " 吗？", function(){
		showLoading();
		
		$.post("${base}SysMenu/delete.html?id=" + treeNode.id,function(data){
			hideLoading();
			
			getZTree().removeNode(treeNode);
			
			parentAlertMsg('删除成功');
		});
	});
}
//删除时触发
function zTreeOnRemove(event, treeId, treeNode) {
}
//重命名
function zTreeOnRename(event, treeId, treeNode, isCancel) {
}

//显示删除按钮
function showRemoveBtn(treeId, treeNode){
	//根节点不允许删除
	return treeNode.level != 0;
}
//显示修改按钮
function showRenameBtn(treeId, treeNode){
	//所有不允许修改
	return false;
}
function beforeDrag(treeId, treeNodes) {
	/*
	for (var i=0,l=treeNodes.length; i<l; i++) {
		if (treeNodes[i].drag === false) {
			return false;
		}
	}
	*/
	return true;
}
function beforeDrop(treeId, treeNodes, targetNode, moveType) {
	var allow = targetNode ? targetNode.drop !== false : true;
	if(allow){
		var sourceIds = "";
		for (var i=0,l=treeNodes.length; i<l; i++) {
			if(i != 0){
				sourceIds += ",";
			}
			sourceIds += treeNodes[i].id;
		}

		showLoading();
		$.post("${base}SysMenu/move.html",
				{
					"sourceIds":sourceIds,
					"targetId":targetNode.id,
					"moveType":moveType
				},
				function(data){
					hideLoading();
					//刷新页面
					window.location.href="${base}SysMenu/list.html";
		});
	}
	return allow;
}

//树设置
var setting = {
	view: {
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom,
		selectedMulti:true
	},
	edit: {
		enable: true,
		showRemoveBtn: showRemoveBtn,
		showRenameBtn: showRenameBtn,
		removeTitle: "删除",
		renameTitle: "重命名"
	},
	data: {
		simpleData: {
			enable: true,
			rootPid: -1
		}
	},
	callback: {
		beforeDrag: beforeDrag,
		beforeDrop: beforeDrop,
		beforeRemove: beforeRemove,
		beforeRename: beforeRename,
		onAsyncSuccess: zTreeOnAsyncSuccess,
		onRemove: zTreeOnRemove,
		onRename: zTreeOnRename
	},
	async: { 
		enable: true,
		url:"${base}SysMenu/listData.html"
	}
};

$(document).ready(function(){
	$.fn.zTree.init($("#" + treeDivId), setting);
	
	showLoading();
});

</script>
</@p.header>

<form action="${base}SysMenu/list.html" method="post">

  <div class="menuTreeContainer">
	<ul id="menuTree" class="ztree"></ul>
  </div>

  <div class="menuDataContainer" id="menuDataContainer">
    <div class="singleTableContainer">
	  <@form.hidden id="menuId" path="sysMenu.id" />
	  <@form.hidden id="menuPid" path="sysMenu.menuPid" />
	  <input type="hidden" name="menuTreeTId" id="menuTreeTId" />
      <table width="100%" cellpadding="0" cellspacing="1" class="singleTable">
        <tr>
		  <td width="25%" class="txtlabel"><span class="red12">*</span>菜单名称：</td>
		  <td width="75%" class="txtcont">
		  	<@form.input path="sysMenu.menuName" id="menuName" reg="^.+$" tip="请输入菜单名称" class="textinput"/>
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel"><span class="red12">*</span>菜单网址：</td>
		  <td class="txtcont">
		  	<@form.input path="sysMenu.menuUrl" id="menuUrl" reg="^.+$" tip="请输入菜单网址" class="textinput"/>
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">菜单序号：</td>
		  <td class="txtcont">
		  	<@form.input path="sysMenu.menuOrder" id="menuOrder" class="textinput"/>
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">&nbsp;</td>
		  <td class="txtcontbtn">
			<input type="button" value="保存" class="but_big" onclick="updateMenu();"/>
			&nbsp;&nbsp;
			<input type="button" value="取消" class="but_big_cancel" onclick="hideEditMenu();"/>
		  </td>
		</tr>
	</table>
	</div>
  </div>

</form>

<@p.includeLoading/>

<@p.footer/>