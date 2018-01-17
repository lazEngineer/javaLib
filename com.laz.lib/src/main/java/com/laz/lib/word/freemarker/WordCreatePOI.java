package com.laz.lib.word.freemarker;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.docx4j.model.datastorage.XPathEnhancerParser.main_return;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

public class WordCreatePOI {
	public static void main(String[] args) {
		new WordCreatePOI().createWord2007();
	}
	/**
	 * 2007word文档创建
	 */
	@Test
	public void createWord2007() {
		XWPFDocument doc = new XWPFDocument();
		XWPFParagraph p1 = doc.createParagraph();

		XWPFTable table = doc.createTable(11, 4);
		// CTTblBorders borders=table.getCTTbl().getTblPr().addNewTblBorders();
		CTTblPr tblPr = table.getCTTbl().getTblPr();
		tblPr.getTblW().setType(STTblWidth.DXA);
		tblPr.getTblW().setW(new BigInteger("7000"));

		// 设置上下左右四个方向的距离，可以将表格撑大
		table.setCellMargins(20, 20, 20, 20);

		// 表格
		List<XWPFTableCell> tableCells = table.getRow(0).getTableCells();

		XWPFTableCell cell = tableCells.get(0);
		XWPFParagraph newPara = new XWPFParagraph(cell.getCTTc().addNewP(),
				cell);
		XWPFRun run = newPara.createRun();
		/** 内容居中显示 **/
		newPara.setAlignment(ParagraphAlignment.CENTER);
		// run.getCTR().addNewRPr().addNewColor().setVal("FF0000");/**FF0000红色*/
		// run.setUnderline(UnderlinePatterns.THICK);
		run.setText("第一 数据");

		tableCells.get(1).setText("第一 数据");
		tableCells.get(2).setText("第一 据");
		tableCells.get(3).setText("第 据");

		tableCells = table.getRow(1).getTableCells();
		tableCells.get(0).setText("第数据");
		tableCells.get(1).setText("第一 数据");
		tableCells.get(2).setText("第一 据");
		tableCells.get(3).setText("第 据");

		// 设置字体对齐方式
		p1.setAlignment(ParagraphAlignment.CENTER);
		p1.setVerticalAlignment(TextAlignment.TOP);

		// 第一页要使用p1所定义的属性
		XWPFRun r1 = p1.createRun();

		// 设置字体是否加粗
		r1.setBold(true);
		r1.setFontSize(20);

		// 设置使用何种字体
		r1.setFontFamily("Courier");

		// 设置上下两行之间的间距
		r1.setTextPosition(20);
		r1.setText("标题");

		FileOutputStream out;
		try {
			out = new FileOutputStream("d:/word2007.docx");
			// 以下代码可进行文件下载
			// response.reset();
			// response.setContentType("application/x-msdownloadoctet-stream;charset=utf-8");
			// response.setHeader("Content-Disposition",
			// "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8"));
			// OutputStream out = response.getOutputStream();
			// this.doc.write(out);
			// out.flush();

			doc.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("success");
	}
}
