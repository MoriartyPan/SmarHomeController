package com.model;

public class Devices{					//设备实体类
	private int devicesid;				//存储设备id
	private String devicesname;			//存储设备名称
	private int location;				//存储设备位置
	private int status;					//存储设备状态
	private int familyid;
	public Devices(){					//默认构造函数
		super();
	}
	//定义有参构造函数，用来初始化设备实体类中的各个字段
	public Devices(int id,String name,int location,int status,int familyid){
		super();
		this.devicesid = id;			//为设备id赋值
		this.devicesname = name;		//为设备名称赋值
		this.location = location;		//为设备位置赋值
		this.status = status;			//为设备状态赋值
		this.familyid = familyid;			//为用户id赋值
	}
	public int getdevicesid(){			//设置设备id的可读性
		return devicesid;
	}
	public void setdevicesid(int id){	//设置设备id的可写性
		this.devicesid = id;
	}
	public String getdevicesname(){		//设置设备名称的可读性
		return devicesname;
	}
	public void setdevicesname(String name){	//设置设备名称的可写性
		this.devicesname = name;
	}
	public int getlocation(){			//设置设备位置的可读性
		return location;
	}
	public void setlocation(int location){		//设置设备位置的可写性
		this.location = location;
	}
	public int getstatus(){				//设置设备状态的可读性
		return status;
	}
	public void setstatus(int status){	//设置设备状态的可写性
		this.status = status;
	}
	public int getfamilyid(){
		return familyid;
	}
	public void setfamilyidid(int familyid){
		this.familyid = familyid;
	}
	
}