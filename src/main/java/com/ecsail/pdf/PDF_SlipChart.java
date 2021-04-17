package com.ecsail.pdf;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

import com.ecsail.main.Paths;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.AreaBreakType;

public class PDF_SlipChart {

	public PDF_SlipChart() {
	try {
		createChart();
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	System.out.println("destination=" + Paths.EMAILLIST + "_SlipCart.pdf");
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
	public void createChart() throws FileNotFoundException {
		PdfWriter writer = new PdfWriter(Paths.EMAILLIST + "_directory.pdf");
		// Initialize PDF document
		PdfDocument pdf = new PdfDocument(writer);
		// PageSize A5v = new PageSize(PageSize.A5.getWidth(), PageSize.A5.getHeight());
		Document doc = new Document(pdf, new PageSize(PageSize.A4));
		doc.setLeftMargin(0.5f);
		doc.setRightMargin(0.5f);
		doc.setTopMargin(1f);
		doc.setBottomMargin(0.5f);
		doc.add(new Paragraph("slipchart"));
		//Collections.sort(rosters, Comparator.comparing(Object_MembershipList::getLname));

		doc.close();
	}
	

}

