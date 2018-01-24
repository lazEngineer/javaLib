package com.laz.lib.word.freemarker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.Filetype;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class WordGenerator {
	private static Configuration configuration = null;

	public static Configuration getConfiguration() {
	        configuration = new Configuration(Configuration.VERSION_2_3_23);
	        try {
	            configuration.setDirectoryForTemplateLoading(new File(WordGenerator.class.getResource("temp.xml").getPath()).getParentFile());
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	        configuration.setDefaultEncoding("utf-8");
	        configuration.setLogTemplateExceptions(false);
	        return configuration;
	    }

	/**
	 * fileName 你要输出的文件名 dataMap ftl 需要显示的值 name ftl名称 ps： test.ftl
	 **/
	public static File createDoc(String fileName,Map<?, ?> dataMap,String name) {
		configuration = getConfiguration();
		Writer w = null;
		File f = new File(fileName);
		FileOutputStream fos = null;
		try {
			Template t = configuration.getTemplate(name);
			fos = new FileOutputStream(f);
			w = new OutputStreamWriter(fos, "utf-8");
			t.process(dataMap, w);
			w.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// TODO
		} finally {
			IOUtils.closeQuietly(w);
			IOUtils.closeQuietly(fos);
		}
		return f;
	}
	
	public static void main(String[] args) throws Docx4JException, IOException {
		/**ftl 中出现的${value} 这样的值 表现为dataMap中的key value则为你要显示的值**/
		Map<String,Object> dataMap = new HashMap<>();
		dataMap.put("name","测试");

		File resumeFile = WordGenerator.createDoc("d:\\222.xml",dataMap,"temp.xml");
		System.out.println("000000000");
		//这样你就能得到你想要的xml
		//这个xml可以用office word 打开，但是如果你想得到docx的文件，你可能会直接把xml重命名为xx.docx 但事实是 你这样做了之后，office word打不开提示错误那么接下来的操作比较重要
		WordprocessingMLPackage wmlPackage = (WordprocessingMLPackage) WordprocessingMLPackage.load(new FileInputStream(resumeFile));
		File file = new File("d:\\test.docx");
		try {
			wmlPackage.save(file, Docx4J.FLAG_SAVE_ZIP_FILE);
		} catch (Docx4JException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
