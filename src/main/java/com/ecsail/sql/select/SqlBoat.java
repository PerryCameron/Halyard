package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Halyard;
import com.ecsail.structures.BoatDTO;
import com.ecsail.structures.BoatListDTO;
import com.ecsail.structures.BoatOwnerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqlBoat {
    // was a list
    public static ObservableList<BoatOwnerDTO> getBoatOwners() {
        ObservableList<BoatOwnerDTO> thisBoatOwner = FXCollections.observableArrayList();
        String query = "select * from boat_owner";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            while (rs.next()) {
                thisBoatOwner.add(new BoatOwnerDTO(
                        rs.getInt("MS_ID"),
                        rs.getInt("BOAT_ID")));
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisBoatOwner;
    }

    public static ObservableList<BoatDTO> getBoats() {
        ObservableList<BoatDTO> thisBoat = FXCollections.observableArrayList();
        String query = "select * from boat";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            while (rs.next()) {
                thisBoat.add(new BoatDTO(
                        rs.getInt("BOAT_ID"), 0, // because Object_Boat has a ms-id variable but database does not
                        rs.getString("MANUFACTURER"), // might be the best note I have ever left ^^ lol
                        rs.getString("MANUFACTURE_YEAR"),
                        rs.getString("REGISTRATION_NUM"),
                        rs.getString("MODEL"),
                        rs.getString("BOAT_NAME"),
                        rs.getString("SAIL_NUMBER"),
                        rs.getBoolean("HAS_TRAILER"),
                        rs.getString("LENGTH"),
                        rs.getString("WEIGHT"),
                        rs.getString("KEEL"),
                        rs.getString("PHRF"),
                        rs.getString("DRAFT"),
                        rs.getString("BEAM"),
                        rs.getString("LWL"),
                        rs.getBoolean("AUX")));

            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisBoat;
    }

    public static ObservableList<BoatListDTO> getBoatsWithOwners() {
        ObservableList<BoatListDTO> thisBoat = FXCollections.observableArrayList();
        String query = "select id.MEMBERSHIP_ID,id.MS_ID, p.L_NAME, p.F_NAME, "
                + "b.* from boat b left join boat_owner bo on "
                + "b.BOAT_ID=bo.BOAT_ID left join membership_id id "
                + "on bo.MS_ID=id.MS_ID left join membership m on "
                + "id.MS_ID=m.MS_ID left join person p on m.P_ID=p.P_ID "
                + "where id.RENEW=true and id.FISCAL_YEAR='2021'";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            while (rs.next()) {
                thisBoat.add(new BoatListDTO(
                        rs.getInt("BOAT_ID"),
                        rs.getInt("MS_ID"),
                        rs.getString("MANUFACTURER"),
                        rs.getString("MANUFACTURE_YEAR"),
                        rs.getString("REGISTRATION_NUM"),
                        rs.getString("MODEL"),
                        rs.getString("BOAT_NAME"),
                        rs.getString("SAIL_NUMBER"),
                        rs.getBoolean("HAS_TRAILER"),
                        rs.getString("LENGTH"),
                        rs.getString("WEIGHT"),
                        rs.getString("KEEL"),
                        rs.getString("PHRF"),
                        rs.getString("DRAFT"),
                        rs.getString("BEAM"),
                        rs.getString("LWL"),
                        rs.getBoolean("AUX"),
                        rs.getInt("MEMBERSHIP_ID"),
                        rs.getString("L_NAME"),
                        rs.getString("F_NAME")));
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisBoat;
    }

    public static List<BoatDTO> getBoats(int ms_id) { // overload but must be separate
        List<BoatDTO> thisBoat = new ArrayList<>();
        String query = "select b.BOAT_ID, bo.MS_ID, b.MANUFACTURER"
                + ", b.MANUFACTURE_YEAR, b.REGISTRATION_NUM, b.MODEL, b.BOAT_NAME, b.SAIL_NUMBER"
                + ", b.HAS_TRAILER, b.LENGTH, b.WEIGHT, b.KEEL, b.PHRF, b.DRAFT, b.BEAM, b.LWL, b.AUX from boat b inner join boat_owner bo using (boat_id) where ms_id='" + ms_id + "';";
        try {
        Statement stmt = ConnectDatabase.sqlConnection.createStatement();
        ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            while (rs.next()) {
            thisBoat.add(new BoatDTO(
                    rs.getInt("BOAT_ID"),
                    rs.getInt("MS_ID"),
                    rs.getString("MANUFACTURER"),
                    rs.getString("MANUFACTURE_YEAR"),
                    rs.getString("REGISTRATION_NUM"),
                    rs.getString("MODEL"),
                    rs.getString("BOAT_NAME"),
                    rs.getString("SAIL_NUMBER"),
                    rs.getBoolean("HAS_TRAILER"),
                    rs.getString("LENGTH"),
                    rs.getString("WEIGHT"),
                    rs.getString("KEEL"),
                    rs.getString("PHRF"),
                    rs.getString("DRAFT"),
                    rs.getString("BEAM"),
                    rs.getString("LWL"),
                    rs.getBoolean("AUX")));
        }
        stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisBoat;
    }

    public static BoatDTO getBoatbyBoatId(int boat_id) { // overload but must be separate
        BoatDTO thisBoat = null;
        String query = "select b.BOAT_ID, bo.MS_ID, b.MANUFACTURER"
                + ", b.MANUFACTURE_YEAR, b.REGISTRATION_NUM, b.MODEL, b.BOAT_NAME, b.SAIL_NUMBER"
                + ", b.HAS_TRAILER, b.LENGTH, b.WEIGHT, b.KEEL, b.PHRF, b.DRAFT, b.BEAM, b.LWL, b.AUX from boat b inner join boat_owner bo using (boat_id) where boat_id='" + boat_id + "';";
        try {
        Statement stmt = ConnectDatabase.sqlConnection.createStatement();
        ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            while (rs.next()) {
            thisBoat = new BoatDTO(
                    rs.getInt("BOAT_ID"),
                    rs.getInt("MS_ID"),
                    rs.getString("MANUFACTURER"),
                    rs.getString("MANUFACTURE_YEAR"),
                    rs.getString("REGISTRATION_NUM"),
                    rs.getString("MODEL"),
                    rs.getString("BOAT_NAME"),
                    rs.getString("SAIL_NUMBER"),
                    rs.getBoolean("HAS_TRAILER"),
                    rs.getString("LENGTH"),
                    rs.getString("WEIGHT"),
                    rs.getString("KEEL"),
                    rs.getString("PHRF"),
                    rs.getString("DRAFT"),
                    rs.getString("BEAM"),
                    rs.getString("LWL"),
                    rs.getBoolean("AUX"));
        }
        stmt.close();
        } catch (SQLException e) {
//            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
            e.printStackTrace();
        }
        return thisBoat;
    }
}
