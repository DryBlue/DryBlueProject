package it.unisa.DryBlue.ordini.util;


import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;


import javax.servlet.http.HttpServletResponse;
import java.awt.*;



public class PDFExport {

    public PDFExport() {
    }

    private void writeTableHeader(final PdfPTable pdfTable) {
        PdfPCell cell = new PdfPCell();
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        final int padding = 5;
        cell.setPadding(padding);
        Font fontCorpo = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        final int size = 18;
        fontCorpo.setSize(size);

        cell = new PdfPCell(new Phrase("Destinatario", fontCorpo));
        pdfTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Mittente", fontCorpo));
        pdfTable.addCell(cell);
    }


    public void export(final HttpServletResponse response, final String nome,
                       final String cognome, final String indirizzo) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        Font fontTitolo = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        final int size = 28;
        fontTitolo.setSize(size);
        fontTitolo.setColor(Color.blue);
       document.open();

        Paragraph titolo = new Paragraph("DryBlueLaundry", fontTitolo);
        titolo.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(titolo);

        PdfPTable table = new PdfPTable(2);
        final int spacing = 100;
        table.setSpacingBefore(spacing);


        writeTableHeader(table);
        table.addCell(nome + " " + cognome + "\n" + indirizzo);
        table.addCell("DryBlueLaundry\nAriano Irpino, via Cardito, 52\nItalia");

        final int columns = 3;
        final int spacingB = 50;
        PdfPTable table1 = new PdfPTable(columns);
        table1.setSpacingBefore(spacingB);

            document.add(table);
            document.close();

    }
}
