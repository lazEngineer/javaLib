package com.laz.test.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	public static void main(String[] args) throws IOException {
		InputStream is = new FileInputStream(new File(
				"C:\\Users\\lz578\\Desktop\\1.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(is);
		Map<Integer, Map<Integer, Object>> content = new HashMap<Integer, Map<Integer, Object>>();

		XSSFSheet sh = wb.getSheetAt(0);
		Map<Integer, Map<Integer, Object>> data = new HashMap<Integer, Map<Integer, Object>>();
		// 得到总行数
		int rowNum = sh.getLastRowNum();
		XSSFRow row = sh.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		for (int i = 0; i < rowNum; i++) {
			row = sh.getRow(i);  
			Map<Integer, Object> params = new HashMap<Integer, Object>();
			for (int j = 0; j < colNum; j++) {
				row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
				params.put(j, row.getCell(j).getStringCellValue());
			}
			data.put(i, params);
		}
		System.out.println(data);
	}
}
