package com.laz.test.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class HiveCreateDb {
	private static String driverName = "org.apache.hive.jdbc.HiveDriver";

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
	      // Register driver and create driver instance
	   
	      Class.forName(driverName);
	      // get connection
	      
	      Connection con = DriverManager.getConnection("jdbc:hive2://172.18.130.100:10000/default", "", "");
	      
	      // create statement
	      Statement stmt = con.createStatement();
	      // execute statement
	      stmt.executeQuery("CREATE TABLE IF NOT EXISTS "
	         +" employee ( eid int, name String, "
	         +" salary String, destignation String, year string)"
	         +" COMMENT 'Employee details'"
	         +" ROW FORMAT DELIMITED"
	         +" FIELDS TERMINATED BY '\t'"
	         +" LINES TERMINATED BY '\n'"
	         +" STORED AS TEXTFILE");
	         
	      con.close();
	   }
}
