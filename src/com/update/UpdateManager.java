package com.update;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.os.Looper;
import android.os.Message;

import com.smarthomecontroller.AboutappActivity;;

public class UpdateManager {
	private static UpdateManager manager = null;
	private UpdateManager(){};
	public static UpdateManager getInstance(){
		manager = new UpdateManager();
		return manager;
	}
	
	//获取版本号
	public int getVersion(Context context){
		int version = 0;
		try {
			version = context.getPackageManager().getPackageInfo("com.smarthomecontroller", 0).versionCode;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.print("获取版本号异常!");
		}
		return version;
	}
	
	//获取版本名
	public String getVersionName(Context context){
		String versionName = null;
		try {
			versionName = context.getPackageManager().getPackageInfo("com.smarthomecontroller", 0).versionName;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.print("获取版本名异常！");
		}
		return versionName;
	}
	
	//获取服务器版本号
	public String getServerVersion(){
		String serverJson = null;
		byte[] buffer = new byte[128];
		
		try {
			URL serverURL = new URL("http://192.168.137.1/ver.aspx");
			HttpURLConnection connect = (HttpURLConnection) serverURL.openConnection();
			BufferedInputStream bis = new BufferedInputStream(connect.getInputStream());
			int n = 0;
			while((n = bis.read(buffer))!= -1){
				serverJson = new String(buffer);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return serverJson;
	}
	
	//比较服务器版本与本地版本弹出对话框
	public boolean compareVersion(Context context){
		final Context contextTemp = context;
		
		new Thread(){
			public void run(){
				Looper.prepare();
				String serverJson = manager.getServerVersion();
				try{
					//解析Json数据
					JSONArray array = new JSONArray(serverJson);
					JSONObject object = array.getJSONObject(0);
					String getServerVersion = object.getString("version");
					String getServerVersionName = object.getString("versionName");
					
					AboutappActivity.serverVersion = Integer.parseInt(getServerVersion);
					AboutappActivity.serverVersionName = getServerVersionName;
					
					if(AboutappActivity.version < AboutappActivity.serverVersion){
						//弹出一个对话框
						AlertDialog.Builder builder = new Builder(contextTemp);
						builder.setTitle("版本更新");
						builder.setMessage("当前版本："+AboutappActivity.versionName+
								"\n"+"服务器版本："+AboutappActivity.serverVersionName);
						builder.setPositiveButton("立即更新",new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int arg1) {
								// TODO Auto-generated method stub
								//开启线程下载apk
								new Thread(){
									public void run(){
										Looper.prepare();
										downloadApkFile(contextTemp);
										Looper.loop();
									};
								}.start();
							}
						});
						builder.setNegativeButton("下次再说", null);
						builder.show();
					}
					else{
						AlertDialog.Builder builder = new Builder(contextTemp);
						builder.setTitle("版本信息");
						builder.setMessage("当前已是最新版本");
						builder.setPositiveButton("确定", null);
						builder.show();
					}
				}
				catch(JSONException e){
					e.printStackTrace();
					System.out.println("获取服务器版本线程异常！"+e);
				}
				
				Looper.loop();
			};
		}.start();
		return false;
	}
	
	//下载apk文件
	public void downloadApkFile(Context context){
		String savePath = Environment.getExternalStorageDirectory()+"/SmartHomeControlle.apk";
		String serverFilePath = "http://192.168.137.1/SmartHomeController.png";
		try {
			if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){  
				URL serverURL = new URL(serverFilePath);
				HttpURLConnection connect = (HttpURLConnection) serverURL.openConnection();
				BufferedInputStream bis = new BufferedInputStream(connect.getInputStream());
				File apkfile = new File(savePath);
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(apkfile));
				
				int fileLength = connect.getContentLength();
				int downLength = 0;
				int progress = 0;
				int n;
				byte[] buffer = new byte[1024];
				while((n=bis.read(buffer, 0, buffer.length))!=-1){
					bos.write(buffer, 0, n);
					downLength +=n;
					progress = (int) (((float) downLength / fileLength) * 100);
					Message msg = new Message();
					msg.arg1 = progress;
					AboutappActivity.handler.sendMessage(msg);
				}
				bis.close();
				bos.close();
				connect.disconnect();
	        } 
			
		} catch (Exception e) {
			System.out.println("下载出错！"+e);
		}

	}
}
