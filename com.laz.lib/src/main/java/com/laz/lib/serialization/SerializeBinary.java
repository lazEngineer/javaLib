package com.laz.lib.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class SerializeBinary {
	public static void main(String[] args) {
		//serializeToBinary();
		readBinary();
	}
	
	public static void serializeToBinary() {
		Person p = new Person();
		p.setName("xx");
		try {
			FileOutputStream fos = new FileOutputStream(new File("d:/1.txt"));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(p);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static void readBinary() {
		try {
			FileInputStream fis = new FileInputStream(new File("d:/1.txt"));
			ObjectInputStream in = new ObjectInputStream(fis);
			Person p = (Person) in.readObject();
			System.out.println(p.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
