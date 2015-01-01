/*
 * �ļ�����LoginActivity.java
 * ���ܣ���½����
 * ���ߣ�huwei
 * ����ʱ�䣺2013-10-17
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
import android.database.Cursor;
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
import android.widget.TextView;
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements InterpolatedTimeListener,LoginViewListener,OnItemClickListener
{
	private RelativeLayout mRelativeLayoutViewHome ;
	private LoginView mRelativeLayoutViewLogin;
	private RelativeLayout mRelativeLayoutMain;
	private RelativeLayout mRelativeLayoutLogo;
	private AppContext appContext;
	
	private DBtableReminderItem mDBtableReminderItem;
	private Cursor myCursor;
	private int mAllMsgCount = 0;//����Ϣ��
	private List<UserReminder> mUserReminderList = new ArrayList<UserReminder>();
	
	private UserInfo mUserInfo;
	
	/** �Ƿ��л�view */
	private boolean enableRefresh;
	
	private SlidingMenu mMenu;
	
	private List<ServiceStatus> lpAllServiceStatusList = new ArrayList<ServiceStatus>();
	private ServiceStatusListViewAdapter mServiceStatusListViewAdapter;
	
	private List<UserReminder> lpAllReminder = new ArrayList<UserReminder>();
	private ReminderListViewAdapter mReminderListViewAdapter;
	
	private ListView mListViewServiceStatus;
	private ListView mListViewReminder;
	
	public final int HANDLE_LOGIN = 1;
	@ViewById(R.id.TextViewHintCount)
	public TextView mTextViewHintCount;
	@ViewById(R.id.TextViewHintContent)
	public TextView mTextViewHintContent;
	private static GetReminderMsgThread mGetReminderMsgThread;
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
	@Click(R.id.ButtonQuitLogin)
	public void OnClickQuitLogin(){
		mRelativeLayoutViewLogin.setVisibility(View.VISIBLE);
		mRelativeLayoutViewHome.setVisibility(View.INVISIBLE);
		
		if(mMenu != null){
			mMenu.toggle();
		}
			
		mMenu.setSlideEnable(false);
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
			case HANDLE_LOGIN:{
	/*			enableRefresh = true;
				RotateAnimation rotateAnim = null;
				float cX = mRelativeLayoutMain.getWidth() / 2.0f;
				float cY = mRelativeLayoutMain.getHeight() / 2.0f;
				rotateAnim = new RotateAnimation(cX, cY, RotateAnimation.ROTATE_DECREASE);
				if (rotateAnim != null) {
					rotateAnim.setInterpolatedTimeListener(MainActivity.this);
					rotateAnim.setFillAfter(true);
					mRelativeLayoutMain.startAnimation(rotateAnim);
				}*/
				mRelativeLayoutViewLogin.setVisibility(View.INVISIBLE);
				mRelativeLayoutViewHome.setVisibility(View.VISIBLE);
				updateProductServiceList();
				selectNewReminderNum();
				mGetReminderMsgThread = new GetReminderMsgThread();
				mGetReminderMsgThread.start();
			}
				break;  
			default:  
				break;            
			}  
			super.handleMessage(msg);  
		}  
	}; 
	//��ʼ������
	@AfterViews
	public void init(){
		appContext = (AppContext) getApplication();
		 //�ر��Զ����������뷨
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //ȥ����Ϣ��
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

		mDBtableReminderItem = new DBtableReminderItem(appContext);
		mDBtableReminderItem.createDBtable();
		
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
/*	private void createDialog() {
		//����ProgressDialog����
		m_pDialog = new ProgressDialog(this,R.style.dialog);

		// ���ý�������񣬷��ΪԲ�Σ���ת��
		m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
		// ����ProgressDialog �Ľ������Ƿ���ȷ
		m_pDialog.setIndeterminate(false);
		
		// ����ProgressDialog �Ƿ���԰��˻ذ���ȡ��
		m_pDialog.setCancelable(true);

		// ��ProgressDialog��ʾ
		m_pDialog.show();
	}  */


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
						UIHealper.DisplayToast(appContext,"�û������������");
						mRelativeLayoutViewLogin.LoginFail();
					}
					else if(resXml.equals("null")){
						UIHealper.DisplayToast(appContext,"��½ʧ�ܣ����������Ƿ���ͨ��");
						mRelativeLayoutViewLogin.LoginFail();
					}
					else
					{	
						mUserInfo = UserInfo.getAppManager();
						parseXML.ConserveUserInfo(resXml, mUserInfo);
						mRelativeLayoutViewLogin.LoginSuccess();
						Message message = new Message();
						message.what = HANDLE_LOGIN;
						mHandler.sendMessage(message); 
					}
				}
			}else if(intent.getAction().equals(WebClient.INTERNAL_ACTION_GETREMINDS)){
				if(resXml.equals("error")){
					
				}else{
					lpAllReminder.clear();
					parseXML.ConserveReminder(resXml, lpAllReminder);
					if(lpAllReminder.size()>0){
						mTextViewHintContent.setText(lpAllReminder.get(0).getMsg_Content());
						updateDB(lpAllReminder);
						selectNewReminderNum();
					}
					
				}
			}else if(intent.getAction().equals(WebClient.INTERNAL_ACTION_FINDPRODUCTSERVICELIST)){
				
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
		};
	};
	protected void onDestroy() 
	{ 
		super.onDestroy();
	}
	private static long firstTime;
	/**
	 * ���������η��ؼ����˳�
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (firstTime + 2000 > System.currentTimeMillis()) {
			Log.i("huwei", getPackageName()+"�����˳���");
//			ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE); 
//			am.killBackgroundProcesses(getPackageName()); // API Level����Ϊ8����ʹ��
			AppManager.getAppManager().AppExit(this);
			super.onBackPressed();
		} else {
			UIHealper.DisplayToast(this, getString(R.string.exitAppHit));
		}
		firstTime = System.currentTimeMillis();
	}
	//��ת��������
	@Override
	public void interpolatedTime(float interpolatedTime) {
		// TODO Auto-generated method stub
		// ��������ת���ȹ���ʱ������txtNumber��ʾ���ݡ�
		if (enableRefresh && interpolatedTime > 0.5f) {
			mRelativeLayoutViewLogin.setVisibility(View.INVISIBLE);
			mRelativeLayoutViewHome.setVisibility(View.VISIBLE);
			enableRefresh = false;
		}
	}
	//�ѵ�¼�����ģ������
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
    
    //ģ������
    @SuppressWarnings("deprecation")
	private void blur(Bitmap bkg, View view){
        long startMs = System.currentTimeMillis();
        float scaleFactor = 1;
        float radius = 15;//20
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
			UIHealper.DisplayToast(appContext,"�û��������벻��Ϊ�գ�");
		}
		else 
		{

			mMenu.setSlideEnable(true);
			WebClient client = WebClient.getInstance();
			Map<String,String> param = new HashMap<String, String>();
			param.put(username, password);	
			client.sendMessage(appContext, WebClient.Method_login, param);
		}
	}
	//��ȡ���в�Ʒ����
	private void updateProductServiceList(){
		WebClient client = WebClient.getInstance();
		Map<String,String> param = new HashMap<String, String>();
		param.put(getString(R.string.UserID), mUserInfo.getUserID());	
		client.sendMessage(appContext, WebClient.Method_findProductServiceList, param);
	}
	//��ȡ��Ϣ����
	private void updateGetReminds(){
		WebClient client = WebClient.getInstance();
		Map<String,String> param = new HashMap<String, String>();
		param.put(getString(R.string.UserID), mUserInfo.getUserID());	
		client.sendMessage(appContext, WebClient.Method_getReminds, param);
	}
	@Override
	public void OnSeekPassword() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		//���뱻ѡ�еķ�������
		if(lpAllServiceStatusList.get(arg2).getmServiceStatus()){
			Intent intent = new Intent();
			intent.putExtra(getString(R.string.ServiceType), lpAllServiceStatusList.get(arg2).getmServiceType());
			intent.setClass(this, CellMonitorActivity_.class);
			startActivity(intent);
		}

	}
	@SuppressLint("NewApi") private void showReminderDialog(){
		AlertDialog.Builder builder = new Builder(MainActivity.this,R.style.dialog);
		builder.setInverseBackgroundForced(true);
		builder.setTitle(getString(R.string.reminderTitle));
		final LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
		View viewReminder= inflater.inflate(R.layout.view_dialog_msg_reminder, null);
		mListViewReminder = (ListView) viewReminder.findViewById(R.id.listViewReminder);
		mReminderListViewAdapter = new ReminderListViewAdapter(appContext, mUserReminderList, R.layout.listitem_reminder_content);
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
	/*������Ϣ�������ݿ⣬��������Ϣ��ɾ�����ڵ���Ϣ*/
	private void updateDB(List<UserReminder> x_UserReminderList){
		if(mDBtableReminderItem == null){
			return;
		}
		List<UserReminder> l_UserReminderList = new ArrayList<UserReminder>();
		myCursor=mDBtableReminderItem.select();
//		�Ѵ����ݿ��л�ȡ�����ݷ��������б�
		for(int i = 0;i < myCursor.getCount();i++){
			myCursor.moveToPosition(i);
			UserReminder l_UserReminder = new UserReminder(myCursor.getString(0), myCursor.getInt(1), myCursor.getString(2));
			l_UserReminderList.add(l_UserReminder);
		}
		//��������Ϣ�����ݿ�
		if(x_UserReminderList.size()>0){
			for(int i = 0;i < x_UserReminderList.size();i++){
				if(!IsExistCurrentMsg(l_UserReminderList,x_UserReminderList.get(i))){
					mDBtableReminderItem.insert(x_UserReminderList.get(i));
				}
			}

		}
		//ɾ�����ڵ���Ϣ
		if(l_UserReminderList.size() > 0){
			for(int i = 0;i < l_UserReminderList.size();i++){
				if(!IsExistCurrentMsg(x_UserReminderList,l_UserReminderList.get(i))){
					mDBtableReminderItem.delete(l_UserReminderList.get(i).getMsg_Reminder_Id());
				}
			}
		}
	}
	//�жϵ�ǰ�������б����Ƿ����
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
	/*��ѯ����Ϣ��������ʾ*/
	private void selectNewReminderNum(){
		
		int l_NewCount = 0;
		
		if(mDBtableReminderItem == null){
			mDBtableReminderItem = new DBtableReminderItem(appContext);
			mDBtableReminderItem.createDBtable();
		}
		
		myCursor=mDBtableReminderItem.select();
		mUserReminderList.clear();
//		�Ѵ����ݿ��л�ȡ�����ݷ��������б�
		for(int i = 0;i < myCursor.getCount();i++){
			myCursor.moveToPosition(i);
			UserReminder l_UserReminder = new UserReminder(myCursor.getString(0), myCursor.getInt(1), myCursor.getString(2));
			mUserReminderList.add(l_UserReminder);
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
			mTextViewHintContent.setText(mUserReminderList.get(0).getMsg_Content());
		}
	}
	//�������Ϣ����
	private void clearNewReminder(){
		List<UserReminder> l_UserReminderList = new ArrayList<UserReminder>();
		if(mDBtableReminderItem == null){
			mDBtableReminderItem = new DBtableReminderItem(appContext);
			mDBtableReminderItem.createDBtable();
		}
		
		myCursor=mDBtableReminderItem.select();
//		�Ѵ����ݿ��л�ȡ�����ݷ��������б�
		for(int i = 0;i < myCursor.getCount();i++){
			myCursor.moveToPosition(i);
			UserReminder l_UserReminder = new UserReminder(myCursor.getString(0), myCursor.getInt(1), myCursor.getString(2));
			l_UserReminderList.add(l_UserReminder);
		}
		if(l_UserReminderList.size() > 0){
			for(int i = 0;i < l_UserReminderList.size();i++){
				if(l_UserReminderList.get(i).getMsg_Is_New()==1){
					UserReminder l_UserReminder = new UserReminder(l_UserReminderList.get(i).getMsg_Reminder_Id(), 0, l_UserReminderList.get(i).getMsg_Content());	
					mDBtableReminderItem.update(l_UserReminder, l_UserReminderList.get(i).getMsg_Reminder_Id());
				}
			}
		}
		mTextViewHintCount.setText("0");
		mTextViewHintCount.setVisibility(View.GONE);
	}
}