package com.thread.locks;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

///create 2 bank accounts and call transfer money api between 2 accounts parallely
public class DeadLibraryLockApp {

	Lock aLock = new ReentrantLock();
	Lock bLock = new ReentrantLock(); 
	
	public static String getThreadId(){
		return "Thread-ID#"+Thread.currentThread().getId();
	}
	
	public void forward_money_movement(Account a, Account b, int value){
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	//some processing simulation
		aLock.lock();
		System.out.println(getThreadId()+"-> aqcuired A-lock");

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	//some processing simulation
		bLock.lock();
		System.out.println(getThreadId()+"-> aqcuired B-lock");
		
		try{
			a.debit(value);
			b.credit(value);
		}finally{
			aLock.unlock();
			bLock.unlock();
		}
	}
	
	public void backward_money_movement(Account a, Account b, int value){
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	//some processing simulation
		bLock.lock();
		System.out.println(getThreadId()+"-> aqcuired B-lock");

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	//some processing simulation
		aLock.lock();
		System.out.println(getThreadId()+"-> aqcuired A-lock");
		
		try{
			b.debit(value);
			a.credit(value);
		}finally{
			aLock.unlock();
			bLock.unlock();
		}
	}
	
	public static void main(String[] args) throws InterruptedException{
		Account a = new Account();
		Account b = new Account();
		
		DeadLibraryLockApp neft = new DeadLibraryLockApp();
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


class Account{
	private int balance=1000;
	
	public int getBalance() {
		return balance;
	}

	public void debit(int value){
		balance -= value;
		System.out.println("debited : " + value+", " + this);
	}
	
	public void credit(int value){
		balance += value;
		System.out.println("credited : " + value+", " + this);
	}

	@Override
	public String toString() {
		return "Account [balance=" + balance + "]";
	}
}