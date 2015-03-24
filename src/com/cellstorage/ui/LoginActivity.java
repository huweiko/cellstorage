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
	
	//初始化函数
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
						UIHealper.DisplayToast(appContext,"用户名和密码错误！");
						mRelativeLayoutLogin.LoginFail();
					}
					else if(resXml.equals("null")){
						UIHealper.DisplayToast(appContext,"登陆失败，请检查网络是否连通！");
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
	//把登录框进行模糊处理
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
    
    //模糊处理
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
			UIHealper.DisplayToast(appContext,"用户名或密码不能为空！");
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
	 * 连续按两次返回键就退出
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (firstTime + 2000 > System.currentTimeMillis()) {
			Log.i("huwei", getPackageName()+"程序退出！");
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