package com.smarthomecontroller;

import java.util.List;

import com.database.DevicesDAO;
import com.model.Devices;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;

public class showDevicesStatsActivity extends Activity{
	public  static final String FLAG = "id";		//定义一个常量，用来作为请求码
	public static final int REQUSET = 1;
	ListView lvDevices;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showdevices);		//显示id为activity_signin的activity
		lvDevices = (ListView) findViewById(R.id.showdevicesStats);
		ShowInfo();
		lvDevices.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				//记录单机的项信息
				String strInfo = String.valueOf(((TextView) view).getText());
				String strid = strInfo.substring(0, strInfo.indexOf('|'));//从信息项中截取id
				Intent intent = new Intent(showDevicesStatsActivity.this,DevicesManage.class);
				intent.putExtra(FLAG, strid);
				startActivityForResult(intent, REQUSET);
			}
			
		});
	}
	private void ShowInfo(){
		ArrayAdapter<String> arrayAdapter = null;
		String[] strInfos = null;
		Intent intent = getIntent();
		String strid;
		Bundle bundle = intent.getExtras();		//获取传入的数据，并使用Bundle记录
		strid = bundle.getString(MainActivityTab1.FLAG);
		DevicesDAO devicesinfo = new DevicesDAO(showDevicesStatsActivity.this);	//创建DevicesDAO对象
		//List<Devices> listDevices = devicesinfo.getScrollData(0, (int)devicesinfo.getCount());
		List<Devices> listDevices = devicesinfo.getScrollData(Integer.parseInt(strid),0,
				(int)devicesinfo.getCount(Integer.parseInt(strid)));
		strInfos = new String[listDevices.size()];
		int m=0;				//定义一个开始标识
		for(Devices devices:listDevices){		// 遍历List泛型
			//将设备相关信息组合成一个字符串，存储到字符串数组的相应位置
			strInfos[m] = devices.getdevicesid() + "|" + devices.getdevicesname();
			m++;
		}
		//使用字符串初始化ArrayAdapter对象
		arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
				strInfos);
		lvDevices.setAdapter(arrayAdapter);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == showDevicesStatsActivity.REQUSET && resultCode == 1){
			ShowInfo();
		}
	}
}