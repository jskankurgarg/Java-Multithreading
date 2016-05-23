package com.thread.application;

import java.util.ArrayList;
import java.util.Random;

public class EffortFulProducerConsumerSynchronization {

	public static void main(String[] args) throws InterruptedException{
		BlockingList list = new BlockingList(10);
		
		Thread Giver = new Thread(new Giver(list));
		Thread Taker = new Thread(new Taker(list));
		
		Giver.start();
		Taker.start();
		
		Giver.join();
		Taker.join();
	}
}

class Giver implements Runnable{

	private BlockingList list;
	
	public Giver(BlockingList list) {
		super();
		this.list = list;
	}

	@Override
	public void run() {
		Random random = new Random();
		while(true){
				list.put(random.nextInt(100));
		}
	}
}

class Taker implements Runnable{

	private BlockingList list;
	
	public Taker(BlockingList list) {
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
				System.out.println("Consuming..."+list.take()+"; list-size:"+list.size()+"; list:"+list);
			}
		}
	}
}


class BlockingList{
	
	private int capacity = 0;
	
	private ArrayList<Integer> list;
	
	public BlockingList(int capacity){
		this.capacity = capacity;
		list = new ArrayList<>(capacity);
	}
	
	public synchronized Integer take(){
		Integer i;
		if(list.size() == 0){
			try {
				System.out.println("BlockingList is EMPTY, waiting for it to be filled!");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i = list.remove(0);
		}else{
			i = list.remove(0);
			
		}
		notify();
		return i;
	}
	
	public synchronized void put(Integer i){
		if(list.size() < capacity){
			list.add(i);
		}else{
			try {
				System.out.println("BlockingList is FULL, waiting for it to be emptied!");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			list.add(i);
		}
		notify();
	}
	
	public synchronized int size(){
		return list.size();
	}

	@Override
	public synchronized String toString() {
		return "BlockingList [capacity=" + capacity + ", list=" + list + "]";
	}
}