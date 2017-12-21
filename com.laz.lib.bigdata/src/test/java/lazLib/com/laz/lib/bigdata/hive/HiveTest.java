package lazLib.com.laz.lib.bigdata.hive;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HiveTest {
	 public static void main(String[] args) {
	  test1("select * from a");
	  // test2();
	
	 }

	private static void test2() {
		// TODO Auto-generated method stub
		Connection con = null;
		try {
			con = HiveDBUtil.getConnect();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List list;
		try {
			list = getAllTableName(con);
			System.out.println(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static List getAllTableName(Connection cnn) throws SQLException {
		List tables = new ArrayList();

		DatabaseMetaData dbMetaData = cnn.getMetaData();

		// 可为:"TABLE", "VIEW", "SYSTEM   TABLE",
		// "GLOBAL   TEMPORARY", "LOCAL   TEMPORARY", "ALIAS", "SYNONYM"
		String[] types = { "TABLE" };

		ResultSet tabs = dbMetaData
				.getTables(null, null, null, types/* 只要表就好了 */);
		/*
		 * 记录集的结构如下: TABLE_CAT String => table catalog (may be null) TABLE_SCHEM
		 * String => table schema (may be null) TABLE_NAME String => table name
		 * TABLE_TYPE String => table type. REMARKS String => explanatory
		 * comment on the table TYPE_CAT String => the types catalog (may be
		 * null) TYPE_SCHEM String => the types schema (may be null) TYPE_NAME
		 * String => type name (may be null) SELF_REFERENCING_COL_NAME String =>
		 * name of the designated "identifier" column of a typed table (may be
		 * null) REF_GENERATION String => specifies how values in
		 * SELF_REFERENCING_COL_NAME are created. Values are "SYSTEM", "USER",
		 * "DERIVED". (may be null)
		 */
		while (tabs.next()) {
			// 只要表名这一列
			tables.add(tabs.getObject("TABLE_NAME"));

		}
		System.out.println(tables);
		return tables;

	}

//	public static void main(String[] args) {
//		try {
//			Class.forName("org.apache.hive.jdbc.HiveDriver");
//
//			Connection con = DriverManager
//					.getConnection("jdbc:hive2://172.18.130.100:10000/default",
//							"hive", "hive");
//			Statement stmt = con.createStatement();
//			// execute statement
//			ResultSet rs = stmt
//					.executeQuery("select cs_ship_date_sk,cs_item_sk,cs_quantity from catalog_sales where cs_bill_customer_sk is not null  limit 0,10 ");
//			int col = rs.getMetaData().getColumnCount();
//			ResultSetMetaData rsmd = rs.getMetaData();
//			System.out.println("============================");
//			List records = new ArrayList();
//			while (rs.next()) {
//				Map<String, String> valueMap = new LinkedHashMap<String, String>();
//				for (int i = 1; i <= col; i++) {
//					String fieldClassName = rsmd.getColumnClassName(i);
//					String fieldName = rsmd.getColumnName(i);
//					System.out.println(fieldClassName+":"+fieldName);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	private static void test1(String sql) {
		Connection con = null;
		try {
			con = HiveDBUtil.getConnect();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			List list = HiveDBUtil
					.query(con,
							sql);
			System.out.println(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
