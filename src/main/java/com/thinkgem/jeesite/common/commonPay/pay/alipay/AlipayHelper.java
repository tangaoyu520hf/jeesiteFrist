package com.thinkgem.jeesite.common.commonPay.pay.alipay;

/**
 * 支付宝各种交易模型对象的父对象
 */
public abstract class AlipayHelper {
    /**
     * 接口名称
     */
    private String service;
    /**
     * 支付宝合作身份者ID,以2088开头由16位纯数字组成的字符串 支付宝提供
     */
    private String partner;
    /**
     * 参数编码字符集
     */
    private String input_charset = "utf-8";
    /**
     * 商户的私钥 支付宝提供
     */
    private String key;
    /**
     * 卖家支付宝帐户
     */
    private String seller_email;
    /**
     * 买家支付宝帐户
     */
    private String buyer_email;
    /**
     * 支付类型
     */
    private String payment_type = "1";
    /**
     * 与支付宝交易的唯一订单号
     */
    private String out_trade_no;
    /**
     * 商城与支付宝交易的唯一凭证,退款可用
     */
    private String trade_no;
    /**
     * 商品数量
     */
    private int quantity = 1;
    /**
     * 商品单价 ,取值范围为[0.01,1000000.00],精确到小数点后两位
     */
    private String price;
    /**
     * 交易总金额
     */
    private String total_fee;
    /**
     * 商品名称
     */
    private String subject;
    /**
     * 支付宝主动通知地址(异步)
     */
    private String notify_url;
    /**
     * 支付宝主动通知地址(异步)
     */
    private String return_url;
    /**
     * 商品描述
     */
    private String body;
    /**
     * 商品展示地址
     */
    private String show_url;
    /**
     * 防钓鱼时间戳
     */
    private String anti_phishing_key;
    /**
     * 客户端的IP地址
     */
    private String exter_invoke_ip = "";
    /**
     * 公用回传参数
     */
    private String extra_common_param;
    /**
     * 买家逾期不付款,自动关闭交易
     */
    private String it_b_pay;
    /**
     * 自动登录标识
     */
    private String default_login;
    /**
     * 快捷登录授权令牌,需要开通支付宝快速登录接口
     */
    private String token;
    /**
     * 接口调用状态,支付宝返回结果
     */
    private String is_success;
    /**
     * 交易状态,支付宝返回结果
     */
    private String trade_status;

    public String getService() {
        return service;
    }

    /**
     * <Strong>接口名称,不可空;</Strong><br/>
     * 担保交易为create_partner_trade_by_buyer;
     * 即时交易为create_direct_pay_by_user;
     *
     * @param service version 1.0
     */
    public void setService(String service) {
        this.service = service;
    }

    public String getPartner() {
        return partner;
    }

    /**
     * <Strong>合作者身份ID,不可空;</Strong><br/>
     * 商户签约的支付宝账号对应的支付宝唯一商户号;以2088开头的16位纯数字组成;
     * 最大长度为16位;
     *
     * @param partner version 1.0
     */
    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getInput_charset() {
        return input_charset;
    }

    /**
     * <Strong>参数编码字符集,不可空;</Strong><br/>
     * 商户网站使用的编码格式,如utf-8、gbk、gb2312等;
     *
     * @param input_charset version 1.0
     */
    public void setInput_charset(String input_charset) {
        this.input_charset = input_charset;
    }

    public String getKey() {
        return key;
    }

    /**
     * <Strong>商户的私钥,不可空;</Strong><br/>
     * 商户与支付宝签约之后,在支付宝平台上获取
     * 最大长度为32位;
     *
     * @param key version 1.0
     */
    public void setKey(String key) {
        this.key = key;
    }

    public String getSeller_email() {
        return seller_email;
    }

    /**
     * <Strong>卖家支付宝账号,不可空;</Strong><br/>
     * 卖家支付宝账号,卖家信息优先级:
     * seller_id > seller_account_name > seller_email;
     * seller_id,seller_account_name,seller_email不能全部为空,至少有一项不为空;
     * 最大长度为100位;
     *
     * @param seller_email version 1.0
     */
    public void setSeller_email(String seller_email) {
        this.seller_email = seller_email;
    }

    public String getBuyer_email() {
        return buyer_email;
    }

    /**
     * <Strong>买家支付宝账号,可空;</Strong><br/>
     * 最大长度为100位;
     *
     * @param buyer_email version 1.0
     */
    public void setBuyer_email(String buyer_email) {
        this.buyer_email = buyer_email;
    }

    /**
     * <Strong>支付类型,不可空;</Strong><br/>
     *
     * @return version 1.0
     */
    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    /**
     * 与支付宝交易的唯一订单号,通过该单号可以在商户网站找到关联在订单信息
     *
     * @return
     * @author bxmen
     */
    public String getOut_trade_no() {
        return out_trade_no;
    }

    /**
     * <Strong>商户网站唯一订单号,不可空;</Strong><br/>
     * 支付宝合作商户网站唯一订单号;
     * 最大长度为64位;
     *
     * @param out_trade_no version 1.0
     */
    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTrade_no() {
        return trade_no;
    }

    /**
     * 商城与支付宝进行交易,支付宝返回商城的唯一标示,商户需记录该值,因为该值可用户退款操作;
     *
     * @param trade_no version 1.0
     */
    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * <Strong>购买数量,可空;</Strong><br/>
     * price、quantity能代替total_fee;即存在total_fee,就不能存在price和quantity;存在price、quantity,就不能存在total_fee;
     *
     * @param quantity version 1.0
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    /**
     * <Strong>商品单价,不可空;</Strong><br/>
     * 单位为:RMB Yuan;取值范围为[0.01,100000000.00],精确到小数点后两位;
     * 规则:price、quantity能代替total_fee;即存在total_fee,就不能存在price和quantity;存在price、quantity,就不能存在total_fee;
     *
     * @param price version 1.0
     */
    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    /**
     * <Strong>交易总金额,可空;</Strong><br/>
     * 该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。
     *
     * @param total_fee version 1.0
     */
    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSubject() {
        return subject;
    }

    /**
     * <Strong>商品名称,不可空;</Strong><br/>
     * 商品的标题/交易标题/订单标题/订单关键字等;该参数最长为128个汉字;
     * 最大长度为256位;
     *
     * @param subject version 1.0
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNotify_url() {
        return notify_url;
    }

    /**
     * <Strong>服务器异步通知页面路径,可空;</Strong><br/>
     * 支付宝服务器主动通知商户网站里指定页面的http路径;<br/>
     * 商户在在接口中处理自己的业务逻辑之后一定要返回"success"或"fail",否则支付宝会持续通知该接口,知道24小时22分之后停止通知;
     * 该地址中不得出现特殊符号如"!"等,不能挟带任何参数;
     * 最大长度为190位;
     *
     * @param notify_url version 1.0
     */
    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getReturn_url() {
        return return_url;
    }

    /**
     * <Strong>页面跳转同步通知页面路径,可空;</Strong><br/>
     * 支付宝处理完请求后,当前页面自动跳转到商户网站里指定页面的http路径;
     * 该页面可以由商户网站自行美化;
     * 该地址中不得出现特殊符号如"!"等,不能挟带任何参数;
     * 最大长度为200位;
     *
     * @param return_url version 1.0
     */
    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getBody() {
        return body;
    }

    /**
     * <Strong>商品描述,可空;</Strong><br/>
     * 对一笔交易的具体描述信息;如果是多种商品,请将商品描述字符串累加传给body;
     * 最大长度为1000位;
     *
     * @param body version 1.0
     */
    public void setBody(String body) {
        this.body = body;
    }

    public String getShow_url() {
        return show_url;
    }

    /**
     * <Strong>商品展示网址,可空;</Strong><br/>
     * 收银台页面上,商品展示的超链接;
     * 最大长度为400位;
     *
     * @param show_url version 1.0
     */
    public void setShow_url(String show_url) {
        this.show_url = show_url;
    }

    public String getAnti_phishing_key() {
        return anti_phishing_key;
    }

    /**
     * <Strong>防钓鱼时间戳,可空;</Strong><br/>
     * 通过时间戳查询接口获取的加密支付宝系统时间戳。如果已申请开通防钓鱼时间戳验证，则此字段必填
     *
     * @param anti_phishing_key version 1.0
     */
    public void setAnti_phishing_key(String anti_phishing_key) {
        this.anti_phishing_key = anti_phishing_key;
    }

    public String getExter_invoke_ip() {
        return exter_invoke_ip;
    }

    /**
     * <Strong>客户端IP,可空;</Strong><br/>
     * 用户在创建交易时，该用户当前所使用机器的IP。如果商户申请后台开通防钓鱼IP地址检查选项，此字段必填，校验用。
     * 最大长度为15位;
     *
     * @param exter_invoke_ip version 1.0
     */
    public void setExter_invoke_ip(String exter_invoke_ip) {
        this.exter_invoke_ip = exter_invoke_ip;
    }

    public String getExtra_common_param() {
        return extra_common_param;
    }

    /**
     * <Strong>公用回传参数,可空;</Strong><br/>
     * 如果用户请求时传递了该参数，则返回给商户时会回传该参数,原样返回。
     * 最大长度为100位;
     *
     * @param extra_common_param version 1.0
     */
    public void setExtra_common_param(String extra_common_param) {
        this.extra_common_param = extra_common_param;
    }

    public String getIt_b_pay() {
        return it_b_pay;
    }

    /**
     * <Strong>超时时间,可空;</Strong><br/>
     * 设置未付款交易的超时时间，一旦超时，该笔交易就会自动被关闭。
     * 取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
     * 该参数数值不接受小数点，如1.5h，可转换为90m。该功能需要联系支付宝配置关闭时间。
     *
     * @param it_b_pay version 1.0
     */
    public void setIt_b_pay(String it_b_pay) {
        this.it_b_pay = it_b_pay;
    }

    public String getDefault_login() {
        return default_login;
    }

    /**
     * <Strong>该功能需要联系支付宝配置</Strong><br/>
     * 用于标识商户是否使用自动登录的流程;
     * 如果和参数buyer_email一起使用时,就不会再让用户登录支付宝,
     * 即在收银台中不会出现登录页面
     * Y代表使用
     * N代表不使用
     *
     * @param default_login version 1.0
     */
    public void setDefault_login(String default_login) {
        this.default_login = default_login;
    }

    public String getToken() {
        return token;
    }

    /**
     * 如果开通了快捷登录产品,则需要填写,如果没有开通,则为空;
     *
     * @param token version 1.0
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 支付宝调用接口,支付宝返回接口,用户判断接口是否调用成功<br/>
     * T 表示调用成功;
     * <Strong>支付时该参数必须为空;</Strong>
     *
     * @param is_success version 1.0
     */
    public String getIs_success() {
        return is_success;
    }

    /**
     * 支付宝调用接口,支付宝返回接口,用户判断接口是否调用成功<br/>
     * T 表示调用成功;
     * <Strong>支付时该参数必须为空;</Strong>
     *
     * @param is_success version 1.0
     */
    public void setIs_success(String is_success) {
        this.is_success = is_success;
    }

    /**
     * 交易状态,支付宝返回结果;
     * 成功状态有两种:TRADE_FINISHED和TRADE_SUCCESS;<br/>
     * TRADE_FINISHED: 普通即时到账的交易成功状态;
     * TRADE_SUCCESS : 开通了高级即时到账或机票分销产品后的交易成功状态
     *
     * @param trade_status TRADE_FINISHED	和	TRADE_SUCCESS
     *                     version 1.0
     */
    public String getTrade_status() {
        return trade_status;
    }

    /**
     * 交易状态,支付宝返回结果;<Strong>支付时该参数必须为空;</Strong>
     * 成功状态有两种:TRADE_FINISHED和TRADE_SUCCESS;<br/>
     * TRADE_FINISHED: 普通即时到账的交易成功状态;
     * TRADE_SUCCESS : 开通了高级即时到账或机票分销产品后的交易成功状态
     *
     * @param trade_status TRADE_FINISHED	和	TRADE_SUCCESS
     *                     version 1.0
     */
    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }
}