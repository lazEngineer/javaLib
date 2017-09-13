package com.laz.lib.less;

import com.asual.lesscss.LessEngine;
import com.asual.lesscss.LessException;

public class LessCompileTest {
	public static void main(String[] args) throws LessException {
		// Instantiates a new LessEngine
		LessEngine engine = new LessEngine();

		// Compiles a CSS string
		String text = engine.compile("div { width: 1 + 1 }");
		
		System.out.println(text);
//		// Compiles an URL resource
		System.out.println(LessCompileTest.class.getResource("test.less"));
	String url = engine.compile(LessCompileTest.class.getResource("test.less"));
	System.out.println(url);
//
//		// Creates a new file containing the compiled content
//		engine.compile(new File("/Users/User/Projects/styles.less"), 
//		               new File("/Users/User/Projects/styles.css"));
	}
}
