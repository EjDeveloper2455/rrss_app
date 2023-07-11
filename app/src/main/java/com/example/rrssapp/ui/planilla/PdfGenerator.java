package com.example.rrssapp.ui.planilla;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.Log;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfGenerator {

    public static String generatePDF(String filePath) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph("Contenido del PDF"));
            document.close();
            return "Archivo PDF generado correctamente.";
        } catch (DocumentException | IOException e) {
           return e.toString();
        }
    }
}
