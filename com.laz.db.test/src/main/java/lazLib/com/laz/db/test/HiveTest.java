package lazLib.com.laz.db.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class HiveTest {
	private Connection connection;
	private Statement stat;

	public HiveTest() throws SQLException {
		DriverManager.registerDriver(new org.apache.hive.jdbc.HiveDriver());
		connection = DriverManager
				.getConnection(
						"jdbc:hive2://172.18.130.100:10000/default",
						"", "");
		stat = connection.createStatement();
		connection.setAutoCommit(false);
	}

	@Test
	public void testCreateTable1() throws SQLException {
		String sql = "";
		StringBuffer sb = new StringBuffer();
		int count = 1;
		sb.append("create table dbtest_0_"+count);
		sb.append(" (");
		sb.append(" id varchar(64),");
		for (int i=0;i<1;i++) {
			if (i==count-1) {
				sb.append("col"+i+" "+"varchar(32)");
			}else {

				sb.append("col"+i+" "+"varchar(32),");
			}
		}
		sb.append(")");
		sql = sb.toString();
		System.out.println(sql);
		stat.execute(sql);
		connection.commit();
	}
	
	@Test
	public void testCreateTable50() throws SQLException {
		String sql = "";
		StringBuffer sb = new StringBuffer();
		int count = 50;
		sb.append("create table dbtest_0_"+count);
		sb.append(" (");
		sb.append(" id varchar(64),");
		for (int i=0;i<count;i++) {
			if (i==count-1) {
				sb.append("col"+i+" "+"varchar(32)");
			}else {

				sb.append("col"+i+" "+"varchar(32),");
			}
		}
		sb.append(")");
		sql = sb.toString();
		System.out.println(sql);
		stat.execute(sql);
		connection.commit();
	}
	
	@Test
	public void testCreateTable100() throws SQLException {
		try {
			String sql = "";
			StringBuffer sb = new StringBuffer();
			int count = 100;
			sb.append("create table dbtest_0_"+count);
			sb.append(" (");
			sb.append(" id varchar(64),");
			for (int i=0;i<count;i++) {
				if (i==count-1) {
					sb.append("col"+i+" "+"varchar(32)");
				}else {

					sb.append("col"+i+" "+"varchar(32),");
				}
			}
			sb.append(")");
			sql = sb.toString();
			System.out.println(sql);
			stat.execute(sql);
			connection.commit();
		}catch(Exception e) {
				e.printStackTrace();
		}
	
	}


}
