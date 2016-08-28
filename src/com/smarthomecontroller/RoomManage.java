package com.smarthomecontroller;

import java.util.List;

import com.database.DevicesDAO;
import com.database.RoomDAO;
import com.model.Devices;
import com.model.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RoomManage extends Activity{
	public  static final String FLAG = "id";		//定义一个常量，用来作为请求码
	public static final int REQUSET = 1;
	TextView txtroomid;
	EditText txtroomname,txtroomtype;
	String strid,strFaid;
	Button btnroomdelete,btnroomedit,btnroomcancel,btnroomrefresh;
	ListView lvDevices;
	RoomDAO roomdao = new RoomDAO(RoomManage.this);
	//获取系统返回键，定义系统自带返回键功能
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event){
			if(keyCode == KeyEvent.KEYCODE_BACK&&event.getAction() == KeyEvent.ACTION_DOWN)
			{
				Intent intent=new Intent();
				setResult(1, intent);
				finish();
				return true;
			}
			return super.onKeyDown(keyCode, event);
			}
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roommanage);
		txtroomid = (TextView) findViewById(R.id.txtroomid);
		txtroomname = (EditText) findViewById(R.id.txtroomname);
		txtroomtype = (EditText) findViewById(R.id.txtroomtype);
		btnroomdelete = (Button) findViewById(R.id.btnroomdelete);
		btnroomedit = (Button) findViewById(R.id.btnroomedit);
		btnroomcancel = (Button) findViewById(R.id.btnroomcancel);
		btnroomrefresh = (Button) findViewById(R.id.btnroomrefresh);
		lvDevices = (ListView) findViewById(R.id.showdevicesStatsByRoom);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();		//获取传入的数据，并使用Bundle记录
		strid = bundle.getString(showRoomActivity.FLAG);
		Room room = roomdao.find(Integer.parseInt(strid));
		txtroomid.setText(Integer.toString(room.getroomid()));
		txtroomname.setText(room.getroomname());
		txtroomtype.setText(Integer.toString(room.getroomtype()));
		ShowInfo(room.getroomid(),room.getfamilyid());
		btnroomdelete.setOnClickListener(new OnClickListener(){	//为删除按钮设置监听事件

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				roomdao.delete(Integer.parseInt(strid));
				Toast.makeText(RoomManage.this, "【数据】删除成功！", Toast.LENGTH_SHORT).show();
				Intent intent=new Intent();
				setResult(1, intent);
				finish();
			}		
		});
		btnroomedit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Room room = new Room();
				room.setroomid(Integer.parseInt(strid));//设置id
				room.setroomname(txtroomname.getText().toString());//设置设备名称
				room.setroomtype(Integer.parseInt(txtroomtype.getText().toString()));	//设置设备位置
				roomdao.update(room);
			}
		});
		btnroomcancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				setResult(1, intent);
				finish();
			}
		});
		btnroomrefresh.setOnClickListener(new OnClickListener(){	//刷新整个Activity

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onCreate(null);		//暂时使用重写onCreate方法试一试能不能成功
			}
		});
		lvDevices.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				//记录单机的项信息
				String strInfo = String.valueOf(((TextView) view).getText());
				String strid = strInfo.substring(0, strInfo.indexOf('|'));//从信息项中截取id
				Intent intent = new Intent(RoomManage.this,DevicesManage1.class);
				intent.putExtra(FLAG, strid);
				startActivityForResult(intent, REQUSET);
			}
			
		});
	}
	private void ShowInfo(int location,int familyid){
		ArrayAdapter<String> arrayAdapter = null;
		String[] strInfos = null;
		int loc = location;
		int fam = familyid;
		DevicesDAO devicesinfo = new DevicesDAO(RoomManage.this);
		//初始化ListView
				List<Devices> listDevices = devicesinfo.getScrollDataByLocation(fam,
						loc,0,(int)devicesinfo.getCount1(location));
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
			recreate();
		}
	}
}
