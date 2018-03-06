package org.com.sunsheen.bigdata.hadoop.demo.mapreduce.demo5;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WCMain {
	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException {
		// 创建job对象
		Job job = Job.getInstance(new Configuration());
		// 指定程序的入口
		job.setJarByClass(WCMain.class);

		// 指定自定义的Mapper阶段的任务处理类
		job.setMapperClass(WordCountMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);

		// 数据HDFS文件服务器读取数据路径
		FileInputFormat.setInputPaths(job, new Path(
				"D:/test/hdfsData/wordsAll.txt"));

		// 指定自定义的Reducer阶段的任务处理类
		job.setReducerClass(WordCountReduce.class);
		// 设置最后输出结果的Key和Value的类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);

		File file = new File("D:/test/hdfsData/wordsAllResult");
		if (file.exists()) {
			deleteDir(file);
		}
		// 将计算的结果上传到HDFS服务
		FileOutputFormat.setOutputPath(job, new Path(
				"D:/test/hdfsData/wordsAllResult"));

		// 设置分区与Reduce数量
		job.setPartitionerClass(PPartition.class);
		job.setNumReduceTasks(27);

		job.setOutputFormatClass(MyFileOutputFormat.class);

		/** 指定本job使用combiner组件，组件所用的类为ReduceWordCountTask **/
		job.setCombinerClass(MyCombiner.class);

		// 执行提交job方法，直到完成，参数true打印进度和详情
		job.waitForCompletion(true);
		System.out.println("Finished");
	}

	static class MyCombiner extends
			Reducer<Text, LongWritable, Text, LongWritable> {

		private LongWritable v3 = new LongWritable();

		@Override
		protected void reduce(Text k2, Iterable<LongWritable> v2s,
				Context context) throws IOException, InterruptedException {
			long sum = 0;
			for (LongWritable longWritable : v2s) {
				sum += longWritable.get();
				v3.set(sum);
			}
			System.out.println("combiner"+k2+":"+v3);
			context.write(k2, v3);
		}
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	/**
	 * 自定义MyFileOutputFormat继承FileOutputFormat，实现其中的getRecordWriter方法；
	 * 该方法返回一个RecordWriter对象，需先创建此对象，实现其中的write、close方法；
	 * 文件通过FileSystem在write方法中写出到hdfs自定义文件中
	 */
	public static String SEPERATOR = "mapreduce.output.textoutputformat.separator";

	public static String[] letterArr = new String[] { "a", "b", "c", "d", "e",
			"f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
			"s", "t", "u", "v", "w", "x", "y", "z" };

	/**
	 * 自定义分区
	 * 
	 * @author laz
	 *
	 */
	static class PPartition<K, Text> extends Partitioner<K, Text> {
		@Override
		public int getPartition(K key, Text value, int numReduceTasks) {
			/**
			 * 自定义分区，实现长度不同的字符串，分到不同的reduce里面
			 * 
			 * 现在只有3个长度的字符串，所以可以把reduce的个数设置为3 有几个分区，就设置为几
			 * */

			String k = key.toString();
			int i = 0;
			for (String letter : letterArr) {

				if (k.startsWith(letter)) {
					return i;
				}
				i++;
			}
			return i;
		}
	}

	/*
	 * 继承Mapper类需要定义四个输出、输出类型泛型： 四个泛型类型分别代表： KeyIn
	 * Mapper的输入数据的Key，这里是每行文字的起始位置（0,11,...） ValueIn Mapper的输入数据的Value，这里是每行文字
	 * KeyOut Mapper的输出数据的Key，这里是每行文字中的单词"hello" ValueOut
	 * Mapper的输出数据的Value，这里是每行文字中的出现的次数
	 * 
	 * Writable接口是一个实现了序列化协议的序列化对象。
	 * 在Hadoop中定义一个结构化对象都要实现Writable接口，使得该结构化对象可以序列化为字节流，字节流也可以反序列化为结构化对象。
	 * LongWritable类型:Hadoop.io对Long类型的封装类型
	 */
	static class WordCountMapper extends
			Mapper<LongWritable, Text, Text, LongWritable> {
		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, Text, LongWritable>.Context context)
				throws IOException, InterruptedException {
			// 获得每行文档内容，并且进行折分
			String[] words = value.toString().split(" ");

			// 遍历折份的内容
			for (String word : words) {
				// 每出现一次则在原来的基础上：+1
				context.write(new Text(word), new LongWritable(1));
			}
		}
	}

	/*
	 * 继承Reducer类需要定义四个输出、输出类型泛型： 四个泛型类型分别代表： KeyIn
	 * Reducer的输入数据的Key，这里是每行文字中的单词"hello" ValueIn
	 * Reducer的输入数据的Value，这里是每行文字中的次数 KeyOut Reducer的输出数据的Key，这里是每行文字中的单词"hello"
	 * ValueOut Reducer的输出数据的Value，这里是每行文字中的出现的总次数
	 */
	static class WordCountReduce extends
			Reducer<Text, LongWritable, Text, LongWritable> {
		/**
		 * 重写reduce方法
		 */
		@Override
		protected void reduce(Text key, Iterable<LongWritable> values,
				Reducer<Text, LongWritable, Text, LongWritable>.Context context)
				throws IOException, InterruptedException {
			long sum = 0;
			for (LongWritable i : values) {
				System.out.println("redece: "+key+":"+i.get());
				// i.get转换成long类型
				sum += i.get();
			}
			// 输出总计结果
			context.write(key, new LongWritable(sum));
		}

	}
}
