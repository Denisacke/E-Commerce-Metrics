package com.commerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class Cart extends AbstractEntity{

    @Column(name = "id_customer")
    private int customerId;

    @Column
    private int productId;

    @Column
    private int quantity;

    public Cart(){

    }

    public Cart(int customerId, int productId, int quantity){
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }

    public void setProductId(int productId){
        this.productId = productId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
