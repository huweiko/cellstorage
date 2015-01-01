package com.cellstorage.utils;

import java.io.StringReader;
import java.util.Date;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.cellstorage.OtherHealper;
import com.cellstorage.struct.ContractInfo;
import com.cellstorage.struct.SampleStatusInfoTBL;
import com.cellstorage.struct.ServiceStatus;
import com.cellstorage.struct.UserInfo;
import com.cellstorage.struct.UserReminder;

public class parseXML{
	//解析XML
	private static Element StringToElement(String xml){
		Element rootElement = null;
		try
		{
			SAXBuilder builder = new SAXBuilder();
			StringReader sr = new StringReader(xml);   
			InputSource is = new InputSource(sr); 
			Document Doc = builder.build(is);
			rootElement = (Element) Doc.getRootElement();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		return rootElement;
	}
	//解析用户信息
	
		
	public static void ConserveUserInfo(String XMLString, UserInfo mUserInfo2) 
	{
		Element rootElement = StringToElement(XMLString);
		
		Element e = null;
		
		if(rootElement != null && mUserInfo2 != null)
		{
			 if((e =rootElement.getChild("userId")) != null)
			 {
				 mUserInfo2.setUserID(e.getValue());
			 }  
			 if((e =rootElement.getChild("userName")) != null)
			 {
				 mUserInfo2.setUserName(e.getValue());
			 } 
			 if((e =rootElement.getChild("userPwd")) != null)
			 {
				 mUserInfo2.setPassWord(e.getValue());
			 } 
			 if((e =rootElement.getChild("gender")) != null)
			 {
				 mUserInfo2.setGender(e.getValue());
			 } 
			 if((e =rootElement.getChild("age")) != null)
			 {
				 mUserInfo2.setAge(e.getValue());
			 } 
			 if((e =rootElement.getChild("email")) != null)
			 {
				 mUserInfo2.setEmail(e.getValue());
			 } 
			 if((e =rootElement.getChild("phone")) != null)
			 {
				 mUserInfo2.setPhoneCode(e.getValue());
			 } 
			 if((e =rootElement.getChild("qq")) != null)
			 {
				 mUserInfo2.setPhoneCode(e.getValue());
			 } 
		
		}
	}
	//样本实时信息转换
	public static void ConserveSampleStatusInfo(String XMLString, SampleStatusInfoTBL mSampleStatusInfoTBL) 
	{
		Element rootElement = StringToElement(XMLString);
		
		Element e = null;
		
		if(rootElement != null && mSampleStatusInfoTBL != null){
			if((e =rootElement.getChild("sampleStatusId")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setSampleStatusId(Integer.parseInt(e.getValue()));
				}
			}  
			if((e =rootElement.getChild("applyTime")) != null){
				if(!e.getValue().equals("null")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfoTBL.setApplyTime(aa);
				}
			}  
			if((e =rootElement.getChild("arrivePlace")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setArrivePlace(e.getValue());
				}
			}  
			if((e =rootElement.getChild("arriveSurTemp")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setArriveSurTemp(Float.parseFloat(e.getValue()));
				}
			}  
			if((e =rootElement.getChild("arriveTime")) != null){
				if(!e.getValue().equals("null")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfoTBL.setArriveTime(aa);
				}
			}  
			if((e =rootElement.getChild("collectPlace")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setCollectPlace(e.getValue());
				}
			}  
			if((e =rootElement.getChild("collectQuantity")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setCollectQuantity(Float.parseFloat(e.getValue()));
				}
			}
			if((e =rootElement.getChild("collectTime")) != null){
				if(!e.getValue().equals("null")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfoTBL.setCollectTime(aa);
				}
			}
			if((e =rootElement.getChild("collectVerdict")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setCollectVerdict(e.getValue());
				}
			}  
			if((e =rootElement.getChild("examNds")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setExamNds(e.getValue());
				}
			}  
			if((e =rootElement.getChild("examVerdict")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setExamVerdict(e.getValue());
				}
			}  
			if((e =rootElement.getChild("examXbbmky")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setExamXbbmky(e.getValue());
				}
			}  
			if((e =rootElement.getChild("examXyzj")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setExamXyzj(e.getValue());
				}
			}  
			if((e =rootElement.getChild("examYyzj")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setExamYyzj(e.getValue());
				}
			}  
			if((e =rootElement.getChild("examZyt")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setExamZyt(e.getValue());
				}
			}  
			if((e =rootElement.getChild("freezonStatus")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setFreezonStatus(e.getValue());
				}
			}  
			if((e =rootElement.getChild("freezonTime")) != null){
				if(!e.getValue().equals("null")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfoTBL.setFreezonTime(aa);
				}
			}
			if((e =rootElement.getChild("livingcellNum")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setLivingcellNum(Float.parseFloat(e.getValue()));
				}
			}  
			if((e =rootElement.getChild("livingcellRate")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setLivingcellRate(Integer.parseInt(e.getValue()));
				}
			}  
			if((e =rootElement.getChild("pass0Status")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setPass0Status(e.getValue());
				}
			}
			if((e =rootElement.getChild("pass0Time")) != null){
				if(!e.getValue().equals("null")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfoTBL.setPass0Time(aa);
				}
			}
			if((e =rootElement.getChild("pass1Status")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setPass1Status(e.getValue());
				}
			}
			if((e =rootElement.getChild("pass1Time")) != null){
				if(!e.getValue().equals("null")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfoTBL.setPass1Time(aa);
				}
			}
			if((e =rootElement.getChild("pass2Status")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setPass2Status(e.getValue());
				}
			}
			if((e =rootElement.getChild("pass2Time")) != null){
				if(!e.getValue().equals("null")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfoTBL.setPass2Time(aa);
				}
			}
			if((e =rootElement.getChild("prepareTime")) != null){
				if(!e.getValue().equals("null")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfoTBL.setPrepareTime(aa);
				}
			}
			if((e =rootElement.getChild("primaryStatus")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setPrimaryStatus(e.getValue());
				}
			}
			if((e =rootElement.getChild("primaryTime")) != null){
				if(!e.getValue().equals("null")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfoTBL.setPrimaryTime(aa);
				}
			}
			if((e =rootElement.getChild("reportTime")) != null){
				if(!e.getValue().equals("null")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfoTBL.setReportTime(aa);
				}
			}
			if((e =rootElement.getChild("sampleQuantity")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setSampleQuantity(Float.parseFloat(e.getValue()));
				}
			} 
			if((e =rootElement.getChild("signedTime")) != null){
				if(!e.getValue().equals("null")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfoTBL.setSignedTime(aa);
				}
			}
			if((e =rootElement.getChild("startTransTime")) != null){
				if(!e.getValue().equals("null")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfoTBL.setStartTransTime(aa);
				}
			}
			if((e =rootElement.getChild("storageTime")) != null){
				if(!e.getValue().equals("null")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfoTBL.setStorageTime(aa);
				}
			}
			if((e =rootElement.getChild("storageTemp")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setStorageTemp(Float.parseFloat(e.getValue()));
				}
			} 
			if((e =rootElement.getChild("storageVerdict")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setStorageVerdict(e.getValue());
				}
			}
			
			if((e =rootElement.getChild("takeoverSurTemp")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setTakeoverSurTemp(Float.parseFloat(e.getValue()));
				}
			}
			if((e =rootElement.getChild("takeoverTime")) != null){
				if(!e.getValue().equals("null")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfoTBL.setTakeoverTime(aa);
				}
			}
			if((e =rootElement.getChild("takeoverVerdict")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setTakeoverVerdict(e.getValue());
				}
			}
			if((e =rootElement.getChild("transportVerdict")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setTransportVerdict(e.getValue());
				}
			}
			if((e =rootElement.getChild("wrapStatus")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setWrapStatus(e.getValue());
				}
			}
			if((e =rootElement.getChild("serviceId")) != null){
				if(!e.getValue().equals("null")){
					mSampleStatusInfoTBL.setServiceId(Integer.parseInt(e.getValue()));
				}
			}
		}
	}
	public static void ConserveServiceStatus(String XMLString, List<ServiceStatus> xServiceStatusList) 
	{
		Element element = StringToElement(XMLString);
		
		Element e = null;
		while ((e = element.getChild("services")) != null) {
			String mType = e.getAttributeValue("type") != null ? e.getAttributeValue("type") : "";
			String mStatus = e.getAttributeValue("status") != null ? e.getAttributeValue("status") : "";
			boolean bStatus = mStatus.equals("0")?false:true;
			ServiceStatus n = new ServiceStatus(mType, bStatus);
			xServiceStatusList.add(n);
			element.removeChild("services");
		}
	}	
	public static void ConserveReminder(String XMLString, List<UserReminder> xUserReminderList) 
	{
		Element element = StringToElement(XMLString);
		
		Element e = null;
		while ((e = element.getChild("message")) != null) {
			String mID = e.getAttributeValue("id") != null ? e.getAttributeValue("id") : "";
			String mContent = e.getAttributeValue("content") != null ? e.getAttributeValue("content") : "";
			UserReminder n = new UserReminder(mID, 1, mContent);
			xUserReminderList.add(n);
			element.removeChild("message");
		}
	}	
	public static void ConserveContractInfo(String XMLString, List<ContractInfo> xContractInfo) 
	{
		Element element = StringToElement(XMLString);
		
		Element e = null;
		while ((e = element.getChild("services")) != null) {
			String mContractNo = e.getAttributeValue("contractNo") != null ? e.getAttributeValue("contractNo") : "";
			String mServiceId = e.getAttributeValue("serviceId") != null ? e.getAttributeValue("serviceId") : "";
			ContractInfo n = new ContractInfo(mContractNo, mServiceId);
			xContractInfo.add(n);
			element.removeChild("services");
		}
	}	
}