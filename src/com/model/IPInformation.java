package com.model;

public class IPInformation{				//ip地址实体类
	private int ip_id;					//存储ip地址编号
	private String ip;					//存储ip地址
	public IPInformation(){				//默认构造函数
		super();
	}
	//定义有参构造函数，用来初始化ip地址实体类中的各个字段
	public IPInformation(int id,String ip){
		super();
		this.ip_id = id;				//为ip地址编号赋值
		this.ip = ip;					//为ip地址赋值
	}
	public int getip_id(){				//设置ip地址编号的可读属性
		return ip_id;
	}
	public void setip_id(int id){		//设置ip地址编号的可写属性
		this.ip_id = id;
	}
	public String getip(){				//设置ip地址的可读属性
		return ip;
	}
	public void setip(String ip){				//设置ip地址的可写属性
		this.ip = ip;
	}
}
