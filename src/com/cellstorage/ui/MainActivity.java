/*
 * 文件名：LoginActivity.java
 * 功能：登陆界面
 * 作者：huwei
 * 创建时间：2013-10-17
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

import com.cellstorage.AppContext;
import com.cellstorage.AppManager;
import com.cellstorage.UIHealper;
import com.cellstorage.adapter.ReminderListViewAdapter;
import com.cellstorage.adapter.ServiceStatusListViewAdapter;
import com.cellstorage.custom.RotateAnimation;
import com.cellstorage.custom.RotateAnimation.InterpolatedTimeListener;
import com.cellstorage.custom.SlidingMenu;
import com.cellstorage.db.DBtableReminderItem;
import com.cellstorage.net.WebClient;
import com.cellstorage.struct.ServiceStatus;
import com.cellstorage.struct.UserInfo;
import com.cellstorage.struct.UserReminder;
import com.cellstorage.utils.FastBlur;
import com.cellstorage.utils.UpdateManager;
import com.cellstorage.utils.parseXML;
import com.cellstorage.view.LoginView;
import com.cellstorage.view.LoginView.LoginViewListener;
import com.example.cellstorage.R;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
@SuppressLint("NewApi") @EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements OnItemClickListener
{
	private AppContext appContext;
	
	private DBtableReminderItem mDBtableReminderItem;
	private Cursor myCursor;
	private int mAllMsgCount = 0;//总消息数
	private List<UserReminder> mUserReminderList = new ArrayList<UserReminder>();
	
	private UserInfo mUserInfo;
	
	/** 是否切换view */
	private boolean enableRefresh;
	
	private SlidingMenu mMenu;
	
	private List<ServiceStatus> lpAllServiceStatusList = new ArrayList<ServiceStatus>();
	private ServiceStatusListViewAdapter mServiceStatusListViewAdapter;
	
	private List<UserReminder> lpAllReminder = new ArrayList<UserReminder>();
	private ReminderListViewAdapter mReminderListViewAdapter;
	
	private ListView mListViewServiceStatus;
	private ListView mListViewReminder;
	
	private final int HANDLE_REMINDER = 1;
	private final int HANDLE_UPDATE_VERSION = 2;
	
	/**版本升级**/
	private UpdateManager manager;
	
	@ViewById(R.id.TextViewHintCount)
	public TextView mTextViewHintCount;
	@ViewById(R.id.TextViewHintContent)
	public TextView mTextViewHintContent;
	@ViewById(R.id.TextViewUserName)
	TextView mTextViewUserName;
	//版本号
	@ViewById(R.id.TextViewCurrentVersion)
	TextView mTextViewCurrentVersion;
	
	@ViewById
	ImageView ImageViewScrollArrowUp;
	
	@ViewById
	ImageView ImageViewScrollArrowDown;
	
	//密码管理
	@Click(R.id.RelativeLayoutPwdManager)
	void OnClickPwdManager(){
		Intent intent = new Intent();
		intent.setClass(this, UpdatePwdActivity_.class);
		startActivity(intent);
	}
	
	//更改邮箱
	@Click(R.id.RelativeLayoutChangeMail)
	void OnClickChangeMail(){
		Intent intent = new Intent();
		intent.setClass(this, UpdateMailActivity_.class);
		startActivity(intent);
	}
	
	//帮助与反馈
	@Click(R.id.RelativeLayoutHelpFeedback)
	void OnClickHelpFeedback(){
		Intent intent = new Intent();
		intent.setClass(this, HelpOrFeedbackActivity_.class);
		startActivity(intent);
	}
	
	//关于我们
	@Click(R.id.RelativeLayoutAboutUs)
	void OnClickAboutUs(){
		Intent intent = new Intent();
		intent.setClass(this, AboutUsActivity_.class);
		startActivity(intent);
	}
	
	//版本升级
	@Click(R.id.RelativeLayoutVersionUpdate)
	void OnClickVersionUpdate(){
		WebClient client = WebClient.getInstance();
		client.sendMessage(appContext, WebClient.Method_getVersionInfo, null);
	}
		

	private GetReminderMsgThread mGetReminderMsgThread;
	class GetReminderMsgThread extends Thread{
		public void run() {
			while(true){
				updateGetReminds();
				try {
					sleep(60000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void toggleMenu(View view)
	{
		if(mMenu != null)
			mMenu.toggle();

	}
	public void view_main(View view)
	{
		
	}
	@Click(R.id.ButtonQuitLogin)
	public void OnClickQuitLogin(){
		
		AlertDialog.Builder builder;
		if(Build.VERSION.SDK_INT < 11){
			builder = new Builder(this);
		}else{
			builder = new Builder(this,R.style.dialog);
		}
		builder.setTitle(R.string.string_quit_login);
		
		builder.setPositiveButton(R.string.string_confirm, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				finish();
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
	@Click(R.id.ImageButtonReminder)
	public void OnClickReminder(){
		if(mAllMsgCount > 0){
			showReminderDialog();
			clearNewReminder();
		}

	}
	public Handler mHandler=new Handler()  
	{  
		public void handleMessage(Message msg)  
		{  
			switch(msg.what)  
			{  
			case HANDLE_REMINDER:{
//				mTextViewHintContent.setText(lpAllReminder.get(0).getMsg_Content());
				updateDB(lpAllReminder);
				selectNewReminderNum();
			}
				break;  
			case HANDLE_UPDATE_VERSION:{
				mTextViewCurrentVersion.setText(getResources().getString(R.string.soft_update_no));
			}
			break;  
			default:  
				break;            
			}  
			super.handleMessage(msg);  
		}  
	}; 
	//初始化函数
	@AfterViews
	public void init(){
		appContext = (AppContext) getApplication();
		mUserInfo = UserInfo.getAppManager();
        //去掉信息栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 //关闭自动弹出的输入法
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mListViewServiceStatus = (ListView) findViewById(R.id.ListViewCell);
        mMenu = (SlidingMenu) findViewById(R.id.id_menu);
		mMenu.setSlideEnable(true);
		mServiceStatusListViewAdapter = new ServiceStatusListViewAdapter(appContext, lpAllServiceStatusList, R.layout.listitem_service_status);
		mListViewServiceStatus.setAdapter(mServiceStatusListViewAdapter);
		mListViewServiceStatus.setOnItemClickListener(this);
		
		mListViewServiceStatus.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
			    switch (scrollState) {
			    // 当不滚动时
			    case OnScrollListener.SCROLL_STATE_IDLE:
				    // 判断滚动到底部
				    if (mListViewServiceStatus.getLastVisiblePosition() == (mListViewServiceStatus.getCount() - 1)) {
				    	Log.d("huwei", "滚动到底部"); 
				    }
				    // 判断滚动到顶部
	
				    if(mListViewServiceStatus.getFirstVisiblePosition() == 0){
				    	Log.d("huwei", "滚动到顶部"); 
				    }

			     break;
		        } 
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				Log.d("huwei", "firstVisibleItem = " + firstVisibleItem ); 
				Log.d("huwei", "visibleItemCount = " + visibleItemCount ); 
				Log.d("huwei", "totalItemCount = " + totalItemCount ); 
				if(totalItemCount > 2){
					if(firstVisibleItem == 0){
						ImageViewScrollArrowUp.setVisibility(View.GONE);
						
						ImageViewScrollArrowDown.setVisibility(View.VISIBLE);
					}
					else if((firstVisibleItem + visibleItemCount) == totalItemCount){
						
						ImageViewScrollArrowDown.setVisibility(View.GONE);
					}
					else{
						ImageViewScrollArrowUp.setVisibility(View.VISIBLE);
						
						ImageViewScrollArrowDown.setVisibility(View.VISIBLE);
					}
				}
			}
		});
		mDBtableReminderItem = new DBtableReminderItem(appContext);
		mDBtableReminderItem.createDBtable();
		
		updateProductServiceList();
		selectNewReminderNum();
		
		mTextViewUserName.setText(mUserInfo.getUserName());
		mGetReminderMsgThread = new GetReminderMsgThread();
		mGetReminderMsgThread.start();
		
		manager = new UpdateManager(this);
		mTextViewCurrentVersion.setText("V"+appContext.AppVesion);
		
		WebClient client = WebClient.getInstance();
		client.sendMessage(appContext, WebClient.Method_getVersionInfo, null);
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(WebClient.INTERNAL_ACTION_GETREMINDS);
		filter.addAction(WebClient.INTERNAL_ACTION_FINDPRODUCTSERVICELIST);
		filter.addAction(WebClient.INTERNAL_ACTION_GETVERSIONINFO);
		
		appContext.registerReceiver(receiver, filter);

	}

	public BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			String resXml = intent.getStringExtra(WebClient.Param_resXml);
			
			if(intent.getAction().equals(WebClient.INTERNAL_ACTION_GETREMINDS)){
				if(resXml.equals("error")){
					
				}else{
					lpAllReminder.clear();
					parseXML.ConserveReminder(resXml, lpAllReminder);
//					UserReminder n = new UserReminder("12", 1, "发到公司等个人奋斗是法国人色如果");
//					lpAllReminder.add(n);
						Message message = new Message();
						message.what = HANDLE_REMINDER;
						mHandler.sendMessage(message);

					
				}
			}else if(intent.getAction().equals(WebClient.INTERNAL_ACTION_FINDPRODUCTSERVICELIST)){
				stopProgressDialog();
				if(resXml.equals("error")){
					
				}else{
					if(lpAllServiceStatusList != null){
						lpAllServiceStatusList.clear();
						parseXML.ConserveServiceStatus(resXml, lpAllServiceStatusList);
						List<ServiceStatus> L_AllServiceStatusList = new ArrayList<ServiceStatus>();
						int k = 0;
						int j = lpAllServiceStatusList.size()-1;
						for(int i = 0;i<lpAllServiceStatusList.size();i++){
							L_AllServiceStatusList.add(new ServiceStatus(null, false));
						}
						for(int i = 0;i<lpAllServiceStatusList.size();i++){
							
							if(lpAllServiceStatusList.get(i).getmServiceStatus()){
								
								L_AllServiceStatusList.get(k).setmServiceStatus(lpAllServiceStatusList.get(i).getmServiceStatus());
								L_AllServiceStatusList.get(k).setmServiceType(lpAllServiceStatusList.get(i).getmServiceType());
								k++;
							}else{
								L_AllServiceStatusList.get(j).setmServiceStatus(lpAllServiceStatusList.get(i).getmServiceStatus());
								L_AllServiceStatusList.get(j).setmServiceType(lpAllServiceStatusList.get(i).getmServiceType());
								j--;
							}
						}
						lpAllServiceStatusList = L_AllServiceStatusList;
						mServiceStatusListViewAdapter.setListItems(lpAllServiceStatusList);
						mServiceStatusListViewAdapter.notifyDataSetInvalidated();
					}
				}
			}
			else if(intent.getAction().equals(WebClient.INTERNAL_ACTION_GETVERSIONINFO))
			{
				if(resXml.equals("error")){
					
				}
				else if(resXml.equals("null")){
					
				}
				else{
					try{
						// 解析XML文件。 由于XML文件比较小，因此使用DOM方式进行解析
						HashMap<String, String> HashMap = parseXML.ConserveVersionUpdateInfo(resXml);
						int res = manager.checkUpdate(HashMap);
						if(res == 1){
							Message message=new Message();  
							message.what = HANDLE_UPDATE_VERSION;  
							mHandler.sendMessage(message);  
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}	
				}

			}
		};
	};
	protected void onDestroy() 
	{ 
		super.onDestroy();
	}
	private static long firstTime;
	/**
	 * 连续按两次返回键就退出
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (firstTime + 2000 > System.currentTimeMillis()) {
			Log.i("huwei", getPackageName()+"程序退出！");
//			ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE); 
//			am.killBackgroundProcesses(getPackageName()); // API Level至少为8才能使用
			AppManager.getAppManager().AppExit(this);
			super.onBackPressed();
		} else {
			UIHealper.DisplayToast(this, getString(R.string.exitAppHit));
		}
		firstTime = System.currentTimeMillis();
	}
	//获取所有产品服务
	private void updateProductServiceList(){
		startProgressDialog("正在加载...");
		WebClient client = WebClient.getInstance();
		Map<String,String> param = new HashMap<String, String>();
		param.put(getString(R.string.UserID), mUserInfo.getUserID());	
		client.sendMessage(appContext, WebClient.Method_findProductServiceList, param);
	}
	//获取消息提醒
	private void updateGetReminds(){
		WebClient client = WebClient.getInstance();
		Map<String,String> param = new HashMap<String, String>();
		param.put(getString(R.string.UserID), mUserInfo.getUserID());	
		client.sendMessage(appContext, WebClient.Method_getReminds, param);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		//传入被选中的服务类型
		if(lpAllServiceStatusList.get(arg2).getmServiceStatus()){
			Intent intent = new Intent();
			intent.putExtra(getString(R.string.ServiceType), lpAllServiceStatusList.get(arg2).getmServiceType());
			intent.setClass(this, CellMonitorActivity_.class);
			startActivity(intent);
		}

	}
	@SuppressLint("NewApi") private void showReminderDialog(){
		AlertDialog.Builder builder;
		if(Build.VERSION.SDK_INT < 11){
			builder = new Builder(this);
		}else{
			builder = new Builder(this,R.style.dialog);
		}
		builder.setInverseBackgroundForced(true);
		builder.setTitle(getString(R.string.reminderTitle));
		final LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
		View viewReminder= inflater.inflate(R.layout.view_dialog_msg_reminder, null);
		mListViewReminder = (ListView) viewReminder.findViewById(R.id.listViewReminder);
		mReminderListViewAdapter = new ReminderListViewAdapter(appContext, mUserReminderList, R.layout.listitem_reminder_content);
		mListViewReminder.setAdapter(mReminderListViewAdapter);
		mReminderListViewAdapter.notifyDataSetChanged();
		builder.setView(viewReminder);
		builder.setPositiveButton(R.string.string_confirm, new android.content.DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
			}
		});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}
	/*更新消息提醒数据库，增加新消息，删除过期的消息*/
	private void updateDB(List<UserReminder> x_UserReminderList){
		if(mDBtableReminderItem == null){
			return; 
		}
		List<UserReminder> l_UserReminderList = new ArrayList<UserReminder>();
		myCursor=mDBtableReminderItem.selectByAttribute(mUserInfo.getUserID());
//		把从数据库中获取的数据放入数组列表
		for(int i = 0;i < myCursor.getCount();i++){
			myCursor.moveToPosition(i);
			UserReminder l_UserReminder = new UserReminder(myCursor.getString(0), myCursor.getString(1), myCursor.getInt(2), myCursor.getString(3));
			l_UserReminderList.add(l_UserReminder);
		}
		if(myCursor != null){
			myCursor.close();
		}
		
		//插入新消息到数据库
		if(x_UserReminderList.size()>0){
			for(int i = 0;i < x_UserReminderList.size();i++){
				if(!IsExistCurrentMsg(l_UserReminderList,x_UserReminderList.get(i))){
					mDBtableReminderItem.insert(x_UserReminderList.get(i));
				}
			}

		}
		//删除过期的消息
		if(l_UserReminderList.size() > 0){
			for(int i = 0;i < l_UserReminderList.size();i++){
				if(!IsExistCurrentMsg(x_UserReminderList,l_UserReminderList.get(i))){
					mDBtableReminderItem.delete(l_UserReminderList.get(i).getMsg_User_Id(),l_UserReminderList.get(i).getMsg_Reminder_Id());
				}
			}
		}
	}
	//判断当前数据在列表中是否存在
	private boolean IsExistCurrentMsg(List<UserReminder> x_UserReminderList,UserReminder x_UserReminder){
		if(x_UserReminderList == null || x_UserReminder == null){
			return false;
		}
		for(int i = 0;i< x_UserReminderList.size();i++){
			if(x_UserReminderList.get(i).getMsg_Reminder_Id().equals(x_UserReminder.getMsg_Reminder_Id())){
				return true;
			}
		}
		return false;
	}
	/*查询新消息个数并显示*/
	private void selectNewReminderNum(){
		
		int l_NewCount = 0;
		
		if(mDBtableReminderItem == null){
			mDBtableReminderItem = new DBtableReminderItem(appContext);
			mDBtableReminderItem.createDBtable();
		}
		
		myCursor=mDBtableReminderItem.selectByAttribute(mUserInfo.getUserID());
		mUserReminderList.clear();
//		把从数据库中获取的数据放入数组列表
		for(int i = 0;i < myCursor.getCount();i++){
			myCursor.moveToPosition(i);
			UserReminder l_UserReminder = new UserReminder(myCursor.getString(0), myCursor.getString(1), myCursor.getInt(2), myCursor.getString(3));
			mUserReminderList.add(l_UserReminder);
		}
		if(myCursor != null){
			myCursor.close();
		}
		
		mAllMsgCount = mUserReminderList.size();
		if(mUserReminderList.size() > 0){
			for(int i = 0;i < mUserReminderList.size();i++){
				if(mUserReminderList.get(i).getMsg_Is_New()==1){
					l_NewCount++;
				}
			}
		}
		if(l_NewCount > 0){
			mTextViewHintCount.setText(""+l_NewCount);
			mTextViewHintCount.setVisibility(View.VISIBLE);
			
		}else{
			mTextViewHintCount.setText("0");
			mTextViewHintCount.setVisibility(View.GONE);
		}
		if(mAllMsgCount>0){
			mTextViewHintContent.setText(mUserReminderList.get(mAllMsgCount-1).getMsg_Content());
		}
	}
	//清空新消息提醒
	private void clearNewReminder(){
		List<UserReminder> l_UserReminderList = new ArrayList<UserReminder>();
		if(mDBtableReminderItem == null){
			mDBtableReminderItem = new DBtableReminderItem(appContext);
			mDBtableReminderItem.createDBtable();
		}
		
		myCursor=mDBtableReminderItem.selectByAttribute(mUserInfo.getUserID());
//		把从数据库中获取的数据放入数组列表
		for(int i = 0;i < myCursor.getCount();i++){
			myCursor.moveToPosition(i);
			UserReminder l_UserReminder = new UserReminder(myCursor.getString(0), myCursor.getString(1), myCursor.getInt(2), myCursor.getString(3));
			l_UserReminderList.add(l_UserReminder);
		}
		if(myCursor != null){
			myCursor.close();
		}
		
		if(l_UserReminderList.size() > 0){
			for(int i = 0;i < l_UserReminderList.size();i++){
				if(l_UserReminderList.get(i).getMsg_Is_New()==1){
					UserReminder l_UserReminder = new UserReminder(l_UserReminderList.get(i).getMsg_User_Id(),l_UserReminderList.get(i).getMsg_Reminder_Id(),0, l_UserReminderList.get(i).getMsg_Content());	
					mDBtableReminderItem.update(l_UserReminder, l_UserReminderList.get(i).getMsg_User_Id(),l_UserReminderList.get(i).getMsg_Reminder_Id());
				}
			}
		}
		mTextViewHintCount.setText("0");
		mTextViewHintCount.setVisibility(View.GONE);
	}
}