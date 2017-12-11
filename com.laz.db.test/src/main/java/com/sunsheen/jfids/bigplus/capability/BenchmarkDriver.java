package com.sunsheen.jfids.bigplus.capability;

import com.sunsheen.jfids.bigplus.capability.benchmark.db.DBBenchmark;

/**
 * 
 * @author lz
 *
 */
public class BenchmarkDriver {

	public static ProgramDriver pgd = new ProgramDriver();

	static {
		try {
			//数据库性能测试
			pgd.addClass("dbtest", DBBenchmark.class, "数据库性能测试");
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static void main(String argv[]) {
		Loggers.init(argv[0]);
		if (run(argv) == -1)
			System.exit(run(argv));
	}

	public static int run(String argv[]) {
		try {
			return pgd.run(argv);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return -1;
	}
}
