/*
package com.thinkgem.jeesite.modules.report;

import com.lucheng.base.act.BaseAct;
import com.lucheng.base.annotation.Auth;
import com.lucheng.message.serv.MessageServ;
import com.lucheng.statistics.serv.StatisticsServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * 统计管理
 * Created by YRS on 2016/4/18.
 *//*

@Controller
*/
/*@Auth(permissions = "statistics", description = "统计管理")*//*

@RequestMapping("/statistics")
@Description(value = "统计管理")
public class StatisticsAct extends BaseAct {

    @Autowired
    private StatisticsServ statisticsServ;

    @RequestMapping("supplyChainUI")
    @Description(value = "供应链服务统计页面")
    public String supplyChainUI() {
        return "statistics/supplychain";
    }

    @RequestMapping("supplyChainList")
    @ResponseBody
    @Description(value = "供应链服务统计页面数据")
    public Map<String, Object> supplyChainList() {
        preParam();
        return statisticsServ.supplyChainList(params);
    }
    @RequestMapping("orderUI")
    @Description(value = "订单统计页面")
    public String orderUI() {
        return "statistics/order";
    }

    @RequestMapping("orderList")
    @ResponseBody
    @Description(value = "订单统计页面数据")
    public Map<String, Object> orderList() {
        preParam();
        return statisticsServ.orderList(params);
    }

}
*/
