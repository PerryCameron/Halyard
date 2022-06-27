package com.ecsail.connection;

import com.ecsail.main.Halyard;
import com.jcraft.jsch.*;

import java.util.ArrayList;
import java.util.Vector;

import com.ecsail.main.TestSftp.MyProgressMonitor;

public class Sftp{
	JSch jsch;
	Session session;
	ChannelSftp c;
	
  public Sftp(JSch jsch, Session session){
	  this.jsch = jsch;
	  this.session = session;
	  connectSession();
	  Halyard.getLogger().info("Sftp Session Started: " + session);
  }
  
	public void connectSession() {
		Channel channel;
		try {
			channel = session.openChannel("sftp");
			channel.connect();
			this.c = (ChannelSftp) channel;
		} catch (JSchException e) {
			Halyard.getLogger().error("There was a problem starting the sftp session");
			Halyard.getLogger().error(e.getMessage());
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
	

	public ArrayList<String> ls(String dir) {
		ArrayList<String> fileNames = new ArrayList<String>();
		Vector<?> files = new Vector<>();
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