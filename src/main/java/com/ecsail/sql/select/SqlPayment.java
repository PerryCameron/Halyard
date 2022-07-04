package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Halyard;
import com.ecsail.structures.PaymentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlPayment {
    public static ObservableList<PaymentDTO> getPayments() {
        ObservableList<PaymentDTO> thisPayments = FXCollections.observableArrayList();
        String query = "select * from payment";
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                thisPayments.add(new PaymentDTO(
                        rs.getInt("PAY_ID"),
                        rs.getInt("MONEY_ID"),
                        rs.getString("CHECKNUMBER"),
                        rs.getString("PAYMENT_TYPE"),
                        rs.getString("PAYMENT_DATE"),
                        rs.getString("AMOUNT"),
                        rs.getInt("DEPOSIT_ID")
                        ));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisPayments;
    }

    public static ObservableList<PaymentDTO> getPayments(int money_id) {
        ObservableList<PaymentDTO> thisPayments = FXCollections.observableArrayList();
        String query = "select * from payment where money_id=" + money_id;
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                thisPayments.add(new PaymentDTO(
                        rs.getInt("PAY_ID"),
                        rs.getInt("MONEY_ID"),
                        rs.getString("CHECKNUMBER"),
                        rs.getString("PAYMENT_TYPE"),
                        rs.getString("PAYMENT_DATE"),
                        rs.getString("AMOUNT"),
                        rs.getInt("DEPOSIT_ID")
                        ));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisPayments;
    }

    public static PaymentDTO getPayment(int money_id) {
        PaymentDTO thisPayment = null;
        String query = "select * from payment where money_id=" + money_id;
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                thisPayment = new PaymentDTO(
                        rs.getInt("PAY_ID"),
                        rs.getInt("MONEY_ID"),
                        rs.getString("CHECKNUMBER"),
                        rs.getString("PAYMENT_TYPE"),
                        rs.getString("PAYMENT_DATE"),
                        rs.getString("AMOUNT"),
                        rs.getInt("DEPOSIT_ID")
                        );
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisPayment;
    }

    public static int getNumberOfPayments() {
        int number = 0;
        String query = "select PAY_ID from payment ORDER BY pay_id DESC LIMIT 1";
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            number = rs.getInt("PAY_ID");
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }
}
