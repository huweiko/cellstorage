/*
 * 文件名：UserInfo.java
 * 功能：保存用户信息
 * 作者：huwei
 * 创建时间：2013-11-08
 * 
 * 
 * 
 * */
package com.cellstorage.struct;


public class UserInfo {
	
	private static UserInfo instance;
	private UserInfo(){}
	
	public static UserInfo getAppManager(){
		if(instance==null){
			instance=new UserInfo();
		}
		return instance;
	}
	
	
	public String mUserID;
	
	public String mUserName;
	
	public String mPassWord;
	
	public String mGender;//性别
	
	public String mAge;//年龄
	
	public String mEmail;
	
	public String mPhoneCode;
	
	public String mQQ;
	
	
	
	public String getUserID() {
		return mUserID;
	}

	public void setUserID(String mUserID) {
		this.mUserID = mUserID;
	}

	public String getUserName() {
		return mUserName;
	}

	public void setUserName(String mUserName) {
		this.mUserName = mUserName;
	}

	public String getPassWord() {
		return mPassWord;
	}

	public void setPassWord(String mPassWord) {
		this.mPassWord = mPassWord;
	}
	public String getGender() {
		return mGender;
	}
	
	public void setGender(String mGender) {
		this.mGender = mGender;
	}
	public String getAge() {
		return mAge;
	}
	
	public void setAge(String mAge) {
		this.mAge = mAge;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String mEmail) {
		this.mEmail = mEmail;
	}

	public String getPhoneCode() {
		return mPhoneCode;
	}

	public void setPhoneCode(String mPhoneCode) {
		this.mPhoneCode = mPhoneCode;
	}
	
	public String getQQ() {
		return mPhoneCode;
	}
	
	public void setQQ(String mQQ) {
		this.mQQ = mQQ;
	}
	
	public UserInfo(String UserID, String UserName,String PassWord,String Gender ,String Age,String Email,String Alias,String PhoneCode,String QQ)
	{
		this.mUserID = UserID;
		
		this.mUserName = UserName;
		
		this.mPassWord = PassWord;
		
		this.mGender = Gender;
		
		this.mAge = Age;
		
		this.mEmail = Email;
		
		this.mPhoneCode = PhoneCode;
		
		this.mQQ = QQ;
	}

}
