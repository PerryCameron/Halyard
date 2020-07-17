package com.ecsail.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	public void add(String note) {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
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
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("INSERT INTO memo () VALUES (" + memo_id + "," + msid + ",'" + date + "',\"" + memo + "\");"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateMemo(int memo_id, String field, String attribute)  {
		try {
			Statement stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("UPDATE memo SET " + field + "=\"" + attribute + "\" WHERE memo_id='" + memo_id + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeMemo(int index) {
		Statement stmt;
		try {
			stmt = ConnectDatabase.connection.createStatement();
			stmt.execute(Main.console.setRegexColor("delete from memo where memo_id='" + memos.get(index).getMemo_id() + "';"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
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
