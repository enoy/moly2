/*
	Copyright (C) 2009 - 2012
	WebSite:	Http://wangking717.javaeye.com/
	Author:		wangking
	
	支持身份证号,使用:reg=idCard
	支持可选,添加optional="1"时,只有在非空情况下才进行验证
	Author:		gary
*/
$(function(){
	var xOffset = -20; // x distance from mouse
    var yOffset = 5; // y distance from mouse  
	
	
	//input action
	$("[reg],[url]:not([reg]),[tip]").hover(
		function(e) {
			if($(this).attr('tip') != undefined){
				var top = (e.pageY + yOffset);
				var left = (e.pageX + xOffset);
				$('body').append( '<p id="vtip">' + $(this).attr('tip') + '</p>' );
				$('p#vtip').css("top", top+"px").css("left", left+"px");
				//$('p#vtip').bgiframe();
			}
		},
		function() {
			if($(this).attr('tip') != undefined){
				$("p#vtip").remove();
			}
		}
	).mousemove(
		function(e) {
			if($(this).attr('tip') != undefined){
				var top = (e.pageY + yOffset);
				var left = (e.pageX + xOffset);
				$("p#vtip").css("top", top+"px").css("left", left+"px");
			}
		}
	).blur(function(){
		if($(this).attr("url") != undefined){
			ajax_validate($(this));
		}else if($(this).attr("reg") != undefined){
			validate($(this));
		}
	});
	
	$("form").submit(function(){
		var isSubmit = true;
		$(this).find("[reg],[url]:not([reg])").each(function(){
			if($(this).attr("reg") == undefined){
				if(!ajax_validate($(this))){
					isSubmit = false;
				}
			}else{
				if(!validate($(this))){
					isSubmit = false;
				}
			}
		});
		if(typeof(isExtendsValidate) != "undefined"){
   			if(isSubmit && isExtendsValidate){
				return extendsValidate();
			}
   		}
		return isSubmit;
	});
	
});

function validate(obj){
	var regAttr = obj.attr("reg");
	var objValue = obj.attr("value");
	//非必填
	var optionalAttr = obj.attr("optional");
	if("1" == optionalAttr && "" == objValue){
		change_error_style(obj,"remove");
		change_tip(obj,null,"remove");
		return true;
	}
	//必填
	if("idCard" == regAttr){
		if(checkCnId(objValue) != "0"){
			change_error_style(obj,"add");
			change_tip(obj,null,"remove");
			return false;
		}else{
			change_error_style(obj,"remove");
			change_tip(obj,null,"remove");
			return true;
		}
	}else{
		var reg = new RegExp(regAttr);
		if(!reg.test(objValue)){
			change_error_style(obj,"add");
			change_tip(obj,null,"remove");
			return false;
		}else{
			if(obj.attr("url") == undefined){
				change_error_style(obj,"remove");
				change_tip(obj,null,"remove");
				return true;
			}else{
				return ajax_validate(obj);
			}
		}
	}
}

function ajax_validate(obj){
	var url_str = obj.attr("url");
	if(url_str.indexOf("?") != -1){
		url_str = url_str+"&"+obj.attr("name")+"="+obj.attr("value");
	}else{
		url_str = url_str+"?"+obj.attr("name")+"="+obj.attr("value");
	}
	var feed_back = $.ajax({url: url_str,cache: false,async: false}).responseText;
	feed_back = feed_back.replace(/(^\s*)|(\s*$)/g, "");
	if(feed_back == 'success'){
		change_error_style(obj,"remove");
		change_tip(obj,null,"remove");
		return true;
	}else{
		change_error_style(obj,"add");
		change_tip(obj,feed_back,"add");
		return false;
	}
}

function change_tip(obj,msg,action_type){
	if(obj.attr("tip") == undefined){//初始化判断TIP是否为空
		obj.attr("is_tip_null","yes");
	}
	if(action_type == "add"){
		if(obj.attr("is_tip_null") == "yes"){
			obj.attr("tip",msg);
		}else{
			if(msg != null){
				if(obj.attr("tip_bak") == undefined){
					obj.attr("tip_bak",obj.attr("tip"));
				}
				obj.attr("tip",msg);
			}
		}
	}else{
		if(obj.attr("is_tip_null") == "yes"){
			obj.removeAttr("tip");
			obj.removeAttr("tip_bak");
		}else{
			obj.attr("tip",obj.attr("tip_bak"));
			obj.removeAttr("tip_bak");
		}
	}
}

function change_error_style(obj,action_type){
	if(action_type == "add"){
		obj.addClass("input_validation-failed");
	}else{
		obj.removeClass("input_validation-failed");
	}
}

$.fn.validate_callback = function(msg,action_type,options){
	this.each(function(){
		if(action_type == "failed"){
			change_error_style($(this),"add");
			change_tip($(this),msg,"add");
		}else{
			change_error_style($(this),"remove");
			change_tip($(this),null,"remove");
		}
	});
};


/*
 * <p>Description: 检查输入参数是否为合法的身份证号码 ,输入参数为身份证号码，返回一个字符串，用于描述验证结果： 0,验证通过!
 * 1,身份证号码位数不对! 2,身份证号码出生日期超出范围或含有非法字符! 3,身份证号码校验错误! 4,身份证地区非法!
 */
function checkCnId(idcard) {
	//var Errors = new Array("0,验证通过!", "1,身份证号码位数不对!",
	//		"2,身份证号码出生日期超出范围或含有非法字符!", "3,身份证号码校验错误!", "4,身份证地区非法!");
	var Errors = new Array("0","1","2","3","4");
	var area = {
		11 : "北京",
		12 : "天津",
		13 : "河北",
		14 : "山西",
		15 : "内蒙古",
		21 : "辽宁",
		22 : "吉林",
		23 : "黑龙江",
		31 : "上海",
		32 : "江苏",
		33 : "浙江",
		34 : "安徽",
		35 : "福建",
		36 : "江西",
		37 : "山东",
		41 : "河南",
		42 : "湖北",
		43 : "湖南",
		44 : "广东",
		45 : "广西",
		46 : "海南",
		50 : "重庆",
		51 : "四川",
		52 : "贵州",
		53 : "云南",
		54 : "西藏",
		61 : "陕西",
		62 : "甘肃",
		63 : "青海",
		64 : "宁夏",
		65 : "新疆",
		71 : "台湾",
		81 : "香港",
		82 : "澳门",
		91 : "国外"
	};
	var Y, JYM;
	var S, M;
	var idcard_array = new Array();
	idcard_array = idcard.split("");
	if (area[parseInt(idcard.substr(0, 2))] == null)
		return Errors[4];// 地区检验
	switch (idcard.length) {
		case 15: {
			if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0
					|| ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard
							.substr(6, 2)) + 1900) % 4 == 0)) {
				ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;// 测试出生日期的合法性
			} else {
				ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;// 测试出生日期的合法性
			}
			if (ereg.test(idcard))
				return Errors[0];
			else
				return Errors[2];
			break;
		}
		case 18: {
			// 18位身份号码检测
			// 出生日期的合法性检查
			/*
			 闰年月日: ((01 | 03 | 05 | 07 | 08 | 10 | 12)(0[1 - 9] | [ 1 - 2 ][0 - 9]
					| 3[0 - 1])
					| (04 | 06 | 09 | 11)(0[1 - 9] | [ 1 - 2 ][0 - 9] | 30) | 02
					(0[1 - 9] | [ 1 - 2 ][0 - 9]))
			 平年月日: ((01 | 03 | 05 | 07 | 08 | 10 | 12)(0[1 - 9] | [ 1 - 2 ][0 - 9]
					| 3[0 - 1])
					| (04 | 06 | 09 | 11)(0[1 - 9] | [ 1 - 2 ][0 - 9] | 30) | 02
					(0[1 - 9] | 1[0 - 9] | 2[0 - 8]))
			*/
			if (parseInt(idcard.substr(6, 4)) % 4 == 0
					|| (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard
							.substr(6, 4)) % 4 == 0)) {
				//闰年出生日期的合法性正则表达式
				ereg = /^[1-9][0-9]{5}(19|20|21|22)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;
			} else {
				//平年出生日期的合法性正则表达式
				ereg = /^[1-9][0-9]{5}(19|20|21|22)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;
			}
			if (ereg.test(idcard)) {
				// 测试出生日期的合法性
				S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 + // 计算校验位
				(parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
						+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12]))
						* 10
						+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13]))
						* 5
						+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14]))
						* 8
						+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15]))
						* 4
						+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16]))
						* 2 + parseInt(idcard_array[7]) * 1
						+ parseInt(idcard_array[8]) * 6 + parseInt(idcard_array[9])
						* 3;
				Y = S % 11;
				M = "F";
				JYM = "10X98765432";
				M = JYM.substr(Y, 1);// 判断校验位
				if (M == idcard_array[17])
					return Errors[0]; // 检测ID的校验位
				else
					return Errors[3];
			} else
				return Errors[2];
			break;
		}
		default: {
			return Errors[1];
			break;
		}
	}
}

/*
 * * 通过身份证判断是男是女 * @param idCard 15/18位身份证号码 * @return '0'-女、'1'-男
 */
function maleOrFemalByIdCard(idCard) {
	if (idCard.length == 15) {
		if (idCard.substring(14, 15) % 2 == 0) {
			return 0;
		} else {
			return 1;
		}
	} else if (idCard.length == 18) {
		if (idCard.substring(14, 17) % 2 == 0) {
			return 0;
		} else {
			return 1;
		}
	} else {
		return -1;
	}
}

/**
 * 通过身份证号的得到出生日期函数
 * 参数，输入的证件号码
 * 返回 出生日期
 */
function getBirthdatByIdNo(iIdNo) {
	var tmpStr = "";
	var strReturn = "";
	if ((iIdNo.length != 15) && (iIdNo.length != 18)) {
		strReturn = "输入的身份证号位数错误";
		return strReturn;
	}
	if (iIdNo.length == 15) {
		tmpStr = iIdNo.substring(6, 12);
		tmpStr = "19" + tmpStr;
		tmpStr = tmpStr.substring(0, 4) + "-" + tmpStr.substring(4, 6) + "-"
				+ tmpStr.substring(6);
		return tmpStr;
	} else { 
		// if(iIdNo.length==18)
		tmpStr = iIdNo.substring(6, 14);
		tmpStr = tmpStr.substring(0, 4) + "-" + tmpStr.substring(4, 6) + "-"
				+ tmpStr.substring(6);
		return tmpStr;
	}
}
