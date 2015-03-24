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
import android.widget.TextView;
import com.cellstorage.AppContext;
import com.cellstorage.OtherHealper;
import com.cellstorage.UIHealper;
import com.cellstorage.net.WebClient;
import com.cellstorage.struct.UserInfo;
import com.example.cellstorage.R;

@SuppressLint("NewApi") @EActivity(R.layout.activity_update_mail)
public class UpdateMailActivity extends BaseActivity 
{
	private AppContext appContext;
	private static final int HANDLE_UPDATEMAIL_SUCCESS = 0;
	private static final int HANDLE_UPDATEMAIL_FAIL = 1;
	private UserInfo mUserInfo;
	//修改邮箱编辑框View
	@ViewById(R.id.LinearLayoutUpdateMailEditView)
	LinearLayout mLinearLayoutUpdateMailEditView;
	
	//修改邮箱成功View
	@ViewById(R.id.LinearLayoutUpdateMailViewSuccess)
	LinearLayout mLinearLayoutUpdateMailViewSuccess;
	
	//修改邮箱失败View
	@ViewById(R.id.LinearLayoutUpdateMailFail)
	LinearLayout mLinearLayoutUpdateMailFail;
	
	//旧邮箱输入框
	@ViewById(R.id.TextViewUpdateMailOld)
	TextView mTextViewUpdateMailOld;
	
	//新邮箱输入框
	@ViewById(R.id.EditTextUpdateMailNew)
	EditText mEditTextUpdateMailNew;
	
	//提交
	@ViewById(R.id.ButtonUpdateMailSubmit)
	Button mButtonUpdateMailSubmit;
	
	//修改邮箱返回按钮
	@ViewById(R.id.ImageButtonUpdateMailBack)
	ImageButton mImageButtonUpdateMailBack;
	
	//修改邮箱成功确认按钮
	@ViewById(R.id.ButtonUpdateMailSuccessConfirm)
	Button mButtonUpdateMailSuccessConfirm;
	
	@AfterViews
	void Init(){
		appContext = (AppContext) getApplication();
		mUserInfo = UserInfo.getAppManager();
		mTextViewUpdateMailOld.setText(mUserInfo.getEmail());
		IntentFilter filter = new IntentFilter();
		filter.addAction(WebClient.INTERNAL_ACTION_UPDATEEMAIL);
		appContext.registerReceiver(receiver, filter);
	}
	@Click(R.id.ImageButtonUpdateMailBack)
	void OnClickImageButtonMonitorBack(){
		finish();
	}
	@Click(R.id.ButtonUpdateMailSubmit)
	void OnClickButtonUpdateMailSubmit(){
		final String oldPwdEditViewContent = mTextViewUpdateMailOld.getText().toString();
		final String newPwdEditViewContent = mEditTextUpdateMailNew.getText().toString();
		if(oldPwdEditViewContent.equals("") || newPwdEditViewContent.equals("")){
			UIHealper.DisplayToast(appContext, getString(R.string.editview_null_hint));
		}
		else{
			if(OtherHealper.isEmail(oldPwdEditViewContent) && OtherHealper.isEmail(newPwdEditViewContent)){
				
				AlertDialog.Builder builder;
				if(Build.VERSION.SDK_INT < 11){
					builder = new Builder(this);
				}else{
					builder = new Builder(this,R.style.dialog);
				}
				builder.setTitle(R.string.updateMail);
				
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
			
			}else{
				UIHealper.DisplayToast(appContext, getString(R.string.email_formaterror_text));
			}
	
		}
	}
	@Click(R.id.ButtonUpdateMailSuccessConfirm)
	void OnClickButtonUpdateMailSuccessConfirm(){
		finish();
	}
	public Handler mHandler=new Handler()  
	{  
		public void handleMessage(Message msg)  
		{  
			switch(msg.what)  
			{  
			case HANDLE_UPDATEMAIL_SUCCESS:{
				mLinearLayoutUpdateMailViewSuccess.setVisibility(View.VISIBLE);
				mLinearLayoutUpdateMailEditView.setVisibility(View.GONE);
				mLinearLayoutUpdateMailFail.setVisibility(View.GONE);
			}
				break;  
			case HANDLE_UPDATEMAIL_FAIL:{
				mLinearLayoutUpdateMailFail.setVisibility(View.VISIBLE);
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
			
			if(intent.getAction().equals(WebClient.INTERNAL_ACTION_UPDATEEMAIL))
			{
				Message message = new Message();
				if(resXml.equals("error"))
				{
					message.what = HANDLE_UPDATEMAIL_FAIL;
					mHandler.sendMessage(message);
				}
				else if(resXml.equals("null")){
					message.what = HANDLE_UPDATEMAIL_FAIL;
					mHandler.sendMessage(message);
				}
				else{
					message.what = HANDLE_UPDATEMAIL_SUCCESS;
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
		param.put(getString(R.string.OldMail), oldPwd);
		param.put(getString(R.string.NewMail), newPwd);
		client.sendMessage(appContext, WebClient.Method_updateEmail, param);
	}
}