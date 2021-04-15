package com.ecsail.pdf.directory;

import java.io.IOException;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.CalRgb;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Style;

public class PDF_Object_Settings {
	public static final String HEADINGS = "src/main/resources/fonts/Fredoka_One/FredokaOne-Regular.ttf";

	private String logoPath;
	private String selectedYear;
	private int normalFontSize;
	private int slipFontSize;
	private int fixedLeading;
	private int fixedLeadingNarrow;
	private float titleBoxHeight;
	private PdfFont columnHead;
	private DeviceCmyk mainColor;
	private Color dockColor;
	private Style emailColor;
	private int numberOfRowsByNumber; // this is the number of columns in MembersByNumber
	private int numberOfCommodoreColumes; // this is the number of rows in Commodore Page
	
	public PDF_Object_Settings(String selectedYear) {
		super();
		this.logoPath = "/Stickers/2021complete.png";
		this.selectedYear = selectedYear;
		this.normalFontSize = 10;
		this.slipFontSize = 6;
		this.fixedLeading = 25;
		this.fixedLeadingNarrow = 10;
		this.titleBoxHeight =20;
		this.columnHead = constructFontHeading();
		this.mainColor = new DeviceCmyk(.93f, 0, 0.7f, 0.62f);  // green color in document
		this.dockColor  = new DeviceRgb(237, 237, 237);
		this.emailColor = new Style().setFontColor(ColorConstants.BLUE);
		this.numberOfRowsByNumber = 28;
		this.numberOfCommodoreColumes = 2;
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

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
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

	public int getFixedLeadingNarrow() {
		return fixedLeadingNarrow;
	}

	public void setFixedLeadingNarrow(int fixedLeadingNarrow) {
		this.fixedLeadingNarrow = fixedLeadingNarrow;
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

	public Style getEmailColor() {
		return emailColor;
	}

	public void setEmailColor(Style emailColor) {
		this.emailColor = emailColor;
	}

	public int getNumberOfRowsByNumber() {
		return numberOfRowsByNumber;
	}

	public void setNumberOfRowsByNumber(int numberOfRowsByNumber) {
		this.numberOfRowsByNumber = numberOfRowsByNumber;
	}

	public int getNumberOfCommodoreColumes() {
		return numberOfCommodoreColumes;
	}

	public void setNumberOfCommodoreColumes(int numberOfCommodoreColumes) {
		this.numberOfCommodoreColumes = numberOfCommodoreColumes;
	}

	public Color getDockColor() {
		return dockColor;
	}

	public void setDockColor(Color dockColor) {
		this.dockColor = dockColor;
	}

	public int getSlipFontSize() {
		return slipFontSize;
	}

	public void setSlipFontSize(int slipFontSize) {
		this.slipFontSize = slipFontSize;
	}
	
	
	
}
