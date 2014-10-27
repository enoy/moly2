<#-- 后台普通页header -->
<#macro header title="管理系统" body=""
keywords="管理系统" description="管理系统" author="gary" containerDiv=true
navTitle="" toggleSearch=false>
<#import "/common/ui.ftl" as ui>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${title}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keywords" content="${keywords}"/>
	<meta name="description" content="${description}" /> 
	<meta name="author" content="${author}" />
	<meta name="robots" content="${base}robots.txt" />
	<link rel="shortcut icon" href="${base}favicon.ico" />
	
	<link href="${base}css/index.css" rel="stylesheet" type="text/css" />
	<link href="${base}css/easyvalidate.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${base}js/jquery.js"></script>
	<script type="text/javascript" src="${base}js/framework/common.js"></script>
	<script type="text/javascript" src="${base}js/easyvalidator.js"></script>

	<script type="text/javascript" src="${base}js/zDialog/zDialog.js"></script>
	<script type="text/javascript" src="${base}js/zDialog/zDrag.js"></script>
	<script type="text/javascript">
	IMAGESPATH = '${base}js/zDialog/images/';
	</script>
	<#nested>
</head>
<body ${body}>
<#if containerDiv>
<div id="rigmain">
  <div id="righbox">
</#if>
  <#if navTitle!="">
  	<@ui.nav title=navTitle toggleSearch=toggleSearch/>
  </#if>
</#macro>

<#-- 后台普通页footer -->
<#macro footer containerDiv=true>
<#nested>
<#if containerDiv>
  </div>
</div>
</#if>
</body>
</html>
</#macro>

<#--  内容页header  -->
<#macro header4content navTitle="" toggleSearch=false containerDiv=true>
<@header navTitle=navTitle toggleSearch=toggleSearch containerDiv=containerDiv>
	<#if (returnMsg?exists)>
	<script language="javascript">
	$(document).ready(function() {
		parentAlertMsg('${returnMsg }');
	});
	</script>
	</#if>
	<#nested>
</@header>
</#macro>

<#-- 引入My97DatePicker -->
<#macro includeDatePicker>
<script type="text/javascript" src="${base}js/My97DatePicker/WdatePicker.js"></script>
</#macro>

<#-- 引入zTree -->
<#macro includeZTree>
<link href="${base}js/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}js/zTree/js/jquery.ztree.all-3.5.min.js"></script>
<style type="text/css">
.ztree li span.button.add {background-position:-144px 0; vertical-align:top; *vertical-align:middle;}
</style>
</#macro>

<#-- 引入uploadify -->
<#macro includeUploadify>
<script type="text/javascript" src="${base}js/uploadify/jquery.uploadify.min.js"></script>
<link rel="stylesheet" type="text/css" href="${base}js/uploadify/uploadify.css">
</#macro>

<#-- 引入SWFUpload -->
<#macro includeSWFUpload>
<script type="text/javascript" src="${base}js/SWFUpload/swfupload.js"></script>
<script type="text/javascript" src="${base}js/SWFUpload/swfupload.queue.js"></script>
<script type="text/javascript" src="${base}js/SWFUpload/swfupload.speed.js"></script>
<script type="text/javascript" src="${base}js/SWFUpload/handlers.js"></script>
</#macro>

<#-- 使用loading界面 -->
<#macro includeLoading>
<script type="text/javascript">
//显示loading
function showLoading(){
	$("#loadingBgDiv").css({ display: "block", height: $(document).height() });
    $("#loadingDiv").css({ top: "50px", display: "block" });
	document.documentElement.scrollTop = 0;
}
//隐藏loading
function hideLoading(){
	$("#loadingBgDiv").css({ display: "none", height: $(document).height() });
	$("#loadingDiv").css("display", "none");
}
</script>
<div id="loadingBgDiv" style="display:none; background-color:#e3e3e3;position:absolute;z-index:99;left:0;top:0;width:100%;height:1000px;opacity:0.5;filter:alpha(opacity=50);-moz-opacity:0.5;"></div>
<div id="loadingDiv" style="display:none; position:absolute;left:50%;top:50%;margin-left:-200px;margin-top:10%;height:auto;z-index:100;">
	<img src="${base}pics/wait.gif" />
</div>
</#macro>


<#macro paging pagingList>
<#local totalRows=pagingList.totalRows>
<#local pageNum=pagingList.pageNum>
<#local pageSize=pagingList.pageSize>
<#local totalPages=pagingList.totalPages>
<div class="pager-top">
<#if totalRows == 0>
	<div align="center">暂无记录</div>
<#else>
	<#if pageNum != 1>
		<#if pageNum != totalPages>
	        <span>
	        	当前显示
	        	 ${(pagingList.pageNum - 1) * pagingList.pageSize + 1}-${pagingList.pageNum * pagingList.pageSize}
        		条 / 共${totalRows} 条记录 ${totalPages}页
	        </span>
	  	<#else>
	  		<#if ((pageNum - 1) * pageSize + 1)  == totalRows>
	             <span>当前显示 ${totalRows} 条/ 共 ${totalRows} 条记录 ${totalPages}页</span>
	        <#else>
	             <span>当前显示 ${(pageNum - 1) * pageSize + 1} - ${pageNum * pageSize} 条 / 共 ${totalRows} 条记录 ${totalPages}页</span>
	        </#if>
	    </#if>
	<#else>
		<#if pageNum != totalPages>
	         <span>当前显示 1 - ${pageSize} 条 / 共 ${totalRows} 条记录 ${totalPages}页</span>
	    <#else>
	         <span>当前显示 1 - ${totalRows} 条 / 共 ${totalRows} 条记录 ${totalPages}页</span>       
	  	</#if>
	</#if>

	<ol class="pagerpro">
	<#if (totalPages > 1)>
		<#if pageNum != 1>
	    	<li><a href="#" onclick="gotoPager('1');" title="首页"><img src="${base }images/paging_first.gif" alt="首页"/></a></li>
	    	<li><a href="#" onclick="gotoPager('${pagingList.pageNum - 1}');" title="上页"><img src="${base }images/paging_pre.gif" alt="上一页"/></a></li>
	  	</#if>
	  	<span class="pagenum">
	  	<#if (totalPages <= 11)>
	  		<#local from = 1>
	  		<#local to = totalPages>
	  	<#elseif (pageNum + 5>totalPages)>
	  		<#local from = totalPages - 10>
	  		<#local to = totalPages>
	  	<#elseif (pageNum - 5 < 1)>
	  		<#local from = 1>
	  		<#local to = 11>
	  	<#else>
	  		<#local from = pageNum - 5>
	  		<#local to = pageNum + 5>
	  	</#if>
	  	<#list from..to as x>
	  		<#if x = pageNum>
	  			<li class="current"><a href="#">${x}</a></li>
			<#else>
		        <li><a class="chn" href="#" onclick="gotoPager('${x}');" title="${x}">${x}</a></li>
			</#if>	  	
	  	</#list>
	  	</span>
	  	<#if totalPages != pageNum>
		    <li><a href="#" onclick="gotoPager('${pagingList.pageNum + 1}');" title="下页"><img src="${base }images/paging_next.gif" alt="下一页"/></a></li>
		    <li><a href="#" onclick="gotoPager('${totalPages}');" title="尾页"><img src="${base }images/paging_last.gif" alt="尾页"/></a></li>
	  	</#if>
	</#if>
	</ol>
</#if>
<input type="hidden" name="pageSize" id="pager_pageSize" value="${pageSize}"/>
<input type="hidden" name="pageNum" id="pager_pageNum"/>
<script type="text/javascript">
function gotoPager(pageNum){
	$("#pager_pageNum").val(pageNum);
	$("#listform").submit();
}
</script>
</div>
</#macro>