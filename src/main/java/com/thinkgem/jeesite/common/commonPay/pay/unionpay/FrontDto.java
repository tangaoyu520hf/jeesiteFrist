package com.thinkgem.jeesite.common.commonPay.pay.unionpay;

public class FrontDto {

    private int status; //验证签名结果 0：成功； 1 失败

    private String merId; //商户代码
    private String orderId;//商户订单号
    private String txnTime;//订单发送时间
    private String txnAmt;//交易金额

    private String activateStatus; //开通全渠道支付，状态；0：未开通业务 1：已开通银联全渠道支付 2：已开通小额认证支付 3：评级开通
    private String certId; //证书ID
    private String customerInfo; // 银行卡验证信息及身份信息

    private String accNo; //账号

    private String origQryId;//原始交易流水号
    private String queryId;//银联交易流水号

    private String respCode;//响应码 “00” 成功；
    private String respMsg;//应答信息
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
	public String getActivateStatus() {
		return activateStatus;
	}
	public void setActivateStatus(String activateStatus) {
		this.activateStatus = activateStatus;
	}
	public String getCertId() {
		return certId;
	}
	public void setCertId(String certId) {
		this.certId = certId;
	}
	public String getCustomerInfo() {
		return customerInfo;
	}
	public void setCustomerInfo(String customerInfo) {
		this.customerInfo = customerInfo;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getOrigQryId() {
		return origQryId;
	}
	public void setOrigQryId(String origQryId) {
		this.origQryId = origQryId;
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
    
}
