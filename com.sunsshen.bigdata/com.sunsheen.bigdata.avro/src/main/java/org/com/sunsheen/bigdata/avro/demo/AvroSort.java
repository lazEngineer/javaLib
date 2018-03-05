package org.com.sunsheen.bigdata.avro.demo;

import java.io.File;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapreduce.AvroJob;
import org.apache.avro.mapreduce.AvroKeyInputFormat;
import org.apache.avro.mapreduce.AvroKeyOutputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class AvroSort implements Tool {
	@Override
	public int run(String[] args) throws Exception {

		String schemaFile = "demo3.avsc";

		Job job = Job.getInstance(new Configuration());
		job.setJarByClass(getClass());

		job.getConfiguration().setBoolean(
				Job.MAPREDUCE_JOB_USER_CLASSPATH_FIRST, true);
		FileInputFormat.setInputPaths(job, new Path("d:/test/hdfsData/demo3.db"));
		FileOutputFormat.setOutputPath(job, new Path("d:/test/hdfsData/avroData"));

		AvroJob.setDataModelClass(job, GenericData.class);

		Schema schema = new Schema.Parser().parse(AvroSort.class.getResourceAsStream(schemaFile));
		AvroJob.setInputKeySchema(job, schema);
		AvroJob.setMapOutputKeySchema(job, schema);
		AvroJob.setMapOutputValueSchema(job, schema);
		AvroJob.setOutputKeySchema(job, schema);

		job.setInputFormatClass(AvroKeyInputFormat.class);
		job.setOutputFormatClass(AvroKeyOutputFormat.class);

		job.setOutputKeyClass(AvroKey.class);
		job.setOutputValueClass(NullWritable.class);

		job.setMapperClass(AvroMapper.class);
		job.setReducerClass(AvroReducer.class);
		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String... args) throws Exception {
		int exitCode = ToolRunner.run(new AvroSort(), args);
		System.exit(exitCode);
	}

	@Override
	public void setConf(Configuration conf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Configuration getConf() {
		// TODO Auto-generated method stub
		return null;
	}
}
