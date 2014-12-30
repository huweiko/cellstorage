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

import com.cellstorage.AppContext;
import com.cellstorage.AppManager;
import com.cellstorage.UIHealper;
import com.cellstorage.adapter.ReminderListViewAdapter;
import com.cellstorage.adapter.ServiceStatusListViewAdapter;
import com.cellstorage.custom.RotateAnimation;
import com.cellstorage.custom.RotateAnimation.InterpolatedTimeListener;
import com.cellstorage.custom.SlidingMenu;
import com.cellstorage.net.WebClient;
import com.cellstorage.struct.ServiceStatus;
import com.cellstorage.struct.UserInfo;
import com.cellstorage.struct.UserReminder;
import com.cellstorage.utils.FastBlur;
import com.cellstorage.utils.parseXML;
import com.cellstorage.view.LoginView;
import com.cellstorage.view.LoginView.LoginViewListener;
import com.example.cellstorage.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements InterpolatedTimeListener,LoginViewListener,OnItemClickListener
{
	private RelativeLayout mRelativeLayoutViewHome ;
	private LoginView mRelativeLayoutViewLogin;
	private RelativeLayout mRelativeLayoutMain;
	private RelativeLayout mRelativeLayoutLogo;
	private AppContext appContext;
	
	private ProgressDialog m_pDialog;
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
	
	public final int HANDLE_LOGIN = 1;
	public void toggleMenu(View view)
	{
		/*if(mMenu != null)
			mMenu.toggle();*/
		Intent intent = new Intent();
		intent.setClass(this, CellMonitorActivity.class);
		startActivity(intent);
	}
	public Handler mHandler=new Handler()  
	{  
		public void handleMessage(Message msg)  
		{  
			switch(msg.what)  
			{  
			case HANDLE_LOGIN:{
				enableRefresh = true;
				RotateAnimation rotateAnim = null;
				float cX = mRelativeLayoutMain.getWidth() / 2.0f;
				float cY = mRelativeLayoutMain.getHeight() / 2.0f;
				rotateAnim = new RotateAnimation(cX, cY, RotateAnimation.ROTATE_DECREASE);
				if (rotateAnim != null) {
					rotateAnim.setInterpolatedTimeListener(MainActivity.this);
					rotateAnim.setFillAfter(true);
					mRelativeLayoutMain.startAnimation(rotateAnim);
				}
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
		 //关闭自动弹出的输入法
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //去掉信息栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mRelativeLayoutViewLogin = (LoginView) findViewById(R.id.RelativeLayoutLogin);
        mRelativeLayoutViewHome = (RelativeLayout) findViewById(R.id.RelativeLayoutHome);
        mRelativeLayoutMain = (RelativeLayout) findViewById(R.id.RelativeLayoutMain);
        mRelativeLayoutLogo = (RelativeLayout) findViewById(R.id.RelativeLayoutLogo);
        mListViewServiceStatus = (ListView) findViewById(R.id.ListViewCell);
        mMenu = (SlidingMenu) findViewById(R.id.id_menu);
		if(mMenu.getSlideEnable()){
			mMenu.setSlideEnable(false);
		}
		mRelativeLayoutViewLogin.setLoginViewListener(this);
		mServiceStatusListViewAdapter = new ServiceStatusListViewAdapter(appContext, lpAllServiceStatusList, R.layout.listitem_service_status);
		mListViewServiceStatus.setAdapter(mServiceStatusListViewAdapter);
		mListViewServiceStatus.setOnItemClickListener(this);
//		mListViewServiceStatus.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(WebClient.INTERNAL_ACTION_LOGIN);
		filter.addAction(WebClient.INTERNAL_ACTION_GETREMINDS);
		filter.addAction(WebClient.INTERNAL_ACTION_FINDPRODUCTSERVICELIST);
		appContext.registerReceiver(receiver, filter);
        appBlur();
	}
	private void createDialog() {
		//创建ProgressDialog对象
		m_pDialog = new ProgressDialog(this,R.style.dialog);

		// 设置进度条风格，风格为圆形，旋转的
		m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
		// 设置ProgressDialog 的进度条是否不明确
		m_pDialog.setIndeterminate(false);
		
		// 设置ProgressDialog 是否可以按退回按键取消
		m_pDialog.setCancelable(true);

		// 让ProgressDialog显示
		m_pDialog.show();
	}  


	private final BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			String resXml = intent.getStringExtra(WebClient.Param_resXml);
			
			if(intent.getAction().equals(WebClient.INTERNAL_ACTION_LOGIN))
			{

				if(resXml != null)
				{
					if(resXml.equals("error"))
					{
						if(m_pDialog != null)
							m_pDialog.cancel();
						UIHealper.DisplayToast(appContext,"用户名和密码错误！");
						Message message = new Message();
						message.what = HANDLE_LOGIN;
						mHandler.sendMessage(message); 
					}
					else if(resXml.equals("null")){
						if(m_pDialog != null)
							m_pDialog.cancel();
						UIHealper.DisplayToast(appContext,"登陆失败，请检查网络是否连通！");
					}
					else
					{	
						mUserInfo = UserInfo.getAppManager();
						parseXML.ConserveUserInfo(resXml, mUserInfo);
						if(m_pDialog != null){
							m_pDialog.cancel();	
						}
						mRelativeLayoutViewLogin.LoginFinish();
						Message message = new Message();
						message.what = HANDLE_LOGIN;
						mHandler.sendMessage(message); 
					}
				}
			}else if(intent.getAction().equals(WebClient.INTERNAL_ACTION_GETREMINDS)){
				if(resXml.equals("error")){
					
				}else{
					parseXML.ConserveReminder(resXml, lpAllReminder);
					showReminderDialog();
				}
			}else if(intent.getAction().equals(WebClient.INTERNAL_ACTION_FINDPRODUCTSERVICELIST)){
				
				if(resXml.equals("error")){
					
				}else{
					if(lpAllServiceStatusList != null){
						parseXML.ConserveServiceStatus(resXml, lpAllServiceStatusList);
						mServiceStatusListViewAdapter.setListItems(lpAllServiceStatusList);
						mServiceStatusListViewAdapter.notifyDataSetInvalidated();
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
	//翻转动作监听
	@Override
	public void interpolatedTime(float interpolatedTime) {
		// TODO Auto-generated method stub
		// 监听到翻转进度过半时，更新txtNumber显示内容。
		if (enableRefresh && interpolatedTime > 0.5f) {
			mRelativeLayoutViewLogin.setVisibility(View.INVISIBLE);
			mRelativeLayoutViewHome.setVisibility(View.VISIBLE);
			enableRefresh = false;
		}
	}
	//把登录框进行模糊处理
    private void appBlur(){
    	mRelativeLayoutLogo.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
            
            @Override
            public boolean onPreDraw() {
            	mRelativeLayoutLogo.getViewTreeObserver().removeOnPreDrawListener(this);
            	mRelativeLayoutLogo.buildDrawingCache();

                Bitmap bmp = mRelativeLayoutLogo.getDrawingCache();
                blur(bmp, mRelativeLayoutViewLogin);
                return true;
            }
        });
    }
    
    //模糊处理
    @SuppressWarnings("deprecation")
	private void blur(Bitmap bkg, View view){
        long startMs = System.currentTimeMillis();
        float scaleFactor = 1;
        float radius = 8;//20
        Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth()), 
                (int) (view.getMeasuredHeight()), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft(), -view.getTop());
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);
        overlay = FastBlur.doBlur(overlay, (int)radius, true);
        view.setBackgroundDrawable(new BitmapDrawable(getResources(), overlay));
        Log.e("", System.currentTimeMillis() - startMs + "ms");
    }
	@Override
	public void OnClickLogin(String username, String password) {
		// TODO Auto-generated method stub
		if((username.equals("")) || (password.equals("")))
		{
			UIHealper.DisplayToast(appContext,"用户名或密码不能为空！");
		}
		else 
		{

//			mMenu.setSlideEnable(true);
			createDialog();
			WebClient client = WebClient.getInstance();
			Map<String,String> param = new HashMap<String, String>();
			param.put(username, password);	
			client.sendMessage(appContext, WebClient.Method_login, param);
		}
	}
	@Override
	public void OnSeekPassword() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		//传入被选中的服务类型
	}
	@SuppressLint("NewApi") private void showReminderDialog(){
		AlertDialog.Builder builder = new Builder(MainActivity.this,R.style.dialog);
		builder.setInverseBackgroundForced(true);
		builder.setTitle(getString(R.string.reminderTitle));
		final LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
		View viewReminder= inflater.inflate(R.layout.view_dialog_msg_reminder, null);
		mListViewReminder = (ListView) viewReminder.findViewById(R.id.listViewReminder);
		mReminderListViewAdapter = new ReminderListViewAdapter(appContext, lpAllReminder, R.layout.listitem_reminder_content);
		mListViewReminder.setAdapter(mReminderListViewAdapter);
		mReminderListViewAdapter.notifyDataSetChanged();
		builder.setView(viewReminder);
		builder.setPositiveButton(R.string.dialog_confirm, new android.content.DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
			}
		});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}
	
}