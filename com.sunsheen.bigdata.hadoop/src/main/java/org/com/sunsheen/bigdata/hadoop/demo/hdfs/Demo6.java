package org.com.sunsheen.bigdata.hadoop.demo.hdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.Text;

/**
 * 写入seq文件
 * 
 * @author laz
 *
 */
public class Demo6 {
	public static void main(String[] args) throws IOException,
			InterruptedException, URISyntaxException {
		/**
		 * 写SequenceFile
		 */
		String uri = Constants.hdfsHosts + "/testData/test2.seq";
		Configuration conf = new Configuration();
		Path path = new Path(uri);
		Text key = new Text();

		Writer writer = null;
		try {
			/**
			 * CompressionType.NONE 不压缩<br>
			 * CompressionType.RECORD 只压缩value<br>
			 * CompressionType.BLOCK 压缩很多记录的key/value组成块
			 */
			writer = SequenceFile.createWriter(conf, Writer.file(path),
					Writer.keyClass(key.getClass()),
					Writer.valueClass(BytesWritable.class),
					Writer.compression(SequenceFile.CompressionType.BLOCK));

			File dir = new File("D:\\word");
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isFile()) {
					BytesWritable value = new BytesWritable(
							org.apache.commons.io.IOUtils
									.toByteArray(new FileInputStream(file)));
					value.set(value);
					key.set(file.getName());
					writer.append(key, value);
				}
			}
		} finally {
			IOUtils.closeStream(writer);
		}

	}

}
