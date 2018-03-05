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
				conf.set("dfs.replication", "1");
				FileSystem fileSystem = FileSystem.get(new URI(Constants.hdfsHosts),
						conf, "hdfs");
				// 创建输入流，参数指定文件输出地址
				InputStream in = new FileInputStream("D:\\work\\任务\\大数据\\1.zip");
				// 调用create方法指定文件上传，参数HDFS上传路径
				OutputStream out = fileSystem.create(new Path("/testData/1.zip"));
				// 使用Hadoop提供的IOUtils，将in的内容copy到out，设置buffSize大小，是否关闭流设置true
				long s = System.currentTimeMillis();
				IOUtils.copyBytes(in, out, 4096,true);
				long e = System.currentTimeMillis();
				System.out.println((e-s)/1000d+"s");
	}
}
