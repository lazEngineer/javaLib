package org.com.sunsheen.bigdata.hadoop.demo.hdfs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * Hadoop目录创建
 * 
 * @author laz
 *
 */
public class Demo3 {
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		// 获得FileSystem对象
		FileSystem fileSystem = FileSystem.get(new URI(Constants.hdfsHosts),
				new Configuration(), "hdfs");
		// 调用mkdirs方法，在HDFS文件服务器上创建文件夹。
		boolean flag = fileSystem.mkdirs(new Path("/testData/demo3"));
		// 执行结果：true
		System.out.println(flag);
	}
}
