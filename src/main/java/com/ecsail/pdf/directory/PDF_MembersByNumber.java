package com.ecsail.pdf.directory;

import java.util.Collections;
import java.util.Comparator;

import com.ecsail.structures.Object_MembershipList;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import javafx.collections.ObservableList;

public class PDF_MembersByNumber {
	ObservableList<Object_MembershipList> rosters;
	int startNumber = 0;
	PDF_Object_Settings set;
	public PDF_MembersByNumber(PDF_Object_Settings set) {
		this.set = set;
		this.rosters = rosters;
		//setWidth(PageSize.A5.getWidth() * 0.9f);  // makes table 90% of page width
		//setHorizontalAlignment(HorizontalAlignment.LEFT);
		
		// sort membership by membership number
		Collections.sort(this.rosters , Comparator.comparing(Object_MembershipList::getMembershipId));
		///////////////// Cells ////////////////////////////
		Cell cell;
		while(startNumber < rosters.size()) {
			
			cell = new Cell();
			cell.add(createColumnTable(28));
		}

	}
	
	private Table createColumnTable(int columnSize) {
		Table columnTable = new Table(1);
		for(int i = 0; i < columnSize; i++) {
			columnTable.addCell(createMembershipEntry(rosters.get(startNumber)));
			startNumber++;
		}
		
		return columnTable;
	}
	
	private Cell createMembershipEntry(Object_MembershipList m) {
		Cell cell;
		Paragraph p;
		cell = new Cell();
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		p = new Paragraph(m.getMembershipId() + " " + m.getLname());
		p.setFontSize(set.getNormalFontSize());
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		return cell;
	}
	

}
