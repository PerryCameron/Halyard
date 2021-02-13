package com.ecsail.gui.tabs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ecsail.main.Paths;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.SqlSelect;
import com.ecsail.structures.Object_Temp;

// will use this temporarily to make ids.

public class TabNewYearGenerator {

	static int membershipID = 0;
	static String fName;
	static String lName;
	static int year = 2010;
	private static int MY_MINIMUM_COLUMN_COUNT = 1;
	static List<Object_Temp> memberships = new ArrayList<Object_Temp>();
	static ArrayList<String> tuples = new ArrayList<String>();
	static ArrayList<String> errortuples = new ArrayList<String>();
	static ArrayList<String> existtuples = new ArrayList<String>();
	public static void makeItSo() throws IOException {
		// put memberships into a list
		readFromExcel(Paths.ROSTERS + "/"+year+"_Roster.xlsx");
		System.out.println("Size of memberships are " + memberships.size());
		int mid = SqlSelect.getCount("membership_id", "mid") + 1; // get last mid number add 1
	//	System.out.println(SqlExists.isThere(memberships.get(0), year));
		for(Object_Temp temp: memberships) {
			//System.out.println(temp.toString());
			if(SqlExists.isThere(temp, year)) {
			    existtuples.add("This record exists->" + temp.toString());
			} else {
				//SqlInsert.addMembershipId(new Object_MembershipId());
				int ms_id = SqlSelect.getMSID(temp, 2020, errortuples);
				tuples.add("INSERT INTO membership_id () VALUES (" + mid + "," + year + "," + ms_id + "," + temp.getMembership_id() + ");");
				//System.out.println("INSERT INTO membership_id () VALUES (" + mid + "," + year + "," + ms_id + "," + temp.getMembership_id() + ");");
				mid++;
			}
			
		}
		
		writeToFile(Paths.ROSTERS + "/RosterTuples.txt");
	}
	
	public static void writeToFile(String filename) {
		try {
			File file = new File(filename);
			FileWriter writer = new FileWriter(file, false);

			for (String s : tuples)
				writer.write(s + System.lineSeparator());
			for (String s : existtuples)
				writer.write(s + System.lineSeparator());
			for (String s : errortuples)
				writer.write(s + System.lineSeparator());
			writer.close();
			tuples.clear();
			existtuples.clear();
			errortuples.clear();
			memberships.clear();
			System.out.println("SQL Insert file sucessfully written");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static void readFromExcel(String file) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
		XSSFSheet firstSheet = workbook.getSheetAt(0);
		int rowStart = Math.min(1, firstSheet.getFirstRowNum());
		int rowEnd = Math.max(600, firstSheet.getLastRowNum());
		for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
			Row r = firstSheet.getRow(rowNum);
			if (r == null) {
				continue;
			}
			int lastColumn = Math.max(r.getLastCellNum(), MY_MINIMUM_COLUMN_COUNT);
			int count = 0;
			Boolean isNewRow = false;
			Object_Temp m = null;
			for (int cn = 0; cn < lastColumn; cn++) {
				Cell cell = r.getCell(cn, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				
				System.out.println(cell);
				if(cell != null) {
				CellType type = cell.getCellType();
				if (type == CellType.NUMERIC) {
					isNewRow = true;
					m = new Object_Temp();
				}
				}
				if (isNewRow) {
					if (count == 0)
						m.setMembership_id((int) cell.getNumericCellValue());
					if (count == 1)
						m.setLname(cell.getStringCellValue().trim());
					if (count == 2) {
						m.setFname(cell.getStringCellValue().trim());
						memberships.add(m);
						//System.out.println(m.toString());
						count = 0;
						isNewRow = false;
					}
				}
				count++;
			}
		}
		workbook.close();
	}

}
