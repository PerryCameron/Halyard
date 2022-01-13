package com.ecsail.main;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.select.SqlMembership_Id;
import com.ecsail.sql.select.SqlPerson;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.structures.MembershipListDTO;
import com.ecsail.structures.MemoDTO;
import com.ecsail.structures.PersonDTO;

public class CreateMembership {

	public static PersonDTO createUser(int msid) {
		// create a main person for the membership
		int pid = SqlPerson.getCount() + 1;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO person () VALUES (" + pid  +"," + msid + ",1,'','',null,'','',true,null,null);"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new PersonDTO(pid,msid,1,"","",null,"","",true,null);
	}
	
	public static void Create() { // create a membership
		// makes sure we don't have a New Membership tab open
		if (!Launcher.tabOpen("New Membership")) {
			// int ms_id = SqlSelect.getMSIDCount() + 1;
			int ms_id = SqlSelect.getCount("membership", "ms_id") + 1;
			// int membership_id = SqlSelect.getMembershipIDCount() +1;
			int membership_id = SqlMembership_Id.getHighestMembershipId(HalyardPaths.getYear()) + 1;
			int mid = SqlSelect.getCount("membership_id", "mid") + 1;

			int pid = SqlPerson.getCount() + 1;
			Note newMemNote = new Note();
			int note_id = newMemNote.getCount() + 1;
			// primary user creation is done in TabMembership();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			String date = dtf.format(now);
			MembershipListDTO newMembership = new MembershipListDTO(ms_id, pid, membership_id, date, "FM", "",
					"", "", 0, "", "", "", "", HalyardPaths.getYear());
			if (SqlInsert.addMembershipIsSucessful(newMembership)) {
				newMemNote.addMemo(new MemoDTO(note_id, ms_id, date, "Created new membership record", 0, "N"));
				Main.activememberships.add(newMembership);
				//SqlInsert.addMembershipId(new Object_MembershipId(mid, Paths.getYear(), ms_id, membership_id + "", true,
				//		"RM", false, false));
				Launcher.createMembershipTabForRoster(newMembership.getMembershipId(), newMembership.getMsid());
			}
		} else {
			Launcher.tabPane.getSelectionModel().select(Launcher.getTabIndex("New Membership"));
		}
	}
}


