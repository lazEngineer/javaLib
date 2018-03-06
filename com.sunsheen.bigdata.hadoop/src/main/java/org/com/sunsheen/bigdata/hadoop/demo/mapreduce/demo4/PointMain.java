package org.com.sunsheen.bigdata.hadoop.demo.mapreduce.demo4;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PointMain {
	/**
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException,
			InterruptedException, ClassNotFoundException {
		Configuration conf = new Configuration();
		// 创建job对象
		Job job = Job.getInstance(conf);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setInputFormatClass(Point3DinputFormat.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Point3D.class);
		job.setMapperClass(Point3DMapper.class);
		job.setNumReduceTasks(0);
		job.waitForCompletion(false);
	}

	static class Point3DMapper extends Mapper<Text, Point3D, Text, Point3D> {
		protected void map(Text key, Point3D value, Context context)
				throws IOException, InterruptedException {
			context.write(key, value);
		}

	}
}
