package com.thinkgem.jeesite.common.commonPay.pay.unionpay;

public class AcpOrderDto {
	private int status; //验证签名结果 0：成功； 1 失败

    private String merId; //商户代码
    private String orderId;//商户订单号
    private String txnTime;//订单发送时间
    private String txnAmt;//交易金额
    private String queryId;//银联交易流水号

    private String respCode;//响应码  00成功，其他失败
    private String respMsg;//应答信息

    private String origRespCode;//原交易应答码
    private String origRespMsg;//原交易应答信息
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTxnTime() {
		return txnTime;
	}
	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}
	public String getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getOrigRespCode() {
		return origRespCode;
	}
	public void setOrigRespCode(String origRespCode) {
		this.origRespCode = origRespCode;
	}
	public String getOrigRespMsg() {
		return origRespMsg;
	}
	public void setOrigRespMsg(String origRespMsg) {
		this.origRespMsg = origRespMsg;
	}
    
}
