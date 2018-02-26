package org.com.sunsheen.bigdata.hadoop.demo.hdfs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

/**
 * Hadoop文件上传示例
 * 
 * @author laz
 *
 */
public class Demo1 {
	public static void main(String[] args) throws IOException,
			InterruptedException, URISyntaxException {
		// 获得FileSystem对象，指定使用用户上传
		Configuration conf = new Configuration();
		FileSystem fileSystem = FileSystem.get(new URI(Constants.hdfsHosts),
				conf, "hdfs");
		// 创建输入流，参数指定文件输出地址
		InputStream in = new FileInputStream("d:/test/hdfsData/demo1.txt");
		// 调用create方法指定文件上传，参数HDFS上传路径
		OutputStream out = fileSystem.create(new Path("/testData/demo1.txt"));
		// 使用Hadoop提供的IOUtils，将in的内容copy到out，设置buffSize大小，是否关闭流设置true
		IOUtils.copyBytes(in, out, conf);
	}
}
