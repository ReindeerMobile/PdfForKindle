
package com.reindeermobile;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            manipulatePdf("Struts2.pdf", "Struts2.kindle.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void manipulatePdf(String src, String dest)
            throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        Document document = new Document();
        PdfWriter writer =
                PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfContentByte content = writer.getDirectContent();
        for (int i = 3; i < 120; i++) {
            PdfImportedPage page = writer.getImportedPage(reader, i);
            float x, y;
            if (i % 2 == 0) {
                x = -30;
            } else {
                x = -50;
            }
            y = -90;
            content.addTemplate(page, 1.6f, 0, 0, 1.6f, x, y);
            content.moveTo(10, 10);
            content.lineTo(11, 11);
            content.moveTo(10, 830);
            content.lineTo(11, 831);
            content.closePath();
            content.stroke();
            document.newPage();
        }
        document.close();
    }
}
