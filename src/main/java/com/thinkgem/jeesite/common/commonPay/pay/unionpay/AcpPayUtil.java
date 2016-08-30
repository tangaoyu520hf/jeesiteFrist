package com.thinkgem.jeesite.common.commonPay.pay.unionpay;

import java.util.HashMap;
import java.util.Map;

import com.pay.common.PayInfoUtil;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKUtil;

public class AcpPayUtil extends AcpBase {

    /**
     * 网页跳转支付--消费类交易 发送请求
     * @param orderId 商户订单号，8-40位数字字母
     * @param txnTime 订单发送时间，取系统时间yyyyMMddHHmmss
     * @param txnAmt 交易金额，单位分
     * @param frontUrl 前台通知地址
     * @param backUrl 后台通知地址
     */
	public static String sendFrontConsumePay(String orderId,String txnTime,String txnAmt,String frontUrl,String backUrl) {

		//SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件

		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", "5.0.0");
		// 字符集编码 默认"UTF-8"
		data.put("encoding", "UTF-8");
		// 签名方法 01 RSA
		data.put("signMethod", "01");
		// 交易类型 01-消费
		data.put("txnType", "01");
		// 交易子类型 01:自助消费 02:订购 03:分期付款
		data.put("txnSubType", "01");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "08");
		// 前台通知地址 ，控件接入方式无作用
		data.put("frontUrl",frontUrl);
		// 后台通知地址
		data.put("backUrl", backUrl);
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码
		data.put("merId", PayInfoUtil.instants().getValue("merId"));
		// 商户订单号，8-40位数字字母
		data.put("orderId",orderId);
		// 订单发送时间，取系统时间yyyyMMddHHmmss
		data.put("txnTime", txnTime);
		// 交易金额，单位分
		data.put("txnAmt", txnAmt);
		// 交易币种
		data.put("currencyCode", "156");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		// data.put("reqReserved", "透传信息");
		// 订单描述，可不上送，上送时控件中会显示该信息
		// data.put("orderDesc", "订单描述");

		Map<String, String> submitFromData = signData(data);

		// 交易请求url 从配置文件读取
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();

		/**
		 * 创建表单
		 */
		String html = createHtml(requestFrontUrl, submitFromData);
        return html;
	}


    /**
     * 网页跳转支付--查询交易订单
     * @param orderId 订单（商户支付的订单）
     * @param txnTime 订单提交时间
     * @return
     */
    public static AcpOrderDto queryFront(String orderId,String txnTime){

        /**
         * 组装请求报文
         */
        Map<String, String> data = new HashMap<String, String>();
        // 版本号
        data.put("version", "5.0.0");
        // 字符集编码 默认"UTF-8"
        data.put("encoding", "UTF-8");
        // 签名方法 01 RSA
        data.put("signMethod", "01");
        // 交易类型
        data.put("txnType", "00");
        // 交易子类型
        data.put("txnSubType", "00");
        // 业务类型
        data.put("bizType", "000000");
        // 渠道类型，07-PC，08-手机
        data.put("channelType", "08");
        // 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
        data.put("accessType", "0");
        // 商户号码
        data.put("merId", PayInfoUtil.instants().getValue("merId"));
        // 商户订单号
        data.put("orderId",  orderId);
        // 订单发送时间
        data.put("txnTime", txnTime);

        data = signData(data);

        // 交易请求url 从配置文件读取
        String url = SDKConfig.getConfig().getSingleQueryUrl();

        Map<String, String> resmap = submitUrl(data, url);

        AcpOrderDto acpOrderDto = new AcpOrderDto(); //订单信息

        if (!SDKUtil.validate(resmap, encoding)) {
            LogUtil.writeLog("验证签名结果[失败].");
            acpOrderDto.setStatus(1);
        } else {
            LogUtil.writeLog("验证签名结果[成功].");
            acpOrderDto.setStatus(0);
        }
        System.out.println("应答报文=["+resmap.toString()+"]");
        acpOrderDto = (AcpOrderDto)getObjectByMap(AcpOrderDto.class,resmap);
        return acpOrderDto;
    }
}
