package com.thread.latches;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LatchManager {

	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(5);
		
		ExecutorService executor = Executors.newFixedThreadPool(8);
		
		System.out.println("Before submitting tasks to count down latches...");
		
		for(int i=0; i<6; i++){
			executor.submit(new Processor(latch));
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Latches count down reached to ZERO..");
	}
}

class Processor implements Runnable{
	private CountDownLatch latch;

	public Processor(CountDownLatch latch) {
		super();
		this.latch = latch;
	}

	@Override
	public void run() {
		System.out.println("Start Processing... with .... thread-id:"+ Thread.currentThread().getId()+"; latch-count:"+latch.getCount());
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		latch.countDown();
		System.out.println("End Processing... with .... thread-id:"+ Thread.currentThread().getId()+"; latch-count:"+latch.getCount());
	}
}