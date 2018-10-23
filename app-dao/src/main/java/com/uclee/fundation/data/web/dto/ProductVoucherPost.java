package com.uclee.fundation.data.web.dto;

public class ProductVoucherPost {

	public int id;
	
	public int vid;
	
	public int pid;
	
	public String title;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	} 

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVid() {
		return vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
}
