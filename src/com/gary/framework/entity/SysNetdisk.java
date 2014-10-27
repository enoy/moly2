package com.gary.framework.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 网盘
 * @author gary
 *
 */
public class SysNetdisk implements Serializable {

	private static final long serialVersionUID = -4804799290325538073L;

	private Integer id;
	/**
	 * 文件网址
	 */
	private String fileUrl;
	/**
	 * 文件名
	 */
	private String fileName;
	/**
	 * 文件说明
	 */
	private String fileDesc;
	/**
	 * 文件大小
	 */
	private String fileSize;
	/**
	 * 下载次数
	 */
	private Long fileHit;
	/**
	 * 上传人ID
	 */
	private Integer fileOwner;
	/**
	 * 上传时间
	 */
	private Date uploadTime;
	/**
	 * 有效
	 */
	private Integer enable;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileDesc() {
		return fileDesc;
	}
	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public Long getFileHit() {
		return fileHit;
	}
	public void setFileHit(Long fileHit) {
		this.fileHit = fileHit;
	}
	public Integer getFileOwner() {
		return fileOwner;
	}
	public void setFileOwner(Integer fileOwner) {
		this.fileOwner = fileOwner;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Netdisk [id=");
		builder.append(id);
		builder.append(", fileUrl=");
		builder.append(fileUrl);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", fileDesc=");
		builder.append(fileDesc);
		builder.append(", fileSize=");
		builder.append(fileSize);
		builder.append(", fileHit=");
		builder.append(fileHit);
		builder.append(", fileOwner=");
		builder.append(fileOwner);
		builder.append(", uploadTime=");
		builder.append(uploadTime);
		builder.append(", enable=");
		builder.append(enable);
		builder.append("]");
		return builder.toString();
	}
	
}
