package com.thinkgem.jeesite.common.commonPay.pay.wxpay;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by xujiajun on 15/2/14.
 */
public class WxResult implements Serializable{

    private int state ;
    private Map<String,Object> cont;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Map<String, Object> getCont() {
        return cont;
    }

    public void setCont(Map<String, Object> cont) {
        this.cont = cont;
    }
}

