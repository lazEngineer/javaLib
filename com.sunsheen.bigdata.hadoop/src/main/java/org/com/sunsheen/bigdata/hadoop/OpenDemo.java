package org.com.sunsheen.bigdata.hadoop;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class OpenDemo {
	public static void main(String[] args) throws IOException, URISyntaxException {  
		Configuration conf = new Configuration();
		// 获得FileSystem对象  
		//一般9000端口，，fs.defaultFS配置可修改端口
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://172.18.130.100:8020"), conf);  
        
        // 调用open方法进行下载，参数HDFS路径  
        InputStream in = fileSystem.open(new Path("/testData/install.log"));  
        // 创建输出流，参数指定文件输出地址  
        OutputStream out = new FileOutputStream("D://test/hdfsData/1.log");  
        // 使用Hadoop提供的IOUtils，将in的内容copy到out，设置buffSize大小，是否关闭流设置true  
        IOUtils.copyBytes(in, out, 4096, true);  
    }  
}
