/*
package com.thinkgem.jeesite.modules.report;

import com.lucheng.brand.model.Brand;
import com.lucheng.goods.model.Goods;
import com.lucheng.goods.serv.GoodCategoryServ;
import com.lucheng.message.model.Message;
import com.lucheng.message.serv.MessageServ;
import com.lucheng.statistics.util.StatisticsUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * 统计服务
 * Created by YRS on 2016/4/19.
 *//*

@Service("StatisticsServ")
public class StatisticsServ {

    @Resource
    MessageServ messageServ;

    @Resource
    GoodCategoryServ goodsCateServ;

    private StatisticsUtil su;


    */
/**
     * 供应链统计数据列表
     *
     * @return
     *//*

    public Map<String, Object> supplyChainList(JSONObject params) {
        su = new StatisticsUtil(messageServ, params);
        Map<String, Object> map = new HashMap<String, Object>();
        if (params.containsKey("cat")) {
            params.remove("cat");
            JSONObject root_jo = goodsCateServ.cat2Json().getJSONObject("0");
            if (root_jo.containsKey("child"))
                for (Object child_o : root_jo.getJSONArray("child")) {
                    JSONObject child_jo = (JSONObject) child_o;
                    params.put("catId", child_jo.getString("catId"));
                    map.put(child_jo.getString("catId"), supplyChainList(params));
                }
            return map;
        } else {
            Map<String, Object> rs = new HashMap<String, Object>();
            String[] sum = {"SUM(supply_price)*10000 AS sum_supply_price", "SUM(record_money) AS sum_back_money", "SUM(pay) AS sum_pay", "SUM(profit) AS sum_over"};
            String fields = "SUM(r.record_money) AS record_money, supply_price, IF ( supply_price * 10000 < SUM(r.record_money), SUM(r.record_money) - supply_price * 10000, 0 ) AS profit, IF ( ISNULL(SUM(r.record_money)), supply_price * 10000, IF ( supply_price * 10000 >= SUM(r.record_money), supply_price * 10000 - SUM(r.record_money), 0 )) AS pay";
            String time = "messager_time";
            String table = new Message().table();
            table = table.concat(" AS m LEFT JOIN p_order_record AS r ON r.order_id = m.order_id AND r.record_status = 1 AND r.record_type IN (1, 3) ");

            String where = "messager_status = 2";

            String cat_where = "";
            //获取分类id
            if (params.containsKey("catId")) {
                JSONArray cat_ja = goodsCateServ.selectChildByParent(params.getString("catId"));
                cat_ja.add(params.getString("catId"));
                cat_where = " AND m.order_id IN ( SELECT IF ( parent_id = '0-0', order_id, parent_id ) FROM p_order_info AS oi, p_goods AS g WHERE ( oi.parent_id = m.order_id OR oi.order_id = m.order_id ) AND oi.goods_id = g.goods_id AND g.cat_id IN ".concat(cat_ja.toString().replaceAll("\\[", "(").replaceAll("]", ")")).concat(")");
                where = where.concat(cat_where);
            }

            String group = "m.order_id";
            Integer limit = 12;
            where = where.concat(su.str2LimitTime(time));
            List<Map<String, Object>> d = su.template(sum, fields, su.str2DateType(params.getString("type")), time, table, where, group, limit);
            String sql = "SELECT * FROM ( SELECT tmp.d, tmp.dt, SUM(supply_price) * 10000 AS sum_supply_price, SUM(record_money) AS sum_back_money, sum(pay) AS sum_pay, sum(profit) AS sum_over FROM ( SELECT sum(r.record_money) AS record_money, supply_price, IF ( supply_price * 10000 < sum(r.record_money), sum(r.record_money) - supply_price * 10000, 0 ) AS profit, IF ( ISNULL(SUM(r.record_money)), supply_price * 10000, IF ( supply_price * 10000 >= SUM(r.record_money), supply_price * 10000 - SUM(r.record_money), 0 )) AS pay, messager_time AS d, CONCAT( date_format( messager_time, '%Y年%m月%d日' )) AS dt FROM p_message AS m LEFT JOIN p_order_record AS r ON r.order_id = m.order_id AND r.record_status = 1 AND r.record_type IN (1, 3) WHERE messager_status = 2".concat(cat_where).concat(" GROUP BY m.order_id ) tmp ORDER BY tmp.dt ASC ) t");
//        List<Map<String, Object>> m = su.template(sum, StatisticsUtil.DateType.MONTH, time, table, where, limit);
//        List<Map<String, Object>> y = su.template(sum, StatisticsUtil.DateType.YEAR, time, table, where, limit);
//        List<Map<String, Object>> q = su.template(sum, StatisticsUtil.DateType.QUARTER, time, table, where, limit);
            rs.put("data", d);
            rs.put("total", messageServ.selectBySQL(sql).get(0));
            return rs;
        }
    }

    */
/**
     * 订单统计数据列表
     *
     * @return
     *//*

    public Map<String, Object> orderList(JSONObject params) {
        su = new StatisticsUtil(messageServ, params);
        JSONObject root_jo = goodsCateServ.cat2Json().getJSONObject("0");
        Map<String, Object> map = new HashMap<String, Object>();
        if (root_jo.containsKey("child"))
            for (Object child_o : root_jo.getJSONArray("child")) {
                JSONObject child_jo = (JSONObject) child_o;
                String[] sum = {"total_money", "oi.goods_number"};
                String time = "oi.ctime";
                String table = "p_order_info as oi,p_goods as g ";
                JSONArray cat_ja = goodsCateServ.selectChildByParent(child_jo.getString("catId"));
                cat_ja.add(child_jo.getString("catId"));
                String where = "oi.goods_id!=0 and oi.goods_id=g.goods_id and g.cat_id IN ".concat(cat_ja.toString().replaceAll("\\[", "(").replaceAll("]", ")"));
                where = where.concat(su.str2LimitTime(time));
                Integer limit = 12;
                List<Map<String, Object>> d = su.template(sum, su.str2DateType(params.getString("type")), time, table, where, limit);
                map.put(child_jo.getString("catId"), d);
            }
        return map;
    }
}
*/
