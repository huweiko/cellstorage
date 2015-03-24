package com.cellstorage.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.cellstorage.struct.SampleStatusInfo;
import com.cellstorage.struct.UserInfo;
import com.cellstorage.utils.parseXML;
import com.cellstorage.view.CellProcessNodeView;
import com.cellstorage.view.CellProcessNodeView.OnClickItemProcessNode;
import com.example.cellstorage.R;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
@SuppressLint("SimpleDateFormat") @EActivity(R.layout.activity_cell_monitor)
public class CellMonitorActivity extends BaseActivity{
	private LinearLayout mLinearLayoutText;
	private AppContext appContext;
	private String mServiceType;
	private UserInfo mUserInfo;
	private SampleStatusInfo mSampleStatusInfo;
	private List<ContractInfo> lpContractInfo = new ArrayList<ContractInfo>();
	private ContractInfo mCurrentContractInfo;
	private SimpleDateFormat mYearFormat = new SimpleDateFormat("yyyy");
	private SimpleDateFormat mMonthFormat = new SimpleDateFormat("MM");
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("dd");
	private SimpleDateFormat mHourFormat = new SimpleDateFormat("hh");
	private SimpleDateFormat mMinutesFormat = new SimpleDateFormat("mm");
	private final String[] week= {"������", "����һ", "���ڶ�", "������", "������", "������", "������"};
	public static final String[] mSampleNodeName = {"","��ѯ��ԤԼ","ǩ����ͬ","�ɼ�׼��","�����ɼ�","��������","��������","�����Ʊ�","��ⱨ��","�������"};
	private final String[] mSampleNodeTime = new String[10];
	private ArrayAdapter<String> adapter;
	//��ǰ��ͬ��
	private int mCurrentContractNO = 0;
	//����ǰ״̬
	public int mSampleCurrentStatus = 0;
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
	
	//ˢ�½�����
	@ViewById(R.id.ProgressBarMonitorRefresh)
	public ProgressBar mProgressBarMonitorRefresh;
	
	//��ͬ���
	@ViewById(R.id.SpinnerMonitorContractNum)
	public Spinner mSpinnerMonitorContractNum;
	
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
	
	//��ǰ��ͬ����
	@ViewById(R.id.TextViewSampleProcess)
	public TextView mTextViewSampleProcess;
	
	private final int REFRESH_DATA = 1;
	private void getSampleTime(){
		Date []today = new Date[10];
		String l_Year = null;
		String l_Month = null;
		String l_getDate = null;
		String l_Hours = null;
		String l_Minutes = null;
		if(mSampleStatusInfo.getStorageTime() != null){
			today[9] = mSampleStatusInfo.getStorageTime();
		}
		if(mSampleStatusInfo.getReportTime() != null){
			today[8] = mSampleStatusInfo.getReportTime();
		}
		if(mSampleStatusInfo.getPrimaryTime() != null){
			today[7] = mSampleStatusInfo.getPrimaryTime();
		}
		if(mSampleStatusInfo.getTakeoverTime() != null){
			today[6] = mSampleStatusInfo.getTakeoverTime();
		}
		if(mSampleStatusInfo.getStartTransTime() != null){
			today[5] = mSampleStatusInfo.getStartTransTime();
		}
		if(mSampleStatusInfo.getCollectTime() != null){
			today[4] = mSampleStatusInfo.getCollectTime();
		}
		if(mSampleStatusInfo.getPrepareTime() != null){
			today[3] = mSampleStatusInfo.getPrepareTime();
		}
		if(mSampleStatusInfo.getSignedTime() != null){
			today[2] = mSampleStatusInfo.getSignedTime();
		}
		if(mSampleStatusInfo.getApplyTime() != null){
			today[1] = mSampleStatusInfo.getApplyTime();
		}
		for(int i = 9;i > 0;i-- ){
			if(today[i] != null){
				l_Year = mYearFormat.format(today[i]);
				l_Month = mMonthFormat.format(today[i]);
				l_getDate = mDateFormat.format(today[i]);
				l_Hours = mHourFormat.format(today[i]);
				l_Minutes = mMinutesFormat.format(today[i]);
				mSampleNodeTime[i] = l_Year+"�� "+l_Month+"�� "+l_getDate+"�� "+l_Hours+"ʱ "+l_Minutes+"��";
			}

		}
	}
	private int getSampleCurrentStatus(){
		if(mSampleStatusInfo != null){
			for(int i = 9;i > 0;i--){
				if(mSampleStatusInfo.getSampleProcess().equals(mSampleNodeName[i])){
					return i;
				}
			}	
		}

		return 0;
	}
	@SuppressLint("SimpleDateFormat") 
	private void updateData(){
		//���ú�ͬ��
		SimpleDateFormat updateTimeFormat = new SimpleDateFormat("hh:mm:ss");
		Date l_updateTime = new Date();
		//���ø���ʱ��
		mTextViewMonitorUpdateTime.setText(updateTimeFormat.format(l_updateTime)+"����");

		mTextViewMonitorAddress.setText(mServiceType);
		mTextViewMonitorQCP.setText(mSampleStatusInfo.getQualityCL());
		mTextViewMonitorSampleStatus.setText(mSampleStatusInfo.getQualityCLStatus());
		mTextViewMonitorControlRange.setText(mSampleStatusInfo.getQualityCLRange());
		mTextViewSampleProcess.setText(mSampleStatusInfo.getSampleProcess());
		getSampleTime();
		int l_ProccessCurrentstatus = getSampleCurrentStatus();
		CellProcessNodeView.LoopNodeCount = 0;
		mLinearLayoutText.removeAllViews();
		for(int i = 1;i <= 9;i++){
			CellProcessNodeView CellNode = new CellProcessNodeView(appContext); 
			
			if(i <= l_ProccessCurrentstatus){
				CellNode.setmNodeStatus(CellProcessNodeView.NODE_STATUS_DONE);
			}else{
				CellNode.setmNodeStatus(CellProcessNodeView.NODE_STATUS_WILLDO);
			}
			if((i+1)%2 == 0){
				CellNode.setmShowContentStatus(CellProcessNodeView.SHOW_LEFT);
			}else{
				CellNode.setmShowContentStatus(CellProcessNodeView.SHOW_RIGHT);
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
		String l_Month = mMonthFormat.format(today);
		String l_getDate = mDateFormat.format(today);
		String l_Day = week[today.getDay()];
		String l_Hours = mHourFormat.format(today);
		String l_Minutes = mMinutesFormat.format(today);
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
		mSampleStatusInfo = new SampleStatusInfo();
		//��ȡintent����ֵ
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			mServiceType = extras.getString(getString(R.string.ServiceType));	
		}else{
		}
/*		new Thread(){
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
		}.start();*/
		adapter = new ArrayAdapter<String>(this, R.layout.spinner_checked_text);
        //���������б�ķ��  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        //��adapter2 ��ӵ�spinner�� 
        mSpinnerMonitorContractNum.setAdapter(adapter); 
        //����¼�Spinner�¼�����   
        mSpinnerMonitorContractNum.setOnItemSelectedListener(new SpinnerXMLSelectedListener()); 
        //����Ĭ��ֵ 
        mSpinnerMonitorContractNum.setVisibility(View.VISIBLE);
		
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
		this.finish();
	}
	@Click
	public void ImageButtonMonitorFresh(){
		if(lpContractInfo.size() > 0){
			updateindSampleStatusInfo(mCurrentContractNO);	
		} 
		
	}
    //ʹ��XML��ʽ���� 
    class SpinnerXMLSelectedListener implements OnItemSelectedListener{ 
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, 
                long arg3) {
        	mCurrentContractNO = arg2;
        	updateindSampleStatusInfo(mCurrentContractNO);
        } 

        public void onNothingSelected(AdapterView<?> arg0) { 

        } 
    } 
	public BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			String resXml = intent.getStringExtra(WebClient.Param_resXml);
			
			if(intent.getAction().equals(WebClient.INTERNAL_ACTION_FINDSPECIMENLIST))
			{

				if(resXml != null)
				{
					stopProgressDialog();
					if(resXml.equals("error"))
					{
						UIHealper.DisplayToast(appContext,"��ȡ����ʧ�ܣ�");
					}
					else if(resXml.equals("null")){
						UIHealper.DisplayToast(appContext,"��ȡ����ʧ�ܣ�");
					}
					else
					{  

						lpContractInfo.clear();
						parseXML.ConserveContractInfo(resXml, lpContractInfo);
						if(!lpContractInfo.isEmpty()){
							adapter.clear();
							for(int i = 0;i < lpContractInfo.size();i++){
								adapter.add(lpContractInfo.get(i).getContractNo());
							}

							updateindSampleStatusInfo(0);
						}
					}
				}
			}
			else if(intent.getAction().equals(WebClient.INTERNAL_ACTION_FINDSAMPLESTAYUSINFO)){
				if(resXml != null)
				{
					stopProgressDialog();
					if(resXml.equals("error"))
					{
						
					}
					else if(resXml.equals("null")){
					}
					else
					{	
						parseXML.ConserveSampleStatusInfo(resXml, mSampleStatusInfo);
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
			if(NodeType > 2){
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra(getString(R.string.SampleStatusType), NodeType);
				intent.putExtra(getString(R.string.SampleStatusId), mSampleStatusInfo.getSampleStatusId());
				intent.putExtra(getString(R.string.ServiceID), mCurrentContractInfo.getServiceId());
				intent.putExtra(getString(R.string.ContractNo), mCurrentContractInfo.getContractNo());
				intent.putExtra(getString(R.string.ServiceType), mServiceType);
				intent.setClass(appContext, SampleProcessInfoActivity_.class);
				startActivity(intent);
			}
		}
	};
	//��ȡ��ͬ���
	private void updateFindSpecimenList(){
		startProgressDialog("���ڼ���...");
		WebClient client = WebClient.getInstance();
		Map<String,String> param = new HashMap<String, String>();
		param.put(getString(R.string.UserID), mUserInfo.getUserID());
		param.put(getString(R.string.ServiceType), mServiceType);
		client.sendMessage(appContext, WebClient.Method_findSpecimenList, param);
	}
	//��ȡ��ǰʵʱ��Ϣ
	private void updateindSampleStatusInfo(int arg){
		startProgressDialog("���ڼ���...");
		WebClient client = WebClient.getInstance();
		Map<String,String> param = new HashMap<String, String>();
		mCurrentContractInfo = lpContractInfo.get(arg);
		param.put(getString(R.string.ContractNo), mCurrentContractInfo.getContractNo());
		param.put(getString(R.string.ServiceID), mCurrentContractInfo.getServiceId());
		client.sendMessage(appContext, WebClient.Method_findSampleStatusInfo, param);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		appContext.unregisterReceiver(receiver);
		super.onDestroy();
		
	}
}