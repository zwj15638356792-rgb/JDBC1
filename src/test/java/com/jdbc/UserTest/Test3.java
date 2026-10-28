package com.jdbc.UserTest;

import com.dao.OrdersDao;
import com.entity.Orders;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.Reader;
import java.util.List;

public class Test3 {

    @Test
    public void test1() throws Exception{
        //1.读取配置文件---解析配置文件. mybatis知道连接的数据库和账号密码。 知道映射文件中内。
        Reader reader = Resources.getResourceAsReader("conf.xml");
        //2.获取SqlSessionFactory对象
        SqlSessionFactory sessionFactory=new SqlSessionFactoryBuilder().build(reader);
        //1. 获取SqlSession对象----理解为: Connection对象。类中包含对表的各种操作方法。
        SqlSession session=sessionFactory.openSession();
        //2.获取UsersDao的代理实现类对象。
        OrdersDao mapper = session.getMapper(OrdersDao.class);
        Orders orders = mapper.getByNo("aaaa");
        System.out.println(orders);
    }

    @Test
    public void test2() throws Exception{
        //1.读取配置文件---解析配置文件. mybatis知道连接的数据库和账号密码。 知道映射文件中内。
        Reader reader = Resources.getResourceAsReader("conf.xml");
        //2.获取SqlSessionFactory对象
        SqlSessionFactory sessionFactory=new SqlSessionFactoryBuilder().build(reader);
        //1. 获取SqlSession对象----理解为: Connection对象。类中包含对表的各种操作方法。
        SqlSession session=sessionFactory.openSession();
        //2.获取UsersDao的代理实现类对象。
        OrdersDao mapper = session.getMapper(OrdersDao.class);
        List<Orders> all = mapper.getAll();
        System.out.println(all);
    }
}
