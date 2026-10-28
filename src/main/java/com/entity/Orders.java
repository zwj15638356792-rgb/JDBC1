package com.entity;

import lombok.Data;

@Data
public class Orders {
    private Integer id;
    private String no;//订单号
    private double price;//订单的价格
}
