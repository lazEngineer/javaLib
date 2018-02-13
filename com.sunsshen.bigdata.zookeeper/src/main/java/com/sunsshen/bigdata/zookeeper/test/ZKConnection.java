package com.sunsshen.bigdata.zookeeper.test;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class ZKConnection   {
	/** 
     * server列表, 以逗号分割 
     */  
    protected static String hosts = "192.168.23.130:2181,192.168.23.131:2181,192.168.23.132:2181";  
    /** 
     * 连接的超时时间, 毫秒 
     */  
    private static final int SESSION_TIMEOUT = 5000;  
    private static CountDownLatch connectedSignal = new CountDownLatch(1);  
    protected static ZooKeeper zk;  
  
    /** 
     * 连接zookeeper server 
     * @return 
     */  
    public static ZooKeeper connect() throws Exception {  
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, new ConnWatcher());  
        // 等待连接完成  
        connectedSignal.await();  
        return zk;
    }  
  
    public static class ConnWatcher implements Watcher {  
        public void process(WatchedEvent event) {  
            // 连接建立, 回调process接口时, 其event.getState()为KeeperState.SyncConnected  
            if (event.getState() == KeeperState.SyncConnected) {  
                // 放开闸门, wait在connect方法上的线程将被唤醒  
                connectedSignal.countDown();  
            }  
        }  
    }  
}
