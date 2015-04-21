package com.mygame.cloudcontact.bean;

public class ContactBean {
	private String accountName;
	private String displayName;
	private String phone;
	
	public ContactBean(String acName, String displayName, String phone){
		this.accountName = acName;
		this.displayName = displayName;
		this.phone = phone;
	}
	
	public ContactBean() {
		// TODO Auto-generated constructor stub
	}

	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
