package org.com.sunsheen.bigdata.avro.demo;

import java.io.File;
import java.util.Random;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.util.Utf8;

public class GenerateData {
	public static final String[] COLORS = { "red", "orange", "yellow", "green",
			"blue", "purple", null };
	public static final int USERS = 100;
	public static final String PATH = "demo3.db";
	private static Schema schema;

	public static void main(String[] args) throws Exception {
		schema = new Parser().parse(Demo1.class
				.getResourceAsStream("demo3.avsc"));
		System.out.println(GenerateData.class.getResource("").getPath()+"/"+PATH);
		DataFileWriter<Record> writer = new DataFileWriter<Record>(
				new GenericDatumWriter<Record>(schema)).create(schema,
				new File(GenerateData.class.getResource("").getPath()+"/"+PATH));
		try {
			Random random = new Random();
			for (int i = 0; i < USERS; i++) {
				writer.append(createData("user", 0,
						COLORS[random.nextInt(COLORS.length)/6]));
			}
		} finally {
			writer.close();
		}
	}

	private static Record createData(String name, int favorite_number,
			String favorite_color) {
		Record r = new GenericData.Record(schema);

		r.put("name", name);
		r.put("favorite_number", favorite_number);
		r.put("favorite_color", new Utf8(favorite_color));
		return r;
	}
}
