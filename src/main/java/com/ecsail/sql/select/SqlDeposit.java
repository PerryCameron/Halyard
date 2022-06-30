package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Halyard;
import com.ecsail.structures.DepositDTO;
import com.ecsail.structures.PaidDuesDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlDeposit {
    public static DepositDTO getDeposit(String year, int batch) {
        DepositDTO thisDeposit = null;
        String query = "select * from deposit where fiscal_year=" + year + " and batch=" + batch;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt, query);
            while (rs.next()) {
                thisDeposit = new DepositDTO(
                        rs.getInt("DEPOSIT_ID"),
                        rs.getString("DEPOSIT_DATE"),
                        rs.getString("FISCAL_YEAR"),
                        rs.getInt("BATCH")
                );
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
        }
        return thisDeposit;
    }

    public static ObservableList<PaidDuesDTO> getPaidDues(DepositDTO currentDeposit) {
        ObservableList<PaidDuesDTO> theseFiscals = FXCollections.observableArrayList();
        String query = "SELECT id.MEMBERSHIP_ID, mo.*, p.l_name, p.f_name FROM money mo "
                + "INNER JOIN membership_id id on mo.MS_ID=id.MS_ID and mo.FISCAL_YEAR=id.FISCAL_YEAR "
                + "INNER JOIN membership me on mo.MS_ID=me.MS_ID "
                + "INNER JOIN person p ON me.P_ID=p.P_ID  WHERE mo.FISCAL_YEAR='" + currentDeposit.getFiscalYear()
                + "' AND mo.COMMITED=true AND mo.BATCH=" + currentDeposit.getBatch() + " "
                + "ORDER BY id.MEMBERSHIP_ID";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt, query);
            while (rs.next()) {
                theseFiscals.add(new PaidDuesDTO(rs.getInt("MONEY_ID"), rs.getInt("MS_ID"),
                        rs.getInt("FISCAL_YEAR"), rs.getInt("BATCH"), rs.getString("OFFICER_CREDIT"), rs.getInt("EXTRA_KEY"),
                        rs.getInt("KAYAK_SHED_KEY"), rs.getInt("SAIL_LOFT_KEY"),
                        rs.getInt("SAIL_SCHOOL_LOFT_KEY"), rs.getInt("BEACH"),
                        rs.getString("WET_SLIP"), rs.getInt("KAYAK_RACK"), rs.getInt("KAYAK_BEACH_RACK"), rs.getInt("KAYAK_SHED"),
                        rs.getInt("SAIL_LOFT"), rs.getInt("SAIL_SCHOOL_LASER_LOFT"), rs.getInt("WINTER_STORAGE"),
                        rs.getString("YSC_DONATION"), rs.getString("PAID"), rs.getString("TOTAL"), rs.getString("CREDIT"),
                        rs.getString("BALANCE"), rs.getString("DUES"), rs.getBoolean("COMMITED"), rs.getBoolean("CLOSED"),
                        rs.getString("OTHER"), rs.getString("INITIATION"), rs.getBoolean("SUPPLEMENTAL"), rs.getInt("WORK_CREDIT"), rs.getString("OTHER_CREDIT"), rs.getString("F_NAME"),
                        rs.getString("L_NAME"), rs.getInt("MEMBERSHIP_ID")));
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
        }
        return theseFiscals;
    }

    public static ObservableList<PaidDuesDTO> getPaidDues(String selectedYear) {
        String query = "SELECT id.MEMBERSHIP_ID, mo.*, p.l_name, p.f_name FROM money mo "
                + "INNER JOIN membership_id id on mo.MS_ID=id.MS_ID and mo.FISCAL_YEAR=id.FISCAL_YEAR "
                + "INNER JOIN membership me on mo.MS_ID=me.MS_ID "
                + "INNER JOIN person p ON me.P_ID=p.P_ID WHERE mo.FISCAL_YEAR=" + selectedYear + " AND mo.COMMITED=true ORDER BY id.MEMBERSHIP_ID";
        ObservableList<PaidDuesDTO> theseFiscals = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt, query);
            while (rs.next()) {
                theseFiscals.add(new PaidDuesDTO(
                        rs.getInt("MONEY_ID"),
                        rs.getInt("MS_ID"),
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
                        rs.getString("OTHER_CREDIT"),
                        rs.getString("F_NAME"),
                        rs.getString("L_NAME"),
                        rs.getInt("MEMBERSHIP_ID")));
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
        }
        return theseFiscals;
    }

    public static ObservableList<PaidDuesDTO> getPaidDues(String selectedYear, int batch) { // overload
        String query = "SELECT mo.*, id.MEMBERSHIP_ID, p.l_name, p.f_name FROM membership_id id INNER JOIN membership m ON m.MS_ID=id.MS_ID LEFT JOIN person p ON m.P_ID=p.P_ID INNER JOIN money mo ON mo.MS_ID=m.MS_ID WHERE id.FISCAL_YEAR=" + selectedYear + " AND mo.BATCH=" + batch + " AND mo.FISCAL_YEAR=" + selectedYear;
        ObservableList<PaidDuesDTO> theseFiscals = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt, query);
            while (rs.next()) {
                theseFiscals.add(new PaidDuesDTO(rs.getInt("MONEY_ID"), rs.getInt("MS_ID"),
                        rs.getInt("FISCAL_YEAR"), rs.getInt("BATCH"), rs.getString("OFFICER_CREDIT"), rs.getInt("EXTRA_KEY"),
                        rs.getInt("KAYAK_SHED_KEY"), rs.getInt("SAIL_LOFT_KEY"),
                        rs.getInt("SAIL_SCHOOL_LOFT_KEY"), rs.getInt("BEACH"),
                        rs.getString("WET_SLIP"), rs.getInt("KAYAK_RACK"), rs.getInt("KAYAK_BEACH_RACK"), rs.getInt("KAYAK_SHED"),
                        rs.getInt("SAIL_LOFT"), rs.getInt("SAIL_SCHOOL_LASER_LOFT"), rs.getInt("WINTER_STORAGE"),
                        rs.getString("YSC_DONATION"), rs.getString("PAID"), rs.getString("TOTAL"), rs.getString("CREDIT"),
                        rs.getString("BALANCE"), rs.getString("DUES"), rs.getBoolean("COMMITED"), rs.getBoolean("CLOSED"),
                        rs.getString("OTHER"), rs.getString("INITIATION"), rs.getBoolean("SUPPLEMENTAL"), rs.getInt("WORK_CREDIT"),
                        rs.getString("OTHER_CREDIT"), rs.getString("F_NAME"),
                        rs.getString("L_NAME"), rs.getInt("MEMBERSHIP_ID")));
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
        }
        return theseFiscals;
    }

    public static ObservableList<DepositDTO> getDeposits() {
        ObservableList<DepositDTO> thisDeposits = FXCollections.observableArrayList();
        String query = "select * from deposit";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt, query);
            while (rs.next()) {
                thisDeposits.add(new DepositDTO(
                        rs.getInt("DEPOSIT_ID"),
                        rs.getString("DEPOSIT_DATE"),
                        rs.getString("FISCAL_YEAR"),
                        rs.getInt("BATCH")
                        ));
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisDeposits;
    }

    // can do this with a regular object but not properties for some reason
    public static void  updateDeposit(String year, int batch, DepositDTO thisDeposit) {
        String query = "select * from deposit where fiscal_year=" + year + " and batch=" + batch;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt, query);
            while (rs.next()) {
            thisDeposit.setDeposit_id(rs.getInt("DEPOSIT_ID"));
            thisDeposit.setDepositDate(rs.getString("DEPOSIT_DATE"));
            thisDeposit.setFiscalYear(rs.getString("FISCAL_YEAR"));
            thisDeposit.setBatch(rs.getInt("BATCH"));
            }
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
    }

    public static int getNumberOfDeposits() {
        int number = 0;
        String query = "select deposit_id from deposit ORDER BY deposit_id DESC LIMIT 1";
        try { // select PAY_ID from payment ORDER BY pay_id DESC LIMIT 1
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt, query);
            rs.next();
            number = rs.getInt("deposit_id");

        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }

    public static int getNumberOfDepositBatches(String year) {
        int number = 0;
        String query = "select max(batch) from deposit where FISCAL_YEAR=" + year;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt, query);
            rs.next();
            number = rs.getInt("max(batch)");

        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }
}
