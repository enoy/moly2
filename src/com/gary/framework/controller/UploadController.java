package com.gary.framework.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gary.framework.core.Anonymous;
import com.gary.framework.util.FileUtil;
import com.gary.framework.util.ResponseUtils;

/**
 * 上传
 * @author ztb
 * 2014-3-7 下午2:47:40
 *
 */
@Controller
public class UploadController extends BaseController implements Anonymous {

	/**
	 * 上传并返回url
	 * @author ztb
	 * 2014-3-7 下午2:47:45
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void returnUrl(HttpServletRequest request,
			HttpServletResponse response) {
		String folderName = request.getParameter("folderName");
		if(StringUtils.isBlank(folderName)){
			folderName = "default";
		}
		String path = getRealPath(request,  "upload" + File.separator + folderName + File.separator);
		File dir = new File(path);

		if(!dir.exists()){
			dir.mkdir();
		}
    	
        MultipartHttpServletRequest multipart = (MultipartHttpServletRequest)request;

        MultipartFile file = multipart.getFile("file");

        if(file == null || file.isEmpty()) {
            // 文件不存在
            try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			} catch (IOException e) {
				logger.error("faild to return 400", e);
			}
        }
            
        File finalFile = new File(dir.getAbsolutePath() + File.separator + 
				FileUtil.generateFileName(file.getOriginalFilename()));
        
        try {
			FileCopyUtils.copy(file.getBytes(), finalFile);
		} catch (IOException e) {
			logger.error("faild to copy file", e);
		}
        
        if (logger.isDebugEnabled()) {
        	logger.debug(file.getOriginalFilename() + "上传并重命名为: " + dir.getAbsolutePath() + File.separator
					+ finalFile.getName());
		}
        
//        String finalUrl = "upload/" + folderName + "/" + finalFile.getName();
        String finalUrl = finalFile.getName();
        
        ResponseUtils.renderText(response, finalUrl);
	}
}
