package org.com.sunsheen.bigdata.hadoop.demo.hdfs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * 返回Hadoop目录列表
 * 
 * @author laz
 *
 */
public class Demo5 {
	public static void main(String[] args) throws IOException,
			InterruptedException, URISyntaxException {
		// 获得FileSystem对象
		FileSystem fileSystem = FileSystem.get(new URI(Constants.hdfsHosts),
				new Configuration(), "hdfs");

		FileStatus[] files = fileSystem.listStatus(new Path("/user"));
		for (FileStatus file : files) {
			printFile(fileSystem, file);
		}
	}

	private static void printFile(FileSystem fileSystem, FileStatus file)
			throws FileNotFoundException, IOException {
		System.out.print("文件名:" + file.getPath().getName());
		System.out.print("\t是否目录：" + file.isDirectory());
		System.out.print("\t拥有者：" + file.getOwner());
		System.out.print("\t文件大小：" + (double) file.getLen() / 1024 + "kb");
		System.out.print("\n");
		if (file.isDirectory()) {
			for (FileStatus f : fileSystem.listStatus(file.getPath())) {
				printFile(fileSystem, f);
			}
		}

	}

}
