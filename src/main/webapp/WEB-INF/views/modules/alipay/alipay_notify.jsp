<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.alipay.util.*"%>    
<%
			String partner = ""; //partner�������id��������д��
			String privateKey = ""; //partner �Ķ�Ӧ���װ�ȫУ���루������д��
//**********************************************************************************
//�������������֧��https����������ʹ��http����֤��ѯ��ַ
		//String alipayNotifyURL = "https://www.alipay.com/cooperate/gateway.do?service=notify_verify"
			String alipayNotifyURL = "http://notify.alipay.com/trade/notify_query.do?"
					+ "partner="
					+ partner
					+ "&notify_id="
					+ request.getParameter("notify_id");

			//��ȡ֧����ATN���ؽ����true����ȷ�Ķ�����Ϣ��false ����Ч��
			String responseTxt = CheckURL.check(alipayNotifyURL);

			Map params = new HashMap();
			//���POST �����������õ��µ�params��
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter
					.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";				
				}
				params.put(name, valueStr);
			}
			
			String mysign = com.alipay.util.SignatureHelper.sign(params, privateKey);

			if (mysign.equals(request.getParameter("sign")) && responseTxt.equals("true") ){
				out.println("success");
			}
			else
			{
				out.println("fail");
			}
%>
