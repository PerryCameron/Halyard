package com.ecsail.main;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_Person;

public class CreateMembership {

	public CreateMembership() {
	}
	
	public static Object_Person createUser(int msid) {
		
		int pid = SqlSelect.getCount() + 1;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO person () VALUES (" + pid  +"," + msid + ",1,'','',null,'','',true,null);"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Object_Person(pid,msid,1,"","",null,"","",true);
	}
	
	public static void Create() {
		
		int msid = SqlSelect.getMSIDCount() + 1;
		int membership_id = SqlSelect.getMembershipIDCount() +1;
		int pid = SqlSelect.getCount() + 1;
		Note newMemNote = new Note();
		int note_id = newMemNote.getCount() + 1;
		// primary user creation is done in TabMembership();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now(); 
		String date = dtf.format(now);
		Object_MembershipList newMembership = new Object_MembershipList(msid, pid, membership_id, date,true, "FM", "", "", "", 0, "", "", "", "");
		if(SqlInsert.addMembershipIsSucessful(newMembership)) {
			newMemNote.addMemo(note_id,msid, date, "Created new membership record");  // adds a note that the membership was created.
			Main.activememberships.add(newMembership);
			TabLauncher.createTab(newMembership.getMembershipId(),newMembership.getMsid()); 
			
		}
	}
}


