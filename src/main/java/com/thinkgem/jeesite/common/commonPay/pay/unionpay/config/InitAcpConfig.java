package com.thinkgem.jeesite.common.commonPay.pay.unionpay.config;

import com.thinkgem.jeesite.common.commonPay.pay.unionpay.sdk.SDKConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;


public class InitAcpConfig extends HttpServlet implements
		ServletContextListener {

	//服务器关闭时执行
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("--------server is close");
    }

    //服务器启动时执行
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("--------server is start");

        try {
            SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
