package com.gary.framework.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.gary.framework.core.IsManageController;
import com.gary.framework.entity.SysNetdisk;
import com.gary.framework.service.SysNetdiskService;
import com.gary.framework.thread.CleanUpFileThread;
import com.gary.framework.util.DateUtil;
import com.gary.framework.util.FileUtil;
import com.gary.framework.util.WebUtil;

/**
 * 网盘管理
 * @author gary
 */
@Controller
public class SysNetdiskController extends BaseController implements IsManageController {

	private static final String REDIRECT_TO_LIST = "redirect:/SysNetdisk/list.html";
	private static final String REDIRECT_TO_RECYCLE = "redirect:/SysNetdisk/recycle.html";
	
	//上传文件限制大小
	private static final String UPLOAD_SIZE_LIMIT = "1GB";
	
	/**
	 * 列表页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/manage/SysNetdisk/list");
		
		String fileName = request.getParameter("fileName");
		String fileDesc = request.getParameter("fileDesc");
		String fileOwnerNickName = request.getParameter("fileOwnerNickName");
		String uploadTimeFrom = request.getParameter("uploadTimeFrom");
		String uploadTimeTo = request.getParameter("uploadTimeTo");
		
		setPagingList(getServiceManager().getSysNetdiskService().getPaging(fileName, fileDesc, 
				fileOwnerNickName, uploadTimeFrom, uploadTimeTo));
		
		request.setAttribute("fileName", fileName);
		request.setAttribute("fileDesc", fileDesc);
		request.setAttribute("fileOwnerNickName", fileOwnerNickName);
		request.setAttribute("uploadTimeFrom", uploadTimeFrom);
		request.setAttribute("uploadTimeTo", uploadTimeTo);
		
		return mav;
	}
	
	/**
	 * 显示文件上传页面
	 * @param request
	 * @param response
	 * @param sysNetdisk
	 * @return
	 * @throws Exception
	 */
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("sizeLimit", UPLOAD_SIZE_LIMIT);
		return new ModelAndView("/manage/SysNetdisk/save");
	}
	
	/**
	 * 保存上传文件
	 * @param request
	 * @param response
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView doSave(HttpServletRequest request,
			HttpServletResponse response) {
		Enumeration<String> names = request.getParameterNames();
		final String namePrefix = "f_fileUrl_";
		
		Integer enable = Integer.valueOf(1);
		Integer fileOwner = WebUtil.getLoginUserId();
		Date uploadTime = DateUtil.getCurrentDate();

		String dir = WebUtil.getRealPath(SysNetdiskService.NET_DISK_BASE_PATH) + File.separator;
        
		while(names.hasMoreElements()){
        	String name = names.nextElement();
        	if(name.startsWith(namePrefix)){
        		String index = name.substring(namePrefix.length());
        		
        		String fileUrl = request.getParameter(name);
        		String fileName = request.getParameter("f_fileName_" + index);
        		String fileDesc = request.getParameter("f_fileDesc_" + index);
        		//获取上传文件大小,顺便检验文件是否存在
        		File uploadFile = new File(dir + fileUrl);
        		if(uploadFile.exists()){
        			SysNetdisk sysNetdisk = new SysNetdisk();
        			sysNetdisk.setEnable(enable);
        			sysNetdisk.setFileDesc(fileDesc);
        			sysNetdisk.setFileName(fileName);
        			sysNetdisk.setFileOwner(fileOwner);
        			sysNetdisk.setFileHit(0L);
        			sysNetdisk.setFileSize(FileUtil.getFileSize(uploadFile.length()));
        			sysNetdisk.setFileUrl(fileUrl);
        			sysNetdisk.setUploadTime(uploadTime);
        			
        			getServiceManager().getSysNetdiskService().save(sysNetdisk);
        		}else{
        			logger.error("上传文件不存在:" + uploadFile.getAbsolutePath());
        		}
        	}
        }
		return new ModelAndView(REDIRECT_TO_LIST);
	}
	
	/**
	 * 下载文件
	 * @param request
	 * @param response
	 */
	public void download(HttpServletRequest request,
			HttpServletResponse response) {
		Integer id = getId(request);
		SysNetdisk sysNetdisk = getServiceManager().getSysNetdiskService().getById(id);
		
		//更新下载次数
		getServiceManager().getSysNetdiskService().updateFileHit(id);
		
		String dir = WebUtil.getRealPath(SysNetdiskService.NET_DISK_BASE_PATH) + File.separator;
		File file = new File(dir + sysNetdisk.getFileUrl());
		
		try {
			WebUtil.downloadFile(request, response, file, sysNetdisk.getFileName());
		} catch (FileNotFoundException e){
			try {
				addReturnMsg("文件不存在");
				request.getRequestDispatcher("list.html").forward(request, response);
			} catch (Exception e1) {
				logger.error("forward faild", e1);
			}
		} catch (IOException e) {
			if("ClientAbortException".equals(e.getClass().getSimpleName())){
				logger.debug("客户端取消下载,sysNetdisk:" + sysNetdisk);
			}else{
				logger.error("download file faild, id:" + id, e);
			}
		}
	}
	
	/**
	 * 修改文件页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView update(HttpServletRequest request,
			HttpServletResponse response) {
		SysNetdisk sysNetdisk = getSysNetdiskById(request);
		return new ModelAndView("/manage/SysNetdisk/update", "sysNetdisk", sysNetdisk);
	}

	/**
	 * 修改文件基本信息
	 * @param request
	 * @param response
	 * @param sysNetdisk
	 * @return
	 */
	public ModelAndView doUpdate(HttpServletRequest request,
			HttpServletResponse response, SysNetdisk sysNetdisk) {
		getServiceManager().getSysNetdiskService().updateInfo(sysNetdisk);
		addReturnMsg(MESSAGE_UPDATE_SUCCESS);
		return new ModelAndView(REDIRECT_TO_LIST);
	}
	
	/**
	 * 查看文件详细信息
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response) {
		SysNetdisk sysNetdisk = getSysNetdiskById(request);
		ModelAndView mav = new ModelAndView("/manage/SysNetdisk/detail");
		mav.addObject("sysNetdisk", sysNetdisk);
		return mav;
	}
	
	/**
	 * 根据id获取文件
	 * @param request
	 * @return
	 */
	private SysNetdisk getSysNetdiskById(HttpServletRequest request){
		return getServiceManager().getSysNetdiskService().getById(getId(request));
	}
	
	/**
	 * 删除文件
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {
		getServiceManager().getSysNetdiskService().deleteById(getId(request));
		addReturnMsg(MESSAGE_UPDATE_SUCCESS);
		return new ModelAndView(REDIRECT_TO_LIST);
	}
	
	/**
	 * 清理多余文件
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView cleanUp(HttpServletRequest request,
			HttpServletResponse response) {
		new CleanUpFileThread(getServiceManager().getSysNetdiskService()).start();
		addReturnMsg("后台开始清理多余文件");
		return new ModelAndView(REDIRECT_TO_LIST);
	}
	
	/**
	 * 回收站
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView recycle(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/manage/SysNetdisk/recycle");
		
		String fileName = request.getParameter("fileName");
		String fileDesc = request.getParameter("fileDesc");
		String fileOwnerNickName = request.getParameter("fileOwnerNickName");
		String uploadTimeFrom = request.getParameter("uploadTimeFrom");
		String uploadTimeTo = request.getParameter("uploadTimeTo");
		
		setPagingList(getServiceManager().getSysNetdiskService().getRecyclePaging(fileName, fileDesc, 
				fileOwnerNickName, uploadTimeFrom, uploadTimeTo));
		
		request.setAttribute("fileName", fileName);
		request.setAttribute("fileDesc", fileDesc);
		request.setAttribute("fileOwnerNickName", fileOwnerNickName);
		request.setAttribute("uploadTimeFrom", uploadTimeFrom);
		request.setAttribute("uploadTimeTo", uploadTimeTo);
		
		return mav;
	}
	
	/**
	 * 恢复文件
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView restore(HttpServletRequest request,
			HttpServletResponse response) {
		getServiceManager().getSysNetdiskService().restore(getId(request));
		addReturnMsg("恢复成功");
		return new ModelAndView(REDIRECT_TO_RECYCLE);
	}
	
	/**
	 * 彻底删除文件
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView forceDelete(HttpServletRequest request,
			HttpServletResponse response) {
		getServiceManager().getSysNetdiskService().forceDeleteById(getId(request));
		addReturnMsg("彻底删除成功");
		return new ModelAndView(REDIRECT_TO_RECYCLE);
	}
	
	/**
	 * 清空回收站
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView emptyRecycle(HttpServletRequest request,
			HttpServletResponse response) {
		getServiceManager().getSysNetdiskService().emptyRecycle();
		addReturnMsg("清空回收站成功");
		return new ModelAndView(REDIRECT_TO_RECYCLE);
	}
	
}
