package com.smarthomecontroller;

import com.database.UserDAO;
import com.model.UserInformation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditUserinfoActivity extends Activity{
	String strid;
	TextView txtuserid;
	EditText txtusername,txtpassword,txtfamilyid;
	Button btnuserinfoedit,btnuserinfocancel;
	UserDAO userdao = new UserDAO(EditUserinfoActivity.this);
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
		setContentView(R.layout.activity_edituserinfo);
		txtuserid = (TextView) findViewById(R.id.txtuserid);
		txtusername = (EditText) findViewById(R.id.txtusername);
		txtpassword = (EditText) findViewById(R.id.txtpassword);
		txtfamilyid = (EditText) findViewById(R.id.txtfamilyid);
		btnuserinfoedit = (Button) findViewById(R.id.btnuserinfoedit);
		btnuserinfocancel = (Button) findViewById(R.id.btnuserinfocancel);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();		//获取传入的数据，并使用Bundle记录
		strid = bundle.getString(MyInfoActivity.FLAG);
		UserInformation userinformation = userdao.find(Integer.parseInt(strid));
		txtuserid.setText(Integer.toString(userinformation.getid()));
		txtusername.setText(userinformation.getusername());
		txtpassword.setText(userinformation.getpassword());
		txtfamilyid.setText(Integer.toString(userinformation.getfamilyid()));
		btnuserinfoedit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UserInformation userinfo = new UserInformation();
				if(txtusername.getText().toString().equals("")){
					Toast.makeText(EditUserinfoActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
				}
				else if(txtpassword.getText().toString().equals("")){
					Toast.makeText(EditUserinfoActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
				}
				else if(txtfamilyid.getText().toString().equals("")){
					Toast.makeText(EditUserinfoActivity.this, "家庭码不能为空", Toast.LENGTH_SHORT).show();
				}
				else{
					userinfo.setid(Integer.parseInt(strid));
					userinfo.setusername(txtusername.getText().toString());
					userinfo.setpassword(txtpassword.getText().toString());
					userinfo.setfamilyid(Integer.parseInt(txtfamilyid.getText().toString()));
					userdao.update(userinfo);
					Toast.makeText(EditUserinfoActivity.this, "信息修改成功", Toast.LENGTH_SHORT).show();
					Intent intent=new Intent();
					setResult(1, intent);
					finish();
				}
			}
		});
		btnuserinfocancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				setResult(1, intent);
				finish();
			}
		});
	}
}