package com.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088101122136241";
	// 商户的私钥
	public static String key = "760bdzec6y9goq7ctyx96ezkz78287de";

	public static String private_key_test = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMaOLksr1ydui+pv8nhEiowhzs9nDg+ct9nE9f/kxxJxrL18QSLvg5zgWWdsDg0abqgHy3br5zKrQEO+Ntzxy89yJ23CoTtW5cZJRVS/XRM5sqmse5XpVFULnQGHOpMtY1eBbLbWykWZ9l9Qy0ZGNQnNFcKPqdgikCVl9p3yHLRlAgMBAAECgYEAiMDQnbGaRGxdLaDg7Xi3vABlAnBGPPaBohmIHTEi808b3H5CFp/ElD3pFdRUcS3tXQnRVxzvZfSRFJdkDhTze7tIYWzcuJNeBbtz+ltk2n4/JdoE4BK15aL6QT8nXfsbDTa2kG0I338BtTluWLDMHmEtqQsDwYU8ikQJpowLqIECQQD5gclwT+wBA1eeT166CzLvtO0YlZ8A5gthBEgtI0Zl9RtfgZqmNUWmy630rEdQm7482bcEvONlTHA991pCGe/VAkEAy7j0ebYEJGz4KMGXbH0jkcWK+sOG0Z7G7vYe9z6kb1uVz8BC3D1Qwg5eOHj03nRbD9q1qTrEEn7RnM7H +KOKUQJAKucDQTms5huyBDm3mxvz3T3qCvEnTxs/V6ZJ1uEa6Ms1sr1xXyUH3f9l4Q2CkbzGAGabTsg//ZogLreKoJUfJQJBALyBniZpGBgkhNQhnl4SxTEDm4BCIKQiyW0gbLLXUgyAsHUWMEferLWD2gdjVSHqhMB83g+Dgx6iHo98f2lA2qECQHMRSf1gYVmt1wHZc8MuFRBZGqoRVwl0IaPkrQMxya5J6WecPpq8IWTH6YkK/FTlcvAe3EIvMIaUg7K77DSdF2k=";
	public static String public_key_test = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGji5LK9cnbovqb/J4RIqMIc7PZw4PnLfZxPX/5McScay9fEEi74Oc4FlnbA4NGm6oB8t26+cyq0BDvjbc8cvPcidtwqE7VuXGSUVUv10TObKprHuV6VRVC50BhzqTLWNXgWy21spFmfZfUMtGRjUJzRXCj6nYIpAlZfad8hy0ZQIDAQAB";
	// 支付宝的公钥，无需修改该值
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	//支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
	//正确的支付宝公钥
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "MD5";
	
	public static String CREATE_FOREX_TRADE = "create_forex_trade";//海外接口开发
	
	/**商家email*/
	public static String EMAIL = "overseas_kgtest@163.com";
	
	/**通知url*/
	public static String NOTIFY_URL = "http://localhost:8090/jeesite/a/alipayNotify";	
	
	/**成功url*/
	public static String RETURN_URL = "http://localhost:8090/jeesite/a/alipayReturn";	
}
