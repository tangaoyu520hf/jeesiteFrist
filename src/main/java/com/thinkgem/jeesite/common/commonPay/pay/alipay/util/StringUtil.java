package com.thinkgem.jeesite.common.commonPay.pay.alipay.util;

public class StringUtil {
	public static boolean isEmpty(String object) {
        return object == null || object.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
