package com.ecsail.pdf.directory;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.ecsail.main.Paths;
import com.ecsail.sql.SQL_SelectMembership;
import com.ecsail.structures.Object_MembershipList;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
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

public class PDF_Directory {
	private ObservableList<Object_MembershipList> rosters;
	
	PDF_Object_Settings set;
	
	public PDF_Directory(String year) {
		this.set = new PDF_Object_Settings(year);
		
		this.rosters = SQL_SelectMembership.getRoster(year, true);
		Paths.checkPath(Paths.EMAILLIST);

		
		try {
			createDirectory();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("destination=" + Paths.EMAILLIST + "_directory.pdf");
		File file = new File(Paths.EMAILLIST + "_directory.pdf");
		Desktop desktop = Desktop.getDesktop(); // Gui_Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()

		// Open the document
		try {
			desktop.open(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createDirectory() throws FileNotFoundException {
		PdfWriter writer = new PdfWriter(Paths.EMAILLIST + "_directory.pdf");
		// Initialize PDF document
		PdfDocument pdf = new PdfDocument(writer);
		PageSize A5v = new PageSize(PageSize.A5.getWidth(), PageSize.A5.getHeight());
		Document doc = new Document(pdf, new PageSize(A5v));
		doc.setLeftMargin(0.5f);
		doc.setRightMargin(0.5f);
		doc.setTopMargin(1f);
		doc.setBottomMargin(0.5f);
		
		Collections.sort(rosters , Comparator.comparing(Object_MembershipList::getLname));
		
		doc.add(new PDF_Cover(1, set));
		doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
		
		doc.add(new PDF_CommodoreMessage(1,set));
		doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
		
		doc.add(new PDF_BoardOfDirectors(1,set));
		doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
		
		doc.add(new PDF_TableOfContents(1,set));
		doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
		
		doc.add(new PDF_ChapterPage(1, "Membership Information",set));
		doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
		createMemberInfoPages(doc);
		
		createMemberByNumberPages(doc);
		doc.close();
	}
	
	private void createMemberByNumberPages(Document doc) {
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
		doc.add(listTable);
		doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
		listTable = new Table(5);
		listTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
		listTable.addCell(tables.get(5));
		listTable.addCell(tables.get(6));
		listTable.addCell(tables.get(7));
		listTable.addCell(tables.get(8));
		listTable.addCell(tables.get(9));
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
	
	private Cell addVerticalSpace(int space) {
		String carrageReturn = "";
		for(int i = 0; i < space; i++) {
			carrageReturn += "\n";
		}
		Cell cell = new Cell();
		cell.add(new Paragraph(carrageReturn));
		cell.setBorder(Border.NO_BORDER);
		return cell;
	}
	
	private void createMemberInfoPages(Document doc) {
			int count = 0;
			doc.add(new Paragraph("\n"));
			for(Object_MembershipList l: rosters) {
			doc.add(new PDF_MemberShipInformation(2,l,set));
			count++;
			if(count % 6 == 0) {
				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
				doc.add(new Paragraph("\n"));
			}
			//if(count == 60) break;  // this reduces pages made for testing
		}
	}

}
