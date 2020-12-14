package com.zieta.tms.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zieta.tms.model.ProjectReport;
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
        createCell(row, 2, "Ts Date", style);    
        createCell(row, 3, "User Id", style);
        createCell(row, 4, "User Name", style);
        createCell(row, 5, "Emp Id", style);
        createCell(row, 6, "Project Id", style);
        createCell(row, 7, "Project Name", style);
        createCell(row, 8, "Task Name", style);
        createCell(row, 9, "Request Date", style);
        createCell(row, 10, "Action Date", style);
        createCell(row, 11, "State Name", style);
        createCell(row, 12, "Action Name", style);
        createCell(row, 13, "Approver Id", style);
        createCell(row, 14, "Approver Name", style);
        createCell(row, 15, "Submitted Hours", style);
        createCell(row, 16, "Approved Hours", style);
        createCell(row, 17, "Comments", style);
        
         
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
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
     
    private void writeDataLines( List<TimeSheetReport> timeSheetList) {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        CellStyle style2= formatDecimalStyle(workbook);
        font.setFontHeight(14);
        style2.setFont(font);
        
        for (TimeSheetReport timeSheetReport : timeSheetList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, timeSheetReport.getClientId().intValue(), style);
            createCell(row, columnCount++, timeSheetReport.getTsId().intValue(), style);
            createCell(row, columnCount++, timeSheetReport.getTsDate(), style);
            createCell(row, columnCount++, timeSheetReport.getUserId().intValue(), style);
            createCell(row, columnCount++, timeSheetReport.getUserName(), style);
            createCell(row, columnCount++, timeSheetReport.getEmpId(), style);
            createCell(row, columnCount++, timeSheetReport.getProjectId().intValue(), style);
            createCell(row, columnCount++, timeSheetReport.getProjectName(), style);
            createCell(row, columnCount++, timeSheetReport.getTaskName(), style);
            createCell(row, columnCount++, timeSheetReport.getRequestDate().toString(), style);
            createCell(row, columnCount++, timeSheetReport.getActionDate()!= null ?timeSheetReport.getActionDate().toString():StringUtils.EMPTY, style);
            createCell(row, columnCount++, timeSheetReport.getStateName(), style);
            createCell(row, columnCount++, timeSheetReport.getActionName(), style);
            createCell(row, columnCount++, timeSheetReport.getApproverId().intValue(), style);
            createCell(row, columnCount++, timeSheetReport.getApproverName(), style);
            createCell(row, columnCount++, timeSheetReport.getSubmittedHours().floatValue(), style2);
            createCell(row, columnCount++, timeSheetReport.getApprovedHours()!= null ?timeSheetReport.getApprovedHours().floatValue():StringUtils.EMPTY, style2);
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
    
    //Proj Repor
    
    public ByteArrayInputStream downloadProjReport(HttpServletResponse response, List<ProjectReport> projectList) throws IOException {
        writeHeaderLines();
        writeDataLiness(projectList);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());
         
    }
  
    private void writeDataLiness( List<ProjectReport> projectList) {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
       CellStyle style2= formatDecimalStyle(workbook);
       font.setFontHeight(14);
       style2.setFont(font);
                 
        for (ProjectReport projectReport : projectList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
         
            
         // BigDecimal bd= new BigDecimal(values);
            
            createCell(row, columnCount++, projectReport.getClientId().intValue(), style);
            createCell(row, columnCount++, projectReport.getClientCode(), style);
            createCell(row, columnCount++, projectReport.getClientName(), style);
            createCell(row, columnCount++, projectReport.getProjectId().intValue(), style);
            createCell(row, columnCount++, projectReport.getProjectCode(), style);
            createCell(row, columnCount++, projectReport.getProjectName(), style);
            createCell(row, columnCount++, projectReport.getProjectManager().intValue(), style);
            createCell(row, columnCount++, projectReport.getPmFname(), style);
            createCell(row, columnCount++, projectReport.getPmMname(), style);
            createCell(row, columnCount++, projectReport.getPmLname(), style);
            createCell(row, columnCount++, projectReport.getUserId().intValue(), style);
            createCell(row, columnCount++, projectReport.getUserFname(), style);
            createCell(row, columnCount++, projectReport.getUserMname(), style);
            createCell(row, columnCount++, projectReport.getUserLname(), style);
            createCell(row, columnCount++, projectReport.getEmpId(), style);
            createCell(row, columnCount++, projectReport.getTsId().intValue(), style);
            createCell(row, columnCount++, projectReport.getTsDate().toString(), style);
      //      createCell(row, columnCount++, projectReport.getRequestDate().toString(), style);
            createCell(row, columnCount++, projectReport.getTaskId().intValue(), style);
            createCell(row, columnCount++, projectReport.getTaskName(), style);
            createCell(row, columnCount++, projectReport.getTaskCode(), style);
            createCell(row, columnCount++, projectReport.getActivityId().intValue(), style);
            createCell(row, columnCount++, projectReport.getActivityCode(), style);
            createCell(row, columnCount++, projectReport.getActivityDesc(), style);
            createCell(row, columnCount++, projectReport.getTimesheetStatus().intValue(), style);
            createCell(row, columnCount++, projectReport.getTimesheetStatusDesc(), style);
            createCell(row, columnCount++, projectReport.getTsStartTime()!=null?projectReport.getTsStartTime().floatValue():StringUtils.EMPTY, style2);
           createCell(row, columnCount++, projectReport.getTsEndTime()!= null ?projectReport.getTsEndTime().floatValue():StringUtils.EMPTY, style2);
            createCell(row, columnCount++, projectReport.getTimeEntryDuration()!= null ?projectReport.getTimeEntryDuration().floatValue():StringUtils.EMPTY, style2);
            createCell(row, columnCount++, projectReport.getTimeentryDesc(), style);
            createCell(row, columnCount++, projectReport.getTimetypeId().intValue(), style);
            createCell(row, columnCount++, projectReport.getTimeType(), style);
            createCell(row, columnCount++, projectReport.getTimeentryStatus().intValue(), style);
            createCell(row, columnCount++, projectReport.getTimeentryStatusDesc(), style);
            
            
        }
    }
    
    public static CellStyle formatDecimalStyle(Workbook workbook) {
    	
    	CellStyle style1 = workbook.createCellStyle();
    	style1.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
    	return style1;
    	
    }
    

	private void writeHeaderLines() {
    	workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("TimeSheet");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "Client ID", style);     
        createCell(row, 1, "Client Code", style);   
        createCell(row, 2, "Client Name", style);   
        createCell(row, 3, "Project Id", style); 
        createCell(row, 4, "Project Name", style);
        createCell(row, 5, "Project Code", style);  
        createCell(row, 6, "Project Manager", style);   
        createCell(row, 7, "PM FName", style);
        createCell(row, 8, "PM Middle Name", style);
        createCell(row, 9, "PM Last Name", style);     
        createCell(row, 10, "User ID", style);    
        createCell(row, 11, "User First Name", style);
        createCell(row, 12, "User Middle Name", style);
        createCell(row, 13, "User Last Name", style);
        createCell(row, 14, "Emp Id", style);
        createCell(row, 15, "TS ID", style); 
        createCell(row, 16, "TS Date", style); 
     //   createCell(row, 17, "Request Date", style);
        createCell(row, 17, "Task Id", style);
        createCell(row, 18, "Task Name", style);
        createCell(row, 19, "Task Code", style);
        createCell(row, 20, "Activity Id", style);
        createCell(row, 21, "Activity Code", style);
        createCell(row, 22, "Activity Desc", style);
        createCell(row, 23, "Timesheet Status", style);
        createCell(row, 24, "TimesheetStatus Desc", style);
        createCell(row, 25, "Ts Start Time", style);
        createCell(row, 26, "TS End Time", style);
        createCell(row, 27, "TimeEntry Duration", style);
        createCell(row, 28, "TimeEntry Desc", style);
        createCell(row, 29, "TimeType Id", style);
        createCell(row, 30, "TimeType", style);
        createCell(row, 31, "TimeEntry Status", style);
        createCell(row, 32, "TimeEntry Status Desc", style);
        
         
    }
    
    
    
    
}