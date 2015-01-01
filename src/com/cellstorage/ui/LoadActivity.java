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

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import java.lang.Thread;
import com.cellstorage.AppContext;
import com.example.cellstorage.R;

import android.content.Intent;

public class LoadActivity extends BaseActivity 
{
	private AppContext appContext;
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        //ȥ����Ϣ��
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        appContext = (AppContext) getApplication();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);//ȥ����Ϣ��
        //Android�豸displayĬ���ǲ���16-bits color palette����ʾ������ɫ����˶��ڴ�alphaֵ��32λpngͼƬ�������ʾʧ�档
//      getWindow().setFormat(PixelFormat.RGBA_8888);
        setContentView(R.layout.activity_load);
        
        showDensity();
        
        new Thread()
        { 
			 public void run()
			 {
				try {
					sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				login_activity();
			}
        }.start(); 
    }

	private void showDensity() {
		/*��hdpi: 240 , ldpi: 120 , mdpi: 160 , xhdpi: 320����*/
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // ��Ļ��ȣ����أ�
        int height = metric.heightPixels;  // ��Ļ�߶ȣ����أ�
        float density = metric.density;  // ��Ļ�ܶȣ�0.75 / 1.0 / 1.5��
        int densityDpi = metric.densityDpi;  // ��Ļ�ܶ�DPI��120 / 160 / 240�� 
        String StrDensityDpi = null;
        switch(densityDpi){
        case 120:
        	StrDensityDpi = "ldpi";
        	break;
        case 160:
        	StrDensityDpi = "mdpi";
        	break;
        case 240:
        	StrDensityDpi = "hdpi";
        	break;
        case 320:
        	StrDensityDpi = "xhdpi";
        	break;
        	default:
        		StrDensityDpi = "null";
        		break;
        }
        Log.i("huwei","���豸��Ļ�ֱ���Ϊ��"+height+"*"+width);
        Log.i("huwei","��Ļ�ܶ�Ϊ��"+"("+density+"_"+densityDpi+")"+":"+StrDensityDpi);
	}

    /*
     * ���ܣ�������½����
     * ��������
     * ����ֵ�� ��
     * 
     * */
	public void login_activity()
	{
		/* �½�һ��Intent���� */
		Intent intent = new Intent();
		/* ָ��intentҪ�������� */
		intent.setClass(LoadActivity.this, MainActivity_.class);
		byte LoginStatus = 0;
		intent.putExtra("LoginStatus", LoginStatus);
		/* ����һ���µ�Activity */
		startActivity(intent);
		/* �رյ�ǰ��Activity */
		LoadActivity.this.finish();	
	}
	protected void onDestroy() 
	{
		super.onDestroy();
	}
}