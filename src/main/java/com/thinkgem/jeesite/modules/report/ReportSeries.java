package com.thinkgem.jeesite.modules.report;


import java.util.List;

/**
 * 报表数据对象
 * 
 * @author tangaoyu
 * 
 */
public class ReportSeries<T>{
    private String name;
    private String type;
    private String stack;
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }
}
