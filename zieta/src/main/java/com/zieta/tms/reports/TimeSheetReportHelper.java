package com.zieta.tms.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.zieta.tms.dto.TimeSheetSumReportDTO;
import com.zieta.tms.model.TSReport;
import com.zieta.tms.model.TSSumReport;


@Component
public class TimeSheetReportHelper extends BaseHelper {
	
    private void writeDataLines(List<TSReport> tsReportList) {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)5);
        style.setFont(font);
                 
        font.setFontHeightInPoints((short)13);
        
        for (TSReport timeSheetReport : tsReportList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, timeSheetReport.getEmp_id(), style);
            createCell(row, columnCount++, timeSheetReport.getTeam_id(), style);
            createCell(row, columnCount++, timeSheetReport.getTeam(), style);
            createCell(row, columnCount++, timeSheetReport.getEmployee_name(), style);
            createCell(row, columnCount++, timeSheetReport.getProj_id(), style);
            createCell(row, columnCount++, timeSheetReport.getProject_name(), style);
            createCell(row, columnCount++, timeSheetReport.getTask_name(), style);
            createCell(row, columnCount++, timeSheetReport.getActivity_desc(), style);
            createCell(row, columnCount++, timeSheetReport.getPlanned_hours(), style);
            createCell(row, columnCount++, timeSheetReport.getActual_hours(), style);
            createCell(row, columnCount++, timeSheetReport.getTs_date(), style);
            createCell(row, columnCount++, timeSheetReport.getSubmit_date(), style);
            createCell(row, columnCount++, timeSheetReport.getSubmitted_hrs(), style);
            createCell(row, columnCount++, timeSheetReport.getApproved_hrs(), style);
             
        }
    }
     
    public ByteArrayInputStream downloadReport(HttpServletResponse response, List<TSReport> tsReportList) throws IOException {
        String[] columNames = {"EMP_ID", "TEAM_ID","TEAM","EMPLOYEE_NAME","PROJ_ID","PROJECT_NAME","TASK_NAME","ACTIVITY_DESC",
        		"PLANNED_HOURS","ACTUAL_HOURS","TS_DATE","SUBMIT_DATE","SUBMITTED_HRS","APPROVED_HRS"};
    	writeHeaderLine("TimeSheet Details",columNames);
        writeDataLines(tsReportList);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());
         
    } 
    
    
    
    private void writeSumDataLines(List<TSSumReport> tsReportList) {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)5);
        style.setFont(font);
                 
        font.setFontHeightInPoints((short)14);
        
        for (TSSumReport timeSheetReport : tsReportList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, timeSheetReport.getEmp_id(), style);
            createCell(row, columnCount++, timeSheetReport.getTeam_id(), style);
            createCell(row, columnCount++, timeSheetReport.getTeam(), style);
            createCell(row, columnCount++, timeSheetReport.getEmployee_name(), style);
            createCell(row, columnCount++, timeSheetReport.getProj_id(), style);
            createCell(row, columnCount++, timeSheetReport.getProject_name(), style);
            createCell(row, columnCount++, timeSheetReport.getTid(), style);
            createCell(row, columnCount++, timeSheetReport.getTask_name(), style);
            createCell(row, columnCount++, timeSheetReport.getActivity_desc(), style);
            createCell(row, columnCount++, timeSheetReport.getSubmitted_hrs(), style);
            createCell(row, columnCount++, timeSheetReport.getApproved_hrs(), style);
            createCell(row, columnCount++, timeSheetReport.getTotal_planned_hrs(), style);
            createCell(row, columnCount++, timeSheetReport.getTotal_actual_hrs(), style);
             
        }
    }
     
    public ByteArrayInputStream downloadSumReport(HttpServletResponse response, List<TSSumReport> tsReportList) throws IOException {
        String[] columNames = {"EMP_ID", "TEAM_ID","TEAM","EMP_NAME","PROJECT_ID","PROJECT_NAME","TID","TASK_NAME",
        		"ACTIVITY_DESC","SUBMITTED_HOURS","APPROVED_HOURS","TOTAL_PLANNED_HOURS","TOTAL_ACTUAL_HOURS"};
    	writeHeaderLine("TimeSheetSummary",columNames);
        writeSumDataLines(tsReportList);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());
         
    } 
    

}
