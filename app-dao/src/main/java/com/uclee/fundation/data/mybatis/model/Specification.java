package com.uclee.fundation.data.mybatis.model;

import java.util.List;

public class Specification {
    private Integer specificationId;

    private String specification;
    
    private List<SpecificationValue> specificationValues;

    public List<SpecificationValue> getValues() {
		return specificationValues;
	}

	public void setValues(List<SpecificationValue> specificationValues) {
		this.specificationValues = specificationValues;
	}

	public Integer getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Integer specificationId) {
        this.specificationId = specificationId;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification == null ? null : specification.trim();
    }
}