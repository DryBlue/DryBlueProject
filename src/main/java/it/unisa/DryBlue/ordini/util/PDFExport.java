package it.unisa.DryBlue.ordini.util;


import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;


import javax.servlet.http.HttpServletResponse;
import java.awt.*;



public class PDFExport {

    public PDFExport() {
    }

    private void writeTableHeader(PdfPTable pdfTable) {
        PdfPCell cell = new PdfPCell();
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        Font fontCorpo = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontCorpo.setSize(18);

        cell = new PdfPCell(new Phrase("Destinatario", fontCorpo));
        pdfTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Mittente", fontCorpo));
        pdfTable.addCell(cell);
    }


    public void export(HttpServletResponse response, String nome, String cognome, String indirizzo) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        Font fontTitolo = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitolo.setSize(28);
        fontTitolo.setColor(Color.blue);
       document.open();

        Paragraph titolo = new Paragraph("DryBlueLaundry", fontTitolo);
        titolo.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(titolo);

        PdfPTable table = new PdfPTable(2);
        table.setSpacingBefore(100);


        writeTableHeader(table);
        table.addCell(nome + " " +cognome+ "\n" +indirizzo );
        table.addCell("DryBlueLaundry\nAriano Irpino, via Cardito, 52\nItalia");

        PdfPTable table1 = new PdfPTable(3);
        table1.setSpacingBefore(50);

            document.add(table);
            document.close();

    }
}
