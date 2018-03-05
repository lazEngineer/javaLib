package org.com.sunsheen.bigdata.hadoop.demo.mapreduce.demo3;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;


public class ProviderPartitioner extends
		HashPartitioner<UserEntity, NullWritable> {

	// 声明providerMap，并且在static静态块中初始化
	private static Map<String, Integer> providerMap = new HashMap<String, Integer>();
	static {
		providerMap.put("130", 0);
		providerMap.put("133", 0);
		providerMap.put("134", 0);
		providerMap.put("135", 0);
		providerMap.put("136", 0);
		providerMap.put("137", 0);
		providerMap.put("138", 0);
		providerMap.put("139", 0);
		providerMap.put("150", 1);
		providerMap.put("151", 1);
		providerMap.put("153", 1);
		providerMap.put("158", 1);
		providerMap.put("159", 1);
		providerMap.put("170", 2);
		providerMap.put("180", 3);
		providerMap.put("181", 3);
		providerMap.put("183", 3);
		providerMap.put("185", 3);
		providerMap.put("186", 3);
		providerMap.put("187", 3);
		providerMap.put("188", 3);
		providerMap.put("189", 3);
	}

	/**
	 * 实现自定义的getPartition()方法,自定义分区规则
	 */
	@Override
	public int getPartition(UserEntity key, NullWritable value,
			int numPartitions) {
		String prefix = key.getMobile().substring(0, 3);
		return providerMap.get(prefix);
	}

}
