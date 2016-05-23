package com.thread.application;

import java.util.Scanner;

public class ThreadCoordinationByWaitNotify {

	
	public synchronized void produce(){
		System.out.println("Producing information...");
		
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Completed producing information.");
	}
	
	public void consume(){
		System.out.println("Consuming information...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		synchronized(this){
			System.out.println("Press return to notify waiting block..");
			Scanner sc = new Scanner(System.in);
			sc.nextLine();
			notify();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Completed consuming information...");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		
		ThreadCoordinationByWaitNotify wn = new ThreadCoordinationByWaitNotify();
		
		Thread t1 = new Thread(new Runnable(){
			public void run(){
				wn.produce();
			}
		});

		Thread t2 = new Thread(new Runnable(){
			public void run(){
				wn.consume();
			}
		});
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();

		System.out.println("Completed main thread..");
	}

}
