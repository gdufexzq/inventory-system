package com.cdc.inventorysystem.entity;

public class QueryVo {
	
	private int dpage;//查看第几页
	private int npage;//每页显示几条
	private int userId;//查找那个用户的信息
	public int getDpage() {
		return dpage;
	}
	public void setDpage(int dpage) {
		this.dpage = dpage;
	}
	public int getNpage() {
		return npage;
	}
	public void setNpage(int npage) {
		this.npage = npage;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public QueryVo() {
		super();
	}
	
}
