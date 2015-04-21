package com.mygame.cloudcontact;

import com.mygame.cloudcontact.bean.AccountInfo;

public class ContactsInfo {

	private AccountInfo accountInfo;
	private String id;
	private String phone;
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;
	private long rawContactId;
	private String address;

	public String getId() {
		return id;
	}

	public AccountInfo getAccountInfo() {
		return accountInfo;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public void setAccountInfo(AccountInfo info) {
		this.accountInfo = info;
	}

	public void setRawContactId(long uid) {
		this.rawContactId = uid;
	}

	public long getRaw_contact_id() {
		return this.rawContactId;
	}

	public void setAddress(String string) { 
		this.address = string;
	}

}
