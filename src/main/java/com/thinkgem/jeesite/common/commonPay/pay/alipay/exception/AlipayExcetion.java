package com.thinkgem.jeesite.common.commonPay.pay.alipay.exception;

import com.thinkgem.jeesite.common.commonPay.pay.alipay.util.StringUtil;

import java.io.PrintStream;


public class AlipayExcetion extends Exception {
    private static final long serialVersionUID = 1L;
    /**
     * 异常代码
     */
    private String code;
    /**
     * 异常信息
     */
    private String message;

    public AlipayExcetion() {
    }

    public AlipayExcetion(String code) {
        super(code);
        this.code = code;
    }

    public AlipayExcetion(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void printStackTrace() {
        PrintStream stream = System.err;
        String description = "自定义异常信息: -> 异常原因 -> ";
        
        if (StringUtil.isNotEmpty(this.code)) {
            if (AlipayExcetionConstant.CODE_Ox000000000.equals(this.code)) {
                if (StringUtil.isEmpty(this.message)) {
                    description += this.code + " -> " + AlipayExcetionConstant.CODE_Ox000000000_MSG;
                } else {
                    description += this.code + " -> " + this.message;
                }
            }
        }
        stream.println(description);
    }
}
