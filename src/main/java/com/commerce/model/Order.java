package com.commerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "order")
public class Order extends AbstractEntity{

    @Column
    private int id_customer;

    @Column
    private int total_cost;

    @Column(name = "shipping_address")
    private String shipping_address;

    @Column
    private String products;

    @Column
    private String status;

    public Order(){

    }

    public Order(int id_customer, int total_cost, String shipping_address, String products, String status){
        this.id_customer = id_customer;
        this.total_cost = total_cost;
        this.shipping_address = shipping_address;
        this.products = products;
        this.status = status;
    }

    public int getId_customer() {
        return id_customer;
    }

    public int getTotal_cost() {
        return total_cost;
    }

    public String getProducts() {
        return products;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public String getStatus() {
        return status;
    }
}
