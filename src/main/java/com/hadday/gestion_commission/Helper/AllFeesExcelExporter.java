package com.hadday.gestion_commission.Helper;

import com.hadday.gestion_commission.entities.AllFees;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class AllFeesExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<AllFees> listAllFees;

    public AllFeesExcelExporter(List<AllFees> listAllFees) {
        this.listAllFees = listAllFees;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("AllFees");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        style.setFont(font);

        createCell(row, 0, "COM_SEQ", style);
        createCell(row, 1, "FACT_NO", style);
        createCell(row, 2, "DATE", style);
        createCell(row, 3, "BPID_RECIPIENT", style);
        createCell(row, 4, "BPID_LIABLE", style);
        createCell(row, 5, "FEECATEGORY", style);
        createCell(row, 6, "FEETYPE", style);
        createCell(row, 7, "TRANSACTIONREFERENCE", style);
        createCell(row, 8, "TRANSACTIONTYPE", style);
        createCell(row, 9, "TRADEDATE", style);
        createCell(row, 10, "SETTLEMENTDATE", style);
        createCell(row, 11, "ACNO", style);
        createCell(row, 12, "ACCOUNTTYPE", style);
        createCell(row, 13, "ACCATEGORY", style);
        createCell(row, 14, "ISIN", style);
        createCell(row, 15, "ISIN_CREATIONDATE", style);
        createCell(row, 16, "INSTRUMENTTYPE", style);
        createCell(row, 17, "PRICE", style);
        createCell(row, 18, "FEEBASIS", style);
        createCell(row, 19, "AMOUNT", style);
        createCell(row, 20, "CURRENCY", style);
        createCell(row, 21, "BPID_AMC4MF", style);
        createCell(row, 22, "DC", style);
        createCell(row, 23, "CAPI", style);

    }


    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Timestamp) {
            cell.setCellValue((Timestamp) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (AllFees allFees : listAllFees) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            System.out.println(allFees);
            createCell(row, columnCount++, allFees.getCOM_SEQ(), style);
            createCell(row, columnCount++, allFees.getFACT_NO(), style);
            createCell(row, columnCount++, allFees.getDATE(), style);
            createCell(row, columnCount++, allFees.getBPID_RECIPIENT(), style);
            createCell(row, columnCount++, allFees.getBPID_LIABLE(), style);
            createCell(row, columnCount++, allFees.getFEECATEGORY(), style);
            createCell(row, columnCount++, allFees.getFEETYPE(), style);
            createCell(row, columnCount++, allFees.getTRANSACTIONREFERENCE(), style);
            createCell(row, columnCount++, allFees.getTRANSACTIONTYPE(), style);
            createCell(row, columnCount++, allFees.getTRADEDATE(), style);
            createCell(row, columnCount++, allFees.getSETTLEMENTDATE(), style);
            createCell(row, columnCount++, allFees.getACNO(), style);
            createCell(row, columnCount++, allFees.getACCOUNTTYPE(), style);
            createCell(row, columnCount++, allFees.getACCATEGORY(), style);
            createCell(row, columnCount++, allFees.getISIN(), style);
            createCell(row, columnCount++, allFees.getISIN_CREATIONDATE(), style);
            createCell(row, columnCount++, allFees.getINSTRUMENTTYPE(), style);
            createCell(row, columnCount++, allFees.getPRICE(), style);
            createCell(row, columnCount++, allFees.getFEEBASIS(), style);
            createCell(row, columnCount++, allFees.getAMOUNT(), style);
            createCell(row, columnCount++, allFees.getCURRENCY(), style);
            createCell(row, columnCount++, allFees.getBPID_AMC4MF(), style);
            createCell(row, columnCount++, allFees.getDC(), style);
            createCell(row, columnCount++, allFees.getCAPI(), style);
        }
    }


    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        }


}
