package com.thread.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

//Resource is pool of DB connection objects, and there is only MAX number of resources available
//limit the resources usage at any time to MAX count
public class ResourceSemaphoreApp {

	public static void main(String[] args) throws InterruptedException{
		// Basics
		Semaphore sem = new Semaphore(1);
		System.out.println("semaphore permits : " + sem.availablePermits());
		sem.acquire();
		sem.release();
		System.out.println("semaphore permits : " + sem.availablePermits());
		
		//App
		Connection con = Connection.getInstance();
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		for(int i=0; i<50; i++){
			executor.submit(new Runnable() {
				
				@Override
				public void run() {
					System.out.println("spawning thread#"+Thread.currentThread().getId()+"...");
					con.connect();
				}
			});
		}
		
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.DAYS);
	}
	
}
