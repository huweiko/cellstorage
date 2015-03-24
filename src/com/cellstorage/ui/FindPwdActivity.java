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

import java.util.HashMap;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cellstorage.AppContext;
import com.cellstorage.OtherHealper;
import com.cellstorage.UIHealper;
import com.cellstorage.net.WebClient;
import com.example.cellstorage.R;

@EActivity(R.layout.activity_find_password)
public class FindPwdActivity extends BaseActivity 
{
	private AppContext appContext;
	private static final int HANDLE_FINDPWD_SUCCESS = 0;
	private static final int HANDLE_FINDPWD_FAIL = 1;
	//返回按钮
	@ViewById(R.id.ImageButtonMonitorBack)
	ImageView mImageButtonMonitorBack;
	
	//找回密码View
	@ViewById(R.id.LinearLayoutFindPwdEditView)
	LinearLayout mLinearLayoutFindPwdEditView;
	
	//找回密码成功View
	@ViewById(R.id.LinearLayoutFindPasswordViewSuccess)
	LinearLayout mLinearLayoutFindPasswordViewSuccess;
	
	//找回密码失败View
	@ViewById(R.id.LinearLayoutFindPasswordFail)
	LinearLayout mLinearLayoutFindPasswordFail;
	
	//找回密码输入框
	@ViewById(R.id.EditTextFindPwd)
	EditText mEditTextFindPwd;
	
	//找回密码下一步按钮
	@ViewById(R.id.ButtonFindPasswordNext)
	Button mButtonFindPasswordNext;
	
	//找回密码返回按钮
	@ViewById(R.id.ButtonFindPasswordBack)
	Button mButtonFindPasswordBack;
	
	//找回密码成功确认按钮
	@ViewById(R.id.ButtonFindPwdSuccessConfirm)
	Button mButtonFindPwdSuccessConfirm;
	
	@AfterViews
	void Init(){
		appContext = (AppContext) getApplication();
		IntentFilter filter = new IntentFilter();
		filter.addAction(WebClient.INTERNAL_ACTION_FINDPWORD);
		appContext.registerReceiver(receiver, filter);
	}
	@Click(R.id.ImageButtonMonitorBack)
	void OnClickImageButtonMonitorBack(){
		finish();
	}
	@Click(R.id.ButtonFindPasswordNext)
	void OnClickButtonFindPasswordNext(){
		String l_EditViewContent = mEditTextFindPwd.getText().toString();
		if(!OtherHealper.isEmail(l_EditViewContent)){
			UIHealper.DisplayToast(appContext, getString(R.string.email_formaterror_text));
		}
		else{
			sendMessageFindPwd(l_EditViewContent);
		}
	}
	@Click(R.id.ButtonFindPasswordBack)
	void OnClickButtonFindPasswordBack(){
		finish();
	}
	@Click(R.id.ButtonFindPwdSuccessConfirm)
	void OnClickButtonFindPwdSuccessConfirm(){
		finish();
	}
	public Handler mHandler=new Handler()  
	{  
		public void handleMessage(Message msg)  
		{  
			switch(msg.what)  
			{  
			case HANDLE_FINDPWD_SUCCESS:{
				mLinearLayoutFindPasswordViewSuccess.setVisibility(View.VISIBLE);
				mLinearLayoutFindPwdEditView.setVisibility(View.GONE);
				mLinearLayoutFindPasswordFail.setVisibility(View.GONE);
			}
				break;  
			case HANDLE_FINDPWD_FAIL:{
				mLinearLayoutFindPasswordFail.setVisibility(View.VISIBLE);
			}
			break;  
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
			
			if(intent.getAction().equals(WebClient.INTERNAL_ACTION_FINDPWORD))
			{
				Message message = new Message();
				if(resXml.equals("error"))
				{
					message.what = HANDLE_FINDPWD_FAIL;
					mHandler.sendMessage(message);
				}
				else if(resXml.equals("null")){
					message.what = HANDLE_FINDPWD_FAIL;
					mHandler.sendMessage(message);
				}
				else{
					message.what = HANDLE_FINDPWD_SUCCESS;
					mHandler.sendMessage(message);
				}
			}
		};
	};
	protected void onDestroy() 
	{
		appContext.unregisterReceiver(receiver);
		super.onDestroy();
	}
	private void sendMessageFindPwd(String x_Email){
		WebClient client = WebClient.getInstance();
		Map<String,String> param = new HashMap<String, String>();
		param.put(getString(R.string.E_Mail), x_Email);
		client.sendMessage(appContext, WebClient.Method_findPword, param);
	}
}