package com.zieta.tms.reports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class BaseHelper {

	protected Workbook workbook;
	protected Sheet sheet;

	public BaseHelper() {
		super();
	}

	public void createCell(Row row, int columnCount, Object value, CellStyle style) {
	    
	    Cell cell = row.createCell(columnCount);

		cell.setCellValue((String) value);
		cell.setCellStyle(style);
	}
	
	public void writeHeaderLine(String sheetName, String[] columnNames) {
		workbook = new SXSSFWorkbook(1000);
		sheet = workbook.createSheet(sheetName);

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 13);
		style.setFont(font);

		for (int i = 0; i < columnNames.length; i++) {
			createCell(row, i, columnNames[i], style);
		}

	}

}