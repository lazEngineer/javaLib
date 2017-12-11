package com.sunsheen.jfids.bigplus.capability.benchmark.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import com.sunsheen.jfids.bigplus.capability.benchmark.db.util.DBSqlUtils;

public class DBThread implements Runnable {
	// 测试数量
	private long count;
	private String dbType;
	private String sql;
	private int threadCount;
	private int sqlcol;

	public DBThread(String dbType, String sql, long count, int threadCount,
			int sqlcol) {
		this.dbType = dbType;
		this.sql = sql;
		this.count = count;
		this.threadCount = threadCount;
		this.sqlcol = sqlcol;
	}
	
	public static Object syc = new Object();


	public void run(){
			DBBenchmark.logger.info("线程:" + Thread.currentThread().getName()
					+ "执行" + dbType + "\t" + sql + "\t" + count + "脚本");
			Connection conn = null;
			PreparedStatement pstmt = null;
			if (DBBenchmark.HIVE.equals(dbType)) {
				try {
					conn = DBSqlUtils.getConnection(dbType);
					if (sql.startsWith(DBSqlUtils.SELECT)) {
						pstmt = conn.prepareStatement(sql);
						pstmt.executeQuery();
					} else {
						if (sql.startsWith(DBSqlUtils.INSERT)) {
							pstmt = conn.prepareStatement(sql);
							for (int i = 0; i < (count / threadCount); i++) {
								//插入1000条id定的数据，方便测试
								if (DBSqlUtils.num >= 100) {
									pstmt.setString(1, UUID.randomUUID().toString());
								} else {
									synchronized(syc) {
										pstmt.setString(1, DBSqlUtils.num+"");
										DBSqlUtils.num++;
									}
								}
								for (int j = 0; j < sqlcol; j++) {
									pstmt.setString(2 + j, "测试" + j);
								}
								pstmt.executeUpdate();
								
							}
						} else {
							pstmt = conn.prepareStatement(sql);
							pstmt.executeUpdate();
						}
					}
					conn.commit();
				} catch (Exception e) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
					Thread.currentThread().interrupt();

				}
			} else {
				try {
					conn = DBSqlUtils.getConnection(dbType);
					conn.setAutoCommit(false);
					if (sql.startsWith(DBSqlUtils.SELECT)) {
						pstmt = conn.prepareStatement(sql);
						pstmt.executeQuery();
					} else {
						if (sql.startsWith(DBSqlUtils.INSERT)) {
							pstmt = conn.prepareStatement(sql);
							for (int i = 0; i < (count / threadCount); i++) {
								//插入1000条id定的数据，方便测试
								if (DBSqlUtils.num >= 100) {
									pstmt.setString(1, UUID.randomUUID().toString());
								} else {
									synchronized(syc) {
										pstmt.setString(1, DBSqlUtils.num+"");
										DBSqlUtils.num++;
									}
								}
								for (int j = 0; j < sqlcol; j++) {
									pstmt.setString(2 + j, "测试" + j);
								}
								pstmt.addBatch();
								if (i % 1000 == 0) {
									pstmt.executeBatch();
									conn.commit();
									pstmt.clearBatch();
								}
							}
							pstmt.executeBatch();
							conn.commit();
							pstmt.clearBatch();
						} else {
							pstmt = conn.prepareStatement(sql);
							pstmt.executeUpdate();
							conn.commit();
						}
					}
			
				} catch (Exception e) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
					Thread.currentThread().interrupt();

				}
			}
			

	}
}
