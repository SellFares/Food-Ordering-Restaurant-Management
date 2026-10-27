package com.foodorder.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartItem implements Serializable {
    private Integer dishId;
    private String dishName;
    private String image;
    private BigDecimal price;  
    private Integer quantity;

    public CartItem() {}

    public CartItem(Integer dishId, String dishName, String image, BigDecimal price, Integer quantity) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        if (price == null || quantity == null) return BigDecimal.ZERO;
        return price.multiply(new BigDecimal(quantity));
    }

    public Integer getDishId() { return dishId; }
    public void setDishId(Integer dishId) { this.dishId = dishId; }
    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
