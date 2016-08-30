package com.thinkgem.jeesite.common.commonPay.pay.alipay.exception;

public class AlipayVerifyExction extends AlipayExcetion {
	private static final long serialVersionUID = 4203215406243327976L;

    public AlipayVerifyExction() {
        super();
    }

    public AlipayVerifyExction(String code) {
        super(code);
    }

    public AlipayVerifyExction(String code, String message) {
        super(code, message);
    }
}
