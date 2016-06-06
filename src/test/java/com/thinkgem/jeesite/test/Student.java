package com.thinkgem.jeesite.test;

public class Student {
	public Student(){
		System.out.println(this.getClass());
		System.out.println(this.getClass());
	}
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private StudentSon studentSon;
	
	public StudentSon getStudentSon() {
		return studentSon;
	}
	public void setStudentSon(StudentSon studentSon) {
		this.studentSon = studentSon;
	}
	
	public Student getTest(int i){
		if(i<2){
			i++;
			Student student = new Student();
			student.setName(i+"");
			return student.getTest(i);
		}
		return this;
	} 
	
	public void init(){
		System.out.println("出书画了");
	}
	
	
}
