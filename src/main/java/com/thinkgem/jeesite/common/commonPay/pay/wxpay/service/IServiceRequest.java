package com.thinkgem.jeesite.common.commonPay.pay.wxpay.service;


import com.thinkgem.jeesite.common.commonPay.pay.wxpay.BBCRquestDto;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * Created by zl on 15-3-18.
 * 这里定义服务层需要请求器标准接口
 */
public interface IServiceRequest {
	//Service依赖的底层https请求器必须实现这么一个接口
    public String sendPost(String api_url, Object xmlObj) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException;

    //b2b2c支付时接口
    public String sendPost(String api_url, BBCRquestDto initRequest, Object xmlObj) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException;

}
