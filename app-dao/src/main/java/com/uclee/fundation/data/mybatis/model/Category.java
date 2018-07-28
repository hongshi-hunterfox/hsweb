package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class Category {
    private Integer categoryId;

    private String category;

    private Integer parentId;
    
    private BigDecimal batchDiscount;
    
    private BigDecimal leibie;
    
    private String startTimeStrs;
    
    private String endTimeStrs;
    
    private Date startTimeStr;
    
    private Date endTimeStr;
	
      
    public Date getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(Date startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public Date getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(Date endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getStartTimeStrs() {
		return startTimeStrs;
	}

	public void setStartTimeStrs(String startTimeStrs) {
		this.startTimeStrs = startTimeStrs;
	}

	public String getEndTimeStrs() {
		return endTimeStrs;
	}

	public void setEndTimeStrs(String endTimeStrs) {
		this.endTimeStrs = endTimeStrs;
	}

	
	
    public BigDecimal getLeibie() {
		return leibie;
	}

	public void setLeibie(BigDecimal leibie) {
		this.leibie = leibie;
	}

	public BigDecimal getBatchDiscount() {
		return batchDiscount;
	}

	public void setBatchDiscount(BigDecimal batchDiscount) {
		this.batchDiscount = batchDiscount;
	}

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}