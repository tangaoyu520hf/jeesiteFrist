package com.thinkgem.jeesite.test;

public class StudentSon extends Student {
	
	private String name;
	
	public static void main(String[] args) {
		StudentSon studentSon = new StudentSon();
		//System.out.println(studentSon);
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
