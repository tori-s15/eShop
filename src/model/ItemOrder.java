package model;

import java.util.List;

public class ItemOrder {

	private long orderid;					// 注文ID
	private String userid;					// 注文者ID
	private long totalamount;				// 合計金額
	private int status;						// ステータス
	private int payment;					// 支払方法
	private String address;					// 発送先
	private List<ItemOrderDetail> detail;	// 注文明細

	public static final int STATUS_INIT = 0;		// ステータス（カート）
	public static final int STATUS_ORDER = 1;		// ステータス（注文済み）
	public static final int STATUS_SEND = 9;		// ステータス（発送済み）

	public static final int PAYMENT_CASH = 0;		// 支払方法（現金）
	public static final int PAYMENT_CARD = 1;		// 支払方法（クレジットカード）
	public static final int PAYMENT_PAY = 2;	// 支払方法（振り込み）

	public long getOrderid() {
		return orderid;
	}
	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public long getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(long totalamount) {
		this.totalamount = totalamount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<ItemOrderDetail> getDetail() {
		return detail;
	}

	public void setDetail(List<ItemOrderDetail> detail) {
		this.detail = detail;
	}



}
