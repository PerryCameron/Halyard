package com.ecsail.main;

import com.jcraft.jsch.*;
import javax.swing.*;

public class PortForwardingL{
  public PortForwardingL(){

    int lport;
    String rhost;
    int rport;

    try{
      JSch jsch=new JSch();

      String host=null;
      host="pcameron@eaglecreeksailing.com";
      String user=host.substring(0, host.indexOf('@'));
     // String user="root";
      host=host.substring(host.indexOf('@')+1);


      Session session=jsch.getSession(user, host, 22);
      
      lport=3306;
      rhost="localhost";
      rport=3306;

      // username and password will be given via UserInfo interface.
      UserInfo ui=new MyUserInfo();
      session.setUserInfo(ui);

      session.connect();

      //Channel channel=session.openChannel("shell");
      //channel.connect();

      int assinged_port=session.setPortForwardingL(lport, rhost, rport);
      System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);
    }
    catch(Exception e){
      System.out.println(e);
    }
  }

  public static class MyUserInfo implements UserInfo, UIKeyboardInteractive{
    String passwd="Pa25191Z345!";
	public String getPassword(){ return passwd; }
    public boolean promptYesNo(String str){
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
    public boolean promptPassword(String message){
		return true;

}
	public String[] promptKeyboardInteractive(String destination, String name, String instruction, String[] prompt,
			boolean[] echo) {
		// TODO Auto-generated method stub
		return null;
	}
	public void showMessage(String message) {
		// TODO Auto-generated method stub
		
	}
  }
}