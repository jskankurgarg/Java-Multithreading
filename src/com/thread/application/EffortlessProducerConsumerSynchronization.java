package com.thread.application;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class EffortlessProducerConsumerSynchronization {

	public static void main(String[] args) throws InterruptedException{
		BlockingQueue<Integer> list = new ArrayBlockingQueue<>(10);
		
		Thread producer = new Thread(new Producer(list));
		Thread consumer = new Thread(new Consumer(list));
		
		producer.start();
		consumer.start();
		
		producer.join();
		consumer.join();
	}
}

class Producer implements Runnable{

	private BlockingQueue<Integer> list;
	
	public Producer(BlockingQueue<Integer> list) {
		super();
		this.list = list;
	}

	@Override
	public void run() {
		Random random = new Random();
		while(true){
			try {
				list.put(random.nextInt(100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}
}

class Consumer implements Runnable{

	private BlockingQueue<Integer> list;
	
	public Consumer(BlockingQueue<Integer> list) {
		super();
		this.list = list;
	}

	@Override
	public void run() {
		Random random = new Random();
		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(random.nextInt(10) == 0){
				try {
					System.out.println("Consuming..."+list.take()+"; list-size:"+list.size()+"; list:"+list);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}