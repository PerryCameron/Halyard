package com.ecsail.pdf;

import com.ecsail.main.HalyardPaths;
import com.ecsail.sql.SqlSelect;
import com.ecsail.sql.Sql_SelectMembership;
import com.ecsail.structures.Object_MembershipList;
import com.ecsail.structures.Object_PaidDues;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.collections.ObservableList;

import java.awt.*;
import java.io.*;
import java.util.Observable;

public class PDF_BoatReport {
    private ObservableList<Object_MembershipList> membershipLists;

    public PDF_BoatReport() {
        this.membershipLists = Sql_SelectMembership.getRosterOfAll(HalyardPaths.getYear());

        // Initialize PDF writer
        PdfWriter writer = null;
        // Check to make sure directory exists and if not create it
        HalyardPaths.checkPath(HalyardPaths.BOATLISTS + "/" + HalyardPaths.getYear());
        String dest = HalyardPaths.BOATLISTS+ "/" + HalyardPaths.getYear() + "/BoatList_" + HalyardPaths.getDate() + ".pdf";

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
        document.add(titlePdfTable());
        for(Object_MembershipList m: membershipLists) {
        document.add(membershipTable(m));



        }

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

    public Table membershipTable(Object_MembershipList ml) {
        Table detailTable = new Table(3);
        // mainTable.setKeepTogether(true);
        Cell cell;

            cell = new Cell();
            cell.setBorder(Border.NO_BORDER);
            cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
            cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
            cell.setWidth(50);
            cell.add(new Paragraph(SqlSelect.getMembershipId(HalyardPaths.getYear(), ml.getMsid()) + "" + "")).setFontSize(10);
            detailTable.addCell(cell);

            cell = new Cell();
            cell.setBorder(Border.NO_BORDER);
            cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
            cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
            cell.setWidth(100);
            cell.add(new Paragraph(ml.getLname() + "")).setFontSize(10);
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

        return detailTable;
        }

    public Table titlePdfTable() {
        com.itextpdf.layout.element.Image ecscLogo = new Image(ImageDataFactory.create(toByteArray(getClass().getResourceAsStream("/EagleCreekLogoForPDF.png"))));
        Table mainTable = new Table(3);
        Cell cell;
        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        cell.setWidth(200);
        cell.add(new Paragraph("Membership List with boats"));
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
        cell.add(new Paragraph(HalyardPaths.getDate() + "")).setFontSize(10);
        mainTable.addCell(cell);

        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        mainTable.addCell(cell);

        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        mainTable.addCell(cell);
        return mainTable;
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
