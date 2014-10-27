<@p.header title="您没有访问权限"/>

	<div class="nav2" id="memoryInfo">
		<span style="background-color:#1F72B8;">
			<img width="28" height="34" align="absmiddle" src="${base }pics/kj/kjst_r4_c9_s1.jpg">
		</span>
		<span>
			内存信息
			<div onclick="toggleMemory();" id="memoryBarCtl" class="searchBarCtl">关闭内存信息</div>
			<script type='text/javascript'>
				function toggleMemory(){
					if($('#memoryTab').is(':hidden')){
						$('#memoryTab').slideDown();
						$('#memoryBarCtl').html('关闭内存信息');
					}else{
						$('#memoryTab').slideUp();
						$('#memoryBarCtl').html('显示内存信息');
					}
				}
			</script>
		</span>
	</div>

	<div class="singleTableContainer" id="memoryTab">
	  <table width="100%" cellpadding="0" cellspacing="1" class="singleTable">
		<tr>
		  <td width="15%" class="txtlabel">free memory：</td>
		  <td width="85%" class="txtcont">
		  	${mi.freeMemory} (${mi.freeMemoryFormat})
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">max memory：</td>
		  <td class="txtcont">
		  	${mi.maxMemory} (${mi.maxMemoryFormat})
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">total memory：</td>
		  <td class="txtcont">
		  	${mi.totalMemory} (${mi.totalMemoryFormat})
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">heap init：</td>
		  <td class="txtcont">
		  	${mi.heapInit} (${mi.heapInitFormat})
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">heap committed：</td>
		  <td class="txtcont">
		  	${mi.heapCommitted} (${mi.heapCommittedFormat})
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">heap max：</td>
		  <td class="txtcont">
		  	${mi.heapMax} (${mi.heapMaxFormat})
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">heap used：</td>
		  <td class="txtcont">
		  	${mi.heapUsed} (${mi.heapUsedFormat})
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">nonheap init：</td>
		  <td class="txtcont">
		  	${mi.nonHeapInit} (${mi.nonHeapInitFormat})
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">nonheap committed：</td>
		  <td class="txtcont">
		  	${mi.nonHeapCommitted} (${mi.nonHeapCommittedFormat})
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">nonheap max：</td>
		  <td class="txtcont">
		  	${mi.nonHeapMax} (${mi.nonHeapMaxFormat})
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">nonheap used：</td>
		  <td class="txtcont">
		  	${mi.nonHeapUsed} (${mi.nonHeapUsedFormat})
		  </td>
		</tr>
	  </table>
	</div>
	
	<div class="nav2" id="memoryInfo">
		<span style="background-color:#1F72B8;">
			<img width="28" height="34" align="absmiddle" src="${base }pics/kj/kjst_r4_c9_s1.jpg">
		</span>
		<span>
			系统信息
			<div onclick="toggleSys();" id="sysBarCtl" class="searchBarCtl">显示系统信息</div>
			<script type='text/javascript'>
				function toggleSys(){
					if($('#sysTab').is(':hidden')){
						$('#sysTab').slideDown();
						$('#sysBarCtl').html('关闭系统信息');
					}else{
						$('#sysTab').slideUp();
						$('#sysBarCtl').html('显示系统信息');
					}
				}
			</script>
		</span>
	</div>

	<div class="singleTableContainer" id="sysTab" style="display:none;">
	  <table width="100%" cellpadding="0" cellspacing="1" class="singleTable">
		<tr>
		  <td width="15%" class="txtlabel">os name：</td>
		  <td width="85%" class="txtcont">
		  	${si.osName}
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">os version：</td>
		  <td class="txtcont">
		  	${si.osVersion}
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">os arch：</td>
		  <td class="txtcont">
		  	${si.osArch}
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">user dir：</td>
		  <td class="txtcont">
		  	${si.userDir}
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">java runtime version：</td>
		  <td class="txtcont">
		  	${si.jVer}
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">java home：</td>
		  <td class="txtcont">
		  	${si.javaHome}
		  </td>
		</tr>
		<tr>
		  <td class="txtlabel">java io tempdir：</td>
		  <td class="txtcont">
		  	${si.javaIOTmpdir}
		  </td>
		</tr>
	  </table>
	</div>
	
<@p.footer/>