package com.ecsail.pdf;

import com.ecsail.main.SqlSelect;
import com.ecsail.structures.Object_PaidDues;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import javafx.collections.ObservableList;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
	
public class PDF_FiscalReport {

		private static ObservableList<Object_PaidDues> paidDues;
		static String[] boatHeaders = {
				"MemberShip ID",
				"Last",
				"First",
				"Dues",
				"Credit",
				"YSC Donation",
				"Paid",
				"Total",
				"Balance",
				"Batch",
			};
		static String dest = System.getProperty("user.home") + "/Fiscal_Report.pdf";
	    public static int counter = 1;
	    
		public PDF_FiscalReport(String selectedYear) throws IOException {
			paidDues = SqlSelect.getPaidDues(selectedYear);
			
			
			sortByMembershipId();
			
			
			// Initialize PDF writer
			PdfWriter writer = null;

			writer = new PdfWriter(dest);

			// Initialize PDF document
			PdfDocument pdf = new PdfDocument(writer);

			// Initialize document
			Document document = new Document(pdf);
			// PageSize ps = PageSize.A5;

			document.add(mainPdfTable());
			document.setTopMargin(0);
			// Close document
			document.close();
			File file = new File(dest);
			Desktop desktop = Desktop.getDesktop(); // Gui_Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()
			desktop.open(file);
		}

	    public static Table mainPdfTable() throws IOException {
	    	Table mainTable = new Table(boatHeaders.length);
	    	mainTable.setBorder(Border.NO_BORDER);
	    	
			for(String str : boatHeaders ) {
				mainTable.addCell(new Cell()
						.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f))
						//.setWidth(12)
						.add(new Paragraph(str).setFontSize(10)));
			}
			for(Object_PaidDues due: paidDues) {
				mainTable.addCell(new Cell().add(new Paragraph(due.getMembershipId() + "").setFontSize(7)));
				mainTable.addCell(new Cell().add(new Paragraph(due.getL_name() + "").setFontSize(7)));
				mainTable.addCell(new Cell().add(new Paragraph(due.getF_name() + "").setFontSize(7)));
				mainTable.addCell(new Cell().add(new Paragraph("$" + due.getDues() + "").setFontSize(7)));
				mainTable.addCell(new Cell().add(new Paragraph("$" + due.getCredit() + "").setFontSize(7)));
				mainTable.addCell(new Cell().add(new Paragraph("$" + due.getYsc_donation() + "").setFontSize(7)));
				mainTable.addCell(new Cell().add(new Paragraph("$" + due.getPaid() + "").setFontSize(7)));
				mainTable.addCell(new Cell().add(new Paragraph("$" + due.getTotal() + "").setFontSize(7)));
				mainTable.addCell(new Cell().add(new Paragraph("$" + due.getBalance() + "").setFontSize(7)));
				mainTable.addCell(new Cell().add(new Paragraph(due.getBatch() + "").setFontSize(7)));
			}
	    	

			
			return mainTable;
	    }
	    
	    public static void sortByMembershipId() {
			  Collections.sort(paidDues, new Comparator<Object_PaidDues>() {
			        @Override public int compare(Object_PaidDues p1, Object_PaidDues p2) {
			            return p1.getMembershipId() - p2.getMembershipId(); // Ascending
			        }

			    });
	    }
// https://howtodoinjava.com/java/collections/arraylist/arraylist-sort-objects-by-field/
      
	}



