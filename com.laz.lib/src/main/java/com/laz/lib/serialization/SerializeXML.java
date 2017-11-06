package com.laz.lib.serialization;

import com.thoughtworks.xstream.XStream;

public class SerializeXML {
	public static void main(String[] args) {
		serializeToXml();
	}
	
	public static void serializeToXml() {
		Person p = new Person();
		p.setName("xx");
		
		XStream xS = new XStream();
		xS.alias("Person", Person.class);
		String a= xS.toXML(p);
		System.out.println(a+"-----");
	}
}
