package com.example.pass.entity;

import java.sql.Timestamp;

public class GetOrder {
    private Long orderno;
    private String delivery_type;
    private Timestamp instime;

    public GetOrder(String delivery_type) {
        this.delivery_type = delivery_type;
    }
//    private Timestamp updtime;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public GetOrder() {
    }

    @Override
    public String toString() {
        return "GetOrder{" +
                "orderno=" + orderno +
                ", delivery_type='" + delivery_type + '\'' +
                ", instime=" + instime +
                '}';
    }

    public GetOrder(Long id, String delivery_type, Timestamp instime) {
        this.orderno = id;
        this.delivery_type = delivery_type;
        this.instime = instime;
    }

    public GetOrder(Long id) {
        this.orderno = id;
    }

    public Long getId() {
        return orderno;
    }

    public void setId(Long id) {
        this.orderno = id;
    }

    public String getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(String delivery_type) {
        this.delivery_type = delivery_type;
    }

    public Timestamp getInstime() {
        return instime;
    }

    public void setInstime(Timestamp instime) {
        this.instime = instime;
    }
}