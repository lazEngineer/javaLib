package com.sunsheen.bigdata.zookeeper.demo;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

/**
 * 一个节点加入另一个节点，作为其子节点
 * 
 * @author laz
 *
 */
public class Demo3 extends ZookeeperMain {
	public static void main(String[] args) throws IOException,
			InterruptedException, KeeperException {
		Demo3 d = new Demo3();
		d.join("testZk","joinOne");
		d.close();
	}

	public void join(String groupName, String memberName)
			throws KeeperException, InterruptedException {
		String path = "/" + groupName + "/" + memberName;
		String createdPath = zk.create(path, null, Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		System.out.println("Created:" + createdPath);
	}
}
