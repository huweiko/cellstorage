/*
 * 文件名：LoadActivity.java
 * 功能：应用程序启动时的加载界面，程序入口
 * 作者：huwei
 * 创建时间：2013-10-22
 * 
 * 
 * 
 * */
package com.cellstorage.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cellstorage.AppContext;
import com.cellstorage.net.WebClient;
import com.cellstorage.struct.SampleStatusDetection;
import com.cellstorage.struct.SampleStatusPreparation;
import com.cellstorage.struct.SampleStatusStorage;
import com.cellstorage.struct.SampleStatusTakeover;
import com.cellstorage.utils.ReportDownloadManager;
import com.cellstorage.utils.parseXML;
import com.cellstorage.view.CellProcessNodeView;
import com.cellstorage.view.PreparationProcessNodeView;
import com.cellstorage.view.TempCurveView;
import com.example.cellstorage.R;

@SuppressLint("SimpleDateFormat") @EActivity(R.layout.activity_sample_process)
public class SampleProcessInfoActivity extends BaseActivity
{
	private AppContext appContext;
	//服务当前状态
	public int mSampleCurrentStatus = 0;
	public int mSampleStatusId = 0;
	private String mServiceId;
	//当前合同号
	private String mCurrentContractNO = "";
	//服务类型
	private String mServiceType = "";
	//合同节点名
	private String mSampleNodeName = "";
	//合同提醒信息
	public List<String> mSampleProcessInfoWare = new ArrayList<String>();
	//合同要求信息
	public List<String> mSampleProcessInfoRequst = new ArrayList<String>();;
	
	private ArrayAdapter<String> mWareAdapter;
	private ArrayAdapter<String> mRequestAdapter;
	
	private final int HANDLE_SAMPLE_TAKEOVER = 0;
	private final int HANDLE_SAMPLE_PREPARATION = 1;
	private final int HANDLE_SAMPLE_DETECTION = 2;
	private final int HANDLE_SAMPLE_STORAGE = 3;
	private final int HANDLE_SAMPLE_PREPARE= 4;
	private final int HANDLE_SAMPLE_COLLECT = 5;
	private final int HANDLE_SAMPLE_TRANSPORT = 6;
	
	private SampleStatusTakeover mSampleStatusTakeover;
	
	/*样本准备参数*************************************************************/
	@ViewById
	LinearLayout LinearLayoutSamplePrepareWare;
	@ViewById
	ListView ListViewSamplePrepareWare;
	@ViewById
	LinearLayout LinearLayoutSamplePrepareRequire;
	@ViewById
	ListView ListViewSamplePrepareRequire;
	
	//*******************************************************************************/
	
	/*样本采集参数*************************************************************/
	@ViewById
	LinearLayout LinearLayoutSampleCollectWare;
	@ViewById
	ListView ListViewSampleCollectWare;
	@ViewById
	LinearLayout LinearLayoutSampleCollectRequest;
	@ViewById
	ListView ListViewSampleCollectRequest;
	
	//*******************************************************************************/
	
	/*样本运输参数*************************************************************/
	@ViewById
	LinearLayout LinearLayoutSampleTransportWare;
	@ViewById
	ListView ListViewSampleTransportWare;
	@ViewById
	LinearLayout LinearLayoutSampleTransportRequest;
	@ViewById
	ListView ListViewSampleTransportRequest;
	
	//*******************************************************************************/
	
	//样本制备参数************************************************************/
	private SampleStatusPreparation mSampleStatusPreparation;
	
	private final String[] mSamplePreparationName = {"","原代培养","换液","传代P1","传代P2","冻存"};
	private final String[] mSamplePreparationDate= new String[6];
	private final String[] mSamplePreparationTime = new String[6];
	private int mSamplePreparationCurrentStatus = 0;
	private String mSamplePreparationCurrentStatusDes = "";
	
	@ViewById
	TextView TextViewPreparationVerdict;
	
	//**********************************************************************/
	
	//样本检测参数************************************************************/
	private SampleStatusDetection mSampleStatusDetection;
	
	@ViewById
	TextView TextViewSampleDetectionXYZJ;
	@ViewById
	TextView TextViewSampleDetectionZYT;
	@ViewById
	TextView TextViewSampleDetectionNMS;
	@ViewById
	TextView TextViewSampleDetectionXBBMKY;
	@ViewById
	TextView TextViewSampleDetectionVerdict;
	private ReportDownloadManager mReportDownloadManager;
	@SuppressLint("SetJavaScriptEnabled") @Click
	void LinearLayoutSampleCheckReport(){
		mReportDownloadManager = new ReportDownloadManager(this, mServiceId,mCurrentContractNO);
		mReportDownloadManager.getCheckReport();
		
	}
	//**********************************************************************/
	
	//样本入库参数************************************************************/
	private SampleStatusStorage mSampleStatusStorage;
	
	private String mSampleStorageFridgeName;
	@ViewById
	TextView TextViewSampleStorageStorageTime;
	@ViewById
	TextView TextViewSampleStorageLivingcellRate;
	@ViewById
	TextView TextViewSampleStorageLivingcellNum;
	@ViewById
	TextView TextViewSampleStorageStorageTemp;
	@ViewById
	TextView TextViewSampleStorageVerdict;
	@ViewById
	LinearLayout LinearLayoutSampleStorageCurve;
	
	TempCurveView mTempCurveViewnew;
	
	//**********************************************************************/
	
	
	@ViewById(R.id.ImageButtonSampleProcessInfoBack)
	ImageButton mImageButtonSampleProcessInfoBack;
	
	@ViewById
	View sample_view_sample_takeover;
	
	@ViewById
	View sample_view_sample_preparation;
	
	@ViewById
	View sample_view_sample_prepare;
	
	@ViewById
	View sample_view_sample_collect;
	
	@ViewById
	View sample_view_sample_transport;
	
	@ViewById
	View sample_view_sample_storage;
	
	@ViewById
	View sample_view_sample_check;
	
	@ViewById
	TextView TextViewSampleProcessName;
	
	@ViewById
	LinearLayout LinearLayoutPreparationProcess;
	
	
	/*样本交接View内控件*******************************************************/

	@ViewById
	TextView TextViewTakeoverTemp;
	
	@ViewById
	TextView TextViewTakeoverCapacity;
	
	@ViewById
	TextView TextViewTakeoverPackStatus;
	
	@ViewById
	TextView TextViewTakeoverVerdict;
	
	
	@Click(R.id.ImageButtonSampleProcessInfoBack)
	void OnClickImageButtonAboutUsBack(){
		finish();
	}
	
	@AfterViews
	void Init(){
		appContext = (AppContext) getApplication();
        //去掉信息栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//获取intent附加值
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			mSampleCurrentStatus = extras.getInt(getString(R.string.SampleStatusType));	
			mSampleStatusId = extras.getInt(getString(R.string.SampleStatusId));	
			mServiceId = extras.getString(getString(R.string.ServiceID));	
			mCurrentContractNO = extras.getString(getString(R.string.ContractNo));	
			mServiceType = extras.getString(getString(R.string.ServiceType));	
		}
		mWareAdapter = new ArrayAdapter<String>(appContext, R.layout.listitem_sample_proccess_request);
		mRequestAdapter = new ArrayAdapter<String>(appContext, R.layout.listitem_sample_proccess_request);
		
		mSampleNodeName = CellMonitorActivity.mSampleNodeName[mSampleCurrentStatus];
		showView(mSampleCurrentStatus);
		TextViewSampleProcessName.setText(mSampleNodeName);
		IntentFilter filter = new IntentFilter();
		filter.addAction(WebClient.INTERNAL_ACTION_GETSAMPLETRANSVER);
		filter.addAction(WebClient.INTERNAL_ACTION_GETSAMPLEPREPARATION);
		filter.addAction(WebClient.INTERNAL_ACTION_GETSAMPLESTORAGE);
		filter.addAction(WebClient.INTERNAL_ACTION_GETSAMPLEDETECTION);
		filter.addAction(WebClient.INTERNAL_ACTION_GETPROCESSINFO);
		appContext.registerReceiver(receiver, filter);
	}
	//判断显示哪一个流程
	private void showView(int x_SampleCurrentStatus){
		if(x_SampleCurrentStatus == 0){
			
		}
		else if(x_SampleCurrentStatus == 1){
		}
		else if(x_SampleCurrentStatus == 2){
		}
		else if(x_SampleCurrentStatus == 3){
			sample_view_sample_prepare.setVisibility(View.VISIBLE);
			getServiceProcessInfo(mSampleNodeName, mServiceType);
		}
		else if(x_SampleCurrentStatus == 4){
			sample_view_sample_collect.setVisibility(View.VISIBLE);
			getServiceProcessInfo(mSampleNodeName, mServiceType);
		}
		else if(x_SampleCurrentStatus == 5){
			sample_view_sample_transport.setVisibility(View.VISIBLE);
			getServiceProcessInfo(mSampleNodeName, mServiceType);
			
		}
		else if(x_SampleCurrentStatus == 6){
			sample_view_sample_takeover.setVisibility(View.VISIBLE);
			updateFindSpecimenList(WebClient.Method_getSampleTransfer);
		}
		else if(x_SampleCurrentStatus == 7){
			updateFindSpecimenList(WebClient.Method_getSamplePreparation);
			sample_view_sample_preparation.setVisibility(View.VISIBLE);

		}
		else if(x_SampleCurrentStatus == 8){
			updateFindSpecimenList(WebClient.Method_getSampleDetection);
			sample_view_sample_check.setVisibility(View.VISIBLE);
		}
		else if(x_SampleCurrentStatus == 9){
			updateFindSpecimenList(WebClient.Method_getSampleStorage);
			sample_view_sample_storage.setVisibility(View.VISIBLE);
		}
	}
	@SuppressLint("SimpleDateFormat") 
	private void getSamplePreparationTime(){
		SimpleDateFormat mYMDFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm");
		if(mSampleStatusPreparation.getPrimaryTime() != null){
			mSamplePreparationDate[1] = mYMDFormat.format(mSampleStatusPreparation.getPrimaryTime());
			mSamplePreparationTime[1] = mTimeFormat.format(mSampleStatusPreparation.getPrimaryTime());
		}
		if(mSampleStatusPreparation.getPass0Time() != null){
			mSamplePreparationDate[2] = mYMDFormat.format(mSampleStatusPreparation.getPass0Time());
			mSamplePreparationTime[2] = mTimeFormat.format(mSampleStatusPreparation.getPass0Time());
		}
		if(mSampleStatusPreparation.getPass1Time() != null){
			mSamplePreparationDate[3] = mYMDFormat.format(mSampleStatusPreparation.getPass1Time());
			mSamplePreparationTime[3] = mTimeFormat.format(mSampleStatusPreparation.getPass1Time());
		}
		if(mSampleStatusPreparation.getPass2Time() != null){
			mSamplePreparationDate[4] = mYMDFormat.format(mSampleStatusPreparation.getPass2Time());
			mSamplePreparationTime[4] = mTimeFormat.format(mSampleStatusPreparation.getPass2Time());
		}
		if(mSampleStatusPreparation.getFreezonTime()!= null){
			mSamplePreparationDate[5] = mYMDFormat.format(mSampleStatusPreparation.getFreezonTime());
			mSamplePreparationTime[5] = mTimeFormat.format(mSampleStatusPreparation.getFreezonTime());
		}
	}
	private int getSamplePreparationCurrentStatus(){
		int l_SamplePreparationStatus = 0;
		if(mSampleStatusPreparation.getFreezonTime() != null){
			l_SamplePreparationStatus = 5;
			mSamplePreparationCurrentStatusDes = mSampleStatusPreparation.getFreezonStatus();
		}
		else if(mSampleStatusPreparation.getPass2Time() != null){
			l_SamplePreparationStatus = 4;
			mSamplePreparationCurrentStatusDes = mSampleStatusPreparation.getPass2Status();
		}
		else if(mSampleStatusPreparation.getPass1Time() != null){
			l_SamplePreparationStatus = 3;
			mSamplePreparationCurrentStatusDes = mSampleStatusPreparation.getPass1Status();
		}
		else if(mSampleStatusPreparation.getPass0Time() != null){
			l_SamplePreparationStatus = 2;
			mSamplePreparationCurrentStatusDes = mSampleStatusPreparation.getPass0Status();
		}
		else if(mSampleStatusPreparation.getPrimaryTime() != null){
			l_SamplePreparationStatus = 1;
			mSamplePreparationCurrentStatusDes = mSampleStatusPreparation.getPrimaryStatus();
		}
		else{
			l_SamplePreparationStatus = 0;
		}
		return l_SamplePreparationStatus;
	}
	@SuppressLint("HandlerLeak") 
	private Handler mHandler=new Handler()  
	{  
		@SuppressLint("SetJavaScriptEnabled") public void handleMessage(Message msg)  
		{  
			switch(msg.what)  
			{ 
			case HANDLE_SAMPLE_PREPARE:{
				mWareAdapter.clear();
				mRequestAdapter.clear();
				int WareTotalHeight = 0; 
				int RequestTotalHeight = 0; 
				if(mSampleProcessInfoWare.size()>0){
					LinearLayoutSamplePrepareWare.setVisibility(View.VISIBLE);
					for(int i = 0;i < mSampleProcessInfoWare.size();i++){
						mWareAdapter.add(mSampleProcessInfoWare.get(i));
						View listItem = mWareAdapter.getView(i, null, ListViewSamplePrepareWare);
						listItem.measure(0, 0);
						WareTotalHeight += listItem.getMeasuredHeight();
					}
					ViewGroup.LayoutParams params = ListViewSamplePrepareWare.getLayoutParams();
					params.height = WareTotalHeight + (ListViewSamplePrepareWare.getDividerHeight() * (mWareAdapter.getCount() - 1)); 
					ListViewSamplePrepareWare.setLayoutParams(params);
					ListViewSamplePrepareWare.setAdapter(mWareAdapter);
				}
				if(mSampleProcessInfoRequst.size()>0){
					LinearLayoutSamplePrepareRequire.setVisibility(View.VISIBLE);
					for(int i = 0;i < mSampleProcessInfoRequst.size();i++){
						mRequestAdapter.add(mSampleProcessInfoRequst.get(i));
						View listItem = mRequestAdapter.getView(i, null, ListViewSamplePrepareRequire);
						listItem.measure(0, 0);
						RequestTotalHeight += listItem.getMeasuredHeight();
					}
					ViewGroup.LayoutParams params = ListViewSamplePrepareRequire.getLayoutParams();
					params.height = RequestTotalHeight + (ListViewSamplePrepareRequire.getDividerHeight() * (mRequestAdapter.getCount() - 1)); 
					ListViewSamplePrepareRequire.setLayoutParams(params);
					ListViewSamplePrepareRequire.setAdapter(mRequestAdapter);
				}
				long endTime=System.currentTimeMillis(); //获取结束时间
				Log.d("","handle时的时间为："+(endTime-startTime)+"ms");
			}break;
			case HANDLE_SAMPLE_COLLECT:{
				mWareAdapter.clear();
				mRequestAdapter.clear();
				int WareTotalHeight = 0; 
				int RequestTotalHeight = 0; 
				if(mSampleProcessInfoWare.size()>0){
					LinearLayoutSampleCollectWare.setVisibility(View.VISIBLE);
					for(int i = 0;i < mSampleProcessInfoWare.size();i++){
						mWareAdapter.add(mSampleProcessInfoWare.get(i));
						View listItem = mWareAdapter.getView(i, null, ListViewSampleCollectWare);
						listItem.measure(0, 0);
						WareTotalHeight += listItem.getMeasuredHeight();
					}
					ViewGroup.LayoutParams params = ListViewSampleCollectWare.getLayoutParams();
					params.height = WareTotalHeight + (ListViewSampleCollectWare.getDividerHeight() * (mWareAdapter.getCount() - 1)); 
					ListViewSampleCollectWare.setLayoutParams(params);
					ListViewSampleCollectWare.setAdapter(mWareAdapter);
				}
				if(mSampleProcessInfoRequst.size()>0){
					LinearLayoutSampleCollectRequest.setVisibility(View.VISIBLE);
					for(int i = 0;i < mSampleProcessInfoRequst.size();i++){
						mRequestAdapter.add(mSampleProcessInfoRequst.get(i));
						View listItem = mRequestAdapter.getView(i, null, ListViewSampleCollectRequest);
						listItem.measure(0, 0);
						RequestTotalHeight += listItem.getMeasuredHeight();
					}
					ViewGroup.LayoutParams params = ListViewSampleCollectRequest.getLayoutParams();
					params.height = RequestTotalHeight + (ListViewSampleCollectRequest.getDividerHeight() * (mRequestAdapter.getCount() - 1)); 
					ListViewSampleCollectRequest.setLayoutParams(params);
					ListViewSampleCollectRequest.setAdapter(mRequestAdapter);
				}
				long endTime=System.currentTimeMillis(); //获取结束时间
				Log.d("","handle时的时间为："+(endTime-startTime)+"ms");
			}break;
			case HANDLE_SAMPLE_TRANSPORT:{
				mWareAdapter.clear();
				mRequestAdapter.clear();
				int WareTotalHeight = 0; 
				int RequestTotalHeight = 0; 
				if(mSampleProcessInfoWare.size()>0){
					LinearLayoutSampleTransportWare.setVisibility(View.VISIBLE);
					for(int i = 0;i < mSampleProcessInfoWare.size();i++){
						mWareAdapter.add(mSampleProcessInfoWare.get(i));
						View listItem = mWareAdapter.getView(i, null, ListViewSampleTransportWare);
						listItem.measure(0, 0);
						WareTotalHeight += listItem.getMeasuredHeight();
					}
					ViewGroup.LayoutParams params = ListViewSampleTransportWare.getLayoutParams();
					params.height = WareTotalHeight + (ListViewSampleTransportWare.getDividerHeight() * (mWareAdapter.getCount() - 1)); 
					ListViewSampleTransportWare.setLayoutParams(params);
					ListViewSampleTransportWare.setAdapter(mWareAdapter);
				}
				if(mSampleProcessInfoRequst.size()>0){
					LinearLayoutSampleTransportRequest.setVisibility(View.VISIBLE);
					for(int i = 0;i < mSampleProcessInfoRequst.size();i++){
						mRequestAdapter.add(mSampleProcessInfoRequst.get(i));
						View listItem = mRequestAdapter.getView(i, null, ListViewSampleTransportRequest);
						listItem.measure(0, 0);
						RequestTotalHeight += listItem.getMeasuredHeight();
					}
					ViewGroup.LayoutParams params = ListViewSampleTransportRequest.getLayoutParams();
					params.height = RequestTotalHeight + (ListViewSampleTransportRequest.getDividerHeight() * (mRequestAdapter.getCount() - 1)); 
					ListViewSampleTransportRequest.setLayoutParams(params);
					ListViewSampleTransportRequest.setAdapter(mRequestAdapter);
				}	
				long endTime=System.currentTimeMillis(); //获取结束时间
				Log.d("","handle时的时间为："+(endTime-startTime)+"ms");
			}break;
			case HANDLE_SAMPLE_TAKEOVER:{
				TextViewTakeoverTemp.setText(mSampleStatusTakeover.getTakeoverSurTemp());
				TextViewTakeoverCapacity.setText(mSampleStatusTakeover.getSampleQuantity());
				TextViewTakeoverPackStatus.setText(mSampleStatusTakeover.getWrapStatus());
				TextViewTakeoverVerdict.setText(mSampleStatusTakeover.getTakeoverVerdict());
				long endTime=System.currentTimeMillis(); //获取结束时间
				Log.d("","handle时的时间为："+(endTime-startTime)+"ms");
			}break;
			case HANDLE_SAMPLE_PREPARATION:{
				mSamplePreparationCurrentStatus = getSamplePreparationCurrentStatus();
				getSamplePreparationTime();
				LinearLayoutPreparationProcess.removeAllViews();
				for(int i = 1;i <= 5;i++){
					PreparationProcessNodeView PreparationNode = new PreparationProcessNodeView(appContext); 

					
					PreparationNode.setmStringPreparationDate(mSamplePreparationDate[i]);
					PreparationNode.setmStringPreparationTime(mSamplePreparationTime[i]);
					PreparationNode.setmStringPreparationName(mSamplePreparationName[i]);
					if(i < mSamplePreparationCurrentStatus){
						PreparationNode.setmNodeStatus(CellProcessNodeView.NODE_STATUS_DONE);
					}else if(i == mSamplePreparationCurrentStatus){
						PreparationNode.setmNodeStatus(CellProcessNodeView.NODE_STATUS_DOING);
					}
					else{
						PreparationNode.setmNodeStatus(CellProcessNodeView.NODE_STATUS_WILLDO);
					}
					

					PreparationNode.create(i);
					if((i+1)%2 == 0){
						PreparationNode.setBackgroundColor(getResources().getColor(R.color.white));
					}else{
						PreparationNode.setBackgroundColor(getResources().getColor(R.color.theme_gray));
					}
					LinearLayoutPreparationProcess.addView(PreparationNode);
				}
				TextViewPreparationVerdict.setText(mSamplePreparationCurrentStatusDes);
				long endTime=System.currentTimeMillis(); //获取结束时间
				Log.d("","handle时的时间为："+(endTime-startTime)+"ms");
			}break;
			case HANDLE_SAMPLE_DETECTION:{
				long endTime=System.currentTimeMillis(); //获取结束时间
				Log.d("","handle时的时间为："+(endTime-startTime)+"ms");
				TextViewSampleDetectionXYZJ.setText(mSampleStatusDetection.getExamXyzj());
				TextViewSampleDetectionZYT.setText(mSampleStatusDetection.getExamZyt());
				TextViewSampleDetectionNMS.setText(mSampleStatusDetection.getExamNDS());
				TextViewSampleDetectionXBBMKY.setText(mSampleStatusDetection.getExamXbbmky());
				TextViewSampleDetectionVerdict.setText(mSampleStatusDetection.getExamVerdict());
			}break;
			case HANDLE_SAMPLE_STORAGE:{
				long endTime=System.currentTimeMillis(); //获取结束时间
				Log.d("","handle时的时间为："+(endTime-startTime)+"ms");
				mTempCurveViewnew  = new TempCurveView(appContext,mSampleStorageFridgeName);
				LinearLayoutSampleStorageCurve.removeAllViews();
				LinearLayoutSampleStorageCurve.addView(mTempCurveViewnew.getView());
				
				SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				if(mSampleStatusStorage.getStorageTime() != null){
					TextViewSampleStorageStorageTime.setText(mFormat.format(mSampleStatusStorage.getStorageTime()));
				}
				TextViewSampleStorageLivingcellRate.setText(mSampleStatusStorage.getLivingcellRate());
				TextViewSampleStorageLivingcellNum.setText(mSampleStatusStorage.getLivingcellNum());
				TextViewSampleStorageStorageTemp.setText(mSampleStatusStorage.getStorageTemp());
				TextViewSampleStorageVerdict.setText(mSampleStatusStorage.getStorageVerdict());
			}break;
			default:  
				break;            
			}  
			super.handleMessage(msg);  
		}  
	}; 
	
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			long endTime=System.currentTimeMillis(); //获取结束时间
			Log.d("","receiver时的时间为："+(endTime-startTime)+"ms");
			String resXml = intent.getStringExtra(WebClient.Param_resXml);
			
			if(intent.getAction().equals(WebClient.INTERNAL_ACTION_GETSAMPLETRANSVER))
			{
				stopProgressDialog();
				if(resXml.equals("error"))
				{
					
				}
				else if(resXml.equals("null")){
				}
				else{
					mSampleStatusTakeover = new SampleStatusTakeover();
					parseXML.ConserveSampleTakeoverInfo(resXml, mSampleStatusTakeover);
					Message message = new Message();
					message.what = HANDLE_SAMPLE_TAKEOVER;
					mHandler.sendMessage(message);
				}
			}
			else if(intent.getAction().equals(WebClient.INTERNAL_ACTION_GETSAMPLESTORAGE)){
				stopProgressDialog();
				if(resXml.equals("error"))
				{
					
				}
				else if(resXml.equals("null")){
				}
				else{
					mSampleStatusStorage = new SampleStatusStorage();
					parseXML.ConserveSampleStorageInfo(resXml, mSampleStatusStorage);
					if(!mSampleStatusStorage.getFridgeName().equals("")){
						mSampleStorageFridgeName = mSampleStatusStorage.getFridgeName();
					}
					Message message = new Message();
					message.what = HANDLE_SAMPLE_STORAGE;
					mHandler.sendMessage(message);
				}
			}
			else if(intent.getAction().equals(WebClient.INTERNAL_ACTION_GETSAMPLEPREPARATION)){
				stopProgressDialog();
				if(resXml.equals("error"))
				{
					
				}
				else if(resXml.equals("null")){
				}
				else{
					mSampleStatusPreparation = new SampleStatusPreparation();
					parseXML.ConserveSamplePreparationInfo(resXml, mSampleStatusPreparation);
					Message message = new Message();
					message.what = HANDLE_SAMPLE_PREPARATION;
					mHandler.sendMessage(message);
				}
			}
			else if(intent.getAction().equals(WebClient.INTERNAL_ACTION_GETSAMPLEDETECTION)){
				stopProgressDialog();
				if(resXml.equals("error"))
				{
					
				}
				else if(resXml.equals("null")){
				}
				else{
					mSampleStatusDetection = new SampleStatusDetection();
					parseXML.ConserveSampleDetectionInfo(resXml, mSampleStatusDetection);
					Message message = new Message();
					message.what = HANDLE_SAMPLE_DETECTION;
					mHandler.sendMessage(message);
				}
			}
			else if(intent.getAction().equals(WebClient.INTERNAL_ACTION_GETPROCESSINFO)){
				stopProgressDialog();
				if(resXml.equals("error")){
				}
				else if(resXml.equals("null")){
				}
				else{
					mSampleStatusDetection = new SampleStatusDetection();
					mSampleProcessInfoWare.clear();
					mSampleProcessInfoRequst.clear();
					parseXML.ConserveSampleProcessInfo(resXml, mSampleProcessInfoWare, mSampleProcessInfoRequst);;
					Message message = new Message();
					if(mSampleCurrentStatus == 3){
						message.what = HANDLE_SAMPLE_PREPARE;
						mHandler.sendMessage(message);
					}
					else if(mSampleCurrentStatus == 4){
						message.what = HANDLE_SAMPLE_COLLECT;
						mHandler.sendMessage(message);
					}
					else if(mSampleCurrentStatus == 5){
						message.what = HANDLE_SAMPLE_TRANSPORT;
						mHandler.sendMessage(message);
					}
				}
			}
			
		};
	};
	protected void onDestroy() 
	{
		appContext.unregisterReceiver(receiver);
		if(mTempCurveViewnew != null){
			mTempCurveViewnew.Destroy();
		}
		super.onDestroy();
	}
	
	//获取合同编号
	private void updateFindSpecimenList(String method){
		startTime = System.currentTimeMillis();
		startProgressDialog("正在加载...");
		WebClient client = WebClient.getInstance();
		Map<String,String> param = new HashMap<String, String>();
		param.put(getString(R.string.SampleStatusId), ""+mSampleStatusId);
		client.sendMessage(appContext, method, param);
	}
	long startTime=0; //获取开始时间
	//获取服务流程信息（提醒和要求）
	private void getServiceProcessInfo(String processType,String serviceType){
		startTime = System.currentTimeMillis();
		startProgressDialog("正在加载...");
		WebClient client = WebClient.getInstance();
		Map<String,String> param = new HashMap<String, String>();
		param.put(getString(R.string.ProcessType), processType);
		param.put(getString(R.string.ServiceType), serviceType);
		client.sendMessage(appContext, WebClient.Method_getProcessInfo, param);
	}

/*	@Override
	public void OnItemClick(String fileName) {
		// TODO Auto-generated method stub
		Message message = new Message();
		message.what = HANDLE_OPEN_SAMPLE_REPORT;
		message.obj = fileName;
		mHandler.sendMessage(message);

	}
*/}