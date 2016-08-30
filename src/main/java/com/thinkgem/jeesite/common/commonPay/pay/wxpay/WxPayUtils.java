package com.thinkgem.jeesite.common.commonPay.pay.wxpay;

import com.thinkgem.jeesite.common.commonPay.pay.common.CommonConstants;
import com.thinkgem.jeesite.common.commonPay.pay.common.PayInfoUtil;
import com.thinkgem.jeesite.common.commonPay.pay.wxpay.protocol.OrderQueryReqData;
import com.thinkgem.jeesite.common.commonPay.pay.wxpay.protocol.OrderQueryResData;
import com.thinkgem.jeesite.common.commonPay.pay.wxpay.protocol.UnifiedOrderReqData;
import com.thinkgem.jeesite.common.commonPay.pay.wxpay.protocol.UnifiedOrderResData;
import com.thinkgem.jeesite.common.commonPay.pay.wxpay.service.OrderQueryService;
import com.thinkgem.jeesite.common.commonPay.pay.wxpay.service.UnifiedOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付统一调用工具
 * Created by zl on 15-3-20.
 */
public class WxPayUtils {
    /**
     * 查询订单
     * @param out_trade_no 商户系统的订单号
     * @return
     */
    public static OrderQueryResData getOrder(String out_trade_no){
        OrderQueryResData res = null;
        try {
            OrderQueryService service = new OrderQueryService();
            OrderQueryReqData req = new OrderQueryReqData();
            req.setAppid(PayInfoUtil.instants().getValue("wx_appId"));
            req.setMch_id(PayInfoUtil.instants().getValue("mchID"));
            req.setNonce_str(Signature.getRandomStringByLength(30));
            req.setOut_trade_no(out_trade_no);
            req.setSign(Signature.getSign(req));

            String respXml = service.request(req);
            res = (OrderQueryResData) Util.getObjectFromXML(respXml, OrderQueryResData.class);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("查询订单异常:"+out_trade_no);
        }

        return res;
    }


    /**
     * 扫码支付 --  模式二  (建议：支付成功后,请删除imgPhth图片)
     * @param product_id 商品id
     * @param body 商品描述
     * @param outTradeNo 订单号
     * @param totalFee 金额
     * @param notifyUrl 回调通知地址
     * @return map 扫码的返回数据  imgPath地址组成：根路径+配置的图片位置/+订单号+商品id+".png"
     */
    public static WxResult scanCodePay(String product_id,String body,String outTradeNo,int totalFee,String notifyUrl){
        WxResult result = new WxResult();
        Map<String,Object> map = new HashMap<String,Object>();

        UnifiedOrderReqData req = new UnifiedOrderReqData();
        //后台通知地址
        req.setNotify_url(notifyUrl);
        //商品描述
        req.setBody(body);
        //订单号
        req.setOut_trade_no(outTradeNo);
        //商品id
        req.setProduct_id(product_id);
        //金额
        req.setTotal_fee(totalFee);
        //交易类型
        req.setTrade_type("NATIVE");

        try {
            UnifiedOrderResData res = new UnifiedOrderService().unifiedOrder(req);
            if(res == null){
                result.setState(0);
                map.put("msg","请求异常");
            }else if(res.getReturn_code() != null && res.getResult_code() != null && "SUCCESS".equals(res.getReturn_code()) && "SUCCESS".equals(res.getResult_code())){
                result.setState(1);
                String code_url = res.getCode_url();
                String img_url = PayInfoUtil.instants().getValue("codeImgPath")+outTradeNo+".png";
                String imgPath = CommonConstants.ROOTPATH+img_url;
               /* new QRcodeUtil().encoderQRCode(code_url,imgPath);*/
                map.put("imgUrl",img_url);
                map.put("msg","请求微信下单成功");
                System.out.println("------code_url:"+code_url);
                System.out.println("------img_url:"+img_url);
            }else if(res.getReturn_msg() != null && !"OK".equals(res.getReturn_msg())){
                result.setState(0);
                map.put("msg",res.getReturn_msg());
            }else{
                result.setState(0);
                map.put("msg",res.getErr_code_des());
            }
        }catch (Exception e){
            result.setState(0);
            map.put("msg","扫码支付异常");
            e.printStackTrace();
            System.out.println("扫码支付错误");
        }
        result.setCont(map);
        return result;
    }
    
    /**
     * app支付
     * @param body 商品描述
     * @param outTradeNo 订单号
     * @param totalFee 金额
     * @param notifyUrl 回调通知地址
     * @return WxResult 扫码的返回数据 map中prepayId为预支付交易会话标识；msg信息；state状态1成功
     */
    public static WxResult appPay(String body,String outTradeNo,int totalFee,String notifyUrl){
        WxResult result = new WxResult();
        Map<String,Object> map = new HashMap<String,Object>();

        UnifiedOrderReqData req = new UnifiedOrderReqData();
        //后台通知地址
        req.setNotify_url(notifyUrl);
        //商品描述
        req.setBody(body);
        //订单号
        req.setOut_trade_no(outTradeNo);
        //金额
        req.setTotal_fee(totalFee);
        //交易类型
        req.setTrade_type("APP");


        try {
            //公众号
            req.setAppid(PayInfoUtil.instants().getValue("app_wx_appId"));
            //商户号
            req.setMch_id(PayInfoUtil.instants().getValue("app_mchID"));
            //随机字符串
            req.setNonce_str(Signature.getRandomStringByLength(30));
            //请求ip
            req.setSpbill_create_ip(PayInfoUtil.instants().getValue("ip"));

            //支付签名
            String sign = Signature.getSign(req,PayInfoUtil.instants().getValue("app_wx_key"));

            req.setSign(sign);

            String resData = new UnifiedOrderService(Configure.HttpsRequestClassName_APP).request(req);

            UnifiedOrderResData res = (UnifiedOrderResData) Util.getObjectFromXML(resData, UnifiedOrderResData.class);
            if(res == null){
                result.setState(0);
                map.put("msg","请求异常");
            }else if(res.getReturn_code() != null && res.getResult_code() != null && "SUCCESS".equals(res.getReturn_code()) && "SUCCESS".equals(res.getResult_code())){
                result.setState(1);
                String prepayId = res.getPrepay_id();
                map.put("prepayId",prepayId);
                map.put("msg","请求微信下单成功");
                System.out.println("------prepayId:"+prepayId);
            }else if(res.getReturn_msg() != null && !"OK".equals(res.getReturn_msg())){
                result.setState(0);
                map.put("msg",res.getReturn_msg());
            }else{
                result.setState(0);
                map.put("msg",res.getErr_code_des());
            }
        }catch (Exception e){
            result.setState(0);
            map.put("msg","app支付异常");
            e.printStackTrace();
            System.out.println("app支付异常");
        }
        result.setCont(map);
        return result;
    }
    
}
