package com.smarthomecontroller;

import com.database.RoomDAO;
import com.model.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddRoomActivity extends Activity{
	String faid;
	EditText txtAddRoomName,txtAddRoomType;
	Button btnAddRoom,btnAddRoomCancel;
	RoomDAO roomdao = new RoomDAO(AddRoomActivity.this);
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addroom);
		txtAddRoomName = (EditText) findViewById(R.id.txtAddRoomName);
		txtAddRoomType = (EditText) findViewById(R.id.txtAddRoomType);
		btnAddRoom = (Button) findViewById(R.id.btnAddRoom);
		btnAddRoomCancel = (Button) findViewById(R.id.btnAddRoomCancel);
		btnAddRoom.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = getIntent();
				Bundle bundle = intent.getExtras();
				faid = bundle.getString(MainActivityTab1.FLAG);
				String strRoomname = txtAddRoomName.getText().toString().trim();
				String strRoomType = txtAddRoomType.getText().toString().trim();
				if(strRoomname.equals(""))
				{
					Toast.makeText(AddRoomActivity.this, "房间名称不能为空",
							Toast.LENGTH_SHORT).show();
				}
				else if(strRoomType.equals(""))
				{
					Toast.makeText(AddRoomActivity.this, "房间类型不能为空",
							Toast.LENGTH_SHORT).show();
				}
				else
				{
					int Type = Integer.parseInt(strRoomType);
					Room room = new Room(roomdao.getMaxId()+1,strRoomname,Type,Integer.parseInt(faid));
					roomdao.addRoom(room);
					Log.i("TAG", "添加房间成功");
					Toast.makeText(AddRoomActivity.this, "添加房间成功",
							Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		});
		btnAddRoomCancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}