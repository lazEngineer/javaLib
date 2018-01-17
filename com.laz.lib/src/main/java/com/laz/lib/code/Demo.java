package com.laz.lib.code;

import org.docx4j.model.datastorage.XPathEnhancerParser.main_return;

public class Demo {
	public static void main(String[] args) {
		encode();
	}

	private static void encode() {
		String name = "I am 君山";
		toHex(name.toCharArray());
		try {
			byte[] iso8859 = name.getBytes("ISO-8859-1");
			toHex(iso8859);
			byte[] gb2312 = name.getBytes("GB2312");
			toHex(gb2312);
			byte[] gbk = name.getBytes("GBK");
			toHex(gbk);
			byte[] utf16 = name.getBytes("UTF-16");
			toHex(utf16);
			byte[] utf8 = name.getBytes("UTF-8");
			toHex(utf8);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void toHex(byte[] b) {
		for (byte c : b) {
			System.out.print(c);
		}
		System.out.println();
	}

	private static void toHex(char[] c) {
		for (char d : c) {
			System.out.print(d+"=");
		}
		System.out.println();
	}
}
