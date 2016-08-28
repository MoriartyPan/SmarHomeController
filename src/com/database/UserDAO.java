package com.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.model.UserInformation;

public class UserDAO{
	private DBOpenHelper helper;		//创建DBOpenHelper对象
	private SQLiteDatabase db;			//创建SQLiteDatabase对象
	public UserDAO(Context context)	//定义构造函数
	{
		helper = new DBOpenHelper(context);		//初始化DBOpenHelper对象
	}
	/**
	 * 通过用户id查找用户信息
	 * @param id
	 * @return
	 */
	public UserInformation find(int id){
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select userid,username,password,familyid from UserInformation where userid=?",
				new String[]{String.valueOf(id)});
		if(cursor.moveToNext())
		{
			return new UserInformation(
					cursor.getInt(cursor.getColumnIndex("userid")),
					cursor.getString(cursor.getColumnIndex("username")),
					cursor.getString(cursor.getColumnIndex("password")),
					cursor.getInt(cursor.getColumnIndex("familyid")));
		}
		return null;
	}
	/**
	 * 通过userid找到家庭码
	 * @param id
	 * @return
	 */
	public int findfamilyid(int id){
		db = helper.getWritableDatabase();		//初始化SQLiteDatabase对象
		//获取UserIformation表中的最大userid
		Cursor cursor = db.rawQuery("select familyid from UserInformation where userid=?",
				new String[]{String.valueOf(id)});
		while(cursor.moveToLast()){				//访问Cursor中的最后一条数据
			return cursor.getInt(0);			//获取访问到的数据，即最大userid
		}
		return 1;
	}
	/**
	 * 更新
	 * @param userinformation
	 */
	public void update(UserInformation userinformation){
		db = helper.getWritableDatabase();		//初始化SQLiteDatabase对象
		//执行修改设备信息操作
		db.execSQL("update Userinformation set username = ?,password = ?,familyid=? where userid = ?",
				new Object[]{userinformation.getusername(),userinformation.getpassword(),
						userinformation.getfamilyid(),userinformation.getid()});
	}
}