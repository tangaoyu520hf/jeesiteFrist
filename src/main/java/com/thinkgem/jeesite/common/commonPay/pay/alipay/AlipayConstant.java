package com.thinkgem.jeesite.common.commonPay.pay.alipay;

public final class AlipayConstant {
    /**
     * 即时交易接口
     */
    public static final String SERVICE_DIRECT = "create_direct_pay_by_user";
    /**
     * 即时交易:余额支付
     */
    public static final String SERVICE_DIRECT_DIRECT_PAY = "directPay";
    /**
     * 即时交易:信用支付
     */
    public static final String SERVICE_DIRECT_CREDIT_PAY = "creditPay";
    /**
     * 担保交易接口
     */
    public static final String SERVICE_PARTNER = "create_partner_trade_by_buyer";
    /**
     * 即时交易退款接口
     */
    public static final String SERVICE_DIRECT_REFUND = "refund_fastpay_by_platform_pwd";
    /**
     * 担保交易发货接口
     */
    public static final String SERVICE_PARTNER_SEND_GOODS = "send_goods_confirm_by_platform";

    public static final String INPUT_CHARSET = "_input_charset";
    public static final String PARTNER = "partner";
    public static final String KEY = "key";


    /**
     * 交易创建，等待买家付款
     */
    public static final String STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";

    /**
     * 在指定时间段内未支付时关闭的交易；
     * 在交易完成全额退款成功时关闭的交易。
     */
    public static final String STATUS_TRADE_CLOSED = "TRADE_CLOSED";

    /**
     * 交易成功，且可对该交易做操作，如：多级分润、退款等
     */
    public static final String STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

    /**
     * 等待卖家收款（买家付款后，如果卖家账号被冻结）
     */
    public static final String STATUS_TRADE_PENDING = "TRADE_PENDING";

    /**
     * 交易成功且结束，即不可再做任何操作
     */
    public static final String STATUS_TRADE_FINISHED = "TRADE_FINISHED";
}

