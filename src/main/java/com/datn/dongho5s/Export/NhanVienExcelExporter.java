package com.datn.dongho5s.Export;

import com.datn.dongho5s.Entity.NhanVien;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class NhanVienExcelExporter extends AbstractExporter {
    private XSSFWorkbook workbook;
    private Sheet sheet;

    public NhanVienExcelExporter() {
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("NhanVien");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        cellStyle.setFont(font);

        createCell(row, 0, "User ID", cellStyle);
        createCell(row, 1, "Address", cellStyle);
        createCell(row, 2, "E-mail", cellStyle);
        createCell(row, 3, "LastName", cellStyle);
        createCell(row, 4, "FirstName", cellStyle);
        createCell(row, 5, "Phone", cellStyle);
        createCell(row, 6, "Roles", cellStyle);
        createCell(row, 7, "Enabled", cellStyle);
    }

    private void createCell(Row row, int columnIndex, Object value, CellStyle style) {
        Cell cell = row.createCell(columnIndex);
        sheet.autoSizeColumn(columnIndex);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue(value.toString());
        }

        if (style != null) {
            cell.setCellStyle(style);
        }
    }

    public void export(List<NhanVien> listNhanVien, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/octet-stream", ".xlsx","users_");

        writeHeaderLine();
        writeDataLines(listNhanVien);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeDataLines(List<NhanVien> listNhanVien) {
        int rowIndex = 1;

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);
        cellStyle.setFont(font);

        for (NhanVien nhanVien : listNhanVien) {
            Row row = sheet.createRow(rowIndex++);
            int columnIndex = 0;
            createCell(row, columnIndex++, nhanVien.getId(), cellStyle);
            createCell(row, columnIndex++, nhanVien.getDiaChi(), cellStyle);
            createCell(row, columnIndex++, nhanVien.getEmail(), cellStyle);
            createCell(row, columnIndex++, nhanVien.getHo(), cellStyle);
            createCell(row, columnIndex++, nhanVien.getTen(), cellStyle);
            createCell(row, columnIndex++, nhanVien.getSoDienThoai(), cellStyle);
//            createCell(row, columnIndex++, nhanVien.getChucVu().toString(), cellStyle);
            createCell(row, columnIndex++, nhanVien.isEnabled(), cellStyle);

        }
    }
}
