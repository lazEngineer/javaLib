package com.sunsheen.jfids.bigplus.capability.benchmark.db.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.sunsheen.jfids.bigplus.capability.benchmark.db.DBBenchmark;
import com.sunsheen.jfids.bigplus.capability.benchmark.db.DBThread;

/**
 * Mysql工具类
 * 
 * @author lz
 *
 */
public class DBSqlUtils {
	public static final String INSERT = "insert";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String SELECT = "select";
	public static int num = 0;
	public static Map<String, Map<String, String>> connInfo = new HashMap<String, Map<String, String>>();
	static {
		loadMysql();
		loadPostgresql();
		loadGreenPlumsql();
		loadHivesql();
	}

	

	private static void loadMysql() {
		loadDriver(DBBenchmark.MYSQL, "mysql.properties");
	}

	private static void loadHivesql() {
		loadDriver(DBBenchmark.HIVE, "hive.properties");
	}

	private static void loadGreenPlumsql() {
		loadDriver(DBBenchmark.GREENPLUM, "greenplum.properties");
		
	}

	private static void loadPostgresql() {
		loadDriver(DBBenchmark.POSTGRESQL, "postgresql.properties");
	}

	private static void loadDriver(String dbType, String configFile) {
		InputStream in = null;
		try {
			Properties p = new Properties();
			in = DBSqlUtils.class.getClassLoader().getResourceAsStream(
					configFile);
			p.load(in);
			Map<String, String> map = new HashMap<String, String>();
			map.put("url", p.get("url").toString().trim());
			map.put("user", p.get("user").toString().trim());
			map.put("password", p.get("password").toString().trim());
			map.put("driver", p.get("driver").toString().trim());
			connInfo.put(dbType, map);
			Class.forName(map.get("driver"));
		} catch (Exception e) {
			DBBenchmark.logger.error(dbType + " 驱动加载失败", e);
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 得到数据库连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection(String dbType) throws SQLException {
		Map<String, String> conn = connInfo.get(dbType);
		if (conn == null) {
			DBBenchmark.logger.error("数据库类型 " + dbType + " 不存在");
			return null;
		}
		return DriverManager.getConnection(conn.get("url"), conn.get("user"),
				conn.get("password"));
	}

	/**
	 * 
	 * @param sql
	 * @param dbType
	 * @param count
	 * @param threadCount
	 * @param sqlcol 
	 */
	public static void executeBySql(String dbType, String sql, long count,
			int threadCount, int sqlcol) throws Exception {
		num = 0;
		ExecutorService exec = Executors.newFixedThreadPool(threadCount);
		for (int i = 0; i < threadCount; i++) {
			DBThread dbThead = new DBThread(dbType, sql, count, threadCount,sqlcol);
			exec.execute(dbThead);
		}
		exec.shutdown();
		try {
			// 请求关闭、发生超时或者当前线程中断，无论哪一个首先发生之后，都将导致阻塞，直到所有任务完成执行
			// 设置最长等待10秒
			exec.awaitTermination(100000, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			DBBenchmark.logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		System.out.println("主线执行。");
	}

	public static void executeBySql(String sql,String dbType) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBSqlUtils.getConnection(dbType);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			DBBenchmark.logger.error(e.getMessage(),e);
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		
	}

	
	
}
