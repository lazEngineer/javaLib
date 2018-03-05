package com.sunsheen.bigdata.zookeeper.demo;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

/**
 * 得到节点数据
 * 
 * @author laz
 *
 */
public class Demo4 extends ZookeeperMain {
	public static void main(String[] args) throws IOException,
			InterruptedException, KeeperException {
		Demo4 d = new Demo4();
		byte[] b = d.getData("/testZk");
		System.out.println("数据："+new String(b));
		d.close();
	}
	
	public byte[] getData(String groupName) throws KeeperException, InterruptedException {
		return zk.getData(groupName, false, null);
	}
}
