package com.laz.lib.js;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.junit.Test;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

public class TestJsZip {
	static File dir = new File("D:\\learn\\source\\java\\com.laz.lib\\src\\test\\java\\com\\laz\\lib\\js\\login.js");
	int linebreakpos =5000;
	boolean munge = true;
	boolean verbose = true;
	boolean preserveAllSemiColons = true;
	boolean disableOptimizations = true;

//	@Test
//	public void testMain() throws Exception {
//		checkFile(dir);
//	}
	public static void main(String[] args) throws Exception {
		new TestJsZip().checkFile(new File("D:\\learn\\source\\java\\com.laz.lib\\src\\test\\java\\com\\laz\\lib\\js\\pdf.js"));
//		File dir = new File("D:\\learn\\source\\java\\com.laz.lib\\src\\test\\java\\com\\laz\\lib\\js\\login.js");
//		File dir2 = new File("D:\\learn\\source\\java\\com.laz.lib\\src\\test\\java\\com\\laz\\lib\\js\\login_c.js");
//		FileReader in = new FileReader(dir);
//		FileWriter out = new FileWriter(dir2);
//		new TestJsZip().compressor(in,out);
//		in.close();
//		out.close();
	}

	public void checkFile(File file) throws Exception {
		if (file.getName().endsWith(".svn"))
			return;
		if (file.isFile()) {
			jsZip(file);
			return;
		}
		File[] files = file.listFiles();
		if (files == null || files.length == 0)
			return;
		for (File f : files) {
			if (file.getName().endsWith(".svn"))
				return;
			if (file.isFile()) {
				jsZip(file);
				continue;
			}
			checkFile(f);
		}
	}
	/**
	 * 压缩脚本
	 * @param isr
	 * @param osw
	 * @throws EvaluatorException
	 * @throws IOException
	 */
	public  void compressor(Reader isr,Writer osw) throws EvaluatorException, IOException {
		JavaScriptCompressor jscompressor = new JavaScriptCompressor(isr,
				new ErrorReporter() {
					public void warning(String message, String sourceName,
							int line, String lineSource, int lineOffset) {
						if (line < 0) {
							System.err.println("\n[WARNING] " + message);
						} else {
							System.err.println("\n[WARNING] " + line + ':'
									+ lineOffset + ':' + message);
						}
					}

					public void error(String message, String sourceName,
							int line, String lineSource, int lineOffset) {
						if (line < 0) {
							System.err.println("\n[ERROR] " + message);
						} else {
							System.err.println("\n[ERROR] " + line + ':'
									+ lineOffset + ':' + message);
						}
					}

					public EvaluatorException runtimeError(String message,
							String sourceName, int line, String lineSource,
							int lineOffset) {
						error(message, sourceName, line, lineSource, lineOffset);
						return new EvaluatorException(message);
					}
				});
		jscompressor.compress(osw, linebreakpos, munge, verbose,
				preserveAllSemiColons, disableOptimizations);
	}
	public void jsZip(File file) throws Exception {
		String fileName = file.getName();
		System.out.println(fileName);
		if (fileName.endsWith(".js") == false
				&& fileName.endsWith(".css") == false) {
			return;
		}
		
		Reader in = new FileReader(file);
		String filePath = file.getAbsolutePath();
		File tempFile = new File(filePath + ".c.js");
		Writer out = new FileWriter(tempFile);
		
		if (fileName.endsWith(".js")) {
			compressor(in, out);
//			JavaScriptCompressor jscompressor = new JavaScriptCompressor(in,
//					new ErrorReporter() {
//						public void warning(String message, String sourceName,
//								int line, String lineSource, int lineOffset) {
//							if (line < 0) {
//								System.err.println("\n[WARNING] " + message);
//							} else {
//								System.err.println("\n[WARNING] " + line + ':'
//										+ lineOffset + ':' + message);
//							}
//						}
//
//						public void error(String message, String sourceName,
//								int line, String lineSource, int lineOffset) {
//							if (line < 0) {
//								System.err.println("\n[ERROR] " + message);
//							} else {
//								System.err.println("\n[ERROR] " + line + ':'
//										+ lineOffset + ':' + message);
//							}
//						}
//
//						public EvaluatorException runtimeError(String message,
//								String sourceName, int line, String lineSource,
//								int lineOffset) {
//							error(message, sourceName, line, lineSource,
//									lineOffset);
//							return new EvaluatorException(message);
//						}
//					});
//			jscompressor.compress(out, linebreakpos, munge, verbose,
//					preserveAllSemiColons, disableOptimizations);
		} else if (fileName.endsWith(".css")) {
			CssCompressor csscompressor = new CssCompressor(in);
			csscompressor.compress(out, linebreakpos);
		}
		
		//必须关闭流
		out.close();
		in.close();
		//file.delete();
		//tempFile.renameTo(file);
		//tempFile.delete();
	}
}
