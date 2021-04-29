package com.ecsail.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.main.Paths;
import com.ecsail.structures.Object_Membership;
import com.ecsail.structures.Object_MembershipList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Sql_SelectMembership {
	

	public static ObservableList<Object_MembershipList> getRosterOfKayakRackOwners(String year) {
		ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor(
					"Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip \n"
					+ "from slip s \n"
					+ "right join membership m on m.MS_ID=s.MS_ID \n"
					+ "left join membership_id id on m.MS_ID=id.MS_ID\n"
					+ "left join money mo on m.MS_ID=mo.MS_ID \n"
					+ "left join person p on p.MS_ID=m.MS_ID \n"
					+ "where mo.FISCAL_YEAR='"+year+"' and id.fiscal_year='"+year+"' and id.renew=1 and kayak_rack=1 and p.member_type=1 order by membership_id;"));
			while (rs.next()) {
				rosters.add(new Object_MembershipList(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						rs.getInt("MEMBERSHIP_ID"), 
						rs.getString("JOIN_DATE"), 
						rs.getString("MEM_TYPE"), 
						rs.getString("SLIP_NUM"), 
						rs.getString("L_NAME"),
						rs.getString("F_NAME"), 
						rs.getInt("SUBLEASED_TO"), 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP"),
						rs.getString("FISCAL_YEAR")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Creating Roster list for " + year + "...");
		return rosters;
	}
	
	public static ObservableList<Object_MembershipList> getRosterOfKayakShedOwners(String year) {
		ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor(
					"Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip \n"
					+ "from slip s \n"
					+ "right join membership m on m.MS_ID=s.MS_ID \n"
					+ "left join membership_id id on m.MS_ID=id.MS_ID\n"
					+ "left join money mo on m.MS_ID=mo.MS_ID \n"
					+ "left join person p on p.MS_ID=m.MS_ID \n"
					+ "where mo.FISCAL_YEAR='"+year+"' and id.fiscal_year='"+year+"' and id.renew=1 and kayak_shed=1 and p.member_type=1 order by membership_id;"));
			while (rs.next()) {
				rosters.add(new Object_MembershipList(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						rs.getInt("MEMBERSHIP_ID"), 
						rs.getString("JOIN_DATE"), 
						rs.getString("MEM_TYPE"), 
						rs.getString("SLIP_NUM"), 
						rs.getString("L_NAME"),
						rs.getString("F_NAME"), 
						rs.getInt("SUBLEASED_TO"), 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP"),
						rs.getString("FISCAL_YEAR")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Creating Roster list for " + year + "...");
		return rosters;
	}
	
	public static ObservableList<Object_MembershipList> getRoster(String year, boolean isActive) {
		ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor(
					"Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip "
							+ "from slip s "
							+ "right join membership m on m.MS_ID=s.MS_ID "
							+ "left join membership_id id on m.MS_ID=id.MS_ID "
							+ "left join person p on p.MS_ID=m.MS_ID "
							+ "where id.FISCAL_YEAR='" + year + "' and p.MEMBER_TYPE=1 and id.RENEW=" + isActive + " order by membership_id"));
			while (rs.next()) {
				rosters.add(new Object_MembershipList(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						rs.getInt("MEMBERSHIP_ID"), 
						rs.getString("JOIN_DATE"), 
						rs.getString("MEM_TYPE"), 
						rs.getString("SLIP_NUM"), 
						rs.getString("L_NAME"),
						rs.getString("F_NAME"), 
						rs.getInt("SUBLEASED_TO"), 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP"),
						rs.getString("FISCAL_YEAR")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Creating Roster list for " + year + "...");
		return rosters;
	}
	
	public static ObservableList<Object_MembershipList> getRosterOfAllActiveMembers(String year) {
		ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor(
					"Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip "
							+ "from slip s "
							+ "right join membership m on m.MS_ID=s.MS_ID "
							+ "left join membership_id id on m.MS_ID=id.MS_ID "
							+ "left join person p on p.MS_ID=m.MS_ID "
							+ "where id.FISCAL_YEAR='" + year + "' and id.RENEW=true order by membership_id"));
			while (rs.next()) {
				rosters.add(new Object_MembershipList(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						rs.getInt("MEMBERSHIP_ID"), 
						rs.getString("JOIN_DATE"), 
						rs.getString("MEM_TYPE"), 
						rs.getString("SLIP_NUM"), 
						rs.getString("L_NAME"),
						rs.getString("F_NAME"), 
						rs.getInt("SUBLEASED_TO"), 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP"),
						rs.getString("FISCAL_YEAR")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Creating Roster list for " + year + "...");
		return rosters;
	}
	
	public static ObservableList<Object_MembershipList> getRosterOfAll(String year) {
		ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor(
					"Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip "
							+ "from slip s "
							+ "right join membership m on m.MS_ID=s.MS_ID "
							+ "left join membership_id id on m.MS_ID=id.MS_ID "
							+ "left join person p on p.MS_ID=m.MS_ID "
							+ "where id.FISCAL_YEAR='" + year + "' and p.MEMBER_TYPE=1 order by membership_id"));
			while (rs.next()) {
				rosters.add(new Object_MembershipList(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						rs.getInt("MEMBERSHIP_ID"), 
						rs.getString("JOIN_DATE"), 
						rs.getString("MEM_TYPE"), 
						rs.getString("SLIP_NUM"), 
						rs.getString("L_NAME"),
						rs.getString("F_NAME"), 
						rs.getInt("SUBLEASED_TO"), 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP"),
						rs.getString("FISCAL_YEAR")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Creating Roster list for " + year + "...");
		return rosters;
	}
	
	public static ObservableList<Object_MembershipList> getRosterOfSlipOwners(String year) {
		ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor(
					"Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip "
					+ "from slip s "
					+ "inner join membership m on s.ms_id=m.ms_id "
					+ "left join membership_id id on m.MS_ID=id.MS_ID "
					+ "left join person p on p.MS_ID=m.MS_ID "
					+ "where p.MEMBER_TYPE=1 and FISCAL_YEAR="+ Paths.getYear()));
			while (rs.next()) {
				rosters.add(new Object_MembershipList(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						rs.getInt("MEMBERSHIP_ID"), 
						rs.getString("JOIN_DATE"), 
						rs.getString("MEM_TYPE"), 
						rs.getString("SLIP_NUM"), 
						rs.getString("L_NAME"),
						rs.getString("F_NAME"), 
						rs.getInt("SUBLEASED_TO"), 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP"),
						rs.getString("FISCAL_YEAR")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Creating Roster list for " + year + "...");
		return rosters;
	}
	
	public static ObservableList<Object_MembershipList> getRosterOfSubleasedSlips() {
		ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor(
					"Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip from slip s "
					+ "inner join membership m on s.ms_id=m.ms_id "
					+ "left join membership_id id on m.MS_ID=id.MS_ID "
					+ "left join person p on p.MS_ID=s.subleased_to "
					+ "where subleased_to IS NOT NULL and p.MEMBER_TYPE=1 and FISCAL_YEAR="+ Paths.getYear()));
			while (rs.next()) {
				rosters.add(new Object_MembershipList(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						rs.getInt("MEMBERSHIP_ID"), 
						rs.getString("JOIN_DATE"), 
						rs.getString("MEM_TYPE"), 
						rs.getString("SLIP_NUM"), /// shows subleased slip
						rs.getString("L_NAME"),
						rs.getString("F_NAME"), 
						rs.getInt("SUBLEASED_TO"), 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP"),
						rs.getString("FISCAL_YEAR")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rosters;
	}
	
	public static Object_MembershipList getMembershipList(int ms_id, String year) {
		System.out.println("msid=" + ms_id);
		Object_MembershipList thisMembership = null;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(
					"Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,"
					+ "id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,"
					+ "m.zip from slip s right join membership m on m.MS_ID=s.MS_ID left join membership_id "
					+ "id on m.MS_ID=id.MS_ID left join person p on p.MS_ID=m.MS_ID where id.FISCAL_YEAR='" + year + "' "
					+ "and m.ms_id=" + ms_id));
			while (rs.next()) {
				thisMembership = new Object_MembershipList(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						rs.getInt("MEMBERSHIP_ID"), 
						rs.getString("JOIN_DATE"), 
						rs.getString("MEM_TYPE"), 
						rs.getString("SLIP_NUM"), 
						rs.getString("L_NAME"),
						rs.getString("F_NAME"), 
						rs.getInt("SUBLEASED_TO"), 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP"),
						rs.getString("FISCAL_YEAR"));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(thisMembership.toString());
		return thisMembership;
	}
	
	public static Object_MembershipList getMembershipFromList(int ms_id, String year) {
		System.out.println("Pulling membership for ms_id=" + ms_id + " for the year year");
		Object_MembershipList thisMembership = null;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(
					"Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,"
					+ "id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,"
					+ "m.zip from slip s right join membership m on m.MS_ID=s.MS_ID left join membership_id "
					+ "id on m.MS_ID=id.MS_ID left join person p on p.MS_ID=m.MS_ID where id.FISCAL_YEAR='" + year + "' "
					+ "and p.MEMBER_TYPE=1 and m.ms_id=" + ms_id));
			while (rs.next()) {
				thisMembership = new Object_MembershipList(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						rs.getInt("MEMBERSHIP_ID"), 
						rs.getString("JOIN_DATE"), 
						rs.getString("MEM_TYPE"), 
						rs.getString("SLIP_NUM"), 
						rs.getString("L_NAME"),
						rs.getString("F_NAME"), 
						rs.getInt("SUBLEASED_TO"), 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP"),
						rs.getString("FISCAL_YEAR"));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(thisMembership.toString());
		return thisMembership;
	}
	
	public static Object_MembershipList getMembershipFromListWithoutMembershipId(int ms_id) {
		Object_MembershipList thisMembership = null;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(
					"Select m.MS_ID,m.P_ID,m.JOIN_DATE,s.SLIP_NUM,p.L_NAME,"
					+ "p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip from slip s right join "
					+ "membership m on m.MS_ID=s.MS_ID left join person p on p.MS_ID=m.MS_ID where m.ms_id=" + ms_id));
			while (rs.next()) {
				thisMembership = new Object_MembershipList(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						0, 
						rs.getString("JOIN_DATE"), 
						null, 
						rs.getString("SLIP_NUM"), 
						rs.getString("L_NAME"),
						rs.getString("F_NAME"), 
						rs.getInt("SUBLEASED_TO"), 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP"),
						"No Year"
						);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(thisMembership.toString());
		return thisMembership;
	}

	public static ObservableList<Object_MembershipList> getSlipRoster(String year) {
		ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor(
					"Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip "
							+ "from slip s inner join membership m on s.ms_id=m.ms_id inner join membership_id id on id.ms_id=m.ms_id "
							+ "inner join person p on p.p_id=m.p_id where id.fiscal_year="+year));
			while (rs.next()) {
				rosters.add(new Object_MembershipList(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						rs.getInt("MEMBERSHIP_ID"), 
						rs.getString("JOIN_DATE"), 
						rs.getString("MEM_TYPE"), 
						rs.getString("SLIP_NUM"), 
						rs.getString("L_NAME"),
						rs.getString("F_NAME"), 
						rs.getInt("SUBLEASED_TO"), 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP"),
						rs.getString("FISCAL_YEAR")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rosters;
	}
	
	public static ObservableList<Object_MembershipList> getWaitListRoster(String waitlist) {
		ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor(
					"Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip "
					+ "from waitlist w "
					+ "inner join membership m on w.ms_id=m.ms_id "
					+ "left join membership_id id on m.MS_ID=id.MS_ID "
					+ "left join person p on p.MS_ID=m.MS_ID "
					+ "left join slip s on s.MS_ID=m.MS_ID "
					+ "where " + waitlist + "=true and id.fiscal_year='" + Paths.getYear() + "' and p.MEMBER_TYPE=1"));
			while (rs.next()) {
				rosters.add(new Object_MembershipList(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						rs.getInt("MEMBERSHIP_ID"), 
						rs.getString("JOIN_DATE"), 
						rs.getString("MEM_TYPE"), 
						rs.getString("SLIP_NUM"), 
						rs.getString("L_NAME"),
						rs.getString("F_NAME"), 
						rs.getInt("SUBLEASED_TO"),
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP"),
						rs.getString("FISCAL_YEAR")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rosters;
	}
	
	public static ObservableList<Object_MembershipList> getNewMemberRoster(String year) {
		ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor(
					"select id.membership_id, id.FISCAL_YEAR, m.JOIN_DATE, id.MEM_TYPE, m.ADDRESS, "
					+ "m.CITY, m.state,m.zip, m.p_id, p.l_name, p.f_name,m.MS_ID from membership m "
					+ "inner join person p on m.p_id=p.p_id "
					+ "inner join membership_id id on id.ms_id=m.ms_id "
					+ "where YEAR(JOIN_DATE)='" + year + "' and id.FISCAL_YEAR='" + year + "' group by m.MS_ID;"));
			while (rs.next()) {
				rosters.add(new Object_MembershipList(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						rs.getInt("MEMBERSHIP_ID"), 
						rs.getString("JOIN_DATE"), 
						rs.getString("MEM_TYPE"), 
						"", 
						rs.getString("L_NAME"),
						rs.getString("F_NAME"), 
						0, 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP"),
						rs.getString("FISCAL_YEAR")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Creating Roster list for " + year + "...");
		return rosters;
	}
	
	public static ObservableList<Object_MembershipList> getFullNewMemberRoster(String year) {
		int lastYear = Integer.parseInt(year) - 1;
		ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor(
					"select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip "
					+ "from membership_id id left join membership m on m.MS_ID=id.MS_ID "
					+ "left join person p on p.P_ID=m.P_ID left join slip s on s.MS_ID=m.MS_ID "
					+ "where id.FISCAL_YEAR='" + year + "' and id.MEMBERSHIP_ID > ("
					+ "select membership_id from membership_id where FISCAL_YEAR='" + year + "' and MS_ID=("
					+ "select MS_ID from membership_id where FISCAL_YEAR='" + lastYear + "' and membership_id=("
					+ "select max(membership_id) from membership_id where FISCAL_YEAR='" + lastYear + "' and membership_id < 500 and id.renew=1))) "
					+ " and id.LATE_RENEW=false and id.MEMBERSHIP_ID < 500;"));
			while (rs.next()) {
				rosters.add(new Object_MembershipList(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						rs.getInt("MEMBERSHIP_ID"), 
						rs.getString("JOIN_DATE"), 
						rs.getString("MEM_TYPE"), 
						rs.getString("SLIP_NUM"), 
						rs.getString("L_NAME"),
						rs.getString("F_NAME"), 
						rs.getInt("SUBLEASED_TO"), 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP"),
						rs.getString("FISCAL_YEAR")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rosters;
	}
	
	public static ObservableList<Object_MembershipList> getReturnMembers(int fiscalYear) { // and those who lost their membership number
		int lastYear = fiscalYear - 1;
		ObservableList<Object_MembershipList> rosters = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor(
					"select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,m.JOIN_DATE,id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip "
					+ "from membership_id id left join membership m on m.MS_ID=id.MS_ID "
					+ "left join person p on p.P_ID=m.P_ID left join slip s on s.MS_ID=m.MS_ID "
					+ "where id.FISCAL_YEAR='" + fiscalYear + "' and YEAR(m.JOIN_DATE) < "+ fiscalYear +" and id.MEMBERSHIP_ID > ("
					+ "select membership_id from membership_id where FISCAL_YEAR='" + fiscalYear + "' and MS_ID=("
					+ "select MS_ID from membership_id where FISCAL_YEAR='" + lastYear + "' and membership_id=("
					+ "select max(membership_id) from membership_id where FISCAL_YEAR='" + lastYear + "' and membership_id < 500 and id.renew=1))) "
					+ " and id.MEMBERSHIP_ID < 500;"));
			while (rs.next()) {
				rosters.add(new Object_MembershipList(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						rs.getInt("MEMBERSHIP_ID"), 
						rs.getString("JOIN_DATE"), 
						rs.getString("MEM_TYPE"), 
						rs.getString("SLIP_NUM"), 
						rs.getString("L_NAME"),
						rs.getString("F_NAME"), 
						rs.getInt("SUBLEASED_TO"), 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP"),
						rs.getString("FISCAL_YEAR")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rosters;
	}
	
	public static ObservableList<Object_Membership> getMemberships() {  /// for SQL Script Maker
		ObservableList<Object_Membership> memberships = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from membership;"));
			while (rs.next()) {
				memberships.add(new Object_Membership(
						rs.getInt("MS_ID"), 
						rs.getInt("P_ID"),
						rs.getString("JOIN_DATE"), 
						rs.getString("MEM_TYPE"), 
						rs.getString("ADDRESS"), 
						rs.getString("CITY"), 
						rs.getString("STATE"),
						rs.getString("ZIP")));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return memberships;
	}
}
