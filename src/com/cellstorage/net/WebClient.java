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
import android.text.format.Time;
import android.util.Log;

public class WebClient {
	
	/*
	 * �㲥action
	 * */
	//��¼
	public static final String INTERNAL_ACTION_LOGIN="broadcast.LOGIN";
	//��ȡ���з���
	public static final String INTERNAL_ACTION_FINDPRODUCTSERVICELIST="broadcast.FINDPRODUCTSERVICELIST";
	//��ȡ����
	public static final String INTERNAL_ACTION_GETREMINDS="broadcast.GETREMINDS";
	//��ȡ��ͬ�б�
	public static final String INTERNAL_ACTION_FINDSPECIMENLIST="broadcast.FINDSPECIMENLIST";
	//��ȡ��ǰ�б�״̬
	public static final String INTERNAL_ACTION_FINDSAMPLESTAYUSINFO="broadcast.FINDSAMPLESTAYUSINFO";
	//�һ�����
	public static final String INTERNAL_ACTION_FINDPWORD="broadcast.FINDPWORD";
	//�޸�����
	public static final String INTERNAL_ACTION_UPDATEPWORD="broadcast.UPDATEPWORD";
	//�޸�����
	public static final String INTERNAL_ACTION_UPDATEEMAIL="broadcast.UPDATEEMAIL";
	//���߷���
	public static final String INTERNAL_ACTION_SAVEFEEDBACK="broadcast.SAVEFEEDBACK";
	//��������
	public static final String INTERNAL_ACTION_GETSAMPLETRANSVER="broadcast.GETSAMPLETRANSVER";
	//�����Ʊ�
	public static final String INTERNAL_ACTION_GETSAMPLEPREPARATION="broadcast.GETSAMPLEPREPARATION";
	//�������
	public static final String INTERNAL_ACTION_GETSAMPLEDETECTION="broadcast.GETSAMPLEDETECTION";
	//�������
	public static final String INTERNAL_ACTION_GETSAMPLESTORAGE="broadcast.GETSAMPLESTORAGE";
	//��ȡ������ʷ�¶ȣ�һ�ܻ�һ�µ�ƽ���¶ȣ�
	public static final String INTERNAL_ACTION_FINDALLTEMPBYMACDES="broadcast.FINDALLTEMPBYMACDES";
	//��ȡ�汾��Ϣ
	public static final String INTERNAL_ACTION_GETVERSIONINFO="broadcast.GETVERSIONINFO";
	//�����ɼ���׼��������������Ѻ�Ҫ��
	public static final String INTERNAL_ACTION_GETPROCESSINFO="broadcast.GETPROCESSINFO";
	
	private static WebClient mInstance;
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
	//�һ�����
	public static final String Method_findPword = "findPword";
	//�޸�����
	public static final String Method_updatePword = "updatePword";
	//�޸�����
	public static final String Method_updateEmail = "updateEmail";
	//���߷���
	public static final String Method_saveFeedback = "saveFeedback";
	//��������
	public static final String Method_getSampleTransfer = "getSampleTransfer";
	//�����Ʊ�
	public static final String Method_getSamplePreparation = "getSamplePreparation";
	//�������
	public static final String Method_getSampleDetection = "getSampleDetection";
	//�������
	public static final String Method_getSampleStorage = "getSampleStorage";
	//��ȡ��ʷ�¶�
	public static final String Method_findAllTempByMacdes = "findAllTempByMacdes";
	//��ȡ�汾��Ϣ
	public static final String Method_getVersionInfo = "getVersion";
	//�����ɼ���׼��������������Ѻ�Ҫ��
	public static final String Method_getProcessInfo = "getProcessInfo";
	
	/*
	 * ����URL
	*/ 
//	private static String URL_USERAPI = "http://172.16.43.205:9102/cell-ws/services/userAPI"; 
//	private static String URL_OPRATIONAPI = "http://172.16.43.205:9102/cell-ws/services/operationAPI"; 
////	
	private static String URL_USERAPI = "http://58.64.139.239:9102/cell-ws/services/userAPI"; 
	private static String URL_OPRATIONAPI = "http://58.64.139.239:9102/cell-ws/services/operationAPI"; 
	private static String URL_SERVICEPROCESSAPI = "http://58.64.139.239:9102/cell-ws/services/sProcessAPI"; 
	private static String URL_LOGAPI = "http://58.64.200.105:9102/temperature-ws/services/logAPI"; 
	private static String URL_OTHERAPI = "http://58.64.139.239:9102/cell-ws/services/otherAPI";
		
/*	private static String URL_USERAPI = "http://172.16.43.12:9102/cell-ws/services/userAPI"; 
	private static String URL_OPRATIONAPI = "http://172.16.43.12:9102/cell-ws/services/operationAPI"; 
	private static String URL_SERVICEPROCESSAPI = "http://172.16.43.12:9102/cell-ws/services/sProcessAPI"; 
	private static String URL_LOGAPI = "http://172.16.43.12:9102/temperature-ws/services/logAPI"; 
	private static String URL_OTHERAPI = "http://172.16.43.12:9102/cell-ws/services/otherAPI"; 
	*/
/*	private static String URL_USERAPI = "http://172.16.43.205:9102/cell-ws/services/userAPI"; 
	private static String URL_OPRATIONAPI = "http://172.16.43.205:9102/cell-ws/services/operationAPI"; 
	private static String URL_SERVICEPROCESSAPI = "http://172.16.43.205:9102/cell-ws/services/sProcessAPI"; 
	private static String URL_LOGAPI = "http://58.64.200.105:9102/temperature-ws/services/logAPI"; 
	private static String URL_OTHERAPI = "http://172.16.43.205:9102/cell-ws/services/otherAPI"; 
	*/
	// ���ʲ���
	public static final String Param_SendXml = "xml";
	public static final String Param_resXml = "return";


	private static final int timeOut = 10000;//�������糬ʱ
	
	private WebClient(){
	}

	public static WebClient getInstance(){
		if(mInstance == null){
			mInstance = new WebClient();
		}
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
	    	}else if(mMethod.equals(Method_findPword)){
	    		action = INTERNAL_ACTION_FINDPWORD;
	    	}else if(mMethod.equals(Method_updatePword)){
	    		action = INTERNAL_ACTION_UPDATEPWORD;
	    	}else if(mMethod.equals(Method_saveFeedback)){
	    		action = INTERNAL_ACTION_SAVEFEEDBACK;
	    	}else if(mMethod.equals(Method_updateEmail)){
	    		action = INTERNAL_ACTION_UPDATEEMAIL;
	    	}
	    	else if(mMethod.equals(Method_getSampleTransfer)){
	    		action = INTERNAL_ACTION_GETSAMPLETRANSVER;
	    	}
	    	else if(mMethod.equals(Method_getSamplePreparation)){
	    		action = INTERNAL_ACTION_GETSAMPLEPREPARATION;
	    	}
	    	else if(mMethod.equals(Method_getSampleDetection)){
	    		action = INTERNAL_ACTION_GETSAMPLEDETECTION;
	    	}
	    	else if(mMethod.equals(Method_getSampleStorage)){
	    		action = INTERNAL_ACTION_GETSAMPLESTORAGE;
	    	}
	    	else if(mMethod.equals(Method_findAllTempByMacdes)){
	    		action = INTERNAL_ACTION_FINDALLTEMPBYMACDES;
	    	}
	    	else if(mMethod.equals(Method_getVersionInfo)){
	    		action = INTERNAL_ACTION_GETVERSIONINFO;
	    	}
	    	else if(mMethod.equals(Method_getProcessInfo)){
	    		action = INTERNAL_ACTION_GETPROCESSINFO;
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
    		else if(method.equals(Method_findPword)){
    			if(param != null)
    			{
    				String Email = null;
    				Iterator<Entry<String, String>> it= param.entrySet().iterator();
    				while(it.hasNext())
    				{
    					Entry<String, String> entry = it.next();   
    					if(entry.getKey().equals(mContext.getString(R.string.E_Mail)))
    					{
    						Email = entry.getValue();
    					}

    				}
    				Xml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><email>"+Email+"</email></root>";
    				Url = URL_USERAPI;
    			}
    		}
    		else if(method.equals(Method_updatePword)){
    			if(param != null)
    			{
    				String UserID = null;
    				String oldUserPwd = null;
    				String newUserPwd = null;
    				Iterator<Entry<String, String>> it= param.entrySet().iterator();
    				while(it.hasNext())
    				{
    					Entry<String, String> entry = it.next();   
    					if(entry.getKey().equals(mContext.getString(R.string.UserID)))
    					{
    						UserID = entry.getValue();
    					}
    					else if(entry.getKey().equals(mContext.getString(R.string.OldPassword)))
    					{
    						oldUserPwd = entry.getValue();
    					}
    					else if(entry.getKey().equals(mContext.getString(R.string.NewPassword)))
    					{
    						newUserPwd = entry.getValue();
    					}
    				}
    				Xml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><userId>"+UserID+"</userId><oldUserPwd>"+oldUserPwd+"</oldUserPwd><newUserPwd>"+newUserPwd+"</newUserPwd></root>";
    				Url = URL_USERAPI;
    			}
    		}
    		else if(method.equals(Method_saveFeedback)){
    			if(param != null)
    			{
    				String UserID = null;
    				String textContent = null;
    				Iterator<Entry<String, String>> it= param.entrySet().iterator();
    				while(it.hasNext())
    				{
    					Entry<String, String> entry = it.next();   
    					if(entry.getKey().equals(mContext.getString(R.string.UserID)))
    					{
    						UserID = entry.getValue();
    					}
    					else if(entry.getKey().equals(mContext.getString(R.string.TextContent)))
    					{
    						textContent = entry.getValue();
    					}
    				}
    				Xml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><userId>"+UserID+"</userId><content>"+textContent+"</content></root>";
    				Url = URL_OPRATIONAPI;
    			}
    		}
    		else if(method.equals(Method_updateEmail)){
    			if(param != null)
    			{
    				String UserID = null;
    				String OldMail = null;
    				String NewMail = null;
    				Iterator<Entry<String, String>> it= param.entrySet().iterator();
    				while(it.hasNext())
    				{
    					Entry<String, String> entry = it.next();   
    					if(entry.getKey().equals(mContext.getString(R.string.UserID)))
    					{
    						UserID = entry.getValue();
    					}
    					else if(entry.getKey().equals(mContext.getString(R.string.OldMail)))
    					{
    						OldMail = entry.getValue();
    					}
    					else if(entry.getKey().equals(mContext.getString(R.string.NewMail)))
    					{
    						NewMail = entry.getValue();
    					}
    				}
    				Xml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><userId>"+UserID+"</userId><oldEmail>"+OldMail+"</oldEmail><newEmail>"+NewMail+"</newEmail></root>";
    				Url = URL_USERAPI;
    			}
    		}
    		else if(method.equals(Method_getSampleTransfer)){
    			if(param != null)
    			{
    				String SampleStatusId = null;
    				Iterator<Entry<String, String>> it= param.entrySet().iterator();
    				while(it.hasNext())
    				{
    					Entry<String, String> entry = it.next();   
    					if(entry.getKey().equals(mContext.getString(R.string.SampleStatusId)))
    					{
    						SampleStatusId = entry.getValue();
    					}
    				}
    				Xml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><sampleStatusId>"+SampleStatusId+"</sampleStatusId></root>";
    				Url = URL_SERVICEPROCESSAPI;
    			}
    		}
    		else if(method.equals(Method_getSamplePreparation)){
    			if(param != null)
    			{
    				String SampleStatusId = null;
    				Iterator<Entry<String, String>> it= param.entrySet().iterator();
    				while(it.hasNext())
    				{
    					Entry<String, String> entry = it.next();   
    					if(entry.getKey().equals(mContext.getString(R.string.SampleStatusId)))
    					{
    						SampleStatusId = entry.getValue();
    					}
    				}
    				Xml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><sampleStatusId>"+SampleStatusId+"</sampleStatusId></root>";
    				Url = URL_SERVICEPROCESSAPI;
    			}
    		}
    		else if(method.equals(Method_getSampleDetection)){
    			if(param != null)
    			{
    				String SampleStatusId = null;
    				Iterator<Entry<String, String>> it= param.entrySet().iterator();
    				while(it.hasNext())
    				{
    					Entry<String, String> entry = it.next();   
    					if(entry.getKey().equals(mContext.getString(R.string.SampleStatusId)))
    					{
    						SampleStatusId = entry.getValue();
    					}
    				}
    				Xml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><sampleStatusId>"+SampleStatusId+"</sampleStatusId></root>";
    				Url = URL_SERVICEPROCESSAPI;
    			}
    		}
    		else if(method.equals(Method_getSampleStorage)){
    			if(param != null)
    			{
    				String SampleStatusId = null;
    				Iterator<Entry<String, String>> it= param.entrySet().iterator();
    				while(it.hasNext())
    				{
    					Entry<String, String> entry = it.next();   
    					if(entry.getKey().equals(mContext.getString(R.string.SampleStatusId)))
    					{
    						SampleStatusId = entry.getValue();
    					}
    				}
    				Xml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><sampleStatusId>"+SampleStatusId+"</sampleStatusId></root>";
    				Url = URL_SERVICEPROCESSAPI;
    			}
    		}
    		else if(method.equals(Method_findAllTempByMacdes)){
    			if(param != null)
    			{
    				String macdes = null;
    				String sign = null;
    				Iterator<Entry<String, String>> it= param.entrySet().iterator();
    				while(it.hasNext())
    				{
    					Entry<String, String> entry = it.next();   
    					if(entry.getKey().equals(mContext.getString(R.string.MacDes)))
    					{
    						macdes = entry.getValue();
    					}else if(entry.getKey().equals(mContext.getString(R.string.CurveSign))){
    						sign = entry.getValue();
    					}
    				}
    				Xml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><macdes>"+macdes+"</macdes><sign>"+sign+"</sign></root>";
    				Url = URL_LOGAPI;
    			}
    		}
			else if(method.equals(Method_getVersionInfo)){
				Url = URL_OTHERAPI;
				Xml = "";
			}
			else if(method.equals(Method_getProcessInfo)){
				String processType = null;
				String serviceType = null;
				Iterator<Entry<String, String>> it= param.entrySet().iterator();
				while(it.hasNext())
				{
					Entry<String, String> entry = it.next();   
					if(entry.getKey().equals(mContext.getString(R.string.ProcessType)))
					{
						processType = entry.getValue();
					}else if(entry.getKey().equals(mContext.getString(R.string.ServiceType))){
						serviceType = entry.getValue();
					}
				}
				Url = URL_SERVICEPROCESSAPI;
				Xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><processType>"+processType+"</processType><serviceType>"+serviceType+"</serviceType></root>";
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
            	long startTime=System.currentTimeMillis(); //��ȡ��ʼʱ��
                httpTransport.call(ServiceNameSpace+"/"+method, soapEnvelope);
                long endTime=System.currentTimeMillis(); //��ȡ����ʱ��
                Log.d(method,"��������ʱ�䣺 "+(endTime-startTime)+"ms"); 
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
