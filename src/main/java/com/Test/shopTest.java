package com.Test;

import com.entity.shop;

import java.util.List;

public class
shopTest {
    public static void main(String[] args) {
        shopDao dao = new shopDao();
//        List<shop> list = dao.findAll();
//        for (shop s : list) {
//            System.out.println("商品名称：" + s.getName() + " - 商品价格：" + s.getPrice());
//        }
//        shop s = dao.findById(1);
//        System.out.println(s.getName());
//
//        shop newShop = new shop();
//        newShop.setName("苹果");
//        newShop.setAddTime(new Date());
//        newShop.setPrice(new BigDecimal("5.99"));
//        newShop.setOrderId(4);
//        newShop.setNum(10);
//
//        boolean result = dao.add(newShop);
//        System.out.println(result ? "添加成功" : "添加失败");
//        shop s = dao.findById(1);
//        s.setName("全麦面包");
//        s.setPrice(new BigDecimal("9.99"));
//
//        boolean result = dao.update(s);
//        System.out.println(result ? "更新成功" : "更新失败");
        boolean result = dao.delete(9);
        System.out.println(result ? "删除成功" : "删除失败");
        List<shop> orderShops = dao.findByOrderId(1);
        for (shop s : orderShops) {
            System.out.println(s.getName());
        }

    }
}
