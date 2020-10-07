package com.zieta.tms.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zieta.tms.model.TimeSheetReport;
 
public class ReportUtil {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
     
  
 
    private void writeHeaderLine() {
    	workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("TimeSheet");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "Client ID", style);      
        createCell(row, 1, "TS ID", style);       
        createCell(row, 2, "User ID", style);    
        createCell(row, 3, "User First Name", style);
        createCell(row, 4, "User Middle Name", style);
        createCell(row, 5, "User Last Name", style);
        createCell(row, 6, "Emp Id", style);
        createCell(row, 7, "Project Id", style);
        createCell(row, 8, "Project Name", style);
        createCell(row, 9, "Task Name", style);
        createCell(row, 10, "Request Date", style);
        createCell(row, 11, "Action Date", style);
        createCell(row, 12, "State Name", style);
        createCell(row, 13, "Action Name", style);
        createCell(row, 14, "Approver Id", style);
        createCell(row, 15, "Approver First Name", style);
        createCell(row, 16, "Approver Middle Name", style);
        createCell(row, 17, "Approver Last Name", style);
        createCell(row, 18, "Comments", style);
        
         
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeDataLines( List<TimeSheetReport> timeSheetList) {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        for (TimeSheetReport timeSheetReport : timeSheetList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, timeSheetReport.getClientId().intValue(), style);
            createCell(row, columnCount++, timeSheetReport.getTsId().intValue(), style);
            createCell(row, columnCount++, timeSheetReport.getUserId().intValue(), style);
            createCell(row, columnCount++, timeSheetReport.getUserFname(), style);
            createCell(row, columnCount++, timeSheetReport.getUserMname(), style);
            createCell(row, columnCount++, timeSheetReport.getUserLname(), style);
            createCell(row, columnCount++, timeSheetReport.getEmpId(), style);
            createCell(row, columnCount++, timeSheetReport.getProjectId().intValue(), style);
            createCell(row, columnCount++, timeSheetReport.getProjectName(), style);
            createCell(row, columnCount++, timeSheetReport.getTaskName(), style);
            createCell(row, columnCount++, timeSheetReport.getRequestDate().toString(), style);
            createCell(row, columnCount++, timeSheetReport.getActionDate().toString(), style);
            createCell(row, columnCount++, timeSheetReport.getStateName(), style);
            createCell(row, columnCount++, timeSheetReport.getActionName(), style);
            createCell(row, columnCount++, timeSheetReport.getApproverId().intValue(), style);
            createCell(row, columnCount++, timeSheetReport.getApproverFname(), style);
            createCell(row, columnCount++, timeSheetReport.getApproverMname(), style);
            createCell(row, columnCount++, timeSheetReport.getApproverLname(), style);
            createCell(row, columnCount++, timeSheetReport.getComments(), style);
             
        }
    }
     
    public ByteArrayInputStream downloadReport(HttpServletResponse response, List<TimeSheetReport> timeSheetList) throws IOException {
        writeHeaderLine();
        writeDataLines(timeSheetList);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());
         
    }
}