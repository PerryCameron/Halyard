package com.ecsail.sql.select;


import com.ecsail.main.ConnectDatabase;
import com.ecsail.structures.HashDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HashSeSQL {

    public static HashDTO getHashFromMsid(int msid) {
        HashDTO hashDTO = new HashDTO();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from msid_hash where MS_ID=" + msid + ";");
            while (rs.next()) {
                        hashDTO.setHash_id(rs.getInt("HASH_ID"));
                        hashDTO.setHash(rs.getLong("HASH"));
                        hashDTO.setMsid(rs.getInt("MS_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(hashDTO);
        return hashDTO;
    }

    public static HashDTO getAllHash() {
        HashDTO hashDTO = new HashDTO();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from msid_hash");
            while (rs.next()) {
                hashDTO.setHash_id(rs.getInt("HASH_ID"));
                hashDTO.setHash(rs.getLong("HASH"));
                hashDTO.setMsid(rs.getInt("MS_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(hashDTO);
        return hashDTO;
    }

    public static HashDTO getMsidFromHash(String hash) {
        HashDTO hashDTO = new HashDTO();
        try {
            Statement stmt = ConnectDatabase.sqlConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from msid_hash where HASH=" + hash + ";");
            while (rs.next()) {
                hashDTO.setHash_id(rs.getInt("HASH_ID"));
                hashDTO.setHash(rs.getLong("HASH"));
                hashDTO.setMsid(rs.getInt("MS_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(hashDTO);
        return hashDTO;
    }
}
