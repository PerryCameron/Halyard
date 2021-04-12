package com.ecsail.pdf.directory;

import java.io.IOException;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Style;

public class PDF_Object_Settings {
	public static final String HEADINGS = "src/main/resources/fonts/Fredoka_One/FredokaOne-Regular.ttf";

	private String logoPath;
	private String selectedYear;
	private int normalFontSize;
	private int fixedLeading;
	private int fixedLeadingNarrow;
	private float titleBoxHeight;
	private PdfFont columnHead;
	private DeviceCmyk mainColor;
	private Style emailColor;
	private int numberOfColumns; // this is the number of columns in MembersByNumber
	
	public PDF_Object_Settings(String selectedYear) {
		super();
		this.logoPath = "/Stickers/2021complete.png";
		this.selectedYear = selectedYear;
		this.normalFontSize = 10;
		this.fixedLeading = 25;
		this.fixedLeadingNarrow = 10;
		this.titleBoxHeight =20;
		this.columnHead = constructFontHeading();
		this.mainColor = new DeviceCmyk(.93f, 0, 0.7f, 0.62f);  // green color in document
		this.emailColor = new Style().setFontColor(ColorConstants.BLUE);
		this.numberOfColumns = 28;

	}
	
	private PdfFont constructFontHeading() {
		PdfFont pdfFont = null;
		
		try {
			FontProgram fontProgram = FontProgramFactory.createFont(HEADINGS);
			pdfFont = PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI, true);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return pdfFont;
	}


	public String getLogoPath() {
		return logoPath;
	}

	public String getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(String selectedYear) {
		this.selectedYear = selectedYear;
	}

	public int getNormalFontSize() {
		return normalFontSize;
	}

	public void setNormalFontSize(int normalFontSize) {
		this.normalFontSize = normalFontSize;
	}

	public int getFixedLeading() {
		return fixedLeading;
	}

	public void setFixedLeading(int fixedLeading) {
		this.fixedLeading = fixedLeading;
	}

	public float getTitleBoxHeight() {
		return titleBoxHeight;
	}

	public void setTitleBoxHeight(float titleBoxHeight) {
		this.titleBoxHeight = titleBoxHeight;
	}

	public PdfFont getColumnHead() {
		return columnHead;
	}

	public void setColumnHead(PdfFont columnHead) {
		this.columnHead = columnHead;
	}

	public DeviceCmyk getMainColor() {
		return mainColor;
	}

	public void setMainColor(DeviceCmyk mainColor) {
		this.mainColor = mainColor;
	}

	public int getFixedLeadingNarrow() {
		return fixedLeadingNarrow;
	}

	public void setFixedLeadingNarrow(int fixedLeadingNarrow) {
		this.fixedLeadingNarrow = fixedLeadingNarrow;
	}

	public Style getEmailColor() {
		return emailColor;
	}

	public void setEmailColor(Style emailColor) {
		this.emailColor = emailColor;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}
	
	
	
}
