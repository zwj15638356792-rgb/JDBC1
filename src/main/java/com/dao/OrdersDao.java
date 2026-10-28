package com.dao;

import com.entity.Orders;

import java.util.List;

public interface OrdersDao {

    public Orders getByNo(String no);

    public List<Orders> getAll();
}

