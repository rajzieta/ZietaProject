package com.zieta.tms.reports;

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
import org.springframework.stereotype.Component;

import com.zieta.tms.dto.TimeSheetReportDTO;
import com.zieta.tms.model.TSReport;
import com.zieta.tms.util.ReportUtil;


@Component
public class TimeSheetReportHelper {
	
	
	
	private XSSFWorkbook workbook;
    private XSSFSheet sheet;
     
  
 
    private void writeHeaderLine() {
    	workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("TimeSheet");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(13);
        style.setFont(font);
        

         
        createCell(row, 0, "EMP_ID", style);      
        createCell(row, 1, "TEAM", style);       
        createCell(row, 2, "EMP_NAME", style);    
        createCell(row, 3, "PROJECT_NAME", style);
        createCell(row, 4, "TASK_NAME", style);
        createCell(row, 5, "ACTIVITY_DESC", style);
        createCell(row, 6, "PLANNED_HOURS", style);
        createCell(row, 7, "ACTUAL_HOURS", style);
        createCell(row, 8, "TS_DATE", style);
        createCell(row, 9, "SUBMIT_DATE", style);
        createCell(row, 10, "SUBMITTED_HOURS", style);
        createCell(row, 11, "APPROVED_HOURS", style);
        
         
    }
     
    public  void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
     //   DecimalFormat df = new DecimalFormat("#.00");
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Float) {
            cell.setCellValue((Float) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeDataLines(List<TimeSheetReportDTO> tsReportList) {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(10);
        style.setFont(font);
                 
        CellStyle style2= ReportUtil.formatDecimalStyle(workbook);
        font.setFontHeight(14);
        style2.setFont(font);
        
        for (TimeSheetReportDTO timeSheetReport : tsReportList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, timeSheetReport.getEmp_id(), style);
            createCell(row, columnCount++, timeSheetReport.getTeam(), style);
            createCell(row, columnCount++, timeSheetReport.getEmp_name(), style);
            createCell(row, columnCount++, timeSheetReport.getProject_name(), style);
            createCell(row, columnCount++, timeSheetReport.getTask_name(), style);
            createCell(row, columnCount++, timeSheetReport.getActivity_desc(), style);
            createCell(row, columnCount++, timeSheetReport.getPlanned_hours(), style);
            createCell(row, columnCount++, timeSheetReport.getActual_hours(), style);
            createCell(row, columnCount++, timeSheetReport.getTs_date(), style);
            createCell(row, columnCount++, timeSheetReport.getSubmit_date(), style);
            createCell(row, columnCount++, timeSheetReport.getSubmitted_hours(), style);
            createCell(row, columnCount++, timeSheetReport.getApproved_hours(), style);
             
        }
    }
     
    public ByteArrayInputStream downloadReport(HttpServletResponse response, List<TimeSheetReportDTO> tsReportList) throws IOException {
        writeHeaderLine();
        writeDataLines(tsReportList);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());
         
    } 

}
