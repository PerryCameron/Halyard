package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.Halyard;
import com.ecsail.main.HalyardPaths;
import com.ecsail.structures.BoatDTO;
import com.ecsail.structures.BoatListDTO;
import com.ecsail.structures.BoatOwnerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlBoat {
    // was a list
    public static ObservableList<BoatOwnerDTO> getBoatOwners() {
        ObservableList<BoatOwnerDTO> thisBoatOwner = FXCollections.observableArrayList();
        String query = "SELECT * FROM boat_owner";
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                thisBoatOwner.add(new BoatOwnerDTO(
                        rs.getInt("ms_id"),
                        rs.getInt("boat_id")));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisBoatOwner;
    }

    public static ObservableList<BoatDTO> getBoats() {
        ObservableList<BoatDTO> thisBoat = FXCollections.observableArrayList();
        String query = "SELECT * FROM boat";
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                thisBoat.add(new BoatDTO(
                        rs.getInt("boat_id"), 0, // because Object_Boat has a ms-id variable but database does not
                        rs.getString("manufacturer"), // might be the best note I have ever left ^^ lol
                        rs.getString("manufacture_year"),
                        rs.getString("registration_num"),
                        rs.getString("model"),
                        rs.getString("boat_name"),
                        rs.getString("sail_number"),
                        rs.getBoolean("has_trailer"),
                        rs.getString("length"),
                        rs.getString("weight"),
                        rs.getString("keel"),
                        rs.getString("phrf"),
                        rs.getString("draft"),
                        rs.getString("beam"),
                        rs.getString("lwl"),
                        rs.getBoolean("aux")));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisBoat;
    }

    public static ObservableList<BoatListDTO> getBoatsWithOwners() {
        ObservableList<BoatListDTO> thisBoat = FXCollections.observableArrayList();
        String query = "SELECT id.membership_id,id.ms_id, p.l_name, p.f_name, "
                + "b.* FROM boat b LEFT JOIN boat_owner bo ON "
                + "b.boat_id=bo.boat_id LEFT JOIN membership_id id "
                + "ON bo.ms_id=id.ms_id LEFT JOIN membership m ON "
                + "id.ms_id=m.ms_id LEFT JOIN person p ON m.p_id=p.p_id "
                + "WHERE id.renew=true and id.fiscal_year='"+ HalyardPaths.getYear() +"'";
        try {
            
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                thisBoat.add(new BoatListDTO(
                        rs.getInt("boat_id"),
                        rs.getInt("ms_id"),
                        rs.getString("manufacturer"),
                        rs.getString("manufacture_year"),
                        rs.getString("registration_num"),
                        rs.getString("model"),
                        rs.getString("boat_name"),
                        rs.getString("sail_number"),
                        rs.getBoolean("has_trailer"),
                        rs.getString("length"),
                        rs.getString("weight"),
                        rs.getString("keel"),
                        rs.getString("phrf"),
                        rs.getString("draft"),
                        rs.getString("beam"),
                        rs.getString("lwl"),
                        rs.getBoolean("aux"),
                        rs.getInt("membership_id"),
                        rs.getString("l_name"),
                        rs.getString("f_name")));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisBoat;
    }

    public static List<BoatDTO> getBoats(int ms_id) { // overload but must be separate
        List<BoatDTO> thisBoat = new ArrayList<>();
        String query = "SELECT b.boat_id, bo.ms_id, b.manufacturer"
                + ", b.manufacture_year, b.registration_num, b.model, b.boat_name, b.sail_number"
                + ", b.has_trailer, b.length, b.weight, b.keel, b.phrf, b.draft, b.beam, b.lwl, b.aux FROM boat b INNER JOIN boat_owner bo USING (boat_id) WHERE ms_id=" + ms_id;
        try {
        ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
            thisBoat.add(new BoatDTO(
                    rs.getInt("boat_id"),
                    rs.getInt("ms_id"),
                    rs.getString("manufacturer"),
                    rs.getString("manufacture_year"),
                    rs.getString("registration_num"),
                    rs.getString("model"),
                    rs.getString("boat_name"),
                    rs.getString("sail_number"),
                    rs.getBoolean("has_trailer"),
                    rs.getString("length"),
                    rs.getString("weight"),
                    rs.getString("keel"),
                    rs.getString("phrf"),
                    rs.getString("draft"),
                    rs.getString("beam"),
                    rs.getString("lwl"),
                    rs.getBoolean("aux")));
        }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisBoat;
    }

    public static BoatDTO getBoatbyBoatId(int boat_id) { // overload but must be separate
        BoatDTO thisBoat = null;
        String query = "SELECT b.boat_id, bo.ms_id, b.manufacturer"
                + ", b.manufacture_year, b.registration_num, b.model, b.boat_name, b.sail_number"
                + ", b.has_trailer, b.length, b.weight, b.keel, b.phrf, b.draft, b.beam, b.lwl, b.aux FROM boat b INNER JOIN boat_owner bo USING (boat_id) WHERE boat_id=" + boat_id;
        try {
        ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
            thisBoat = new BoatDTO(
                    rs.getInt("boat_id"),
                    rs.getInt("ms_id"),
                    rs.getString("manufacturer"),
                    rs.getString("manufacture_year"),
                    rs.getString("registration_num"),
                    rs.getString("model"),
                    rs.getString("boat_name"),
                    rs.getString("sail_number"),
                    rs.getBoolean("has_trailer"),
                    rs.getString("length"),
                    rs.getString("weight"),
                    rs.getString("keel"),
                    rs.getString("phrf"),
                    rs.getString("draft"),
                    rs.getString("beam"),
                    rs.getString("lwl"),
                    rs.getBoolean("aux"));
        }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
//            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
            e.printStackTrace();
        }
        return thisBoat;
    }
}
