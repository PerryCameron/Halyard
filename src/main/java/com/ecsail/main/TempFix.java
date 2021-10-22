package com.ecsail.main;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.Object_Money;
import com.ecsail.structures.Object_TempFix;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TempFix {
    private List<Object_TempFix> creditList;
    private List<Object_Money> invoiceList;

    public TempFix() {
        this.creditList = getWorkCredits();
        this.invoiceList = getMonies();

        for (Object_Money m : invoiceList) {
            m.setOther_credit("0.00");
        }

        for (Object_TempFix t : creditList) {
            getMoneyObject(t.getMoney_id()).setWork_credit(t.getWork_credit());
        }

        for (Object_Money m : invoiceList) {
            SqlUpdate.updateMoney(m);
        }
    }

    public Object_Money getMoneyObject(int money_id) {
        for(Object_Money m: invoiceList) {
            if(m.getMoney_id() == money_id) {
                return m;
            }
        }
        return null;
    }

    public static List<Object_Money> getMonies() { // overload
        String query = "SELECT * FROM money";
        List<Object_Money> theseFiscals = new ArrayList<>();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery(Main.console.setRegexColor(query));
            while (rs.next()) {
                theseFiscals.add(new Object_Money(rs.getInt("MONEY_ID"), rs.getInt("MS_ID"),
                        rs.getInt("FISCAL_YEAR"), rs.getInt("BATCH"), rs.getString("OFFICER_CREDIT"), rs.getInt("EXTRA_KEY"),
                        rs.getInt("KAYAK_SHED_KEY"), rs.getInt("SAIL_LOFT_KEY"),
                        rs.getInt("SAIL_SCHOOL_LOFT_KEY"), rs.getInt("BEACH"),
                        rs.getString("WET_SLIP"), rs.getInt("KAYAK_RACK"), rs.getInt("KAYAK_SHED"),
                        rs.getInt("SAIL_LOFT"), rs.getInt("SAIL_SCHOOL_LASER_LOFT"), rs.getInt("WINTER_STORAGE"),
                        rs.getString("YSC_DONATION"),rs.getString("PAID"),rs.getString("TOTAL"),rs.getString("CREDIT"),
                        rs.getString("BALANCE"), rs.getString("DUES"),rs.getBoolean("COMMITED"),rs.getBoolean("CLOSED"),
                        rs.getString("OTHER"),rs.getString("INITIATION"),rs.getBoolean("SUPPLEMENTAL"),rs.getInt("WORK_CREDIT"),rs.getString("OTHER_CREDIT")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseFiscals;
    }

    public static List<Object_TempFix> getWorkCredits() {
        List<Object_TempFix> theseMemos = new ArrayList<>();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("select (RACING)+(HARBOR)+(SOCIAL)+(OTHER), MONEY_ID from work_credit");
            while (rs.next()) {
                theseMemos.add(new Object_TempFix( // why do I keep gettin a nullpointer exception here?

                        rs.getInt("MONEY_ID"),
                        rs.getInt("(RACING)+(HARBOR)+(SOCIAL)+(OTHER)")));
            }
            return theseMemos;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
        }
        return theseMemos;
    }

}
