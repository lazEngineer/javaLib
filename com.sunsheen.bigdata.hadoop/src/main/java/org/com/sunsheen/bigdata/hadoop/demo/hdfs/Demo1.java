package org.com.sunsheen.bigdata.hadoop.demo.hdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		// 获得FileSystem对象，指定使用用户上传
		Configuration conf = new Configuration();
		conf.set("dfs.replication", "3");
		final FileSystem fileSystem = FileSystem.get(new URI(Constants.hdfsHosts),
				conf, "hdfs");
		// 创建输入流，参数指定文件输出地址
		final File file = new File("/root/test");
		final File[] files = file.listFiles();
		for (int j = 0; j < 30; j++) {
			executorService.execute(new Runnable() {
				public void run() {
					for (int i = 0; i < 100; i++) {
						for (final File file2 : files) {
							if (file2.isFile()) {
								InputStream in;
								try {
									in = new FileInputStream(file2);
									// 调用create方法指定文件上传，参数HDFS上传路径
									OutputStream out = fileSystem
											.create(new Path("/testData/random/"
													+ UUID.randomUUID().toString()));
									// 使用Hadoop提供的IOUtils，将in的内容copy到out，设置buffSize大小，是否关闭流设置true
									long s = System.currentTimeMillis();
									IOUtils.copyBytes(in, out, 4096, true);
									long e = System.currentTimeMillis();
									System.out.println((e - s) / 1000d + "s");
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
							}

						}
					}
				}
			});

		}

	}
}
