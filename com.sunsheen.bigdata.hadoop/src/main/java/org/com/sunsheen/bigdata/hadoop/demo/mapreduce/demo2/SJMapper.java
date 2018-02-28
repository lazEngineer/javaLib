package org.com.sunsheen.bigdata.hadoop.demo.mapreduce.demo2;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SJMapper extends Mapper<LongWritable, Text, Text, Text> {
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		String childName = new String();
		String parentName = new String();
		String relationType = new String();
		String line = value.toString();
		String[] values = line.split("\\s");
		System.out.println(line);
		if (values[0].compareTo("child") != 0) {
			childName = values[0];
			parentName = values[1];
			
			//左表
			relationType = "1";
			context.write(new Text(values[1]), new Text(relationType + "+"
					+ childName + "+" + parentName));
			
			//右表
			relationType = "2";
			context.write(new Text(values[0]), new Text(relationType + "+"
					+ childName + "+" + parentName));
		}
	}
}
