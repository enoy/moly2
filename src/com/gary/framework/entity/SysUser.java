package com.gary.framework.entity;

/**
 * 用户表
 * @author ztb
 * 2014-2-14 下午1:46:39
 *
 */
public class SysUser implements java.io.Serializable {

	private static final long serialVersionUID = -2678578882130857276L;

	private Integer id;
	/**
	 * 登录用户名
	 */
	private String userName;
	/**
	 * 登录密码
	 */
	private String password;
	/**
	 * 用户姓名
	 */
	private String nickName;
	
	/**
	 * 最后登录IP
	 */
	private String lastLoginIp;
	
	/**
	 * 最后登录时间
	 */
	private String lastLoginTime;
	
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public SysUser(){
	}
	
	public SysUser(Integer id, String userName, String password, String nickName,
			String lastLoginIp, String lastLoginTime, Integer enable) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.nickName = nickName;
		this.lastLoginIp = lastLoginIp;
		this.lastLoginTime = lastLoginTime;
		this.enable = enable;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Users [id=");
		builder.append(id);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", password=");
		builder.append(password);
		builder.append(", nickName=");
		builder.append(nickName);
		builder.append(", lastLoginIp=");
		builder.append(lastLoginIp);
		builder.append(", lastLoginTime=");
		builder.append(lastLoginTime);
		builder.append(", enable=");
		builder.append(enable);
		builder.append("]");
		return builder.toString();
	}

}