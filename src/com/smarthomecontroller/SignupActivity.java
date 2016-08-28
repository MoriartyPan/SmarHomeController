package com.smarthomecontroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.model.IPInformation;
import com.model.UserInformation;
import com.database.IpDAO;
//import com.database.SigninDAO;
import com.database.SignupDAO;

public class SignupActivity extends Activity{
	EditText AccountEdittext,PasswordEdittext,FamilyidEdittext,txtipset;
	Button Sign_up,cancel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);		//显示id为activity_signup的activity
		initView();			//初始化Activity函数
	}
	public void initView(){
		AccountEdittext = (EditText) findViewById(R.id.AccountEdittext);	//获取用户名文本框
		PasswordEdittext = (EditText) findViewById(R.id.PasswordEdittext);	//获取密码文本框
		FamilyidEdittext = (EditText) findViewById(R.id.FamilyidEdittext);	//获取家庭码文本框
		txtipset = (EditText) findViewById(R.id.txtipset);					//获取ip设置文本框
		Sign_up = (Button) findViewById(R.id.Sign_up);	//获取注册按钮
		cancel = (Button) findViewById(R.id.cancel);	//获取登录按钮
		//注册按钮监听
		Sign_up.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent intent = new Intent(SignupActivity.this,SignActivity.class);
				String username = AccountEdittext.getText().toString().trim();
				String password = PasswordEdittext.getText().toString().trim();
				String sfamilyid = FamilyidEdittext.getText().toString().trim();
				int familyid = 0;
				if(sfamilyid.isEmpty())
					{
						Toast.makeText(SignupActivity.this, "家庭码不能为空！",
							Toast.LENGTH_SHORT).show();
					}
				else
					{
						familyid = Integer.parseInt(sfamilyid);
						SignupDAO signupdao = new SignupDAO(SignupActivity.this);
						IpDAO ipdao = new IpDAO(SignupActivity.this);
						//判断用户名、密码是否为空
						if(checkEdit())
						{	
							//判断用户名是否存在
							if(signupdao.existUsername(username)==true){
								//提示存在相同用户名
								Log.i("TAG", "存在相同的用户名！");
								Toast.makeText(SignupActivity.this, "存在相同的用户名！",
										Toast.LENGTH_SHORT).show();
							}
							else{
								UserInformation userinformation = new UserInformation(signupdao.getMaxId()+1,
										username,password,familyid);
								signupdao.addUser(userinformation);
								if(txtipset.getText().toString().equals(""))
								{
									String strIp = "null";
									IPInformation ipinfo = new IPInformation(ipdao.getMaxId()+1,strIp);
									ipdao.add(ipinfo);
								}
								else
								{
									IPInformation ipinfo = new IPInformation(ipdao.getMaxId()+1,txtipset.getText().toString());
									ipdao.add(ipinfo);
								}
								Log.i("TAG", "注册成功");
								Toast.makeText(SignupActivity.this, "注册成功！",
										Toast.LENGTH_SHORT).show();
								finish();
							}
						}
						else 
						{
							//提示用户名、密码、家庭码均不能为空
							Toast.makeText(SignupActivity.this, "用户名、密码、家庭码均不能为空",
									Toast.LENGTH_SHORT).show();
						}
					}
			}
		});
		//取消按钮监听
		cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	private boolean checkEdit(){
		if(AccountEdittext.getText().toString().trim().equals(""))
			return false;
		else if(PasswordEdittext.getText().toString().trim().equals(""))
			return false;
		else
			return true;
	}
}
