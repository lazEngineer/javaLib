package com.sunsheen.bigdata.zookeeper.demo;

import java.util.List;

import org.apache.zookeeper.KeeperException;

public class Demo6 extends ZookeeperMain{
	public static void main(String[] args) throws InterruptedException, KeeperException {
		Demo6  d = new Demo6();
		d.list("/",0);
		d.close();
	}
	
	private void list(String path,int level) throws KeeperException, InterruptedException {
		List<String> children = zk.getChildren(path, false);
		if (!children.isEmpty()) {
			for (String child : children) {
				for(int i=0;i<level;i++) {
					System.out.print(" ");
				}
				System.out.print(child);
				System.out.println();
				if (!path.equals("/")) {
					list(path+"/"+child,level++);
				} else {
					list(path+child,level++);
				}
			}
		}
	}

}
