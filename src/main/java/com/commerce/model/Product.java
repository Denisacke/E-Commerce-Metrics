package com.commerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Table(name = "product")
public class Product extends AbstractEntity{

    @Column
    private String name;

    @Column
    @Min(value = 0)
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column
    private String description;

    @Column
    private String picture;

    @Column
    private Integer stock;

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Product() {

    }

    public Product(String name, double price, String description, String picture, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.picture = picture;
    }

    public Product(String name, double price, String description, String picture) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}