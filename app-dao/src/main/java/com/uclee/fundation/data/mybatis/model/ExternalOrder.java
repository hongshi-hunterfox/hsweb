package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class ExternalOrder {
    private String orderCode;
    private String departName;
    private String tardNo;
    private Date pickUpTime;
    private String vipCode;
    private String callNumber;
    private String remarks;
    private String destination;
    private BigDecimal totalAmount;
    private BigDecimal deducted;
    private BigDecimal shippingCost;
    private String departmentWeb;
    private String oauth_id;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getTardNo() {
        return tardNo;
    }

    public void setTardNo(String tardNo) {
        this.tardNo = tardNo;
    }

    public Date getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(Date pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getVipCode() {
        return vipCode;
    }

    public void setVipCode(String vipCode) {
        this.vipCode = vipCode;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getDeducted() {
        return deducted;
    }

    public void setDeducted(BigDecimal deducted) {
        this.deducted = deducted;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getDepartmentWeb() {
        return departmentWeb;
    }

    public void setDepartmentWeb(String departmentWeb) {
        this.departmentWeb = departmentWeb;
    }

    public String getOauth_id() {
        return oauth_id;
    }

    public void setOauth_id(String oauth_id) {
        this.oauth_id = oauth_id;
    }
}
