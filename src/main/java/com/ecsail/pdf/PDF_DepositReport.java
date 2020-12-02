package com.ecsail.pdf;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

import com.ecsail.main.SqlSelect;
import com.ecsail.structures.Object_DefinedFee;
import com.ecsail.structures.Object_Deposit;
import com.ecsail.structures.Object_DepositPDF;
import com.ecsail.structures.Object_DepositSummary;
import com.ecsail.structures.Object_PaidDues;
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
			"Initiation Fee"
		};
	
	public PDF_DepositReport(Object_Deposit cd,Object_DefinedFee cdf, Object_DepositPDF pdfOptions) {
		this.currentDeposit = cd;
		PDF_DepositReport.paidDuesForDeposit = SqlSelect.getPaidDues(currentDeposit);
		this.currentDefinedFee = cdf;
		this.totals = updateTotals();
		this.fiscalYear = currentDeposit.getFiscalYear();
		String dest = System.getProperty("user.home") + "/Deposit_Report_" + currentDeposit.getBatch() + "_" + currentDeposit.getFiscalYear() + ".pdf";

		////////////// CHOOSE SORT //////////////
		sortByMembershipId();  ///////  will add more sorts later
		
		//////////////  PREPARE PDF /////////////////////
		

		// Initialize PDF writer
		PdfWriter writer = null;

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
		
		if(pdfOptions.isSingleDeposit()) {  // are we only creating a report of a single deposit
			if(pdfOptions.isDetailedReportIncluded()) {
				document.add(headerPdfTable("Detailed Report of Deposits"));
			}
			
			
			if(pdfOptions.isSummaryReportIncluded()) {  // is the summary of the deposit report included
				document.add(headerPdfTable("Summary Report of Deposits"));
				document.add(mainPdfTable());
				
			}
			
			
		} else { // we are creating a report for the entire year
			if(pdfOptions.isDetailedReportIncluded()) {
				document.add(headerPdfTable("Detailed Report of Deposits"));
			}
			
			
			if(pdfOptions.isSummaryReportIncluded()) {  // is the summary of the deposit report included
				
				//document.add(mainPdfTable());
				int numberOfBatches = SqlSelect.getNumberOfDepositBatches(currentDeposit.getFiscalYear()) + 1;
				for(int i = 1; i < numberOfBatches; i++) {
					createSummaryTable(i, document);
				}
				
			}
		}

		document.setTopMargin(0);
		// Close document
		document.close();
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
	
	private void createSummaryTable(int batch, Document document) {
		currentDeposit.clear();
		paidDuesForDeposit.clear();
		currentDeposit = SqlSelect.getDeposit(fiscalYear, batch);
		//System.out.println(currentDeposit.toString());
		paidDuesForDeposit = SqlSelect.getPaidDues(currentDeposit);
		totals.clear();
		totals = updateTotals();
		//document.add(addBlankTable());
		document.add(headerPdfTable("Deposit Summary"));
		document.add(mainPdfTable());
		document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
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
				t.setWet_slip(currentDefinedFee.getWet_slip() + t.getWet_slip());
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
			numberOfRecordsCounted++;
		}
		int total = 0;
		total += t.getBeach();
		total -= t.getCredit();
		total += t.getDues();
		total += t.getGate_key();
		total += t.getInitiation();
		total += t.getKayac_rack();
		total += t.getKayac_shed();
		total += t.getKayac_shed_key();
		total += t.getOther();
		total += t.getSail_loft();
		total += t.getSail_loft_key();
		total += t.getSail_school_laser_loft();
		total += t.getSail_school_loft_key();
		total += t.getWet_slip();
		total += t.getWinter_storage();
		total += t.getYsc_donation();
		t.setTotal(total);
		t.setNumberOfRecords(numberOfRecordsCounted);
		return t;
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
	
	public Table mainPdfTable()  {
		Table mainTable = new Table(TDRHeaders.length);
		// mainTable.setKeepTogether(true);
		Cell cell;
		
		for (String str : TDRHeaders) {
			mainTable.addCell(new Cell().setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f))
					// .setWidth(12)
					.add(new Paragraph(str).setFontSize(10)));
		}
		
		mainTable.addCell(new Cell().setWidth(80).add(new Paragraph(currentDeposit.getDepositDate()).setFontSize(10)));
		mainTable.addCell(new Cell().setWidth(100).add(new Paragraph("" + currentDeposit.getBatch()).setFontSize(10)));
		mainTable.addCell(new Cell().setWidth(200));
		mainTable.addCell(new Cell().setWidth(70));
		mainTable.addCell(new Cell());
		if (totals.getDuesNumber() != 0) {
			addRow(mainTable, "Annual Dues" , totals.getDuesNumber(), totals.getDues());
		}
		if (totals.getWinter_storageNumber() != 0) {
			addRow(mainTable, "Winter Storage Fee" , totals.getWinter_storageNumber(), totals.getWinter_storage());
		}
		if (totals.getWet_slip() != 0) {
			addRow(mainTable, "Wet slip Fee" , totals.getWet_slipNumber(), totals.getWet_slip());
		}
		if (totals.getBeach() != 0) {
			addRow(mainTable, "Beach Spot Fee" , totals.getBeachNumber(), totals.getBeach());
		}
		if (totals.getKayac_rack() != 0) {
			addRow(mainTable, "Outside Kayak Rack Fee" , totals.getKayac_rackNumber(), totals.getKayac_rack());
		}
		if (totals.getKayac_shed() != 0) {
			addRow(mainTable, "Inside Kayak Storage Fee" , totals.getKayac_shedNumber(), totals.getKayac_shed());
		}
		if (totals.getSail_loft() != 0) {
			addRow(mainTable, "Sail Loft Access Fee" , totals.getSail_loftNumber(), totals.getSail_loft());
		}
		if (totals.getInitiation() != 0) {
			addRow(mainTable, "Initiation Fee" , totals.getInitiationNumber(), totals.getInitiation());
		}
		///////////////////  KEYS //////////////////////////////
		if (totals.getGate_key() != 0) {
			addRow(mainTable, "Extra Gate Key Fee" , totals.getGate_keyNumber(), totals.getGate_key());
		}
		if (totals.getSail_loft_key() != 0) {
			addRow(mainTable, "Sail Loft Key Fee" , totals.getSail_loft_keyNumber(), totals.getSail_loft_key());
		}
		if (totals.getSail_school_loft_key() != 0) {
			addRow(mainTable, "Sail School Loft Key Fee" , totals.getSail_school_loft_keyNumber(), totals.getSail_school_loft_key());
		}
		if (totals.getKayac_shed_key() != 0) {
			addRow(mainTable, "Kayak Shed Key Fee" , totals.getKayac_shed_keyNumber(), totals.getKayac_shed_key());
		}
		if (totals.getYsc_donation() != 0) {
			addRow(mainTable, "Youth Sailing Club Donation" , totals.getYsc_donationNumber(), totals.getYsc_donation());
		}
		if (totals.getCredit() != 0) {
			addRow(mainTable, "Credits" , totals.getCreditNumber(), totals.getCredit());
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
		cell.add(new Paragraph("$" + totals.getTotal())).setFontSize(10);
		mainTable.addCell(cell);
		
		return mainTable;
	}
	
	private void addRow(Table mainTable, String label, int total, int money) {
		mainTable.addCell(new Cell());
		mainTable.addCell(new Cell());
		mainTable.addCell(new Cell().add(new Paragraph(label)).setFontSize(10));
		mainTable.addCell(new Cell().add(new Paragraph(total + "")).setFontSize(10));
		mainTable.addCell(new Cell().add(new Paragraph("$" + money).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
	}
	
	private Table addBlankTable() {
		Table blankTable = new Table(1);
		return blankTable.addCell(new Cell().setBorder(Border.NO_BORDER).setWidth(500).setHeight(30).add(new Paragraph("")));
	}
	
	private static void RemoveBorder(Table table)
	{
	    for (IElement iElement : table.getChildren()) {
	        ((Cell)iElement).setBorder(Border.NO_BORDER);
	    }
	}
	
}
