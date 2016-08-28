package com.smarthomecontroller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivityTab3 extends Fragment{
	public  static final String FLAG = "id";
	String strid;
	private LinearLayout myinfo_layout;
	private LinearLayout aboutapp_layout;
	private LinearLayout setting_layout;
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.activity_main_me, container, false);
		Bundle bundle = getArguments();
		strid = bundle.getString(MainActivity.FLAG);
		myinfo_layout = (LinearLayout) view.findViewById(R.id.myinfo_layout);
		aboutapp_layout = (LinearLayout) view.findViewById(R.id.aboutapp_layout);
		setting_layout = (LinearLayout) view.findViewById(R.id.setting_layout);
		myinfo_layout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),MyInfoActivity.class);
				intent.putExtra(FLAG, strid);
				startActivity(intent);
			}
		});
		aboutapp_layout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),AboutappActivity.class);
				startActivity(intent);
			}
		});
		setting_layout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(getActivity(),SettingActivity.class);
				 startActivity(intent);
			}
		});
		return view;			//暂时
		
	}
}