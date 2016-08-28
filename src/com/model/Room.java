package com.model;

public class Room{							//房间实体类
	private int roomid;						//设置房间id
	private String roomname;				//设置房间名称
	private int roomtype;					//设置房间类型
	private int familyid;
	public Room(){							//默认构造函数
		super();
	}
	//定义有参构造函数，用来初始化房间实体类中的各个字段
	public Room(int id,String name,int type,int familyid){
		super();
		this.roomid = id;					//为房间id赋值
		this.roomname = name;				//为房间名称赋值
		this.roomtype = type;				//为房间类型赋值
		this.familyid = familyid;
	}
	public int getroomid(){					//设置房间id的可读性
		return roomid;
	}
	public void setroomid(int id){			//设置房间id的可写性
		this.roomid = id;
	}
	public String getroomname(){			//设置房间名称的可读性
		return roomname;
	}
	public void setroomname(String name){	//设置房间名称的可写性
		this.roomname = name;
	}
	public int getroomtype(){				//设置房间类型的可读性
		return roomtype;
	}
	public void setroomtype(int type){		//设置房间类型的可写性
		this.roomtype = type;
	}
	public int getfamilyid(){
		return familyid;
	}
	public void setfamilyid(int familyid){
		this.familyid = familyid;
	}
}