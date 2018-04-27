package com.uclee.fundation.data.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jiang on 2018/4/2.
 */
public class RefundOrder {
    private Integer refundOrderId;

    private Integer userId;

    private Integer paymentId;//1:微信支付 ;2、会员卡支付 ;3、支付宝支付

    private Integer paymentOrderId;//paymentOrders表中的主键ID

    private String paymentSerialNum;//支付单号

    private String refundSerialNum;//退款单号

    private String transactionId;//微信订单号,或者支付宝订单号

    private String payType;//1:支付 2：充值 3退款

    private short transactionType;//1:支付2：充值

    private BigDecimal totalFree;//订单金额

    private BigDecimal refundFree;//退款金额，暂时默认是与订单金额相等的

    private String refundDesc;//退款原因

    private Date createTime;//创建时间

    private Date completeTime;//完成时间

    private boolean isCompleted;//是否已经完成退款 0:未完成 1：已完成

    private Integer flag;//

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getRefundOrderId() {
        return refundOrderId;
    }

    public void setRefundOrderId(Integer refundOrderId) {
        this.refundOrderId = refundOrderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getPaymentOrderId() {
        return paymentOrderId;
    }

    public void setPaymentOrderId(Integer paymentOrderId) {
        this.paymentOrderId = paymentOrderId;
    }

    public String getPaymentSerialNum() {
        return paymentSerialNum;
    }

    public void setPaymentSerialNum(String paymentSerialNum) {
        this.paymentSerialNum = paymentSerialNum;
    }

    public String getRefundSerialNum() {
        return refundSerialNum;
    }

    public void setRefundSerialNum(String refundSerialNum) {
        this.refundSerialNum = refundSerialNum;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public short getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(short transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getTotalFree() {
        return totalFree;
    }

    public void setTotalFree(BigDecimal totalFree) {
        this.totalFree = totalFree;
    }

    public BigDecimal getRefundFree() {
        return refundFree;
    }

    public void setRefundFree(BigDecimal refundFree) {
        this.refundFree = refundFree;
    }

    public String getRefundDesc() {
        return refundDesc;
    }

    public void setRefundDesc(String refundDesc) {
        this.refundDesc = refundDesc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
