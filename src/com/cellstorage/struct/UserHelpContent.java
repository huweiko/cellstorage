package com.cellstorage.struct;

public class UserHelpContent {
	private String mHelpName;
	private String mHelpContent;
	private boolean mHelpContentShowStatus;
	
	public UserHelpContent(String mHelpName,String mHelpContent,boolean mHelpContentShowStatus){
		this.mHelpName = mHelpName;
		this.mHelpContent = mHelpContent;
		this.mHelpContentShowStatus = mHelpContentShowStatus;
	}
	
	public String getmHelpName() {
		return mHelpName;
	}
	public void setmHelpName(String mHelpName) {
		this.mHelpName = mHelpName;
	}
	public String getmHelpContent() {
		return mHelpContent;
	}
	public void setmHelpContent(String mHelpContent) {
		this.mHelpContent = mHelpContent;
	}
	public boolean ismHelpContentShowStatus() {
		return mHelpContentShowStatus;
	}
	public void setmHelpContentShowStatus(boolean mHelpContentShowStatus) {
		this.mHelpContentShowStatus = mHelpContentShowStatus;
	}
}
