
package com.ecsail.connection;

import com.jcraft.jsch.*;
import javax.swing.*;
// https://dentrassi.de/2015/07/13/programmatically-adding-a-host-key-with-jsch/
public class PortForwardingL {
	static String passwd;
	static JSch jsch = new JSch();
	static Session session;
  
  public PortForwardingL (String host, String rhost, int lport,int rport, String user, String password) {    //int lport;
	  PortForwardingL.passwd = password;

		try {
			//JSch jsch 
			jsch.setKnownHosts("/Users/parrishcameron/.ssh/known_hosts");

			HostKeyRepository hkr = jsch.getHostKeyRepository();
			HostKey[] hks = hkr.getHostKey();
			if (hks != null) {
				System.out.println("Host keys in " + hkr.getKnownHostsRepositoryID());
				for (int i = 0; i < hks.length; i++) {
					HostKey hk = hks[i];
					System.out.println(hk.getHost() + " " + hk.getType() + " " + hk.getFingerPrint(jsch));
				}
				System.out.println("");
			}
      
      session=jsch.getSession(user, host, 22);
      UserInfo ui=new MyUserInfo();
      session.setUserInfo(ui);
      session.connect();
      int assinged_port=session.setPortForwardingL(lport, rhost, rport);
      System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);
    }
    catch(Exception e){
      System.out.println(e);
    }
  }
  
  public static class MyUserInfo implements UserInfo {
    
	  public String getPassword(){ return passwd; }
	  
	  public boolean promptYesNo(String str){
		  // change to java fx
      Object[] options={ "yes", "no" };
      int foo=JOptionPane.showOptionDialog(null, 
             str,
             "Warning", 
             JOptionPane.DEFAULT_OPTION, 
             JOptionPane.WARNING_MESSAGE,
             null, options, options[0]);
       return foo==0;
    }

    public String getPassphrase(){ return null; }
    
    public boolean promptPassphrase(String message){ return true; }
    
    public boolean promptPassword(String message){ return true; }
    
    public void showMessage(String message){
    	/// put in a JavaFX message display here.
    }
  }

public static JSch getJsch() {
	return jsch;
}

public static void setJsch(JSch jsch) {
	PortForwardingL.jsch = jsch;
}

public static Session getSession() {
	return session;
}

public static void setSession(Session session) {
	PortForwardingL.session = session;
}
  
}