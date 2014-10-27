<#-- 禁用缓存 -->
<#macro nocache>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
</#macro>

<#--	jquery ui dialog	-->
<#macro dialog context>
<div id="dialog-confirm" style="display:none;">
	<p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		${context }
	</p>
</div>
</#macro>

<#--	解析连接		-->
<#function parseLink link>
	<#switch link>
		<#case "@back"><#return "javascript:history.go(-1)"><#break>
		<#case "@close"><#return "javascript:window.close()"><#break>
	<#default>
		<#return link>
	</#switch>
</#function>


<#-- 引入CSS -->
<#function importCSS src>
	<#return "<link href=\"${src}\" rel=\"stylesheet\" type=\"text/css\" />">
</#function>

<#-- 引入JS -->
<#function importJS src>
	<#return "<script language=\"javascript\" src=\"${src}\"></script>">
</#function>