package com.laz.lib.word.freemarker;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;

/**
 * 创建word文档 步骤: 1,建立文档 2,创建一个书写器 3,打开文档 4,向文档中写入数据 5,关闭文档
 */
public class WordDemo {

	public WordDemo() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List tableDatas = new ArrayList();
		int[] i = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		tableDatas = Arrays.asList(i);
		String[] columns = new String[] { "项目类别", "实施方案", "规划环评", "大纲+报告书",
				"仅大纲", "报告书", "报告表", "补充报告", "补充报告书", "补充报告表" };

		new WordDemo().itextCreateTable(tableDatas, columns, "ddd");
	}

	/**
	 * word创建表单
	 *
	 * @param tableDatas真实数据集合
	 * @param columns标题
	 * @param imagePath文件地址
	 * @param wordFileName
	 *            文档名字
	 */
	public String itextCreateTable(List tableDatas, String[] columns,
			String wordFileName) {
		boolean bool = true;
		try {
			// 把文件放到桌面上
			File desktopDir = FileSystemView.getFileSystemView()
					.getHomeDirectory();
			String desktopPath = desktopDir.getAbsolutePath();
			Document document = new Document(PageSize.A4.rotate());
			RtfWriter2.getInstance(document, new FileOutputStream(desktopPath
					+ "\\" + wordFileName + ".pdf"));
			document.open();
			// 创建多个表格
			Table aTable = new Table(columns.length);
			for (String s : columns) {
				// 把表格上方的标题创建出来
				Cell cell = new Cell(s);
				cell.setColspan(2);
				cell.setBackgroundColor(Color.red);
				aTable.addCell(cell);
			}
			// 把数据填写到表格中，只要够了表格数量会自动换行
			for (Object tableData : tableDatas) {
				aTable.addCell(new Cell(tableData.toString()));
			}
			document.add(aTable);
			document.add(new Paragraph("\n"));
			document.close();
		} catch (Exception e) {
			bool = false;
			e.printStackTrace();
		}
		if (bool) {
			return "导出成功(在桌面)";
		} else {
			return "请关闭word文档，再重新导出";
		}
	}

}
