package com.thinkgem.jeesite.common.commonPay.pay.wxpay;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * B2B2C支付请求参数
 * Created by zl on 15-3-23.
 */
public class BBCRquestDto {

    private String product_id =""; //商品id
    private String body =""; //商品描述
    private String outTradeNo =""; //订单号
    private int totalFee; //订单金额
    private String notifyUrl =""; //回调通知地址

    private String wx_appId =""; //公众号appId
    private String appSecret =""; //公众号appSecret
    private String mchID =""; //商户号
    private String wx_key =""; //商户秘钥
    private String certLocalPath =""; //证书路径（绝对）
    private String certPassword =""; //证书密码 (默认商户号)

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getWx_appId() {
        return wx_appId;
    }

    public void setWx_appId(String wx_appId) {
        this.wx_appId = wx_appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getMchID() {
        return mchID;
    }

    public void setMchID(String mchID) {
        this.mchID = mchID;
    }

    public String getWx_key() {
        return wx_key;
    }

    public void setWx_key(String wx_key) {
        this.wx_key = wx_key;
    }

    public String getCertLocalPath() {
        return certLocalPath;
    }

    public void setCertLocalPath(String certLocalPath) {
        this.certLocalPath = certLocalPath;
    }

    public String getCertPassword() {
        return certPassword;
    }

    public void setCertPassword(String certPassword) {
        this.certPassword = certPassword;
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
