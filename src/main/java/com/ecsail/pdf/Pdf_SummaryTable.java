package com.ecsail.pdf;

import com.ecsail.structures.Object_Deposit;
import com.ecsail.structures.Object_DepositSummary;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

public class Pdf_SummaryTable  {
	
	private static Object_DepositSummary totals;
	private static Object_Deposit currentDeposit;
	
	static String[] TDRHeaders = {
			"Date",
			"Deposit Number",
			"Fee",
			"Records",
			"Amount"
		};
	
	
	public Pdf_SummaryTable(Object_DepositSummary t, Object_Deposit cd) {
		Pdf_SummaryTable.totals = t;
		Pdf_SummaryTable.currentDeposit = cd;
		Table mainTable = new Table(TDRHeaders.length);
		Cell cell;
		
		addTableHead(mainTable);
		
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
	}
	
	private static void addTableHead(Table mainTable) {
		for (String str : TDRHeaders) {
			mainTable.addCell(new Cell().setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f))
					// .setWidth(12)
					.add(new Paragraph(str).setFontSize(10)));
		}
	}
	
	private static void RemoveBorder(Table table)
	{
	    for (IElement iElement : table.getChildren()) {
	        ((Cell)iElement).setBorder(Border.NO_BORDER);;
	    }
	}

}
