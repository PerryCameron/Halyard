package com.ecsail.pdf.directory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.ecsail.structures.Object_MembershipList;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import javafx.collections.ObservableList;

public class PDF_MembersByNumber {
	ObservableList<Object_MembershipList> rosters;
	PDF_Object_Settings set;
	
	public PDF_MembersByNumber(PDF_Object_Settings set, Document doc, ObservableList<Object_MembershipList> rosters) {
		this.set = set;
		this.rosters = rosters;
		Collections.sort(this.rosters , Comparator.comparing(Object_MembershipList::getMembershipId));
		ArrayList<Table> tables = new ArrayList<Table>();
		
		int count = 0;
		int columnLength = 28;
		int remainder = rosters.size() % columnLength;
		int numberOfColumnTables = rosters.size() / columnLength;
		if(remainder > 0) numberOfColumnTables++;
		Table columnTable;
		
		while(count < rosters.size() - remainder) {
			columnTable = new Table(1);
			count = fillColumnTable(columnLength,count,columnTable);
			tables.add(columnTable);
		}
		/// creates a column table with the remainder
		while(count < rosters.size()) {
			columnTable = new Table(1);
			count = fillColumnTable(remainder, count, columnTable);
			tables.add(columnTable);
		}
		Table listTable = new Table(5);
		listTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
		listTable.addCell(tables.get(0));
		listTable.addCell(tables.get(1));
		listTable.addCell(tables.get(2));
		listTable.addCell(tables.get(3));
		listTable.addCell(tables.get(4));
		System.out.println("tables=" + numberOfColumnTables);
		doc.add(new Paragraph("\n"));
		doc.add(listTable);
		doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
		listTable = new Table(5);
		listTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
		listTable.addCell(tables.get(5));
		listTable.addCell(tables.get(6));
		listTable.addCell(tables.get(7));
		listTable.addCell(tables.get(8));
		listTable.addCell(tables.get(9));
		doc.add(new Paragraph("\n"));
		doc.add(listTable);
	}
	private int fillColumnTable(int columnSize, int count, Table columnTable) {
		for(int i = 0; i < columnSize; i++) {
			columnTable.addCell(createMembershipEntry(rosters.get(count )));
			count++;
		}
		return count;
	}
	
	private Cell createMembershipEntry(Object_MembershipList m) {
		Cell cell;
		Paragraph p;
		cell = new Cell();
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.setBorder(Border.NO_BORDER);
		p = new Paragraph(m.getMembershipId() + "  " + m.getLname());
		p.setFontSize(set.getNormalFontSize() - 2);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		return cell;
	}
}