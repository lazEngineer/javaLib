package org.com.sunsheen.bigdata.hadoop.demo.hdfs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * Hadoop文件删除
 * 
 * @author laz
 *
 */
public class Demo4 {
	public static void main(String[] args) throws IOException,
			URISyntaxException, InterruptedException {
		// 获得FileSystem对象
		FileSystem fileSystem = FileSystem.get(new URI(Constants.hdfsHosts),
				new Configuration(), "hdfs");
		// 调用delete方法，删除指定的文件。参数:false:表示是否递归删除
		boolean flag = fileSystem.delete(new Path("/testData/demo1.txt"), false);
		// 执行结果：true
		System.out.println(flag);
	}
}
