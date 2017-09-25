package com.laz.test;

import java.util.ArrayList;

public class TestOOM {
	static class Obj {
		public byte[] bytes = "hello everyone".getBytes();
		
	}
	
	public static void  main(String[] args) {
		String s = "1  4		rr";
		System.out.println(s.split("\\s+"));
	}
}
