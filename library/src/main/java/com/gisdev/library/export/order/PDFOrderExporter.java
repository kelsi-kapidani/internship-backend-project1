package com.gisdev.library.export.order;

import com.gisdev.library.exception.BadRequestException;
import org.springframework.stereotype.Component;
import com.gisdev.library.entity.BookLibraryOrder;
import com.gisdev.library.entity.LibraryOrder;
import com.gisdev.library.entity.LibraryUser;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.ByteArrayOutputStream;

@Component
public class PDFOrderExporter {

    public byte[] exportOrder(LibraryOrder order) {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf);


            doc.add(new Paragraph("ORDER DETAILS")
                    .setBold()
                    .setFontSize(18));

            doc.add(new Paragraph("Order ID: " + order.getId()));
            doc.add(new Paragraph("Status: " + order.getStatus()));

            if (order.getNote() != null) {
                doc.add(new Paragraph("Note: " + order.getNote()));
            }

            doc.add(new Paragraph("\n"));

            LibraryUser user = order.getUser();

            doc.add(new Paragraph("USER INFORMATION")
                    .setBold()
                    .setFontSize(14));

            doc.add(new Paragraph("• Name: " + user.getName() + " " + user.getSurname()));
            doc.add(new Paragraph("• Username: " + user.getUsername()));
            doc.add(new Paragraph("• Email: " + user.getEmail()));

            doc.add(new Paragraph("\n"));

            doc.add(new Paragraph("BOOKS")
                    .setBold()
                    .setFontSize(14));

            int total = 0;

            for (BookLibraryOrder bo : order.getBooks()) {

                total += bo.getValue();

                String bookLine = "✓ Book: " + bo.getBook().getTitle();

                doc.add(new Paragraph(bookLine).setBold());

                doc.add(new Paragraph("   - Quantity: " + bo.getSize()));
                doc.add(new Paragraph("   - Value: " + bo.getValue()));
                doc.add(new Paragraph(""));
            }

            doc.add(new Paragraph("TOTAL: " + total + " €")
                    .setBold()
                    .setFontSize(12));

            doc.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new BadRequestException("Error generating PDF");
        }
    }
}
