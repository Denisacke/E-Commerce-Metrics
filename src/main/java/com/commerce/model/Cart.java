package com.commerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class Cart extends AbstractEntity{

    @Column
    private int id_customer;

    @Column
    private int id_product;

    @Column
    private int quantity;

    public Cart(){

    }

    public Cart(int id_customer, int id_product, int quantity){
        this.id_customer = id_customer;
        this.id_product = id_product;
        this.quantity = quantity;
    }

    public void setId_Customer(int id_customer){
        this.id_customer = id_customer;
    }

    public void setId_Product(int id_product){
        this.id_product = id_product;
    }

    public int getId_customer() {
        return id_customer;
    }

    public int getId_product() {
        return id_product;
    }

    public int getQuantity() {
        return quantity;
    }
}
