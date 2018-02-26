package org.com.sunsheen.bigdata.hadoop.demo.mapreduce.demo1;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.com.sunsheen.bigdata.hadoop.test.mapreduce.WCMapper;
import org.com.sunsheen.bigdata.hadoop.test.mapreduce.WCReducer;
import org.com.sunsheen.bigdata.hadoop.test.mapreduce.WordCount;

public class WCMain {
	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException {
		// 创建job对象
		Job job = Job.getInstance(new Configuration());
		// 指定程序的入口
		job.setJarByClass(WordCount.class);

		// 指定自定义的Mapper阶段的任务处理类
		job.setMapperClass(WCMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);

		job.setCombinerClass(WCReducer.class);
		// 数据HDFS文件服务器读取数据路径
		FileInputFormat.setInputPaths(job, new Path("/testData/words.txt"));

		// 指定自定义的Reducer阶段的任务处理类
		job.setReducerClass(WCReducer.class);
		// 设置最后输出结果的Key和Value的类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		// 将计算的结果上传到HDFS服务
		FileOutputFormat.setOutputPath(job, new Path("/testData/wordsResult"));

		// 执行提交job方法，直到完成，参数true打印进度和详情
		job.waitForCompletion(true);
		System.out.println("Finished");
	}
}
