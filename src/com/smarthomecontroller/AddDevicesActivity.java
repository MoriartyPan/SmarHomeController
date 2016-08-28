package com.smarthomecontroller;

import java.util.List;

import com.database.DevicesDAO;
import com.database.RoomDAO;
import com.model.Devices;
import com.model.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddDevicesActivity extends Activity{
	String faid;
	EditText txtAddDevices,txtAddLocation;
	//Switch switch2;
	Button btnAddDevices,btnAddCancel;
	ListView lvRoom;
	DevicesDAO devicesdao = new DevicesDAO(AddDevicesActivity.this);
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adddevices);
		txtAddDevices = (EditText) findViewById(R.id.txtAddDevices);
		txtAddLocation = (EditText) findViewById(R.id.txtAddLocation);
		//switch2 = (Switch) findViewById(R.id.switch2);
		btnAddDevices = (Button) findViewById(R.id.btnAddDevices);
		btnAddCancel = (Button) findViewById(R.id.btnAddCancel);
		lvRoom = (ListView) findViewById(R.id.showRoom1);
		ShowInfo();
		btnAddDevices.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = getIntent();
				Bundle bundle = intent.getExtras();
				faid = bundle.getString(MainActivityTab1.FLAG);
				String strDevicesname = txtAddDevices.getText().toString().trim();
				String strLocation = txtAddLocation.getText().toString().trim();
				if(strDevicesname.equals(""))
				{
					Toast.makeText(AddDevicesActivity.this, "设备名称不能为空",
							Toast.LENGTH_SHORT).show();
				}
				else if(strLocation.equals(""))
				{
					Toast.makeText(AddDevicesActivity.this, "设备位置不能为空",
							Toast.LENGTH_SHORT).show();
				}
				else
				{
					int Location = Integer.parseInt(strLocation);
					Devices devices = new Devices(devicesdao.getMaxId()+1,strDevicesname,Location,0,Integer.parseInt(faid));
					devicesdao.addDevices(devices);
					Log.i("TAG", "添加设备成功");
					Toast.makeText(AddDevicesActivity.this, "添加设备成功",
							Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		});
		btnAddCancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	private void ShowInfo(){
		ArrayAdapter<String> arrayAdapter = null;
		String[] strInfos = null;
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		faid = bundle.getString(MainActivityTab1.FLAG);
		RoomDAO roominfo = new RoomDAO(AddDevicesActivity.this);	//创建DevicesDAO对象
		List<Room> listRoom = roominfo.getScrollData(Integer.parseInt(faid),0,
				(int)roominfo.getCount(Integer.parseInt(faid)));
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
}