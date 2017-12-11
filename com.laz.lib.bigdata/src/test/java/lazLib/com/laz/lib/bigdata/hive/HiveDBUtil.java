package lazLib.com.laz.lib.bigdata.hive;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HiveDBUtil {
	private static String driverName = "org.apache.hive.jdbc.HiveDriver";
	private static String url = "jdbc:hive2://172.18.130.100:10000/default";
	private static String username = "hive";
	private static String passwd = "hive";
	private static Connection con;

	public static Connection getConnect() throws SQLException {
		if (con != null) {
			return con;
		} else {
			try {
				Class.forName(driverName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			con = DriverManager.getConnection(url, username, passwd);
			return con;
		}
	}

	/**
	 * 将ResultSet结果集中的记录映射到Map对象中.
	 * 
	 * @param fieldClassName
	 *            是JDBC API中的类型名称,
	 * @param fieldName
	 *            是字段名，
	 * @param rs
	 *            是一个ResultSet查询结果集,
	 * @param fieldValue
	 *            Map对象,用于存贮一条记录.
	 * @throws SQLException
	 */
	private static void recordMappingToMap(String fieldClassName,
			String fieldName, ResultSet rs, Map fieldValue) throws SQLException {
		fieldName = fieldName.toLowerCase();
		// 优先规则：常用类型靠前
		if (fieldClassName.equals("java.lang.String")) {
			String s = rs.getString(fieldName);
			if (rs.wasNull()) {
				fieldValue.put(fieldName, null);
			} else {
				fieldValue.put(fieldName, s);
			}
		} else if (fieldClassName.equals("java.lang.Integer")) {
			int s = rs.getInt(fieldName);
			if (rs.wasNull()) {
				fieldValue.put(fieldName, null);
			} else {
				fieldValue.put(fieldName, s);// 早期jdk需要包装，jdk1.5后不需要包装
			}
		} else if (fieldClassName.equals("java.lang.Long")) {
			long s = rs.getLong(fieldName);
			if (rs.wasNull()) {
				fieldValue.put(fieldName, null);
			} else {
				fieldValue.put(fieldName, s);
			}
		} else if (fieldClassName.equals("java.lang.Boolean")) {
			boolean s = rs.getBoolean(fieldName);
			if (rs.wasNull()) {
				fieldValue.put(fieldName, null);
			} else {
				fieldValue.put(fieldName, s);
			}
		} else if (fieldClassName.equals("java.lang.Short")) {
			short s = rs.getShort(fieldName);
			if (rs.wasNull()) {
				fieldValue.put(fieldName, null);
			} else {
				fieldValue.put(fieldName, s);
			}
		} else if (fieldClassName.equals("java.lang.Float")) {
			float s = rs.getFloat(fieldName);
			if (rs.wasNull()) {
				fieldValue.put(fieldName, null);
			} else {
				fieldValue.put(fieldName, s);
			}
		} else if (fieldClassName.equals("java.lang.Double")) {
			double s = rs.getDouble(fieldName);
			if (rs.wasNull()) {
				fieldValue.put(fieldName, null);
			} else {
				fieldValue.put(fieldName, s);
			}
		} else if (fieldClassName.equals("java.sql.Timestamp")) {
			java.sql.Timestamp s = rs.getTimestamp(fieldName);
			if (rs.wasNull()) {
				fieldValue.put(fieldName, null);
			} else {
				fieldValue.put(fieldName, s);
			}
		} else if (fieldClassName.equals("java.sql.Date")
				|| fieldClassName.equals("java.util.Date")) {
			java.util.Date s = rs.getDate(fieldName);
			if (rs.wasNull()) {
				fieldValue.put(fieldName, null);
			} else {
				fieldValue.put(fieldName, s);
			}
		} else if (fieldClassName.equals("java.sql.Time")) {
			java.sql.Time s = rs.getTime(fieldName);
			if (rs.wasNull()) {
				fieldValue.put(fieldName, null);
			} else {
				fieldValue.put(fieldName, s);
			}
		} else if (fieldClassName.equals("java.lang.Byte")) {
			byte s = rs.getByte(fieldName);
			if (rs.wasNull()) {
				fieldValue.put(fieldName, null);
			} else {
				fieldValue.put(fieldName, new Byte(s));
			}
		} else if (fieldClassName.equals("[B")
				|| fieldClassName.equals("byte[]")) {
			// byte[]出现在SQL Server中
			byte[] s = rs.getBytes(fieldName);
			if (rs.wasNull()) {
				fieldValue.put(fieldName, null);
			} else {
				fieldValue.put(fieldName, s);
			}
		} else if (fieldClassName.equals("java.math.BigDecimal")) {
			BigDecimal s = rs.getBigDecimal(fieldName);
			if (rs.wasNull()) {
				fieldValue.put(fieldName, null);
			} else {
				fieldValue.put(fieldName, s);
			}
		} else if (fieldClassName.equals("java.lang.Object")
				|| fieldClassName.equals("oracle.sql.STRUCT")) {
			Object s = rs.getObject(fieldName);
			if (rs.wasNull()) {
				fieldValue.put(fieldName, null);
			} else {
				fieldValue.put(fieldName, s);
			}
		} else if (fieldClassName.equals("java.sql.Array")
				|| fieldClassName.equals("oracle.sql.ARRAY")) {
			java.sql.Array s = rs.getArray(fieldName);
			if (rs.wasNull()) {
				fieldValue.put(fieldName, null);
			} else {
				fieldValue.put(fieldName, s);
			}
		} else if (fieldClassName.equals("java.sql.Clob")) {
			java.sql.Clob s = rs.getClob(fieldName);
			if (rs.wasNull()) {
				fieldValue.put(fieldName, null);
			} else {
				fieldValue.put(fieldName, s);
			}
		} else if (fieldClassName.equals("java.sql.Blob")) {
			java.sql.Blob s = rs.getBlob(fieldName);
			if (rs.wasNull()) {
				fieldValue.put(fieldName, null);
			} else {
				fieldValue.put(fieldName, s);
			}
		} else {// 对于其它任何未知类型的处理
			Object s = rs.getObject(fieldName);
			if (rs.wasNull()) {
				fieldValue.put(fieldName, null);
			} else {
				fieldValue.put(fieldName, s);
			}
		}
	}

	public static List<Map<String, Object>> query(Connection con, String sql)
			throws SQLException {
		// create statement
		Statement stmt = con.createStatement();
		// execute statement
		ResultSet rs = stmt.executeQuery(sql);
		int col = rs.getMetaData().getColumnCount();
		ResultSetMetaData rsmd = rs.getMetaData();
		System.out.println("============================");
		List records = new ArrayList();
		while (rs.next()) {
			Map<String, String> valueMap = new LinkedHashMap<String, String>();
			for (int i = 1; i <= col; i++) {
				String fieldClassName = rsmd.getColumnClassName(i);
				String fieldName = rsmd.getColumnName(i);
				recordMappingToMap(fieldClassName, fieldName, rs, valueMap);
			}
			records.add(valueMap);
		}
		return records;
	}
}
