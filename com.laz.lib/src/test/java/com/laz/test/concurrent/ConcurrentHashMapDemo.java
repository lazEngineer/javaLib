package com.laz.test.concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentHashMapDemo {
	public static void main(String[] args) {
		ConcurrentMap concurrentMap = new ConcurrentHashMap();

		concurrentMap.put("key", "value");

		Object value = concurrentMap.get("key");
		System.out.println(value);
	}
}
