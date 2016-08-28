package com.smarthomecontroller;

import com.database.UserDAO;
import com.model.UserInformation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MyInfoActivity extends Activity{
	public  static final String FLAG = "id";		//定义一个常量，用来作为请求码
	public static final int REQUSET = 1;
	String strid,strid1;
	TextView txtUserid,txtUserName,txtFamilyId;
	Button btnUserEdit,btnUserCancel;
	UserDAO userdao = new UserDAO(MyInfoActivity.this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinfo);
		txtUserid = (TextView) findViewById(R.id.txtUserid);
		txtUserName = (TextView) findViewById(R.id.txtUserName);
		txtFamilyId = (TextView) findViewById(R.id.txtFamilyId);
		btnUserEdit = (Button) findViewById(R.id.btnUserEdit);
		btnUserCancel = (Button) findViewById(R.id.btnUserCancel);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();		//获取传入的数据，并使用Bundle记录
		strid = bundle.getString(MainActivityTab3.FLAG);
		UserInformation userinformation = userdao.find(Integer.parseInt(strid));
		strid1 = Integer.toString(userinformation.getid());
		txtUserid.setText(Integer.toString(userinformation.getid()));
		txtUserName.setText(userinformation.getusername());
		txtFamilyId.setText(Integer.toString(userinformation.getfamilyid()));
		btnUserEdit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyInfoActivity.this,EditUserinfoActivity.class);
				intent.putExtra(FLAG, strid1);
				startActivityForResult(intent, REQUSET);
			}
		});
		btnUserCancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == MyInfoActivity.REQUSET && resultCode == 1){
			onCreate(null);
		}
	}
}