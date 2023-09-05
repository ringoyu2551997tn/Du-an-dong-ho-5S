package com.datn.dongho5s.Export;

import com.datn.dongho5s.Entity.ThuongHieu;
import com.datn.dongho5s.Entity.VatLieu;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class VatLieuExcelExporter extends AbstractExporter{
    private XSSFWorkbook workbook;
    private Sheet sheet;

    public VatLieuExcelExporter() {
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Vật Liệu");
        Row row = sheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        cellStyle.setFont(font);

        createCell(row, 0, "Material ID", cellStyle);
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

    public void export(List<VatLieu> listVatLieu, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/octet-stream", ".xlsx","material_");

        writeHeaderLine();
        writeDataLines(listVatLieu);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeDataLines(List<VatLieu> listVatLieu) {
        int rowIndex = 1;

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);
        cellStyle.setFont(font);

        for (VatLieu vatLieu : listVatLieu) {
            Row row = sheet.createRow(rowIndex++);
            int columnIndex = 0;
            createCell(row, columnIndex++, vatLieu.getIdVatLieu(), cellStyle);
            createCell(row, columnIndex++, vatLieu.getTenVatLieu(), cellStyle);
            createCell(row, columnIndex++, vatLieu.getMoTaVatLieu(), cellStyle);
            createCell(row, columnIndex++, vatLieu.isEnabled(), cellStyle);

        }
    }
}
