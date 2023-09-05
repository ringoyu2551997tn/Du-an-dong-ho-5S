package com.datn.dongho5s.Export;

import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.NhanVien;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DanhMucExcelExporter extends AbstractExporter {
    private XSSFWorkbook workbook;
    private Sheet sheet;

    public DanhMucExcelExporter() {
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Danh Mục");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        cellStyle.setFont(font);

        createCell(row, 0, "Categories ID", cellStyle);
        createCell(row, 1, "Name", cellStyle);
        createCell(row, 2, "Enabled", cellStyle);
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

    public void export(List<DanhMuc> listDanhMuc, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/octet-stream", ".xlsx","categories_");

        writeHeaderLine();
        writeDataLines(listDanhMuc);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeDataLines(List<DanhMuc> listDanhMuc) {
        int rowIndex = 1;

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);
        cellStyle.setFont(font);

        for (DanhMuc danhMuc : listDanhMuc) {
            Row row = sheet.createRow(rowIndex++);
            int columnIndex = 0;
            createCell(row, columnIndex++, danhMuc.getId(), cellStyle);
            createCell(row, columnIndex++, danhMuc.getTen(), cellStyle);
            createCell(row, columnIndex++, danhMuc.isEnabled(), cellStyle);

        }
    }
}
