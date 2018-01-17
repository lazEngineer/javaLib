package com.laz.test.word.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

public class Test {
	static void mergeCellVertically(XWPFTable table, int col, int fromRow,
			int toRow) {
		for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
			CTVMerge vmerge = CTVMerge.Factory.newInstance();
			if (rowIndex == fromRow) {
				// The first merged cell is set with RESTART merge value
				vmerge.setVal(STMerge.RESTART);
			} else {
				// Cells which join (merge) the first one, are set with CONTINUE
				vmerge.setVal(STMerge.CONTINUE);
			}
			XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
			// Try getting the TcPr. Not simply setting an new one every time.
			CTTcPr tcPr = cell.getCTTc().getTcPr();
			if (tcPr != null) {
				tcPr.setVMerge(vmerge);
			} else {
				// only set an new TcPr if there is not one already
				tcPr = CTTcPr.Factory.newInstance();
				tcPr.setVMerge(vmerge);
				cell.getCTTc().setTcPr(tcPr);
			}
		}
	}

	static void mergeCellHorizontally(XWPFTable table, int row, int fromCol,
			int toCol) {
		for (int colIndex = fromCol; colIndex <= toCol; colIndex++) {
			CTHMerge hmerge = CTHMerge.Factory.newInstance();
			if (colIndex == fromCol) {
				// The first merged cell is set with RESTART merge value
				hmerge.setVal(STMerge.RESTART);
			} else {
				// Cells which join (merge) the first one, are set with CONTINUE
				hmerge.setVal(STMerge.CONTINUE);
			}
			XWPFTableCell cell = table.getRow(row).getCell(colIndex);
			// Try getting the TcPr. Not simply setting an new one every time.
			CTTcPr tcPr = cell.getCTTc().getTcPr();
			if (tcPr != null) {
				tcPr.setHMerge(hmerge);
			} else {
				// only set an new TcPr if there is not one already
				tcPr = CTTcPr.Factory.newInstance();
				tcPr.setHMerge(hmerge);
				cell.getCTTc().setTcPr(tcPr);
			}
		}
	}

	public static void main(String[] args) throws Exception {

		// write();
		read();

	}

	private static void read() {
		try {
			FileInputStream in = new FileInputStream(
					"C:\\Users\\lz578\\Desktop\\1.docx");// 载入文档
			XWPFDocument doc = new XWPFDocument(in);
			List<XWPFParagraph> paras = doc.getParagraphs();
			for (XWPFParagraph para : paras) {
				// 当前段落的属性
				// CTPPr pr = para.getCTP().getPPr();
				System.out.println(para.getText());
			}
			// 获取文档中所有的表格
			List<XWPFTable> tables = doc.getTables();
			List<XWPFTableRow> rows;
			List<XWPFTableCell> cells;
			for (XWPFTable table : tables) {
				// 表格属性
				// CTTblPr pr = table.getCTTbl().getTblPr();
				// 获取表格对应的行
				rows = table.getRows();
				for (XWPFTableRow row : rows) {
					// 获取行对应的单元格
					cells = row.getTableCells();
					for (XWPFTableCell cell : cells) {
						CTTcPr tcPr = cell.getCTTc().getTcPr();
						System.out.print(cell.getText() + "\t");
						if (tcPr.getVMerge()!=null) {
							System.out.println(tcPr.getVMerge().getVal()+"hang");
						}
						if (tcPr.getGridSpan()!= null){
							System.out.print(tcPr.getGridSpan().getVal()+" ");
						}

					}
					System.out.println("");
				}
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void write() {
		XWPFDocument document = new XWPFDocument();

		XWPFParagraph paragraph = document.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.setText("The table:");

		// create table
		XWPFTable table = document.createTable(3, 5);

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 5; col++) {
				table.getRow(row).getCell(col)
						.setText("row " + row + ", col " + col);
			}
			
			
		}

		// create and set column widths for all columns in all rows
		// most examples don't set the type of the CTTblWidth but this
		// is necessary for working in all office versions
		for (int col = 0; col < 5; col++) {
			CTTblWidth tblWidth = CTTblWidth.Factory.newInstance();
			tblWidth.setW(BigInteger.valueOf(1000));
			tblWidth.setType(STTblWidth.DXA);
			for (int row = 0; row < 3; row++) {
				CTTcPr tcPr = table.getRow(row).getCell(col).getCTTc()
						.getTcPr();
				if (tcPr != null) {
					tcPr.setTcW(tblWidth);
				} else {
					tcPr = CTTcPr.Factory.newInstance();
					tcPr.setTcW(tblWidth);
					table.getRow(row).getCell(col).getCTTc().setTcPr(tcPr);
				}
			}
		}

		// using the merge methods
		mergeCellVertically(table, 0, 0, 1);
		mergeCellHorizontally(table, 1, 2, 3);
		mergeCellHorizontally(table, 2, 1, 4);

		paragraph = document.createParagraph();

		FileOutputStream out;
		try {
			out = new FileOutputStream("d:\\create_table.docx");
			try {
				document.write(out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("create_table.docx written successully");

	}
}
