package com.tangaoyu.act.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;


@Transactional
@Service
public class ActTest {
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	@Test
	public void test(){
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment()
		  .addClasspathResource("actTest/leave.bpmn")
		  .deploy();
		System.out.println("helloWorld success");
	}
	
	@Test
	public void createPro(){
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment()
		  .addClasspathResource("actTest/HelloWorld.bpmn")
		  .addClasspathResource("actTest/HelloWorld.png")
		  .deploy();
		System.out.println("helloWorld success");
	}
	
	@Test
	public void queryTask() throws SQLException{
		Connection conn = getConn();
		System.out.println(conn);
		PreparedStatement prepareStatement = conn.prepareStatement("insert into ACT_RE_DEPLOYMENT(ID_, NAME_, CATEGORY_, TENANT_ID_, DEPLOY_TIME_)  values(?, ?, ?, ?, ?)");
		prepareStatement.setObject(1, "3");
		prepareStatement.setObject(2, "3");
		prepareStatement.setObject(3, "4");
		prepareStatement.setObject(4, "5");
		prepareStatement.setDate(5, new Date(new java.util.Date().getTime()));
		prepareStatement.executeUpdate();
		/*TaskService taskService = processEngine.getTaskService();
		List<Task> list = taskService.createNativeTaskQuery().list();
		for(Task task :list){
			System.out.println(task.getName()+"");
		}*/
	}
	
	public Connection getConn()
	 {
	  Connection conn=null;
	  try {
	   Class.forName("com.mysql.jdbc.Driver");
	  } catch (ClassNotFoundException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	  String url="jdbc:mysql://localhost:3306/acttest";
	  try {
	   conn=DriverManager.getConnection(url, "root", "root");
	  } catch (SQLException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }  
	  return conn;
	 }
}
