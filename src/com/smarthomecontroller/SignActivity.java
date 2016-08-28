package com.smarthomecontroller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.database.IpDAO;
//import com.database.DBManager;
import com.database.SigninDAO;
import com.database.UserDAO;
import com.model.IPInformation;
import com.smarthomecontroller.MainActivity;
import com.smarthomecontroller.SignupActivity;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.content.Context;
import android.content.Intent;
//import android.view.Window;
//import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class SignActivity extends Activity{
	//private DBManager smc;				//定义一个DBManager
	EditText accountEdittext,passwordEdittext;
	Button sign_up,sign_in,ip_set;
	public static final String FLAG = "id";
	public static final String FAMILYID = "faid";
	Handler handler = new Handler();
	String msg = "";
	String ip = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);		//显示id为activity_signin的activity
		//初始化Database
		//smc = new DBManager(this);
		initView();			//初始化Activity函数
	}
	
/*	protected void onDestroy(){
		super.onDestroy();
		//应用的最后一个Activity关闭时释放Database
		smc.closeDB();
	}
*/
	public void initView(){								//具体函数定义与实现
		final RunnableThread runnable = new RunnableThread();
		accountEdittext = (EditText) findViewById(R.id.accountEdittext);	//获取用户名文本框
		passwordEdittext = (EditText) findViewById(R.id.passwordEdittext);	//获取密码文本框
		sign_up = (Button) findViewById(R.id.sign_up);	//获取注册按钮
		sign_in = (Button) findViewById(R.id.sign_in);	//获取登录按钮
		ip_set = (Button) findViewById(R.id.ip_set);	//获取ip设置按钮
		//登录按钮监听
		sign_in.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SignActivity.this,MainActivity.class);
				String name = accountEdittext.getText().toString();
				String password = passwordEdittext.getText().toString();
				SigninDAO signindao = new SigninDAO(SignActivity.this);
				UserDAO userdao = new UserDAO(SignActivity.this);
				IpDAO ipdao = new IpDAO(SignActivity.this);
				IPInformation ipinformation = ipdao.find(1);
				ip = ipinformation.getip();
				boolean flag = signindao.Signin(name,password);
				new Thread(runnable).start();
				try{
					Thread.currentThread();
					Thread.sleep(200);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				if(flag && msg.equals("1") ){
					//后期加上和服务器的连接 如果服务器在线进行跳转主页面，不在线提示服务器不在线
					int intid,intfaid;
					String strid,strfaid;
					intid = signindao.findid(name).getid();
					intfaid = userdao.findfamilyid(signindao.findid(name).getid());
					strid = Integer.toString(intid);
					strfaid = Integer.toString(intfaid);
					intent.putExtra(FLAG, strid);
					intent.putExtra(FAMILYID, strfaid);
					startActivity(intent);	//跳转到主页面
				}else if(!(msg.equals("1"))){
					Toast.makeText(SignActivity.this, "服务器不在线",Toast.LENGTH_SHORT).show();
				}
				else{
					Log.i("TAG", "用户名或密码错误");
					Toast.makeText(SignActivity.this, "用户名或密码错误！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		//注册按钮监听
		sign_up.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SignActivity.this,SignupActivity.class);
				startActivity(intent);
			}
			
		});
		ip_set.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SignActivity.this,SettingActivity.class);
				startActivity(intent);
				recreate();
			}
		});
	}
	//子线程
	public class RunnableThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Socket socket = null;
			String message = "请求登录";
			try{
				socket = new Socket(ip,8888);
				PrintWriter out = new PrintWriter(new BufferedWriter(new 
						OutputStreamWriter(socket.getOutputStream())),true);
				out.println(message);
				out.flush();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				msg = in.readLine();
				out.close();
				in.close();
			}catch(UnknownHostException e){
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				try{
					if(message == null){
						socket.close();
					}
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		
	}
}