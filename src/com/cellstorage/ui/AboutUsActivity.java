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


import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;


import com.example.cellstorage.R;

@EActivity(R.layout.activity_about_us)
public class AboutUsActivity extends BaseActivity 
{
	@Click(R.id.ImageButtonAboutUsBack)
	void OnClickImageButtonAboutUsBack(){
		super.finish();
	}
	protected void onDestroy() 
	{
		super.onDestroy();
	}
}