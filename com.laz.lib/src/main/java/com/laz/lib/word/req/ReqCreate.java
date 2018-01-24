package com.laz.lib.word.req;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.docx4j.Docx4J;
import org.docx4j.TextUtils;
import org.docx4j.XmlUtils;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFont;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.jaxb.Context;
import org.docx4j.model.fields.FieldUpdater;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.services.client.ConversionException;
import org.docx4j.utils.BufferUtil;
import org.docx4j.wml.Body;
import org.docx4j.wml.CTBorder;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Document;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.Hdr;
import org.docx4j.wml.HdrFtrRef;
import org.docx4j.wml.HeaderReference;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase.PBdr;
import org.docx4j.wml.PPrBase.Spacing;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.STBorder;
import org.docx4j.wml.STLineSpacingRule;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblBorders;
import org.docx4j.wml.TblPr;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Tr;
import org.jvnet.jaxb2_commons.ppp.Child;

import com.laz.lib.word.freemarker.Docx4JDemo;

/**
 * 需求文档生成测试方案
 * 
 * @author laz
 *
 */
public class ReqCreate {
	public ReqCreate() {

	}

	private static ObjectFactory objectFactory = new ObjectFactory();

	public void run() {
		try {
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
					.load(this.getClass().getResourceAsStream("需求分析报告模板.docx"));
			// WordprocessingMLPackage wordMLPackage =
			// WordprocessingMLPackage.createPackage();
			objectFactory = Context.getWmlObjectFactory();

			// OK, the guts of this sample:
			// The 2 things you need:
			// 1. the Header part
			// Relationship relationship = createHeaderPart(wordMLPackage);
			// // 2. an entry in SectPr
			// createHeaderReference(wordMLPackage, relationship);

			// createTable(wordMLPackage);

			Body body = wordMLPackage.getMainDocumentPart().getContents()
					.getBody();
			List<Object> list = body.getContent();
			List<Object> newList = new ArrayList<Object>();
			for (Object object : list) {
				if (object instanceof P) {
					P p = (P)object;
					StringWriter str = new StringWriter();
					TextUtils.extractText(p, str);
					System.out.println(str);
					System.out.println(p.toString());
					String paragraph = walkList(((org.docx4j.wml.P) object)
							.getContent());
					System.out.println(paragraph+"-------------------------");
				}
				newList.add(object);
//				newList.add(wordMLPackage.getMainDocumentPart()
//						.createParagraphOfText("哈哈"));

			}
			body.getContent().clear();
			body.getContent().addAll(newList);

			//wordMLPackage.save(new File("d:\\word\\需求报告.docx"));

			wordToPDF("d:\\word\\1.pdf", wordMLPackage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static String walkList(List children) {
		String line = "";
		for (Object o : children) {
			if (o instanceof javax.xml.bind.JAXBElement) {
				if (((JAXBElement) o).getDeclaredType().getName()
						.equals("org.docx4j.wml.Text")) {
					org.docx4j.wml.Text t = (org.docx4j.wml.Text) ((JAXBElement) o)
							.getValue();
					line = line + t.getValue();
				} else if (((JAXBElement) o).getDeclaredType().getName()
						.equals("org.docx4j.wml.Drawing")) {
					System.out.println("find img");
				}
				((JAXBElement) o).getValue();
			} else if (o instanceof org.w3c.dom.Node) {
				System.out.println(" IGNORED "
						+ ((org.w3c.dom.Node) o).getNodeName());
			} else if (o instanceof org.docx4j.wml.R) {
				RPr rPr = ((R) o).getRPr();
				if (null == rPr) {
					return null;
				}
				org.docx4j.wml.U u = rPr.getU();
				org.docx4j.wml.R run = (org.docx4j.wml.R) o;
				String tmpStr = walkList(run.getContent());
				if (u != null) {
					for (int i = 0; i < tmpStr.length(); i++) {
						line = line + "_" + tmpStr.charAt(i);
					}
				} else
					line = line + tmpStr;
			} else {
				System.out.println(" IGNORED " + o.getClass().getName());
			}
		}
		return line;
	}

	private void wordToPDF(String outputfilepath,
			WordprocessingMLPackage wordMLPackage) throws Exception {
		
		
		// Refresh the values of DOCPROPERTY fields
		Mapper fontMapper = new IdentityPlusMapper();
		Map<String, PhysicalFont>  m = PhysicalFonts.getPhysicalFonts();
		for (String str : m.keySet()) {
			PhysicalFont f = m.get(str);
			System.out.println("字体"+f.getEmbeddedFile()+" "+f.getName());
		}
		fontMapper.put("隶书", PhysicalFonts.get("LiSu"));
		fontMapper.put("宋体", PhysicalFonts.get("SimSun"));
		fontMapper.put("微软雅黑", PhysicalFonts.get("Microsoft Yahei"));
		fontMapper.put("黑体", PhysicalFonts.get("SimHei"));
		fontMapper.put("楷体", PhysicalFonts.get("KaiTi"));
		fontMapper.put("楷体_GB2312", PhysicalFonts.get("KaiTi_GB2312"));
		fontMapper.put("新宋体", PhysicalFonts.get("NSimSun"));
		fontMapper.put("华文行楷", PhysicalFonts.get("STXingkai"));
		fontMapper.put("华文仿宋", PhysicalFonts.get("STFangsong"));
		fontMapper.put("宋体扩展", PhysicalFonts.get("simsun-extB"));
		fontMapper.put("仿宋", PhysicalFonts.get("FangSong"));
		fontMapper.put("仿宋_GB2312", PhysicalFonts.get("FangSong_GB2312"));
		fontMapper.put("幼圆", PhysicalFonts.get("YouYuan"));
		fontMapper.put("华文宋体", PhysicalFonts.get("STSong"));
		fontMapper.put("华文中宋", PhysicalFonts.get("STZhongsong"));
		
		// For font specific, enable the below lines
		// PhysicalFont font = PhysicalFonts.getPhysicalFonts().get("Comic
		// Sans MS");
		// fontMapper.getFontMappings().put("Algerian", font);
		
		wordMLPackage.setFontMapper(fontMapper);

		FileOutputStream os = new java.io.FileOutputStream(outputfilepath);

		FOSettings foSettings = Docx4J.createFOSettings();
		foSettings.setWmlPackage(wordMLPackage);
		Docx4J.toFO(foSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
		// Docx4J.toFO(foSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
		if (wordMLPackage.getMainDocumentPart().getFontTablePart() != null) {
			wordMLPackage.getMainDocumentPart().getFontTablePart()
					.deleteEmbeddedFontTempFiles();
		}
	}

	private static List<Object> getAllElementFromObject(Object obj,
			Class<?> toSearch) {
		List<Object> result = new ArrayList<Object>();
		if (obj instanceof JAXBElement)
			obj = ((JAXBElement<?>) obj).getValue();
		if (obj.getClass().equals(toSearch))
			result.add(obj);
		else if (obj instanceof ContentAccessor) {
			List<?> children = ((ContentAccessor) obj).getContent();
			for (Object child : children) {
				result.addAll(getAllElementFromObject(child, toSearch));
			}
		}
		return result;
	}

	private void createTable(WordprocessingMLPackage wordMLPackage) {
		ObjectFactory factory = Context.getWmlObjectFactory();
		Tbl table = factory.createTbl();
		Tr tableRow = factory.createTr();
		Tc tableCell = factory.createTc();
		tableCell.getContent().add(
				wordMLPackage.getMainDocumentPart().createParagraphOfText(
						"Field 1"));
		tableRow.getContent().add(tableCell);
		table.getContent().add(tableRow);
		wordMLPackage.getMainDocumentPart().addObject(table);

		table.setTblPr(new TblPr());
		CTBorder border = new CTBorder();
		border.setColor("auto");
		border.setSz(new BigInteger("4"));
		TblBorders borders = new TblBorders();
		borders.setBottom(border);
		borders.setLeft(border);
		borders.setInsideV(border);
		table.getTblPr().setTblBorders(borders);
	}

	public static Relationship createHeaderPart(
			WordprocessingMLPackage wordprocessingMLPackage) throws Exception {

		HeaderPart headerPart = new HeaderPart();
		Relationship rel = wordprocessingMLPackage.getMainDocumentPart()
				.addTargetPart(headerPart);

		// After addTargetPart, so image can be added properly
		headerPart.setJaxbElement(getHdr(wordprocessingMLPackage, headerPart));

		return rel;
	}

	public static void createHeaderReference(
			WordprocessingMLPackage wordprocessingMLPackage,
			Relationship relationship) throws InvalidFormatException {

		List<SectionWrapper> sections = wordprocessingMLPackage
				.getDocumentModel().getSections();

		SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
		// There is always a section wrapper, but it might not contain a sectPr
		if (sectPr == null) {
			sectPr = objectFactory.createSectPr();
			wordprocessingMLPackage.getMainDocumentPart().addObject(sectPr);
			sections.get(sections.size() - 1).setSectPr(sectPr);
		}

		HeaderReference headerReference = objectFactory.createHeaderReference();
		headerReference.setId(relationship.getId());
		headerReference.setType(HdrFtrRef.DEFAULT);
		sectPr.getEGHdrFtrReferences().add(headerReference);// add header or
		// footer references

	}

	public static Hdr getHdr(WordprocessingMLPackage wordprocessingMLPackage,
			Part sourcePart) throws Exception {

		Hdr hdr = objectFactory.createHdr();
		File file = new File(Docx4JDemo.class.getResource("logo.png").getPath());
		java.io.InputStream is = new java.io.FileInputStream(file);

		hdr.getContent().add(
				newImage(wordprocessingMLPackage, sourcePart,
						BufferUtil.getBytesFromInputStream(is), "filename",
						"alttext", 1, 2));
		return hdr;

	}

	// public static P getP() {
	// P headerP = objectFactory.createP();
	// R run1 = objectFactory.createR();
	// Text text = objectFactory.createText();
	// text.setValue("123head123");
	// run1.getRunContent().add(text);
	// headerP.getParagraphContent().add(run1);
	// return headerP;
	// }

	public static org.docx4j.wml.P newImage(
			WordprocessingMLPackage wordMLPackage, Part sourcePart,
			byte[] bytes, String filenameHint, String altText, int id1, int id2)
			throws Exception {

		BinaryPartAbstractImage imagePart = BinaryPartAbstractImage
				.createImagePart(wordMLPackage, sourcePart, bytes);

		Inline inline = imagePart.createImageInline(filenameHint, altText, id1,
				id2, false);

		// Now add the inline in w:p/w:r/w:drawing
		org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory();
		org.docx4j.wml.P p = factory.createP();
		org.docx4j.wml.R run = factory.createR();
		p.getContent().add(run);
		org.docx4j.wml.Drawing drawing = factory.createDrawing();
		run.getContent().add(drawing);
		drawing.getAnchorOrInline().add(inline);

		return p;

	}

	// 图片页眉
	public Relationship createHeaderPart(
			WordprocessingMLPackage wordprocessingMLPackage,
			MainDocumentPart t, ObjectFactory factory, boolean isUnderLine,
			String underLineSize) throws Exception {
		HeaderPart headerPart = new HeaderPart();
		Relationship rel = t.addTargetPart(headerPart);
		// After addTargetPart, so image can be added properly
		headerPart.setJaxbElement(getHdr(wordprocessingMLPackage, factory,
				headerPart, isUnderLine, underLineSize));
		return rel;
	}

	public Hdr getHdr(WordprocessingMLPackage wordprocessingMLPackage,
			ObjectFactory factory, Part sourcePart, boolean isUnderLine,
			String underLineSize) throws Exception {
		Hdr hdr = factory.createHdr();
		File file = new File(Docx4JDemo.class.getResource("logo.png").getPath());
		java.io.InputStream is = new java.io.FileInputStream(file);
		hdr.getContent().add(
				newImage(wordprocessingMLPackage, factory, sourcePart,
						BufferUtil.getBytesFromInputStream(is), "filename",
						"这是页眉部分", 1, 2, isUnderLine, underLineSize,
						JcEnumeration.CENTER));
		return hdr;
	}

	public P newImage(WordprocessingMLPackage wordMLPackage,
			ObjectFactory factory, Part sourcePart, byte[] bytes,
			String filenameHint, String altText, int id1, int id2,
			boolean isUnderLine, String underLineSize,
			JcEnumeration jcEnumeration) throws Exception {
		BinaryPartAbstractImage imagePart = BinaryPartAbstractImage
				.createImagePart(wordMLPackage, sourcePart, bytes);
		Inline inline = imagePart.createImageInline(filenameHint, altText, id1,
				id2, false);
		P p = factory.createP();
		R run = factory.createR();
		p.getContent().add(run);
		Drawing drawing = factory.createDrawing();
		run.getContent().add(drawing);
		drawing.getAnchorOrInline().add(inline);
		PPr pPr = p.getPPr();
		if (pPr == null) {
			pPr = factory.createPPr();
		}
		Jc jc = pPr.getJc();
		if (jc == null) {
			jc = new Jc();
		}
		jc.setVal(jcEnumeration);
		pPr.setJc(jc);
		p.setPPr(pPr);

		if (isUnderLine) {
			PBdr pBdr = pPr.getPBdr();
			if (pBdr == null) {
				pBdr = factory.createPPrBasePBdr();
			}
			CTBorder value = new CTBorder();
			value.setVal(STBorder.SINGLE);
			value.setColor("000000");
			value.setSpace(new BigInteger("0"));
			value.setSz(new BigInteger(underLineSize));
			pBdr.setBetween(value);
			pPr.setPBdr(pBdr);
		}
		setParagraphSpacing(factory, p, jcEnumeration, true, "0", "0", null,
				null, true, "240", STLineSpacingRule.AUTO);
		return p;
	}

	/**
	 * @param jcEnumeration
	 *            对齐方式
	 * @param isSpace
	 *            是否设置段前段后值
	 * @param before
	 *            段前磅数
	 * @param after
	 *            段后磅数
	 * @param beforeLines
	 *            段前行数
	 * @param afterLines
	 *            段后行数
	 * @param isLine
	 *            是否设置行距
	 * @param lineValue
	 *            行距值
	 * @param sTLineSpacingRule
	 *            自动auto 固定exact 最小 atLeast
	 */
	public void setParagraphSpacing(ObjectFactory factory, P p,
			JcEnumeration jcEnumeration, boolean isSpace, String before,
			String after, String beforeLines, String afterLines,
			boolean isLine, String lineValue,
			STLineSpacingRule sTLineSpacingRule) {
		PPr pPr = p.getPPr();
		if (pPr == null) {
			pPr = factory.createPPr();
		}
		Jc jc = pPr.getJc();
		if (jc == null) {
			jc = new Jc();
		}
		jc.setVal(jcEnumeration);
		pPr.setJc(jc);

		Spacing spacing = new Spacing();
		if (isSpace) {
			if (before != null) {
				// 段前磅数
				spacing.setBefore(new BigInteger(before));
			}
			if (after != null) {
				// 段后磅数
				spacing.setAfter(new BigInteger(after));
			}
			if (beforeLines != null) {
				// 段前行数
				spacing.setBeforeLines(new BigInteger(beforeLines));
			}
			if (afterLines != null) {
				// 段后行数
				spacing.setAfterLines(new BigInteger(afterLines));
			}
		}
		if (isLine) {
			if (lineValue != null) {
				spacing.setLine(new BigInteger(lineValue));
			}
			spacing.setLineRule(sTLineSpacingRule);
		}
		pPr.setSpacing(spacing);
		p.setPPr(pPr);
	}

	public static void main(String[] args) {
		new ReqCreate().run();
	}

}
