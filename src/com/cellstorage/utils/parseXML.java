package com.cellstorage.utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.cellstorage.OtherHealper;
import com.cellstorage.struct.ContractInfo;
import com.cellstorage.struct.SampleStatusDetection;
import com.cellstorage.struct.SampleStatusInfo;
import com.cellstorage.struct.SampleStatusPreparation;
import com.cellstorage.struct.SampleStatusStorage;
import com.cellstorage.struct.SampleStatusTakeover;
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
	public static void ConserveSampleStatusInfo(String XMLString, SampleStatusInfo mSampleStatusInfo) 
	{
		Element rootElement = StringToElement(XMLString);
		
		Element e = null;
		
		if(rootElement != null && mSampleStatusInfo != null){
			if((e =rootElement.getChild("sampleStatusId")) != null){
				if(!e.getValue().equals("")){
					mSampleStatusInfo.setSampleStatusId(Integer.parseInt(e.getValue()));
				}
			}  
			if((e =rootElement.getChild("qualityCL")) != null){
				if(!e.getValue().equals("")){
					mSampleStatusInfo.setQualityCL(e.getValue());
				}
			}  
			if((e =rootElement.getChild("process")) != null){
				if(!e.getValue().equals("")){
					mSampleStatusInfo.setSampleProcess(e.getValue());
				}
			}  
			if((e =rootElement.getChild("range")) != null){
				if(!e.getValue().equals("")){
					mSampleStatusInfo.setQualityCLRange(e.getValue());
				}
			}  
			if((e =rootElement.getChild("status")) != null){
				if(!e.getValue().equals("")){
					mSampleStatusInfo.setQualityCLStatus(e.getValue());
				}
			}  
			if((e =rootElement.getChild("applyTime")) != null){
				if(!e.getValue().equals("")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfo.setApplyTime(aa);
				}
			}  
			if((e =rootElement.getChild("collectTime")) != null){
				if(!e.getValue().equals("")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfo.setCollectTime(aa);
				}
			}
			if((e =rootElement.getChild("prepareTime")) != null){
				if(!e.getValue().equals("")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfo.setPrepareTime(aa);
				}
			}
			if((e =rootElement.getChild("primaryTime")) != null){
				if(!e.getValue().equals("")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfo.setPrimaryTime(aa);
				}
			}
			if((e =rootElement.getChild("reportTime")) != null){
				if(!e.getValue().equals("")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfo.setReportTime(aa);
				}
			}
			if((e =rootElement.getChild("signedTime")) != null){
				if(!e.getValue().equals("")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfo.setSignedTime(aa);
				}
			}
			if((e =rootElement.getChild("startTransTime")) != null){
				if(!e.getValue().equals("")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfo.setStartTransTime(aa);
				}
			}
			if((e =rootElement.getChild("storageTime")) != null){
				if(!e.getValue().equals("")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfo.setStorageTime(aa);
				}
			}
			
			if((e =rootElement.getChild("takeoverTime")) != null){
				if(!e.getValue().equals("")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					mSampleStatusInfo.setTakeoverTime(aa);
				}
			}
		}
	}
	public static void ConserveServiceStatus(String XMLString, List<ServiceStatus> xServiceStatusList) 
	{
		Element element = StringToElement(XMLString);
		
		Element e = null;
		if(element != null){
			while ((e = element.getChild("services")) != null) {
				String mType = e.getAttributeValue("type") != null ? e.getAttributeValue("type") : "";
				String mStatus = e.getAttributeValue("status") != null ? e.getAttributeValue("status") : "";
				boolean bStatus = mStatus.equals("0")?false:true;
				ServiceStatus n = new ServiceStatus(mType, bStatus);
				xServiceStatusList.add(n);
				element.removeChild("services");
			}
		}

	}	
	public static void ConserveReminder(String XMLString, List<UserReminder> xUserReminderList) 
	{
		UserInfo mUserInfo = UserInfo.getAppManager();
		Element e = null;
		Element element = StringToElement(XMLString);
		if(element != null){
			while ((e = element.getChild("message")) != null) {
				String mID = e.getAttributeValue("id") != null ? e.getAttributeValue("id") : "";
				String mContent = e.getAttributeValue("content") != null ? e.getAttributeValue("content") : "";
				UserReminder n = new UserReminder(mUserInfo.getUserID(),mID, 1, mContent);
				xUserReminderList.add(n);
				element.removeChild("message");
			}
		}
		

	}	
	public static void ConserveContractInfo(String XMLString, List<ContractInfo> xContractInfo) 
	{
		Element element = StringToElement(XMLString);
		
		Element e = null;
		if(element != null){
			while ((e = element.getChild("services")) != null) {
				String mContractNo = e.getAttributeValue("contractNo") != null ? e.getAttributeValue("contractNo") : "";
				String mServiceId = e.getAttributeValue("serviceId") != null ? e.getAttributeValue("serviceId") : "";
				ContractInfo n = new ContractInfo(mContractNo, mServiceId);
				xContractInfo.add(n);
				element.removeChild("services");
			}
		}

	}	
	public static void ConserveSampleTakeoverInfo(String XMLString, SampleStatusTakeover xSampleStatusTakeover) 
	{
		Element element = StringToElement(XMLString);
		
		Element e = null;
		if(element != null && xSampleStatusTakeover != null)
		{
			 if((e =element.getChild("takeoverSurTemp")) != null)
			 {
				 xSampleStatusTakeover.setTakeoverSurTemp(e.getValue());
			 }  
			 if((e =element.getChild("sampleQuantity")) != null)
			 {
				 xSampleStatusTakeover.setSampleQuantity(e.getValue());
			 }  
			 if((e =element.getChild("wrapStatus")) != null)
			 {
				 xSampleStatusTakeover.setWrapStatus(e.getValue());
			 }  
			 if((e =element.getChild("takeoverVerdict")) != null)
			 {
				 xSampleStatusTakeover.setTakeoverVerdict(e.getValue());
			 }  
		
		}
		
	}	
	public static void ConserveSamplePreparationInfo(String XMLString, SampleStatusPreparation xSampleStatusPreparation) 
	{
		Element element = StringToElement(XMLString);
		
		Element e = null;
		if(element != null && xSampleStatusPreparation != null)
		{
			if((e =element.getChild("primaryTime")) != null)
			{
				if(!e.getValue().equals("")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					xSampleStatusPreparation.setPrimaryTime(aa);
				}
				
			}  
			if((e =element.getChild("pass0Time")) != null)
			{
				if(!e.getValue().equals("")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					xSampleStatusPreparation.setPass0Time(aa);
				}
				
			}  
			if((e =element.getChild("pass1Time")) != null)
			{
				if(!e.getValue().equals("")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					xSampleStatusPreparation.setPass1Time(aa);
				}
				
			}  
			if((e =element.getChild("pass2Time")) != null)
			{
				if(!e.getValue().equals("")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					xSampleStatusPreparation.setPass2Time(aa);
				}
				
			}  
			if((e =element.getChild("freezonTime")) != null)
			{
				if(!e.getValue().equals("")){
					String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
					Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
					xSampleStatusPreparation.setFreezonTime(aa);
				}
				
			}  

			if((e =element.getChild("primaryStatus")) != null)
			{
				xSampleStatusPreparation.setPrimaryStatus(e.getValue());
			}  
			if((e =element.getChild("pass0Status")) != null)
			{
				xSampleStatusPreparation.setPass0Status(e.getValue());
			}  
			if((e =element.getChild("pass1Status")) != null)
			{
				xSampleStatusPreparation.setPass1Status(e.getValue());
			}  
			if((e =element.getChild("pass2Status")) != null)
			{
				xSampleStatusPreparation.setPass2Status(e.getValue());
			}  
			if((e =element.getChild("freezonStatus")) != null)
			{
				xSampleStatusPreparation.setFreezonStatus(e.getValue());
			}  
			
		}
		
	}	
	public static void ConserveSampleDetectionInfo(String XMLString, SampleStatusDetection xSampleStatusDetection) 
	{
		Element element = StringToElement(XMLString);
		
		Element e = null;
		if(element != null && xSampleStatusDetection != null)
		{
			
			if((e =element.getChild("examZyt")) != null)
			{
				xSampleStatusDetection.setExamZyt(e.getValue());
			}  
			if((e =element.getChild("examXyzj")) != null)
			{
				xSampleStatusDetection.setExamXyzj(e.getValue());
			}  
			if((e =element.getChild("examYyzj")) != null)
			{
				xSampleStatusDetection.setExamYyzj(e.getValue());
			}  
			if((e =element.getChild("examXbbmky")) != null)
			{
				xSampleStatusDetection.setExamXbbmky(e.getValue());
			}  
			if((e =element.getChild("examVerdict")) != null)
			{
				xSampleStatusDetection.setExamVerdict(e.getValue());
			}  
			if((e =element.getChild("examNds")) != null)
			{
				xSampleStatusDetection.setExamNDS(e.getValue());
			}  
			
		}
		
	}	
	public static void ConserveSampleStorageInfo(String XMLString, SampleStatusStorage xSampleStatusStorage) 
	{
		Element element = StringToElement(XMLString);
		
		Element e = null;
		if(element != null && xSampleStatusStorage != null)
		{
			if((e =element.getChild("storageTime")) != null)
			{
				String l_DateFormat = "yyyy-MM-dd hh:mm:ss";
				Date aa = OtherHealper.StringToDate(e.getValue(), l_DateFormat);
				xSampleStatusStorage.setStorageTime(aa);
			}  
			
			if((e =element.getChild("livingcellNum")) != null)
			{
				xSampleStatusStorage.setLivingcellNum(e.getValue());
			}  
			if((e =element.getChild("livingcellRate")) != null)
			{
				xSampleStatusStorage.setLivingcellRate(e.getValue());
			}  
			if((e =element.getChild("storageTemp")) != null)
			{
				xSampleStatusStorage.setStorageTemp(e.getValue());
			}  
			if((e =element.getChild("storageVerdict")) != null)
			{
				xSampleStatusStorage.setStorageVerdict(e.getValue());
			}  
			if((e =element.getChild("fridgeName")) != null)
			{
				xSampleStatusStorage.setFridgeName(e.getValue());
			}  
		}
		
	}	
	public static HashMap<String, String> ConserveVersionUpdateInfo(String resXml) throws Exception
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		SAXBuilder builder = new SAXBuilder();
		StringReader sr = new StringReader(resXml);   
		InputSource is = new InputSource(sr); 
		Document Doc = builder.build(is);
		Element rootElement = (Element) Doc.getRootElement();

		Element e = null;
		 if((e =rootElement.getChild("version")) != null)
		 {
			hashMap.put("version",e.getValue());
		 }  
		 if((e =rootElement.getChild("name")) != null)
		 {
			 hashMap.put("name",e.getValue());
		 } 
		 if((e =rootElement.getChild("url")) != null)
		 {
			 hashMap.put("url",e.getValue());
		 } 
		 if((e =rootElement.getChild("upgrade")) != null)
		 {
			 hashMap.put("upgrade",e.getValue());
		 } 
		 if((e =rootElement.getChild("content")) != null)
		 {
			 hashMap.put("content",e.getValue());
		 } 

		return hashMap;
	}
	public static void ConserveSampleProcessInfo(String XMLString,List<String> x_SampleProcessInfoWare,List<String> x_SampleProcessInfoRequst)
	{
		Element element = StringToElement(XMLString);
		
		Element childElement = null;
		if((childElement = element.getChild("list")) != null){
			Element e = null;
			while((e = childElement.getChild("remind")) != null){
				x_SampleProcessInfoWare.add(e.getValue());
				childElement.removeChild("remind");
			}
			element.removeChild("list");
		}
		if((childElement = element.getChild("list")) != null){
			Element e = null;
			while((e = childElement.getChild("demand")) != null){
				x_SampleProcessInfoRequst.add(e.getValue());
				childElement.removeChild("demand");
			}
			element.removeChild("list");
		}
	}
}