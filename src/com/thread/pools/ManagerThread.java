package com.thread.pools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ManagerThread {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(15);
		long start = System.currentTimeMillis();
		System.out.println("Submitted new tasks to executor...");
		
		for(int i=0; i<15; i++){
			executor.submit(new Processor(i));
		}
		executor.shutdown();
		System.out.println("Completed task submission to executor...");
		
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("task execution completed...");
		long end = System.currentTimeMillis();
		System.out.println("Time taken:"+(end-start));
	}

}

class Processor implements Runnable{

	private int id;
	
	public Processor(int id) {
		super();
		this.id = id;
	}

	@Override
	public void run() {
		System.out.println("Start processing..."+this);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("End processing..."+this);
	}

	@Override
	public String toString() {
		return "Processor [thread-id="+Thread.currentThread().getId()+", id=" + id + "]";
	}
}
