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

import com.zieta.tms.dto.LeaveReportDTO;

@Component
public class LeaveReportHelper extends BaseHelper{

	

	private void writeDataLines(List<LeaveReportDTO> leaveInfoList) {
		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 5);
		style.setFont(font);

		font.setFontHeightInPoints((short) 13);

		for (LeaveReportDTO leaveReport : leaveInfoList) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			
			createCell(row, columnCount++, leaveReport.getLeaveId(), style);
			createCell(row, columnCount++, leaveReport.getClientName(), style);
			createCell(row, columnCount++, leaveReport.getUserName(), style);
			createCell(row, columnCount++, leaveReport.getLeaveDesc(), style);
			createCell(row, columnCount++, leaveReport.getLeaveType(), style);
			createCell(row, columnCount++, leaveReport.getLeaveStartDate(), style);
			createCell(row, columnCount++, leaveReport.getStartSession(), style);
			createCell(row, columnCount++, leaveReport.getLeaveEndDate(), style);
			createCell(row, columnCount++, leaveReport.getEndSession(), style);
			createCell(row, columnCount++, leaveReport.getApproverName(), style);
			createCell(row, columnCount++, leaveReport.getApproverComments(), style);
			createCell(row, columnCount++, leaveReport.getStatus(), style);

		}
	}

	public ByteArrayInputStream downloadReport(HttpServletResponse response, List<LeaveReportDTO> leaveInfoList)
			throws IOException {
		String [] columNames = {"LEAVE ID", "CLIENT NAME","USER NAME","LEAVE DESC","LEAVE TYPE",
				"LEAVE START DATE","START SESSION","LEAVE END DATE","END SESSION", "APPROVER NAME",  "APPROVER COMMENTS", "STATUS"};
		writeHeaderLine("Leave Report",columNames);
		writeDataLines(leaveInfoList);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		workbook.write(out);
		workbook.close();
		return new ByteArrayInputStream(out.toByteArray());

	}

}
