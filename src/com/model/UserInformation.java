package com.model;

public class UserInformation {		//用户信息实体类
	private int userid;				//存储用户编号
	private String username;		//存储用户名
	private String password;		//存储用户密码
	private int familyid;			//存储用户家庭编号
	public UserInformation()		//默认构造函数
	{
		super();
	}
	//定义有参构造函数，用来初始化用户信息实体类中的各个字段
	public UserInformation(int id,String username,String password,int familyid)
	{
		super();
		this.userid = id;			//为用户编号赋值
		this.username = username;	//为用户名赋值
		this.password = password;	//为用户密码赋值
		this.familyid = familyid;	//为用户家庭编号赋值
	}
	public int getid()				//设置用户编号的可读属性
	{
		return userid;
	}
	public void setid(int id)		//设置用户编号的可写属性
	{
		this.userid = id;
	}
	public String getusername()		//设置用户名的可读属性
	{
		return username;
	}
	public void setusername(String username)	//设置用户名的可写属性 
	{
		this.username = username;
	}
	public String getpassword()					//设置用户密码的可读属性
	{
		return password;
	}
	public void setpassword(String password)	//设置用户密码的可写属性
	{
		this.password = password;
	}
	public int getfamilyid()
	{
		return familyid;
	}
	public void setfamilyid(int familyid)
	{
		this.familyid = familyid;
	}
}
