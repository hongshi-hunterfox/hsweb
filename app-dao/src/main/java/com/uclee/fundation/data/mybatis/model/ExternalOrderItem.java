package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;

public class ExternalOrderItem {
    private Integer pid;
    private String goodsName;
    private Integer goodsCount;
    private BigDecimal totalAmountMoney;
    private BigDecimal extendPriceMoney;
    private String memo;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public BigDecimal getTotalAmountMoney() {
        return totalAmountMoney;
    }

    public void setTotalAmountMoney(BigDecimal totalAmountMoney) {
        this.totalAmountMoney = totalAmountMoney;
    }

    public BigDecimal getExtendPriceMoney() {
        return extendPriceMoney;
    }

    public void setExtendPriceMoney(BigDecimal extendPriceMoney) {
        this.extendPriceMoney = extendPriceMoney;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
