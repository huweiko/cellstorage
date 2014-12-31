package com.cellstorage.net;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.example.cellstorage.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

public class WebClient {
	
	/*
	 * �㲥action
	 * */
	//��¼
	public static final String INTERNAL_ACTION_LOGIN="com.refeved.monitor.net.broadcast.LOGIN";
	//��ȡ���з���
	public static final String INTERNAL_ACTION_FINDPRODUCTSERVICELIST="com.refeved.monitor.net.broadcast.FINDPRODUCTSERVICELIST";
	//��ȡ����
	public static final String INTERNAL_ACTION_GETREMINDS="com.refeved.monitor.net.broadcast.GETREMINDS";
	//��ȡ��ͬ�б�
	public static final String INTERNAL_ACTION_FINDSPECIMENLIST="com.refeved.monitor.net.broadcast.FINDSPECIMENLIST";
	//��ȡ��ǰ�б�״̬
	public static final String INTERNAL_ACTION_FINDSAMPLESTAYUSINFO="com.refeved.monitor.net.broadcast.FINDSAMPLESTAYUSINFO";
	
	private final static WebClient mInstance = new WebClient();
	/*
	 * �����ռ� 
	 */
	private static final String ServiceNameSpace = "http://api"; 
	
	/*
	 * WebService���ýӿ�
	 */
	//��½�ӿ�
	public static final String Method_login = "login";
	//��ȡ���з���ӿ�
	public static final String Method_findProductServiceList = "findProductServiceList";
	//��ȡ���ѽӿ�
	public static final String Method_getReminds = "getReminds";
	//��ȡ��ͬ�б�ӿ�
	public static final String Method_findSpecimenList = "findSpecimenList";
	//��ȡ��ǰ����״̬
	public static final String Method_findSampleStatusInfo = "findSampleStatusInfo";
	/*
	 * ����URL
	*/ 
	private static String URL_USERAPI = "http://172.16.43.205:9102/cell-ws/services/userAPI"; 
	private static String URL_OPRATIONAPI = "http://172.16.43.205:9102/cell-ws/services/operationAPI"; 
//	
//	private static String URL_USERAPI = "http://172.16.43.7:9102/cell-ws/services/userAPI"; 
//	private static String URL_OPRATIONAPI = "http://172.16.43.7:9102/cell-ws/services/operationAPI"; 
	
	// ���ʲ���
	public static final String Param_SendXml = "xml";
	public static final String Param_resXml = "return";


	private static final int timeOut = 10000;//�������糬ʱ
	
	private WebClient(){
	}

	public static WebClient getInstance(){
		return mInstance;
	}

	
	public void sendMessage(Context context , String method ,Map<String,String> param){
		SendThread sendThread = new SendThread(context ,method,param);
		sendThread.start();
	}
	
	
	public class SendThread extends Thread{
		Map<String,String> mParam = null;
    	String mMethod = null;
    	Context mContext = null;
    	
    	public SendThread(Context context ,String method,Map<String,String> param){
    		mParam = param;
    		mContext = context;
    		mMethod = method;
    	}
    	
    	@Override 
    	public void run(){
    		String result = null;
    		try {
				result = CallWebServer(mParam,mMethod);
			} catch (Exception e) {
				e.printStackTrace();
			}
    		
    		if(result == null){
    			result = "null";
    		}
			String action = null;
			if(mMethod.equals(Method_login)){
				action = INTERNAL_ACTION_LOGIN;
			}else if(mMethod.equals(Method_findProductServiceList)){
				action = INTERNAL_ACTION_FINDPRODUCTSERVICELIST;
			}else if(mMethod.equals(Method_findSpecimenList)){
				action = INTERNAL_ACTION_FINDSPECIMENLIST;
			}else if(mMethod.equals(Method_getReminds)){
				action = INTERNAL_ACTION_GETREMINDS;
			}else if(mMethod.equals(Method_findSampleStatusInfo)){
				action = INTERNAL_ACTION_FINDSAMPLESTAYUSINFO;
			}
			
			Intent intent = new Intent(action);
			intent.putExtra(WebClient.Param_resXml, result);
			mContext.sendBroadcast(intent);
    	}

    	@SuppressLint("SimpleDateFormat") private String CallWebServer(Map<String,String> param,String method) throws XmlPullParserException
    	{
			String Url = null;
			String Xml = null;
    		if(method.equals(Method_login))
    		{	
                if(param != null)
                {
                	Iterator<Entry<String, String>> it= param.entrySet().iterator();
                    while(it.hasNext())
                    {
                    	Entry<String, String> entry = it.next();   
                    	Xml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><userName>"+entry.getKey()+"</userName><userPwd>"+entry.getValue()+"</userPwd></root>";
                    }	
                    Url = URL_USERAPI;
                }
    		}
    		else if(method.equals(Method_findProductServiceList)){
                if(param != null)
                {
                	String UserID = null;
                	Iterator<Entry<String, String>> it= param.entrySet().iterator();
                    while(it.hasNext())
                    {
                    	Entry<String, String> entry = it.next();   
                      	if(entry.getKey().equals(mContext.getString(R.string.UserID)))
                    	{
                      		UserID = entry.getValue();
                    	}
                    	
                    }
                    Xml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><userId>"+UserID+"</userId></root>";
                    Url = URL_OPRATIONAPI;
                }
    		}
			
    		else if(method.equals(Method_getReminds)){
    			if(param != null)
    			{
    				String UserID = null;
    				Iterator<Entry<String, String>> it= param.entrySet().iterator();
    				while(it.hasNext())
    				{
    					Entry<String, String> entry = it.next();   
    					if(entry.getKey().equals(mContext.getString(R.string.UserID)))
    					{
    						UserID = entry.getValue();
    					}
    					
    				}
    				Xml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><userId>"+UserID+"</userId></root>";
    				Url = URL_OPRATIONAPI;
    			}
    		}
    		else if(method.equals(Method_findSpecimenList)){
    			if(param != null)
    			{
    				String UserID = null;
    				String ServiceType = null;
    				Iterator<Entry<String, String>> it= param.entrySet().iterator();
    				while(it.hasNext())
    				{
    					Entry<String, String> entry = it.next();   
    					if(entry.getKey().equals(mContext.getString(R.string.UserID)))
    					{
    						UserID = entry.getValue();
    					}
    					else if(entry.getKey().equals(mContext.getString(R.string.ServiceType)))
    					{
    						ServiceType = entry.getValue();
    					}
    					
    				}
    				Xml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><userId>"+UserID+"</userId><serviceType>"+ServiceType+"</serviceType></root>";
    				Url = URL_OPRATIONAPI;
    			}
    		}
    		else if(method.equals(Method_findSampleStatusInfo)){
    			if(param != null)
    			{
    				String ServiceID = null;
    				String contractNo = null;
    				Iterator<Entry<String, String>> it= param.entrySet().iterator();
    				while(it.hasNext())
    				{
    					Entry<String, String> entry = it.next();   
    					if(entry.getKey().equals(mContext.getString(R.string.ServiceID)))
    					{
    						ServiceID = entry.getValue();
    					}
    					else if(entry.getKey().equals(mContext.getString(R.string.ContractNo)))
    					{
    						contractNo = entry.getValue();
    					}
    					
    				}
    				Xml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><serviceId>"+ServiceID+"</serviceId><contractNo>"+contractNo+"</contractNo></root>";
    				Url = URL_OPRATIONAPI;
    			}
    		}
    		
    		if(Url != null)
    		{
    			try {
    				return readContentFromWeb(Url,method,Xml);
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    		
    		return null;
        }
		private String readContentFromWeb(String Url,String method,String Xml) throws IOException, XmlPullParserException {

    		String resXml = null;
    		SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            SoapObject soapReq = new SoapObject(ServiceNameSpace,method);

            soapReq.addProperty(Param_SendXml,Xml);
            soapEnvelope.setOutputSoapObject(soapReq);
            HttpTransportSE httpTransport = new HttpTransportSE(Url,timeOut);
            try{
                httpTransport.call(ServiceNameSpace+"/"+method, soapEnvelope);
                Object retObj = soapEnvelope.bodyIn;
                if (retObj instanceof SoapFault){
                    SoapFault fault = (SoapFault)retObj;
                    Exception ex = new Exception(fault.faultstring);
                    ex.printStackTrace();
                }else{
                    SoapObject result=(SoapObject)retObj;
                    if (result.getPropertyCount() > 0){
	                   if (result.hasProperty(Param_resXml))
	                   {
	                	   Object obj = result.getProperty(Param_resXml);
	                       if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
	                    	   resXml =  ((SoapPrimitive) obj).toString();
	                       }else if (obj!= null && obj instanceof String){
	                           resXml = (String) obj;
	                       }
	                       
	                       return resXml;
	                   }
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            
            return null;
    	}
    }
}
