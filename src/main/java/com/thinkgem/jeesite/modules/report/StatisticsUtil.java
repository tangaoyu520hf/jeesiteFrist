/*
package com.thinkgem.jeesite.modules.report;

import com.lucheng.base.serv.BaseServ;
import com.lucheng.base.util.DataTimeUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

*/
/**
 * 统计工具
 * Created by YRS on 2016/4/19.
 *//*

public class StatisticsUtil {

    public StatisticsUtil(BaseServ serv) {
        this.serv = serv;
    }

    public StatisticsUtil(BaseServ serv, JSONObject params) {
        this.params = params;
        this.serv = serv;
    }

    private BaseServ serv;

    private JSONObject params;

    public BaseServ getServ() {
        return serv;
    }

    public void setServ(BaseServ serv) {
        this.serv = serv;
    }

    public JSONObject getParams() {
        return params;
    }

    public void setParams(JSONObject params) {
        this.params = params;
    }

    */
/**
     * 日期格式
     *//*

    public enum DateType {
        */
/**
         * 日
         *//*

        DAY("%Y年%m月%d日"),
        */
/**
         * 周
         *//*

        WEEK("WEEK"),
        */
/**
         * 月份
         *//*

        MONTH("%Y年%m月份"),
        */
/**
         * 季度
         *//*

        QUARTER("QUARTER"),
        */
/**
         * 年份
         *//*

        YEAR("%Y年份");

        private final String value;

        DateType(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }

        public static DateType value(String value) {
            for (DateType val : DateType.values()) {
                if (val.value().equals(value))
                    return val;
            }
            return null;
        }
    }

    public List<Map<String, Object>> template(String[] sumfields, DateType dt, String time, String table, String where, Integer limit) {
        return template(sumfields, null, dt, time, table, where, null, limit);
    }

    public List<Map<String, Object>> template(String[] sumfields, String fields, DateType dt, String time, String table, String where, Integer limit) {
        return template(sumfields, fields, dt, time, table, where, null, limit);
    }

    */
/**
     * 统计模板
     *
     * @param sumfields 统计字段
     * @param fields    查询字段
     * @param dt
     * @param time
     * @param table
     * @param where
     * @param group
     * @param limit
     * @return
     *//*

    public List<Map<String, Object>> template(String[] sumfields, String fields, DateType dt, String time, String table, String where, String group, Integer limit) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ( SELECT tmp.d,tmp.dt,");
        int i = 1;
        for (String sumfield : sumfields) {
            if (!sumfield.contains("SUM(")) {
                sb.append("SUM(");
                if (sumfield.contains("."))
                    sumfield = sumfield.substring(sumfield.indexOf(".") + 1);
                sb.append(sumfield);
                sb.append(")");
                sb.append(" AS ");
                sb.append("sum_");
            }
            sb.append(sumfield);
            if (i < sumfields.length)
                sb.append(",");
            i++;
        }
        sb.append(" FROM ( SELECT ");
        if (StringUtils.isEmpty(fields))
            for (String sumfield : sumfields) {
                sb.append(sumfield);
                sb.append(",");
            }
        else {
            sb.append(fields);
            sb.append(",");
        }
//        sb.append(",");
        sb.append(time);
        sb.append(" AS d");
        sb.append(", CONCAT( date_format(");
        sb.append(time);

        if (dt.equals(DateType.QUARTER)) {
            sb.append(", '");
            sb.append(DateType.YEAR.value());
            sb.append("'");
            sb.append("), '第', CONVERT(QUARTER (");
            sb.append(time);
            sb.append("),CHAR), '季度'");
        } else if (dt.equals(DateType.WEEK)) {
            sb.append(", '");
            sb.append(DateType.YEAR.value());
            sb.append("'");
            sb.append("), '第',CONVERT(WEEK (");
            sb.append(time);
            sb.append(",1),CHAR), '周'");
        } else {
            sb.append(", '");
            sb.append(dt.value());
            sb.append("')");
        }
        sb.append(" ) AS dt FROM ");
        sb.append(table);
        if (StringUtils.isNotEmpty(where)) {
            sb.append(" WHERE ");
            sb.append(where);
        }
        if (StringUtils.isNotEmpty(group)) {
            sb.append(" GROUP BY ");
            sb.append(group);
        }
        sb.append(" ) tmp GROUP BY tmp.dt ORDER BY tmp.dt ASC ) t LIMIT ");
        sb.append(limit);
        List<Map<String, Object>> list = serv.selectBySQL(sb.toString());
        String dts = "", temd = "";
        Date fd = new Date();
        List<Map<String, Object>> all_list = new ArrayList<Map<String, Object>>();
        SimpleDateFormat df;
        Map<String, Object> limit_map = new LinkedHashMap<String, Object>();
        boolean stb = params.containsKey("stime") && StringUtils.isNotEmpty(params.getString("stime"));
        boolean etb = params.containsKey("etime") && StringUtils.isNotEmpty(params.getString("etime"));

        Map<String, Object> rs;

        int add_i = limit - list.size();
        Calendar ca = Calendar.getInstance();
        df = new SimpleDateFormat("yyyy-MM-dd");
        Date stime = null, etime = null;
        if (list.size() > 0) {
            dts = list.get(0).get("d").toString();
        }
        try {
            if (list.size() > 0) {
                etime = df.parse(list.get(list.size() - 1).get("d").toString());
            }
            if (StringUtils.isNotEmpty(dts))
                fd = df.parse(dts);
            if (stb)
                stime = df.parse(params.getString("stime"));
            if (etb)
                etime = df.parse(params.getString("etime"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Integer type = null;
        Integer daysOfTwo = 0, yq1 = 0, yq2 = 0;
        switch (dt) {
            case DAY:
                df = new SimpleDateFormat("yyyy年MM月dd日");
                type = Calendar.DATE;
                if (stb) {
                    if (null == etime)
                        etime = new Date();
                    ca.setTime(stime);
                    daysOfTwo = DataTimeUtil.daysOfTwo(stime, etime).intValue();
                }
                break;
            case MONTH:
                df = new SimpleDateFormat("yyyy年MM月份");
                type = Calendar.MONTH;
                int year = 0;
                if (stb) {
                    if (null == etime)
                        etime = new Date();
                    ca.setTime(etime);
                    daysOfTwo = ca.get(type);
                    year = ca.get(Calendar.YEAR);
                    ca.setTime(stime);
                    year = year - ca.get(Calendar.YEAR);
                    daysOfTwo = daysOfTwo - ca.get(type);
                    daysOfTwo += year * 12;
                }
                break;
            case YEAR:
                df = new SimpleDateFormat("yyyy年份");
                type = Calendar.YEAR;
                if (stb) {
                    if (null == etime)
                        etime = new Date();
                    ca.setTime(etime);
                    daysOfTwo = ca.get(type);
                    ca.setTime(stime);
                    daysOfTwo = daysOfTwo - ca.get(type);
                }
                break;
            case QUARTER:
                df = new SimpleDateFormat("yyyy年份");
                add_i = limit;
                if (null == etime)
                    etime = new Date();
                if (stb) {
                    ca.setTime(etime);
                    daysOfTwo = ca.get(Calendar.MONTH);
                    year = ca.get(Calendar.YEAR);
                    ca.setTime(stime);
                    year = year - ca.get(Calendar.YEAR);
                    daysOfTwo = daysOfTwo - ca.get(Calendar.MONTH);
                    daysOfTwo += year * 12;
                    add_i = daysOfTwo / 3;
                    add_i += 1;
                }


                if (stb) {
                    fd = stime;
                } else {
                    ca.setTime(etime);
                    ca.add(Calendar.MONTH, -(add_i - 1) * 3);
                    fd = ca.getTime();
                }
                for (int i1 = 0 ; i1 < add_i ; i1++) {

                    ca.setTime(fd);
                    yq1 = ca.get(Calendar.YEAR) * 10 + getSeason(fd);
                    ca.setTime(etime);
                    yq2 = ca.get(Calendar.YEAR) * 10 + getSeason(etime);
                    if (yq1 > yq2)
                        break;
                    ca.setTime(fd);
                    Map<String, Object> map = new HashMap<String, Object>();

                    map.put("dt", df.format(fd) + "第" + getSeason(fd) + "季度");
                    ca.add(Calendar.MONTH, 3);
                    fd = ca.getTime();
                    limit_map.put(map.get("dt").toString(), map);
                }
                break;
            case WEEK:
                df = new SimpleDateFormat("yyyy年份");
                type = Calendar.WEEK_OF_YEAR;
                if (null == etime)
                    etime = new Date();
                add_i = limit;
                if (stb) {
                    daysOfTwo = new Long((etime.getTime() - stime.getTime()) / (1000 * 3600 * 24 * 7)).intValue();
                    add_i = daysOfTwo + 1;
                }
                if (stb) {
                    fd = stime;
                } else {
                    ca.setTime(etime);
                    ca.add(Calendar.DATE, -(add_i - 1) * 7);
                    fd = ca.getTime();
                }
                for (int i1 = 0 ; i1 < add_i ; i1++) {

                    ca.setTime(fd);
                    yq1 = ca.get(Calendar.YEAR) * 100 + ca.get(type);
                    ca.setTime(etime);
                    yq2 = ca.get(Calendar.YEAR) * 100 + ca.get(type);
                    if (yq1 > yq2)
                        break;
                    ca.setTime(fd);
                    Map<String, Object> map = new HashMap<String, Object>();

                    map.put("dt", df.format(fd) + "第" + ca.get(type) + "周");
                    ca.add(type, 1);
                    fd = ca.getTime();
                    limit_map.put(map.get("dt").toString(), map);
                }
                break;
            default:
                break;
        }

        //计算时间区间内的统计数据
        if (limit_map.size() == 0) {
            if (!stb) {
                if (null == etime)
                    etime = new Date();
                ca.setTime(etime);
                limit -= 1;
                ca.add(type, -limit);
                daysOfTwo = limit;
            }

            if (null != type)
                for (int k = 0 ; k <= daysOfTwo ; k++) {
                    rs = new HashMap<String, Object>();
                    temd = df.format(ca.getTime());
                    rs.put("dt", temd);
                    limit_map.put(temd, rs);
                    ca.add(type, 1);
                }
        }

        if (null != type)
            for (int i1 = 0 ; i1 < add_i ; i1++) {
                ca.setTime(fd);
                ca.add(type, -1);
                Map<String, Object> map = new HashMap<String, Object>();
                fd = ca.getTime();
                map.put("dt", df.format(fd));
                all_list.add(map);
            }
        if (limit_map.size() > 0) {
            String mkey;
            if (!list.isEmpty()) {
                for (Map<String, Object> om : list) {
                    mkey = om.get("dt").toString();
                    if (limit_map.containsKey(mkey))
                        limit_map.put(mkey, om);
                }
            }
            all_list.clear();
            for (Object o : limit_map.values()) {
                all_list.add((Map<String, Object>) o);
            }
        } else {
            Collections.reverse(all_list);
            all_list.addAll(list);
        }
        return all_list;
    }

    */
/**
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     *
     * @param date
     * @return
     *//*

    public static int getSeason(Date date) {

        int season = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    */
/**
     * 输入参数类型转时间类型
     *
     * @param type
     * @return
     *//*

    public DateType str2DateType(String type) {
        DateType dt = DateType.DAY;
        if (type.equals("d"))
            dt = DateType.DAY;
        if (type.equals("w"))
            dt = DateType.WEEK;
        if (type.equals("m"))
            dt = DateType.MONTH;
        if (type.equals("q"))
            dt = DateType.QUARTER;
        if (type.equals("y"))
            dt = DateType.YEAR;
        return dt;
    }

    */
/**
     * 输入参数时间区间转时间条件
     *
     * @param time
     * @return
     *//*

    public String str2LimitTime(String time) {
        String where = "";
        if (params.containsKey("stime") && StringUtils.isNotEmpty(params.getString("stime"))) {
            where = where.concat(" and ").concat(time).concat(">='").concat(params.getString("stime")).concat(" 24:00:00'");
        }
        if (params.containsKey("etime") && StringUtils.isNotEmpty(params.getString("etime"))) {
            where = where.concat(" and ").concat(time).concat("<='").concat(params.getString("etime")).concat(" 24:00:00'");
        }
        return where;
    }
}
*/
