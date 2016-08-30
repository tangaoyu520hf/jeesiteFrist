package com.thinkgem.jeesite.common.quartz;

import org.apache.activemq.console.Main;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by tangaoyu on 2016/8/26.
 */
@Component
public class TaskFrist {

    public TaskFrist() {
        System.out.println("我奥你");
    }

    public  void  sendNotify(){
        TaskFrist taskFrist = new TaskFrist();
        System.out.println("草拟大爷");
    }

    public static void  main(String[] args){
        TaskFrist taskFrist = new TaskFrist();
    }
}
