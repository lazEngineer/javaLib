package com.laz.lib.classloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.docx4j.model.datastorage.XPathEnhancerParser.main_return;

public class ClassReloader extends ClassLoader{
	private String classPath;
	String classname = "Message";
	
	public ClassReloader(String classPath) {
		this.classPath = classPath;
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classData = getData(name);
		if (classData == null) {
			throw new ClassNotFoundException();
		}else {
			return defineClass(classname, classData, 0,classData.length);
		}
		
	}

	private byte[] getData(String className) {
		String path = classPath+className;
		try {
			InputStream is = new FileInputStream(path);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			byte[] buffer = new byte[2048];
			int num = 0;
			while ((num = is.read(buffer)) != -1) {
				stream.write(buffer,0,num);
			}
			return stream.toByteArray();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
			String path = "d:/";
			ClassReloader reloader = new ClassReloader(path);
			Class r = reloader.findClass("Message.class");
			System.out.println(r.newInstance());
			
			String path2 = "e:/";
			ClassReloader reloader2 = new ClassReloader(path2);
			Class r2 = reloader2.findClass("Message.class");
			
			System.out.println(r2.newInstance());
			
	}
}
