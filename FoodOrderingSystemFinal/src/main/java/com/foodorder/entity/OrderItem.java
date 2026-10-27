package com.foodorder.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItem implements Serializable {
    private Integer id;
    private Integer orderId;
    private Integer dishId;
    private String dishName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subtotal;

    public OrderItem() {}

    public OrderItem(Integer dishId, String dishName, BigDecimal price, Integer quantity) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.price = price;
        this.quantity = quantity;
        this.subtotal = price.multiply(new BigDecimal(quantity));
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }
    public Integer getDishId() { return dishId; }
    public void setDishId(Integer dishId) { this.dishId = dishId; }
    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
