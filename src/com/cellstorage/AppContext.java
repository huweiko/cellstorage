package com.cellstorage;

import com.example.cellstorage.R;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
public class AppContext extends Application{ 
	public String ApplicationName;
    @Override 
    public void onCreate() { 
        // TODO Auto-generated method stub 
        super.onCreate(); 
        ApplicationName = getString(R.string.app_name);
		
    } 
    /*
     * ��ȡӦ�ð汾��
     * return ���ذ汾��
     * */
	public String getVersionName() throws Exception
	{
       // ��ȡpackagemanager��ʵ��
       PackageManager packageManager = getPackageManager();
       // getPackageName()���㵱ǰ��İ�����0�����ǻ�ȡ�汾��Ϣ
       PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
       String version = packInfo.versionName;
       return version;
	}
}
