package com.cellstorage.view;

import com.example.cellstorage.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.EditText;
import android.widget.RelativeLayout;
 
public class LoginView extends RelativeLayout{
	private Context mContext;
	private View mLogin;
	private SharedPreferences sp;//保存用户名和密码
	private EditText mUserName;
	private EditText mPassWord;
	private CheckBox m_cb_autoLogin;//记住密码选框
	private ProgressDialog m_pDialog;
	private Button mButtonLogin;
	private Button mButtonForgetPwd;
	private LoginViewListener mLoginViewListener;
	
	public LoginView(Context context) {
		super(context);
		mContext = context;
		Init(context);
		// TODO Auto-generated constructor stub
	}
	public LoginView(Context context, AttributeSet attrs) {
		super(context,attrs);
		mContext = context;
		Init(context);
		// TODO Auto-generated constructor stub
	}
	public LoginView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
		mContext = context;
		Init(context);
	}
	
	private void Init(Context context){
		mLogin = inflate(context, R.layout.view_login, null);
		RelativeLayout.LayoutParams lpLl = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lpLl.addRule(CENTER_IN_PARENT);
		mLogin.setLayoutParams(lpLl);
		addView(mLogin);
		mUserName = (EditText) mLogin.findViewById(R.id.username);
		mPassWord = (EditText) mLogin.findViewById(R.id.password);
		mButtonLogin = (Button) mLogin.findViewById(R.id.login);
		mButtonForgetPwd = (Button) mLogin.findViewById(R.id.ButtonForgetPwd);
		m_cb_autoLogin = (CheckBox) mLogin.findViewById(R.id.cb_autoLogin);
		sp = mContext.getSharedPreferences(mContext.getString(R.string.userInfo), Context.MODE_PRIVATE);
		mUserName.setText(sp.getString(mContext.getString(R.string.userName), ""));  
		mPassWord.setText(sp.getString(mContext.getString(R.string.passWord), ""));  
		
	    mButtonLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String uname = mUserName.getText().toString();
				String password = mPassWord.getText().toString();
				mButtonLogin.setText(mContext.getString(R.string.loginingText));
				if(mLoginViewListener != null){
					mLoginViewListener.OnClickLogin(uname, password);
				}
			}
		});
	    mButtonForgetPwd.setOnClickListener(new OnClickListener() {
	    	
	    	@Override
	    	public void onClick(View v) {
	    		// TODO Auto-generated method stub
				if(mLoginViewListener != null){
					mLoginViewListener.OnSeekPassword();
				}
	    	}
	    });
        //监听记住密码多选框按钮事件   
	    m_cb_autoLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
                if (m_cb_autoLogin.isChecked()) {  
                      
                    sp.edit().putBoolean(mContext.getString(R.string.isAutoLogin), true).commit();  
                      
                }else {  
                      
                    sp.edit().putBoolean(mContext.getString(R.string.isAutoLogin), false).commit();  
                      
                }  
  
            }  
        });  
	}
	//保存用户名和密码
	protected void onSaveContent() 
	{
		String usernameContent = mUserName.getText().toString();
		String passwordContent = mPassWord.getText().toString();
		sp.edit().putString(mContext.getString(R.string.userName), usernameContent).commit();
		sp.edit().putString(mContext.getString(R.string.passWord), passwordContent).commit();
	}
	//清除用户名和密码
	protected void onClearContent() 
	{
		sp.edit().putString(mContext.getString(R.string.userName), "").commit();
		sp.edit().putString(mContext.getString(R.string.passWord), "").commit();
	}
	public void setLoginViewListener(LoginViewListener l){
		mLoginViewListener = l;
		//判断记住密码多选框的状态   
	    if(sp.getBoolean(mContext.getString(R.string.isAutoLogin), false)){
	    	m_cb_autoLogin.setChecked(true); 
			String uname = mUserName.getText().toString();
			String password = mPassWord.getText().toString();
			mButtonLogin.setText(mContext.getString(R.string.loginingText));
			if(mLoginViewListener != null){
				mLoginViewListener.OnClickLogin(uname, password);
			}
	    }
	}
	public interface LoginViewListener{
		public void OnClickLogin(String username,String password);
		public void OnSeekPassword();
	}
	public void LoginSuccess(){
		mButtonLogin.setText(mContext.getString(R.string.loginText));
		onSaveContent();
	}
	public void LoginFail(){
		mButtonLogin.setText(mContext.getString(R.string.loginText));
	}
}