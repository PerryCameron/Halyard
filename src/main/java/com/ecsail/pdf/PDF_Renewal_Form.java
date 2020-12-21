package com.ecsail.pdf;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.ecsail.main.Paths;
import com.ecsail.structures.Object_Boat;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.VerticalAlignment;

import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

public class PDF_Renewal_Form {
	private static String year;
	private static String membership_id; 
	private int borderSize = 1;
	private ArrayList<Object_Boat> boats = new ArrayList<Object_Boat>();
	
	public PDF_Renewal_Form(String y, String memId) throws IOException {
		PDF_Renewal_Form.year = y;
		PDF_Renewal_Form.membership_id = memId;
		boats.add(new Object_Boat(0, 0, "Manufacturer", "Year", "Registration", "Model", "Boat Name", "Sail #", true, "Length", "Header", "Keel Type", "PHRF"));
		boats.add(new Object_Boat(0, 0, "Flying Scot Inc.", "2016", "IN34234", "Flying Scot", "Crow", "3845", true, "19", "800", "Center Board", "PHRF"));
		boats.add(new Object_Boat(0, 0, "", "", "", "", "", "", true, "", "", "", ""));
		// Check if our path exists, if not create it
		Paths.checkPath(Paths.RENEWALFORM + "/" + year);
		// Initialize PDF writer
		
		
		PdfWriter writer = new PdfWriter(Paths.RENEWALFORM + "/" + year + "/" + year + "_Renewal_Forms.pdf");

		// Initialize PDF document
		PdfDocument pdf = new PdfDocument(writer);
		// Initialize document
		Document document = new Document(pdf);
		document.setTopMargin(0);
		document.setLeftMargin(0.5f);
		document.setRightMargin(0.5f);
		//FontProgramFactory.registerFont("c:/windows/fonts/Arial.ttf", "arial");
		FontProgramFactory.registerFont("c:/windows/fonts/garabd.ttf", "garamond bold");
		PdfFont font = PdfFontFactory.createRegisteredFont("garamond bold");
		//PdfFont font = PdfFontFactory.createFont("arial");

		//Rectangle rectangle = pdf.getDefaultPageSize();
		//float width = rectangle.getWidth() - document.getLeftMargin() - document.getRightMargin();
		//System.out.println("Page size is " + rectangle);
		// add tables here
		document.add(titlePdfTable(font));
		document.add(membershipNumberPdfTable(font));
		document.add(personPdfTable());
		document.add(childrenPdfTable());
		document.add(boatsPdfTable());
		//document.add(testTable());
		// Close document
		document.close();
		
		System.out.println("destination=" + Paths.RENEWALFORM + "/" + year + "/" + year + "_Renewal_Forms.pdf");
		File file = new File(Paths.RENEWALFORM + "/" + year + "/" + year + "_Renewal_Forms.pdf");
		Desktop desktop = Desktop.getDesktop(); // Gui_Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()
		
		// Open the document
		try {
			desktop.open(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	///////////  Class Methods ///////////////
	
	public Table testTable() {

		Table table = new Table(UnitValue.createPercentArray(new float[] {1, 2, 2, 2, 1}));

        Cell cell = new Cell(2, 1).add(new Paragraph("S/N"));
        table.addCell(cell);

        cell = new Cell(1, 3).add(new Paragraph("Name"));
        table.addCell(cell);

        cell = new Cell(2, 1).add(new Paragraph("Age"));
        table.addCell(cell);

        table.addCell("SURNAME");
        table.addCell("FIRST NAME");
        table.addCell("MIDDLE NAME");
        table.addCell("1");
        table.addCell("James");
        table.addCell("Fish");
        table.addCell("Stone");
        table.addCell("17");
        return table;
	}
	
	public Table boatsPdfTable() {
		Table mainTable = new Table(9);
		mainTable.setWidth(590);
		for(Object_Boat b: boats) {
			createBoatTableRow(mainTable, b);
		}
		
		Cell cell;
		cell = new Cell(1,9);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.add(new Paragraph("If boat is co-owned, put a check next to the boat and list co-owner(s) __________________________________").setFontSize(10));
		mainTable.addCell(cell);
		return mainTable;
	}
	
	public void createBoatTableRow(Table mainTable, Object_Boat boat) {
		Boolean isHeader = false;
		//////// Determine if Header ///
		if (boat.getWeight().equals("Header")) // Used unused field to mark the header
			isHeader = true;
		else
			isHeader = false;
		/////// Create reference ///
		Cell cell;
		Paragraph p;

		p = new Paragraph(boat.getBoat_name());
		p.setFontSize(10);
		cell = new Cell();
		if (isHeader) {
			cell.setBorderTop(new SolidBorder(borderSize));
			cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		}
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.add(p);
		mainTable.addCell(cell);

		p = new Paragraph(boat.getRegistration_num());
		p.setFontSize(10);
		cell = new Cell();
		if (isHeader) {
			cell.setBorderTop(new SolidBorder(borderSize));
			cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		}
		cell.add(p);
		mainTable.addCell(cell);

		p = new Paragraph(boat.getManufacturer());
		p.setFontSize(10);
		cell = new Cell();
		if (isHeader) {
			cell.setBorderTop(new SolidBorder(borderSize));
			cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		}
		cell.add(p);
		mainTable.addCell(cell);

		p = new Paragraph(boat.getManufacture_year());
		p.setFontSize(10);
		cell = new Cell();
		if (isHeader) {
			cell.setBorderTop(new SolidBorder(borderSize));
			cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		}
		cell.add(p);
		mainTable.addCell(cell);

		p = new Paragraph(boat.getModel());
		p.setFontSize(10);
		cell = new Cell();
		if (isHeader) {
			cell.setBorderTop(new SolidBorder(borderSize));
			cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		}
		cell.add(p);
		mainTable.addCell(cell);

		p = new Paragraph(boat.getLength());
		p.setFontSize(10);
		cell = new Cell();
		if (isHeader) {
			cell.setBorderTop(new SolidBorder(borderSize));
			cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		}
		cell.add(p);
		mainTable.addCell(cell);

		p = new Paragraph(boat.getKeel());
		p.setFontSize(10);
		cell = new Cell();
		if (isHeader) {
			cell.setBorderTop(new SolidBorder(borderSize));
			cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		}
		cell.add(p);
		mainTable.addCell(cell);

		if (isHeader)
			p = new Paragraph("Has Trailer");
		else
			p = new Paragraph(boat.isHasTrailer() + "");
		p.setFontSize(10);
		cell = new Cell();
		if (isHeader) {
			cell.setBorderTop(new SolidBorder(borderSize));
			cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		}
		cell.add(p);
		mainTable.addCell(cell);

		p = new Paragraph(boat.getSail_number());
		p.setFontSize(10);
		cell = new Cell();
		if (isHeader) {
			cell.setBorderTop(new SolidBorder(borderSize));
			cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		}
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.add(p);
		// cell.setBorder(Border.NO_BORDER);
		// cell.setBorderLeft(new SolidBorder(borderSize));
		// cell.setBorderRight(new SolidBorder(borderSize));
		// cell.setBorderTop(new SolidBorder(borderSize));
		mainTable.addCell(cell);
	}
	
	public Table childrenPdfTable() {
		Table mainTable = new Table(2);
		Cell cell;
		Paragraph p;
		
		p = new Paragraph("Children under age 12 (In order of oldest first)");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setWidth(580);
		cell.add(p);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.setBorderTop(new SolidBorder(borderSize));
		mainTable.addCell(cell);
		
		p = new Paragraph("First Name(s):");
		p.setFontSize(10);
		cell = new Cell();
		cell.setWidth(290);
		cell.add(p);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		mainTable.addCell(cell);
		
		p = new Paragraph("Birth Year(s): ");
		p.setFontSize(10);
		cell = new Cell();
		cell.setWidth(290);
		cell.add(p);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(borderSize));
		mainTable.addCell(cell);
		
		return mainTable;
	}
	
	public Table personPdfTable() {
		Table mainTable = new Table(4);
		//mainTable.setBorderTop(Border.SOLID);
		Cell cell;
		Paragraph p;
		
		p = new Paragraph("Member Name: John P. Hunklemont");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setWidth(290);
		cell.add(p);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setBorderTop(new SolidBorder(borderSize));
		mainTable.addCell(cell);
		
		p = new Paragraph("Spouse/Partner Name: Martha. Hunklemont");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setWidth(290);
		cell.add(p);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.setBorderTop(new SolidBorder(borderSize));
		mainTable.addCell(cell);
		
		p = new Paragraph("Email:");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Email:");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Cell Phone: ");
		p.setFontSize(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Birth Year: _____________");
		p.setFontSize(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Cell Phone: ");
		p.setFontSize(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Birth Year: _____________");
		p.setFontSize(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Occupation: ");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(290);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Occupation: ");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.setWidth(290);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Company/Business: ");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(290);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Company/Business: ");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.setWidth(290);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Company/Business Phone:");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(290);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Company/Business Phone:");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.setWidth(290);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Circle any email addresses or phone numbers that you do not want listed in the membership directory");
		p.setFontSize(10);
		cell = new Cell(1,4);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.add(p);
		mainTable.addCell(cell);
		
		return mainTable;
	}
	

	
	public Table membershipNumberPdfTable(PdfFont font)  { // 580 total cell width
		
		Table mainTable = new Table(5);
		//mainTable.setBorderTop(Border.SOLID);
		Cell cell;
		Paragraph p;
		
		p = new Paragraph(Integer.parseInt(year) -1 + " Membership Number: 123");
		p.setFontSize(10);
		cell = new Cell(1,1);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(borderSize));
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.add(p);
		cell.setWidth(180);
		mainTable.addCell(cell);
		
		p = new Paragraph(Integer.parseInt(year) + " Membership Number: 98");
		p.setFontSize(10);
		cell = new Cell(1,4);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(borderSize));
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.add(p);
		cell.setWidth(400);
		mainTable.addCell(cell);
		
		p = new Paragraph("Address: 4421 N. Frankilin Rd.");
		p.setFontSize(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(180);
		cell.add(p);

		mainTable.addCell(cell);
		
		p = new Paragraph("City: Indianapolis");
		p.setFontSize(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);

		mainTable.addCell(cell);
		
		p = new Paragraph("State: IN");
		p.setFontSize(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setWidth(20);
		cell.add(p);

		mainTable.addCell(cell);
		
		p = new Paragraph("Zip: 46226");
		p.setFontSize(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		cell.setWidth(80);
		mainTable.addCell(cell);
		
		p = new Paragraph("Emergency Phone: 123-456-7894");
		p.setFontSize(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.add(p);
		mainTable.addCell(cell);
		

		
		return mainTable;
	}
	
	public Table titlePdfTable(PdfFont font)  {
		
		Image ecscLogo = new Image(ImageDataFactory.create(PDF_DepositReport.toByteArray(getClass().getResourceAsStream("/EagleCreekLogoForPDF.png"))));
		Table mainTable = new Table(3);
		Cell cell;
		Paragraph p;
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setWidth(90);		
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
        p = new Paragraph("Eagle Creek Sailing Club, Inc");
        p.setTextAlignment(TextAlignment.CENTER);
        p.setFontSize(12).setBold().setFont(font);
        p.setFixedLeading(10);
		cell.add(p);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.setWidth(410);
		mainTable.addCell(cell);
		
		cell = new Cell(3,1);
		cell.setBorder(Border.NO_BORDER);
		cell.setWidth(90);
		ecscLogo.setMarginLeft(30);
		ecscLogo.scale(0.30f, 0.30f);
		cell.add(ecscLogo);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
        p = new Paragraph(year + " Annual Dues and Fees Statement");
        p.setTextAlignment(TextAlignment.CENTER);
        p.setFontSize(12).setBold().setFont(font);
        p.setFixedLeading(10);
		cell.add(p);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
        p = new Paragraph("Due February 29, " + year);
        p.setTextAlignment(TextAlignment.CENTER);
        p.setFontSize(12).setBold().setFont(font);
        p.setFixedLeading(10);
		cell.add(p);
		mainTable.addCell(cell);

		return mainTable;
	}

}
