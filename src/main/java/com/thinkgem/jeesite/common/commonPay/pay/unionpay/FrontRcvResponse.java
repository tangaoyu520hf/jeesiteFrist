package com.thinkgem.jeesite.common.commonPay.pay.unionpay;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.thinkgem.jeesite.common.commonPay.pay.common.PayCommonUtil;
import com.thinkgem.jeesite.common.commonPay.pay.unionpay.sdk.LogUtil;
import com.thinkgem.jeesite.common.commonPay.pay.unionpay.sdk.SDKConstants;
import com.thinkgem.jeesite.common.commonPay.pay.unionpay.sdk.SDKUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 名称：商户通知工具类
 * 功能： 类属性： 方法调用
 * 版本：5.0
 * 日期：2014-07 中国银联ACP团队
 * 作者：wxliu基于FrontRcvResponse更改
 * 版权：中国银联
 * */


public class FrontRcvResponse {

	private static final Logger logger = LoggerFactory.getLogger(FrontRcvResponse.class);

	public static FrontDto getFrontDto(HttpServletRequest req)
			throws ServletException, IOException {

		LogUtil.writeLog("FrontRcvResponse前台接收报文返回开始");

		req.setCharacterEncoding("ISO-8859-1");
		String encoding = req.getParameter(SDKConstants.param_encoding);
		LogUtil.writeLog("返回报文中encoding=[" + encoding + "]");

		Map<String, String> respParam = getAllRequestParam(req);

		// 打印请求报文
		FrontDto frontDto = new FrontDto();

		Map<String, String> valideData = null;
		StringBuffer page = new StringBuffer();
		if (null != respParam && !respParam.isEmpty()) {
			Iterator<Entry<String, String>> it = respParam.entrySet()
					.iterator();
			valideData = new HashMap<String, String>(respParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				value = new String(value.getBytes("ISO-8859-1"), encoding);
				valideData.put(key, value);
			}
		}

		int status = 0;
		if (!SDKUtil.validate(valideData, encoding)) {
			LogUtil.writeLog("验证签名结果[失败].");
			status = 1;
		} else {
			LogUtil.writeLog("验证签名结果[成功].");
			status = 0;
		}

		frontDto = (FrontDto) PayCommonUtil.convertMap(FrontDto.class,
				valideData);
		frontDto.setStatus(status);
		return frontDto;
	}

	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(
			final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}
}
