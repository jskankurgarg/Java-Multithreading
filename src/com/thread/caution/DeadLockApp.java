package com.thread.caution;

public class DeadLockApp {

	public static void main(String[] args) throws InterruptedException {
		Object lock1 = new Object();
		Object lock2 = new Object();
		
		Thread t1 = new Thread(new Runnable(){
			public void run(){
				System.out.println("Inside runnable ID : " + Thread.currentThread().getId());
				synchronized(lock1){
					System.out.println("acquired lock-1...");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("waiting for lock-2 to be available");
					synchronized(lock2){
						System.out.println("acquired lock-2...");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable(){
			public void run(){
				System.out.println("Inside runnable ID : " + Thread.currentThread().getId());
				synchronized(lock2){
					System.out.println("acquired lock-2...");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("waiting for lock-1 to be available");
					synchronized(lock1){
						System.out.println("acquired lock-1...");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		
		System.out.println("Inside thread ID:"+Thread.currentThread().getId()+"---> DEADLOCK ABOUT TO HAPPEN\n");
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
	}

}
