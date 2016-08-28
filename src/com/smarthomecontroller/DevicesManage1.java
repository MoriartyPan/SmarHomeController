package com.smarthomecontroller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.database.DevicesDAO;
import com.database.IpDAO;
import com.model.Devices;
import com.model.IPInformation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class DevicesManage1 extends Activity{
	TextView txtdevicesid1;
	EditText txtdevicesname1,txtlocation1;
	Switch switch11;
	String strid;
	Button btndevicesdelete1,btndevicesedit1,btndevicescancel1,btndevicesrefresh1;
	DevicesDAO devicesdao = new DevicesDAO(DevicesManage1.this);
	Handler handler = new Handler();
	String msg = "";
	String ip = "";
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
		setContentView(R.layout.activity_devicesmanage1);
		final RunnableThread runnable = new RunnableThread();
		txtdevicesid1 = (TextView) findViewById(R.id.txtdevicesid1);
		txtdevicesname1 = (EditText) findViewById(R.id.txtdevicesname1);
		txtlocation1 = (EditText) findViewById(R.id.txtlocation1);
		switch11 = (Switch) findViewById(R.id.switch11);
		btndevicesdelete1 = (Button) findViewById(R.id.btndevicesdelete1);
		btndevicesedit1 = (Button) findViewById(R.id.btndevicesedit1);
		btndevicescancel1 = (Button) findViewById(R.id.btndevicescancel1);
		btndevicesrefresh1 = (Button) findViewById(R.id.btndevicesrefresh1);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();		//获取传入的数据，并使用Bundle记录
		strid = bundle.getString(showDevicesStatsActivity.FLAG);
		Devices devices = devicesdao.find(Integer.parseInt(strid));
		txtdevicesid1.setText(Integer.toString(devices.getdevicesid()));
		txtdevicesname1.setText(devices.getdevicesname());
		txtlocation1.setText(Integer.toString(devices.getlocation()));
		IpDAO ipdao = new IpDAO(DevicesManage1.this);
		IPInformation ipinformation = ipdao.find(1);
		ip = ipinformation.getip();
		//根据status的值设置switch的状态，1为开，0为关
		if(devices.getstatus()==1)
		{
			switch11.setChecked(true);
		}
		else
		{
			switch11.setChecked(false);
		}
		switch11.setOnCheckedChangeListener(new OnCheckedChangeListener(){	//为switch1设置监听事件
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Devices devices = new Devices();
				if(isChecked)
				{
					//设置数据库Devices表中的status字段为1
					devices.setdevicesid(Integer.parseInt(strid));//设置id
					devices.setdevicesname(txtdevicesname1.getText().toString());//设置设备名称
					devices.setlocation(Integer.parseInt(txtlocation1.getText().toString()));	//设置设备位置
					devices.setstatus(1);
					msg = txtdevicesname1.getText().toString()+"状态更改为：开";
					new Thread(runnable).start();
					devicesdao.update(devices);
				}
				else
				{
					//设置数据库Devices表中的status字段为0
					devices.setdevicesid(Integer.parseInt(strid));//设置id
					devices.setdevicesname(txtdevicesname1.getText().toString());//设置设备名称
					devices.setlocation(Integer.parseInt(txtlocation1.getText().toString()));	//设置设备位置
					devices.setstatus(0);
					msg = txtdevicesname1.getText().toString()+"状态更改为：关";
					new Thread(runnable).start();
					devicesdao.update(devices);
				}
			}
		});
		btndevicesdelete1.setOnClickListener(new OnClickListener(){	//为删除按钮设置监听事件

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				devicesdao.delete(Integer.parseInt(strid));
				Toast.makeText(DevicesManage1.this, "【数据】删除成功！", Toast.LENGTH_SHORT).show();
				Intent intent=new Intent();
				setResult(1, intent);
				finish();
			}		
		});
		btndevicesedit1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Devices devices = new Devices();
				devices.setdevicesid(Integer.parseInt(strid));//设置id
				devices.setdevicesname(txtdevicesname1.getText().toString());//设置设备名称
				devices.setlocation(Integer.parseInt(txtlocation1.getText().toString()));	//设置设备位置
				if(switch11.isChecked()==true)
				{
					devices.setstatus(1);
				}
				else
				{
					devices.setstatus(0);
				}
				devicesdao.update(devices);
			}
		});
		btndevicescancel1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				setResult(1, intent);
				finish();
			}
		});
		btndevicesrefresh1.setOnClickListener(new OnClickListener(){	//刷新整个Activity

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onCreate(null);		//暂时使用重写onCreate方法试一试能不能成功
			}
		});
		
	}
	//子线程
	public class RunnableThread implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Socket socket = null;
			String message = null;
			try{
				socket = new Socket(ip,8888);
				PrintWriter out = new PrintWriter(new BufferedWriter(new 
						OutputStreamWriter(socket.getOutputStream())),true);
				out.println(msg);
				out.flush();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				message = in.readLine();
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
