package com.thinkgem.jeesite.common.commonPay.pay.wxpay;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * H5发起支付请求数据
 * Created by zl on 15-3-23.
 */
public class JsapiDto {

    private String appId=""; //公众号id
    private String timeStamp=""; //时间戳
    private String nonceStr=""; //随机字符串
    private String wx_package=""; //订单详情扩展字符串
    private String signType=""; //签名方式
    private String paySign=""; //签名

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getWx_package() {
        return wx_package;
    }

    public void setWx_package(String wx_package) {
        this.wx_package = wx_package;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }


    public String toMap(){
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        StringBuilder s=new StringBuilder("{");

        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                    if(s.length()>0){
                        s.append(" ");
                    }
                    s.append(field.getName());
                    s.append("=");
                    s.append(obj.toString());
//                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        s.append("}");
        return s.toString();
    }
}
