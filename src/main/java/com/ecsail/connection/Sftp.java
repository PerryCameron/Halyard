package com.ecsail.connection;

/* -*-mode:java; c-basic-offset:2; indent-tabs-mode:nil -*- */
/**
 * This program will demonstrate the sftp protocol support.
 *   $ CLASSPATH=.:../build javac Sftp.java
 *   $ CLASSPATH=.:../build java Sftp
 * You will be asked username, host and passwd. 
 * If everything works fine, you will get a prompt 'sftp>'. 
 * 'help' command will show available command.
 * In current implementation, the destination path for 'get' and 'put'
 * commands must be a file, not a directory.
 *
 */
import com.jcraft.jsch.*;

import java.util.ArrayList;
import java.util.Vector;

import com.ecsail.main.*;
import com.ecsail.main.TestSftp.MyProgressMonitor;

public class Sftp{
	JSch jsch;
	Session session;
	ChannelSftp c;
	
  public Sftp(){
	  this.jsch = Main.getConnect().getForwardedConnection().getJsch();
	  this.session = Main.getConnect().getForwardedConnection().getSession();
	  connectSession();
	  System.out.println("session=" + session);
  }
  
	public void connectSession() {
		Channel channel;
		try {
			channel = session.openChannel("sftp");
			channel.connect();
			this.c = (ChannelSftp) channel;
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeSession() {
		c.quit();
		System.out.println("ftp session closed");
	}
	
	public void pwd() {
		System.out.println(c.lpwd());
	}
	
	public void mkdir(String path) {
		try {
			c.mkdir(path);
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendFile(String sendFrom, String sendTo) {
	      try {
	    	SftpProgressMonitor monitor=new MyProgressMonitor();
	    	int mode=ChannelSftp.OVERWRITE;  
			c.put(sendFrom, sendTo, monitor, mode);
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getFile(String getFrom, String getTo) {
	      try {
	    	SftpProgressMonitor monitor=new MyProgressMonitor();
	    	int mode=ChannelSftp.OVERWRITE;  
			c.get(getFrom, getTo, monitor, mode);
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList<String> ls(String dir) {
		ArrayList<String> fileNames = new ArrayList<String>();
		Vector files = new Vector();
		try {
			files = c.ls(dir);
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i =0; i < files.size(); i++) {
			if(!files.get(i).toString().substring(56).startsWith("."))
			fileNames.add(files.get(i).toString().substring(56));
		}
		return fileNames;
	}
}