package com.ecsail.sql.select;


import com.ecsail.main.Halyard;
import com.ecsail.structures.HashDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlHash {

    public static HashDTO getHashFromMsid(int msid) {
        HashDTO hashDTO = new HashDTO();
        String query = "select * from msid_hash where MS_ID=" + msid;
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                        hashDTO.setHash_id(rs.getInt("HASH_ID"));
                        hashDTO.setHash(rs.getLong("HASH"));
                        hashDTO.setMsid(rs.getInt("MS_ID"));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hashDTO;
    }

    public static ArrayList<HashDTO> getAllHash() {
        ArrayList<HashDTO> hashDTOList = new ArrayList<>();
        String query = "select * from msid_hash";
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                hashDTOList.add(new HashDTO(
                rs.getInt("HASH_ID"),
                rs.getLong("HASH"),
                rs.getInt("MS_ID")));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hashDTOList;
    }

    public static HashDTO getMsidFromHash(String hash) {
        HashDTO hashDTO = new HashDTO();
        String query = "select * from msid_hash where HASH=" + hash;
        try {
            ResultSet rs = Halyard.getConnect().executeSelectQuery(query);
            while (rs.next()) {
                hashDTO.setHash_id(rs.getInt("HASH_ID"));
                hashDTO.setHash(rs.getLong("HASH"));
                hashDTO.setMsid(rs.getInt("MS_ID"));
            }
            Halyard.getConnect().closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(hashDTO);
        return hashDTO;
    }
}
