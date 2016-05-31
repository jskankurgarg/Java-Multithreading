package com.thread.locks;

import java.util.concurrent.Semaphore;

//Singleton
public class Connection {

	private static Connection instance = new Connection();
	
	private int openConnections = 0;
	
	private Connection(){}
	
	private Semaphore sem = new Semaphore(10);
	
	public static Connection getInstance(){
		return instance;
	}

	public void connect(){
		try {
			sem.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		try{
			doConnect();
		}finally{		
			sem.release();
		}
	}
	
	private void doConnect(){

		synchronized (this) {
			setOpenConnections(getOpenConnections() + 1);
			System.out.println("Open connections : "+ openConnections);
		}
		
		try {
			Thread.sleep(1000);// simulation for some processing 
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		synchronized (this) {
			setOpenConnections(getOpenConnections() - 1);
		}
	}

	public int getOpenConnections() {
		return openConnections;
	}

	public void setOpenConnections(int openConnections) {
		this.openConnections = openConnections;
	}
}
