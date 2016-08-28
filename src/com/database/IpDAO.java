package com.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.model.IPInformation;

public class IpDAO{
	private DBOpenHelper helper;		//创建DBOpenHelper对象
	private SQLiteDatabase db;			//创建SQLiteDatabase对象
	
	public IpDAO(Context context)		//定义构造函数
	{
		helper = new DBOpenHelper(context);		//初始化DBOpenHelper对象
	}
	/**
	 * 添加新的ip地址
	 * @param ip
	 */
	public void add(IPInformation ip){
		db = helper.getWritableDatabase();		//初始化SQLiteDatabase对象
		db.execSQL("insert into IPInformation(ip_id,ip) values (?,?)", 
				new Object[]{ip.getip_id(),ip.getip()});
	}
	/**
	 * 更新ip地址
	 * @param ip
	 */
	public void update(IPInformation ip){
		db = helper.getWritableDatabase();
		db.execSQL("update IPInformation set ip=? where ip_id=?",
				new Object[]{ip.getip(),ip.getip_id()});
	}
	/**
	 * 通过id查找ip信息
	 * @param id
	 * @return
	 */
	public IPInformation find(int id){
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"select ip_id,ip from IPInformation where ip_id=?",
				new String[]{String.valueOf(id)});
		if(cursor.moveToNext())
		{
			//将遍历到的设备信息存储到Devices类中去
			return new IPInformation(
					cursor.getInt(cursor.getColumnIndex("ip_id")),
					cursor.getString(cursor.getColumnIndex("ip")));
		}
		return null;
	}
	public int getMaxId(){
		db = helper.getWritableDatabase();		//初始化SQLiteDatabase对象
		//获取UserIformation表中的最大userid
		Cursor cursor = db.rawQuery("select max(ip_id) from IPInformation", null);
		while(cursor.moveToLast()){				//访问Cursor中的最后一条数据
			return cursor.getInt(0);			//获取访问到的数据，即最大userid
		}
		return 0;								//如果没有数据，则返回0
	}
}