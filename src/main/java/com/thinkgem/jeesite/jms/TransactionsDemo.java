package com.thinkgem.jeesite.jms;

public class TransactionsDemo {

	public static void main(String[] args) {
		String url = "tcp://localhost:61616";
		String user = null;
		String password = null;
		
		if (args.length >= 1) {
			url = args[0];
		}
		
		if (args.length >= 2) {
			user = args[1];
		}

		if (args.length >= 3) {
			password = args[2];
		}
		
		/*Retailer r = new Retailer(url, user, password);*/
		/*
	
		Supplier s2 = new Supplier("Monitor", "HelloWorldQueue", url, user, password);*/
		/*Vendor v = new Vendor(url, user, password);*/
		Supplier s1 = new Supplier("HardDrive", "HelloWorldQueue", url, user, password);
		new Thread(s1, "Supplier 1").start();
	/*	new Thread(r, "Retailer").start();
		new Thread(v, "Vendor").start();*/
		/*;
		new Thread(s2, "Supplier 2").start();*/
	}

}
