package com.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.model.UserInformation;

public class SignupDAO{
	private DBOpenHelper helper;		//创建DBOpenHelper对象
	private SQLiteDatabase db;
	
	public SignupDAO(Context context){	//定义构造函数
		helper = new DBOpenHelper(context);		//初始化DBOpenHelper对象
	}
	/**
	 * 添加用户信息
	 * @param UserInformation
	 */
	public void addUser(UserInformation UserInformation){
		db = helper.getWritableDatabase();		//初始化SQLiteDatabase对象
		//执行添加用户信息操作
		db.execSQL("insert into UserInformation (userid,username,password,familyid) values (?,?,?,?)",
				new Object[]{UserInformation.getid(),UserInformation.getusername(),
						UserInformation.getpassword(),UserInformation.getfamilyid()});
	}
	/**
	 * 更新用户信息
	 * @param UserInformation
	 */
	public void update(UserInformation UserInformation){
		db = helper.getWritableDatabase();		//初始化SQLiteDatabase对象
		//执行修改用户信息操作
		db.execSQL("update UserInformation set username = ?,password = ?,familyid = ?",
				new Object[]{UserInformation.getusername(),UserInformation.getpassword(),
						UserInformation.getfamilyid()});
	}
	/**
	 * 获取userid最大值
	 * @return
	 */
	public int getMaxId(){
		db = helper.getWritableDatabase();		//初始化SQLiteDatabase对象
		//获取UserIformation表中的最大userid
		Cursor cursor = db.rawQuery("select max(userid) from UserInformation", null);
		while(cursor.moveToLast()){				//访问Cursor中的最后一条数据
			return cursor.getInt(0);			//获取访问到的数据，即最大userid
		}
		return 0;								//如果没有数据，则返回0
	}
	public boolean existUsername(String username){
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from UserInformation where username=?", new String[]{username});
		if(cursor.moveToFirst()==false){			//判断有无相同用户名
			return false;							//没有同样的用户名存在
		}
		return true;								//存在相同用户名
	}
}