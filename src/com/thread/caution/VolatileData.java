package com.thread.caution;

import java.util.Scanner;

public class VolatileData implements Runnable{

	//without volatile there is no guarantee that the running variable will have a common copy for any runnable thread
	private volatile boolean running = true;
	//with volatile it is guaranteed that running variable will not be cached, and so there will always be one common copy of running variable
	
	public void run(){
		while(running){
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Hello!");
		}
		System.out.println("Bye!");
	}
	
	public void shutdown(){
		running = false;
	}
	
	public static void main(String[] args) throws InterruptedException {
		VolatileData obj = new VolatileData();
		
		Thread t1 = new Thread(obj);
		
		t1.start();
		
		Scanner sc = new Scanner(System.in);
		
		sc.nextLine();
		
		obj.shutdown();
		
		t1.join();
		
	}

}