package org.com.sunsheen.bigdata.avro.demo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import org.apache.avro.Schema;
import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class Demo2 {
	static class Writer {
		public static final String FIELD_CONTENTS = "contents";
		public static final String FIELD_FILENAME = "filename";
		public static final String SCHEMA_JSON = "{\"type\": \"record\",\"name\": \"SmallFilesTest\", "
				+ "\"fields\": ["
				+ "{\"name\":\""
				+ FIELD_FILENAME
				+ "\",\"type\":\"string\"},"
				+ "{\"name\":\""
				+ FIELD_CONTENTS
				+ "\", \"type\":\"bytes\"}]}";
		public static final Schema SCHEMA = new Schema.Parser()
				.parse(SCHEMA_JSON);

		public static void writeToAvro(File srcPath, OutputStream outputStream)
				throws IOException {
			DataFileWriter<Object> writer = new DataFileWriter<Object>(
					new GenericDatumWriter<Object>()).setSyncInterval(100);
			writer.setCodec(CodecFactory.snappyCodec());
			writer.create(SCHEMA, outputStream);
			for (Object obj : FileUtils.listFiles(srcPath, null, false)) {
				File file = (File) obj;
				String filename = file.getAbsolutePath();
				byte content[] = FileUtils.readFileToByteArray(file);
				GenericRecord record = new GenericData.Record(SCHEMA);
				record.put(FIELD_FILENAME, filename);
				record.put(FIELD_CONTENTS, ByteBuffer.wrap(content));
				writer.append(record);
				  System.out.println(file.getAbsolutePath() + ":"+ DigestUtils.md5Hex(content));  
			}
			IOUtils.cleanup(null, writer);
			IOUtils.cleanup(null, outputStream);
		}

	}

	public static void main(String args[]) throws Exception {
		write();
		read();
	}

	private static void read() throws IOException, InterruptedException,
			URISyntaxException {
		Configuration config = new Configuration();
		FileSystem hdfs = FileSystem.get(new URI("hdfs://172.18.130.100:8020"),
				config, "hdfs");
		Path destFile = new Path("/testData/fileW");
		InputStream is = hdfs.open(destFile);
		new Reader().readFromAvro(is);

	}

	static class Reader {
		private static final String FIELD_FILENAME = "filename";
		private static final String FIELD_CONTENTS = "contents";

		public static void readFromAvro(InputStream is) throws IOException {
			DataFileStream<Object> reader = new DataFileStream<Object>(is,
					new GenericDatumReader<Object>());
			for (Object o : reader) {
				GenericRecord r = (GenericRecord) o;
				System.out.println(r.get(FIELD_FILENAME)
						+ ":"
						+ DigestUtils.md5Hex(((ByteBuffer) r
								.get(FIELD_CONTENTS)).array()));
			}
			IOUtils.cleanup(null, is);
			IOUtils.cleanup(null, reader);
		}

	}

	private static void write() throws IOException, InterruptedException,
			URISyntaxException {
		Configuration config = new Configuration();
		FileSystem hdfs = FileSystem.get(new URI("hdfs://172.18.130.100:8020"),
				config, "hdfs");
		File sourceDir = new File("d:\\test\\hdfsData");
		Path destFile = new Path("/testData/fileW");
		OutputStream os = hdfs.create(destFile);
		new Writer().writeToAvro(sourceDir, os);

	}
}
