package com.thread.locks;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLibraryLockResolvedApp {


	Lock aLock = new ReentrantLock();
	Lock bLock = new ReentrantLock(); 
	
	public static String getThreadId(){
		return "Thread-ID#"+Thread.currentThread().getId();
	}
	
	public void acquireLocks(Lock a, Lock b){
		System.out.println(getThreadId()+"-> acquiring locks...");
		boolean aState=false, bState=false;
		while(true){
			aState = a.tryLock();
			bState = b.tryLock();
			System.out.println(getThreadId()+"->"+aState+", "+bState);
			if(aState && bState){
				System.out.println(getThreadId()+"->acquired locks!");
				return;
			}else if(aState){
				a.unlock();
			}else if(bState){
				b.unlock();
			}else{
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void releaseLock(Lock a, Lock b){
		a.unlock();
		b.unlock();
		System.out.println(getThreadId()+"-> released locks!");
	}
	
	public void forward_money_movement(Account a, Account b, int value){
		
		acquireLocks(aLock, bLock);
		
		try{
			a.debit(value);
			b.credit(value);
		}finally{
			releaseLock(aLock, bLock);
		}
	}
	

	public void backward_money_movement(Account a, Account b, int value){
		acquireLocks(aLock, bLock);
		
		try{
			b.debit(value);
			a.credit(value);
		}finally{
			releaseLock(aLock, bLock);
		}
	}
	
	public static void main(String[] args) throws InterruptedException{
		Account a = new Account();
		Account b = new Account();
		
		DeadLibraryLockResolvedApp neft = new DeadLibraryLockResolvedApp();
		Random random = new Random();
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				neft.forward_money_movement(a, b, random.nextInt(100));
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				neft.backward_money_movement(a, b, random.nextInt(200));
			}
		});
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		System.out.println("Total balance : " + (a.getBalance() + b.getBalance()));
	}
	
}
