package com.cellstorage.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import com.cellstorage.AppContext;
import com.cellstorage.UIHealper;
import com.cellstorage.net.WebClient;
import com.cellstorage.struct.ContractInfo;
import com.cellstorage.struct.SampleStatusInfoTBL;
import com.cellstorage.struct.UserInfo;
import com.cellstorage.utils.parseXML;
import com.cellstorage.view.CellProcessNodeView;
import com.cellstorage.view.CellProcessNodeView.OnClickItemProcessNode;
import com.example.cellstorage.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
@EActivity(R.layout.activity_cell_monitor)
public class CellMonitorActivity extends Activity{
	private LinearLayout mLinearLayoutText;
	private AppContext appContext;
	private String mServiceType;
	private UserInfo mUserInfo;
	private SampleStatusInfoTBL mSampleStatusInfoTBL;
	private List<ContractInfo> lpContractInfo = new ArrayList<ContractInfo>();
	@AfterViews
	public void Init(){
		appContext = (AppContext) getApplication();
        //去掉信息栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mUserInfo = UserInfo.getAppManager();
		mSampleStatusInfoTBL = new SampleStatusInfoTBL();
		//获取intent附加值
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			mServiceType = extras.getString(getString(R.string.ServiceType));	
		}else{
		}
		IntentFilter filter = new IntentFilter();
		filter.addAction(WebClient.INTERNAL_ACTION_FINDSPECIMENLIST);
		filter.addAction(WebClient.INTERNAL_ACTION_FINDSAMPLESTAYUSINFO);
		appContext.registerReceiver(receiver, filter);
		updateFindSpecimenList();
		mLinearLayoutText = (LinearLayout) findViewById(R.id.LinearLayoutTest);
		CellProcessNodeView.setmOnClickItemProcessNode(ShowNodeActivity);
		CellProcessNodeView CellNode_1 = new CellProcessNodeView(appContext); 
		CellNode_1.setmNodeStatus(CellProcessNodeView.NODE_STATUS_DONE);
		CellNode_1.setmShowTextStatus(CellProcessNodeView.SHOW_LEFT);
		CellNode_1.setmTextContent("胡威1");
		CellNode_1.create(1);
		CellProcessNodeView CellNode_2 = new CellProcessNodeView(appContext); 
		CellNode_2.setmNodeStatus(CellProcessNodeView.NODE_STATUS_DONE);
		CellNode_2.setmShowTextStatus(CellProcessNodeView.SHOW_RIGHT);
		CellNode_2.setmTextContent("胡威2");
		CellNode_2.create(2);
		CellProcessNodeView CellNode_3 = new CellProcessNodeView(appContext); 
		CellNode_3.setmNodeStatus(CellProcessNodeView.NODE_STATUS_DOING);
		CellNode_3.setmShowTextStatus(CellProcessNodeView.SHOW_LEFT);
		CellNode_3.setmTextContent("胡威3");
		CellNode_3.create(3);
		mLinearLayoutText.addView(CellNode_1);
		mLinearLayoutText.addView(CellNode_2);
		mLinearLayoutText.addView(CellNode_3);
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
	//获取合同编号
	private void updateFindSpecimenList(){
		WebClient client = WebClient.getInstance();
		Map<String,String> param = new HashMap<String, String>();
		param.put(getString(R.string.UserID), mUserInfo.getUserID());
		param.put(getString(R.string.ServiceType), mServiceType);
		client.sendMessage(appContext, WebClient.Method_findSpecimenList, param);
	}
	//获取当前实时信息
	private void updateindSampleStatusInfo(){
		WebClient client = WebClient.getInstance();
		Map<String,String> param = new HashMap<String, String>();
		param.put(getString(R.string.ContractNo), lpContractInfo.get(0).getContractNo());
		param.put(getString(R.string.ServiceID), lpContractInfo.get(0).getServiceId());
		client.sendMessage(appContext, WebClient.Method_findSampleStatusInfo, param);
	}
}