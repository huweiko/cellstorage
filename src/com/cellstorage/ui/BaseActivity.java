package com.cellstorage.ui;

import com.cellstorage.AppManager;
import com.cellstorage.custom.CustomProgressDialog;
import com.example.cellstorage.R;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity{
	private CustomProgressDialog mProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left); 
		//���Activity����ջ
		AppManager.getAppManager().addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//����Activity&�Ӷ�ջ���Ƴ�
		AppManager.getAppManager().removeActivity(this);
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right); 
	}
	protected void startProgressDialog(String message){
        if (mProgressDialog == null){
        	mProgressDialog = CustomProgressDialog.createDialog(this);
        	mProgressDialog.setMessage(message);
        }
        if(!isFinishing()){
        	mProgressDialog.show();	
        }
    }
     
	protected void stopProgressDialog(){
        if (mProgressDialog != null){
        	mProgressDialog.dismiss();
        	mProgressDialog = null;
        }
    }

}
