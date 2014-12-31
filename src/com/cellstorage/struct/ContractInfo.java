package com.cellstorage.struct;
public class ContractInfo{
	private String ContractNo;
	private String ServiceId;
	public ContractInfo(String contractNo,String serviceId){
		this.ContractNo = contractNo;
		this.ServiceId = serviceId;
	}
	public String getContractNo() {
		return ContractNo;
	}
	public void setContractNo(String contractNo) {
		ContractNo = contractNo;
	}
	public String getServiceId() {
		return ServiceId;
	}
	public void setServiceId(String serviceId) {
		ServiceId = serviceId;
	}
}