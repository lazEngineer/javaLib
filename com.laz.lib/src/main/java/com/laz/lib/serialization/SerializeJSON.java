package com.laz.lib.serialization;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class SerializeJSON {
	public static void main(String[] args) {
		serializeToJSON();
		readJSON();
	}
	
	public static void serializeToJSON() {
		Person p = new Person();
		p.setName("xx");
		
		XStream xS = new XStream(new JettisonMappedXmlDriver());
		xS.setMode(XStream.NO_REFERENCES);
		xS.alias("Person", Person.class);
		String a= xS.toXML(p);
		System.out.println(a+"-----");
	}
	
	public static void readJSON() {
		
		XStream xS = new XStream(new JettisonMappedXmlDriver());
		xS.setMode(XStream.NO_REFERENCES);
		xS.alias("Person", Person.class);
		Person  p = (Person)xS.fromXML("{'Person':{'name':'xx','age':0}}");
		System.out.println(p.getName());
	}
}
