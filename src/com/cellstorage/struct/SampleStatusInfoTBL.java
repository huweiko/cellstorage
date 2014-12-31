package com.cellstorage.struct;

import java.util.Date;

public class SampleStatusInfoTBL{
	//sample_status_id
	private int sampleStatusId;
	
	//咨询和预约时间(咨询和预约)
	private Date applyTime;
	
	//抵达地点
	private String arrivePlace;
	
	//抵达时样本表面温度
	private Float arriveSurTemp;
	
	//抵达时间
	private Date arriveTime;
	
	//采集地点
	private String collectPlace;
	
	//采集样本量
	private Float collectQuantity;
	
	//采集时间（样本采集）
	private Date collectTime;
	
	//采集结论
	private String collectVerdict;
	
	//内毒素检测
	private String examNds;
	
	//检测结论
	private String examVerdict;
	
	//细胞表面抗原分析检测
	private String examXbbmky;
	
	//需氧真菌检测
	private String examXyzj;
	
	//厌氧真菌检测
	private String examYyzj;
	
	//支原体检测
	private String examZyt;
	
	//冻存状态描述
	private String freezonStatus;
	
	//冻存时间
	private Date freezonTime;
	
	//活细胞总数(单位：10^7)
	private Float livingcellNum;
	
	//细胞活率
	private int livingcellRate;
	
	//传代P0状态描述
	private String pass0Status;
	
	//传代P0时间
	private Date pass0Time;
	
	//传代P1状态描述
	private String pass1Status;
	
	//传代P1时间
	private Date pass1Time;
	
	//传代P2状态描述
	private String pass2Status;
	
	//传代P2时间
	private Date pass2Time;
	
	//采集准备时间（采集准备）
	private Date prepareTime;
	
	//原代培养状态描述
	private String primaryStatus;
	
	//原代培养时间（样本制备）
	private Date primaryTime;
	
	//report_time（检测报告）
	private Date reportTime;
	
	//交接样本量
	private Float sampleQuantity;
	
	//签订合同时间（签订合同）
	private Date signedTime;
	
	//start_trans_time（样本运输）
	private Date startTransTime;
	
	//入库启始时间（样本入库）
	private Date storageTime;
	
	//储存温度
	private Float storageTemp;
	
	//入库结论
	private String storageVerdict;
	
	//交接时样本表面温度
	private Float takeoverSurTemp;
	
	//takeover_time（样本交接）
	private Date takeoverTime;
	
	//交接结论
	private String takeoverVerdict;
	
	//抵达结论
	private String transportVerdict;
	
	//包裹状态
	private String wrapStatus;
	
	//service_id
	private int serviceId;

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

	public String getArrivePlace() {
		return arrivePlace;
	}

	public void setArrivePlace(String arrivePlace) {
		this.arrivePlace = arrivePlace;
	}

	public Float getArriveSurTemp() {
		return arriveSurTemp;
	}

	public void setArriveSurTemp(Float arriveSurTemp) {
		this.arriveSurTemp = arriveSurTemp;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getCollectPlace() {
		return collectPlace;
	}

	public void setCollectPlace(String collectPlace) {
		this.collectPlace = collectPlace;
	}

	public Float getCollectQuantity() {
		return collectQuantity;
	}

	public void setCollectQuantity(Float collectQuantity) {
		this.collectQuantity = collectQuantity;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

	public String getCollectVerdict() {
		return collectVerdict;
	}

	public void setCollectVerdict(String collectVerdict) {
		this.collectVerdict = collectVerdict;
	}

	public String getExamNds() {
		return examNds;
	}

	public void setExamNds(String examNds) {
		this.examNds = examNds;
	}

	public String getExamVerdict() {
		return examVerdict;
	}

	public void setExamVerdict(String examVerdict) {
		this.examVerdict = examVerdict;
	}

	public String getExamXbbmky() {
		return examXbbmky;
	}

	public void setExamXbbmky(String examXbbmky) {
		this.examXbbmky = examXbbmky;
	}

	public String getExamXyzj() {
		return examXyzj;
	}

	public void setExamXyzj(String examXyzj) {
		this.examXyzj = examXyzj;
	}

	public String getExamYyzj() {
		return examYyzj;
	}

	public void setExamYyzj(String examYyzj) {
		this.examYyzj = examYyzj;
	}

	public String getExamZyt() {
		return examZyt;
	}

	public void setExamZyt(String examZyt) {
		this.examZyt = examZyt;
	}

	public String getFreezonStatus() {
		return freezonStatus;
	}

	public void setFreezonStatus(String freezonStatus) {
		this.freezonStatus = freezonStatus;
	}

	public Date getFreezonTime() {
		return freezonTime;
	}

	public void setFreezonTime(Date freezonTime) {
		this.freezonTime = freezonTime;
	}

	public Float getLivingcellNum() {
		return livingcellNum;
	}

	public void setLivingcellNum(Float livingcellNum) {
		this.livingcellNum = livingcellNum;
	}

	public int getLivingcellRate() {
		return livingcellRate;
	}

	public void setLivingcellRate(int livingcellRate) {
		this.livingcellRate = livingcellRate;
	}

	public String getPass0Status() {
		return pass0Status;
	}

	public void setPass0Status(String pass0Status) {
		this.pass0Status = pass0Status;
	}

	public Date getPass0Time() {
		return pass0Time;
	}

	public void setPass0Time(Date pass0Time) {
		this.pass0Time = pass0Time;
	}

	public String getPass1Status() {
		return pass1Status;
	}

	public void setPass1Status(String pass1Status) {
		this.pass1Status = pass1Status;
	}

	public Date getPass1Time() {
		return pass1Time;
	}

	public void setPass1Time(Date pass1Time) {
		this.pass1Time = pass1Time;
	}

	public String getPass2Status() {
		return pass2Status;
	}

	public void setPass2Status(String pass2Status) {
		this.pass2Status = pass2Status;
	}

	public Date getPass2Time() {
		return pass2Time;
	}

	public void setPass2Time(Date pass2Time) {
		this.pass2Time = pass2Time;
	}

	public Date getPrepareTime() {
		return prepareTime;
	}

	public void setPrepareTime(Date prepareTime) {
		this.prepareTime = prepareTime;
	}

	public String getPrimaryStatus() {
		return primaryStatus;
	}

	public void setPrimaryStatus(String primaryStatus) {
		this.primaryStatus = primaryStatus;
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

	public Float getSampleQuantity() {
		return sampleQuantity;
	}

	public void setSampleQuantity(Float sampleQuantity) {
		this.sampleQuantity = sampleQuantity;
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

	public Float getStorageTemp() {
		return storageTemp;
	}

	public void setStorageTemp(Float storageTemp) {
		this.storageTemp = storageTemp;
	}

	public String getStorageVerdict() {
		return storageVerdict;
	}

	public void setStorageVerdict(String storageVerdict) {
		this.storageVerdict = storageVerdict;
	}

	public Float getTakeoverSurTemp() {
		return takeoverSurTemp;
	}

	public void setTakeoverSurTemp(Float takeoverSurTemp) {
		this.takeoverSurTemp = takeoverSurTemp;
	}

	public Date getTakeoverTime() {
		return takeoverTime;
	}

	public void setTakeoverTime(Date takeoverTime) {
		this.takeoverTime = takeoverTime;
	}

	public String getTakeoverVerdict() {
		return takeoverVerdict;
	}

	public void setTakeoverVerdict(String takeoverVerdict) {
		this.takeoverVerdict = takeoverVerdict;
	}

	public String getTransportVerdict() {
		return transportVerdict;
	}

	public void setTransportVerdict(String transportVerdict) {
		this.transportVerdict = transportVerdict;
	}

	public String getWrapStatus() {
		return wrapStatus;
	}

	public void setWrapStatus(String wrapStatus) {
		this.wrapStatus = wrapStatus;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

}
