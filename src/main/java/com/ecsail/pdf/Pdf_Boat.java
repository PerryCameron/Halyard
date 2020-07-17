package com.ecsail.pdf;

import com.ecsail.main.SqlSelect;
import com.ecsail.structures.Object_Boat;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
	
public class Pdf_Boat {

		private static List<Object_Boat> boats;
		static String[] boatHeaders = {
				"Boat Id",
				"Manufacturer",
				"Year",
				"Registration",
				"Model",
				"Boat Name",
				"Sail Number",
				"Trailer",
				"Length",
				"Width",
				"Keel Type",
			};
		static String dest = System.getProperty("user.home") + "/boats.pdf";
	    public static int counter = 1;
	    int columns = 11;
	    
	    public static void createPdf() throws IOException {
	    	boats = SqlSelect.getBoatsForPdf();
	        //Initialize PDF writer
	        PdfWriter writer = new PdfWriter(dest);
	 
	        //Initialize PDF document
	        PdfDocument pdf = new PdfDocument(writer);
	 
	        // Initialize document
	        Document document = new Document(pdf);
	        //PageSize ps = PageSize.A5;

	        document.add(mainPdfTable());
	        document.setTopMargin(0);
	        //Close document
	        document.close();
	        File file = new File(dest);
	        Desktop desktop = Desktop.getDesktop(); // Gui_Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()
	        desktop.open(file);
	    }

	    public static Table mainPdfTable() throws IOException {
	    	Table mainTable = new Table(11);
	    	mainTable.setBorder(Border.NO_BORDER);
	    	
			for(String str : boatHeaders ) {
				mainTable.addCell(new Cell()
						.setBackgroundColor(new DeviceCmyk(0, 0, 0.76f, 0.01f))
						//.setWidth(12)
						.add(new Paragraph(str).setFontSize(10)));
			}
			for(Object_Boat itm: boats) {
				mainTable.addCell(new Cell().add(new Paragraph(itm.getBoat_id() + "").setFontSize(7)));
				mainTable.addCell(new Cell().add(new Paragraph(itm.getManufacturer() + "").setFontSize(7)));
				mainTable.addCell(new Cell().add(new Paragraph(itm.getManufacture_year() + "").setFontSize(7)));
				mainTable.addCell(new Cell().add(new Paragraph(itm.getRegistration_num() + "").setFontSize(7)));
				mainTable.addCell(new Cell().add(new Paragraph(itm.getModel() + "").setFontSize(7)));
				mainTable.addCell(new Cell().add(new Paragraph(itm.getBoat_name() + "").setFontSize(7)));
				mainTable.addCell(new Cell().add(new Paragraph(itm.getSail_number() + "").setFontSize(7)));
				mainTable.addCell(new Cell().add(new Paragraph(itm.isHasTrailer() + "").setFontSize(7)));
				mainTable.addCell(new Cell().add(new Paragraph(itm.getLength() + "").setFontSize(7)));
				mainTable.addCell(new Cell().add(new Paragraph(itm.getWeight() + "").setFontSize(7)));
				mainTable.addCell(new Cell().add(new Paragraph(itm.getKeel() + "").setFontSize(7)));
				//mainTable.addCell(new Cell().add(new Paragraph(itm.getBoat_id() + "")));
			}
	    	

			
			return mainTable;
	    }
	    

	}

