package org.com.sunsheen.bigdata.hadoop.demo.mapreduce.demo2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class SJLocalMain extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {
		// 创建job对象
		Job job = Job.getInstance(new Configuration());
		// 指定程序的入口
		job.setJarByClass(SJMain.class);

		// 指定自定义的Mapper阶段的任务处理类
		job.setMapperClass(SJMapper.class);
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(Text.class);

		// 数据HDFS文件服务器读取数据路径
		FileInputFormat.setInputPaths(job, new Path("D:/test/hdfsData/child.txt"));

		// 指定自定义的Reducer阶段的任务处理类
		job.setReducerClass(SJReduce.class);
		// 设置最后输出结果的Key和Value的类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		// 将计算的结果上传到HDFS服务
		FileOutputFormat.setOutputPath(job, new Path("D:/test/hdfsData/childRs"));

		// 执行提交job方法，直到完成，参数true打印进度和详情
		boolean flag = job.waitForCompletion(true);
		return flag?1:0;
	}
	public static void main(String[] args) throws Exception {
		int ret = ToolRunner.run(new SJLocalMain(), args);
		System.exit(ret);
	}

}
