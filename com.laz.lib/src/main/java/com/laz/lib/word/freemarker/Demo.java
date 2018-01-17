package com.laz.lib.word.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.docx4j.model.datastorage.XPathEnhancerParser.main_return;

import sun.misc.BASE64Encoder;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class Demo {
	private Configuration configuration= null;
	
	public Demo() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
	}
	
	public void create() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", "张三");
		map.put("img", getImageBase64());
		configuration.setClassForTemplateLoading(this.getClass(), "./");
		
		Template template = configuration.getTemplate("1.xml");
		File outFile = new File("D:/export.doc");
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
		
		template.process(map, out);
	}
	
	private String getImageBase64() throws IOException {
		String imgFile = "d:/11.png";
		InputStream in = null;
		byte[] data = null;
		in = new FileInputStream(imgFile);
		data = new byte[in.available()];
		in.read(data);
		in.close();
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
		
	}
	
	public static void main(String[] args) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		new Demo().create();
	}
}
