/*
 * �ļ�����LoadActivity.java
 * ���ܣ�Ӧ�ó�������ʱ�ļ��ؽ��棬�������
 * ���ߣ�huwei
 * ����ʱ�䣺2013-10-22
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