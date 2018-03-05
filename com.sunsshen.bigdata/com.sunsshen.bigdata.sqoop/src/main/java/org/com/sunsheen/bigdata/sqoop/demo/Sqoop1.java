package org.com.sunsheen.bigdata.sqoop.demo;


import org.apache.hadoop.conf.Configuration;
import org.apache.sqoop.Sqoop;
import org.apache.sqoop.tool.SqoopTool;
import org.apache.sqoop.util.OptionsFileUtil;

public class Sqoop1 {
	public static String hdfsHosts = "hdfs://172.18.130.100:8020";
	private static int importDataFromMysql() throws Exception {
		String[] args = new String[] { "--connect",
				"jdbc:mysql://172.18.130.101:3306/test", "--driver",
				"com.mysql.jdbc.Driver", "-username", "hive", "-password",
				"admin", "--table", "sqoopTest", "-m", "1", "--target-dir",
				"java_import_sqoopTest" };

		String[] expandArguments = OptionsFileUtil.expandArguments(args);
		for (String string : expandArguments) {
			
			System.out.print(string+' ');
		}
		SqoopTool tool = SqoopTool.getTool("import");

		Configuration conf = new Configuration();
		conf.set("fs.default.name", hdfsHosts);// 设置HDFS服务地址
		conf.set("fs.hdfs.impl",org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());  
		Configuration loadPlugins = SqoopTool.loadPlugins(conf);

		Sqoop sqoop = new Sqoop((com.cloudera.sqoop.tool.SqoopTool) tool,
				loadPlugins);
		return Sqoop.runSqoop(sqoop, expandArguments);
	}

	public static void main(String[] args) throws Exception {
		importDataFromMysql();
	}
}
