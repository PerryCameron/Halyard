package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.structures.MoneyDTO;
import com.ecsail.structures.WorkCreditDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlMoney {
    public static ObservableList<MoneyDTO> getMoniesByMsid(int ms_id) { // overload
//        String query = "SELECT * FROM money";
//        if(ms_id != 0)
//        query += " WHERE ms_id=" + ms_id;
        ObservableList<MoneyDTO> theseFiscals = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT * FROM money WHERE ms_id=" + ms_id + ";"));
            while (rs.next()) {
                theseFiscals.add(new MoneyDTO(rs.getInt("MONEY_ID"), rs.getInt("MS_ID"),
                        rs.getInt("FISCAL_YEAR"),
                        rs.getInt("BATCH"),
                        rs.getString("OFFICER_CREDIT"),
                        rs.getInt("EXTRA_KEY"),
                        rs.getInt("KAYAK_SHED_KEY"),
                        rs.getInt("SAIL_LOFT_KEY"),
                        rs.getInt("SAIL_SCHOOL_LOFT_KEY"),
                        rs.getInt("BEACH"),
                        rs.getString("WET_SLIP"),
                        rs.getInt("KAYAK_RACK"),
                        rs.getInt("KAYAK_BEACH_RACK"),
                        rs.getInt("KAYAK_SHED"),
                        rs.getInt("SAIL_LOFT"),
                        rs.getInt("SAIL_SCHOOL_LASER_LOFT"),
                        rs.getInt("WINTER_STORAGE"),
                        rs.getString("YSC_DONATION"),
                        rs.getString("PAID"),
                        rs.getString("TOTAL"),
                        rs.getString("CREDIT"),
                        rs.getString("BALANCE"),
                        rs.getString("DUES"),
                        rs.getBoolean("COMMITED"),
                        rs.getBoolean("CLOSED"),
                        rs.getString("OTHER"),
                        rs.getString("INITIATION"),
                        rs.getBoolean("SUPPLEMENTAL"),
                        rs.getInt("WORK_CREDIT"),
                        rs.getString("OTHER_CREDIT")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseFiscals;
    }

    public static ObservableList<MoneyDTO> getAllMonies() { // overload
        ObservableList<MoneyDTO> theseFiscals = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("SELECT * FROM money"));
            while (rs.next()) {
                theseFiscals.add(new MoneyDTO(rs.getInt("MONEY_ID"), rs.getInt("MS_ID"),
                        rs.getInt("FISCAL_YEAR"),
                        rs.getInt("BATCH"),
                        rs.getString("OFFICER_CREDIT"),
                        rs.getInt("EXTRA_KEY"),
                        rs.getInt("KAYAK_SHED_KEY"),
                        rs.getInt("SAIL_LOFT_KEY"),
                        rs.getInt("SAIL_SCHOOL_LOFT_KEY"),
                        rs.getInt("BEACH"),
                        rs.getString("WET_SLIP"),
                        rs.getInt("KAYAK_RACK"),
                        rs.getInt("KAYAK_BEACH_RACK"),
                        rs.getInt("KAYAK_SHED"),
                        rs.getInt("SAIL_LOFT"),
                        rs.getInt("SAIL_SCHOOL_LASER_LOFT"),
                        rs.getInt("WINTER_STORAGE"),
                        rs.getString("YSC_DONATION"),
                        rs.getString("PAID"),
                        rs.getString("TOTAL"),
                        rs.getString("CREDIT"),
                        rs.getString("BALANCE"),
                        rs.getString("DUES"),
                        rs.getBoolean("COMMITED"),
                        rs.getBoolean("CLOSED"),
                        rs.getString("OTHER"),
                        rs.getString("INITIATION"),
                        rs.getBoolean("SUPPLEMENTAL"),
                        rs.getInt("WORK_CREDIT"),
                        rs.getString("OTHER_CREDIT")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseFiscals;
    }

    public static MoneyDTO getMonies(int ms_id, String fiscalYear) { // overload
        String query = "SELECT * FROM money WHERE ms_id=" + ms_id + " and fiscal_year=" + fiscalYear;
        MoneyDTO thisFiscal = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(query + ";"));
            while (rs.next()) {
                thisFiscal = new MoneyDTO(rs.getInt("MONEY_ID"), rs.getInt("MS_ID"),
                        rs.getInt("FISCAL_YEAR"), rs.getInt("BATCH"), rs.getString("OFFICER_CREDIT"), rs.getInt("EXTRA_KEY"),
                        rs.getInt("KAYAK_SHED_KEY"), rs.getInt("SAIL_LOFT_KEY"),
                        rs.getInt("SAIL_SCHOOL_LOFT_KEY"), rs.getInt("BEACH"),
                        rs.getString("WET_SLIP"), rs.getInt("KAYAK_RACK"), rs.getInt("KAYAK_BEACH_RACK"),rs.getInt("KAYAK_SHED"),
                        rs.getInt("SAIL_LOFT"), rs.getInt("SAIL_SCHOOL_LASER_LOFT"), rs.getInt("WINTER_STORAGE"),
                        rs.getString("YSC_DONATION"),rs.getString("PAID"),rs.getString("TOTAL"),rs.getString("CREDIT"),
                        rs.getString("BALANCE"), rs.getString("DUES"),rs.getBoolean("COMMITED"),rs.getBoolean("CLOSED"),
                        rs.getString("OTHER"),rs.getString("INITIATION"),rs.getBoolean("SUPPLEMENTAL"), rs.getInt("WORK_CREDIT"),
                        rs.getString("OTHER_CREDIT"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisFiscal;
    }

    public static WorkCreditDTO getWorkCredit(int moneyID) {
        Statement stmt;
        WorkCreditDTO workCredits = null;
        try {
            stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    Main.console.setRegexColor("select * from work_credit WHERE money_id='" + moneyID + "';"));
            // if(Main.consoleVerbose) ;
            while (rs.next()) {
                workCredits = new WorkCreditDTO(rs.getInt("MONEY_ID"), rs.getInt("MS_ID"),rs.getInt("RACING"), rs.getInt("HARBOR"),
                        rs.getInt("SOCIAL"), rs.getInt("OTHER"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        // System.out.println(thisMembership.toString());
        return workCredits;
    }

    public static boolean isCommitted(int money_id) {
        boolean committed = false;
        ResultSet rs;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            rs = stmt.executeQuery("select commited from money where money_id=" + money_id + ";");
            rs.next();
            committed = rs.getBoolean("commited");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return committed;
    }

    public static int getTotalAmount(int money_id) {
        int number = 0;
        Statement stmt;
        try {
            stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor("select SUM(amount) from payment where money_id=" + money_id));
            rs.next();
            number = rs.getInt("SUM(amount)");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;

    }

    public static int getBatchNumber(String year) {
        int number = 0;
        ResultSet rs;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            rs = stmt.executeQuery("SELECT MAX(batch) FROM money WHERE commited=true and fiscal_year='"+year+"'");
            rs.next();
            number = rs.getInt("MAX(batch)");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }

    public static int getCount(String type) { // gives the last memo_id number
        int result = 0;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("select * from money ORDER BY " + type + " DESC LIMIT 1;");
            boolean hasResult = rs.next();
            if (hasResult)
                result = rs.getInt(type);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return result;
    }
}
