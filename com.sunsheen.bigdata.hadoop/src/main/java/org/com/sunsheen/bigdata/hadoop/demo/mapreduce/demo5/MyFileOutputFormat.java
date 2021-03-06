package org.com.sunsheen.bigdata.hadoop.demo.mapreduce.demo5;

import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.ReflectionUtils;

public class MyFileOutputFormat<K, V> extends TextOutputFormat<K, V>{
		@Override
		public RecordWriter<K, V> getRecordWriter(TaskAttemptContext job)
				throws IOException, InterruptedException {

			Configuration conf = job.getConfiguration();
			boolean isCompressed = getCompressOutput(job);
			String keyValueSeparator = conf.get(SEPERATOR, "\t");
			CompressionCodec codec = null;
			String extension = "";
			if (isCompressed) {
				Class<? extends CompressionCodec> codecClass = getOutputCompressorClass(
						job, GzipCodec.class);
				codec = (CompressionCodec) ReflectionUtils.newInstance(
						codecClass, conf);
				extension = codec.getDefaultExtension();
			}
			Path path = getDefaultWorkFile(job, extension);
			Path file = new Path(path.getParent()+"/"+path.getName().replace("part", "word"));
			FileSystem fs = file.getFileSystem(conf);
			if (!isCompressed) {
				FSDataOutputStream fileOut = fs.create(file, false);
				return new LineRecordWriter<K, V>(fileOut, keyValueSeparator);
			} else {
				FSDataOutputStream fileOut = fs.create(file, false);
				return new LineRecordWriter<K, V>(new DataOutputStream(
						codec.createOutputStream(fileOut)), keyValueSeparator);
			}

		}
}
