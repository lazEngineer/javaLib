package com.sunsheen.bigdata.zookeeper.test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class TestMainClient implements Watcher {
    protected static ZooKeeper zk = null;
    protected static Integer mutex;
    int sessionTimeout = 5000;
    protected String root;
    protected CountDownLatch connectedSemaphore = new CountDownLatch( 1 );   
    public TestMainClient(String connectString) {
        if(zk == null){
            try {
 
//                String configFile = this.getClass().getResource("/").getPath()+"org/zk/leader/election/log4j.xml";
//                DOMConfigurator.configure(configFile);
                System.out.println("创建一个新的连接:");
                zk = new ZooKeeper(connectString, sessionTimeout, this);
                connectedSemaphore.await();  
                mutex = new Integer(-1);
            } catch (Exception e) {
                zk = null;
            }
        }
    }
   synchronized public void process(WatchedEvent event) {
	   System.out.println( "收到事件通知：" + event.getState() +"\n"  );   
       if ( KeeperState.SyncConnected == event.getState() ) {   
           connectedSemaphore.countDown();   
       }   
        synchronized (mutex) {
            mutex.notify();
        }
    }

}
