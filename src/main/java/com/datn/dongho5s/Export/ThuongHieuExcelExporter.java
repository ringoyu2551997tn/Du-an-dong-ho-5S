package com.datn.dongho5s.Export;

import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.ThuongHieu;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ThuongHieuExcelExporter extends AbstractExporter{

    private XSSFWorkbook workbook;
    private Sheet sheet;

    public ThuongHieuExcelExporter() {
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Thương Hiệu");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        cellStyle.setFont(font);

        createCell(row, 0, "Brands ID", cellStyle);
        createCell(row, 1, "Name", cellStyle);
        createCell(row, 2, "Description", cellStyle);
        createCell(row, 3, "Enabled", cellStyle);
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

    public void export(List<ThuongHieu> listThuongHieu, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/octet-stream", ".xlsx","brands_");

        writeHeaderLine();
        writeDataLines(listThuongHieu);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeDataLines(List<ThuongHieu> listThuongHieu) {
        int rowIndex = 1;

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);
        cellStyle.setFont(font);

        for (ThuongHieu thuongHieu : listThuongHieu) {
            Row row = sheet.createRow(rowIndex++);
            int columnIndex = 0;
            createCell(row, columnIndex++, thuongHieu.getIdThuongHieu(), cellStyle);
            createCell(row, columnIndex++, thuongHieu.getTenThuongHieu(), cellStyle);
            createCell(row, columnIndex++, thuongHieu.getMoTaThuongHieu(), cellStyle);
            createCell(row, columnIndex++, thuongHieu.isEnabled(), cellStyle);

        }
    }
}
