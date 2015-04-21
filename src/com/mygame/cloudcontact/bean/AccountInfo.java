package com.mygame.cloudcontact.bean;

public class AccountInfo {
	private int _id;
	private String account_label;
	private String account_name;
	private String account_type;
	private String data_set;
	private int mSlotId;
	public int iconRes;
	
	public int getmSlotId() {
        return mSlotId;
    }
    public void setmSlotId(int mSlotId) {
        this.mSlotId = mSlotId;
    }
    public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	
	public String getAccount_label() {
		return account_label;
	}
	public void setAccount_label(String account_label) {
		this.account_label = account_label;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public String getData_set() {
		return data_set;
	}
	public void setData_set(String data_set) {
		this.data_set = data_set;
	}

}
