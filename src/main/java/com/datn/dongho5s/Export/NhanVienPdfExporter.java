package com.datn.dongho5s.Export;

import com.datn.dongho5s.Entity.NhanVien;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class NhanVienPdfExporter extends AbstractExporter {
    public void export(List<NhanVien> listNhanVien, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/pdf", ".pdf","users_");
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = new Font(Font.HELVETICA, 18, Font.ITALIC);
        Paragraph paragraph = new Paragraph("Danh Sach Nhan Vien", font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(paragraph);
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);
        table.setWidths(new float[]{1.2f, 3.5f, 3.0f, 3.0f, 1.7f, 1.5f, 2.0f, 1.5f});

        writeTableHeader(table);
        writeTableData(table, listNhanVien);

        document.add(table);
        document.close();
    }

    private void writeTableData(PdfPTable table, List<NhanVien> listNhanVien) {
        for (NhanVien nhanVien : listNhanVien) {
            table.addCell(String.valueOf(nhanVien.getId()));
            table.addCell(nhanVien.getDiaChi());
            table.addCell(nhanVien.getEmail());
            table.addCell(nhanVien.getHo());
            table.addCell(nhanVien.getTen());
            table.addCell(String.valueOf(nhanVien.getSoDienThoai()));
//            table.addCell(nhanVien.getChucVu().toString());
            table.addCell(String.valueOf(nhanVien.isEnabled()));
        }
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = new Font(Font.HELVETICA, 10, Font.ITALIC);
        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Address", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("LastName", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("FirstName", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Phone", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Roles", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Enabled", font));
        table.addCell(cell);
    }
}
