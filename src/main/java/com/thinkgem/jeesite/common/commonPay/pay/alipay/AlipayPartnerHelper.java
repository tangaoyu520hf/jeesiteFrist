package com.thinkgem.jeesite.common.commonPay.pay.alipay;

import com.thinkgem.jeesite.common.commonPay.pay.alipay.util.StringUtil;

/**
 * 担保交易数据模型对象
 */
public final class AlipayPartnerHelper extends AlipayHelper {
    /**
     * 折扣
     */
    private String discount;
    /**
     * 邮政编码
     */
    private String receive_zip;
    /**
     * 固定电话
     */
    private String receive_phone;
    /**
     * 移动电话
     */
    private String receive_mobile;
    /**
     * 收货人姓名
     */
    private String receive_name;
    /**
     * 收货人地址
     */
    private String receive_address;
    /**
     * 物流类型
     */
    private String logistics_type;
    /**
     * 物流费用
     */
    private String logistics_fee;
    /**
     * 物流支付类型
     */
    private String logistics_payment;
    /**
     * 卖家逾期不发货,允许买家退款 单位为天(d)
     */
    private String t_s_send_1;
    /**
     * 买家逾期不确认收货,自动完成交易（平邮） 单位为天(d)
     */
    private String t_b_rec_post;
    /**
     * 发货之后支付宝返回网站信息的地址
     */
    private String seller_ip;
    /**
     * 物流公司名称
     */
    private String logistics_name;
    /**
     * 物流单号
     */
    private String invoice_no;
    /**
     * 物流类型 POST（平邮）、EXPRESS（快递）、EMS（EMS）
     */
    private String transport_type;

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getReceive_phone() {
        return receive_phone;
    }

    public void setReceive_phone(String receive_phone) {
        this.receive_phone = receive_phone;
    }

    public String getReceive_mobile() {
        return receive_mobile;
    }

    public void setReceive_mobile(String receive_mobile) {
        this.receive_mobile = receive_mobile;
    }

    public String getReceive_zip() {
        return receive_zip;
    }

    public void setReceive_zip(String receive_zip) {
        this.receive_zip = receive_zip;
    }

    public String getReceive_name() {
        return receive_name;
    }

    public void setReceive_name(String receive_name) {
        this.receive_name = receive_name;
    }

    public String getReceive_address() {
        return receive_address;
    }

    public void setReceive_address(String receive_address) {
        this.receive_address = receive_address;
    }

    /**
     * <Strong>物流类型,不可空</Strong><br/>
     * 第一组物流类型;
     * 物流类型:共有三种EMS(平邮),EXPRESS(快递),EMS
     *
     * @return version 1.0
     */
    public String getLogistics_type() {
        return logistics_type;
    }

    /**
     * <Strong>物流类型,不可空</Strong><br/>
     * 第一组物流类型;
     * 共有三种EMS(平邮),EXPRESS(快递),EMS
     *
     * @param logistics_type version 1.0
     */
    public void setLogistics_type(String logistics_type) {
        this.logistics_type = logistics_type;
    }

    /**
     * </Strong>物流费用,不可空</Strong><br/>
     * 第一组物流运费。单位为：RMB Yuan。精确到小数点后两位。缺省值为0元。
     *
     * @return version 1.0
     */
    public String getLogistics_fee() {
        return logistics_fee;
    }

    /**
     * </Strong>物流费用,不可空</Strong><br/>
     * 第一组物流运费。单位为：RMB Yuan。精确到小数点后两位。缺省值为0元。
     *
     * @param logistics_fee version 1.0
     */
    public void setLogistics_fee(String logistics_fee) {
        this.logistics_fee = logistics_fee;
    }

    /**
     * <Strong>物流支付类型,不可空</Strong></br>
     * 第一组物流支付类型:BUYER_PAY(物流买家承担运费),SELLER_PAY(物流卖家承担运费),BUYER_PAY_AFTER_RECEIVE(买家到货付款，运费显示但不计入总价)
     *
     * @return version 1.0
     */
    public String getLogistics_payment() {
        return logistics_payment;
    }

    /**
     * <Strong>物流支付类型,不可空</Strong></br>
     * 第一组物流支付类型:BUYER_PAY(物流买家承担运费),SELLER_PAY(物流卖家承担运费),BUYER_PAY_AFTER_RECEIVE(买家到货付款，运费显示但不计入总价)
     *
     * @param logistics_payment version 1.0
     */
    public void setLogistics_payment(String logistics_payment) {
        this.logistics_payment = logistics_payment;
    }

    public String getT_s_send_1() {
        return t_s_send_1;
    }

    public void setT_s_send_1(String t_s_send_1) {
        this.t_s_send_1 = t_s_send_1;
    }

    public String getT_b_rec_post() {
        return t_b_rec_post;
    }

    public void setT_b_rec_post(String t_b_rec_post) {
        this.t_b_rec_post = t_b_rec_post;
    }

    /**
     * 支付宝接口名,若是名称为空,则默认赋值create_partner_trade_by_buyer
     *
     * @param service V1.0
     */
    @Override
    public void setService(String service) {
        if (StringUtil.isEmpty(service)) {
            service = AlipayConstant.SERVICE_PARTNER;
        }
        super.setService(service);
    }

    /**
     * 支付宝接口名,若是名称为空,则默认取值create_partner_trade_by_buyer
     *
     * @return V1.0
     */
    @Override
    public String getService() {
        if (StringUtil.isEmpty(super.getService())) {
            return AlipayConstant.SERVICE_PARTNER;
        }
        return super.getService();
    }

    public String getSeller_ip() {
        return seller_ip;
    }

    public void setSeller_ip(String seller_ip) {
        this.seller_ip = seller_ip;
    }

    public String getLogistics_name() {
        return logistics_name;
    }

    /**
     * 物流公司名称
     *
     * @param logistics_name
     * @see 1.0
     */
    public void setLogistics_name(String logistics_name) {
        this.logistics_name = logistics_name;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    /**
     * 物流单号
     *
     * @param invoice_no
     * @see 1.0
     */
    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public String getTransport_type() {
        return transport_type;
    }

    /**
     * EXPRESS(快递),EMS,POST(平邮)
     *
     * @param transport_type
     * @see 1.0
     */
    public void setTransport_type(String transport_type) {
        this.transport_type = transport_type;
    }
}