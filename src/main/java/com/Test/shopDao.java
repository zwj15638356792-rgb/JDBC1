package com.Test;

import com.entity.shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class shopDao {

    // 查询所有商品
    public List<shop> findAll() {
        List<shop> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // 1. 获取连接对象
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_shop", "root", "12345678");
            
            // 2. 获取执行sql语句的对象
            String sql = "SELECT * FROM tb_product";
            ps = con.prepareStatement(sql);
            
            // 3. 开始执行sql语句
            rs = ps.executeQuery();

            while (rs.next()) {
                shop s = new shop();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setAddTime(rs.getTimestamp("add_time"));
                s.setPrice(rs.getBigDecimal("price"));
                s.setOrderId(rs.getInt("order_id"));
                s.setNum(rs.getInt("num"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 4. 关闭资源
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    // 根据ID查询商品
    public shop findById(int id) {
        shop s = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // 1. 获取连接对象
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_shop", "root", "12345678");
            
            // 2. 获取执行sql语句的对象
            String sql = "SELECT * FROM tb_product WHERE id = ?";
            ps = con.prepareStatement(sql);
            
            // 2.2 为占位符赋值
            ps.setInt(1, id);
            
            // 3. 开始执行sql语句
            rs = ps.executeQuery();

            if (rs.next()) {
                s = new shop();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setAddTime(rs.getTimestamp("add_time"));
                s.setPrice(rs.getBigDecimal("price"));
                s.setOrderId(rs.getInt("order_id"));
                s.setNum(rs.getInt("num"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 4. 关闭资源
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return s;
    }

    // 添加商品
    public boolean add(shop s) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            // 1. 获取连接对象
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_shop", "root", "12345678");
            
            // 2. 获取执行sql语句的对象
            String sql = "INSERT INTO tb_product (name, add_time, price, order_id, num) VALUES (?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            
            // 2.2 为占位符赋值
            ps.setString(1, s.getName());
            ps.setTimestamp(2, new Timestamp(s.getAddTime().getTime()));
            ps.setBigDecimal(3, s.getPrice());
            ps.setInt(4, s.getOrderId());
            ps.setInt(5, s.getNum());
            
            // 3. 开始执行sql语句
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // 4. 关闭资源
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 更新商品
    public boolean update(shop s) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            // 1. 获取连接对象
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_shop", "root", "12345678");
            
            // 2. 获取执行sql语句的对象
            String sql = "UPDATE tb_product SET name=?, add_time=?, price=?, order_id=?, num=? WHERE id=?";
            ps = con.prepareStatement(sql);
            
            // 2.2 为占位符赋值
            ps.setString(1, s.getName());
            ps.setTimestamp(2, new Timestamp(s.getAddTime().getTime()));
            ps.setBigDecimal(3, s.getPrice());
            ps.setInt(4, s.getOrderId());
            ps.setInt(5, s.getNum());
            ps.setInt(6, s.getId());
            
            // 3. 开始执行sql语句
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // 4. 关闭资源
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 删除商品
    public boolean delete(int id) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            // 1. 获取连接对象
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_shop", "root", "12345678");
            
            // 2. 获取执行sql语句的对象
            String sql = "DELETE FROM tb_product WHERE id = ?";
            ps = con.prepareStatement(sql);
            
            // 2.2 为占位符赋值
            ps.setInt(1, id);
            
            // 3. 开始执行sql语句
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // 4. 关闭资源
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 根据订单ID查询商品
    public List<shop> findByOrderId(int orderId) {
        List<shop> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // 1. 获取连接对象
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_shop", "root", "12345678");
            
            // 2. 获取执行sql语句的对象
            String sql = "SELECT * FROM tb_product WHERE order_id = ?";
            ps = con.prepareStatement(sql);
            
            // 2.2 为占位符赋值
            ps.setInt(1, orderId);
            
            // 3. 开始执行sql语句
            rs = ps.executeQuery();

            while (rs.next()) {
                shop s = new shop();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setAddTime(rs.getTimestamp("add_time"));
                s.setPrice(rs.getBigDecimal("price"));
                s.setOrderId(rs.getInt("order_id"));
                s.setNum(rs.getInt("num"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 4. 关闭资源
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

}