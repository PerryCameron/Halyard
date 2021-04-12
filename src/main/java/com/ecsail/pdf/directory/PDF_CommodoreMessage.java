package com.ecsail.pdf.directory;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;

public class PDF_CommodoreMessage extends Table {

	PDF_Object_Settings set;
	public PDF_CommodoreMessage(int numColumns, PDF_Object_Settings set) {
		super(numColumns);
		this.set = set;
		setWidth(PageSize.A5.getWidth() * 0.9f);  // makes table 90% of page width
		setHorizontalAlignment(HorizontalAlignment.CENTER);
		
		///////////////// Cells ////////////////////////////

		String commodoreTitle = "Hello to the greatest sailing club in America.";
		String commodoreParagraph1 = "It is truly an honor to serve my ECSC family.  From the first time we entered the property Nancy and I experienced calm tranquility mixed with excitement and wonder.  We always say that it’s a little vacation every time we are at the club.  I was immediately impressed by how helpful people are if you remain humble, inquisitive, and grateful for them.  There are so many sailors that want to share their knowledge, and we are extremely grateful for that. This is truly a remarkable place where people from diverse backgrounds and experience levels join together to share a little slice of heaven.   Whether you be a casual cruiser or a die-hard racer, sail a big cruiser or a small dinghy, are a CEO or are retired, we all find camaraderie at ECSC.  Nancy and I joined with very little sailing experience, but we threw ourselves into the club and sailing with reckless abandon.  Some attempts were more successful than others, but we continued to practice.  At first, we just wanted to cruise but quickly fell in love with racing as well.  Now we just love to be on the water and around our fellow sailors.  Sometimes it’s just great to be at the club and hear halyards slapping on masts on a windy day.";
		String commodoreParagraph2 = "I encourage everyone to take advantage of the myriad of opportunities at our club from social events to racing and volunteer work parties.  Your board works hard to have plenty to offer all ages and interests.  There are many ways to meet new people, share a few laughs and maybe even learn a thing or two.";
		String commodoreParagraph3 = "I am optimistic that 2021 will be a much more enjoyable and interactive year for us all.  Hopefully we will be able to catch up with old friends, make new ones, and take advantage of all the magic afforded by our beloved club.  Please respect others as we have made great strides but COVID is not behind us yet.  My biggest requests are that we all share a fat happy smile and greet each other warmly, offer a helping hand without being asked, show grace if you feel offended, and spread some joy.  Remember that for all of us this is a 100% discretionary time and income activity, so let’s keep it safe and enjoyable for all.";
		
		addCell(addVerticalSpace(1));
		addCell(newParagraph(commodoreTitle));
		addCell(addVerticalSpace(1));
		addCell(newParagraph(commodoreParagraph1));
		addCell(addVerticalSpace(1));
		addCell(newParagraph(commodoreParagraph2));
		addCell(addVerticalSpace(1));
		addCell(newParagraph(commodoreParagraph3));
		addCell(addVerticalSpace(1));
		addCell(newParagraph("Chuck Goff"));
		addCell(newParagraph("2021 ECSC Commodore"));
	}
	
	private Cell newParagraph(String text) {
		Paragraph p;
		p = new Paragraph(text);
		p.setFontSize(set.getNormalFontSize());
		Cell cell = new Cell();
		cell.add(p);
		cell.setBorder(Border.NO_BORDER);
		return cell;
	}
	
	private Cell addVerticalSpace(int space) {
		String carrageReturn = "";
		for(int i = 0; i < space; i++) {
			carrageReturn += "\n";
		}
		Cell cell = new Cell();
		Paragraph p = new Paragraph(carrageReturn);
		p.setFixedLeading(7);	
		cell.add(p);
		cell.setBorder(Border.NO_BORDER);
		return cell;
	}

}
