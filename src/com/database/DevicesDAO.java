package com.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.model.Devices;

public class DevicesDAO{
	private DBOpenHelper helper;		//创建DBOpenHelper对象
	private SQLiteDatabase db;			//创建SQLiteDatabase对象
	
	public DevicesDAO(Context context)	//定义构造函数
	{
		helper = new DBOpenHelper(context);		//初始化DBOpenHelper对象
	}
	/**
	 * 添加新的设备
	 * @param devices
	 */
	public void addDevices(Devices devices){
		db = helper.getWritableDatabase();		//初始化SQLiteDatabase对象
		db.execSQL("insert into Devices(devicesid,devicesname,location,status,familyid) values (?,?,?,?,?)", 
				new Object[]{devices.getdevicesid(),devices.getdevicesname(),
						devices.getlocation(),devices.getstatus(),devices.getfamilyid()});
	}
	/**
	 * 更新设备信息
	 * @param devices
	 */
	public void update(Devices devices){
		db = helper.getWritableDatabase();		//初始化SQLiteDatabase对象
		//执行修改设备信息操作
		db.execSQL("update Devices set devicesname = ?,location = ?,status = ? where devicesid = ?",
				new Object[]{devices.getdevicesname(),devices.getlocation(),
						devices.getstatus(),devices.getdevicesid()});
	}
	/**
	 * 获取设备信息
	 * @param userid
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Devices> getScrollData(int familyid,int start,int count){
		List<Devices> lisDevices = new ArrayList<Devices>();
		db = helper.getWritableDatabase();
		//获取所有设备信息
		/*Cursor cursor = db.rawQuery("select * from Devices limit ?,?",
				new String[]{String.valueOf(start),String.valueOf(count)});*/
		Cursor cursor = db.rawQuery("select * from Devices where familyid = ? limit ?,?", 
				new String[]{String.valueOf(familyid),String.valueOf(start),String.valueOf(count)});
		while(cursor.moveToNext())				//遍历所有设备信息
		{
			lisDevices.add(new Devices(cursor.getInt(cursor.getColumnIndex("devicesid")),
					cursor.getString(cursor.getColumnIndex("devicesname")),
					cursor.getInt(cursor.getColumnIndex("location")),
					cursor.getInt(cursor.getColumnIndex("status")),
					cursor.getInt(cursor.getColumnIndex("familyid"))));
		}
		return lisDevices;						//返回集合
	}
	/**
	 * 删除设备信息
	 * @param ids
	 */
	public void delete(Integer... ids){
		if(ids.length > 0)
		{
			StringBuffer sb = new StringBuffer();	//创建StringBuffer对象
			for(int i=0;i<ids.length;i++)			//遍历要删除的id集合
			{
				sb.append('?').append(',');			//将删除条件添加到StringBuffer对象中
			}
			sb.deleteCharAt(sb.length()-1);			//去掉最后一个“，”字符
			db = helper.getWritableDatabase();		//创建SQLiteDatabase对象
			//执行删除设备操作
			db.execSQL("delete from Devices where devicesid in("+sb+")", (Object[]) ids);
		}
	}
	/**
	 * 记录获取总数
	 * @return
	 */
	public long getCount(int familyid) {
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select count(devicesid) from Devices where familyid = ?", 
				new String[]{String.valueOf(familyid)});
		if(cursor.moveToNext())
		{
			return cursor.getLong(0);		//返回记录总数
		}
		return 0;							//没有则返回0
	}
	/**
	 * 通过location获得总数
	 * @param location
	 * @return
	 */
	public long getCount1(int location) {
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select count(devicesid) from Devices where location = ?", 
				new String[]{String.valueOf(location)});
		if(cursor.moveToNext())
		{
			return cursor.getLong(0);		//返回记录总数
		}
		return 0;							//没有则返回0
	}
	/**
	 * 通过id查找设备信息
	 * @param id
	 * @return
	 */
	public Devices find(int id){
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"select devicesid,devicesname,location,status,familyid from Devices where devicesid=?",
				new String[]{String.valueOf(id)});
		if(cursor.moveToNext())
		{
			//将遍历到的设备信息存储到Devices类中去
			return new Devices(
					cursor.getInt(cursor.getColumnIndex("devicesid")),
					cursor.getString(cursor.getColumnIndex("devicesname")),
					cursor.getInt(cursor.getColumnIndex("location")),
					cursor.getInt(cursor.getColumnIndex("status")),
					cursor.getInt(cursor.getColumnIndex("familyid")));
		}
		return null;		//没有信息则返回null
	}
	/**
	 * 获取最大设备id
	 * @return
	 */
	public int getMaxId(){
		db = helper.getWritableDatabase();		//初始化SQLiteDatabase对象
		//获取UserIformation表中的最大userid
		Cursor cursor = db.rawQuery("select max(devicesid) from Devices", null);
		while(cursor.moveToLast()){				//访问Cursor中的最后一条数据
			return cursor.getInt(0);			//获取访问到的数据，即最大userid
		}
		return 0;								//如果没有数据，则返回0
	}
	/**
	 * 
	 * @param familyid
	 * @param location
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Devices> getScrollDataByLocation(int familyid,int location,int start,int count){
		List<Devices> lisDevices = new ArrayList<Devices>();
		db = helper.getWritableDatabase();
		//获取所有设备信息
		/*Cursor cursor = db.rawQuery("select * from Devices limit ?,?",
			new String[]{String.valueOf(start),String.valueOf(count)});*/
		Cursor cursor = db.rawQuery("select * from Devices where familyid = ? and location = ? limit ?,?", 
				new String[]{String.valueOf(familyid),String.valueOf(location),String.valueOf(start),String.valueOf(count)});
		while(cursor.moveToNext())				//遍历所有设备信息
		{
			lisDevices.add(new Devices(cursor.getInt(cursor.getColumnIndex("devicesid")),
					cursor.getString(cursor.getColumnIndex("devicesname")),
					cursor.getInt(cursor.getColumnIndex("location")),
					cursor.getInt(cursor.getColumnIndex("status")),
					cursor.getInt(cursor.getColumnIndex("familyid"))));
		}
		return lisDevices;						//返回集合
	}
}