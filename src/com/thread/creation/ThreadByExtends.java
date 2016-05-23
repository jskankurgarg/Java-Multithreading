package com.thread.creation;

public class ThreadByExtends {

	public static void main(String[] args) {
		Worker w1 = new Worker(10);
		Thread t1 = new Thread(new Worker(80));
		
		t1.start();
		w1.start();
		
		try {
			t1.join();
			w1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}


class Worker extends Thread{
	private int id = 0;
	public Worker(){
		
	}
	public Worker(int id){
		this.id = id;
	}
	
	public void run(){
		for(int i=0; i<10; i++){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Hello from " + id + "..." + i);
		}
	}
}