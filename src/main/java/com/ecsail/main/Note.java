package com.ecsail.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import com.ecsail.sql.SqlDelete;
import com.ecsail.sql.SqlInsert;
import com.ecsail.sql.SqlUpdate;
import com.ecsail.structures.Object_Memo;

import javafx.collections.ObservableList;

public class Note {
	private ObservableList<Object_Memo> memos;
	private int msid;
	
	public Note(ObservableList<Object_Memo> memos, int m) {
		super();
		this.memos = memos;
		this.msid = m;
		//Collections.sort(memos, (p1,p2) -> p1.getMemo_date().compareTo(p2.getMemo_date()));
		Collections.sort(memos, Comparator.comparing(Object_Memo::getMemo_date).reversed());
	}
	
	public Note() { // overload
		super();
	}

	public void add(String note, String date, int money_id, String category) {
		//String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
		int memo_id = getCount() + 1;
		Object_Memo memo = new Object_Memo(memo_id,msid,date,note,money_id,category);
		memos.add(memo); // add in observable list
		addMemo(memo); // add in SQL
	}

	protected int getCount() { // gives the last memo_id number
		int count = 0;
		try {
			Statement stmt = ConnectDatabase.sqlConnection.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery("select * from memo ORDER BY memo_id DESC LIMIT 1");
			rs.next();
			count = rs.getInt("memo_id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public void addMemo(Object_Memo memo) {
        SqlInsert.addMemo(memo);
	}
	
	public void updateMemo(int memo_id, String field, String attribute)  {
		SqlUpdate.updateMemo(memo_id, field, attribute);
	}
	
	public void removeMemo(int index) {
		SqlDelete.deleteMemo(memos.get(index));
	}

	public ObservableList<Object_Memo> getMemos() {
		return memos;
	}

	public void setMemos(ObservableList<Object_Memo> memos) {
		this.memos = memos;
	}

	public int getMsid() {
		return msid;
	}

	public void setMsid(int msid) {
		this.msid = msid;
	}
}
