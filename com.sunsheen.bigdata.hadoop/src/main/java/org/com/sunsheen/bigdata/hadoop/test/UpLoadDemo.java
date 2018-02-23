package org.com.sunsheen.bigdata.hadoop.test;

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
 * 文件上传
 * 
 * @author laz
 *
 */
public class UpLoadDemo {
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		// 获得FileSystem对象，指定使用root用户上传
				FileSystem fileSystem = FileSystem.get(new URI(Constants.hdfsHosts),
						new Configuration(), "root");
				// 创建输入流，参数指定文件输出地址
				InputStream in = new FileInputStream(
						"d:/test/hdfsData/33.txt");
				// 调用create方法指定文件上传，参数HDFS上传路径
				OutputStream out = fileSystem.create(new Path("/testData/33.txt"));
				// 使用Hadoop提供的IOUtils，将in的内容copy到out，设置buffSize大小，是否关闭流设置true
				IOUtils.copyBytes(in, out, 4096, true);
	}
}
