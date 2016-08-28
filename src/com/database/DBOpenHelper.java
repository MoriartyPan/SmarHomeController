package com.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{
	public static final int VERSION = 1;
	public static final String DBNAME = "Controller.db";
	public DBOpenHelper(Context context) {
		super(context,DBNAME,null,VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table UserInformation(userid integer primary key,username varcher(32),password varchar(32),familyid integer(4))");
		db.execSQL("create table IPInformation(ip_id integer primary key,ip varchar(32))");
		db.execSQL("create table Devices(devicesid integer primary key,devicesname varchar(32),location integer,status integer,familyid integer)");
		db.execSQL("create table Room(roomid integer primary key,roomname varchar(32),roomtype integer,familyid integer)");
		db.execSQL("create table Notes(notesid integer primary key,notes varchar(32),time varchar(32))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}