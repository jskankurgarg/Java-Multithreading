package com.thread.locks;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableFuture {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(5);

		Future<Integer> futureResult = executor.submit(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				System.out.println("inside thread#"+Thread.currentThread().getId()+" ...");
				
				Random random = new Random();

				int duration = random.nextInt(1000);

				if (duration > 500) {
					throw new IOException("Sleeping duration(" + duration + ") is too high!");
				}

				Thread.sleep(duration);
				return duration;
			}

		});

		System.out.println("Executor state : " + futureResult.isDone());

		executor.shutdown();

//		System.out.println("cancelling submitted task : " + futureResult.cancel(false));

		try {
			System.out.println("Duration : " + futureResult.get()); // will
																	// throw
																	// CancellationException
																	// when the
																	// submitted
																	// task is
																	// cancelled
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		System.out.println("Executor state : " + futureResult.isDone());
	}
}
