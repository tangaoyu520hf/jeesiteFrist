<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>alipay payment</title>
</head>

<%-- <%
//Notice
//The following information presented is confidential. 
//Any unauthorized release of this information is prohibited and punishable by law.
//All original material is copyright  2009 Alipay.



Date Now_Date=new Date();
String paygateway	=	"https://www.alipay.com/cooperate/gateway.do?";	//
String service      = "create_forex_trade";//
String sign_type       =   "MD5";
String out_trade_no		= Now_Date.toString();	//external deal No
String input_charset   =  "UTF-8";       
String partner			=	""; //partners’ User ID at alipay
String key             =    ""; //partners’ User key at alipay
String body			= "Alipay"; //trade description
String total_fee			= "0.01";				 //range is 0.01～1000000.00
String currency     =  "USD";//Please refer to the short name in
String subject			= "Alipay";			 //
String notify_url		= "http://localhost:8080/pay/alipay_notify.jsp";	//Notification acceptance URL after the successful payment of the deal
String return_url		= "http://localhost:8080/pay/alipay_return.jsp";	//After successful payment, returnthe result to URL, which is only appropriate to interface of immediate return transaction result.

String ItemUrl=Payment.CreateUrl(paygateway,service,sign_type,out_trade_no,input_charset,partner,key,body,total_fee,currency,subject,notify_url,return_url);

%> --%>
<a href="${alipayurl}" >点击支付</a>
<!-- <img src="images/alipay_bwrx.gif" border="0"></a> -->
<body>

</body>
</html>