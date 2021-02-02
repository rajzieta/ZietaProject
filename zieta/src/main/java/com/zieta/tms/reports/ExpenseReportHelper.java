package com.zieta.tms.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;

import com.zieta.tms.dto.TimeSheetSumReportDTO;
import com.zieta.tms.model.ExpenseDetailsReport;
import com.zieta.tms.model.ExpenseSummaryReport;
import com.zieta.tms.util.ReportUtil;


@Component
public class ExpenseReportHelper {
	
	
	
	private Workbook workbook;
    private Sheet sheet;
     
  
    /**
     * This method generate the header row for the Expense Details
     */
    private void writeHeaderLine(String sheetTitle, String[] columnNames) {
    	workbook = new SXSSFWorkbook(1000);
        sheet = workbook.createSheet(sheetTitle);
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short)13);
        style.setFont(font);
        
        for (int i = 0; i < columnNames.length; i++) {
        	createCell(row, i, columnNames[i], style);
		}
    }
     
    public  void createCell(Row row, int columnCount, Object value, CellStyle style) {

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
     
    private void writeDataLines(List<ExpenseDetailsReport> expenseDetailsList) {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)5);
        style.setFont(font);
                 
        CellStyle style2= ReportUtil.formatDecimalStyle(workbook);
        font.setFontHeightInPoints((short)13);
        style2.setFont(font);
        
        for (ExpenseDetailsReport expenseDetail : expenseDetailsList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, expenseDetail.getExp_id(), style);
            createCell(row, columnCount++, expenseDetail.getExp_entry_id(), style);
            createCell(row, columnCount++, expenseDetail.getExp_date(), style);
            createCell(row, columnCount++, expenseDetail.getExp_type(), style);
            createCell(row, columnCount++, expenseDetail.getExpense_type(), style);
            createCell(row, columnCount++, expenseDetail.getExp_currency(), style);
            createCell(row, columnCount++, expenseDetail.getCurrency_code(), style);
            createCell(row, columnCount++, expenseDetail.getExp_amount(), style);
            createCell(row, columnCount++, expenseDetail.getExp_country(), style);
            createCell(row, columnCount++, expenseDetail.getCountry_name(), style);
            createCell(row, columnCount++, expenseDetail.getExp_city(), style);
            createCell(row, columnCount++, expenseDetail.getExchange_rate(), style);
            createCell(row, columnCount++, expenseDetail.getExp_amt_inr(), style);
            createCell(row, columnCount++, expenseDetail.getExp_desc(), style);
            createCell(row, columnCount++, expenseDetail.getStatus_id(), style);
            createCell(row, columnCount++, expenseDetail.getStatus_desc(), style);
            createCell(row, columnCount++, expenseDetail.getFile_name(), style);
            createCell(row, columnCount++, expenseDetail.getFile_path(), style);
            createCell(row, columnCount++, expenseDetail.getUser_id(), style);
            createCell(row, columnCount++, expenseDetail.getEmp_id(), style);
            createCell(row, columnCount++, expenseDetail.getEmp_name(), style);
            createCell(row, columnCount++, expenseDetail.getExp_category(), style);
            createCell(row, columnCount++, expenseDetail.getProject_id(), style);
            createCell(row, columnCount++, expenseDetail.getProject_name(), style);
            createCell(row, columnCount++, expenseDetail.getOrgunit_id(), style);
            createCell(row, columnCount++, expenseDetail.getOrg_node_name(), style);
            createCell(row, columnCount++, expenseDetail.getExp_heading(), style);
             
        }
    }
     
    public ByteArrayInputStream downloadReport(HttpServletResponse response, List<ExpenseDetailsReport> expenseDetailsList) throws IOException {
        String[] columnNames = {"EXP_ID","EXP_ENTRY_ID","EXP_DATE","EXP_TYPE","EXPENSE_TYPE","EXP_CURRENCY",
        		"CURRENCY_CODE","EXP_AMOUNT","EXP_COUNTRY","COUNTRY_NAME"," EXP_CITY","EXCHANGE_RAT","EXP_AMT_INR",
        		"EXP_DESC","STATUS_ID","STATUS_DESC","FILE_NAME","FILE_PATH","USER_ID","EMP_ID","EMP_NAME","EXP_CATEGORY","PROJECT_ID",
        		"PROJECT_NAME","ORGUNIT_ID","ORG_NODE_NAME","EXP_HEADING"};
    	
    	writeHeaderLine("Expense details", columnNames);
        
        writeDataLines(expenseDetailsList);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());
         
    } 
   
    
    private void writeExpenseSummaryLines(List<ExpenseSummaryReport> expenseSummaryReportList) {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)5);
        style.setFont(font);
                 
        CellStyle style2= ReportUtil.formatDecimalStyle(workbook);
        font.setFontHeightInPoints((short)14);
        style2.setFont(font);
        
        for (ExpenseSummaryReport expenseSummaryReport : expenseSummaryReportList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, expenseSummaryReport.getExp_id(), style);
            createCell(row, columnCount++, expenseSummaryReport.getUser_id(), style);
            createCell(row, columnCount++, expenseSummaryReport.getEmployee_name(), style);
            createCell(row, columnCount++, expenseSummaryReport.getExp_category(), style);
            createCell(row, columnCount++, expenseSummaryReport.getProject_id(), style);
            createCell(row, columnCount++, expenseSummaryReport.getProject_name(), style);
            createCell(row, columnCount++, expenseSummaryReport.getOrgunit_id(), style);
            createCell(row, columnCount++, expenseSummaryReport.getOrg_node_name(), style);
            createCell(row, columnCount++, expenseSummaryReport.getExp_heading(), style);
            createCell(row, columnCount++, expenseSummaryReport.getExp_start_date(), style);
            createCell(row, columnCount++, expenseSummaryReport.getExp_end_date(), style);
            createCell(row, columnCount++, expenseSummaryReport.getExp_posting_date(), style);
            createCell(row, columnCount++, expenseSummaryReport.getExp_amount(), style);
            createCell(row, columnCount++, expenseSummaryReport.getApproved_amt(), style);
            createCell(row, columnCount++, expenseSummaryReport.getApprover_id(), style);
            createCell(row, columnCount++, expenseSummaryReport.getApprover_name(), style);
            createCell(row, columnCount++, expenseSummaryReport.getStatus_id(), style);
            createCell(row, columnCount++, expenseSummaryReport.getStatus_desc(), style);
             
        }
    }
     
    public ByteArrayInputStream downloadSumReport(HttpServletResponse response, List<ExpenseSummaryReport> expenseSummaryReportList) throws IOException {
        String[] columNames = {"EXP_ID","USER_ID","EMPLOYEE_NAME","EXP_CATEGORY","PROJECT_ID","PROJECT_NAME","ORGUNIT_ID","ORG_NODE_NAME","EXP_HEADING",
        		"EXP_START_DATE","EXP_END_DATE","EXP_POSTING_DATE","EXP_AMOUNT","APPROVED_AMT","APPROVER_ID","APPROVER_NAME","STATUS_ID",
        		"STATUS_DESC"};
    	writeHeaderLine("Expense Summary",columNames);
    	writeExpenseSummaryLines(expenseSummaryReportList);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());
         
    } 
    

}
