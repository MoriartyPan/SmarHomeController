package com.smarthomecontroller;

import java.io.File;

import com.update.UpdateManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class AboutappActivity extends Activity{
	public static int version,serverVersion;
	public static String versionName,serverVersionName,downloadResult;
	private LinearLayout LinearLayout_back,LinearLayout_appupdate;
	public static receiveVersionHandler handler;
	private ProgressBar proBar;
	private UpdateManager manager = UpdateManager.getInstance();
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutapp);
		LinearLayout_back = (LinearLayout) findViewById(R.id.LinearLayout_back);
		LinearLayout_appupdate = (LinearLayout) findViewById(R.id.LinearLayout_appupdate);
		proBar = (ProgressBar) findViewById(R.id.progressBar_id);
		Context c = this;
		version = manager.getVersion(c);
		versionName = manager.getVersionName(c);
		handler = new receiveVersionHandler();
		LinearLayout_appupdate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				manager.compareVersion(AboutappActivity.this);
			}
			
		});
		LinearLayout_back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	public class receiveVersionHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
		   proBar.setProgress(msg.arg1);
		   proBar.setVisibility(R.id.LinearLayout_appupdate);
	       if(msg.arg1 == 100){
	    	   Intent intent = new Intent(Intent.ACTION_VIEW); 
		       String path = Environment.getExternalStorageDirectory()+"/SmartHomeController.apk";
		       intent.setDataAndType(Uri.fromFile(new File(path)), 
		    		   "application/vnd.android.package-archive");
		       startActivity(intent);
	       }
	       proBar.setVisibility(R.id.LinearLayout_appupdate);
		}
	}
}