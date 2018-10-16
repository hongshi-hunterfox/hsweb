package com.uclee.fundation.data.mybatis.model;

public class ProductCategoryLinkKey {
    private Integer categoryId;

    private Integer productId;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}