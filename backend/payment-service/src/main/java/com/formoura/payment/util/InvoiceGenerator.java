package com.formoura.payment.util;

import com.formoura.payment.entity.Payment;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class InvoiceGenerator {

    public String generateInvoice(Payment payment){

        try{

            File folder = new File("invoice");

            if(!folder.exists()){

                folder.mkdirs();

            }

            String fileName =
                    "invoice/invoice_"
                            + payment.getOrderId()
                            + ".pdf";

            PdfWriter writer =
                    new PdfWriter(fileName);

            PdfDocument pdf =
                    new PdfDocument(writer);

            Document document =
                    new Document(pdf);

            document.add(new Paragraph("FORMOURA"));

            document.add(new Paragraph("------------------------"));

            document.add(new Paragraph(
                    "Invoice"));

            document.add(new Paragraph(
                    "Order Id : "
                            + payment.getOrderId()));

            document.add(new Paragraph(
                    "User Id : "
                            + payment.getUserId()));

            document.add(new Paragraph(
                    "Amount : "
                            + payment.getAmount()));

            document.add(new Paragraph(
                    "Status : "
                            + payment.getPaymentStatus()));

            document.close();

            return fileName;

        }

        catch(Exception ex){

            throw new RuntimeException(ex);

        }

    }

}