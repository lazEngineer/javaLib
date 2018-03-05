package org.com.sunsheen.bigdata.hadoop.demo.mapreduce.demo3;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class UserAnalysis {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {  
        // 创建job对象  
        Job job = Job.getInstance(new Configuration());  
        // 指定程序的入口  
        job.setJarByClass(UserAnalysis.class);  
          
        // 指定自定义的Mapper阶段的任务处理类  
        job.setMapperClass(UserMapper.class);  
        job.setMapOutputKeyClass(UserEntity.class);  
        job.setMapOutputValueClass(NullWritable.class);  
        // 数据HDFS文件服务器读取数据路径  
        FileInputFormat.setInputPaths(job, new Path("d:/test/hdfsData/input_data.txt"));  
  
        // 指定自定义的Reducer阶段的任务处理类  
        job.setReducerClass(UserReducer.class);  
        // 设置最后输出结果的Key和Value的类型  
        job.setOutputKeyClass(UserEntity.class);  
        job.setOutputValueClass(NullWritable.class);  
          
        // 设置定义分区的处理类  
        job.setPartitionerClass(ProviderPartitioner.class);  
          
        // 默认ReduceTasks数是1  
        // 我们对手机号分成4类，所以应该设置为4  
        job.setNumReduceTasks(4);  
          
        // 将计算的结果上传到HDFS服务  
        FileOutputFormat.setOutputPath(job, new Path("d:/test/hdfsData/output_data"));  
  
        // 执行提交job方法，直到完成，参数true打印进度和详情  
        job.waitForCompletion(true);  
        System.out.println("Finished");  
    }  
}
