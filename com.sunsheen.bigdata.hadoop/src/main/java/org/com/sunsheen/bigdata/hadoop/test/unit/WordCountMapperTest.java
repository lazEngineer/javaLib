package org.com.sunsheen.bigdata.hadoop.test.unit;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.com.sunsheen.bigdata.hadoop.test.mapreduce.WCMapper;
import org.junit.Test;

public class WordCountMapperTest {
	@Test
	public void processesValidRecord() throws IOException {
		new MapDriver<LongWritable, Text, Text, LongWritable>().withMapper(new WCMapper())
		.withInput(new LongWritable(1), new Text("a b"))
		.withOutput(new Text("a"),new LongWritable(1))
		.withOutput(new Text("b"),new LongWritable(1))
		.runTest();
	}
}
