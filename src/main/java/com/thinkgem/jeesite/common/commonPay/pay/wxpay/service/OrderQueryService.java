package com.thinkgem.jeesite.common.commonPay.pay.wxpay.service;


import com.thinkgem.jeesite.common.commonPay.pay.wxpay.Configure;
import com.thinkgem.jeesite.common.commonPay.pay.wxpay.protocol.OrderQueryReqData;

/**
 * 公众号支付--查询订单
 * User: zl
 * Date: 2015/03/20
 */
public class OrderQueryService extends BaseService{

    public OrderQueryService() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(Configure.ORDERQUERY_API);
    }

    /**
     * 请求支付服务
     * @param orderQueryReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public String request(OrderQueryReqData orderQueryReqData) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(orderQueryReqData);

        return responseString;
    }

}
