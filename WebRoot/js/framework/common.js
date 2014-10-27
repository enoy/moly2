/**
 * 顶部窗口提示信息
 * @param {String} msg 提示信息
 */
function parentAlertMsg(msg){
	try{
		window.parent.frames["topFrame"].alertMsg(msg);
	}catch (e){
	}
}

/**
 * 确认删除
 * @param url
 * @param msg
 */
function confirmDelete(url,msg){
	if(!arguments[0]){
		return;
	}
	if(!arguments[1]){
		msg = "警告：您确认要删除吗?";
	}
	Dialog.confirm(msg, function(){
		gotoUrl(url);
	});
}

/**
 * 全选/全不选
 * @param ctrlObj
 * @param inputName
 */
function selectAll(ctrlObj, inputName){
	if(!arguments[1]){
		inputName = "ids";
	}
	$("input[name = " + inputName + "]:checkbox").attr("checked", ctrlObj.checked);
}

/**
 * 跳转
 * @param url
 */
function gotoUrl(url){
	window.location.href = url;
}

/**
 * 后退
 */
function goback(){
	window.history.go(-1);
}