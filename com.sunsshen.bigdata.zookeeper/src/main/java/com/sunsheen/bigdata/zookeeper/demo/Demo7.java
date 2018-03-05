package com.sunsheen.bigdata.zookeeper.demo;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

/**
 * Zookeeper临时节点创建（只在会话是创建使用，关闭会话节点被删除）
 * 
 * @author laz
 *
 */
public class Demo7 extends ZookeeperMain{
	public static void main(String[] args) throws IOException,
			InterruptedException, KeeperException {
		Demo7 d = new Demo7();
		d.createTemporary("testZkTemporary", "data".getBytes());
		d.close();
	}

	private void createTemporary (String groupName, byte[] data) throws KeeperException,
			InterruptedException {
		String path = "/" + groupName;
		if (zk.exists(path, false) == null) {
			zk.create(path, data/* data */, Ids.OPEN_ACL_UNSAFE,
					CreateMode.EPHEMERAL);
		}
	}
}
