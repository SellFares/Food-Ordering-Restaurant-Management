package com.foodorder.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Dish implements Serializable {
    private Integer id;
    private Integer categoryId;
    private String categoryName; 
    private String name;
    private BigDecimal price;
    private String image;
    private String ingredients;
    private String nutrition;
    private String description;
    private Double rating;
    private Integer stock; 

    public Dish() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }
    public String getNutrition() { return nutrition; }
    public void setNutrition(String nutrition) { this.nutrition = nutrition; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
