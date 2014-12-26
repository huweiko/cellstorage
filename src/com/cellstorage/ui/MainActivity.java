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
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import com.cellstorage.AppContext;
import com.cellstorage.AppManager;
import com.cellstorage.UIHealper;
import com.cellstorage.adapter.ServiceStatusListViewAdapter;
import com.cellstorage.custom.RotateAnimation;
import com.cellstorage.custom.RotateAnimation.InterpolatedTimeListener;
import com.cellstorage.custom.SlidingMenu;
import com.cellstorage.net.WebClient;
import com.cellstorage.struct.ServiceStatus;
import com.cellstorage.struct.UserInfo;
import com.cellstorage.utils.FastBlur;
import com.cellstorage.utils.parseXML;
import com.cellstorage.view.LoginView;
import com.cellstorage.view.LoginView.LoginViewListener;
import com.example.cellstorage.R;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
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
	
	/** �Ƿ��л�view */
	private boolean enableRefresh;
	
	private SlidingMenu mMenu;
	
	private List<ServiceStatus> lpAllServiceStatusList = new ArrayList<ServiceStatus>();
	private ServiceStatusListViewAdapter mServiceStatusListViewAdapter;
	private ListView mListViewServiceStatus;
	public void toggleMenu(View view)
	{
		if(mMenu != null)
			mMenu.toggle();
	}
	//��ʼ������
	@AfterViews
	public void init(){
		appContext = (AppContext) getApplication();
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
		IntentFilter filter = new IntentFilter();
		filter.addAction(WebClient.INTERNAL_ACTION_LOGIN);
		filter.addAction(WebClient.INTERNAL_ACTION_GETREMINDS);
		filter.addAction(WebClient.INTERNAL_ACTION_FINDPRODUCTSERVICELIST);
		appContext.registerReceiver(receiver, filter);
        appBlur();
	}
	private void createDialog() {
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
						UIHealper.DisplayToast(appContext,"�û������������");
					}
					else if(resXml.equals("null")){
						if(m_pDialog != null)
							m_pDialog.cancel();
						UIHealper.DisplayToast(appContext,"��½ʧ�ܣ����������Ƿ���ͨ��");
					}
					else
					{	
						mUserInfo = UserInfo.getAppManager();
						parseXML.ConserveUserInfo(resXml, mUserInfo);
						if(m_pDialog != null){
							m_pDialog.cancel();	
						}
						mRelativeLayoutViewLogin.LoginFinish();
					}
				}
			}else if(intent.getAction().equals(WebClient.INTERNAL_ACTION_GETREMINDS)){
				if(resXml.equals("error")){
					
				}else{
					
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
    private void blur(Bitmap bkg, View view){
        long startMs = System.currentTimeMillis();
        float scaleFactor = 1;
        float radius = 20;//20
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
			enableRefresh = true;
			RotateAnimation rotateAnim = null;
			float cX = mRelativeLayoutMain.getWidth() / 2.0f;
			float cY = mRelativeLayoutMain.getHeight() / 2.0f;
			rotateAnim = new RotateAnimation(cX, cY, RotateAnimation.ROTATE_DECREASE);
			if (rotateAnim != null) {
				rotateAnim.setInterpolatedTimeListener(this);
				rotateAnim.setFillAfter(true);
				mRelativeLayoutMain.startAnimation(rotateAnim);
			}
			mMenu.setSlideEnable(true);
/*			createDialog();
			WebClient client = WebClient.getInstance();
			Map<String,String> param = new HashMap<String, String>();
			param.put(uname, password);	
			client.sendMessage(appContext, WebClient.Method_login, param);*/
		}
	}
	@Override
	public void OnSeekPassword() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
}