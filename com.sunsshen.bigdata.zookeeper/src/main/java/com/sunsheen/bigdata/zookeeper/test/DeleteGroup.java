package com.sunsheen.bigdata.zookeeper.test;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;

public class DeleteGroup extends ConnectionWatcher{
	public void delete(String groupName) throws InterruptedException, KeeperException{
        String path="/"+groupName;
        List<String> children;
        try {
            children = zk.getChildren(path, false);
            
            //不能递归删除，只能手动写递归删除
            for(String child:children){
                zk.delete(path+"/"+child, -1);            
            }
            zk.delete(path, -1);
        } catch (KeeperException.NoNodeException e) {
            System.out.printf("Group %s does not exist\n", groupName);
            System.exit(1);
        }    
    }
    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        DeleteGroup deleteGroup = new DeleteGroup();
        deleteGroup.connect(ZKConnection.hosts);
        deleteGroup.delete("testZk");
        deleteGroup.close();
    }
}
