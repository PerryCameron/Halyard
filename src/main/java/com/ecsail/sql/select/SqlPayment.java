package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Main;
import com.ecsail.structures.Object_Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlPayment {
    public static ObservableList<Object_Payment> getPayments() {
        ObservableList<Object_Payment> thisPayments = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor("select * from payment;"));
            while (rs.next()) {
                thisPayments.add(new Object_Payment(
                        rs.getInt("PAY_ID"),
                        rs.getInt("MONEY_ID"),
                        rs.getString("CHECKNUMBER"),
                        rs.getString("PAYMENT_TYPE"),
                        rs.getString("PAYMENT_DATE"),
                        rs.getString("AMOUNT"),
                        rs.getInt("DEPOSIT_ID")
                        ));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisPayments;
    }

    public static ObservableList<Object_Payment> getPayments(int money_id) {
        ObservableList<Object_Payment> thisPayments = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor("select * from payment where money_id=" + money_id));
            while (rs.next()) {
                thisPayments.add(new Object_Payment(
                        rs.getInt("PAY_ID"),
                        rs.getInt("MONEY_ID"),
                        rs.getString("CHECKNUMBER"),
                        rs.getString("PAYMENT_TYPE"),
                        rs.getString("PAYMENT_DATE"),
                        rs.getString("AMOUNT"),
                        rs.getInt("DEPOSIT_ID")
                        ));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisPayments;
    }

    public static Object_Payment getPayment(int money_id) {
        Object_Payment thisPayment = null;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(Main.console.setRegexColor("select * from payment where money_id=" + money_id));
            while (rs.next()) {
                thisPayment = new Object_Payment(
                        rs.getInt("PAY_ID"),
                        rs.getInt("MONEY_ID"),
                        rs.getString("CHECKNUMBER"),
                        rs.getString("PAYMENT_TYPE"),
                        rs.getString("PAYMENT_DATE"),
                        rs.getString("AMOUNT"),
                        rs.getInt("DEPOSIT_ID")
                        );
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisPayment;
    }

    public static int getNumberOfPayments() {
        int number = 0;
        ResultSet rs;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            rs = stmt.executeQuery("select PAY_ID from payment ORDER BY pay_id DESC LIMIT 1");
            rs.next();
            number = rs.getInt("PAY_ID");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }
}
