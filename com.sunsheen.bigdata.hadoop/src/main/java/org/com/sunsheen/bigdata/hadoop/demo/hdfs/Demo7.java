package org.com.sunsheen.bigdata.hadoop.demo.hdfs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * 读取seq文件
 * 
 * @author laz
 *
 */
public class Demo7 {
	public static void main(String[] args) {
		String url = Constants.hdfsHosts + "/testData/test2.seq";
		Configuration configuration = new Configuration();
		// Reader内部类用于文件的读取操作
		SequenceFile.Reader reader = null;
		try {
			FileSystem fs = FileSystem.get(URI.create(url), configuration);
			reader = new SequenceFile.Reader(fs, new Path(url), configuration);
			Writable key = (Writable) ReflectionUtils.newInstance(
					reader.getKeyClass(), configuration);
			BytesWritable value = (BytesWritable) ReflectionUtils.newInstance(
					reader.getValueClass(), configuration);
			while (reader.next(key, value)) {
				File file = new File("d:/word2/"+key);
				FileOutputStream output=new FileOutputStream(file);
				org.apache.commons.io.IOUtils.write(value.getBytes(), output);
				output.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(reader);
		}
	}

}
