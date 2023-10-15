package com.commerce.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product extends AbstractEntity{

    @Column
    private String name;

    @Column
    @Min(value = 0)
    private double price;

    @Column
    private int id_category;

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

    public Product(String name, double price, int id_category, String description, String picture) {
        this.name = name;
        this.price = price;
        this.id_category = id_category;
        this.description = description;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public int getId_category() {
        return id_category;
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

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }
}