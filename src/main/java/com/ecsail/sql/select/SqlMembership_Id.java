package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.HalyardPaths;
import com.ecsail.main.Halyard;
import com.ecsail.structures.MembershipIdDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlMembership_Id {
    public static ObservableList<MembershipIdDTO> getIds() {
        ObservableList<MembershipIdDTO> ids = FXCollections.observableArrayList();
        String query = "select * from membership_id";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            while (rs.next()) {
                ids.add(new MembershipIdDTO(
                        rs.getInt("MID")
                        , rs.getString("FISCAL_YEAR")
                        , rs.getInt("MS_ID")
                        , rs.getString("MEMBERSHIP_ID")
                        , rs.getBoolean("RENEW")
                        , rs.getString("MEM_TYPE")
                        , rs.getBoolean("SELECTED")
                        , rs.getBoolean("LATE_RENEW")));
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return ids;
    }

    public static ObservableList<MembershipIdDTO> getIds(int ms_id) {
        ObservableList<MembershipIdDTO> ids = FXCollections.observableArrayList();
        String query = "select * from membership_id where ms_id=" +ms_id;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            while (rs.next()) {
                ids.add(new MembershipIdDTO(
                        rs.getInt("MID")
                        , rs.getString("FISCAL_YEAR")
                        , rs.getInt("MS_ID")
                        , rs.getString("MEMBERSHIP_ID")
                        , rs.getBoolean("RENEW")
                        , rs.getString("MEM_TYPE")
                        , rs.getBoolean("SELECTED")
                        , rs.getBoolean("LATE_RENEW")));
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return ids;
    }

    public static String getId(int ms_id) {
        MembershipIdDTO id = null;
        String query = "select * from membership_id where ms_id=" +ms_id + ";";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            while (rs.next()) {
                id = new MembershipIdDTO(
                        rs.getInt("MID")
                        , rs.getString("FISCAL_YEAR")
                        , rs.getInt("MS_ID")
                        , rs.getString("MEMBERSHIP_ID")
                        , rs.getBoolean("RENEW")
                        , rs.getString("MEM_TYPE")
                        , rs.getBoolean("SELECTED")
                        , rs.getBoolean("LATE_RENEW"));
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return id.getMembership_id();
    }

    public static MembershipIdDTO getCount(int ms_id) {
        MembershipIdDTO thisId = null; // new Object_MembershipId();
        String query = "select MID, MIN(FISCAL_YEAR), MS_ID, MAX(MEMBERSHIP_ID), RENEW from membership_id where MS_ID=" + ms_id;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            while (rs.next()) {
                thisId = new MembershipIdDTO(
                rs.getInt("MID")
                , rs.getString("MIN(FISCAL_YEAR)")
                , rs.getInt("MS_ID")
                , rs.getString("MAX(MEMBERSHIP_ID)")
                , rs.getBoolean("RENEW")
                , rs.getString("MEM_TYPE")
                , rs.getBoolean("SELECTED")
                , rs.getBoolean("LATE_RENEW"));
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisId;
    }

    public static int getMembershipIDfromMsid(int msid)  {
        int result = 0;
        String query = "select membership_id from membership_id where ms_id=" + msid + " and fiscal_year=" + HalyardPaths.getYear();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            rs.next();
            result = rs.getInt("MEMBERSHIP_ID");
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return result;
    }

    public static int getMsidFromMembershipID(int membership_id)  {
        int result = 0;
        String query = "select ms_id from membership_id where fiscal_year='" + HalyardPaths.getYear() + "' and membership_id='" + membership_id + "'";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            rs.next();
            result = rs.getInt("ms_id");
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return result;
    }

    public static String getMembershipId(String year, int ms_id) {
        String id = "";
        String query = "select membership_id from membership_id where fiscal_year='" + year + "' and ms_id=" + ms_id;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            if (!rs.next()) {
                id = "none";
            } else {
                do {
                    id = rs.getString("membership_id");
                } while (rs.next());
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return id;
    }

    public static MembershipIdDTO getMembershipIdObject(int mid) {
        MembershipIdDTO id = null;
        String query = "select * from membership_id where mid="  + mid;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            rs.next();
                id = new MembershipIdDTO(
                        rs.getInt("MID")
                        , rs.getString("FISCAL_YEAR")
                        , rs.getInt("MS_ID")
                        , rs.getString("MEMBERSHIP_ID")
                        , rs.getBoolean("RENEW")
                        , rs.getString("MEM_TYPE")
                        , rs.getBoolean("SELECTED")
                        , rs.getBoolean("LATE_RENEW"));
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return id;
    }

    public static int getHighestMembershipId(String year) {  // example-> "email","email_id"
        int result = 0;
        String query = "select Max(membership_id) from membership_id where fiscal_year='" + year + "'";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            rs.next();
            result =  rs.getInt("Max(membership_id)");
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return result;
    }

    public static boolean isRenewed(int ms_id, String year)
    {
        boolean renew = false;
        String query = "select RENEW from membership_id where fiscal_year='" + year + "' and ms_id=" + ms_id;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            rs.next();
            renew = rs.getBoolean("RENEW");
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"membership id record does not exist for ms_id " + ms_id + " for year " + year,"See below for details");
        }
        return renew;
    }

    //////////  FOR CHARTS /////////////

    public static ObservableList<MembershipIdDTO> getAllMembershipIdsByYear(String year) {
		ObservableList<MembershipIdDTO> theseIds = FXCollections.observableArrayList();
        String query = "select * from membership_id where fiscal_year=" + year + " order by MEMBERSHIP_ID";
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
		while (rs.next()) {
			theseIds.add(new MembershipIdDTO(
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
			new Dialogue_ErrorSQL(e,"Unable to retrieve information membership_id info for " + year,"See below for details");
		}
		return theseIds;
	}

    public static ObservableList<MembershipIdDTO> getActiveMembershipIdsByYear(String year) {
        ObservableList<MembershipIdDTO> theseIds = FXCollections.observableArrayList();
        String query = "select * from membership_id where fiscal_year=" + year + " and renew=true order by MEMBERSHIP_ID";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            while (rs.next()) {
                theseIds.add(new MembershipIdDTO(
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
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseIds;
    }

    public static int getNonRenewNumber(String year) {
        int number = 0;
        String query = "select count(*) from membership_id where FISCAL_YEAR='" + year + "' and RENEW=false";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            rs.next();
            number = rs.getInt("count(*)");
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }

    public static int getMsidFromYearAndMembershipId(int year, String membershipId) {
        int number = 0;
        String query = "select MS_ID from membership_id where FISCAL_YEAR=" + year
                   + " and MEMBERSHIP_ID=" + membershipId;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            rs.next();
            number = rs.getInt("MS_ID");
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }
}
