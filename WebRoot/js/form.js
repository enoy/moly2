/**
 * 表单验证
 * 需要验证为空的元素上填写needValidate=1属性,如需更改提示文字,同时再加上errorMsg属性
 */
$(function(){
	$("form").submit(function(){
		return validateForm(this);
	});
});
function validateForm(formObj){
	$(".inputerror").remove();
	var allowSubmit = true;
	//该项为必填，请填写后再保存！
	var standardErrorMsg = "<div class=\"inputerror\">&#35813;&#39033;&#20026;&#24517;&#22635;&#65292;&#35831;&#22635;&#20889;&#21518;&#20877;&#20445;&#23384;&#65281;</div>";
	$(formObj).find("[needValidate]").each(function(){
		if(!validate($(this))){
			allowSubmit = false;
			var customErrorMsg = $(this).attr("errorMsg");
			if(customErrorMsg){
				$(this).after("<div class=\"inputerror\">" + customErrorMsg + "</div>");
			}else{
				$(this).after(standardErrorMsg);
			}
		}
	});
	return allowSubmit;
}
function validate(obj){
	var inputVal = obj.val();
	if(obj.attr("needValidate") == "num"){
		//数字
		return /^\d+$/.test(inputVal);
	}else if(inputVal == ""){
		//验证是否为空
		return false;
	}else{
		return true;
	}
}