package com.ecsail.structures;

import java.io.Serializable;

public class Object_Login implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7434038316336853182L;
	private String port;
	private String host;
	private String user;
	private String passwd;
	private boolean isDefault;
	
	public Object_Login(String port, String host, String user, String passwd, boolean isDefault) {
		this.port = port;
		this.host = host;
		this.user = user;
		this.passwd = passwd;
		this.isDefault = isDefault;
	}
	
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Object_Login [port=" + port + ", host=" + host + ", user=" + user + ", passwd=" + passwd
				+ ", isDefault=" + isDefault + "]";
	}
	
}
