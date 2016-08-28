package com.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.model.Room;

public class RoomDAO{
	private DBOpenHelper helper;		//创建DBOpenHelper对象
	private SQLiteDatabase db;			//创建SQLiteDatabase对象
	
	public RoomDAO(Context context){
		helper = new DBOpenHelper(context);		//初始化DBOpenHelper对象
	}
	/**
	 * 添加新的房间
	 * @param room
	 */
	public void addRoom(Room room){
		db = helper.getWritableDatabase();		//初始化SQLiteDatabase对象
		db.execSQL("insert into Room(roomid,roomname,roomtype,familyid) values (?,?,?,?)", 
				new Object[]{room.getroomid(),room.getroomname(),room.getroomtype(),room.getfamilyid()});
	}
	/**
	 * 更新房间信息
	 * @param room
	 */
	public void update(Room room){
		db = helper.getWritableDatabase();		//初始化SQLiteDatabase对象
		//执行修改设备信息操作
		db.execSQL("update Room set roomname = ?,roomtype = ? where roomid = ?",
				new Object[]{room.getroomname(),room.getroomtype(),
						room.getroomid()});
	}
	/**
	 * 获取房间信息
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Room> getScrollData(int familyid,int start,int count){
		List<Room> lisRoom = new ArrayList<Room>();
		db = helper.getWritableDatabase();
		//获取所有设备信息
		Cursor cursor = db.rawQuery("select * from Room where familyid=? limit ?,?",
				new String[]{String.valueOf(familyid),String.valueOf(start),String.valueOf(count)});
		while(cursor.moveToNext())				//遍历所有设备信息
		{
			lisRoom.add(new Room(cursor.getInt(cursor.getColumnIndex("roomid")),
					cursor.getString(cursor.getColumnIndex("roomname")),
					cursor.getInt(cursor.getColumnIndex("roomtype")),
					cursor.getInt(cursor.getColumnIndex("familyid"))));
		}
		return lisRoom;						//返回集合
	}
	/**
	 * 删除房间信息
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
			db.execSQL("delete from Room where roomid in("+sb+")", (Object[]) ids);
		}
	}
	/**
	 * 获取房间总数
	 * @return
	 */
	public long getCount(int familyid) {
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select count(roomid) from Room where familyid=?",
				new String[]{String.valueOf(familyid)});
		if(cursor.moveToNext())
		{
			return cursor.getLong(0);		//返回记录总数
		}
		return 0;							//没有则返回0
	}
	/**
	 * 通过房间id查找房间信息
	 * @param id
	 * @return
	 */
	public Room find(int id){
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"select roomid,roomname,roomtype,familyid from Room where roomid=?",
				new String[]{String.valueOf(id)});
		if(cursor.moveToNext())
		{
			//将遍历到的设备信息存储到Devices类中去
			return new Room(
					cursor.getInt(cursor.getColumnIndex("roomid")),
					cursor.getString(cursor.getColumnIndex("roomname")),
					cursor.getInt(cursor.getColumnIndex("roomtype")),
					cursor.getInt(cursor.getColumnIndex("familyid")));
		}
		return null;		//没有信息则返回null
	}
	/**
	 * 获取房间最大id
	 * @return
	 */
	public int getMaxId(){
		db = helper.getWritableDatabase();		//初始化SQLiteDatabase对象
		//获取UserIformation表中的最大userid
		Cursor cursor = db.rawQuery("select max(roomid) from Room", null);
		while(cursor.moveToLast()){				//访问Cursor中的最后一条数据
			return cursor.getInt(0);			//获取访问到的数据，即最大userid
		}
		return 0;								//如果没有数据，则返回0
	}
}