package com.laz.lib.word.req;

import java.io.File;
import java.math.BigInteger;

import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.Body;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.CTBookmark;
import org.docx4j.wml.Color;
import org.docx4j.wml.HpsMeasure;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase;
import org.docx4j.wml.ParaRPr;
import org.docx4j.wml.R;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.STHint;
import org.docx4j.wml.STLineSpacingRule;
import org.docx4j.wml.Text;
import org.docx4j.wml.U;
import org.docx4j.wml.UnderlineEnumeration;
import org.docx4j.wml.PPrBase.Ind;
import org.docx4j.wml.PPrBase.Spacing;
import org.junit.Test;

public class DocxUtil {
	private static ObjectFactory objectFactory = new ObjectFactory();

	/**
	 * 创建字体
	 * 
	 * @param isBlod
	 *            粗体
	 * @param isUnderLine
	 *            下划线
	 * @param isItalic
	 *            斜体
	 * @param isStrike
	 *            删除线
	 */
	public static RPr getRPr(ObjectFactory factory, String fontFamily,
			String colorVal, String fontSize, STHint sTHint, boolean isBlod,
			boolean isUnderLine, boolean isItalic, boolean isStrike) {
		RPr rPr = factory.createRPr();
		RFonts rf = new RFonts();
		rf.setHint(sTHint);
		rf.setAscii(fontFamily);
		rf.setHAnsi(fontFamily);
		rPr.setRFonts(rf);

		BooleanDefaultTrue bdt = factory.createBooleanDefaultTrue();
		rPr.setBCs(bdt);
		if (isBlod) {
			rPr.setB(bdt);
		}
		if (isItalic) {
			rPr.setI(bdt);
		}
		if (isStrike) {
			rPr.setStrike(bdt);
		}
		if (isUnderLine) {
			U underline = new U();
			underline.setVal(UnderlineEnumeration.SINGLE);
			rPr.setU(underline);
		}

		Color color = new Color();
		color.setVal(colorVal);
		rPr.setColor(color);

		HpsMeasure sz = new HpsMeasure();
		sz.setVal(new BigInteger(fontSize));
		rPr.setSz(sz);
		rPr.setSzCs(sz);
		return rPr;
	}

	/**
	 * 创建二级标题
	 */
	@Test
	public void test() {
		try {
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
					.createPackage();
			Body body = wordMLPackage.getMainDocumentPart().getContents()
					.getBody();
			objectFactory = Context.getWmlObjectFactory();
			P p = objectFactory.createP();

			// {
			// PPr pr = objectFactory.createPPr();
			// p.setPPr(pr);
			// PPrBase.PStyle style = new PPrBase.PStyle();
			// style.setVal("2");
			// pr.setPStyle(style);
			//
			// PPrBase.Spacing space = new PPrBase.Spacing();
			// space.setLine(BigInteger.valueOf(360L));
			// space.setLineRule(STLineSpacingRule.AUTO);
			// pr.setSpacing(space);
			//
			// ParaRPr rpr = objectFactory.createParaRPr();
			//
			// RFonts fonts = new RFonts();
			// fonts.setCs("Arial");
			// rpr.setRFonts(fonts);
			//
			//
			//
			// HpsMeasure hps = new HpsMeasure();
			// hps.setVal(BigInteger.valueOf(28L));
			// rpr.setSz(hps);
			//
			// rpr.setSzCs(hps);
			// pr.setRPr(rpr);
			//
			// CTBookmark ct = objectFactory.createCTBookmark();
			// ct.setId(BigInteger.valueOf(0));
			// ct.setName("_Toc499828824");
			//
			// CTBookmark ct2 = objectFactory.createCTBookmark();
			// ct2.setId(BigInteger.valueOf(1));
			// ct2.setName("_GoBack");
			// body.getContent().add(p);
			// body.getContent().add(ct);
			// }

			{
				
				R r = objectFactory.createR();
				RPr rpr = getRPr(objectFactory, "宋体", "000000", "28",
						STHint.EAST_ASIA, true, false, false, false);

				Text t = objectFactory.createText();
				t.setValue("项目涉及的用户");
				r.getContent().add(t);
				r.setRPr(rpr);
				p.getContent().add(r);
			}
			body.getContent().add(p);

			wordMLPackage.save(new File("d:\\word\\demo\\test1.xml"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 创建一级标题
	 */
	@Test
	public void test2() {
		try {
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
					.createPackage();
			Body body = wordMLPackage.getMainDocumentPart().getContents()
					.getBody();
			objectFactory = Context.getWmlObjectFactory();
			P p = objectFactory.createP();

			{
				R r = objectFactory.createR();
				RPr rpr = getRPr(objectFactory, "宋体", "000000", "32",
						STHint.EAST_ASIA, true, false, false, false);
				Text t = objectFactory.createText();
				t.setValue("业务流程现状描述与问题分析");
				r.getContent().add(t);
				r.setRPr(rpr);
				p.getContent().add(r);
			}
			body.getContent().add(p);

			wordMLPackage.save(new File("d:\\word\\demo\\test2.xml"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 创建普通段落文本
	 */
	@Test
	public void test3() {
		try {
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
					.createPackage();
			Body body = wordMLPackage.getMainDocumentPart().getContents()
					.getBody();
			RPr fontRPr = getRPr(objectFactory, "宋体", "000000", "22",
					STHint.EAST_ASIA, false, false, false, false);

			objectFactory = Context.getWmlObjectFactory();
			P paragraph = objectFactory.createP();
			R run = objectFactory.createR();
			Text txt = objectFactory.createText();
			
			setParagraphSpacing(objectFactory, paragraph, JcEnumeration.LEFT, true, "0",
					"0", null, null, true, "240", STLineSpacingRule.AUTO);
			setParagraphInd(objectFactory, paragraph, JcEnumeration.LEFT, true, "200",
					false, null);
			txt.setValue("当前的培训管理系统涉及多个业务，针对“实现培训过程管理信息化”这一业务目标，我们分析得出3个具体的业务。第一个业务是培训资料库建设，此业务主要保证培训资料库能顺利完整的建设；第二个业务是实现培训过程信息化，此业务主要保证培训过程规范化、信息化。第三个业务目标是实现培训数据多角色、多维度统计分析与查询，此业务主要保证不同的角色能够查看与其相关的培训信息，同时提供不同维度的培训数据统计，以及培训资料的下载。");
			run = objectFactory.createR();
			run.getContent().add(txt);
			run.setRPr(fontRPr);
			paragraph.getContent().add(run);
			body.getContent().add(paragraph);

			wordMLPackage.save(new File("d:\\word\\demo\\test3.xml"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// 设置缩进 同时设置为true,则为悬挂缩进
		public void setParagraphInd(ObjectFactory factory, P p,
				JcEnumeration jcEnumeration, boolean firstLine,
				String firstLineValue, boolean hangLine, String hangValue) {
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

			Ind ind = pPr.getInd();
			if (ind == null) {
				ind = new Ind();
			}
			if (firstLine) {
				if (firstLineValue != null) {
					ind.setFirstLineChars(new BigInteger(firstLineValue));
				}
			}
			if (hangLine) {
				if (hangValue != null) {
					ind.setHangingChars(new BigInteger(hangValue));
				}
			}
			pPr.setInd(ind);
			p.setPPr(pPr);
		}
	// 设置段间距-->行距 段前段后距离
		// 段前段后可以设置行和磅 行距只有磅
		// 段前磅值和行值同时设置，只有行值起作用
		// TODO 1磅=20 1行=100 单倍行距=240 为什么是这个值不知道
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
}
