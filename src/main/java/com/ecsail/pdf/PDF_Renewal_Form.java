package com.ecsail.pdf;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.ecsail.main.Paths;
import com.ecsail.main.SqlSelect;
import com.ecsail.structures.Object_Boat;
import com.ecsail.structures.Object_DefinedFee;
import com.ecsail.structures.Object_Membership;
import com.ecsail.structures.Object_Person;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
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
	private static String current_membership_id;
	private static int ms_id;
	private static Object_Membership membership;
	private static Object_Person primary;
	private static Object_Person secondary;
	Object_DefinedFee definedFees;
	private int borderSize = 1;
	private ArrayList<Object_Boat> boats = new ArrayList<Object_Boat>();
	Image checkedBox = new Image(ImageDataFactory.create(PDF_DepositReport.toByteArray(getClass().getResourceAsStream("/checked-checkbox9x9.png"))));
	Image uncheckedBox = new Image(ImageDataFactory.create(PDF_DepositReport.toByteArray(getClass().getResourceAsStream("/unchecked-checkbox9x9.png"))));
	
	public PDF_Renewal_Form(String y, String memId, boolean isOneMembership) throws IOException {
		PDF_Renewal_Form.year = y;
		PDF_Renewal_Form.current_membership_id = memId;
		this.definedFees = SqlSelect.selectDefinedFees(Integer.parseInt(year));
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
		document.setLeftMargin(0.25f);
		document.setRightMargin(0.25f);
		//FontProgramFactory.registerFont("c:/windows/fonts/Arial.ttf", "arial");
		FontProgramFactory.registerFont("c:/windows/fonts/garabd.ttf", "garamond bold");
		PdfFont font = PdfFontFactory.createRegisteredFont("garamond bold");
		//PdfFont font = PdfFontFactory.createFont("arial");

		Rectangle rectangle = pdf.getDefaultPageSize();
		float width = rectangle.getWidth() - document.getLeftMargin() - document.getRightMargin();
		System.out.println("Page size is " + rectangle);
		// add tables here
		if(isOneMembership) {  // we are only printing one membership
		ms_id = SqlSelect.getMsidFromMembershipID(Integer.parseInt(current_membership_id));
		membership = SqlSelect.getMembership(ms_id);
		primary = SqlSelect.getPerson(ms_id, 1);
		document.add(titlePdfTable(font));
		document.add(membershipIdPdfTable());
		document.add(membershipAddressPdfTable());
		document.add(personPdfTable());
		document.add(childrenPdfTable());
		document.add(boatsPdfTable());
		document.add(feesPdfTable());
		for(int i = 1; i < 6; i++) {
		document.add(blankTableRow(i));
		}
		document.add(signatureTable());
		}
		
		
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
	
	public Table feesPdfTable() {
		Table mainTable = new Table(12);
		//mainTable.setWidth(590);
		Cell cell;
		Paragraph p;
		//////////////////HEADERS//////////////////////
		cell = new Cell(1,4);
		//cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setBorderTop(new SolidBorder(borderSize));
		cell.setWidth(180);
		p = new Paragraph("Membership Type");
		p.setFontSize(10);
		p.setFixedLeading(10);  // sets spacing between lines of text
		cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		mainTable.addCell(cell);
		
		cell = new Cell(1,4);
		//cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(borderSize));
		cell.setWidth(180);
		p = new Paragraph("Storage and Access");
		p.setFontSize(10);
		p.setFixedLeading(10);
		cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		mainTable.addCell(cell);
		
		cell = new Cell(1,4);
		//cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(borderSize));
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.setWidth(215);
		p = new Paragraph("Keys");
		p.setFontSize(10);
		p.setFixedLeading(10);
		cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		mainTable.addCell(cell);
		feesTableRow1(mainTable);
		feesTableRow2(mainTable);
		feesTableRow3(mainTable);
		feesTableRow4(mainTable);
		feesTableRow5(mainTable);
		feesTableRow6(mainTable);
		feesTableRow7(mainTable);
		feesTableRow8(mainTable);
		feesTableRow9(mainTable);
		feesTableRow10(mainTable);
		feesTableRow11(mainTable);
		return mainTable;
	}
	
	public void feesTableRow1(Table mainTable) {
		Cell cell;
		Paragraph p;
		/////////////////// VISIBLE TABLE CELL LEFT BEGIN Row 1 /////////////////
		cell = new Cell();   
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(10);
		cell.add(uncheckedBox);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("Regular Membership");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		mainTable.addCell(cell);

		mainTable.addCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(0.5f));
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("$" + definedFees.getDues_regular());
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
		
		/////////////////// VISIBLE TABLE CELL CENTER BEGIN /////////////////
		cell = new Cell();   
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		//cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(10);
		cell.add(uncheckedBox);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("Wet Slip");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		mainTable.addCell(cell);

		mainTable.addCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(0.5f));
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("$" + definedFees.getWet_slip());
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
		
		/////////////////// VISIBLE TABLE CELL RIGHT BEGIN /////////////////
		cell = new Cell();   
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		//cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(10);
		cell.add(uncheckedBox);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("Main Gate Extra Key (#16)");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		mainTable.addCell(cell);

		
		/////////  Multiply Cell //////
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("____ x");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("$" + definedFees.getMain_gate_key());
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
	}
	
	public void feesTableRow2(Table mainTable) {
		Cell cell;
		Paragraph p;
		/////////////////// VISIBLE TABLE CELL LEFT BEGIN Row 2 /////////////////
		cell = new Cell();   
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(10);
		cell.add(uncheckedBox);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("Family Membership");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		mainTable.addCell(cell);

		mainTable.addCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(0.5f));
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("$" + definedFees.getDues_family());
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
		
		/////////////////// VISIBLE TABLE CELL CENTER BEGIN /////////////////
		cell = new Cell();   
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		//cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(10);
		cell.add(uncheckedBox);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("Beach Parking");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		mainTable.addCell(cell);

		mainTable.addCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(0.5f));
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("$" + definedFees.getBeach());
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
		
		/////////////////// VISIBLE TABLE CELL RIGHT BEGIN /////////////////
		cell = new Cell();   
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		//cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(10);
		cell.add(uncheckedBox);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("Sail Loft Key (SL16)");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		mainTable.addCell(cell);

		
		/////////  Multiply Cell //////
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("____ x");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("$" + definedFees.getSail_loft_key());
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
	}
	
	public void feesTableRow3(Table mainTable) {
		Cell cell;
		Paragraph p;
		/////////////////// VISIBLE TABLE CELL LEFT BEGIN Row 3 /////////////////
		cell = new Cell();   
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(10);
		cell.add(uncheckedBox);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("Lake Associate");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		mainTable.addCell(cell);

		mainTable.addCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(0.5f));
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("$" + definedFees.getDues_lake_associate());
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
		
		/////////////////// VISIBLE TABLE CELL CENTER BEGIN /////////////////
		cell = new Cell();   
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		//cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(10);
		cell.add(uncheckedBox);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("Winter Storage");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		mainTable.addCell(cell);

		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("____ x");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(0.5f));
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("$" + definedFees.getWinter_storage());
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
		
		/////////////////// VISIBLE TABLE CELL RIGHT BEGIN /////////////////
		cell = new Cell();   
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		//cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(10);
		cell.add(uncheckedBox);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("Sail School Loft Key (SS16)");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		mainTable.addCell(cell);

		
		/////////  Multiply Cell //////
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("____ x");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("$" + definedFees.getSail_school_loft_key());
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
	}
	
	public void feesTableRow4(Table mainTable) {
		Cell cell;
		Paragraph p;
		/////////////////// VISIBLE TABLE CELL LEFT BEGIN Row 4 /////////////////
		cell = new Cell();   
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(10);
		cell.add(uncheckedBox);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("Social Membership");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		mainTable.addCell(cell);

		mainTable.addCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(0.5f));
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("$" + definedFees.getDues_social());
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
		
		/////////////////// VISIBLE TABLE CELL CENTER BEGIN /////////////////
		cell = new Cell();   
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		//cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(10);
		cell.add(uncheckedBox);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("Sail Loft (sail storage)");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		mainTable.addCell(cell);

		mainTable.addCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(0.5f));
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("$" + definedFees.getSail_loft());
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
		
		/////////////////// VISIBLE TABLE CELL RIGHT BEGIN /////////////////
		cell = new Cell();   
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		//cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(10);
		cell.add(uncheckedBox);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("Kayak Shed Key (SL16)");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		mainTable.addCell(cell);

		
		/////////  Multiply Cell //////
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("____ x");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("$" + definedFees.getKayak_shed_key());
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
	}

	public void feesTableRow5(Table mainTable) {
		Cell cell;
		Paragraph p;
		/////////////////// VISIBLE TABLE CELL LEFT BEGIN Row 5 /////////////////

		cell = new Cell(1, 4);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		p = new Paragraph("Lake Associate");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		mainTable.addCell(cell);

		/////////////////// VISIBLE TABLE CELL CENTER BEGIN  /////////////////
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setBorderLeft(new SolidBorder(0.5f));
		cell.setWidth(10);
		cell.add(uncheckedBox);
		mainTable.addCell(cell);

		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("Outside Kayak Racks");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		mainTable.addCell(cell);

		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("____ x");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);

		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(0.5f));
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("$" + definedFees.getKayak_rack());
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);

		/////////////////// VISIBLE TABLE CELL RIGHT BEGIN /////////////////

		cell = new Cell(1, 4);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setBorderRight(new SolidBorder(borderSize));
		//cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		cell.setBackgroundColor(new DeviceCmyk(.5f, .24f, 0, 0.02f));
		p = new Paragraph("Total Dues and Fees");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		mainTable.addCell(cell);
	}
	
	public void feesTableRow6(Table mainTable) {
		Cell cell;
		Paragraph p;
		/////////////////// VISIBLE TABLE CELL LEFT BEGIN Row 6 /////////////////
		cell = new Cell();   
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(10);
		cell.add(uncheckedBox);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("Deferred Initiation");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		mainTable.addCell(cell);

		mainTable.addCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(0.5f));
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("$______");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
		
		/////////////////// VISIBLE TABLE CELL CENTER BEGIN /////////////////
		cell = new Cell();   
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		//cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(10);
		cell.add(uncheckedBox);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("Inside Kayak Storage");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		mainTable.addCell(cell);

		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("____ x");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(0.5f));
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("$" + definedFees.getKayak_shed());
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
		
		/////////////////// VISIBLE TABLE CELL RIGHT BEGIN /////////////////

		
		cell = new Cell(1,4);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setBorderRight(new SolidBorder(borderSize));
		Table innerTable = new Table(2);
		innerTable.addCell(new Cell()
				.setWidth(110)
				.setPadding(0)
				.setBorder(Border.NO_BORDER)
				.add(new Paragraph("Calculated: $900")
						.setFontSize(10)
						.setFixedLeading(10)
						.setTextAlignment(TextAlignment.LEFT)));
		innerTable.addCell(new Cell()
				.setPadding(0)
				.setBorder(Border.NO_BORDER)
				.add(new Paragraph("Actual:")
						.setFontSize(10)
						.setFixedLeading(10)
						.setTextAlignment(TextAlignment.LEFT)));
		cell.add(innerTable);
		mainTable.addCell(cell);
	}
	
	public void feesTableRow7(Table mainTable) {
		Cell cell;
		Paragraph p;
		/////////////////// VISIBLE TABLE CELL LEFT BEGIN Row 7 /////////////////
		cell = new Cell();   
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(10);
		cell.add(uncheckedBox);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("Youth Sailing Donation");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		mainTable.addCell(cell);

		mainTable.addCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(0.5f));
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("$______");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
		
		/////////////////// VISIBLE TABLE CELL CENTER BEGIN /////////////////
		cell = new Cell();   
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		//cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setWidth(10);
		cell.add(uncheckedBox);
		mainTable.addCell(cell);
		
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("Sailing School Loft Access");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		mainTable.addCell(cell);


		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(0.5f));
		cell.setBorderBottom(new SolidBorder(0.5f));
		p = new Paragraph("$" + definedFees.getSail_school_laser_loft());
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell.add(p);
		mainTable.addCell(cell);
		
		/////////////////// VISIBLE TABLE CELL RIGHT BEGIN /////////////////

		
		cell = new Cell(1,4);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setBorderRight(new SolidBorder(borderSize));
		//cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		cell.setBackgroundColor(new DeviceCmyk(.5f, .24f, 0, 0.02f));
		p = new Paragraph("Work Credits Earned (subtract)");
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		mainTable.addCell(cell);
	}
	
	public void feesTableRow8(Table mainTable) {
		Cell cell;
		Paragraph p;
		/////////////////// VISIBLE TABLE CELL LEFT BEGIN Row 8 /////////////////
		cell = new Cell(1,8);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setBorderRight(new SolidBorder(0.5f));
		cell.setBorderLeft(new SolidBorder(borderSize));
		p = new Paragraph("Check if you wish to be on a wait list: ____ Slip ____ Kayak ____Inside Kayak Storage");
		p.setFontSize(9);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		mainTable.addCell(cell);

		
		/////////////////// VISIBLE TABLE CELL RIGHT BEGIN /////////////////

		
		cell = new Cell(1,4);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setBorderRight(new SolidBorder(borderSize));
		Table innerTable = new Table(2);
		innerTable.addCell(new Cell()
				.setWidth(110)
				.setPadding(0)
				.setBorder(Border.NO_BORDER)
				.add(new Paragraph("Calculated: -$150")
						.setFontSize(10)
						.setFixedLeading(10)
						.setTextAlignment(TextAlignment.LEFT)));
		innerTable.addCell(new Cell()
				.setPadding(0)
				.setBorder(Border.NO_BORDER)
				.add(new Paragraph("Actual:")
						.setFontSize(10)
						.setFixedLeading(10)
						.setTextAlignment(TextAlignment.LEFT)));
		cell.add(innerTable);
		mainTable.addCell(cell);
	}
	
	public void feesTableRow9(Table mainTable) {
		Cell cell;
		Paragraph p;
		/////////////////// VISIBLE TABLE CELL LEFT BEGIN Row 8 /////////////////
		cell = new Cell(1,8);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setBorderRight(new SolidBorder(0.5f));
		cell.setBorderLeft(new SolidBorder(borderSize));
		p = new Paragraph("Work Credits are $10/credit.  Max: $150 (Approved by committee heads) ");
				// TODO add credits to defined fee
		p.setBold();
		p.setFontSize(9);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		mainTable.addCell(cell);

		
		/////////////////// VISIBLE TABLE CELL RIGHT BEGIN /////////////////


		mainTable.addCell(new Cell(1,4)
				.setBorder(Border.NO_BORDER)
				.setBorderRight(new SolidBorder(borderSize))
				.setBorderBottom(new SolidBorder(0.5f))
				.setBackgroundColor(new DeviceCmyk(.5f, .24f, 0, 0.02f))
				.add(new Paragraph("Total Amount of Remittance")
						.setFontSize(10)
						.setTextAlignment(TextAlignment.CENTER))
				);

	}
	
	public void feesTableRow10(Table mainTable) {
		Cell cell;
		Paragraph p;
		/////////////////// VISIBLE TABLE CELL LEFT BEGIN Row 10 /////////////////
		cell = new Cell(1,8);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setBorderRight(new SolidBorder(0.5f));
		cell.setBorderLeft(new SolidBorder(borderSize));
		p = new Paragraph("Please make checks payable to: \"Eagle Creek Sailing Club\" or \"ECSC\"");
				// TODO add credits to defined fee
		p.setBold();
		p.setFontSize(10);
		//p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		mainTable.addCell(cell);

		
		/////////////////// VISIBLE TABLE CELL RIGHT BEGIN /////////////////


		cell = new Cell(1,4);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setBorderRight(new SolidBorder(borderSize));
		Table innerTable = new Table(2);
		innerTable.addCell(new Cell()
				.setWidth(110)
				.setPadding(0)
				.setBorder(Border.NO_BORDER)
				.add(new Paragraph("Calculated: $950")
						.setFontSize(10)
						//.setFixedLeading(10)
						.setTextAlignment(TextAlignment.LEFT)));
		innerTable.addCell(new Cell()
				.setPadding(0)
				.setBorder(Border.NO_BORDER)
				.add(new Paragraph("Actual:")
						.setFontSize(10)
						//.setFixedLeading(10)
						.setTextAlignment(TextAlignment.LEFT)));
		cell.add(innerTable);
		mainTable.addCell(cell);

	}
	
	public void feesTableRow11(Table mainTable) {
		Cell cell;
		Paragraph p;
		/////////////////// VISIBLE TABLE CELL LEFT BEGIN Row 10 /////////////////
		cell = new Cell(1,8);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setBorderRight(new SolidBorder(0.5f));
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		p = new Paragraph("Insert corrections below if not enough room above");
				// TODO add credits to defined fee
		p.setBold();
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		mainTable.addCell(cell);

		
		/////////////////// VISIBLE TABLE CELL RIGHT BEGIN /////////////////


		cell = new Cell(1,4);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setBorderRight(new SolidBorder(borderSize));
		p = new Paragraph("Insufficient Check Funds fee: $50");
		// TODO add credits to defined fee
		p.setBold();
		p.setItalic();
		p.setFontSize(10);
		p.setFixedLeading(10);
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		mainTable.addCell(cell);

	}
	
	public Table blankTableRow(int number) {
		Table mainTable = new Table(1);
		mainTable
		.setWidth(590)
		.addCell(new Cell()
				.setBorder(Border.NO_BORDER)
				.setBorderLeft(new SolidBorder(borderSize))
				.setBorderRight(new SolidBorder(borderSize))
				.setBorderBottom(new SolidBorder(0.5f))
				.add(new Paragraph(number + ")")
						.setFontSize(10))
				);
		return mainTable;
	}
	
	public Table signatureTable() {
		Table mainTable = new Table(1);
		mainTable
		.setWidth(590)
		.addCell(new Cell()
				.setBorderLeft(new SolidBorder(borderSize))
				.setBorderRight(new SolidBorder(borderSize))
				.setBorderTop(new SolidBorder(borderSize))
				.add(new Paragraph("I understand and agree to the terms set fourth in this "
						+ "contract(front and back sides) and I agree to "
						+ "abide by the ECSC Bylaws & General Rules.")
						.setFontSize(8)
						.setBold()
						.setTextAlignment(TextAlignment.CENTER)
						.setFixedLeading(10)))
		.addCell(new Cell()
				.setBorderLeft(new SolidBorder(borderSize))
				.setBorderRight(new SolidBorder(borderSize))
				.add(new Paragraph("SIGNATURE: " + printLine(50) + " Date: " + printLine(20))
						.setFontSize(10)
						.setTextAlignment(TextAlignment.CENTER)
						.setFixedLeading(20))
				
				);
		
		return mainTable;
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
		cell.add(new Paragraph("If boat is co-owned, put a check next to the boat and list co-owner(s) " + printLine(50)).setFontSize(10));
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
			p.setTextAlignment(TextAlignment.CENTER);
			p.setFixedLeading(10);  // sets spacing between lines of text
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
			p.setTextAlignment(TextAlignment.CENTER);
			p.setFixedLeading(10);  // sets spacing between lines of text
		}
		cell.add(p);
		mainTable.addCell(cell);

		p = new Paragraph(boat.getManufacturer());
		p.setFontSize(10);
		cell = new Cell();
		if (isHeader) {
			cell.setBorderTop(new SolidBorder(borderSize));
			cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
			p.setTextAlignment(TextAlignment.CENTER);
			p.setFixedLeading(10);  // sets spacing between lines of text
		}
		cell.add(p);
		mainTable.addCell(cell);

		p = new Paragraph(boat.getManufacture_year());
		p.setFontSize(10);
		cell = new Cell();
		if (isHeader) {
			cell.setBorderTop(new SolidBorder(borderSize));
			cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
			p.setTextAlignment(TextAlignment.CENTER);
			p.setFixedLeading(10);  // sets spacing between lines of text
		}
		cell.add(p);
		mainTable.addCell(cell);

		p = new Paragraph(boat.getModel());
		p.setFontSize(10);
		cell = new Cell();
		if (isHeader) {
			cell.setBorderTop(new SolidBorder(borderSize));
			cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
			p.setTextAlignment(TextAlignment.CENTER);
			p.setFixedLeading(10);  // sets spacing between lines of text
		}
		cell.add(p);
		mainTable.addCell(cell);

		p = new Paragraph(boat.getLength());
		p.setFontSize(10);
		cell = new Cell();
		if (isHeader) {
			cell.setBorderTop(new SolidBorder(borderSize));
			cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
			p.setTextAlignment(TextAlignment.CENTER);
			p.setFixedLeading(10);  // sets spacing between lines of text
		}
		cell.add(p);
		mainTable.addCell(cell);

		p = new Paragraph(boat.getKeel());
		p.setFontSize(10);
		cell = new Cell();
		if (isHeader) {
			cell.setBorderTop(new SolidBorder(borderSize));
			cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
			p.setTextAlignment(TextAlignment.CENTER);
			p.setFixedLeading(10);  // sets spacing between lines of text
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
			p.setTextAlignment(TextAlignment.CENTER);
			p.setFixedLeading(10);  // sets spacing between lines of text
		}
		cell.add(p);
		mainTable.addCell(cell);

		p = new Paragraph(boat.getSail_number());
		p.setFontSize(10);
		cell = new Cell();
		if (isHeader) {
			cell.setBorderTop(new SolidBorder(borderSize));
			cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
			p.setTextAlignment(TextAlignment.CENTER);
			p.setFixedLeading(10);  // sets spacing between lines of text
		}
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.add(p);
		mainTable.addCell(cell);
	}
	
	public Table childrenPdfTable() {
		Table mainTable = new Table(2);
		Cell cell;
		Paragraph p;
		
		p = new Paragraph("Dependants under age of 21 (In order of oldest first)");
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
		cell.setBorderBottom(new SolidBorder(0.5f));
		mainTable.addCell(cell);
		
		p = new Paragraph("Spouse/Partner Name: Martha. Hunklemont");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setWidth(290);
		cell.add(p);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.setBorderTop(new SolidBorder(borderSize));
		cell.setBorderBottom(new SolidBorder(0.5f));
		mainTable.addCell(cell);
		
		p = new Paragraph("Email:");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Email:");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Cell Phone: ");
		p.setFontSize(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Birth Year: ");
		p.setFontSize(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Cell Phone: ");
		p.setFontSize(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Birth Year: ");
		p.setFontSize(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Occupation: ");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setWidth(290);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Occupation: ");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setWidth(290);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Company/Business: ");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setWidth(290);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Company/Business: ");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setWidth(290);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Company/Business Phone:");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setWidth(290);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Company/Business Phone:");
		p.setFontSize(10);
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.setBorderBottom(new SolidBorder(0.5f));
		cell.setWidth(290);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Circle any email addresses or phone numbers that you do not want listed in the membership directory");
		p.setFontSize(8).setBold();
		p.setTextAlignment(TextAlignment.CENTER);
		p.setFixedLeading(10);
		cell = new Cell(1,4);
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.add(p);
		mainTable.addCell(cell);
		
		return mainTable;
	}
	

	
	public Table membershipIdPdfTable()  { // 580 total cell width
		
		Table mainTable = new Table(2);
		Cell cell;
		Paragraph p;
		
		p = new Paragraph("If information is missing please add it. If incorrect plase line out and write the correct information next to it, or on the provided space at the bottom of the form");
		p.setFontSize(8);
        p.setTextAlignment(TextAlignment.CENTER);
        p.setFixedLeading(6);
		cell = new Cell(1,5);
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph(Integer.parseInt(year) -1 + " Membership Number: " + current_membership_id);
		p.setFontSize(10);
		cell = new Cell(1,1);
		cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(borderSize));
		cell.setBorderLeft(new SolidBorder(borderSize));
		cell.add(p);
		cell.setWidth(180);
		mainTable.addCell(cell);
		
		p = new Paragraph(Integer.parseInt(year) + " Membership Number: 98");
		p.setFontSize(10);
		p.setTextAlignment(TextAlignment.RIGHT);
		cell = new Cell(1,4);
		cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(borderSize));
		cell.setBorderRight(new SolidBorder(borderSize));
		cell.add(p);
		cell.setWidth(400);
		mainTable.addCell(cell);
		return mainTable;
	}
	
	public Table membershipAddressPdfTable()  { // 580 total cell width
		Table mainTable = new Table(5);
		mainTable.setWidth(590);
		Cell cell;
		Paragraph p;
		p = new Paragraph("Address: " + membership.getAddress());
		p.setFontSize(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderLeft(new SolidBorder(borderSize));
		//cell.setWidth(180);
		cell.add(p);

		mainTable.addCell(cell);
		
		p = new Paragraph("City: " + membership.getCity());
		p.setFontSize(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);

		mainTable.addCell(cell);
		
		p = new Paragraph("State: " + membership.getState());
		p.setFontSize(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);

		cell.add(p);

		mainTable.addCell(cell);
		
		p = new Paragraph("Zip: " + membership.getZip());
		p.setFontSize(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);

		mainTable.addCell(cell);
		
		p = new Paragraph("Emergency #: 123-456-7894");
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
	
	public String printLine(int length) {
		String line = "";
		for(int i =0; i < length; i++) {
			line = "_" + line;
		}
		return line;
	}

}
