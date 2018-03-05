package com.sunsheen.bigdata.zookeeper.demo;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

/**
 * 设置节点数据
 * 
 * @author laz
 *
 */
public class Demo5 extends ZookeeperMain {
	public static void main(String[] args) throws IOException,
			InterruptedException, KeeperException {
		Demo5 d = new Demo5();
		d.setData("/testZk","modifyData".getBytes());
		d.close();
	}

	public void setData(String groupName,byte[] data) throws KeeperException,
			InterruptedException {
		 zk.setData(groupName, data, -1);
	}
}
