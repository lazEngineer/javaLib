package com.laz.test.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceDemo {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);  
		  
//		executorService.execute(new Runnable() {  
//		    public void run() {  
//		        System.out.println("Asynchronous task");  
//		    }  
//		});  
		  
//		Future future = executorService.submit(new Runnable() {  
//		    public void run() {  
//		    	try {
//					Thread.sleep(4000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		        System.out.println("Asynchronous task");  
//		    }  
//		});  
		  
		Future future = executorService.submit(new Callable(){  
		    public Object call() throws Exception {  
		        System.out.println("Asynchronous Callable");  
		        return "Callable Result";  
		    }  
		}); 
		System.out.println(future.get());  //returns null if the task has finished correctly.  
		executorService.shutdown();  
	}
}
