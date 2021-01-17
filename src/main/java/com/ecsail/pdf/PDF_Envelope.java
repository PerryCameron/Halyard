package com.ecsail.pdf;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.ecsail.main.Paths;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

public class PDF_Envelope {
	Image ecscLogo = new Image(ImageDataFactory.create(PDF_DepositReport.toByteArray(getClass().getResourceAsStream("/EagleCreekLogoForPDF.png"))));

	public PDF_Envelope() throws FileNotFoundException {
		Paths.checkPath(Paths.EMAILLIST);
		// Initialize PDF writer

		PdfWriter writer = new PdfWriter(Paths.EMAILLIST + "_envelopes.pdf");

		// Initialize PDF document
		PdfDocument pdf = new PdfDocument(writer);
		
		// Envelope sizeing https://www.paperpapers.com/envelope-size-chart.html
		// No. 6 1/4 (#6-1/4) Envelope inches: 3.5 x 6 (mm: 88.9 x 152.4)
		// 6 inch x 72 points = 432 points (the width)
		// 3.5 inch x 72 points = 252 points (the height)
		
		// #10 Envelope inches: 4.125 x 9.5 (mm: 104.775 x 241.3)
		// 9.5 x 72 points = 684 points (the width)
		// 4.125 x 72 points = 297 points (the height)
		Rectangle envelope = new Rectangle(684, 297);
		// Initialize document
				Document doc = new Document(pdf, new PageSize(envelope));
		doc.setTopMargin(0);
		doc.setLeftMargin(0.25f);
		//doc.add(new Paragraph("Perry Cameron ECSC Membership").setFontSize(10).setFixedLeading(10));
		//doc.add(new Paragraph("7078 Windridge Way").setFontSize(10).setFixedLeading(10));
		//doc.add(new Paragraph("Brownsburg, IN 46112").setFontSize(10).setFixedLeading(10));
		doc.add(createReturnAddress());
		doc.add(new Paragraph(new Text("\n\n\n\n\n")));
		doc.add(createAddress());
		//		p.setFontSize(10);
		//  p.setFixedLeading(10);
		
		doc.close();

		System.out.println("destination=" + Paths.EMAILLIST + "_envelopes.pdf");
		File file = new File(Paths.EMAILLIST + "_envelopes.pdf");
		Desktop desktop = Desktop.getDesktop(); // Gui_Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()

		// Open the document
		try {
			desktop.open(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//doc.add(new Paragraph("Perry Cameron ECSC Membership").setFontSize(10).setFixedLeading(10));
	//doc.add(new Paragraph("7078 Windridge Way").setFontSize(10).setFixedLeading(10));
	//doc.add(new Paragraph("Brownsburg, IN 46112").setFontSize(10).setFixedLeading(10));
	
	public Table createReturnAddress() {
		Table mainTable = new Table(2);
		mainTable.setWidth(290);
		ecscLogo.scale(0.25f, 0.25f);
		
		Cell cell;
		Paragraph p;

		cell = new Cell(3,1);
		cell.setWidth(40);
		cell.setBorder(Border.NO_BORDER);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
		cell.add(ecscLogo);
		mainTable.addCell(cell);
		
		p = new Paragraph("ECSC Membership");
		p.setFontSize(10);
		p.setFixedLeading(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("7078 Windridge Way");
		p.setFontSize(10);
		p.setFixedLeading(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Brownsburg, IN 46112");
		p.setFontSize(10);
		p.setFixedLeading(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		mainTable.addCell(cell);
		
		return mainTable;
		
	}
	
	public Table createAddress() {
		Table mainTable = new Table(2);
		mainTable.setWidth(590);
		ecscLogo.scale(0.25f, 0.25f);
		
		Cell cell;
		Paragraph p;

		cell = new Cell(3,1);
		cell.setWidth(260);
		cell.setBorder(Border.NO_BORDER);


		mainTable.addCell(cell);
		
		p = new Paragraph("John Tester");
		p.setFontSize(16);
		p.setFixedLeading(14);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("1234 testing blvd W");
		p.setFontSize(16);
		p.setFixedLeading(14);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Indianapolis, IN 46222");
		p.setFontSize(16);
		p.setFixedLeading(14);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		mainTable.addCell(cell);
		
		return mainTable;
	}

}
