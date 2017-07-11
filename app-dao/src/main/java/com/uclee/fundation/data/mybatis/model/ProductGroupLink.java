package com.uclee.fundation.data.mybatis.model;

public class ProductGroupLink {
    private Integer groupId;

    private Integer productId;

    private Integer position;
    
    private String Image;
    
    private String groupName;

    public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}