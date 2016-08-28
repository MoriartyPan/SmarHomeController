package com.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.model.Notes;

public class NotesDAO{
	private DBOpenHelper helper;		//创建DBOpenHelper对象
	private SQLiteDatabase db;			//创建SQLiteDatabase对象
	
	public NotesDAO(Context context)		//定义构造函数
	{
		helper = new DBOpenHelper(context);		//初始化DBOpenHelper对象
	}
	/**
	 * 添加新的留言
	 * @param notes1
	 */
	public void add(Notes notes1){
		db = helper.getWritableDatabase();		//初始化SQLiteDatabase对象
		db.execSQL("insert into Notes(notesid,notes,time) values (?,?,?)", 
				new Object[]{notes1.getnotesid(),notes1.getnotes(),notes1.gettime()});
	}
	public void update(Notes notes){
		db = helper.getWritableDatabase();		//初始化SQLiteDatabase对象
		//执行修改设备信息操作
		db.execSQL("update Notes set notes=? where notesid=?",
				new Object[]{notes.getnotes(),notes.getnotesid()});
	}
	/**
	 * 获取留言信息
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Notes> getScrollData(int start,int count){
		List<Notes> lisNotes = new ArrayList<Notes>();
		db = helper.getWritableDatabase();
		//获取所有设备信息
		Cursor cursor = db.rawQuery("select * from Notes limit ?,?",
				new String[]{String.valueOf(start),String.valueOf(count)});
		while(cursor.moveToNext())				//遍历所有设备信息
		{
			lisNotes.add(new Notes(cursor.getInt(cursor.getColumnIndex("notesid")),
					cursor.getString(cursor.getColumnIndex("notes")),
					cursor.getString(cursor.getColumnIndex("time"))));
		}
		return lisNotes;						//返回集合
	}
	/**
	 * 通过id查找Notes信息
	 * @param id
	 * @return
	 */
	public Notes find(int id){
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"select notesid,notes,time from Notes where notesid=?",
				new String[]{String.valueOf(id)});
		if(cursor.moveToNext())
		{
			//将遍历到的设备信息存储到Devices类中去
			return new Notes(
					cursor.getInt(cursor.getColumnIndex("notesid")),
					cursor.getString(cursor.getColumnIndex("notes")),
					cursor.getString(cursor.getColumnIndex("time")));
		}
		return null;		//没有信息则返回null
	}
	/**
	 * 批量根据id删除留言
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
			db.execSQL("delete from Notes where notesid in("+sb+")", (Object[]) ids);
		}
	}
	/**
	 * 获取留言总数
	 * @return
	 */
	public long getCount(){
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select count(notesid) from Notes", null);
		if(cursor.moveToNext())
		{
			return cursor.getLong(0);		//返回记录总数
		}
		return 0;							//没有则返回0
	}
	/**
	 * 获取最大留言id
	 * @return
	 */
	public int getMaxId(){
		db = helper.getWritableDatabase();		//初始化SQLiteDatabase对象
		//获取UserIformation表中的最大userid
		Cursor cursor = db.rawQuery("select max(notesid) from Notes", null);
		while(cursor.moveToLast()){				//访问Cursor中的最后一条数据
			return cursor.getInt(0);			//获取访问到的数据，即最大userid
		}
		return 0;								//如果没有数据，则返回0
	}
}