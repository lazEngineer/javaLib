package com.laz.test.concurrent;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueExample {
	public static void main(String[] args) throws InterruptedException {
		DelayQueue queue = new DelayQueue();

		Delayed element1 = new DelayedElement();

		queue.put(element1);

		System.out.println(queue.take());
	}
}

class DelayedElement implements Delayed {

	@Override
	public int compareTo(Delayed o) {
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return 1;
	}
	
}
