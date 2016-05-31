package com.thread.locks;

import java.util.Random;

public class InterruptingThreads {

	public static void main(String[] args) throws InterruptedException{
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Started thread # " + Thread.currentThread().getId());
				Random random = new Random();
				for(int i=0; i< 1E7; i++){
					Math.sin(random.nextDouble());
					/*if(Thread.currentThread().isInterrupted()){
						System.out.println("Thread # " +Thread.currentThread().getId()+" Interrupted!" );
						break;
					}*/
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						System.out.println(e.getMessage());
						System.out.println("Thread # " +Thread.currentThread().getId()+" Interrupted!" );
						break;
					}
				}
				System.out.println("Finished thread # " + Thread.currentThread().getId());
			}
		});
		
		
		t1.start();
		
		t1.interrupt();
		
		t1.join();
	}
}
