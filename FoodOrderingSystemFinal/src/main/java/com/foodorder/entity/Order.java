package com.foodorder.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Order implements Serializable {
    private Integer id;
    private String orderNo;
    private Integer userId;
    private String recipientName;
    private String recipientPhone;
    private String address;
    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal totalAmount;
    private Integer status;
    private Timestamp createTime;

    private List<OrderItem> items;

    public Order() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
    public String getRecipientPhone() { return recipientPhone; }
    public void setRecipientPhone(String recipientPhone) { this.recipientPhone = recipientPhone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public BigDecimal getDeliveryFee() { return deliveryFee; }
    public void setDeliveryFee(BigDecimal deliveryFee) { this.deliveryFee = deliveryFee; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getStatusText() {
        if (status == null) return "Unknown";
        switch (status) {
            case 0: return "Pending";
            case 1: return "Confirmed";
            case 2: return "Delivering";
            case 3: return "Completed";
            case 4: return "Cancelled";
            default: return "Unknown";
        }
    }
    public Timestamp getCreateTime() { return createTime; }
    public void setCreateTime(Timestamp createTime) { this.createTime = createTime; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}
