package com.thinkgem.jeesite.test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test2 {
	private final char first;
	private final char second;
	
	public static void main(String[] args) throws InterruptedException, ClassNotFoundException {
		Test2.class.forName("");
		ClassLoader parent = Test2.class.getClassLoader().getParent();
		System.out.println(parent);
		ClassLoader parent1 = String.class.getClassLoader();
		System.out.println(parent1);
/*		int availableProcessors = Runtime.getRuntime().availableProcessors();
		System.out.println(availableProcessors);
		ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
		Future<?> submit = executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(1234);
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					System.out.println("异常");
					Thread.currentThread().interrupt();
				}
				System.out.println(1234);
				Thread.currentThread().interrupt();
				System.out.println();
			}
		});
		executorService.shutdown();
		boolean awaitTermination = executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		System.out.println(awaitTermination+"66666666");*/
	}
	
	public Test2(char first,char second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public int hashCode() {
		System.out.println(31*first*second);
		return 31*first*second;
	}

	public boolean equals(Test2 obj) {
		System.out.println("到底神器");
		return super.equals(obj);
	}
	

	
}
