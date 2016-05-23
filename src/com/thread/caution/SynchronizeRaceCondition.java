package com.thread.caution;

public class SynchronizeRaceCondition implements Runnable{

	private static int count = 0;
	
	@Override
	public void run() {
		System.out.println("Thread ID: " + Thread.currentThread().getId());
		for(int i=0; i<10000; i++)
			increment();
	}
	
	//without synchronized keyword, we will get inconsistent values for count
	public synchronized void increment(){
		count++;
	}
	
	public static void main(String[] args) throws InterruptedException{
		System.out.println("Thread ID: " + Thread.currentThread().getId());
		SynchronizeRaceCondition src = new SynchronizeRaceCondition();
		Thread t1 = new Thread(src);
		Thread t2 = new Thread(src);
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		System.out.println("Count : " + count);
		
	}

}
