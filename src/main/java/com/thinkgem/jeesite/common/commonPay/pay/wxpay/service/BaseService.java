package com.thinkgem.jeesite.common.commonPay.pay.wxpay.service;


import com.thinkgem.jeesite.common.commonPay.pay.wxpay.BBCRquestDto;
import com.thinkgem.jeesite.common.commonPay.pay.wxpay.Configure;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * Created by zl on 15-3-18.
 * 服务的基类
 */
public class BaseService {

    //API的地址
    private String apiURL;

    //发请求的HTTPS请求器
    private IServiceRequest serviceRequest;

    public BaseService(String api) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        apiURL = api;
        Class c = Class.forName(Configure.HttpsRequestClassName);
        serviceRequest = (IServiceRequest) c.newInstance();
    }

    public BaseService(String api,String httpsRequestClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        apiURL = api;
        Class c = Class.forName(httpsRequestClassName);
        serviceRequest = (IServiceRequest) c.newInstance();
    }

    protected String sendPost(Object xmlObj) throws UnrecoverableKeyException, IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return serviceRequest.sendPost(apiURL,xmlObj);
    }

    /**
     * b2b2c 支付
     */
    protected String sendPost(Object xmlObj,BBCRquestDto bbcRquestDto) throws UnrecoverableKeyException, IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return serviceRequest.sendPost(apiURL,bbcRquestDto,xmlObj);
    }

    /**
     * 供商户想自定义自己的HTTP请求器用
     * @param request 实现了IserviceRequest接口的HttpsRequest
     */
    public void setServiceRequest(IServiceRequest request){
        serviceRequest = request;
    }
}
