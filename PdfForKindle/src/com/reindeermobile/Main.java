
package com.reindeermobile;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static final float ZOOM = 2.1f;

    public static void main(String[] args) {
        try {
            manipulatePdf("Struts2.pdf", "Struts2.temp.pdf");
            rotatePdf("Struts2.temp.pdf","Struts2.kindle.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void manipulatePdf(String src, String dest)
            throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter writer =
                PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfContentByte content = writer.getDirectContent();
        for (int i = 3; i < 120; i++) {
            PdfImportedPage page = writer.getImportedPage(reader, i);
            float x, y;
            x = 0;
            y = -650;
            content.addTemplate(page, ZOOM, 0, 0, ZOOM, x, y);
            content.moveTo(0, 10);
            content.lineTo(900, 10);
            content.closePath();
            content.stroke();
            document.newPage();
            x = 0;
            y = -100;
            content.addTemplate(page, ZOOM, 0, 0, ZOOM, x, y);
            content.moveTo(0, 570);
            content.lineTo(900, 570);
            content.closePath();
            content.stroke();
            document.newPage();
        }
        document.close();
    }

    public static void rotatePdf(String src, String dest)
            throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        int n = reader.getNumberOfPages();
        int rot;
        PdfDictionary pageDict;
        for (int i = 1; i <= n; i++) {
            rot = reader.getPageRotation(i);
            pageDict = reader.getPageN(i);
            pageDict.put(PdfName.ROTATE, new PdfNumber(rot - 90));
        }
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        stamper.close();
    }
}
