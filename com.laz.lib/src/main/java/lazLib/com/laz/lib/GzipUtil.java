package lazLib.com.laz.lib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtil {
	public static byte[] giz(byte[] data) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(bos);
		gzip.write(data);
		gzip.finish();
		gzip.close();
		
		byte[] b = bos.toByteArray();
		bos.close();
		return b;
	}
	
	public static byte[] ungizp(byte[] data) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		GZIPInputStream gzip = new GZIPInputStream(bis);
		byte[] buf = new byte[1024];
		int num = -1;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((num=gzip.read(buf,0,buf.length)) != -1) {
			baos.write(buf,0,num);
		}
		gzip.close();
		bis.close();
		
		byte[] b = baos.toByteArray();
		baos.flush();
		baos.close();
		return b;
		
		
		
	}
	
	public static void main(String[] args) {
		
	}
}
