package lazLib.com.laz.lib.bigdata.hadoop;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class ReadWriteTest {
	public static void main(String[] args) throws IOException {
		Configuration conf = new Configuration();
		String hdfsuri = "hdfs://172.18.130.100:8020/user/hive/tpc_ds/call_center.dat";
		FileSystem fs = FileSystem.get(URI.create(hdfsuri), conf);
		FSDataInputStream hdfsInStream = fs.open(new Path(hdfsuri));

		String str = IOUtils.toString(hdfsInStream);
		System.out.println(str);
	}

}
