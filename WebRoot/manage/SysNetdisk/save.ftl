<@p.header navTitle="网盘管理-上传文件(文件大小不能超过${sizeLimit})">
	<@p.includeSWFUpload/>
	<style type="text/css">
	.upload-file-content{
		margin:3px;
		line-height: 24px;
	}
	.upload-file-content input{
		border: 1px solid #9c9c9c;
	}
	</style>
	
	<script type="text/javascript">
	function uploadError(file, errorCode, message) {
		if(errorCode == -280){
			//上传停止
		}else{
			if(errorCode == -290){
				message = "上传取消";
			}
			Dialog.alert(file.name + " 上传失败,原因:" + message);
		}
	}

	function uploadSuccess(file, serverData, responseReceived) {
		try {
			updateDisplay.call(this, file);
		} catch (ex) {
			this.debug(ex);
		}
	
		$("#uploadExtraDataContainer").show();
		var html = '<tr>';
		html += '<td width="35%" class="txtlabel">' + file.name + '&nbsp;&nbsp;&nbsp;</td>';
		html += '<td width="64%" class="txtcont">';
		html += '<div class="upload-file-content"><input type="hidden" name="f_fileUrl_' + file.index + '" value="' + serverData + '"/>';
		html += '<div>文件名称&nbsp;&nbsp;&nbsp;<input type="text" name="f_fileName_' + file.index + '" value="' + file.name + '"/></div>';
		html += '<div>文件说明&nbsp;&nbsp;&nbsp;<input type="text" name="f_fileDesc_' + file.index + '"/></div>';
		html += '</div></td>';
		html += '</tr>';
		$("#uploadExtraData").prepend(html);
	}

	var swfu;
	window.onload = function() {
		var settings = {
			upload_url 				: "${base }Upload/returnUrl.html",
			post_params: {
                "folderName": "netdisk"
            },
			flash_url 				: "${base }js/SWFUpload/swfupload.swf",
			file_size_limit 		: "${sizeLimit}",
			file_types 				: "*.*",
			file_types_description 	: "All Files",
			//实例允许上传的最多文件数量,0表示允许上传的数量无限制  
			file_upload_limit 		: 0,
			//设置文件上传队列中等待文件的最大数量限制  
			file_queue_limit 		: 0,
			file_post_name			: "file",

			debug					: false,

			// Button settings
			button_image_url		: "${base}images/bg_button_blue.gif",
			button_width			: "78",
			button_height			: "21",
			button_text				: "选择文件",
			button_text_left_padding : 13,
			button_text_top_padding : 1,
			button_placeholder_id	: "spanButtonPlaceHolder",
			
           	cancelButtonId 			: "btnCancel",
           	
			// The event handler functions are defined in handlers.js
			file_queued_handler 	: fileQueued,
			file_dialog_complete_handler: fileDialogComplete,
			upload_start_handler 	: uploadStart,
			upload_progress_handler : uploadProgress,
			upload_success_handler 	: uploadSuccess,
			upload_complete_handler : uploadComplete,
			upload_error_handler    : uploadError,
			
			custom_settings : {
				tdFilesQueued 		: document.getElementById("tdFilesQueued"),
				tdFilesUploaded 	: document.getElementById("tdFilesUploaded"),
				tdErrors 			: document.getElementById("tdErrors"),
				tdCurrentSpeed 		: document.getElementById("tdCurrentSpeed"),
				tdAverageSpeed 		: document.getElementById("tdAverageSpeed"),
				tdMovingAverageSpeed : document.getElementById("tdMovingAverageSpeed"),
				tdTimeRemaining 	: document.getElementById("tdTimeRemaining"),
				tdTimeElapsed 		: document.getElementById("tdTimeElapsed"),
				tdPercentUploaded 	: document.getElementById("tdPercentUploaded"),
				tdSizeUploaded 		: document.getElementById("tdSizeUploaded")
			}
		};

		swfu = new SWFUpload(settings);
     };
	</script>
</@p.header>

<form id="form1" action="${base}SysNetdisk/doSave.html" method="post" enctype="multipart/form-data">

	<@ui.ctrlBar>
	<input type="button" value="选择上传文件" class="but" id="spanButtonPlaceHolder"/>
	</@ui.ctrlBar>

	<div class="singleTableContainer">
	  <table width="100%" cellpadding="0" cellspacing="1" class="singleTable">
		<tr>
		  <td width="35%" class="txtlabel" rowspan="7">
		  	文件队列: <span id="tdFilesQueued" width="50px;">0</span>
		  	&nbsp;&nbsp;&nbsp;<br/><br/>
		  	已上传: </span><span id="tdFilesUploaded" width="50px;">0</span>
		  	&nbsp;&nbsp;&nbsp;<br/><br/>
		  	错误: </span><span id="tdErrors" width="50px;">0</span>
		  	&nbsp;&nbsp;&nbsp;
		  </td>
		  <td width="65%" class="txtcont">
		  	当前速度<span id="tdCurrentSpeed"></span>
		  </td>
		</tr>
		<tr>
		  <td class="txtcont">
		  	平均速度<span id="tdAverageSpeed"></span>
		  </td>
		</tr>
		<tr>
		  <td class="txtcont">
		  	平均移动速度<span id="tdMovingAverageSpeed"></span>
		  </td>
		</tr>
		<tr>
		  <td class="txtcont">
		  	剩余时间<span id="tdTimeRemaining"></span>
		  </td>
		</tr>
		<tr>
		  <td class="txtcont">
		  	经过时间<span id="tdTimeElapsed"></span>
		  </td>
		</tr>
		<tr>
		  <td class="txtcont">
		  	上传进度<span id="tdPercentUploaded"></span>
		  </td>
		</tr>
		<tr>
		  <td class="txtcont">
		  	已上传大小<span id="tdSizeUploaded"></span>
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">&nbsp;</td>
		  <td class="txtcont">
			<input type="button" value="取消" class="but_big_cancel" onclick="swfu.cancelQueue();"/>
			&nbsp;&nbsp;
			<input type="button" value="返回" class="but_big_cancel" onclick="goback();"/>
		  </td>
		</tr>
	</table>
</div>	

<div id="uploadExtraDataContainer" style="display:none;margin-top:10px;">
	<div class="singleTableContainer">
  		<table width="100%" cellpadding="0" cellspacing="1" class="singleTable" id="uploadExtraData">
  		
  			<tr>
			  <td class="txtlabel">&nbsp;</td>
			  <td class="txtcontbtn">
				<input type="submit" value="保存" class="but_big"/>
			  </td>
			</tr>
		</table>
	</div>
</div>
</form>
<@p.footer/>