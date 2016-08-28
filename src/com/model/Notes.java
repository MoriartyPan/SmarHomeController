package com.model;

public class Notes{				//留言实体类
	private int notesid;		//存储留言编号
	private String notes;		//存储留言内容
	private String time;		//存储留言时间
	public Notes(){				//默认构造函数
		super();
	}
	//定义有参构造函数，用来初始化留言实体类中的各个字段
	public Notes(int id,String notes1,String time1){
		super();
		this.notesid = id;					//为留言编号赋值
		this.notes = notes1;				//为留言内容赋值
		this.time = time1;					//为留言时间赋值
	}
	public int getnotesid(){				//设置留言编号的可读属性
		return notesid;
	}
	public void setnotesid(int id){			//设置留言编号的可写属性
		this.notesid = id;
	}
	public String getnotes(){				//设置留言内容的可读属性
		return notes;
	}
	public void setnotes(String notes1){	//设置留言内容的可写属性
		this.notes = notes1;
	}
	public String gettime(){				//设置留言时间的可读属性
		return time;
	}
	public void settime(String time1){		//设置留言时间的可写属性
		this.time = time1;
	}
}