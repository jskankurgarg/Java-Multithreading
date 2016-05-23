package com.thread.caution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SynchronizeCollectionsWorker{
	private static Random random = new Random();
	
	private Object lock1 = new Object();
	private Object lock2 = new Object(); 
	
	private List<Integer> list1 = null;
	private List<Integer> list2 = null;

	public SynchronizeCollectionsWorker(){
		list1 = new ArrayList<>();
		list2 = new ArrayList<>();
	}
	
	public void stepOne(){
		synchronized(lock1){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			list1.add(random.nextInt(100));
		}
	}
	
	public synchronized void stepTwo(){
		synchronized(lock2){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			list2.add(random.nextInt(100));
		}
	}

	public static void main(String[] args) throws InterruptedException{
		
		SynchronizeCollectionsWorker scw = new SynchronizeCollectionsWorker();
		
		Thread t1 = new Thread(new Runnable(){
			public void run(){
				for(int i=0; i<1000; i++){
					scw.stepOne();
					scw.stepTwo();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable(){
			public void run(){
				for(int i=0; i<1000; i++){
					scw.stepOne();
					scw.stepTwo();
				}
			}
		});
		
		long start = System.currentTimeMillis();
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		long end = System.currentTimeMillis();
		
		System.out.println("Time taken: " + (end-start));
		
		System.out.println("List1 size:"+scw.list1.size() +"; List2 size:"+scw.list2.size());
	}
}
