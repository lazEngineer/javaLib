package com.laz.lib.word.freemarker;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.TextRenderData;

public class POITLDemo {
	public static void main(String[] args) throws Exception {
		Map<String, Object> datas = new HashMap<String, Object>() {
			{

				put("author", new TextRenderData("000000", "Sayi"));
				// 文本模板
				put("date", "2015-04-01");

				// 表格模板
				put("changeLog", new TableRenderData(
						new ArrayList<RenderData>() {
							{
								add(new TextRenderData("d0d0d0", ""));
								add(new TextRenderData("d0d0d0", "introduce"));
							}
						}, new ArrayList<Object>() {
							{
								add("1;add new # gramer");
								add("2;support insert table");
								add("3;support more style");
							}
						}, "no datas", 10600));

				// 列表 1. 2. 3.
				put("number123", new NumbericRenderData(NumbericRenderData.FMT_DECIMAL,
						new ArrayList<TextRenderData>() {
							{
								add(new TextRenderData("df2d4f",
										"Deeply in love with the things you love, just deepoove."));
								add(new TextRenderData(
										"Deeply in love with the things you love, just deepoove."));
								add(new TextRenderData("5285c5",
										"Deeply in love with the things you love, just deepoove."));
							}
						}));

				// 图片模板
				put("logo", new PictureRenderData(100, 100,POITLDemo.class.getResource("logo.png").getPath()));
			}
		};

		// render
		XWPFTemplate template = XWPFTemplate.compile(POITLDemo.class.getResourceAsStream("PB.docx")).render(datas);

		// out document
		FileOutputStream out = new FileOutputStream("d://out.docx");
		template.write(out);
		template.close();
		out.close();
	}
}
