package com.ecsail.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.ecsail.structures.Object_Memo;

import javafx.collections.ObservableList;

public class Note {
	private ObservableList<Object_Memo> memos;
	private int msid;
	
	public Note(ObservableList<Object_Memo> memos, int m) {
		super();
		this.memos = memos;
		this.msid = m;
	}
	
	public Note() { // overload
		super();
	}

	public void add(String note, String date) {
		//String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
		int memo_id = getCount() + 1;
		memos.add(new Object_Memo(memo_id,msid,date,note));
		addMemo(memo_id,msid, date, note);
	}

	protected int getCount() { // gives the last memo_id number
		int count = 0;
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
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
	
	public void addMemo(int memo_id,int msid, String date, String memo) {
        SqlInsert.addMemo(memo_id,msid,date,memo);
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
