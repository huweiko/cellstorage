package com.cellstorage.struct;

import java.util.Date;

public class SampleStatusCollect{
	//sample_status_id
	private int sampleStatusId;
	
	//质控点
	private String qualityCL;
	//合同当前流程状态
	private String SampleProcess;
	//质控范围
	private String qualityCLRange;
	//质控状态
	private String qualityCLStatus;
	
	//咨询和预约时间(咨询和预约)
	private Date applyTime;
	
	//采集时间（样本采集）
	private Date collectTime;
	
	//采集准备时间（采集准备）
	private Date prepareTime;
	
	//原代培养时间（样本制备）
	private Date primaryTime;
	
	//report_time（检测报告）
	private Date reportTime;
	
	//签订合同时间（签订合同）
	private Date signedTime;
	
	//start_trans_time（样本运输）
	private Date startTransTime;
	
	//入库启始时间（样本入库）
	private Date storageTime;
	
	//takeover_time（样本交接）
	private Date takeoverTime;
	

	public int getSampleStatusId() {
		return sampleStatusId;
	}

	public void setSampleStatusId(int sampleStatusId) {
		this.sampleStatusId = sampleStatusId;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}


	public Date getPrepareTime() {
		return prepareTime;
	}

	public void setPrepareTime(Date prepareTime) {
		this.prepareTime = prepareTime;
	}

	public Date getPrimaryTime() {
		return primaryTime;
	}

	public void setPrimaryTime(Date primaryTime) {
		this.primaryTime = primaryTime;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public Date getSignedTime() {
		return signedTime;
	}

	public void setSignedTime(Date signedTime) {
		this.signedTime = signedTime;
	}

	public Date getStartTransTime() {
		return startTransTime;
	}

	public void setStartTransTime(Date startTransTime) {
		this.startTransTime = startTransTime;
	}

	public Date getStorageTime() {
		return storageTime;
	}

	public void setStorageTime(Date storageTime) {
		this.storageTime = storageTime;
	}

	public Date getTakeoverTime() {
		return takeoverTime;
	}

	public void setTakeoverTime(Date takeoverTime) {
		this.takeoverTime = takeoverTime;
	}

	public String getQualityCL() {
		return qualityCL;
	}

	public void setQualityCL(String qualityCL) {
		this.qualityCL = qualityCL;
	}

	public String getSampleProcess() {
		return SampleProcess;
	}

	public void setSampleProcess(String sampleProcess) {
		SampleProcess = sampleProcess;
	}

	public String getQualityCLRange() {
		return qualityCLRange;
	}

	public void setQualityCLRange(String qualityCLRange) {
		this.qualityCLRange = qualityCLRange;
	}

	public String getQualityCLStatus() {
		return qualityCLStatus;
	}

	public void setQualityCLStatus(String qualityCLStatus) {
		this.qualityCLStatus = qualityCLStatus;
	}

}
