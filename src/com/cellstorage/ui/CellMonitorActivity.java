package com.cellstorage.ui;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.cellstorage.AppContext;
import com.cellstorage.UIHealper;
import com.cellstorage.net.WebClient;
import com.cellstorage.struct.ContractInfo;
import com.cellstorage.struct.SampleStatusInfoTBL;
import com.cellstorage.struct.UserInfo;
import com.cellstorage.ui.MainActivity.GetReminderMsgThread;
import com.cellstorage.utils.parseXML;
import com.cellstorage.view.CellProcessNodeView;
import com.cellstorage.view.CellProcessNodeView.OnClickItemProcessNode;
import com.example.cellstorage.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
@EActivity(R.layout.activity_cell_monitor)
public class CellMonitorActivity extends Activity{
	private LinearLayout mLinearLayoutText;
	private AppContext appContext;
	private String mServiceType;
	private UserInfo mUserInfo;
	private SampleStatusInfoTBL mSampleStatusInfoTBL;
	private List<ContractInfo> lpContractInfo = new ArrayList<ContractInfo>();
	private ContractInfo mCurrentContractInfo;
	private DecimalFormat temFormat = new DecimalFormat("00");
	private SimpleDateFormat mYearFormat = new SimpleDateFormat("yyyy");
	private static final String[] week= {"������", "����һ", "���ڶ�", "������", "������", "������", "������"};
	private static final String[] mSamplePreparation = {"","ԭ������|�޾�","����P0|�޾�","����P1|�޾�","����P2|�޾�","����|�޾�"};
	private static final String[] mSampleNodeName = {"","��ѯ��ԤԼ","ǩ����ͬ","�ɼ�׼��","�����ɼ�","��������","��������","�����Ʊ�","��ⱨ��","�������"};
	private static final String[] mSampleNodeTime = new String[10];
	private static String mQCPoint = new String();
	private static String mQCStutas = new String();
	private static String mQCRange = new String();
	private static String mSampleAddress = new String();
	//����ǰ״̬
	public static int mSampleCurrentStatus = 0;
	//���ذ�ť
	@ViewById(R.id.ImageButtonMonitorBack)
	public ImageButton mImageButtonMonitorBack;
	
	//������ַ
	@ViewById(R.id.TextViewMonitorAddress)
	public TextView mTextViewMonitorAddress;
	
	//��ǰʱ��
	@ViewById(R.id.TextViewMonitorTime)
	public TextView mTextViewMonitorTime;
	
	//ˢ�°�ť
	@ViewById(R.id.ImageButtonMonitorFresh)
	public ImageButton mImageButtonMonitorFresh;
	
	//��ͬ���
	@ViewById(R.id.TextViewMonitorContractNum)
	public TextView mTextViewMonitorContractNum;
	
	//����ʱ��
	@ViewById(R.id.TextViewMonitorUpdateTime)
	public TextView mTextViewMonitorUpdateTime;
	
	//�ʿص�
	@ViewById(R.id.TextViewMonitorQCP)
	public TextView mTextViewMonitorQCP;
	
	//����״̬
	@ViewById(R.id.TextViewMonitorSampleStatus)
	public TextView mTextViewMonitorSampleStatus;
	
	//�ʿط�Χ
	@ViewById(R.id.TextViewMonitorControlRange)
	public TextView mTextViewMonitorControlRange;
	
	private static final int REFRESH_DATA = 1;
	private void getSampleTime(){
		Date today = null;
		String l_Year = null;
		String l_Month = null;
		String l_getDate = null;
		String l_Hours = null;
		String l_Minutes = null;
		if(mSampleStatusInfoTBL.getStorageTime() != null){
			today = mSampleStatusInfoTBL.getStorageTime();
			l_Year = mYearFormat.format(today);
			l_Month = temFormat.format(today.getMonth());
			l_getDate = temFormat.format(today.getDate());
			l_Hours = temFormat.format(today.getHours());
			l_Minutes = temFormat.format(today.getMinutes());
			mSampleNodeTime[9] = l_Year+"�� "+l_Month+"�� "+l_getDate+"�� "+l_Hours+"ʱ "+l_Minutes+"��";
		}
		if(mSampleStatusInfoTBL.getReportTime() != null){
			today = mSampleStatusInfoTBL.getReportTime();
			l_Year = mYearFormat.format(today);
			l_Month = temFormat.format(today.getMonth());
			l_getDate = temFormat.format(today.getDate());
			l_Hours = temFormat.format(today.getHours());
			l_Minutes = temFormat.format(today.getMinutes());
			mSampleNodeTime[8] = l_Year+"�� "+l_Month+"�� "+l_getDate+"�� "+l_Hours+"ʱ "+l_Minutes+"��";
		}
		if(mSampleStatusInfoTBL.getPrimaryTime() != null){
			today = mSampleStatusInfoTBL.getPrimaryTime();
			l_Year = mYearFormat.format(today);
			l_Month = temFormat.format(today.getMonth());
			l_getDate = temFormat.format(today.getDate());
			l_Hours = temFormat.format(today.getHours());
			l_Minutes = temFormat.format(today.getMinutes());
			mSampleNodeTime[7] = l_Year+"�� "+l_Month+"�� "+l_getDate+"�� "+l_Hours+"ʱ "+l_Minutes+"��";
		}
		if(mSampleStatusInfoTBL.getTakeoverTime() != null){
			today = mSampleStatusInfoTBL.getTakeoverTime();
			l_Year = mYearFormat.format(today);
			l_Month = temFormat.format(today.getMonth());
			l_getDate = temFormat.format(today.getDate());
			l_Hours = temFormat.format(today.getHours());
			l_Minutes = temFormat.format(today.getMinutes());
			mSampleNodeTime[6] = l_Year+"�� "+l_Month+"�� "+l_getDate+"�� "+l_Hours+"ʱ "+l_Minutes+"��";
		}
		if(mSampleStatusInfoTBL.getStartTransTime() != null){
			today = mSampleStatusInfoTBL.getStartTransTime();
			l_Year = mYearFormat.format(today);
			l_Month = temFormat.format(today.getMonth());
			l_getDate = temFormat.format(today.getDate());
			l_Hours = temFormat.format(today.getHours());
			l_Minutes = temFormat.format(today.getMinutes());
			mSampleNodeTime[5] = l_Year+"�� "+l_Month+"�� "+l_getDate+"�� "+l_Hours+"ʱ "+l_Minutes+"��";
		}
		if(mSampleStatusInfoTBL.getCollectTime() != null){
			today = mSampleStatusInfoTBL.getCollectTime();
			l_Year = mYearFormat.format(today);
			l_Month = temFormat.format(today.getMonth());
			l_getDate = temFormat.format(today.getDate());
			l_Hours = temFormat.format(today.getHours());
			l_Minutes = temFormat.format(today.getMinutes());
			mSampleNodeTime[4] = l_Year+"�� "+l_Month+"�� "+l_getDate+"�� "+l_Hours+"ʱ "+l_Minutes+"��";
		}
		if(mSampleStatusInfoTBL.getPrepareTime() != null){
			today = mSampleStatusInfoTBL.getPrepareTime();
			l_Year = mYearFormat.format(today);
			l_Month = temFormat.format(today.getMonth());
			l_getDate = temFormat.format(today.getDate());
			l_Hours = temFormat.format(today.getHours());
			l_Minutes = temFormat.format(today.getMinutes());
			mSampleNodeTime[3] = l_Year+"�� "+l_Month+"�� "+l_getDate+"�� "+l_Hours+"ʱ "+l_Minutes+"��";
		}
		if(mSampleStatusInfoTBL.getSignedTime() != null){
			today = mSampleStatusInfoTBL.getSignedTime();
			l_Year = mYearFormat.format(today);
			l_Month = temFormat.format(today.getMonth());
			l_getDate = temFormat.format(today.getDate());
			l_Hours = temFormat.format(today.getHours());
			l_Minutes = temFormat.format(today.getMinutes());
			mSampleNodeTime[2] = l_Year+"�� "+l_Month+"�� "+l_getDate+"�� "+l_Hours+"ʱ "+l_Minutes+"��";
		}
		if(mSampleStatusInfoTBL.getApplyTime() != null){
			today = mSampleStatusInfoTBL.getApplyTime();
			l_Year = mYearFormat.format(today);
			l_Month = temFormat.format(today.getMonth());
			l_getDate = temFormat.format(today.getDate());
			l_Hours = temFormat.format(today.getHours());
			l_Minutes = temFormat.format(today.getMinutes());
			mSampleNodeTime[1] = l_Year+"�� "+l_Month+"�� "+l_getDate+"�� "+l_Hours+"ʱ "+l_Minutes+"��";
		}
		
	}
	private int getSamplePreparationStatus(){
		int l_SamplePreparationStatus = 0;
		if(mSampleStatusInfoTBL.getFreezonTime() != null){
			l_SamplePreparationStatus = 5;
		}
		else if(mSampleStatusInfoTBL.getPass2Time() != null){
			l_SamplePreparationStatus = 4;
		}
		else if(mSampleStatusInfoTBL.getPass1Time() != null){
			l_SamplePreparationStatus = 3;
		}
		else if(mSampleStatusInfoTBL.getPass0Time() != null){
			l_SamplePreparationStatus = 2;
		}
		else if(mSampleStatusInfoTBL.getPrimaryTime() != null){
			l_SamplePreparationStatus = 1;
		}
		else{
			l_SamplePreparationStatus = 0;
		}
		return l_SamplePreparationStatus;
	}
	private void getQCInfo(){
		int l_QCStatus = getSampleCurrentStatus();
		mSampleAddress = "����";
		if(l_QCStatus == 1){
			mQCPoint = "��ѯ��ԤԼ";
			mQCStutas = "����";
			mQCRange = "��";
		}
		else if(l_QCStatus == 2){
			mQCPoint = "ǩ����ͬ";
			mQCStutas = "����";
			mQCRange = "��";
		}
		else if(l_QCStatus == 3){
			mQCPoint = "�ύ�м챨��";
			mQCStutas = "����";
			mQCRange = "��";
			
		}
		else if(l_QCStatus == 4){
			Float l_CollectQuantity = mSampleStatusInfoTBL.getCollectQuantity();
			mQCPoint = l_CollectQuantity+"cm";
			if(l_CollectQuantity >= 15){
				mQCStutas = "����";
			}else{
				mQCStutas = "�쳣";
			}
					
			mQCRange = "��15cm|�޾�";
			mSampleAddress = mSampleStatusInfoTBL.getCollectPlace();
		}
		else if(l_QCStatus == 5){
			Float l_ArriveSurTemp = mSampleStatusInfoTBL.getArriveSurTemp();
			mQCPoint = l_ArriveSurTemp+"��";
			if(l_ArriveSurTemp>=4 && l_ArriveSurTemp <= 10){
				mQCStutas = "����";
			}else{
				mQCStutas = "�쳣";
			}
			
			mQCRange = "4~10��|��24h";
			mSampleAddress = mSampleStatusInfoTBL.getArrivePlace();
		}
		else if(l_QCStatus == 6){
			Float l_SampleQuantity = mSampleStatusInfoTBL.getSampleQuantity();
			Float l_TakeoverSurTemp = mSampleStatusInfoTBL.getTakeoverSurTemp();
			mQCPoint = l_SampleQuantity+"cm|"+l_TakeoverSurTemp+"��";
			if(l_SampleQuantity >= 15 && l_TakeoverSurTemp >= 4 && l_TakeoverSurTemp <= 10){
				mQCStutas = "����";
			}else{
				mQCStutas = "�쳣";
			}
			
			mQCRange = "��15cm|4~10��";
		}
		else if(l_QCStatus == 7){
			mQCPoint = mSamplePreparation[getSamplePreparationStatus()];
			mQCStutas = "����";
			mQCRange = "��";
		}
		else if(l_QCStatus == 8){
			mQCPoint = "������������ⱨ�桷";
			mQCStutas = "����";
			mQCRange = "��";
		}
		else if(l_QCStatus == 9){
			Float l_StorageTemp = mSampleStatusInfoTBL.getStorageTemp();
			mQCPoint = l_StorageTemp+"��";
			if(l_StorageTemp>= -196 && l_StorageTemp <= -135){
				mQCStutas = "����";
			}else{
				mQCStutas = "�쳣";
			}
			mQCRange = "-135��~-196��  ";
		}
		else{
			mQCPoint = "";
			mQCStutas = "";
			mQCRange = "";
		}
	}
	
	private int getSampleCurrentStatus(){
		if(mSampleStatusInfoTBL.getStorageTime() != null){
			mSampleCurrentStatus = 9;
		}
		else if(mSampleStatusInfoTBL.getReportTime() != null){
			mSampleCurrentStatus = 8;
		}
		else if(mSampleStatusInfoTBL.getPrimaryTime() != null){
			mSampleCurrentStatus = 7;
		}
		else if(mSampleStatusInfoTBL.getTakeoverTime() != null){
			mSampleCurrentStatus = 6;
		}
		else if(mSampleStatusInfoTBL.getStartTransTime() != null){
			mSampleCurrentStatus = 5;
		}
		else if(mSampleStatusInfoTBL.getCollectTime() != null){
			mSampleCurrentStatus = 4;
		}
		else if(mSampleStatusInfoTBL.getPrepareTime() != null){
			mSampleCurrentStatus = 3;
		}
		else if(mSampleStatusInfoTBL.getSignedTime() != null){
			mSampleCurrentStatus = 2;
		}
		else if(mSampleStatusInfoTBL.getApplyTime() != null){
			mSampleCurrentStatus = 1;
		}else{
			mSampleCurrentStatus = 0;
		}
		return mSampleCurrentStatus;
	}
	@SuppressLint("SimpleDateFormat") 
	private void updateData(){
		//���ú�ͬ��
		mTextViewMonitorContractNum.setText(mCurrentContractInfo.getContractNo());
		SimpleDateFormat updateTimeFormat = new SimpleDateFormat("hh:mm:ss");
		Date l_updateTime = new Date();
		//���ø���ʱ��
		mTextViewMonitorUpdateTime.setText(updateTimeFormat.format(l_updateTime)+"����");

		getQCInfo();
		mTextViewMonitorAddress.setText(mSampleAddress);
		mTextViewMonitorQCP.setText(mQCPoint);
		mTextViewMonitorSampleStatus.setText(mQCStutas);
		mTextViewMonitorControlRange.setText(mQCRange);
		
		int l_ProccessCurrentstatus = getSampleCurrentStatus();
		CellProcessNodeView.LoopNodeCount = 0;
		mLinearLayoutText.removeAllViews();
		getSampleTime();
		for(int i = 1;i <= 9;i++){
			CellProcessNodeView CellNode = new CellProcessNodeView(appContext); 
			
			if(i <= l_ProccessCurrentstatus){
				CellNode.setmNodeStatus(CellProcessNodeView.NODE_STATUS_DONE);
			}else{
				CellNode.setmNodeStatus(CellProcessNodeView.NODE_STATUS_WILLDO);
			}
			if((i+1)%2 == 0){
				CellNode.setmShowTextStatus(CellProcessNodeView.SHOW_LEFT);
			}else{
				CellNode.setmShowTextStatus(CellProcessNodeView.SHOW_RIGHT);
			}
			
			CellNode.setmNodeTextContent(mSampleNodeName[i]);
			CellNode.setmNodeTimeContent(mSampleNodeTime[i]);
			CellNode.create(i);
			mLinearLayoutText.addView(CellNode);
		}
	}
	
	@SuppressWarnings("deprecation")
	@UiThread
	public void GetCurrentTimeThread(){
		Date today = new Date();
		String l_Month = temFormat.format(today.getMonth());
		String l_getDate = temFormat.format(today.getDate());
		String l_Day = week[today.getDay()];
		String l_Hours = temFormat.format(today.getHours());
		String l_Minutes = temFormat.format(today.getMinutes());
		mTextViewMonitorTime.setText(l_Month+"��"+l_getDate+"�� "+l_Day+" "+l_Hours+":"+l_Minutes);
	}
   @Background
   void someBackgroundWork() {
	/*   try {
		   TimeUnit.SECONDS.sleep(5);
	   } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	   }*/
   		GetCurrentTimeThread();
   }
	public Handler mHandler=new Handler()  
	{  
		public void handleMessage(Message msg)  
		{  
			switch(msg.what)  
			{  
			case REFRESH_DATA:{
				updateData();
			}
				break;  
			default:  
				break;            
			}  
			super.handleMessage(msg);  
		}  
	}; 
	
	@AfterViews
	public void Init(){
		appContext = (AppContext) getApplication();
        //ȥ����Ϣ��
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mUserInfo = UserInfo.getAppManager();
		mSampleStatusInfoTBL = new SampleStatusInfoTBL();
		//��ȡintent����ֵ
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			mServiceType = extras.getString(getString(R.string.ServiceType));	
		}else{
		}
		new Thread(){
			public void run(){
				while(true){
					try {
						someBackgroundWork();
						sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}.start();
		IntentFilter filter = new IntentFilter();
		filter.addAction(WebClient.INTERNAL_ACTION_FINDSPECIMENLIST);
		filter.addAction(WebClient.INTERNAL_ACTION_FINDSAMPLESTAYUSINFO);
		appContext.registerReceiver(receiver, filter);
		updateFindSpecimenList();
		mLinearLayoutText = (LinearLayout) findViewById(R.id.LinearLayoutTest);
		CellProcessNodeView.setmOnClickItemProcessNode(ShowNodeActivity);

	}
	@Click
	public void ImageButtonMonitorBack(){
		finish();
	}
	@Click
	public void ImageButtonMonitorFresh(){
		updateindSampleStatusInfo();
	}
	private final BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			String resXml = intent.getStringExtra(WebClient.Param_resXml);
			
			if(intent.getAction().equals(WebClient.INTERNAL_ACTION_FINDSPECIMENLIST))
			{

				if(resXml != null)
				{
					if(resXml.equals("error"))
					{
					}
					else if(resXml.equals("null")){
					}
					else
					{
						lpContractInfo.clear();
						parseXML.ConserveContractInfo(resXml, lpContractInfo);
						if(!lpContractInfo.isEmpty()){
							updateindSampleStatusInfo();
						}
					}
				}
			}
			else if(intent.getAction().equals(WebClient.INTERNAL_ACTION_FINDSAMPLESTAYUSINFO)){
				if(resXml != null)
				{
					if(resXml.equals("error"))
					{
					}
					else if(resXml.equals("null")){
					}
					else
					{	
						parseXML.ConserveSampleStatusInfo(resXml, mSampleStatusInfoTBL);
						Message message = new Message();
						message.what = REFRESH_DATA;
						mHandler.sendMessage(message);
					}
				}
			}
		};
	};
	private OnClickItemProcessNode ShowNodeActivity = new OnClickItemProcessNode() {
		
		@Override
		public void ShowNodeContent(View v, int NodeType) {
			// TODO Auto-generated method stub
			
		}
	};
	//��ȡ��ͬ���
	private void updateFindSpecimenList(){
		WebClient client = WebClient.getInstance();
		Map<String,String> param = new HashMap<String, String>();
		param.put(getString(R.string.UserID), mUserInfo.getUserID());
		param.put(getString(R.string.ServiceType), mServiceType);
		client.sendMessage(appContext, WebClient.Method_findSpecimenList, param);
	}
	//��ȡ��ǰʵʱ��Ϣ
	private void updateindSampleStatusInfo(){
		WebClient client = WebClient.getInstance();
		Map<String,String> param = new HashMap<String, String>();
		mCurrentContractInfo = lpContractInfo.get(0);
		param.put(getString(R.string.ContractNo), lpContractInfo.get(0).getContractNo());
		param.put(getString(R.string.ServiceID), lpContractInfo.get(0).getServiceId());
		client.sendMessage(appContext, WebClient.Method_findSampleStatusInfo, param);
	}
}