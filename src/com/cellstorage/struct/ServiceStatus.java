package com.cellstorage.struct;
public class ServiceStatus{
	private String mServiceType;
	private boolean mServiceStatus;
	public String getmServiceType() {
		return mServiceType;
	}
	public void setmServiceType(String mServiceType) {
		this.mServiceType = mServiceType;
	}
	public boolean getmServiceStatus() {
		return mServiceStatus;
	}
	public void setmServiceStatus(boolean mServiceStatus) {
		this.mServiceStatus = mServiceStatus;
	}
	public ServiceStatus(String mServiceType,boolean mServiceStatus){
		this.mServiceType = mServiceType;
		this.mServiceStatus = mServiceStatus;
	}
}