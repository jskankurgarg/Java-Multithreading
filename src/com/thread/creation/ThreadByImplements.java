package com.thread.creation;

public class ThreadByImplements {

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new Runner());
		
		Thread t2 = new Thread(new Runnable(){
			public void run(){
				for(int i=0; i<10; i++){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Hello from " + Thread.currentThread().getId() + "..." + i);
				}
			}
		});
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
	}
}

class Runner implements Runnable{
	
	@Override
	public void run(){
		for(int i=0; i<10; i++){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Hello from " + Thread.currentThread().getId() + "..." + i);
		}
	}
}