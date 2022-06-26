package com.ecsail.sql.select;

import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.main.ConnectDatabase;
import com.ecsail.main.Halyard;
import com.ecsail.structures.MembershipDTO;
import com.ecsail.structures.PersonDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlPerson {

    /**
     *
     * @param ms_id each membership has an ID that never changes
     * @return ObservableList<PersonDTO>
     */
    public static ObservableList<PersonDTO> getPeople(int ms_id) {
        String query = "SELECT * FROM person WHERE ms_id=" + ms_id;
        ObservableList<PersonDTO> thesePeople = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
        while (rs.next()) {
            if(rs.getBoolean("IS_ACTIVE")) {  // only add active people
            thesePeople.add(new PersonDTO(
                    rs.getInt("P_ID"),
                    rs.getInt("MS_ID"),
                    rs.getInt("MEMBER_TYPE"),
                    rs.getString("F_NAME"),
                    rs.getString("L_NAME"),
                    rs.getString("BIRTHDAY"),
                    rs.getString("OCCUPATION"),
                    rs.getString("BUISNESS"),
                    rs.getBoolean("IS_ACTIVE"),
                    rs.getString("NICK_NAME"),
                    rs.getInt("OLDMSID")

            ));
            }
        }
        stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thesePeople;
    }

    /**
     * Gets an arraylist of PersonDTO objects
     *
     * @param m a MembershipDTO object
     * @return arraylist of PersonDTO objects
     */
    public static ArrayList<PersonDTO> getDependants(MembershipDTO m) {
        String query = "SELECT * FROM person WHERE ms_id= '" + m.getMsid() + "' and MEMBER_TYPE=3";
        ArrayList<PersonDTO> thesepeople = new ArrayList<>();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
        while (rs.next()) {
            if(rs.getBoolean("IS_ACTIVE")) {  // only add active people
            thesepeople.add(new PersonDTO(
                    rs.getInt("P_ID"),
                    rs.getInt("MS_ID"),
                    rs.getInt("MEMBER_TYPE"),
                    rs.getString("F_NAME"),
                    rs.getString("L_NAME"),
                    rs.getString("BIRTHDAY"),
                    rs.getString("OCCUPATION"),
                    rs.getString("BUISNESS"),
                    rs.getBoolean("IS_ACTIVE"),
                    rs.getString("NICK_NAME"),
                    rs.getInt("OLDMSID")));
            }
        }
        stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thesepeople;
    }

    /**
     *
     * @return observable list of PersonDTO objects
     */
    public static ObservableList<PersonDTO> getPeople() {
        String query = "SELECT * FROM person";
        ObservableList<PersonDTO> thesePeople = FXCollections.observableArrayList();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
        while (rs.next()) {
            thesePeople.add(new PersonDTO(
                    rs.getInt("P_ID"),
                    rs.getInt("MS_ID"),
                    rs.getInt("MEMBER_TYPE"),
                    rs.getString("F_NAME"),
                    rs.getString("L_NAME"),
                    rs.getString("BIRTHDAY"),
                    rs.getString("OCCUPATION"),
                    rs.getString("BUISNESS"),
                    rs.getBoolean("IS_ACTIVE"),
                    rs.getString("NICK_NAME"),
                    rs.getInt("OLDMSID")));
        }
        stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thesePeople;
    }

    /**
     * Returns a PersonDTO using a pid
     *
     * @param pid Each person has a unique primary key known as pid
     * @return PersonDTO
     */
    public static PersonDTO getPersonByPid(int pid) {
        PersonDTO person = null;
        String query = "select * from person WHERE p_id=" + pid;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            if(rs.next()) {
                person = (new PersonDTO(rs.getInt("P_ID"), rs.getInt("MS_ID"), rs.getInt("MEMBER_TYPE"),
                        rs.getString("F_NAME"), rs.getString("L_NAME"), rs.getString("BIRTHDAY"),
                        rs.getString("OCCUPATION"), rs.getString("BUISNESS"), rs.getBoolean("IS_ACTIVE"),rs.getString("NICK_NAME"),rs.getInt("OLDMSID")));
            } else {
                System.out.println("There were no results for SqlPerson.getPersonByPid(int pid)");
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return person;
    }

    /**
     * Returns a PersonDTO using a ms_id and member_type as parameters
     *
     * @param ms_id each membership has an ID that never changes
     * @param member_type denotes the type of member, 1 for primary, 2 for secondary, 3 for dependent
     * @return returns a PersonDTO object
     */
    public static PersonDTO getPerson(int ms_id, int member_type) {
        PersonDTO person = null;
        String query = "select * from person where MS_ID=" + ms_id + " and MEMBER_TYPE=" + member_type;
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            if(rs.next()) {
                person = (new PersonDTO(rs.getInt("P_ID"), rs.getInt("MS_ID"), rs.getInt("MEMBER_TYPE"),
                        rs.getString("F_NAME"), rs.getString("L_NAME"), rs.getString("BIRTHDAY"),
                        rs.getString("OCCUPATION"), rs.getString("BUISNESS"), rs.getBoolean("IS_ACTIVE"),rs.getString("NICK_NAME"),rs.getInt("OLDMSID")));
            } else {
                System.out.println("There were no results for getPerson(int ms_id, int member_type)");
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return person;
    }

    /**
     * This method first gets the MS_ID from a MEMBERSHIP_ID tuple selected by ID and Year.
     * It then selects all people associated with that MS_ID and picks the primary member
     *
     * @param membershipId The number for a membership that changes every few years
     * @param year The year we want to select from
     * @return PersonDTO for the primary member of a selected membership
     */
    public static PersonDTO getPersonFromMembershipID(String membershipId, String year) {
        PersonDTO person = null;
        String query = "select * from person where MS_ID=(select ms_id from membership_id where MEMBERSHIP_ID="+membershipId+" and FISCAL_YEAR="+year+") and MEMBER_TYPE=1";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            if(rs.next()) {
                person = (new PersonDTO(rs.getInt("P_ID"), rs.getInt("MS_ID"), rs.getInt("MEMBER_TYPE"),
                        rs.getString("F_NAME"), rs.getString("L_NAME"), rs.getString("BIRTHDAY"),
                        rs.getString("OCCUPATION"), rs.getString("BUISNESS"), rs.getBoolean("IS_ACTIVE"),rs.getString("NICK_NAME"),rs.getInt("OLDMSID")));
            } else {
                System.out.println("There were no results for getPersonFromMembershipID(String membershipId, String year)");
            }
            stmt.close();
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return person;
    }

    /**
     * Counts the number of people in the database and returns that number
     *
     * @return the number of people tuples in the database
     */
    public static int getCount()  {
        int number = 0;
        String query = "select * from person ORDER BY p_id DESC LIMIT 1";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            rs.next();
            number = rs.getInt("P_ID");
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return number;
    }

    /**
     *
     * @param person a PersonDTO
     * @return an integer representing the age of the person in the personDTO
     */
    public static int getPersonAge(PersonDTO person)  {
        int age = 0;
        String query = "SELECT DATE_FORMAT(FROM_DAYS(DATEDIFF(now(),(select BIRTHDAY from person where P_ID=" + person.getP_id() + "))), '%Y')+0 AS AGE;\n";
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = Halyard.getConnect().executeSelectQuery(stmt,query);
            rs.next();
            age = rs.getInt("AGE");
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return age;
    }
}
