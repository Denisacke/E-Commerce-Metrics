package com.commerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "stock")
public class Stock extends AbstractEntity{

    @Column(name = "id_prod")
    private int id_product;

    @Column
    private int quantity;

    public Stock(){

    }

    public Stock(int id_product, int quantity){
        this.id_product = id_product;
        this.quantity = quantity;
    }

    public int getId_product() {
        return id_product;
    }

    public int getQuantity() {
        return quantity;
    }
}
