package com.ecsail.pdf;

import com.ecsail.main.SqlSelect;
import com.ecsail.structures.Object_DefinedFee;
import com.ecsail.structures.Object_Deposit;
import com.ecsail.structures.Object_DepositSummary;
import com.ecsail.structures.Object_PaidDues;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.*;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import javafx.collections.ObservableList;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
	
public class Pdf_TreasurerReport {
		private static Object_Deposit currentDeposit;
		private static Object_DefinedFee currentDefinedFee;
		private static Object_DepositSummary totals;
		private static ObservableList<Object_PaidDues> paidDues;
		public static final String ECSC_IMG = "/EagleCreekLogoForPDF.png";
		// file:///C:/Users/pcame/git/ECSC/src/main/resources/EagleCreekLogoForPDF.png
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

	    public static int counter = 1;
		public Pdf_TreasurerReport(Object_Deposit cd, Object_DefinedFee cdf) throws IOException {
			Pdf_TreasurerReport.currentDeposit = cd;
			Pdf_TreasurerReport.paidDues = SqlSelect.getPaidDues(currentDeposit);
			Pdf_TreasurerReport.currentDefinedFee = cdf;
			Pdf_TreasurerReport.totals = updateTotals();
			
			String dest = System.getProperty("user.home") + "/Deposit_Report_" + currentDeposit.getBatch() + "_" + currentDeposit.getFiscalYear() + ".pdf";
			
			sortByMembershipId();
			
			
			// Initialize PDF writer
			PdfWriter writer = null;

			writer = new PdfWriter(dest);

			// Initialize PDF document
			PdfDocument pdf = new PdfDocument(writer);

			// Initialize document
			Document document = new Document(pdf);
			// PageSize ps = PageSize.A5;
			document.add(headerPdfTable());
			document.add(mainPdfTable());
			document.setTopMargin(0);
			// Close document
			document.close();
			File file = new File(dest);
			Desktop desktop = Desktop.getDesktop(); // Gui_Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()
			desktop.open(file);
		}

		public static Table headerPdfTable() throws IOException {
			
			//Image image = new Image(ImageDataFactory.create(ECSC_IMG));
			//Image image = new Image(ImageDataFactory.create(ECSC_IMG));
			Table mainTable = new Table(1);
			Cell cell;
			cell = new Cell();
			cell.setBorder(Border.NO_BORDER);
			//cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
			cell.add(new Paragraph("Summary Report of Deposits")).setFontSize(20);
			mainTable.addCell(cell);
			//cell = new Cell();
			//cell.setBorder(Border.NO_BORDER);
			//cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
			//cell.add(image);
			//mainTable.addCell(cell);
			
			return mainTable;
		}
		public static Table mainPdfTable() throws IOException {
			Table mainTable = new Table(TDRHeaders.length);
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
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell().add(new Paragraph("Annual Dues")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph(totals.getDuesNumber() + "")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph("$" + totals.getDues()).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
			}
			if (totals.getWinter_storageNumber() != 0) {
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell().add(new Paragraph("Winter Storage Fee")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph(totals.getWinter_storageNumber() + "")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph("$" + totals.getWinter_storage()).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
			}
			if (totals.getWet_slip() != 0) {
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell().add(new Paragraph("Wet slip Fee")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph(totals.getWet_slipNumber() + "")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph("$" + totals.getWet_slip()).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
			}
			if (totals.getBeach() != 0) {
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell().add(new Paragraph("Beach Spot Fee")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph(totals.getBeachNumber() + "")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph("$" + totals.getBeach()).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
			}
			if (totals.getKayac_rack() != 0) {
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell().add(new Paragraph("Outside Kayak Rack Fee")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph(totals.getKayac_rackNumber() + "")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph("$" + totals.getKayac_rack()).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
			}
			if (totals.getKayac_shed() != 0) {
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell().add(new Paragraph("Inside Kayak Storage Fee")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph(totals.getKayac_shedNumber() + "")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph("$" + totals.getKayac_shed()).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
			}
			if (totals.getSail_loft() != 0) {
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell().add(new Paragraph("Sail Loft Access Fee")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph(totals.getSail_loftNumber() + "")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph("$" + totals.getSail_loft()).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
			}
			if (totals.getInitiation() != 0) {
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell().add(new Paragraph("Initiation Fee")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph(totals.getInitiationNumber() + "")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph("$" + totals.getInitiation()).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
			}
			///////////////////  KEYS //////////////////////////////
			if (totals.getGate_key() != 0) {
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell().add(new Paragraph("Extra Gate Key Fee")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph(totals.getGate_keyNumber() + "")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph("$" + totals.getGate_key()).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
			}
			if (totals.getSail_loft_key() != 0) {
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell().add(new Paragraph("Sail Loft Key Fee")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph(totals.getSail_loft_keyNumber() + "")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph("$" + totals.getSail_loft_key()).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
			}
			if (totals.getSail_school_loft_key() != 0) {
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell().add(new Paragraph("Sail School Loft Key Fee")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph(totals.getSail_school_loft_keyNumber()+ "")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph("$" + totals.getSail_school_loft_key()).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
			}
			if (totals.getKayac_shed_key() != 0) {
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell().add(new Paragraph("Kayak Shed Key Fee")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph(totals.getKayac_shed_keyNumber() + "")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph("$" + totals.getKayac_shed_key()).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
			}
			if (totals.getYsc_donation() != 0) {
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell().add(new Paragraph("Youth Sailing Club Donation")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph(totals.getYsc_donationNumber() + "")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph("$" + totals.getYsc_donation()).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
			}
			if (totals.getCredit() != 0) {
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell());
				mainTable.addCell(new Cell().add(new Paragraph("Credits")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph(totals.getCreditNumber() + "")).setFontSize(10));
				mainTable.addCell(new Cell().add(new Paragraph("-$" + totals.getCredit()).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
			}
			
			
			RemoveBorder(mainTable);
			cell = new Cell();
			cell.setBorder(Border.NO_BORDER);
			cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
			if(totals.getNumberOfRecords() == 1) {
				cell.add(new Paragraph("1 Record").setFontSize(10));
			} else {
				cell.add(new Paragraph(totals.getNumberOfRecords() + " Records").setFontSize(10));
			}
			mainTable.addCell(cell);
			cell = new Cell();
			cell.setBorder(Border.NO_BORDER);
			cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
			mainTable.addCell(cell);
			cell = new Cell();
			cell.setBorder(Border.NO_BORDER);
			cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
			cell.add(new Paragraph("Total")).setFontSize(10);
			mainTable.addCell(cell);
			cell = new Cell();
			cell.setBorder(Border.NO_BORDER);
			cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
			mainTable.addCell(cell);
			cell = new Cell();
			cell.setBorder(Border.NO_BORDER);
			cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
			cell.setTextAlignment(TextAlignment.RIGHT);
			cell.add(new Paragraph("$" + totals.getTotal())).setFontSize(10);
			mainTable.addCell(cell);
			
			return mainTable;
		}
	    
		private Object_DepositSummary updateTotals() {
			int numberOfRecordsCounted = 0; // number of records counted
			Object_DepositSummary t = new Object_DepositSummary();
			for (Object_PaidDues d : paidDues) {
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
		
		private static void RemoveBorder(Table table)
		{
		    for (IElement iElement : table.getChildren()) {
		        ((Cell)iElement).setBorder(Border.NO_BORDER);;
		    }
		}
	    
	    public static void sortByMembershipId() {
			  Collections.sort(paidDues, new Comparator<Object_PaidDues>() {
			        @Override public int compare(Object_PaidDues p1, Object_PaidDues p2) {
			            return p1.getMembershipId() - p2.getMembershipId(); // Ascending
			        }

			    });
	    }
	    
	}



