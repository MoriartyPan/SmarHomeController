package com.database;

import com.model.UserInformation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import com.model.*;

public class SigninDAO{
	private DBOpenHelper helper;	//创建DBOpenHelper对象
	private SQLiteDatabase db;
	
	public SigninDAO(Context context){
		helper = new DBOpenHelper(context);	//初始化DBOpenHelper对象
	}
	/**
	 * 匹配用户名和密码信息
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean Signin(String username,String password){
		db = helper.getWritableDatabase();	//初始化SQLiteDatabase对象
		//查找用户名与密码并存储到Cursor中
		Cursor cursor = db.rawQuery("select * from UserInformation where username=?  and password=?",new String[]{username,password});
		//Cursor cursor = db.rawQuery("select username,password form UserInformation", null);
		if(cursor.moveToFirst()==true)
		{
			cursor.close();
			return true;
		}
		return false;
	}
	public UserInformation findid(String username1){
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select userid,username,password,familyid from UserInformation where username=?",
				new String[]{String.valueOf(username1)});
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
}