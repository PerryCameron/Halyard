package com.ecsail.pdf;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;

import com.ecsail.main.Paths;
import com.ecsail.sql.SqlSelect;
import com.ecsail.structures.Object_DefinedFee;
import com.ecsail.structures.Object_Deposit;
import com.ecsail.structures.Object_DepositPDF;
import com.ecsail.structures.Object_DepositSummary;
import com.ecsail.structures.Object_PaidDues;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DoubleBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;


import javafx.collections.ObservableList;

public class PDF_DepositReport {
	
	private static ObservableList<Object_PaidDues> paidDuesForDeposit;  // these are the paid dues for a single deposit
	private Object_Deposit currentDeposit;
	private Object_DefinedFee currentDefinedFee;
	private Object_DepositSummary totals;
	String fiscalYear;  // save this because I clear current Deposit
	Boolean includeDollarSigns = false;

	static String[] TDRHeaders = {
			"Date",
			"Deposit Number",
			"Fee",
			"Records",
			"Amount"
		};
	
	static String[] Items = {
			"Dues",
			"Beach Fee",
			"Key Fee",
			"Slip Fee",
			"Kayac Rack Fee",
			"Kayac Inside Storage Fee",
			"Winter Storage",
			"Work Credits",
			"YSP Donation",
			"Initiation Fee",
			"Other"
		};
	
	public PDF_DepositReport(Object_Deposit cd,Object_DefinedFee cdf, Object_DepositPDF pdfOptions) {
		this.currentDeposit = cd;
		PDF_DepositReport.paidDuesForDeposit = SqlSelect.getPaidDues(currentDeposit);
		this.currentDefinedFee = cdf;
		this.totals = updateTotals();
		this.fiscalYear = currentDeposit.getFiscalYear();
		String dest = "";

		//Image ecscLogo = new Image(ImageDataFactory.create(toByteArray(getClass().getResourceAsStream("/EagleCreekLogoForPDF.png"))));
		//public static final String SAFETY_IMG = "/img/Safety.png";
		
		////////////// CHOOSE SORT //////////////
		sortByMembershipId();  ///////  will add more sorts later
		
		//////////////  PREPARE PDF /////////////////////
		

		// Initialize PDF writer
		PdfWriter writer = null;
		// Check to make sure directory exists and if not create it
		Paths.checkPath(Paths.DEPOSITREPORTPATH + "/" + currentDeposit.getFiscalYear());
		if (pdfOptions.isSingleDeposit()) { // are we only creating a report of a single deposit
			dest = Paths.DEPOSITREPORTPATH + "/" + currentDeposit.getFiscalYear() + "/Deposit_Report_" + currentDeposit.getBatch() + "_" + currentDeposit.getFiscalYear() + ".pdf";
		} else { // we are creating a report for the entire year
			dest = Paths.DEPOSITREPORTPATH + "/" + currentDeposit.getFiscalYear() + "/Deposit_Report_Fiscal_Year_" + currentDeposit.getFiscalYear() + ".pdf";
		}

		try {
			writer = new PdfWriter(dest);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Initialize PDF document
		PdfDocument pdf = new PdfDocument(writer);

		// Initialize document
		Document document = new Document(pdf);
		
//		document.add(addLogoTable(document, ecscLogo));
		
		if (pdfOptions.isSingleDeposit()) { // are we only creating a report of a single deposit
			createDepositTable(currentDeposit.getBatch(), document);
		} else { // we are creating a report for the entire year
			int numberOfBatches = SqlSelect.getNumberOfDepositBatches(currentDeposit.getFiscalYear()) + 1;
			for (int i = 1; i < numberOfBatches; i++) {
				createDepositTable(i, document);
				document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
			}
		}

		document.setTopMargin(0);
		// Close document
		document.close();
		System.out.println("destination=" + dest);
		File file = new File(dest);
		Desktop desktop = Desktop.getDesktop(); // Gui_Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()
		
		// Open the document
		try {
			desktop.open(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/////////////////////////////   CLASS METHODS  /////////////////////////////////////////
	
	
	private void createDepositTable(int batch, Document document) {
		currentDeposit.clear();
		paidDuesForDeposit.clear();
		SqlSelect.updateDeposit(fiscalYear, batch, currentDeposit);
		paidDuesForDeposit = SqlSelect.getPaidDues(currentDeposit);
		/////////// add the details pages ////////////
		document.add(titlePdfTable("Deposit Report"));
		document.add(detailPdfTable());
		/////////// add the summary page ////////////
		totals.clear();
		totals = updateTotals();
		document.add(headerPdfTable("Summary"));
		document.add(summaryPdfTable());
	}
	
	public Table titlePdfTable(String title) {
		Image ecscLogo = new Image(ImageDataFactory.create(toByteArray(getClass().getResourceAsStream("/EagleCreekLogoForPDF.png"))));
		Table mainTable = new Table(3);
		Cell cell;
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setWidth(200);
		cell.add(new Paragraph(title + " #" + currentDeposit.getBatch())).setFontSize(20);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setWidth(200);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setWidth(90);
		ecscLogo.setMarginLeft(30);
		ecscLogo.scale(0.4f, 0.4f);
		cell.add(ecscLogo);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(new Paragraph(currentDeposit.getDepositDate() + "")).setFontSize(10);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		mainTable.addCell(cell);
		return mainTable;
	}
	
	private Table detailPdfTable() {
		Table detailTable = new Table(5);
		// mainTable.setKeepTogether(true);
		Cell cell;
		
		for(Object_PaidDues dues: paidDuesForDeposit)  // each membership in Deposit
		{
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setWidth(50);
		cell.add(new Paragraph(dues.getMembershipId() + "")).setFontSize(10);
		detailTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setWidth(100);
		cell.add(new Paragraph(dues.getL_name() + "")).setFontSize(10);
		detailTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setWidth(200);
		//cell.add(new Paragraph(dues.getL_name() + "")).setFontSize(10);
		detailTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setWidth(40);
		//cell.add(new Paragraph(dues.getL_name() + "")).setFontSize(10);
		detailTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
		cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setWidth(100);
		//cell.add(new Paragraph(dues.getL_name() + "")).setFontSize(10);
		detailTable.addCell(cell);
		
		if(dues.getDues() != 0) addItemRow(detailTable, "Annual Dues", dues.getDues(),0);
		if(dues.getWinter_storage() != 0) addItemRow(detailTable, "Winter Storage Fee", dues.getWinter_storage() * currentDefinedFee.getWinter_storage(), dues.getWinter_storage());
		//// access ////
		if(dues.getWet_slip() != 0) addItemRow(detailTable, "Wet Slip Fee", dues.getWet_slip(),retrunWetSlipNumber(dues.getWet_slip()));
		if(dues.getBeach() != 0) addItemRow(detailTable, "Beach Spot Fee", dues.getBeach() * currentDefinedFee.getBeach(), dues.getBeach());
		if(dues.getKayac_rack() != 0) addItemRow(detailTable, "Kayak Rack Fee", dues.getKayac_rack() * currentDefinedFee.getKayak_rack(), dues.getKayac_rack());
		if(dues.getKayac_shed() != 0) addItemRow(detailTable, "Kayak Inside Storage Fee", dues.getKayac_shed() * currentDefinedFee.getKayak_shed(), dues.getKayac_shed());
		if(dues.getSail_loft() != 0) addItemRow(detailTable, "Sail Loft Access Fee", dues.getSail_loft()* currentDefinedFee.getSail_loft(), dues.getSail_loft());
		if(dues.getSail_school_laser_loft() != 0) addItemRow(detailTable, "Sail School Loft Acess", dues.getSail_school_laser_loft() * currentDefinedFee.getSail_school_laser_loft(), dues.getSail_school_laser_loft());

		//// keys ////
		if(dues.getExtra_key() != 0) addItemRow(detailTable, "Extra Gate Key Fee", dues.getExtra_key() * currentDefinedFee.getMain_gate_key(), dues.getExtra_key());
		if(dues.getSail_school_loft_key() != 0) addItemRow(detailTable, "Extra Sail School Loft Key Fee", dues.getSail_school_loft_key() * currentDefinedFee.getSail_school_loft_key(), dues.getSail_school_loft_key());
		if(dues.getKayac_shed_key() != 0) addItemRow(detailTable, "Extra Inside Storage Key", dues.getKayac_shed_key() * currentDefinedFee.getKayak_shed_key(), dues.getKayac_shed_key());
		if(dues.getSail_loft_key() != 0) addItemRow(detailTable, "Extra Sail Loft Key Fee", dues.getSail_loft_key()* currentDefinedFee.getSail_loft_key(), dues.getSail_loft_key());

		if(dues.getInitiation() != 0) addItemRow(detailTable, "Initiation Fee", dues.getInitiation(), 0);
		if(dues.getYsc_donation() != 0) addItemRow(detailTable, "Youth Sailing Club Donation", dues.getYsc_donation(), 0);
		if(dues.getOther() != 0) addItemRow(detailTable, "Other: " + getOtherNote(dues) , dues.getOther(),0);
		addItemRow(detailTable, "Total Fees", dues.getTotal(),0);
		if(dues.getCredit() != 0) addCreditRow(detailTable, "Credit", dues.getCredit(),0);
		addItemRow(detailTable, "Total Due", dues.getTotal() - dues.getCredit(),0);
		addItemPaidRow(detailTable, dues.getPaid());
		if(dues.getBalance() != 0) addBalanceRow(detailTable, "Balance", dues.getBalance(), 0);
		}
		
		return detailTable;
	}
	
	private String getOtherNote(Object_PaidDues dues) {
		return SqlSelect.getMemos(dues).getMemo();
	}
	
	int retrunWetSlipNumber(int numberOf) {
		int result = 0;
		if(numberOf != 0) result = 1;
		return result;
	}
	
	private void addItemPaidRow(Table detailTable, int money) {
		Cell cell;
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		detailTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		detailTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
		cell.add(new Paragraph("Amount Paid")).setFontSize(10);
		detailTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
		detailTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setTextAlignment(TextAlignment.RIGHT);
		cell.add(new Paragraph(addDollarSign() + String.format("%,d", money))).setFontSize(10);
		detailTable.addCell(cell);
	}
	
	private void addBalanceRow(Table detailTable, String label, int money, int numberOf) {
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph(label)).setFontSize(10));
		if(numberOf == 0) {
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));	
		} else {
			detailTable.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).add(new Paragraph("" + numberOf)).setFontSize(10));	
		}
		detailTable.addCell(new Cell().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).add(new Paragraph(addDollarSign() + String.format("%,d", money))).setFontSize(10));
	}
	
	private void addItemRow(Table detailTable, String label, int money, int numberOf) {
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph(label)).setFontSize(10));
		if(numberOf == 0) {
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));	
		} else {
			detailTable.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).add(new Paragraph("" + numberOf)).setFontSize(10));	
		}
		detailTable.addCell(new Cell().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).add(new Paragraph(addDollarSign() + String.format("%,d", money))).setFontSize(10));
	}
	
	private void addCreditRow(Table detailTable, String label, int money, int numberOf) {
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph(label)).setFontSize(10));
		if(numberOf == 0) {
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));	
		} else {
			detailTable.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).add(new Paragraph("" + numberOf)).setFontSize(10));	
		}
		detailTable.addCell(new Cell().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).add(new Paragraph("-" + addDollarSign() + String.format("%,d", money))).setFontSize(10));
	}
	
	public Table summaryPdfTable()  {
		Table mainTable = new Table(TDRHeaders.length);
		// mainTable.setKeepTogether(true);
		Cell cell;

		
		for (String str : TDRHeaders) {
			mainTable.addCell(new Cell().setBackgroundColor(new DeviceCmyk(.5f, .24f, 0, 0.02f))
					// .setWidth(12)
					.add(new Paragraph(str).setFontSize(10)));
		}
		
		mainTable.addCell(new Cell().setWidth(80).add(new Paragraph(currentDeposit.getDepositDate()).setFontSize(10)));
		mainTable.addCell(new Cell().setWidth(100).add(new Paragraph("" + currentDeposit.getBatch()).setFontSize(10)));
		mainTable.addCell(new Cell().setWidth(200));
		mainTable.addCell(new Cell().setWidth(70));
		mainTable.addCell(new Cell().setWidth(40));
		if (totals.getDuesNumber() != 0) {
			addSummaryRow(mainTable, "Annual Dues" , totals.getDuesNumber(), totals.getDues());
		}
		if (totals.getWinter_storageNumber() != 0) {
			addSummaryRow(mainTable, "Winter Storage Fee" , totals.getWinter_storageNumber(), totals.getWinter_storage());
		}
		if (totals.getWet_slip() != 0) {
			addSummaryRow(mainTable, "Wet slip Fee" , totals.getWet_slipNumber(), totals.getWet_slip());
		}
		if (totals.getBeach() != 0) {
			addSummaryRow(mainTable, "Beach Spot Fee" , totals.getBeachNumber(), totals.getBeach());
		}
		if (totals.getKayac_rack() != 0) {
			addSummaryRow(mainTable, "Outside Kayak Rack Fee" , totals.getKayac_rackNumber(), totals.getKayac_rack());
		}
		if (totals.getKayac_shed() != 0) {
			addSummaryRow(mainTable, "Inside Kayak Storage Fee" , totals.getKayac_shedNumber(), totals.getKayac_shed());
		}
		if (totals.getSail_loft() != 0) {
			addSummaryRow(mainTable, "Sail Loft Access Fee" , totals.getSail_loftNumber(), totals.getSail_loft());
		}
		if (totals.getInitiation() != 0) {
			addSummaryRow(mainTable, "Initiation Fee" , totals.getInitiationNumber(), totals.getInitiation());
		}
		///////////////////  KEYS //////////////////////////////
		if (totals.getGate_key() != 0) {
			addSummaryRow(mainTable, "Extra Gate Key Fee" , totals.getGate_keyNumber(), totals.getGate_key());
		}
		if (totals.getSail_loft_key() != 0) {
			addSummaryRow(mainTable, "Sail Loft Key Fee" , totals.getSail_loft_keyNumber(), totals.getSail_loft_key());
		}
		if (totals.getSail_school_loft_key() != 0) {
			addSummaryRow(mainTable, "Sail School Loft Key Fee" , totals.getSail_school_loft_keyNumber(), totals.getSail_school_loft_key());
		}
		if (totals.getKayac_shed_key() != 0) {
			addSummaryRow(mainTable, "Kayak Shed Key Fee" , totals.getKayac_shed_keyNumber(), totals.getKayac_shed_key());
		}
		if (totals.getYsc_donation() != 0) {
			addSummaryRow(mainTable, "Youth Sailing Club Donation" , totals.getYsc_donationNumber(), totals.getYsc_donation());
		}
		if (totals.getOther() != 0) {
			addSummaryRow(mainTable, "Other" , totals.getOtherNumber(), totals.getOther());
		}
		if (totals.getCredit() != 0) {
			addSummaryRow(mainTable, "Credits" , totals.getCreditNumber(), totals.getCredit());
		}
		
		
		RemoveBorder(mainTable);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setBorderBottom(new DoubleBorder(ColorConstants.BLACK, 2));
		if(totals.getNumberOfRecords() == 1) {
			cell.add(new Paragraph("1 Record").setFontSize(10));
		} else {
			cell.add(new Paragraph(totals.getNumberOfRecords() + " Records").setFontSize(10));
		}
		mainTable.addCell(cell);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setBorderBottom(new DoubleBorder(ColorConstants.BLACK, 2));
		mainTable.addCell(cell);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setBorderBottom(new DoubleBorder(ColorConstants.BLACK, 2));
		cell.add(new Paragraph("Total")).setFontSize(10);
		mainTable.addCell(cell);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setBorderBottom(new DoubleBorder(ColorConstants.BLACK, 2));
		mainTable.addCell(cell);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setBorderBottom(new DoubleBorder(ColorConstants.BLACK, 2));
		cell.setTextAlignment(TextAlignment.RIGHT);
		cell.add(new Paragraph(addDollarSign() + String.format("%,d", totals.getTotal()))).setFontSize(10);
		mainTable.addCell(cell);
		
		return mainTable;
	}
	
	private Object_DepositSummary updateTotals() {
		int numberOfRecordsCounted = 0; // number of records counted
		Object_DepositSummary t = new Object_DepositSummary();
		for (Object_PaidDues d : paidDuesForDeposit) {
			if (d.getBeach() != 0) { ///////// BEACH
				t.setBeachNumber(d.getBeach() + t.getBeachNumber());
				int totalBeachDollars = currentDefinedFee.getBeach() * d.getBeach();
				t.setBeach(totalBeachDollars + t.getBeach());
			}
			if (d.getCredit() != 0) {  ////////  CREDIT
				t.setCreditNumber(1 + t.getCreditNumber());
				t.setCredit(d.getCredit() + t.getCredit());
			}
			if (d.getDues() != 0) {  ////////  DUES
				t.setDuesNumber(1 + t.getDuesNumber());
				t.setDues(d.getDues() + t.getDues());
			}
			if (d.getExtra_key() != 0) { /////  EXTRA GATE KEY
				t.setGate_keyNumber(d.getExtra_key() + t.getGate_keyNumber());
				int totalGateKeyDollars = currentDefinedFee.getMain_gate_key() * d.getExtra_key();
				t.setGate_key(t.getGate_key() + totalGateKeyDollars);
			}
			if (d.getInitiation() != 0) {  /////// INITIATION
				t.setInitiationNumber(1 + t.getInitiationNumber());
				t.setInitiation(d.getInitiation() + t.getInitiation());
			}
			if (d.getKayac_rack() != 0) {  ///// KAYACK RACK FEE
				t.setKayac_rackNumber(d.getKayac_rack() + t.getKayac_rackNumber());
				int totalKayakRackDollars = currentDefinedFee.getKayak_rack() * d.getKayac_rack();
				t.setKayac_rack(totalKayakRackDollars + t.getKayac_rack());
			}
			if (d.getKayac_shed() != 0) {   //////// KAYAK SHED ACCESS
				t.setKayac_shedNumber(d.getKayac_shed() + t.getKayac_shed_keyNumber());
				int totalKayakShedDollars = currentDefinedFee.getKayak_shed() * d.getKayac_shed();
				t.setKayac_shed(totalKayakShedDollars + t.getKayac_shed());
			}
			if (d.getKayac_shed_key() != 0) {   ///// KAYAK SHED KEY
				t.setKayac_shed_keyNumber(d.getKayac_shed_key() + t.getKayac_shed_keyNumber());
				int totalKayakShedKeyDollars = currentDefinedFee.getKayak_shed_key() * d.getKayac_shed_key();
				t.setKayac_shed_key(totalKayakShedKeyDollars + t.getKayac_shed_key());
			}
			if (d.getOther() != 0) {  /////////  OTHER FEE ///////// IN DOLLARS
				t.setOtherNumber(1 + t.getOtherNumber());
				t.setOther(d.getOther() + t.getOther());
			}
			if (d.getSail_loft() != 0) {   ////////// SAIL LOFT ACCESS ///////// IN NUMBER OF
				t.setSail_loftNumber(1 + t.getSail_loftNumber());
				t.setSail_loft(currentDefinedFee.getSail_loft() + t.getSail_loft());
			}
			if (d.getSail_loft_key() != 0) {  ///////// SAIL LOFT KEY ///////// IN NUMBER OF
				t.setSail_loft_keyNumber(d.getSail_loft_key() + t.getSail_loft_keyNumber());
				int totalSailLoftKeyDollars = currentDefinedFee.getSail_loft_key() * d.getSail_loft_key();
				t.setSail_loft_key(totalSailLoftKeyDollars + t.getSail_loft_key());
			}
			if (d.getSail_school_laser_loft() != 0) {  ///////// SAIL SCHOOL LOFT ACCESS ///////// IN NUMBER OF
				t.setSail_school_laser_loftNumber(d.getSail_school_laser_loft() + t.getSail_school_laser_loftNumber());
				int totalSailSchoolLoftDollars = currentDefinedFee.getSail_school_laser_loft() * d.getSail_school_laser_loft();
				t.setSail_school_laser_loft(totalSailSchoolLoftDollars + t.getSail_school_laser_loft());
			}
			if (d.getSail_school_loft_key() != 0) {  ////////// SAIL SCHOOL LOFT KEY ///////// IN NUMBER OF
				t.setSail_school_loft_keyNumber(d.getSail_school_loft_key() + t.getSail_school_loft_keyNumber());
				int totalSailSchoolLoftKeyDollars = currentDefinedFee.getSail_school_loft_key() * d.getSail_school_loft_key();
				t.setSail_school_loft_key(totalSailSchoolLoftKeyDollars + t.getSail_school_loft_key());
			}
			if (d.getWet_slip() != 0) {  ////////// WET SLIP FEE ///////// IN DOLLARS 
				t.setWet_slipNumber(1 + t.getWet_slipNumber());
				t.setWet_slip(d.getWet_slip() + t.getWet_slip());
			}
			if (d.getWinter_storage() != 0) {  ////////  WINTER STORAGE FEE ///////// IN NUMBER OF
				t.setWinter_storageNumber(d.getWinter_storage() + t.getWinter_storageNumber());
				int totalWinterStorageDollars = currentDefinedFee.getWinter_storage() * d.getWinter_storage();
				t.setWinter_storage(totalWinterStorageDollars + t.getWinter_storage());
			}
			if (d.getYsc_donation() != 0) {  //////// YSC DONATION ///////// IN DOLLARS
				t.setYsc_donationNumber(1 + t.getYsc_donationNumber());
				t.setYsc_donation(d.getYsc_donation() + t.getYsc_donation());
			}
			if (d.getPaid() != 0) {
				t.setPaid(d.getPaid() + t.getPaid());
			}
			numberOfRecordsCounted++;
		}

		t.setTotal(t.getPaid());  /// sets total to added up payments, kind of redundant but a bug fix and hurts nothing
		t.setNumberOfRecords(numberOfRecordsCounted);
		return t;
	}
	
	public String addDollarSign() {
		String sign = "";
		if(includeDollarSigns) sign="$";
		return sign;
	}
	
    public static void sortByMembershipId() {
		  Collections.sort(paidDuesForDeposit, new Comparator<Object_PaidDues>() {
		        @Override public int compare(Object_PaidDues p1, Object_PaidDues p2) {
		            return p1.getMembershipId() - p2.getMembershipId(); // Ascending
		        }

		    });
    }
    
	public Table headerPdfTable(String summary) {
		
		//Image image = new Image(ImageDataFactory.create(ECSC_IMG));
		//Image image = new Image(ImageDataFactory.create(ECSC_IMG));
		Table mainTable = new Table(1);
		Cell cell;
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(new Paragraph(summary)).setFontSize(20);
		mainTable.addCell(cell);		
		return mainTable;
	}
	
	private void addSummaryRow(Table mainTable, String label, int numberOf, int money) {
		mainTable.addCell(new Cell());
		mainTable.addCell(new Cell());
		mainTable.addCell(new Cell().add(new Paragraph(label)).setFontSize(10));
		mainTable.addCell(new Cell().add(new Paragraph(numberOf + "")).setFontSize(10));
		mainTable.addCell(new Cell().add(new Paragraph(addDollarSign() + String.format("%,d", money)).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
	}
	
	private static void RemoveBorder(Table table)
	{
	    for (IElement iElement : table.getChildren()) {
	        ((Cell)iElement).setBorder(Border.NO_BORDER);
	    }
	}
	
	public static byte[] toByteArray(InputStream in)  { // for taking inputStream and returning byte
																			// array
		// InputStream is = new BufferedInputStream(System.in);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		// read bytes from the input stream and store them in buffer
		try {
			while ((len = in.read(buffer)) != -1) {
				// write bytes from the buffer into output stream
				os.write(buffer, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return os.toByteArray();
	}
	
	
	
}
