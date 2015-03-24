package com.cellstorage.struct;

import java.util.Date;

public class SampleStatusStorage{
	private Date storageTime;
	private String livingcellNum;
	private String livingcellRate;
	private String storageTemp;
	private String storageVerdict;
	private String fridgeName;
	public Date getStorageTime() {
		return storageTime;
	}
	public void setStorageTime(Date storageTime) {
		this.storageTime = storageTime;
	}
	public String getLivingcellNum() {
		return livingcellNum;
	}
	public void setLivingcellNum(String livingcellNum) {
		this.livingcellNum = livingcellNum;
	}
	public String getStorageTemp() {
		return storageTemp;
	}
	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
	}
	public String getLivingcellRate() {
		return livingcellRate;
	}
	public void setLivingcellRate(String livingcellRate) {
		this.livingcellRate = livingcellRate;
	}
	public String getStorageVerdict() {
		return storageVerdict;
	}
	public void setStorageVerdict(String storageVerdict) {
		this.storageVerdict = storageVerdict;
	}
	public String getFridgeName() {
		return fridgeName;
	}
	public void setFridgeName(String fridgeName) {
		this.fridgeName = fridgeName;
	}
}
