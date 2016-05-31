package com.thread.locks;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LibraryLock {

	private static int count = 0;

	public void increment() {
		for (int i = 0; i < 10000; i++) {
			count++;
		}
	}

	public static void main(String[] args) throws InterruptedException {

		LibraryLock lib = new LibraryLock();

		Lock lock = new ReentrantLock();

		Condition con = lock.newCondition();

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("Thread ID : " + Thread.currentThread().getId());
				lock.lock();
				try {
					con.await(); /// JUST LIKE WAIT
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					// System.out.println("thread-"+Thread.currentThread().getId()+"
					// -> " + i);
					lib.increment();
				} finally {
					lock.unlock();
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {

			@SuppressWarnings("resource")
			@Override
			public void run() {
				System.out.println("Thread ID : " + Thread.currentThread().getId());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lock.lock();
				System.out.println("Press retun to signal waiting threads");
				new Scanner(System.in).nextLine();
				con.signal();/// JUST LIKE NOTIFY
				try {
					// System.out.println("thread-"+Thread.currentThread().getId()+"
					// -> " + i);
					lib.increment();
					System.out.println("Thread ID : " + Thread.currentThread().getId() + ": Sleeping now");
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		});

		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println("Thread ID : " + Thread.currentThread().getId());
		System.out.println("final count value : " + count);
	}
}