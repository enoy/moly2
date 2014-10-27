<@p.header containerDiv=false/>
<div id="leftmain">
  <#if menuList?exists>
    <#list menuList as m>
  	  <dl class="menu">
		<dt class="mu1"><span>${m.menuName?default("主菜单")}</span></dt>
	    
	    <#if m.subMenus?exists>
	      <#list m.subMenus as sub>
		    <dd><a href="${(base + sub.menuUrl)?default("#")}" target="main">${sub.menuName?default("子菜单")}</a></dd>
		  </#list>
		</#if>
	  </dl>  
    </#list>
  </#if>

  <div style="height:20px;"></div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$(".menu").click(function(){
		$("dd").hide();
		$(this).find("dd").show();
	});
	$(".menu").first().click();
});
</script>
<@p.footer containerDiv=false/>