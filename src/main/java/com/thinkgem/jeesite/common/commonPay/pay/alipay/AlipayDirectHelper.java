package com.thinkgem.jeesite.common.commonPay.pay.alipay;

import java.util.Date;

import com.pay.alipay.util.StringUtil;

/**
 * 支付宝即时交易数据对象
 */
public class AlipayDirectHelper extends AlipayHelper {
    /**
     * 默认支付方式:creditPay(信用支付)和directPay(余额支付),默认识别为余额支付
     */
    private String paymethod;
    /**
     * 支付渠道:控制收银台支付渠道显示
     */
    private String enable_paymethod;
    /**
     * 分润账号集
     */
    private String royalty_parameters;
    /**
     * 请求出错时的通知页面路径
     */
    private String error_notify_url;
    /**
     * 退款日期
     */
    private Date refund_date;
    /**
     * 退款批次号
     */
    private String batch_no;
    /**
     * 退款笔数
     */
    private int batch_num;
    /**
     * 退款金额
     */
    private double amount;
    /**
     * 退款原因
     */
    private String reason;

    public String getPaymethod() {
        return paymethod;
    }

    /**
     * <Strong>默认支付方式,可空;默认为directPay</Strong><br/>
     * 取值范围：
     * creditPay(信用支付)directPay(余额支付)
     * 如果不设置，默认识别为余额支付。<br/>
     * 说明：
     * <Strong>必须注意区分大小写</Strong>。
     *
     * @param paymethod version 1.0
     */
    public void setPaymethod(String paymethod) {
        if (StringUtil.isEmpty(paymethod)) {
            paymethod = AlipayConstant.SERVICE_DIRECT_DIRECT_PAY;
        }
        this.paymethod = paymethod;
    }

    public String getEnable_paymethod() {
        return enable_paymethod;
    }

    /**
     * @param enable_paymethod version 1.0
     */
    public void setEnable_paymethod(String enable_paymethod) {
        this.enable_paymethod = enable_paymethod;
    }

    public String getRoyalty_parameters() {
        return royalty_parameters;
    }

    /**
     * 分润账号集
     *
     * @param royalty_parameters version 1.0
     */
    public void setRoyalty_parameters(String royalty_parameters) {
        this.royalty_parameters = royalty_parameters;
    }

    public String getError_notify_url() {
        return error_notify_url;
    }

    /**
     * <Strong>请求出错时的通知页面路径,可空</Strong><br/>
     * 当商户通过该接口发起请求时,如果出现提示报错,支付宝会根据item_orders_info出错时的通知错误码和请求出错时的通知错误码,通过异步的方式发送通知给商户;<br/>
     * 该功能需要联系支付宝开通。
     * 最大长度200位
     *
     * @param error_notify_url version 1.0
     */
    public void setError_notify_url(String error_notify_url) {
        this.error_notify_url = error_notify_url;
    }

    /**
     * 即时交易接口,不可空,默认为create_direct_pay_by_user
     *
     * @param service V1.0
     */
    @Override
    public void setService(String service) {
        if (StringUtil.isEmpty(service)) {
            service = AlipayConstant.SERVICE_DIRECT;
        }
        super.setService(service);
    }

    @Override
    public String getService() {
        if (StringUtil.isEmpty(super.getService())) {
            return AlipayConstant.SERVICE_DIRECT;
        }
        return super.getService();
    }

    public String getBatch_no() {
        return batch_no;
    }

    /**
     * 退款批次号,表示当前退款流水号
     *
     * @param batch_no
     * @see 1.0
     */
    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public int getBatch_num() {
        return batch_num;
    }

    /**
     * 退款笔数,必填，参数detail_data的值中，“#”字符出现的数量
     *
     * @param batch_num
     * @see 1.0
     */
    public void setBatch_num(int batch_num) {
        this.batch_num = batch_num;
    }

    public double getAmount() {
        return amount;
    }

    /**
     * 退款金额
     *
     * @param amount
     * @see 1.0
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    /**
     * 退款原因
     *
     * @param reason
     * @see 1.0
     */
    public void setReason(String reason) {
        this.reason = reason;
    }
}
