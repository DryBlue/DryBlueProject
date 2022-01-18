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
                       final String cognome, final String indirizzo, final Integer code) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        Font fontTitolo = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        final int size = 28;
        fontTitolo.setSize(size);
        fontTitolo.setColor(Color.blue);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
         final int size1 = 20;
        font.setSize(size1);

       document.open();

        Paragraph titolo = new Paragraph("DryBlueLaundry", fontTitolo);
        titolo.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(titolo);

        Paragraph ordine = new Paragraph("Ordine: " + code, font);
        final int spacing0 = 50;
        ordine.setSpacingBefore(spacing0);
        ordine.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(ordine);

        PdfPTable table = new PdfPTable(2);
        final int spacing = 10;
        table.setSpacingBefore(spacing);


        writeTableHeader(table);
        table.addCell(nome + " " + cognome + "\n" + indirizzo);
        table.addCell("DryBlueLaundry\nAriano Irpino, via Cardito, 52\nItalia");


            document.add(table);
            document.close();

    }
}
