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
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.cellstorage.AppContext;
import com.cellstorage.OtherHealper;
import com.cellstorage.UIHealper;
import com.cellstorage.net.WebClient;
import com.cellstorage.struct.UserInfo;
import com.example.cellstorage.R;

@SuppressLint("NewApi") @EActivity(R.layout.activity_update_password)
public class UpdatePwdActivity extends BaseActivity 
{
	private AppContext appContext;
	private static final int HANDLE_UPDATEPWD_SUCCESS = 0;
	private static final int HANDLE_UPDATEPWD_FAIL = 1;
	private UserInfo mUserInfo;
	//修改密码编辑框View
	@ViewById(R.id.LinearLayoutUpdatePasswordEditView)
	LinearLayout mLinearLayoutUpdatePasswordEditView;
	
	//更新密码成功View
	@ViewById(R.id.LinearLayoutUpdatePasswordViewSuccess)
	LinearLayout mLinearLayoutUpdatePasswordViewSuccess;
	
	//更新密码失败View
	@ViewById(R.id.LinearLayoutUpdatePasswordFail)
	LinearLayout mLinearLayoutUpdatePasswordFail;
	
	//旧密码输入框
	@ViewById(R.id.EditTextUpdatePwdOld)
	EditText mEditTextUpdatePwdOld;
	
	//新密码输入框
	@ViewById(R.id.EditTextUpdatePwdNew)
	EditText mEditTextUpdatePwdNew;
	
	//确认新密码输入框
	@ViewById(R.id.EditTextAgainUpdatePwdNew)
	EditText mEditTextAgainUpdatePwdNew;
	
	//提交
	@ViewById(R.id.ButtonUpdatePasswordSubmit)
	Button mButtonUpdatePasswordSubmit;
	
	//更新密码返回按钮
	@ViewById(R.id.ImageButtonUpdatePasswordBack)
	ImageButton mImageButtonUpdatePasswordBack;
	
	//更新密码成功确认按钮
	@ViewById(R.id.ButtonUpdatePasswordSuccessConfirm)
	Button mButtonUpdatePasswordSuccessConfirm;
	
	@AfterViews
	void Init(){
		appContext = (AppContext) getApplication();
		mUserInfo = UserInfo.getAppManager();
		IntentFilter filter = new IntentFilter();
		filter.addAction(WebClient.INTERNAL_ACTION_UPDATEPWORD);
		appContext.registerReceiver(receiver, filter);
	}
	@Click(R.id.ImageButtonUpdatePasswordBack)
	void OnClickImageButtonMonitorBack(){
		finish();
	}
	@Click(R.id.ButtonUpdatePasswordSubmit)
	void OnClickButtonUpdatePasswordSubmit(){
		final String oldPwdEditViewContent = mEditTextUpdatePwdOld.getText().toString();
		final String newPwdEditViewContent = mEditTextUpdatePwdNew.getText().toString();
		final String AgainNewPwdEditViewContent = mEditTextAgainUpdatePwdNew.getText().toString();
		if(oldPwdEditViewContent.equals("") || newPwdEditViewContent.equals("") || AgainNewPwdEditViewContent.equals("")){
			UIHealper.DisplayToast(appContext, getString(R.string.editview_null_hint));
		}
		else{
			if(!newPwdEditViewContent.equals(AgainNewPwdEditViewContent)){
				UIHealper.DisplayToast(appContext, getString(R.string.password_input_hint));
			}else{
				AlertDialog.Builder builder;
				if(Build.VERSION.SDK_INT < 11){
					builder = new Builder(this);
				}else{
					builder = new Builder(this,R.style.dialog);
				}
				builder.setTitle(R.string.updatePassword);
				
				builder.setPositiveButton(R.string.string_confirm, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
						sendMessageUpdatePwd(oldPwdEditViewContent,newPwdEditViewContent);
					}
				});
				builder.setNegativeButton(R.string.soft_update_cancel, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});
				Dialog noticeDialog = builder.create();
				noticeDialog.show();
			}

		}
	}
	@Click(R.id.ButtonUpdatePasswordSuccessConfirm)
	void OnClickButtonUpdatePasswordSuccessConfirm(){
		finish();
	}
	public Handler mHandler=new Handler()  
	{  
		public void handleMessage(Message msg)  
		{  
			switch(msg.what)  
			{  
			case HANDLE_UPDATEPWD_SUCCESS:{
				mLinearLayoutUpdatePasswordViewSuccess.setVisibility(View.VISIBLE);
				mLinearLayoutUpdatePasswordEditView.setVisibility(View.GONE);
				mLinearLayoutUpdatePasswordFail.setVisibility(View.GONE);
			}
				break;  
			case HANDLE_UPDATEPWD_FAIL:{
				mLinearLayoutUpdatePasswordFail.setVisibility(View.VISIBLE);
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
			
			if(intent.getAction().equals(WebClient.INTERNAL_ACTION_UPDATEPWORD))
			{
				Message message = new Message();
				if(resXml.equals("error"))
				{
					message.what = HANDLE_UPDATEPWD_FAIL;
					mHandler.sendMessage(message);
				}
				else if(resXml.equals("null")){
					message.what = HANDLE_UPDATEPWD_FAIL;
					mHandler.sendMessage(message);
				}
				else{
					message.what = HANDLE_UPDATEPWD_SUCCESS;
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
	private void sendMessageUpdatePwd(String oldPwd,String newPwd){
		WebClient client = WebClient.getInstance();
		Map<String,String> param = new HashMap<String, String>();
		param.put(getString(R.string.UserID), mUserInfo.getUserID());
		param.put(getString(R.string.OldPassword), oldPwd);
		param.put(getString(R.string.NewPassword), newPwd);
		client.sendMessage(appContext, WebClient.Method_updatePword, param);
	}
}