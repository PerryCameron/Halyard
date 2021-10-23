package com.ecsail.pdf;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;

import com.ecsail.main.HalyardPaths;
import com.ecsail.sql.SqlExists;
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
	DecimalFormat df = new DecimalFormat("#,###.00");

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
		HalyardPaths.checkPath(HalyardPaths.DEPOSITREPORTPATH + "/" + currentDeposit.getFiscalYear());
		if (pdfOptions.isSingleDeposit()) { // are we only creating a report of a single deposit
			dest = HalyardPaths.DEPOSITREPORTPATH + "/" + currentDeposit.getFiscalYear() + "/Deposit_Report_" + currentDeposit.getBatch() + "_" + currentDeposit.getFiscalYear() + ".pdf";
		} else { // we are creating a report for the entire year
			dest = HalyardPaths.DEPOSITREPORTPATH + "/" + currentDeposit.getFiscalYear() + "/Deposit_Report_Fiscal_Year_" + currentDeposit.getFiscalYear() + ".pdf";
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

		BigDecimal annualDues = new BigDecimal(dues.getDues());
		if(annualDues.compareTo(BigDecimal.ZERO) != 0) addItemRow(detailTable, "Annual Dues", annualDues,0);
		if(dues.getWinter_storage() != 0) addItemRow(detailTable, "Winter Storage Fee", BigDecimal.valueOf(dues.getWinter_storage()).multiply(currentDefinedFee.getWinter_storage()), dues.getWinter_storage());
		//// access ////
			BigDecimal wetSlip = new BigDecimal(dues.getWet_slip());
		if(wetSlip.compareTo(BigDecimal.ZERO) != 0) addItemRow(detailTable, "Wet Slip Fee", wetSlip,returnWetSlipNumber(wetSlip));
		if(dues.getBeach() != 0) addItemRow(detailTable, "Beach Spot Fee", BigDecimal.valueOf(dues.getBeach()).multiply(currentDefinedFee.getBeach()), dues.getBeach());
		if(dues.getKayac_rack() != 0) addItemRow(detailTable, "Kayak Rack Fee", BigDecimal.valueOf(dues.getKayac_rack()).multiply(currentDefinedFee.getKayak_rack()), dues.getKayac_rack());
		if(dues.getKayac_shed() != 0) addItemRow(detailTable, "Kayak Inside Storage Fee", BigDecimal.valueOf(dues.getKayac_shed()).multiply(currentDefinedFee.getKayak_shed()), dues.getKayac_shed());
		if(dues.getSail_loft() != 0) addItemRow(detailTable, "Sail Loft Access Fee", BigDecimal.valueOf(dues.getSail_loft()).multiply(currentDefinedFee.getSail_loft()), dues.getSail_loft());
		if(dues.getSail_school_laser_loft() != 0) addItemRow(detailTable, "Sail School Loft Acess", BigDecimal.valueOf(dues.getSail_school_laser_loft()).multiply(currentDefinedFee.getSail_school_laser_loft()), dues.getSail_school_laser_loft());

		//// keys ////
		if(dues.getExtra_key() != 0) addItemRow(detailTable, "Extra Gate Key Fee", BigDecimal.valueOf(dues.getExtra_key()).multiply(currentDefinedFee.getMain_gate_key()), dues.getExtra_key());
		if(dues.getSail_school_loft_key() != 0) addItemRow(detailTable, "Extra Sail School Loft Key Fee", BigDecimal.valueOf(dues.getSail_school_loft_key()).multiply(currentDefinedFee.getSail_school_loft_key()), dues.getSail_school_loft_key());
		if(dues.getKayac_shed_key() != 0) addItemRow(detailTable, "Extra Inside Storage Key", BigDecimal.valueOf(dues.getKayac_shed_key()).multiply(currentDefinedFee.getKayak_shed_key()), dues.getKayac_shed_key());
		if(dues.getSail_loft_key() != 0) addItemRow(detailTable, "Extra Sail Loft Key Fee", BigDecimal.valueOf(dues.getSail_loft_key()).multiply(currentDefinedFee.getSail_loft_key()), dues.getSail_loft_key());

		BigDecimal initiation = new BigDecimal(dues.getInitiation());
		BigDecimal ysc = new BigDecimal(dues.getYsc_donation());
			BigDecimal other = new BigDecimal(dues.getOther());
			BigDecimal total = new BigDecimal(dues.getTotal());
			BigDecimal credit = new BigDecimal(dues.getCredit());
			BigDecimal paid = new BigDecimal(dues.getPaid());
			BigDecimal balance = new BigDecimal(dues.getBalance());

		if(initiation.compareTo(BigDecimal.ZERO) != 0) addItemRow(detailTable, "Initiation Fee", initiation, 0);
		if(ysc.compareTo(BigDecimal.ZERO) != 0) addItemRow(detailTable, "Youth Sailing Club Donation", ysc, 0);
		if(other.compareTo(BigDecimal.ZERO) != 0) addItemRow(detailTable, "Other: " + getOtherNote(dues) , other,0);
		addItemRow(detailTable, "Total Fees", total,0);
		if(credit.compareTo(BigDecimal.ZERO) != 0) addCreditRow(detailTable, "Credit", credit,0);
		addItemRow(detailTable, "Total Due", total.subtract(credit),0);
		addItemPaidRow(detailTable, paid);
		if(balance.compareTo(BigDecimal.ZERO) != 0) addBalanceRow(detailTable, "Balance", balance, 0);
		}
		
		return detailTable;
	}
	
	private String getOtherNote(Object_PaidDues dues) {
		String thisMemo = null;
		System.out.println("Getting memo for " + dues.getF_name() + " " + dues.getL_name());
		System.out.println("Money ID=" + dues.getMoney_id());
		// make sure the memo exists
		if(SqlExists.memoExists(dues.getMoney_id())) {
		thisMemo = SqlSelect.getMemos(dues).getMemo();
		} else {
		thisMemo = "No note for this entry";
		}
		return thisMemo;
	}
	
	int returnWetSlipNumber(BigDecimal numberOf) {
		int result = 0;
		if(numberOf.compareTo(BigDecimal.ZERO) != 0) result = 1;
		return result;
	}
	
	private void addItemPaidRow(Table detailTable, BigDecimal money) {
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
		cell.add(new Paragraph(addDollarSign() + df.format(money))).setFontSize(10);
		detailTable.addCell(cell);
	}
	
	private void addBalanceRow(Table detailTable, String label, BigDecimal money, int numberOf) {
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
	
	private void addItemRow(Table detailTable, String label, BigDecimal money, int numberOf) {
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph(label)).setFontSize(10));
		if(numberOf == 0) {
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));	
		} else {
			detailTable.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).add(new Paragraph("" + numberOf)).setFontSize(10));	
		}
		detailTable.addCell(new Cell().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).add(new Paragraph(addDollarSign() + df.format(money))).setFontSize(10));
	}
	
	private void addCreditRow(Table detailTable, String label, BigDecimal money, int numberOf) {
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph(label)).setFontSize(10));
		if(numberOf == 0) {
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));	
		} else {
			detailTable.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).add(new Paragraph("" + numberOf)).setFontSize(10));	
		}
		detailTable.addCell(new Cell().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).add(new Paragraph("-" + addDollarSign() + money.setScale(2).toPlainString())).setFontSize(10));
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
		if (totals.getWet_slip().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Wet slip Fee" , totals.getWet_slipNumber(), totals.getWet_slip());
		}
		if (totals.getBeach().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Beach Spot Fee" , totals.getBeachNumber(), totals.getBeach());
		}
		if (totals.getKayak_rack().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Outside Kayak Rack Fee" , totals.getKayak_rackNumber(), totals.getKayak_rack());
		}
		if (totals.getKayak_shed().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Inside Kayak Storage Fee" , totals.getKayak_shedNumber(), totals.getKayak_shed());
		}
		if (totals.getSail_loft().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Sail Loft Access Fee" , totals.getSail_loftNumber(), totals.getSail_loft());
		}
		if (totals.getSail_school_laser_loft().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Sail School Loft Access Fee" , totals.getSail_school_laser_loftNumber(), totals.getSail_school_laser_loft());
		}
		if (totals.getInitiation().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Initiation Fee" , totals.getInitiationNumber(), totals.getInitiation());
		}
		///////////////////  KEYS //////////////////////////////
		if (totals.getGate_key().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Extra Gate Key Fee" , totals.getGate_keyNumber(), totals.getGate_key());
		}
		if (totals.getSail_loft_key().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Sail Loft Key Fee" , totals.getSail_loft_keyNumber(), totals.getSail_loft_key());
		}
		if (totals.getSail_school_loft_key().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Sail School Loft Key Fee" , totals.getSail_school_loft_keyNumber(), totals.getSail_school_loft_key());
		}
		if (totals.getKayac_shed_key().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Kayak Shed Key Fee" , totals.getKayac_shed_keyNumber(), totals.getKayac_shed_key());
		}
		if (totals.getYsc_donation().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Youth Sailing Club Donation" , totals.getYsc_donationNumber(), totals.getYsc_donation());
		}
		if (totals.getOther().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Other" , totals.getOtherNumber(), totals.getOther());
		}
		if (totals.getCredit().compareTo(BigDecimal.ZERO) != 0) {
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
		cell.add(new Paragraph(addDollarSign() + df.format(totals.getTotal()))).setFontSize(10);
		mainTable.addCell(cell);
		
		return mainTable;
	}
	
	private Object_DepositSummary updateTotals() {
		int numberOfRecordsCounted = 0; // number of records counted
		Object_DepositSummary t = new Object_DepositSummary();
		for (Object_PaidDues d : paidDuesForDeposit) {

			if (d.getBeach() != 0) { ///////// BEACH
				t.setBeachNumber(d.getBeach() + t.getBeachNumber());
				BigDecimal totalBeachDollars = currentDefinedFee.getBeach().multiply(BigDecimal.valueOf(d.getBeach()));
				t.setBeach(totalBeachDollars.add(t.getBeach()));
			}

			BigDecimal credit = new BigDecimal(d.getCredit());
			if (credit.compareTo(BigDecimal.ZERO) != 0) {  ////////  CREDIT
				t.setCreditNumber(1 + t.getCreditNumber());
				t.setCredit(credit.add(t.getCredit()));
			}

			BigDecimal dues = new BigDecimal(d.getDues());
			if (dues.compareTo(BigDecimal.ZERO) != 0) {  ////////  DUES
				t.setDuesNumber(1 + t.getDuesNumber());
				t.setDues(dues.add(t.getDues()));
			}

			if (d.getExtra_key() != 0) { /////  EXTRA GATE KEY
				t.setGate_keyNumber(d.getExtra_key() + t.getGate_keyNumber());
				BigDecimal totalGateKeyDollars = currentDefinedFee.getMain_gate_key().multiply(BigDecimal.valueOf(d.getExtra_key()));
				t.setGate_key(t.getGate_key().add(totalGateKeyDollars));
			}

			BigDecimal initiation = new BigDecimal(d.getInitiation());
			if (initiation.compareTo(BigDecimal.ZERO) != 0) {  /////// INITIATION
				t.setInitiationNumber(1 + t.getInitiationNumber());
				t.setInitiation(initiation.add(t.getInitiation()));
			}

			if (d.getKayac_rack() != 0) {  ///// KAYACK RACK FEE
				t.setKayak_rackNumber(d.getKayac_rack() + t.getKayak_rackNumber());
				BigDecimal totalKayakRackDollars = currentDefinedFee.getKayak_rack().multiply(BigDecimal.valueOf(d.getKayac_rack()));
				t.setKayak_rack(totalKayakRackDollars.add(t.getKayak_rack()));
			}

			if (d.getKayac_shed() != 0) {   //////// KAYAK SHED ACCESS
				t.setKayak_shedNumber(d.getKayac_shed() + t.getKayac_shed_keyNumber());
				BigDecimal totalKayakShedDollars = currentDefinedFee.getKayak_shed().multiply(BigDecimal.valueOf(d.getKayac_shed()));
				t.setKayak_shed(totalKayakShedDollars.add(t.getKayak_shed()));
			}
			if (d.getKayac_shed_key() != 0) {   ///// KAYAK SHED KEY
				t.setKayac_shed_keyNumber(d.getKayac_shed_key() + t.getKayac_shed_keyNumber());
				BigDecimal totalKayakShedKeyDollars = currentDefinedFee.getKayak_shed_key().multiply(BigDecimal.valueOf(d.getKayac_shed_key()));
				t.setKayac_shed_key(totalKayakShedKeyDollars.add(t.getKayac_shed_key()));
			}

			BigDecimal other = new BigDecimal(d.getOther());
			if (other.compareTo(BigDecimal.ZERO) != 0) {  /////////  OTHER FEE ///////// IN DOLLARS
				t.setOtherNumber(1 + t.getOtherNumber());
				t.setOther(other.add(t.getOther()));
			}
			if (d.getSail_loft() != 0) {   ////////// SAIL LOFT ACCESS ///////// IN NUMBER OF
				t.setSail_loftNumber(1 + t.getSail_loftNumber());
				t.setSail_loft(currentDefinedFee.getSail_loft().add(t.getSail_loft()));
			}
			if (d.getSail_loft_key() != 0) {  ///////// SAIL LOFT KEY ///////// IN NUMBER OF
				t.setSail_loft_keyNumber(d.getSail_loft_key() + t.getSail_loft_keyNumber());
				BigDecimal totalSailLoftKeyDollars = currentDefinedFee.getSail_loft_key().multiply(BigDecimal.valueOf(d.getSail_loft_key()));
				t.setSail_loft_key(totalSailLoftKeyDollars.add(t.getSail_loft_key()));
			}
			if (d.getSail_school_laser_loft() != 0) {  ///////// SAIL SCHOOL LOFT ACCESS ///////// IN NUMBER OF
				t.setSail_school_laser_loftNumber(d.getSail_school_laser_loft() + t.getSail_school_laser_loftNumber());
				BigDecimal totalSailSchoolLoftDollars = currentDefinedFee.getSail_school_laser_loft().multiply(BigDecimal.valueOf(d.getSail_school_laser_loft()));
				System.out.println("Sail school laser loft=" + totalSailSchoolLoftDollars);
				t.setSail_school_laser_loft(totalSailSchoolLoftDollars.add(t.getSail_school_laser_loft()));
			}
			if (d.getSail_school_loft_key() != 0) {  ////////// SAIL SCHOOL LOFT KEY ///////// IN NUMBER OF
				t.setSail_school_loft_keyNumber(d.getSail_school_loft_key() + t.getSail_school_loft_keyNumber());
				BigDecimal totalSailSchoolLoftKeyDollars = currentDefinedFee.getSail_school_loft_key().multiply(BigDecimal.valueOf(d.getSail_school_loft_key()));
				t.setSail_school_loft_key(totalSailSchoolLoftKeyDollars.add(t.getSail_school_loft_key()));
			}

			BigDecimal wetSlip = new BigDecimal(d.getWet_slip());
			if (wetSlip.compareTo(BigDecimal.ZERO) != 0) {  ////////// WET SLIP FEE ///////// IN DOLLARS
				t.setWet_slipNumber(1 + t.getWet_slipNumber());
				t.setWet_slip(new BigDecimal(d.getWet_slip()).add(t.getWet_slip()));
			}
			if (d.getWinter_storage() != 0) {  ////////  WINTER STORAGE FEE ///////// IN NUMBER OF
				t.setWinter_storageNumber(d.getWinter_storage() + t.getWinter_storageNumber());
				BigDecimal totalWinterStorageDollars = currentDefinedFee.getWinter_storage().multiply(BigDecimal.valueOf(d.getWinter_storage()));
				t.setWinter_storage(totalWinterStorageDollars.add(t.getWinter_storage()));
			}

			BigDecimal ysc = new BigDecimal(d.getYsc_donation());
			if (ysc.compareTo(BigDecimal.ZERO) != 0) {  //////// YSC DONATION ///////// IN DOLLARS
				t.setYsc_donationNumber(1 + t.getYsc_donationNumber());
				t.setYsc_donation(new BigDecimal(d.getYsc_donation()).add(t.getYsc_donation()));
			}

			BigDecimal paid = new BigDecimal(d.getPaid());
			if (paid.compareTo(BigDecimal.ZERO) != 0) {
				t.setPaid(new BigDecimal(d.getPaid()).add(t.getPaid()));
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
	
	private void addSummaryRow(Table mainTable, String label, int numberOf, BigDecimal money) {
		mainTable.addCell(new Cell());
		mainTable.addCell(new Cell());
		mainTable.addCell(new Cell().add(new Paragraph(label)).setFontSize(10));
		mainTable.addCell(new Cell().add(new Paragraph(numberOf + "")).setFontSize(10));
		mainTable.addCell(new Cell().add(new Paragraph(addDollarSign() + df.format(money)).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
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
