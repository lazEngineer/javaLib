package com.sunsheen.bigdata.zookeeper.demo;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;

import com.sunsheen.bigdata.zookeeper.test.CreateGroup;
import com.sunsheen.bigdata.zookeeper.test.ZKConnection;

/**
 * 创建节点（组）
 * @author laz
 *
 */
public class Demo1 extends ZookeeperMain{
	public static void main(String[] args) throws IOException,
			InterruptedException, KeeperException {
		Demo1 createGroup = new Demo1();
		createGroup.create("testZk","data".getBytes());
		createGroup.close();
	}


	private void create(String groupName,byte[] data) throws KeeperException,
			InterruptedException {
		String path = "/" + groupName;
		if (zk.exists(path, false) == null) {
			zk.create(path, data/* data */, Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT);
		}
		System.out.println("Created:" + path);
	}

}
