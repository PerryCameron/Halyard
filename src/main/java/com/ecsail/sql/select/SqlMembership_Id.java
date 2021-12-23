package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.HalyardPaths;
import com.ecsail.main.Main;
import com.ecsail.structures.Object_MembershipId;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlMembership_Id {
    public static ObservableList<Object_MembershipId> getIds() {
        ObservableList<Object_MembershipId> ids = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from membership_id;");
            while (rs.next()) {
                ids.add(new Object_MembershipId(
                        rs.getInt("MID")
                        , rs.getString("FISCAL_YEAR")
                        , rs.getInt("MS_ID")
                        , rs.getString("MEMBERSHIP_ID")
                        , rs.getBoolean("RENEW")
                        , rs.getString("MEM_TYPE")
                        , rs.getBoolean("SELECTED")
                        , rs.getBoolean("LATE_RENEW")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return ids;
    }

    public static ObservableList<Object_MembershipId> getIds(int ms_id) {
        ObservableList<Object_MembershipId> ids = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from membership_id where ms_id=" +ms_id + ";");
            while (rs.next()) {
                ids.add(new Object_MembershipId(
                        rs.getInt("MID")
                        , rs.getString("FISCAL_YEAR")
                        , rs.getInt("MS_ID")
                        , rs.getString("MEMBERSHIP_ID")
                        , rs.getBoolean("RENEW")
                        , rs.getString("MEM_TYPE")
                        , rs.getBoolean("SELECTED")
                        , rs.getBoolean("LATE_RENEW")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return ids;
    }

    public static String getId(int ms_id) {
        Object_MembershipId id = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from membership_id where ms_id=" +ms_id + ";");
            while (rs.next()) {
                id = new Object_MembershipId(
                        rs.getInt("MID")
                        , rs.getString("FISCAL_YEAR")
                        , rs.getInt("MS_ID")
                        , rs.getString("MEMBERSHIP_ID")
                        , rs.getBoolean("RENEW")
                        , rs.getString("MEM_TYPE")
                        , rs.getBoolean("SELECTED")
                        , rs.getBoolean("LATE_RENEW"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return id.getMembership_id();
    }

    public static Object_MembershipId getCount(int ms_id) {
        Object_MembershipId thisId = null; // new Object_MembershipId();
        Statement stmt;
        try {
            stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select MID, MIN(FISCAL_YEAR), MS_ID, MAX(MEMBERSHIP_ID), RENEW from membership_id where MS_ID=" + ms_id);
            while (rs.next()) {
                thisId = new Object_MembershipId(
            rs.getInt("MID")
            , rs.getString("MIN(FISCAL_YEAR)")
            , rs.getInt("MS_ID")
            , rs.getString("MAX(MEMBERSHIP_ID)")
            , rs.getBoolean("RENEW")
            , rs.getString("MEM_TYPE")
            , rs.getBoolean("SELECTED")
            , rs.getBoolean("LATE_RENEW"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisId;
    }

    public static int getMembershipIDfromMsid(int msid)  {
        int result = 0;
        Statement stmt;
        try {
            stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select membership_id from membership_id where ms_id='" + msid + "' and fiscal_year=" + HalyardPaths.getYear() +";"));
            while(rs.next()) {
            result = rs.getInt("MEMBERSHIP_ID");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return result;
    }

    public static int getMsidFromMembershipID(int membership_id)  {
        int result = 0;
        Statement stmt;
        try {
            stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select ms_id from membership_id where fiscal_year='" + HalyardPaths.getYear() + "' and membership_id='" + membership_id + "';"));
            while(rs.next()) {
            result = rs.getInt("ms_id");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return result;
    }

    public static String getMembershipId(String year, int ms_id) {
        String id = "";
        Statement stmt;

        try {
            stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select membership_id from membership_id where fiscal_year='" + year + "' and ms_id='" + ms_id + "'"));
            if (!rs.next()) {
                id = "none";
            } else {
                do {
                    id = rs.getString("membership_id");
                } while (rs.next());
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        //System.out.println("For year " + year +  " ms_id=" + ms_id + " they are " + id);
        return id;

    }

    public static Object_MembershipId getMembershipIdObject(int mid) {
        Object_MembershipId id = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from membership_id where mid='"  + mid + "'");
            while (rs.next()) {
                id = new Object_MembershipId(
                        rs.getInt("MID")
                        , rs.getString("FISCAL_YEAR")
                        , rs.getInt("MS_ID")
                        , rs.getString("MEMBERSHIP_ID")
                        , rs.getBoolean("RENEW")
                        , rs.getString("MEM_TYPE")
                        , rs.getBoolean("SELECTED")
                        , rs.getBoolean("LATE_RENEW"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return id;
    }

    public static int getHighestMembershipId(String year) {  // example-> "email","email_id"
        int result = 0;
        Statement stmt;
        try {
            stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select Max(membership_id) from membership_id where fiscal_year='" + year + "'");
            rs.next();
            result =  rs.getInt("Max(membership_id)");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return result;
    }

    public static boolean isRenewed(int ms_id, String year)
    {
        boolean renew = false;
        Statement stmt;
        try {
            stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select RENEW from membership_id where fiscal_year='" + year + "' and ms_id=" + ms_id));
            rs.next();
            renew = rs.getBoolean("RENEW");
        } catch (SQLException e) {
            System.out.println("membership id record does not exist for ms_id " + ms_id + " for year " + year);
            //new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return renew;
    }

    //////////  FOR CHARTS /////////////
    public static int getNumberOfActiveMembershipsForYear(int year) {
        int number = 0;
        ResultSet rs;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            rs = stmt.executeQuery("select count(*) from membership_id where fiscal_year=" + year + " and renew=true");
            rs.next();
            number = rs.getInt("count(*)");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }

    public static ObservableList<Object_MembershipId> getMembershipIds(String year) {
		ObservableList<Object_MembershipId> theseIds = FXCollections.observableArrayList();
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
		    ResultSet rs;
			rs = stmt.executeQuery(Main.console.setRegexColor("select * from membership_id where fiscal_year=" + year));
		while (rs.next()) {
			theseIds.add(new Object_MembershipId(
					rs.getInt("MID"),
					rs.getString("FISCAL_YEAR"),
					rs.getInt("MS_ID"),
					rs.getString("MEMBERSHIP_ID"),
					rs.getBoolean("RENEW"),
					rs.getString("MEM_TYPE"),
					rs.getBoolean("SELECTED"),
				    rs.getBoolean("LATE_RENEW")));
		}
		stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
		}
		return theseIds;
	}

    public static int getNonRenewNumber(String year) {
        int number = 0;
        ResultSet rs;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            rs = stmt.executeQuery("select count(*) from membership_id where FISCAL_YEAR='" + year + "' and RENEW=false");
            rs.next();
            number = rs.getInt("count(*)");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        //System.out.println(number);
        return number;
    }

    public static int getNumberOfMembersOfType(String type, int year) {
        int number = 0;
        ResultSet rs;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            rs = stmt.executeQuery("select count(*) from membership_id where FISCAL_YEAR='" + year + "' and MEM_TYPE='" + type + "' and RENEW=true;");
            rs.next();
            number = rs.getInt("count(*)");
            System.out.println(year + " " +type + "= " + number);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        //System.out.println(number);
        return number;
    }

    public static boolean isActive(int ms_id, String year) {
        boolean result = false;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("Select renew from membership_id where FISCAL_YEAR='"+year+"' and MS_ID='"+ms_id+"'"));
            while(rs.next()) {
            result = rs.getBoolean("renew");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return result;
    }

    public static int getNumberOfInactiveMembershipsForYear(int year) {
        int number = 0;
        ResultSet rs;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            rs = stmt.executeQuery("select count(*) from membership_id where fiscal_year=" + year + " and renew=false");
            rs.next();
            number = rs.getInt("count(*)");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }
}
