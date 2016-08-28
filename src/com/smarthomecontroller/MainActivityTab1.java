package com.smarthomecontroller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivityTab1 extends Fragment{
	
	public static final String FLAG = "id";
	private LinearLayout showDevicesStats_layout;
	private LinearLayout addDevices_layout;
	private LinearLayout showRoom_layout;
	private LinearLayout addRoom_layout;
	String strid;

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.activity_mian_devices, container, false);
		Bundle bundle = getArguments();
		strid = bundle.getString(MainActivity.FAMILYID);
		showDevicesStats_layout = (LinearLayout) view.findViewById(R.id.showDevicesStats_layout);
		addDevices_layout = (LinearLayout) view.findViewById(R.id.addDevices_layout);
		showRoom_layout = (LinearLayout) view.findViewById(R.id.showRoom_layout);
		addRoom_layout = (LinearLayout) view.findViewById(R.id.addRoom_layout);
		showDevicesStats_layout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),showDevicesStatsActivity.class);
				intent.putExtra(FLAG, strid);
				startActivity(intent);
			}
		});
		addDevices_layout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),AddDevicesActivity.class);
				intent.putExtra(FLAG, strid);
				startActivity(intent);
			}
		});
		showRoom_layout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),showRoomActivity.class);
				intent.putExtra(FLAG, strid);
				startActivity(intent);
			}
		});
		addRoom_layout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),AddRoomActivity.class);
				intent.putExtra(FLAG, strid);
				startActivity(intent);
			}
		});
		return view;			//暂时
	}
	
}