package com.thinkgem.jeesite.common.commonPay.pay.wxpay.service;

import com.pay.common.PayInfoUtil;
import com.pay.wxpay.*;
import com.pay.wxpay.protocol.UnifiedOrderDto;
import com.pay.wxpay.protocol.UnifiedOrderReqData;
import com.pay.wxpay.protocol.UnifiedOrderResData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import java.io.InputStream;

/**
 * Created by zl on 15-3-18.
 * 统一下单
 */
public class UnifiedOrderService extends BaseService{

	public UnifiedOrderService() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(Configure.UNIFIEDORDER_API);
    }

    public UnifiedOrderService(String httpsRequestClassName) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(Configure.UNIFIEDORDER_API,httpsRequestClassName);
    }


    /**
     * 请求支付服务
     * @param unifiedOrderReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public String request(UnifiedOrderReqData unifiedOrderReqData) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------

        String responseString = sendPost(unifiedOrderReqData);

        return responseString;
    }

    /**
     * 后台通知获取数据
     * @throws Exception
     */
    public UnifiedOrderDto getUnifiedOrderDto(HttpServletRequest request){
        return getUnifiedOrderDtoByPayType(request,Configure.getKey());
    }
    /**
     * 根据key来进行签名认真如果成功则返回参数
     * @param request
     * @return
     */
    public UnifiedOrderDto getUnifiedOrderDtoByKey(HttpServletRequest request,String key) {
        return getUnifiedOrderDtoByPayType(request,key);
    }

    /**
     * appkey来进行签名认真如果成功则返回参数
     * @param request
     * @return
     */
    public UnifiedOrderDto getUnifiedOrderDtoByApp(HttpServletRequest request) {
        return getUnifiedOrderDtoByPayType(request, Configure.getAppWxKey());
    }

    /**
     * 根据微信类型来验证及返回请求参数
     * @param request    请求数据
     * @param key    微信支付key
     * @return
     */
    private UnifiedOrderDto getUnifiedOrderDtoByPayType(HttpServletRequest request,String key) {
        UnifiedOrderDto unifiedOrderDto = new UnifiedOrderDto();
        try {
            ServletInputStream e = request.getInputStream();
            String resp = Util.inputStreamToString(e);
            System.out.println("-------------------------resp---->>" + resp);
            if(Signature.checkIsSignValidFromResponseString(resp,key)) {
                unifiedOrderDto = (UnifiedOrderDto)Util.getObjectFromXML(resp, UnifiedOrderDto.class);
                if(null!=unifiedOrderDto) {
                    unifiedOrderDto.setSignState("1");//验证成功
                }
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return unifiedOrderDto;
    }


    /**
     * b2b2c支付 后台通知获取数据
     * @param key 商户秘钥 为空不验证
     * @throws Exception
     */
    public UnifiedOrderDto getUnifiedOrderDtoBBC(HttpServletRequest request,String key){
        UnifiedOrderDto unifiedOrderDto = new UnifiedOrderDto();
        try {
            InputStream is = request.getInputStream();
            String resp =  Util.inputStreamToString(is);

            System.out.println("-------------------------resp---->>"+resp);
            if(key != null && !"".equals(key)){
                if(Signature.checkIsSignValidFromResponseString(resp,key)){
                    unifiedOrderDto = (UnifiedOrderDto)Util.getObjectFromXML(resp,UnifiedOrderDto.class);
                }
            }else{
                unifiedOrderDto = (UnifiedOrderDto)Util.getObjectFromXML(resp,UnifiedOrderDto.class);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return unifiedOrderDto;
    }

    /**
     * 统一下单
     * @param req
     * @return
     * @throws Exception
     */
    public UnifiedOrderResData unifiedOrder(UnifiedOrderReqData req) throws Exception{

        //公众号
        req.setAppid(PayInfoUtil.instants().getValue("wx_appId"));
        //商户号
        req.setMch_id(PayInfoUtil.instants().getValue("mchID"));
        //随机字符串
        req.setNonce_str(Signature.getRandomStringByLength(30));
        //请求ip
        req.setSpbill_create_ip(PayInfoUtil.instants().getValue("ip"));

        //支付签名
        String sign = Signature.getSign(req);

        req.setSign(sign);

        String resData = new UnifiedOrderService().request(req);

        UnifiedOrderResData res = (UnifiedOrderResData) Util.getObjectFromXML(resData, UnifiedOrderResData.class);
        return res;
    }

    /**
     * b2b2c支付 统一下单
     * @param bbcRquestDto
     * @return
     * @throws Exception
     */
    public UnifiedOrderResData unifiedOrderBBC(BBCRquestDto bbcRquestDto) throws Exception{

        UnifiedOrderReqData req = new UnifiedOrderReqData();
        //后台通知地址
        req.setNotify_url(bbcRquestDto.getNotifyUrl());
        //商品描述
        req.setBody(bbcRquestDto.getBody());
        //订单号
        req.setOut_trade_no(bbcRquestDto.getOutTradeNo());
        //商品id
        req.setProduct_id(bbcRquestDto.getProduct_id());
        //金额
        req.setTotal_fee(bbcRquestDto.getTotalFee());
        //交易类型
        req.setTrade_type("NATIVE");

        //公众号
        req.setAppid(bbcRquestDto.getWx_appId());
        //商户号
        req.setMch_id(bbcRquestDto.getMchID());
        //随机字符串
        req.setNonce_str(Signature.getRandomStringByLength(30));
        //请求ip
        req.setSpbill_create_ip(PayInfoUtil.instants().getValue("ip"));

        //支付签名
        String sign = Signature.getSign(req,bbcRquestDto.getWx_key());

        req.setSign(sign);

        String resData = sendPost(req,bbcRquestDto);

        UnifiedOrderResData res = (UnifiedOrderResData) Util.getObjectFromXML(resData, UnifiedOrderResData.class);
        return res;
    }
}
