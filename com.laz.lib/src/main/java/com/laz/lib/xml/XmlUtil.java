package com.laz.lib.xml;

import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.laz.demo.velocity.VelocitySimple;

public class XmlUtil {
	public static Document parseText(String text) throws DocumentException,
			SAXException {
		Document result = null;

		SAXReader reader = new SAXReader();

		reader.setValidation(false);
		// reader.setFeature(
		// "http://apache.org/xml/features/nonvalidating/load-external-dtd",
		// false);
		String encoding = "utf-8";

		InputSource source = new InputSource(new StringReader(text));
		source.setEncoding(encoding);
		result = reader.read(source);
		if (result.getXMLEncoding() == null) {
			result.setXMLEncoding(encoding);
		}
		return result;
	}
	
	public static void main(String[] args) throws IOException, DocumentException, SAXException {
		System.out.println(XmlUtil.class.getResource("/xml/1.xml"));
		String xmlStr = IOUtils.toString(XmlUtil.class.getResourceAsStream("/xml/1.xml"));
		System.out.println(xmlStr);
		parseText(xmlStr);
	}
}
