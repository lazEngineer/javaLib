package com.sunsheen.bigdata.zookeeper.demo;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

/**
 * 删除节点
 * 
 * @author laz
 *
 */
public class Demo2 extends ZookeeperMain {
	public static void main(String[] args) throws IOException,
			InterruptedException, KeeperException {
		Demo2 d = new Demo2();
		d.delete("testZk");
		System.out.println("删除成功");
		d.close();
	}

	private void delete(String groupName) throws KeeperException,
			InterruptedException {
		String path = "/" + groupName;
		List<String> children;
		try {
			children = zk.getChildren(path, false);

			// 不能递归删除，只能手动写递归删除,如果节点存在子节点，必须先删除子节点
			for (String child : children) {
				zk.delete(path + "/" + child, -1);
			}
			zk.delete(path, -1);
		} catch (KeeperException.NoNodeException e) {
			System.out.printf("Group %s does not exist\n", groupName);
			System.exit(1);
		}
	}
}
