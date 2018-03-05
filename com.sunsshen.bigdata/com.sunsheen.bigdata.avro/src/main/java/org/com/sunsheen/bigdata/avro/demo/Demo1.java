package org.com.sunsheen.bigdata.avro.demo;

import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.util.Utf8;

public class Demo1 {
	private Schema studentSchema;

	public Demo1() {
		try {
			studentSchema = new Parser().parse(Demo1.class
					.getResourceAsStream("student.avro"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		Demo1 d = new Demo1();
		d.init();
		d.print();
	}

	public void init() throws IOException {
		DataFileWriter<Record> writer = new DataFileWriter<Record>(
				new GenericDatumWriter<Record>(studentSchema)).create(
				studentSchema, new File("student.db"));
		try {
			writer.append(createStudent("Zhangsan", "law", "1534354656", 26));
			writer.append(createStudent("Lili", "sdfs", "1534354656", 23));
			writer.append(createStudent("wangwu", "4hfd", "1215656354", 25));
		} finally {
			writer.close();
		}
	}

	int SID = 0;

	/**
	 * 将信息写入到记录中（实体）
	 * 
	 * @param string
	 * @param string2
	 * @param string3
	 * @param i
	 * @return
	 */
	private Record createStudent(String name, String dept, String phone, int age) {
		Record student = new GenericData.Record(studentSchema);

		student.put("SID", (++SID));
		student.put("Name", new Utf8(name));
		student.put("Dept", new Utf8(dept));
		student.put("Phone", new Utf8(phone));
		student.put("Age", age);
		System.out.println("Successfully added " + name);
		return student;
	}

	/**
	 * 输出记录信息
	 * @throws IOException 
	 */
	public void print() throws IOException {
		GenericDatumReader<Record> dr = new GenericDatumReader<GenericData.Record>();
		dr.setExpected(studentSchema);
		
		DataFileReader<Record> reader = new DataFileReader<GenericData.Record>(new File("student.db"), dr);
		System.out.println("\nprint all the records from database");
		
		try {
			while(reader.hasNext()) {
				Record student = reader.next();
				System.out.println(student.get("SID").toString()+" "+student.get("Name")+
						" "+student.get("Dept")+" "+student.get("Phone")+" "+student.get("Age").toString());
				
			}
		}finally {
			reader.close();
		}
	}
}
