package org.com.sunsheen.bigdata.sqoop.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.sqoop.Sqoop;
import org.apache.sqoop.tool.ExportTool;
import org.apache.sqoop.tool.ImportTool;
import org.apache.sqoop.tool.SqoopTool;
import org.apache.sqoop.util.OptionsFileUtil;

public class Sqoop2 {
	public static String hdfsHosts = "hdfs://172.18.130.100:8020";

	private static int exportDataFromMysql() throws Exception {
		String[] args = new String[] { 
				"--connect",
				"jdbc:mysql://172.18.130.101:3306/test", 
				"--driver",
				"com.mysql.jdbc.Driver", 
				"-username", 
				"hive", 
				"-password",
				"admin", 
				"--export-dir", 
				"/user/hdfs/java_import_sqoopTest",
				"--table", 
				"sqoopTest", 
				"-m", "1", 
				"--input-fields-terminated-by",
				","};

		String[] expandArguments = OptionsFileUtil.expandArguments(args);
		for (String string : expandArguments) {

			System.out.print(string + ' ');
		}
		Configuration conf = new Configuration();
		conf.set("fs.default.name", hdfsHosts);// 设置HDFS服务地址
		conf.set("fs.hdfs.impl",org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());  
		ExportTool export = new ExportTool();
		Sqoop sqoop = new Sqoop(export);
		sqoop.setConf(conf);
		int ret  = Sqoop.runSqoop(sqoop, expandArguments);
		System.out.println(ret+"                  状态");
		return ret;
	}

	public static void main(String[] args) throws Exception {
		exportDataFromMysql();
	}
}
