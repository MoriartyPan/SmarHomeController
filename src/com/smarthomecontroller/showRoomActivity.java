package com.smarthomecontroller;

import java.util.List;

import com.database.RoomDAO;
import com.model.Room;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;

public class showRoomActivity extends Activity{
	public static final String FLAG = "id";		//定义一个常量，用来作为请求码
	public static final int REQUSET = 1;
	ListView lvRoom;
	String strFaid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showroom);		//显示id为activity_signin的activity
		lvRoom = (ListView) findViewById(R.id.showRoom);
		ShowInfo();
		lvRoom.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				//记录单机的项信息
				String strInfo = String.valueOf(((TextView) view).getText());
				String strid = strInfo.substring(0, strInfo.indexOf('|'));//从信息项中截取id
				Intent intent = new Intent(showRoomActivity.this,RoomManage.class);
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
		RoomDAO roominfo = new RoomDAO(showRoomActivity.this);	//创建DevicesDAO对象
		List<Room> listRoom = roominfo.getScrollData(Integer.parseInt(strid),0,
				(int)roominfo.getCount(Integer.parseInt(strid)));
		strInfos = new String[listRoom.size()];
		int m=0;				//定义一个开始标识
		for(Room room:listRoom){		// 遍历List泛型
			//将设备相关信息组合成一个字符串，存储到字符串数组的相应位置
			strInfos[m] = room.getroomid() + "|" + room.getroomname();
			m++;
		}
		//使用字符串初始化ArrayAdapter对象
		arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
				strInfos);
		lvRoom.setAdapter(arrayAdapter);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == showRoomActivity.REQUSET && resultCode == 1){
			ShowInfo();
		}
	}
}