<#-- 导航条  -->
<#macro nav title="" toggleSearch=false>
<div class="nav2">
  <span>
    <img width="28" height="32" align="absmiddle" src="${base}pics/kj/kjst_r4_c9_s1.jpg">
  </span>
  <span>
  	${title}
  	<#if toggleSearch>
  		<div onclick='toggleSearch();' id='searchBarCtl' class='searchBarCtl'>搜索</div>
  		<script type="text/javascript">
		function toggleSearch(){
			if($("#search").is(":hidden")){
				$("#search").slideDown();
				$("#searchBarCtl").html("关闭搜索");
			}else{
				$("#search").slideUp();
				$("#searchBarCtl").html("搜索");
			}
		}
		$(document).ready(function(){
			$("#search").hide();
		});
		</script>
  	</#if>
  	<#nested>
  </span>
</div>
</#macro>

<#-- 搜索条 -->
<#macro searchBar>
<div id="search">
  <table class="singleTable" width="100%" cellspacing="1" cellpadding="0">
    <#nested>
    <tr>
      <td class="txtlabel">&nbsp;</td>
	  <td class="txtcontbtn">
	    <input type="submit" value="" class="searchBtn"/>
      </td>
    </tr>
  </table>
</div>
</#macro>	

<#-- 按钮条 -->
<#macro ctrlBar>
<div class="ctrlbar">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td><#nested></td>
    </tr>
  </table>
</div>
</#macro>