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
import com.zieta.tms.util.ReportUtil;

@Component
public class LeaveReportHelper {

	private Workbook workbook;
	private Sheet sheet;

	private void writeHeaderLine() {
		workbook = new SXSSFWorkbook(1000);
		sheet = workbook.createSheet("Leave Report");
		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 13);
		style.setFont(font);

		createCell(row, 0, "CLIENT NAME", style);
		createCell(row, 1, "USER NAME", style);
		createCell(row, 2, "LEAVE DESC", style);
		createCell(row, 3, "LEAVE TYPE", style);
		createCell(row, 4, "LEAVE START DATE", style);
		createCell(row, 5, "START SESSION", style);
		createCell(row, 6, "LEAVE END DATE", style);
		createCell(row, 7, "END SESSION", style);
		createCell(row, 8, "APPROVER NAME", style);
		createCell(row, 9, "APPROVER COMMENTS", style);
		createCell(row, 10, "STATUS", style);

	}

	public void createCell(Row row, int columnCount, Object value, CellStyle style) {

		Cell cell = row.createCell(columnCount);

		cell.setCellValue((String) value);
		cell.setCellStyle(style);
	}

	private void writeDataLines(List<LeaveReportDTO> leaveInfoList) {
		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 5);
		style.setFont(font);

		CellStyle style2 = ReportUtil.formatDecimalStyle(workbook);
		font.setFontHeightInPoints((short) 13);
		style2.setFont(font);

		for (LeaveReportDTO leaveReport : leaveInfoList) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

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
		writeHeaderLine();
		writeDataLines(leaveInfoList);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		workbook.write(out);
		workbook.close();
		return new ByteArrayInputStream(out.toByteArray());

	}

}
