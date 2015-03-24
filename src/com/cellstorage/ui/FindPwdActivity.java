/*
 * �ļ�����LoadActivity.java
 * ���ܣ�Ӧ�ó�������ʱ�ļ��ؽ��棬�������
 * ���ߣ�huwei
 * ����ʱ�䣺2013-10-22
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
	//���ذ�ť
	@ViewById(R.id.ImageButtonMonitorBack)
	ImageView mImageButtonMonitorBack;
	
	//�һ�����View
	@ViewById(R.id.LinearLayoutFindPwdEditView)
	LinearLayout mLinearLayoutFindPwdEditView;
	
	//�һ�����ɹ�View
	@ViewById(R.id.LinearLayoutFindPasswordViewSuccess)
	LinearLayout mLinearLayoutFindPasswordViewSuccess;
	
	//�һ�����ʧ��View
	@ViewById(R.id.LinearLayoutFindPasswordFail)
	LinearLayout mLinearLayoutFindPasswordFail;
	
	//�һ����������
	@ViewById(R.id.EditTextFindPwd)
	EditText mEditTextFindPwd;
	
	//�һ�������һ����ť
	@ViewById(R.id.ButtonFindPasswordNext)
	Button mButtonFindPasswordNext;
	
	//�һ����뷵�ذ�ť
	@ViewById(R.id.ButtonFindPasswordBack)
	Button mButtonFindPasswordBack;
	
	//�һ�����ɹ�ȷ�ϰ�ť
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