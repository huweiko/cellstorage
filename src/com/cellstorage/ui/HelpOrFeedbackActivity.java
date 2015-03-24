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
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.cellstorage.AppContext;
import com.cellstorage.UIHealper;
import com.cellstorage.adapter.HelpListViewAdapter;
import com.cellstorage.adapter.HelpListViewAdapter.OnItemClickClass;
import com.cellstorage.net.WebClient;
import com.cellstorage.struct.UserHelpContent;
import com.cellstorage.struct.UserInfo;
import com.example.cellstorage.R;




@EActivity(R.layout.activity_help_feedback)
public class HelpOrFeedbackActivity extends BaseActivity implements OnItemClickClass 
{
	private AppContext appContext;
	private UserInfo mUserInfo;
	private HelpListViewAdapter mHelpListViewAdapter;
	private List<UserHelpContent> mUserHelpContent = new ArrayList<UserHelpContent>() ;
	@ViewById
	EditText EditTextFeedbackContentText;
	
	@ViewById
	ListView ListViewHelp;
	
	
	@Click(R.id.ButtonFeedbackSubmit)
	void ClickButtonFeedbackSubmit(){
		String FeedbackContent = EditTextFeedbackContentText.getText().toString();
		if(FeedbackContent.equals("")){
			UIHealper.DisplayToast(appContext, getString(R.string.editview_null_hint));
		}else{
			WebClient client = WebClient.getInstance();
			Map<String,String> param = new HashMap<String, String>();
			param.put(getString(R.string.UserID), mUserInfo.getUserID());
			param.put(getString(R.string.TextContent), FeedbackContent);
			client.sendMessage(appContext, WebClient.Method_saveFeedback, param);
		}

	}
	
	@Click(R.id.ImageButtonHelpOrFeedbackBack)
	void ClickImageButtonHelpOrFeedbackBack(){
		finish();
	}
	
	@SuppressLint("HandlerLeak") 
	public Handler mHandler=new Handler()  
	{  
		public void handleMessage(Message msg)  
		{  
			switch(msg.what)  
			{  
			default:  
				break;            
			}  
			super.handleMessage(msg);  
		}  
	}; 
	public BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			String resXml = intent.getStringExtra(WebClient.Param_resXml);
			
			if(intent.getAction().equals(WebClient.INTERNAL_ACTION_SAVEFEEDBACK))
			{
				if(resXml.equals("error"))
				{
					UIHealper.DisplayToast(appContext,"提交失败");
				}
				else if(resXml.equals("null")){
					UIHealper.DisplayToast(appContext,"提交失败");
				}else{
					UIHealper.DisplayToast(appContext,"提交成功");
				}
			}
		};
	};
	@AfterViews
	void Init(){
		appContext = (AppContext) getApplication();
		mUserInfo = UserInfo.getAppManager();
		String [] HelpNameItem = getResources().getStringArray(R.array.help_list_name);
		String [] HelpContentItem = getResources().getStringArray(R.array.help_list_content);
		int length = HelpNameItem.length;
		for(int i = 0;i < length;i++){
			UserHelpContent l_UserHelpConten = new UserHelpContent(HelpNameItem[i], HelpContentItem[i], false);
			mUserHelpContent.add(l_UserHelpConten);
		}
		mHelpListViewAdapter = new HelpListViewAdapter(appContext, mUserHelpContent, R.layout.listitem_help);
		ListViewHelp.setAdapter(mHelpListViewAdapter);
		mHelpListViewAdapter.setOnItemClickClassListenr(this);
		mHelpListViewAdapter.notifyDataSetChanged();
		IntentFilter filter = new IntentFilter();
		filter.addAction(WebClient.INTERNAL_ACTION_SAVEFEEDBACK);
		appContext.registerReceiver(receiver, filter);
	}
	protected void onDestroy() 
	{
		appContext.unregisterReceiver(receiver);
		super.onDestroy();
	}

	@Override
	public void OnItemClick(View v, int Position) {
		// TODO Auto-generated method stub
		for(int i = 0;i < mUserHelpContent.size();i++){
			if(i == Position && !mUserHelpContent.get(i).ismHelpContentShowStatus()){
				mUserHelpContent.get(i).setmHelpContentShowStatus(true);
			}else{
				mUserHelpContent.get(i).setmHelpContentShowStatus(false);
			}
		}
		mHelpListViewAdapter.setListItems(mUserHelpContent);
		mHelpListViewAdapter.notifyDataSetChanged();
		
	}
}