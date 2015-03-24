package com.cellstorage.ui;

import java.util.HashMap;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.RelativeLayout;

import com.cellstorage.AppContext;
import com.cellstorage.AppManager;
import com.cellstorage.UIHealper;
import com.cellstorage.net.WebClient;
import com.cellstorage.struct.UserInfo;
import com.cellstorage.utils.FastBlur;
import com.cellstorage.utils.parseXML;
import com.cellstorage.view.LoginView;
import com.cellstorage.view.LoginView.LoginViewListener;
import com.example.cellstorage.R;

@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements LoginViewListener{
	private AppContext appContext;
	public final int HANDLE_LOGIN = 1;
	private UserInfo mUserInfo;
	
	@ViewById(R.id.RelativeLayoutLogin)
	LoginView mRelativeLayoutLogin;
	@ViewById(R.id.RelativeLayoutlogo)
	RelativeLayout mRelativeLayoutlogo;
	
	//��ʼ������
	@AfterViews
	public void init(){
		appContext = (AppContext) getApplication();
		mRelativeLayoutLogin.setLoginViewListener(this);
		IntentFilter filter = new IntentFilter();
		filter.addAction(WebClient.INTERNAL_ACTION_LOGIN);
		appContext.registerReceiver(receiver, filter);
		appBlur();
	}
	public BroadcastReceiver receiver = new BroadcastReceiver() {
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
						mRelativeLayoutLogin.LoginFail();
					}
					else if(resXml.equals("null")){
						UIHealper.DisplayToast(appContext,"��½ʧ�ܣ����������Ƿ���ͨ��");
						mRelativeLayoutLogin.LoginFail();
					}
					else
					{	
						mUserInfo = UserInfo.getAppManager();
						parseXML.ConserveUserInfo(resXml, mUserInfo);
						mRelativeLayoutLogin.LoginSuccess();
						Message message = new Message();
						message.what = HANDLE_LOGIN;
						mHandler.sendMessage(message); 
					}
				}
			}
		};
	};
	public Handler mHandler=new Handler()  
	{  
		public void handleMessage(Message msg)  
		{  
			switch(msg.what)  
			{  
			case HANDLE_LOGIN:{
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, MainActivity_.class);
				startActivity(intent);
			}
				break;  
			default:  
				break;            
			}  
			super.handleMessage(msg);  
		}  
	}; 
	//�ѵ�¼�����ģ������
    private void appBlur(){
    	mRelativeLayoutlogo.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
            
            @Override
            public boolean onPreDraw() {
            	mRelativeLayoutlogo.getViewTreeObserver().removeOnPreDrawListener(this);
            	mRelativeLayoutlogo.buildDrawingCache();

                Bitmap bmp = mRelativeLayoutlogo.getDrawingCache();
                blur(bmp, mRelativeLayoutLogin);
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
		// TODO Auto-generated method stub
		if((username.equals("")) || (password.equals("")))
		{
			UIHealper.DisplayToast(appContext,"�û��������벻��Ϊ�գ�");
		}
		else 
		{
			WebClient client = WebClient.getInstance();
			Map<String,String> param = new HashMap<String, String>();
			param.put(username, password);	
			client.sendMessage(appContext, WebClient.Method_login, param);
		}
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
			AppManager.getAppManager().AppExit(this);
			super.onBackPressed();
		} else {
			UIHealper.DisplayToast(this, getString(R.string.exitAppHit));
		}
		firstTime = System.currentTimeMillis();
	}
	@Override
	public void OnSeekPassword() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(this, FindPwdActivity_.class);
		startActivity(intent);
	}
	protected void onDestroy() 
	{ 
		super.onDestroy();
	}
}