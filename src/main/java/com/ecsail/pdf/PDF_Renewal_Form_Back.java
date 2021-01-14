package com.ecsail.pdf;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.property.TextAlignment;

public class PDF_Renewal_Form_Back {
	public static void Create_Back_Side(Document document) {
		Table mainTable = new Table(1);
		mainTable.setWidth(590);
		Paragraph p;
		Cell cell;
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
        p = new Paragraph("Eagle Creek Sailing Club, Inc");
        p.setTextAlignment(TextAlignment.CENTER);
        p.setFontSize(12).setBold();
        p.setFixedLeading(10);
		cell.add(p);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.setWidth(410);
		mainTable.addCell(cell);
		document.add(mainTable);
		
	//	document.add(new Paragraph("Eagle Creek Sailing Club, Inc").setAlignment(Element.ALIGN_CENTER));
	}
}
