package com.laz.test.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class BlockingQueueExample {
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue queue = new ArrayBlockingQueue(1024);
		
		Producer producer = new Producer(queue);
		Consumer consumer = new Consumer(queue);
		
		new Thread(producer).start();
		new Thread(consumer).start();
		
		Thread.sleep(4000);
	}
}

class Consumer implements Runnable {
	protected BlockingQueue queue = null;
	
	public Consumer(BlockingQueue queue) {
		this.queue = queue;
	}
	
	public void run() {
		try {
			System.out.println(queue.take());
			System.out.println(queue.take());
			System.out.println(queue.take());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class Producer implements Runnable{
	protected BlockingQueue queue  = null;
	
	public Producer(BlockingQueue queue) {
		this.queue= queue;
	}
	@Override
	public void run() {
		try {
			queue.put("1");
			Thread.sleep(1000);
			queue.put("2");
			Thread.sleep(1000);
			queue.put("3");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
