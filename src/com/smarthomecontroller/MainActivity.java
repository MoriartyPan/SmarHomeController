package com.smarthomecontroller;

import java.util.ArrayList;
import java.util.List;

import com.model.UserInformation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	String strid,strfaid;
	public static final String FLAG = "id";
	public static final String FAMILYID = "faid";
	/**
	 * 顶部三个LinearLayout
	 */
	private LinearLayout mTabDevices;
	private LinearLayout mTabNotes;
	private LinearLayout mTabMe;
	/**
	 * 顶部三个TextView
	 */
	 private TextView mDevices;
	 private TextView mNotes;
	 private TextView mMe;
	 /**
	  * Tab引导线
	  */
	 private ImageView mTabLine;
	 /**
	  * ViewPager的当前选中页
	  */
	 private int currentIndex;
	 /**
	  * 屏幕的宽度
	  */
	 private int screenWidth;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		//显示id为xxx的activity
		
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		initView();
		initTabLine();
		/**
		 * 初始化Adapter
		 */
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()){
			@Override
			public int getCount(){
				return mFragments.size();
			}
			@Override
			public Fragment getItem(int arg0){
				return mFragments.get(arg0);
			}
		};
		mViewPager.setAdapter(mAdapter);
		/**
		 * 设置监听
		 * setOnPageChangeListener被提示已经过期故更换为addOnPageChangeLinstener
		 */
		mViewPager.addOnPageChangeListener(new OnPageChangeListener(){
			@Override
			public void onPageSelected(int position){
				//重置所有TextView的字体颜色
				resetTextView();
				switch(position)
				{
				case 0:
					mDevices.setTextColor(getResources().getColor(R.color.green));
					break;
				case 1:
					mNotes.setTextColor(getResources().getColor(R.color.green));
					break;
				case 2:
					mMe.setTextColor(getResources().getColor(R.color.green));
					break;
				}
				currentIndex = position;
			}
			@Override
			public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels){
				/**
				 * 利用position和currentIndex判断用户的操作是哪一页往哪一页滑动
				 * 然后根据positionOffset动态改变TabLine的leftMargin
				 */
				if(currentIndex==0&&position==0)		//0->1
				{
					LinearLayout.LayoutParams lp=(android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();
					lp.leftMargin=(int)(positionOffset*(screenWidth*1.0/3)+currentIndex*(screenWidth/3));
					mTabLine.setLayoutParams(lp);
				}
				else if(currentIndex==1&&position==0)	//1->0
				{
					LinearLayout.LayoutParams lp=(android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();
					lp.leftMargin=(int)(-(1-positionOffset)*(screenWidth*1.0/3)+currentIndex*(screenWidth/3));
					mTabLine.setLayoutParams(lp);
				}
				else if(currentIndex==1&&position==1)	//1->2
				{
					LinearLayout.LayoutParams lp=(android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();
					lp.leftMargin=(int)(positionOffset*(screenWidth*1.0/3)+currentIndex*(screenWidth/3));
					mTabLine.setLayoutParams(lp);
				}
				else if(currentIndex==2&&position==1)	//2->1
				{
					LinearLayout.LayoutParams lp=(android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();
					lp.leftMargin=(int)(-(1-positionOffset)*(screenWidth*1.0/3)+currentIndex*(screenWidth/3));
					mTabLine.setLayoutParams(lp);
				}
			}
			@Override
			public void onPageScrollStateChanged(int state){
				
			}
		});
		mViewPager.setCurrentItem(1);
	}
	/**
	 * 根据屏幕宽度初始化引导线的宽度
	 */
	private void initTabLine()
	{
		mTabLine = (ImageView) findViewById(R.id.id_tab_line);
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		screenWidth = outMetrics.widthPixels;
		LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();
		lp.width = screenWidth/3;
		mTabLine.setLayoutParams(lp);
	}
	/**
	 * 重置颜色
	 */
	protected void resetTextView()
	{
		mDevices.setTextColor(getResources().getColor(R.color.black));
		mNotes.setTextColor(getResources().getColor(R.color.black));
		mMe.setTextColor(getResources().getColor(R.color.black));
	}
	/**
	 * 初始化控件，初始化Fragment
	 */
	private void initView()
	{
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		strid = bundle.getString(SignActivity.FLAG);
		strfaid = bundle.getString(SignActivity.FAMILYID);
		Bundle bundle1 = new Bundle();
		bundle1.putString(FLAG, strid);
		Bundle bundle2 = new Bundle();
		bundle2.putString(FAMILYID, strfaid);
		mTabDevices = (LinearLayout) findViewById(R.id.id_tab_deviceslayout);
		mTabNotes = (LinearLayout) findViewById(R.id.id_tab_notes_layout);
		mTabMe = (LinearLayout) findViewById(R.id.id_tab_me_layout);
		mDevices = (TextView) findViewById(R.id.id_devices_layout);
		mNotes = (TextView) findViewById(R.id.id_notes_layout);
		mMe = (TextView) findViewById(R.id.id_me_layout);		
		MainActivityTab1 tab01 = new MainActivityTab1();
		MainActivityTab2 tab02 = new MainActivityTab2();
		MainActivityTab3 tab03 = new MainActivityTab3();
		tab03.setArguments(bundle1);
		tab01.setArguments(bundle2);
		mFragments.add(tab01);
		mFragments.add(tab02);
		mFragments.add(tab03);
	}

}
