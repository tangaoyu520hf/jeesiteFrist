package com.thinkgem.jeesite.common.commonPay.pay.alipay;

import com.thinkgem.jeesite.common.commonPay.pay.alipay.exception.AlipayExcetion;
import com.thinkgem.jeesite.common.commonPay.pay.alipay.exception.AlipayExcetionConstant;
import com.thinkgem.jeesite.common.commonPay.pay.alipay.exception.AlipayVerifyExction;
import com.thinkgem.jeesite.common.commonPay.pay.alipay.util.AlipayNotify;
import com.thinkgem.jeesite.common.commonPay.pay.alipay.util.AlipaySubmit;
import com.thinkgem.jeesite.common.commonPay.pay.alipay.util.UtilDate;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付宝 -> 单例类
 */
@SuppressWarnings("all")
public class AlipayHelperSingleton {

    private static final AlipayHelperSingleton singleton = new AlipayHelperSingleton();

    private AlipayHelperSingleton() {
    }

    public static AlipayHelperSingleton getInstance() {
        return singleton;
    }

    /**
     * 获得提交给支付宝的数据
     *
     * @param helper
     * @return
     * @throws AlipayExcetion version 1.0
     */
    public String findAlipayInfo(AlipayHelper helper, String buttonType) throws AlipayExcetion, Exception {
        Map<String, String> sParaTemp = new HashMap<String, String>();
        if (helper instanceof AlipayDirectHelper) {
            returnDirectPayHtmlText(sParaTemp, (AlipayDirectHelper) helper);
        } else if (helper instanceof AlipayPartnerHelper) {
            returnPartnerPayHtmlText(sParaTemp, (AlipayPartnerHelper) helper);
        } else {
            System.out.println(789);
        }
        //建立请求
        String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, buttonType, "确认");
        return sHtmlText;
    }

    /**
     * 即时交易,组织form表单
     *
     * @throws AlipayExcetion version 1.0
     */
    private void returnDirectPayHtmlText(Map<String, String> sParaTemp, AlipayDirectHelper helper) throws AlipayExcetion {
        sParaTemp.put("extra_common_param", helper.getExtra_common_param());
        returnCommonParamMap(sParaTemp, helper);
    }

    /**
     * 担保交易组织form表单
     *
     * @throws AlipayExcetion version 1.0
     */
    private void returnPartnerPayHtmlText(Map<String, String> sParaTemp, AlipayPartnerHelper helper) throws AlipayExcetion {
        returnCommonParamMap(sParaTemp, helper);
        sParaTemp.put("logistics_type", helper.getLogistics_type());
        sParaTemp.put("logistics_payment", helper.getLogistics_payment());
        sParaTemp.put("logistics_fee", helper.getLogistics_fee());
        sParaTemp.put("receive_name", helper.getReceive_name());
        sParaTemp.put("receive_address", helper.getReceive_address());
        sParaTemp.put("receive_mobile", helper.getReceive_mobile());
        sParaTemp.put("receive_zip", helper.getReceive_zip());
    }

    /**
     * 设置功能参数
     *
     * @param sParaTemp
     * @param helper    version 1.0
     */
    private void returnCommonParamMap(Map<String, String> map, AlipayHelper helper) {
        map.put("service", helper.getService());//接口名称,如,即时交易接口(),担保交易接口()
        map.put("partner", helper.getPartner());//合作身份者ID，以2088开头由16位纯数字组成的字符串
        map.put("key", helper.getKey());//商户的私钥
        map.put(AlipayConstant.INPUT_CHARSET, helper.getInput_charset());//字符编码
        map.put("payment_type", helper.getPayment_type());//支付类型,默认是1,取默认值就行
        map.put("notify_url", helper.getNotify_url());//异步请求地址,支付宝主动返回商户网站的地址,用户可根据返回的参数完成自己的业务逻辑;要返回支付宝一个值"success"或"fail",否则支付宝会在24小时之内联系发送请求
        map.put("return_url", helper.getReturn_url());//同步请求地址,支付宝调用该地址,完成交易后可以跳转到商户指定的成功或失败页面;
        map.put("show_url", helper.getShow_url());//商品展示地址
        map.put("seller_email", helper.getSeller_email());//商户注册时的邮箱
        map.put("out_trade_no", helper.getOut_trade_no());//发送给支付宝的订单号,支付宝会将该单号原值返回,商户网站可以根据该单号完成自己的业务操作
        map.put("price", helper.getPrice());//商品单价
        map.put("quantity", String.valueOf(helper.getQuantity()));//商品数量
        map.put("total_fee", String.valueOf(helper.getTotal_fee()));//商品总价,(当有商品单价和总价时,以单价乘以数量所得结果为准)
        map.put("subject", helper.getSubject());//商品名称
        map.put("body", helper.getBody());//商品描述信息
        /*if(helper instanceof AlipayDirectHelper) {
            map.put("extra_common_param", helper.getExtra_common_param());
		}*/
    }
    /*****************************************************以下代码为支付宝支付成功之后同步通知信息******************************************************************************/
    /**
     * <Strong>获得支付宝同步返回信息;</Strong><br/>获得map方式如:<Strong>Map requestParams = ServletActionContext.getRequest().getParameterMap();</Strong>
     * 当提交给支付宝的参数return_url不为空且为外网可以访问的正确地址时,该接口才会被支付宝被动调用;<br/>
     * 支付宝以get方式返回各种参数,我们需要获得所有的参数,所以可以使用request.getParameterMap方式<br/>
     * 获得一个map对象;我们将这些参数重新组织成一个map对象,然后调用支付宝提供的验证接口来验证这些参<br/>
     * 数是不是支付宝返回的数据,若验证通过则商户网站可以执行自己的业务逻辑,若是不通过则说明这些数据<br/>
     * 不是支付宝返回的结果;<br/>
     * 支付宝同步返回	<Strong>即时交易</Strong>	信息如下:<br/>
     * exterface, subject, sign_type, notify_type, out_trade_no, buyer_email, total_fee, buyer_id, trade_no,
     * notify_time, trade_status, sign, is_success, seller_id, notify_id, seller_email, payment_type;<br/>
     * 支付宝同步返回	<Strong>担保交易</Strong>	信息如下:<br/>
     * exterface, subject, sign_type, notify_type, out_trade_no, buyer_email, total_fee, buyer_id, trade_no,
     * notify_time, trade_status, sign, is_success, seller_id, seller_email, notify_id, payment_type;
     *
     * @param map
     * @throws AlipayExcetion               version 1.0
     * @throws UnsupportedEncodingException
     */
    public AlipayHelper findSynchronizedResult(Map map) throws AlipayVerifyExction, AlipayExcetion, UnsupportedEncodingException {
        if (map == null || map.size() == 0) {
            throw new AlipayExcetion(AlipayExcetionConstant.CODE_Ox000000002, AlipayExcetionConstant.CODE_Ox000000002_MSG);
        }
        Map<String, String> params = new HashMap<String, String>();
        boolean verify_result = verifyResponse(map, params,false);
        if (!verify_result) {//验证是否是支付宝的返回结果
            throw new AlipayVerifyExction(AlipayExcetionConstant.CODE_Ox000000000);
        }
        String trade_status = params.get("trade_status");
        if ("TRADE_FINISHED".equals(trade_status) || "TRADE_SUCCESS".equals(trade_status)) {
            String service = params.get("exterface");
            if (AlipayConstant.SERVICE_DIRECT.equals(service)) {//即时交易接口
                return returnSynchronizedAlipayDirectHelper(params);
            } else if (AlipayConstant.SERVICE_PARTNER.equals(service)) {//担保交易接口
                return returnSynchronizedAlipayPartnerHelper(params);
            } else {
                return null;
            }
        } else {
            throw new AlipayVerifyExction(AlipayExcetionConstant.CODE_Ox000000001, AlipayExcetionConstant.CODE_Ox000000001_MSG);
        }
    }

    /**
     * 验证是否是支付宝的返回结果
     *
     * @param map    支付宝返回数据,需要解析
     * @param params 重新组装的数据
     * @return
     * @throws UnsupportedEncodingException version 1.0
     */
    private boolean verifyResponse(Map map, Map<String, String> params,boolean isClient) throws UnsupportedEncodingException {
        String returnString = "";
        for (Iterator iter = map.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) map.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
            returnString += name + "=" + valueStr + ";";
            params.put(name, valueStr);
        }
        System.out.println("支付宝返回的所有参数,请记录该值,作为调试使用->" + returnString);
        boolean verify_result = AlipayNotify.verify(params,isClient);
        return verify_result;
    }

    /**
     * 返回即时交易结果
     *
     * @param map
     * @return AlipayDirectHelper
     * version 1.0
     */
    private AlipayDirectHelper returnSynchronizedAlipayDirectHelper(Map<String, String> map) {
        AlipayDirectHelper helper = new AlipayDirectHelper();
        returnSynchronizedAlipayCommonHelper(map, helper);
        return helper;
    }

    /**
     * 返回担保交易结果
     *
     * @param map
     * @return version 1.0
     */
    private AlipayPartnerHelper returnSynchronizedAlipayPartnerHelper(Map<String, String> map) {
        AlipayPartnerHelper helper = new AlipayPartnerHelper();
        returnSynchronizedAlipayCommonHelper(map, helper);
        return helper;
    }

    /**
     * 支付宝返回公共参数信息
     *
     * @param map
     * @param helper version 1.0
     */
    private void returnSynchronizedAlipayCommonHelper(Map<String, String> map, AlipayHelper helper) {
        helper.setService(map.get("exterface"));//接口类型
        helper.setOut_trade_no(map.get("out_trade_no"));//交易的唯一订单号
        helper.setSubject(map.get("subject"));//商品名称
        helper.setBuyer_email(map.get("buyer_email"));//买家支付宝账号
        helper.setTrade_no(map.get("trade_no"));//交易号,用于退款
        helper.setTrade_status(map.get("trade_status"));//交易状态
        helper.setSeller_email(map.get("seller_email"));//卖家支付宝账号
        helper.setIs_success(map.get("is_success"));//接口调用是否成功

        map.get("notify_id");//支付宝通知校验ID，商户可以用这个流水号询问支付宝该条通知的合法性。
        map.get("notify_time");//通知时间（支付宝时间）
        map.get("notify_type");//通知类型
        map.get("buyer_email");//买家支付宝账号
        map.get("seller_id");//卖家支付宝账户号
        map.get("buyer_id");//买家支付宝账号
        map.get("total_fee");//总金额
        map.get("sign");//签名
        map.get("sign_type");//签名类型
    }
    /*****************************************************以下代码为支付宝支付成功之后异步通知信息*********************************************************/
    /**
     * 获得支付宝异步返回信息;<br>
     * 支付宝异步返回信息如下:<br/>
     * 支付宝异步返回	<Strong>即时交易</Strong>	信息如下:<br/>
     * buyer_id, trade_no, use_coupon, notify_time, subject, sign_type, is_total_fee_adjust, notify_type, out_trade_no, gmt_payment,
     * trade_status, discount, sign, gmt_create, buyer_email, price, total_fee, seller_id, quantity, seller_email, notify_id, payment_type,<br/>
     * 支付宝异步返回	<Strong>担保交易</Strong>	信息如下:<br/>
     * buyer_id, trade_no, use_coupon, notify_time, subject, sign_type, is_total_fee_adjust, notify_type, out_trade_no, gmt_payment,
     * trade_status, discount, sign, gmt_create, buyer_email, price, total_fee, seller_id, quantity, seller_email, notify_id, payment_type,
     * logistics_type, logistics_fee, receive_address, receive_name, gmt_close, gmt_send_goods, receive_mobile, receive_zip, gmt_logistics_modify, logistics_payment,
     *
     * @param map
     * @param exterface 接口类型:担保交易(create_partner_trade_by_buyer)或即时交易(create_direct_pay_by_user)
     * @return
     * @throws AlipayVerifyExction
     * @throws AlipayExcetion
     * @throws UnsupportedEncodingException
     * @see 1.0
     */
    public AlipayHelper findUnSynchronizedResult(Map map, String exterface,boolean isClient) throws AlipayVerifyExction, AlipayExcetion, UnsupportedEncodingException {
        if (map == null || map.size() == 0) {
            throw new AlipayExcetion(AlipayExcetionConstant.CODE_Ox000000002, AlipayExcetionConstant.CODE_Ox000000002_MSG);
        }
        Map<String, String> params = new HashMap<String, String>();
        boolean verify_result = verifyResponse(map, params,isClient);
        if (!verify_result) {//验证是否是支付宝的返回结果
            throw new AlipayVerifyExction(AlipayExcetionConstant.CODE_Ox000000000);
        }
        String trade_status = params.get("trade_status");
        if (AlipayConstant.SERVICE_DIRECT.equals(exterface)) {//即时交易接口
            System.out.println("支付状态："+trade_status);
            if ("TRADE_FINISHED".equals(trade_status) || "TRADE_SUCCESS".equals(trade_status)) {
                return returnUnSynchronizedAlipayDirectHelper(params);
            } else {
                throw new AlipayVerifyExction(AlipayExcetionConstant.CODE_Ox000000001, AlipayExcetionConstant.CODE_Ox000000001_MSG);
            }
            //out.println("success");//以上各种操作最后都要给支付宝返回一个success,不然支付宝会连续发送24个小时
        } else if (AlipayConstant.SERVICE_PARTNER.equals(exterface)) {//担保交易接口
            if ("WAIT_BUYER_PAY".equals(trade_status)) {//等待买家付款,无需任何操作

            } else if ("WAIT_SELLER_SEND_GOODS".equals(trade_status)) {//等待卖家发货

            } else if ("WAIT_BUYER_CONFIRM_GOODS".equals(trade_status)) {//等待买家确认收货

            } else if ("TRADE_FINISHED".equals(trade_status)) {//交易完成

            } else {//其他

            }
            //out.println("success");//以上各种操作最后都要给支付宝返回一个success,不然支付宝会连续发送24个小时
            return returnUnSynchronizedAlipayPartnerHelper(params);
        } else {
            return null;
        }
    }

    /**
     * 即时交易的返回值
     *
     * @param map
     * @return version 1.0
     */
    private AlipayDirectHelper returnUnSynchronizedAlipayDirectHelper(Map<String, String> map) {
        AlipayDirectHelper helper = new AlipayDirectHelper();
        helper.setExtra_common_param(map.get("extra_common_param"));
        returnUnSynchronizedAlipayCommonHelper(map, helper);
        return helper;
    }

    /**
     * 担保交易的返回值
     *
     * @param map
     * @return version 1.0
     */
    private AlipayPartnerHelper returnUnSynchronizedAlipayPartnerHelper(Map<String, String> map) {
        AlipayPartnerHelper helper = new AlipayPartnerHelper();
        returnUnSynchronizedAlipayCommonHelper(map, helper);
        return helper;
    }

    /**
     * buyer_id, trade_no, use_coupon(是否使用红包买家), notify_time, subject, sign_type, is_total_fee_adjust(是否调整总价), notify_type, out_trade_no, gmt_payment,
     * trade_status, discount, sign, gmt_create, buyer_email, price, total_fee, seller_id, quantity, seller_email, notify_id, payment_type,
     *
     * @param map
     * @param helper version 1.0
     */
    private void returnUnSynchronizedAlipayCommonHelper(Map<String, String> map, AlipayHelper helper) {
        helper.setOut_trade_no(map.get("out_trade_no"));
        helper.setSubject(map.get("subject"));
        helper.setBuyer_email(map.get("buyer_email"));
        helper.setTrade_no(map.get("trade_no"));
        helper.setTrade_status(map.get("trade_status"));
        helper.setSeller_email(map.get("seller_email"));
        helper.setIs_success(map.get("is_success"));
        helper.setTotal_fee(map.get("total_fee"));
        helper.setBuyer_email("buyer_email");
        helper.setSeller_email("seller_id");

        map.get("notify_id");
        map.get("notify_time");
        map.get("notify_type");
        map.get("buyer_id");
        map.get("sign");
        map.get("sign_type");
    }

    /*****************************************************以下代码为支付宝退款信息******************************************************************************/
    /**
     * 退款操作:即时交易退款和担保交易退款
     *
     * @param helper
     * @param buttonType
     * @return
     * @throws AlipayExcetion
     * @see 1.0
     */
    public String findAliypayDirectRefund(AlipayHelper helper, String buttonType) throws AlipayExcetion {
        Map<String, String> sParaTemp = new HashMap<String, String>();
        if (helper instanceof AlipayDirectHelper) {//即时交易退款
            returnDirectRefundHtmlText(sParaTemp, (AlipayDirectHelper) helper);
        } else if (helper instanceof AlipayPartnerHelper) {//担保交易退款
            //returnPartnerPayHtmlText(sParaTemp, (AlipayPartnerHelper)helper);
        } else {
            System.out.println(789);
        }
        //建立请求
        String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, buttonType, "确认");
        return sHtmlText;
    }

    /**
     * 组织即时交易的退款参数
     *
     * @param map
     * @param helper
     * @see 1.0
     */
    private void returnDirectRefundHtmlText(Map<String, String> map, AlipayDirectHelper helper) {
        //把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        map.put("service", AlipayConstant.SERVICE_DIRECT_REFUND);
        map.put("partner", helper.getPartner());
        map.put(AlipayConstant.INPUT_CHARSET, helper.getInput_charset());
        map.put("notify_url", helper.getNotify_url());
        map.put("seller_email", helper.getSeller_email());
        map.put("batch_no", helper.getBatch_no());
        map.put("batch_num", String.valueOf(helper.getBatch_num()));
        map.put("detail_data", helper.getTrade_no() + "^" + helper.getAmount() + "^" + helper.getReason());
        map.put("refund_date", UtilDate.getDateFormatter());
    }

    /**
     * 支付宝返回退款信息:即时交易
     *
     * @param map
     * @return
     * @throws AlipayVerifyExction
     * @throws AlipayExcetion
     * @throws UnsupportedEncodingException
     * @see 1.0
     */
    public AlipayHelper findUnSynchronizedRefundResult(Map map) throws AlipayVerifyExction, AlipayExcetion, UnsupportedEncodingException {
        if (map == null || map.size() == 0) {
            throw new AlipayExcetion(AlipayExcetionConstant.CODE_Ox000000002, AlipayExcetionConstant.CODE_Ox000000002_MSG);
        }
        Map<String, String> params = new HashMap<String, String>();
        boolean verify_result = verifyResponse(map, params,false);
        if (!verify_result) {//验证是否是支付宝的返回结果
            throw new AlipayVerifyExction(AlipayExcetionConstant.CODE_Ox000000000);
        }
        if (AlipayNotify.verify(params,false)) {//验证成功
            String result_details = params.get("result_details");
            String success = result_details.substring(result_details.lastIndexOf("^") + 1, result_details.length());
            if ("success".equals(success.toLowerCase())) {
                AlipayDirectHelper helper = new AlipayDirectHelper();
                helper.setBatch_no(params.get("batch_no"));
                helper.setTrade_status(success);
                return helper;
            } else {
                return null;
            }
        } else {//验证失败
            throw new AlipayVerifyExction(AlipayExcetionConstant.CODE_Ox000000001, AlipayExcetionConstant.CODE_Ox000000001_MSG);
        }
    }
    /*****************************************************以下代码为支付宝发货信息******************************************************************************/
    /**
     * 卖家发货
     *
     * @param helper
     * @return
     * @see 1.0
     */
    public boolean findSendGoodsInfo(AlipayPartnerHelper helper, String buttonType) {
        //三个值可选：POST（平邮）、EXPRESS（快递）、EMS（EMS）
        //把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "send_goods_confirm_by_platform");
        sParaTemp.put("partner", helper.getPartner());
        sParaTemp.put(AlipayConstant.INPUT_CHARSET, helper.getInput_charset());
        sParaTemp.put("trade_no", helper.getTrade_no());
        sParaTemp.put("logistics_name", helper.getLogistics_name());
        sParaTemp.put("invoice_no", helper.getInvoice_no());
        sParaTemp.put("transport_type", helper.getTransport_type());
        sParaTemp.put("seller_ip", helper.getNotify_url());
        //建立请求
        String sHtmlText = "";
        try {
            sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}