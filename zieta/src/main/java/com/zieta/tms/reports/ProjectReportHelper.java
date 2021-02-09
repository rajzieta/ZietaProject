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

import com.zieta.tms.model.ProjectDetailsReport;
import com.zieta.tms.model.ProjectSummaryReport;


@Component
public class ProjectReportHelper extends BaseHelper {
	
	
	
     
    private void writeProjectDetailsData(List<ProjectDetailsReport> projectList) {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)5);
        style.setFont(font);
                 
        font.setFontHeightInPoints((short)13);
        
        for (ProjectDetailsReport projectReport : projectList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            
            
            createCell(row, columnCount++, projectReport.getClient_code(), style);
            createCell(row, columnCount++, projectReport.getClient_name(), style);
            createCell(row, columnCount++, projectReport.getProject_code(), style);
            createCell(row, columnCount++, projectReport.getProject_name(), style);
            createCell(row, columnCount++, projectReport.getProject_manager(), style);
            createCell(row, columnCount++, projectReport.getPm_name(), style);
            createCell(row, columnCount++, projectReport.getUser_id(), style);
            createCell(row, columnCount++, projectReport.getUser_name(), style);
            createCell(row, columnCount++, projectReport.getEmp_id(), style);
            createCell(row, columnCount++, projectReport.getTs_id(), style);
            createCell(row, columnCount++, projectReport.getTs_date().toString(), style);
            createCell(row, columnCount++, projectReport.getSubmit_date(), style);
            createCell(row, columnCount++, projectReport.getTask_name(), style);
            createCell(row, columnCount++, projectReport.getTask_code(), style);
            createCell(row, columnCount++, projectReport.getActivity_code(), style);
            createCell(row, columnCount++, projectReport.getActivity_desc(), style);
            createCell(row, columnCount++, projectReport.getTimesheet_status(), style);
            createCell(row, columnCount++, projectReport.getTimesheet_status_desc(), style);
            createCell(row, columnCount++, projectReport.getTs_start_time(), style);
           createCell(row, columnCount++, projectReport.getTs_end_time(), style);
            createCell(row, columnCount++, projectReport.getTimeentry_duration(), style);
            createCell(row, columnCount++, projectReport.getTimeentry_desc(), style);
            createCell(row, columnCount++, projectReport.getTimetype_id(), style);
            createCell(row, columnCount++, projectReport.getTime_type(), style);
            createCell(row, columnCount++, projectReport.getTimeentry_status(), style);
            createCell(row, columnCount++, projectReport.getTimeentry_status_desc(), style);
            
            
        }
    }
     
    public ByteArrayInputStream downloadProjectDetailsReport(HttpServletResponse response, List<ProjectDetailsReport> projectList) throws IOException {
    	
    	String [] columnNames = {"Client Code","Client Name","Project Code","Project Name","Project Manager","PM Name","User ID",
    			"User Name","Emp Id","TS ID", "TS Date", "Submit Date", "Task Name", "Task Code", "Activity Code", "Activity Desc", 
    			"Timesheet Status", "TimesheetStatus Desc",  "Ts Start Time", "TS End Time", "TimeEntry Duration",  "TimeEntry Desc", 
    			"TimeType Id", "TimeType", "TimeEntry Status", "TimeEntry Status Desc"};
        writeHeaderLine( "ProjectDetailsReport",columnNames);
        writeProjectDetailsData(projectList);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());
         
    } 
    
    
    
    private void writeProjectSummaryData(List<ProjectSummaryReport> projectSummaryList) {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)5);
        style.setFont(font);
                 
        font.setFontHeightInPoints((short)13);
        
        for (ProjectSummaryReport projectSummaryReport : projectSummaryList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            
            
            createCell(row, columnCount++, projectSummaryReport.getEmp_id(), style);
            createCell(row, columnCount++, projectSummaryReport.getTeam_id(), style);
            createCell(row, columnCount++, projectSummaryReport.getTeam(), style);
            createCell(row, columnCount++, projectSummaryReport.getEmployee_name(), style);
            createCell(row, columnCount++, projectSummaryReport.getProj_id(), style);
            createCell(row, columnCount++, projectSummaryReport.getProject_name(), style);
            createCell(row, columnCount++, projectSummaryReport.getSubmitted_hrs(), style);
            createCell(row, columnCount++, projectSummaryReport.getApproved_hrs(), style);
            createCell(row, columnCount++, projectSummaryReport.getTotal_planned_hrs(), style);
            createCell(row, columnCount++, projectSummaryReport.getTotal_actual_hrs(), style);
        }
    }
     
    public ByteArrayInputStream downloadProjectSummaryReport(HttpServletResponse response, List<ProjectSummaryReport> projectSummaryList) throws IOException {
    	
    	String [] columnNames = {"EMP_ID","TEAM_ID","TEAM","EMPLOYEE_NAME","PROJ_ID","PROJECT_NAME","SUBMITTED_HRS",
    			"APPROVED_HRS","TOTAL_PLANNED_HRS","TOTAL_ACTUAL_HRS"};
        writeHeaderLine( "ProjectSummaryReport",columnNames);
        writeProjectSummaryData(projectSummaryList);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());
         
    } 
}
