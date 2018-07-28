package com.uclee.fundation.data.web.dto;

public class ProductGroupPost {

	private Integer groupId;
	
	private Integer productId;
	
	private Integer preGroupId;
	
	private Integer preProductId;
	
	private String groupName;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getPreGroupId() {
		return preGroupId;
	}

	public void setPreGroupId(Integer preGroupId) {
		this.preGroupId = preGroupId;
	}

	public Integer getPreProductId() {
		return preProductId;
	}

	public void setPreProductId(Integer preProductId) {
		this.preProductId = preProductId;
	}
	
}
