package org.com.sunsheen.bigdata.hadoop.test.unit;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.com.sunsheen.bigdata.hadoop.test.mapreduce.WCMapper;
import org.com.sunsheen.bigdata.hadoop.test.mapreduce.WCReducer;
import org.junit.Test;

public class WordCountReduceTest {
	@Test
	public void returnsSumValues() throws IOException {
		new ReduceDriver<Text, LongWritable, Text, LongWritable>()
		.withReducer(new WCReducer())
		.withInput(new Text("a"), Arrays.asList(new LongWritable(10),new LongWritable(15)))
		.withOutput(new Text("a"),new LongWritable(25))
		.runTest();
	}
}
