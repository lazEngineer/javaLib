package com.sunsheen.bigdata.zookeeper.test;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public class ListGroup {
	public void list(String groupNmae) throws KeeperException,
			InterruptedException {
		String path = "/" + groupNmae;
		try {
			List<String> children = zk.getChildren(path, false);
			if (children.isEmpty()) {
				System.out.printf("No memebers in group %s\n", groupNmae);
				System.exit(1);
			}
			for (String child : children) {
				System.out.println(child);
			}
		} catch (KeeperException.NoNodeException e) {
			System.out.printf("Group %s does not exist \n", groupNmae);
			System.exit(1);
		}
	}

	public static void main(String[] args) throws Exception {
		ListGroup listGroup = new ListGroup();
		listGroup.connect();
		listGroup.list("");
		listGroup.close();
	}

	private void close() throws InterruptedException {
		zk.close();

	}

	private ZooKeeper zk;

	private void connect() throws Exception {
		zk = ZKConnection.connect();
	}
}
