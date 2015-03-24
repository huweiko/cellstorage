package com.cellstorage.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import com.cellstorage.UIHealper;
import com.cellstorage.adapter.HelpListViewAdapter.OnItemClickClass;
import com.example.cellstorage.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;


@SuppressLint("NewApi") public class ReportDownloadManager
{
	private static final int DOWNLOAD = 1;
	private static final int DOWNLOAD_FINISH = 2;
	private static final int DOWNLOAD_NOT_REPORT = 3;
	private static final int DOWNLOAD_SHOW_DIALOG = 4;
	private String mSavePath;
	private int progress;
	private boolean cancelUpdate = false;

	private Context mContext;
	private ProgressBar mProgress;
	private static Dialog mDownloadDialog;
	private static Dialog noticeDialog;
	private String mServiceId;
	private downloadApkThread mDownloadApkThread;
	//当前合同号
	private String mCurrentContractNO = "";
	
	private final String mHttpUrl = "http://58.64.139.239:9102/cell-ws/uploadFile.do?serviceId=";  
	private String mFileName = "样本检测报告-";  
	
	OnClickReport onItemClickClass;
	@SuppressLint("HandlerLeak") private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case DOWNLOAD:
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				getPictureFileIntent();
				break;
			case DOWNLOAD_NOT_REPORT:
				UIHealper.DisplayToast(mContext, mContext.getString(R.string.string_not_check_report));
				break;
			case DOWNLOAD_SHOW_DIALOG:
				showNoticeDialog();
				break;
			default:
				break;
			}
		};
	};

	public ReportDownloadManager(Context context,String serviceId,String ContractNO)
	{
		this.mContext = context;
		this.mServiceId = serviceId;
		this.mCurrentContractNO = ContractNO;
		mFileName = mFileName + mCurrentContractNO;
	}


	public void getCheckReport(){
		new Thread(){
			public void run(){
				try {
					if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
					{
						String sdpath = Environment.getExternalStorageDirectory() + "/";
						mSavePath = sdpath + "download";
						URL url = new URL(mHttpUrl+mServiceId);
			
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.connect();
						File file = new File(mSavePath);
						if (!file.exists())
						{
							file.mkdir();
						}
						//获取所有响应头字段
					    String fileName = conn.getHeaderField("Content-Disposition");
					    if(fileName == null){
					    	mHandler.sendEmptyMessage(DOWNLOAD_NOT_REPORT);
					    	return;
					    }
					    conn.disconnect();
					    String ContentDisposition =  URLDecoder.decode(fileName,"UTF-8");
					    String [] strs = ContentDisposition.split("fileName=");
					    String [] strs1 = strs[1].split("[.]");
					    String mExtensions = strs1[1];
						mFileName = mFileName+"."+mExtensions;
						File apkFile = new File(mSavePath, mFileName);
						if (apkFile.exists())
						{
							getPictureFileIntent();
							return;
						}
						mHandler.sendEmptyMessage(DOWNLOAD_SHOW_DIALOG);
						

					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		
		
	}
	private void showNoticeDialog()
	{

		AlertDialog.Builder builder;
		if(Build.VERSION.SDK_INT < 11){
			builder = new Builder(mContext);
		}else{
			builder = new Builder(mContext,R.style.dialog);
		}
		builder.setTitle(R.string.download_check_report);
		builder.setPositiveButton(R.string.string_download, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				showDownloadDialog();
			}
		});
		builder.setNegativeButton(R.string.soft_update_cancel, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		if(noticeDialog == null){
			noticeDialog = builder.create();
			noticeDialog.setCanceledOnTouchOutside(false);
			noticeDialog.show();
		}else{
			if(!noticeDialog.isShowing()){
				noticeDialog = builder.create();
				noticeDialog.setCanceledOnTouchOutside(false);
				noticeDialog.show();
			}
		}

	}

	private void showDownloadDialog()
	{
		AlertDialog.Builder builder;
		if(Build.VERSION.SDK_INT < 11){
			builder = new Builder(mContext);
		}else{
			builder = new Builder(mContext,R.style.dialog);
		}
		builder.setTitle(R.string.string_downloading);
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.softupdate_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		builder.setView(v);
		builder.setNegativeButton(R.string.soft_update_cancel, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				cancelUpdate = true;
			}
		});
		mDownloadDialog = builder.create();
		mDownloadDialog.setCanceledOnTouchOutside(false);
		mDownloadDialog.show();
		downloadApk();
	}
	private void downloadApk()
	{
		mDownloadApkThread = new downloadApkThread();
		mDownloadApkThread.start();
	}

	private class downloadApkThread extends Thread
	{
		@Override
		public void run()
		{
			try
			{
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				{
					URL url = new URL(mHttpUrl+mServiceId);
					
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();

					File apkFile = new File(mSavePath, mFileName);
					int length = conn.getContentLength();
					
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					byte buf[] = new byte[1024];
					do
					{
						int numread = is.read(buf);
						count += numread;
						progress = (int) (((float) count / length) * 100);
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0)
						{
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);
					fos.close();
					is.close();
					conn.disconnect();
				}
			} catch (MalformedURLException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				mHandler.sendEmptyMessage(DOWNLOAD_NOT_REPORT);
				e.printStackTrace();
			}
			mDownloadDialog.dismiss();
		}
	};
    //Android获取一个用于打开Word文件的intent     
	private void getWordFileIntent(){    
  
        Intent intent = new Intent("android.intent.action.VIEW");     
        intent.addCategory("android.intent.category.DEFAULT");     
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);     
        Uri uri = Uri.fromFile(new File(mSavePath, mFileName));     
        intent.setDataAndType(uri, "application/msword");  
        mContext.startActivity(intent);   
    } 
	//Android获取一个用于打开图片文件的intent     
	private void getPictureFileIntent(){    
		
		Intent intent = new Intent("android.intent.action.VIEW");     
		intent.addCategory("android.intent.category.DEFAULT");     
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);     
		Uri uri = Uri.fromFile(new File(mSavePath, mFileName));     
		intent.setDataAndType(uri, "image/*");     
		mContext.startActivity(intent);   
	} 
	public interface OnClickReport{
		public void OnItemClick(String fileName);
	}
	public void setOnItemClickClassListenr(OnClickReport x_OnItemClickClass){
		onItemClickClass = x_OnItemClickClass;
	}
	
}
