package com.sunsheen.bigdata.zookeeper.demo;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import com.sunsheen.bigdata.zookeeper.test.ZKConnection;

/**
 * 带ACL权限认证的节点
 * 
 * @author laz
 *
 */
public class Demo8 extends ZookeeperMain {
	public static void main(String[] args) throws NoSuchAlgorithmException, KeeperException, InterruptedException {
		Demo8 d = new Demo8();
		d.create("/testZkACL", "Demo8".getBytes());
		d.close();
	}

	private void create(String path, byte[] data) throws NoSuchAlgorithmException, KeeperException, InterruptedException {
		// new一个acl
		List<ACL> acls = new ArrayList<ACL>();
		// 添加第一个id，采用用户名密码形式
		Id id1 = new Id("digest",
				DigestAuthenticationProvider.generateDigest("admin:admin"));
		ACL acl1 = new ACL(ZooDefs.Perms.ALL, id1);
		acls.add(acl1);
		// 添加第二个id，所有用户可读权限
		Id id2 = new Id("world", "anyone");
		ACL acl2 = new ACL(ZooDefs.Perms.READ, id2);
		acls.add(acl2);
		// Zk用admin认证，创建/test ZNode。
		zk.addAuthInfo("digest", "admin:admin".getBytes());
		zk.create(path, data, acls, CreateMode.PERSISTENT);

	}
}
