package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.structures.Object_DefinedFee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlDefinedFee {
    public static ObservableList<Object_DefinedFee> getDefinedFees() {
        ObservableList<Object_DefinedFee> thisDefinedFee = FXCollections.observableArrayList();
        try {
            queryToObjectArrayList("select * from defined_fee", thisDefinedFee);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisDefinedFee;
    }

    // to create a single defined fee object and fille it with a selected year
    public static Object_DefinedFee getDefinedFeeByYear(String year) {
        Object_DefinedFee newDefinedFee = null;
        try {
        queryToObject("SELECT * FROM defined_fee WHERE fiscal_year='" + year + "'",newDefinedFee);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return newDefinedFee;
    }

    //////////////////////////////// QUERIES /////////////////////////////////////////////////

private static void queryToObject(String query, Object_DefinedFee definedFee) throws SQLException {
    Statement stmt = ConnectDatabase.sqlConnection.createStatement();
    ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(query));
    while (rs.next()) {
        definedFee = new Object_DefinedFee(
                rs.getInt("FISCAL_YEAR"),
                rs.getBigDecimal("DUES_REGULAR"),
                rs.getBigDecimal("DUES_FAMILY"),
                rs.getBigDecimal("DUES_LAKE_ASSOCIATE"),
                rs.getBigDecimal("DUES_SOCIAL"),
                rs.getBigDecimal("INITIATION"),
                rs.getBigDecimal("WET_SLIP"),
                rs.getBigDecimal("BEACH"),
                rs.getBigDecimal("WINTER_STORAGE"),
                rs.getBigDecimal("MAIN_GATE_KEY"),
                rs.getBigDecimal("SAIL_LOFT"),
                rs.getBigDecimal("SAIL_LOFT_KEY"),
                rs.getBigDecimal("SAIL_SCHOOL_LASER_LOFT"),
                rs.getBigDecimal("SAIL_SCHOOL_LOFT_KEY"),
                rs.getBigDecimal("KAYAK_RACK"),
                rs.getBigDecimal("KAYAK_SHED"),
                rs.getBigDecimal("kAYAK_SHED_KEY"),
                rs.getBigDecimal("WORK_CREDIT"));
    }
}

    private static void queryToObjectArrayList(String query, ObservableList<Object_DefinedFee> thisDefinedFee) throws SQLException {
        Statement stmt = ConnectDatabase.sqlConnection.createStatement();
        ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(query));
        while (rs.next()) {
            thisDefinedFee.add(new Object_DefinedFee(
                    rs.getInt("FISCAL_YEAR"),
                    rs.getBigDecimal("DUES_REGULAR"),
                    rs.getBigDecimal("DUES_FAMILY"),
                    rs.getBigDecimal("DUES_LAKE_ASSOCIATE"),
                    rs.getBigDecimal("DUES_SOCIAL"),
                    rs.getBigDecimal("INITIATION"),
                    rs.getBigDecimal("WET_SLIP"),
                    rs.getBigDecimal("BEACH"),
                    rs.getBigDecimal("WINTER_STORAGE"),
                    rs.getBigDecimal("MAIN_GATE_KEY"),
                    rs.getBigDecimal("SAIL_LOFT"),
                    rs.getBigDecimal("SAIL_LOFT_KEY"),
                    rs.getBigDecimal("SAIL_SCHOOL_LASER_LOFT"),
                    rs.getBigDecimal("SAIL_SCHOOL_LOFT_KEY"),
                    rs.getBigDecimal("KAYAK_RACK"),
                    rs.getBigDecimal("KAYAK_SHED"),
                    rs.getBigDecimal("KAYAK_SHED_KEY"),
                    rs.getBigDecimal("WORK_CREDIT")
            ));
        }
    }
}
