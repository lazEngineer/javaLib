package com.sunsheen.jfids.bigplus.capability.benchmark.db;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sunsheen.jfids.bigplus.capability.Loggers;
import com.sunsheen.jfids.bigplus.capability.benchmark.db.util.DBSqlUtils;

/**
 * 数据库性能测试
 * 
 * @author lz
 *
 */
public class DBBenchmark {
	public static Logger logger;
	public static final String MYSQL = "mysql";
	public static final String HIVE = "hive";
	
	public static final String GREENPLUM = "greenplum";
	public static final String POSTGRESQL = "postgresql";
	public static final String RENDA = "renda";
	public static final String XUGU = "xugu";
	public static String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";

	private String dbType; // 数据库类型
	private int threadCount; // 并发量
	private String sql; // 执行的sql
	private long count; // 操作数据量
	private int sqlcol; // 数据库影响列数

	private Date startDate;
	private Date endDate;
	private long time;
	private String runScript;
	private String state = "success";
	protected String pathname; // 脚本文件路径

	public static void main(String[] args) throws Exception {
		Loggers.init("dbtest"
				+ new SimpleDateFormat("yyyy-MM-dd HH-mm-ss")
						.format(new Date()));
		new DBBenchmark().parseScript(args);
	}

	private void parseScript(String[] args) throws Exception {
		for (int i = 0; i < args.length; ++i) {
			if (args[i].startsWith("-file")) {
				pathname = args[++i];
			} else {
				logger.error("参数格式不合法: " + args[i]);
				return;
			}
		}
		parseTasks();

	}

	public void run(String[] args) throws Exception {
		logger = Loggers.getLogger();
		String sqlOperarte = null;
		for (int i = 0; i < args.length; ++i) {
			if (args[i].trim().equals("-dbType")) {
				dbType = args[(++i)].trim();
			} else if (args[i].trim().equals("-threadCount")) {
				threadCount = Integer.parseInt(args[(++i)].trim());
			} else if (args[i].trim().equals("-sql")) {
				sqlOperarte = args[(++i)];
			} else if (args[i].trim().equals("-count")) {
				count = Integer.parseInt(args[(++i)].trim());
			} else if (args[i].trim().equals("-sqlcol")) {
				sqlcol = Integer.parseInt(args[(++i)].trim());
			}
		}
		sql = createSql(sqlOperarte);
		if (sql.startsWith(DBSqlUtils.INSERT) && !dbType.equals(HIVE)) {
			clearTable(sqlOperarte);
		}
		runScript = "-dbType|" + dbType + "|-sql|" + sql + "|-sqlcol|" + sqlcol
				+ "|-threadCount|" + threadCount + "|-count|" + count;
		logger.info(String.format(
				"#########################启动测试:%s##########################",
				runScript));
		long startTime = System.currentTimeMillis();
		startDate = new Date();
		runTasks();
		long endTime = System.currentTimeMillis();
		time = (endTime - startTime);
		endDate = new Date();
		endTask();
		logger.info(String.format(
				"#########################测试结束:%s##########################\n",
				runScript));
	}

	/**
	 * 清空表数据
	 * 
	 * @param sqlOperarte
	 */
	private void clearTable(String sqlOperarte) {
		String tableName = sqlOperarte.split("\\,")[1];
		DBSqlUtils.executeBySql("delete from " + tableName, dbType);

	}

	private String createSql(String sqlOperarte) {
		String tableName = sqlOperarte.split("\\,")[1];
		StringBuilder sb = new StringBuilder();
		if (sqlOperarte.startsWith(DBSqlUtils.INSERT)) {
			sb.append("insert into " + tableName + " values(?,");
			for (int i = 0; i < sqlcol - 1; i++) {
				sb.append("?,");
			}
			sb.append("?)");
		}
		if (sqlOperarte.startsWith(DBSqlUtils.UPDATE)) {
			sb.append("update " + tableName + " set ");
			for (int i = 0; i < sqlcol - 1; i++) {
				sb.append("col" + i + "='test" + i + "',");
			}
			String updateStr = "";
			for (int j = 0; j < 100; j++) {
				updateStr += "'" + j + "'" + ",";
			}
			updateStr += "'1'";
			sb.append("col" + (sqlcol - 1) + "='test" + (sqlcol - 1)
					+ "' where id in (" + updateStr + ")");
		}
		if (sqlOperarte.startsWith(DBSqlUtils.DELETE)) {
			sb.append("delete from " + tableName);
		}
		if (sqlOperarte.startsWith(DBSqlUtils.SELECT)) {

			if (sqlOperarte.contains("order")) {
				// 排序select
				sb.append("select ");
				for (int i = 0; i < sqlcol - 1; i++) {
					sb.append("col" + i + ",");
				}
				sb.append("col" + (sqlcol - 1) + " ");
				sb.append("from " + tableName
						+ " order by id limit 10000 offset 0");
			} else if (sqlOperarte.contains("group")) {
				// 排序select
				sb.append("select ");
				for (int i = 0; i < sqlcol - 1; i++) {
					if (i == 0) {
						sb.append("col" + i + ",");
					} else {
						sb.append("count(col" + i + "),");
					}
				}
				sb.append("count(col" + (sqlcol - 1) + ") ");
				sb.append("from " + tableName
						+ " group by col0 limit 10000 offset 0 ");
			} else {
				// 一般sql
				sb.append("select ");
				for (int i = 0; i < sqlcol - 1; i++) {
					sb.append("col" + i + ",");
				}
				sb.append("col" + (sqlcol - 1) + " ");
				sb.append("from " + tableName + " limit 10000 offset 0");
			}
		}
		return sb.toString().trim();
	}

	private void endTask() {
		logger.info(String.format("%s [%s]",
				new SimpleDateFormat(FORMAT_DATE).format(startDate), "数据库性能测试"));
		logger.info("执行脚本:\t" + runScript);
		logger.info("开始时间 :\t"
				+ new SimpleDateFormat(FORMAT_DATE).format(startDate));
		logger.info("结束时间 :\t"
				+ new SimpleDateFormat(FORMAT_DATE).format(endDate));
		logger.info("并发数量 :\t" + threadCount);
		logger.info("执行sql:\t" + sql);
		logger.info("数据列:\t" + sqlcol);
		logger.info("测试状态:\t" + state);
		logger.info(String.format("耗时 \t%.3f s", (time / 1000.000)));
	}

	private void runTasks() {
		try {
			DBSqlUtils.executeBySql(dbType, sql, count, threadCount, sqlcol);
		} catch (Exception e) {
			DBBenchmark.logger.error(e.getMessage(), e);
			state = "Failure";
			e.printStackTrace();
		}
	}

	protected void parseTasks() throws Exception {
		for (String task : FileUtils.readLines(new File(pathname))) {
			if (StringUtils.isBlank(task) || task.startsWith("#"))
				continue;
			String[] taskArgs = task.split("\\|");
			run(taskArgs);
		}
	}
}
