package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.Halyard;
import com.ecsail.main.HalyardPaths;
import com.ecsail.structures.EmailDTO;
import com.ecsail.structures.Email_InformationDTO;
import com.ecsail.structures.PersonDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlEmail {
    public static ObservableList<Email_InformationDTO> getEmailInfo() {
        ObservableList<Email_InformationDTO> thisEmailInfo = FXCollections.observableArrayList();
        String query = "select id.MEMBERSHIP_ID,m.JOIN_DATE,p.L_NAME,p.F_NAME,EMAIL,PRIMARY_USE "
                + "from email e "
                + "inner join person p ON p.P_ID=e.P_ID "
                + "inner join membership m ON m.ms_id=p.ms_id "
                + "inner join membership_id id ON id.ms_id=m.ms_id "
                + "where id.fiscal_year='" + HalyardPaths.getYear()
                + "' and id.renew=true"
                + " order by id.MEMBERSHIP_ID";
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                thisEmailInfo.add(new Email_InformationDTO(rs.getInt("MEMBERSHIP_ID"), rs.getString("JOIN_DATE"),
                        rs.getString("L_NAME"), rs.getString("F_NAME"), rs.getString("EMAIL"),
                        rs.getBoolean("PRIMARY_USE")));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisEmailInfo;
    }

    public static ObservableList<EmailDTO> getEmail(int p_id) {
        String query = "SELECT * FROM email";
        if(p_id != 0)
            query += " WHERE p_id=" + p_id;
        ObservableList<EmailDTO> email = FXCollections.observableArrayList();
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                email.add(new EmailDTO(
                        rs.getInt("EMAIL_ID")
                        ,rs.getInt("P_ID")
                        ,rs.getBoolean("PRIMARY_USE")
                        ,rs.getString("EMAIL")
                        ,rs.getBoolean("EMAIL_LISTED")));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return email;
    }

    public static String getEmail(PersonDTO person) {
        EmailDTO email = null;
        String returnEmail = "";
        String query = "select * from email where P_ID=" + person.getP_id() + " and PRIMARY_USE=true";
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            rs.next();
                email = new EmailDTO(
                        rs.getInt("EMAIL_ID")
                        ,rs.getInt("P_ID")
                        ,rs.getBoolean("PRIMARY_USE")
                        ,rs.getString("EMAIL")
                        ,rs.getBoolean("EMAIL_LISTED"));
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        if(email.getEmail() != null) {
            returnEmail = email.getEmail();
        }
        return returnEmail;
    }
}
