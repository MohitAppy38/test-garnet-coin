package com.example.demo;

public class Result {

	private String amount;
	private String txn_id;
	private String address;
	private String confirms_needed;
	private String timeout;
	private String status_url;
	private String qrcode_url;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTxn_id() {
		return txn_id;
	}

	public void setTxn_id(String txn_id) {
		this.txn_id = txn_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getConfirms_needed() {
		return confirms_needed;
	}

	public void setConfirms_needed(String confirms_needed) {
		this.confirms_needed = confirms_needed;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getStatus_url() {
		return status_url;
	}

	public void setStatus_url(String status_url) {
		this.status_url = status_url;
	}

	public String getQrcode_url() {
		return qrcode_url;
	}

	public void setQrcode_url(String qrcode_url) {
		this.qrcode_url = qrcode_url;
	}

}
