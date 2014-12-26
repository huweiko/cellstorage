package com.cellstorage.utils;

import java.io.StringReader;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.cellstorage.struct.ServiceStatus;
import com.cellstorage.struct.UserInfo;

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
	public static void ConserveServiceStatus(String XMLString, List<ServiceStatus> xServiceStatusList) 
	{
		Element element = StringToElement(XMLString);
		
		Element e = null;
		while ((e = element.getChild("services")) != null) {
			String mType = e.getChild("type") != null ? e.getChild("type")
					.getValue() : "";
			String mStatus = e.getChild("status") != null ? e.getChild(
					"status").getValue() : "";
			boolean bStatus = mStatus.equals("0")?false:true;
			ServiceStatus n = new ServiceStatus(mType, bStatus);
			xServiceStatusList.add(n);
			element.removeChild("services");
		}
	}	
}