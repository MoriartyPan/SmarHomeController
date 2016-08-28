package com.smarthomecontroller;

import com.database.IpDAO;
import com.model.IPInformation;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends Activity{
	EditText txtip;
	IpDAO ipdao = new IpDAO(SettingActivity.this);
	Button btnipedit,btnipcancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		txtip = (EditText) findViewById(R.id.txtip);
		btnipedit = (Button) findViewById(R.id.btnipedit);
		btnipcancel = (Button) findViewById(R.id.btnipcancel);
		IPInformation ipinformation = ipdao.find(1);
		txtip.setText(ipinformation.getip());
		btnipedit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(txtip.getText().toString().equals(""))
				{
					Toast.makeText(SettingActivity.this, "ip地址不能为空！", Toast.LENGTH_SHORT).show();
				}
				else
				{
					IPInformation ipinfo = new IPInformation();
					ipinfo.setip_id(1);
					ipinfo.setip(txtip.getText().toString());
					ipdao.update(ipinfo);
					Toast.makeText(SettingActivity.this, "ip地址修改成功！", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		});
		btnipcancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}