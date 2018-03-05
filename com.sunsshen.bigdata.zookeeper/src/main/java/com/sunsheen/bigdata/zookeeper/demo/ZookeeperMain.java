package com.sunsheen.bigdata.zookeeper.demo;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

import com.sunsheen.bigdata.zookeeper.test.ZKConnection.ConnWatcher;

public class ZookeeperMain {

	/** 
     * server列表, 以逗号分割 
     */  
    public static String hosts = "172.18.130.100:2181,172.18.130.101:2181,172.18.130.102:2181";  
    /** 
     * 连接的超时时间, 毫秒 
     */  
    public static final int SESSION_TIMEOUT = 5000;  
    public static CountDownLatch connectedSignal = new CountDownLatch(1);  
    protected  ZooKeeper zk;  
	public ZookeeperMain() {
		try {
			connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    /** 
     * 连接zookeeper server 
     * @return 
     */  
    public  ZooKeeper connect() throws Exception {  
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, new ConnWatcher());  
        // 等待连接完成  
        connectedSignal.await();  
        return zk;
    }  
  
    public  class ConnWatcher implements Watcher {  
        public void process(WatchedEvent event) { 
        	//Zookeeper事件监听
            // 连接建立, 回调process接口时, 其event.getState()为KeeperState.SyncConnected  
            if (event.getState() == KeeperState.SyncConnected) {  
                // 放开闸门, wait在connect方法上的线程将被唤醒  
                connectedSignal.countDown();  
            }  
        }  
    }  
    protected void close() throws InterruptedException {
		zk.close();
	}
}
