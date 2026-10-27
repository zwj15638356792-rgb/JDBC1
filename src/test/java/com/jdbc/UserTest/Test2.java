package com.jdbc.UserTest;

import com.Users.Users;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.Reader;
import java.util.List;

public class Test2
{
    @Test
    public void test01() throws Exception{
        //1.读取配置文件---解析配置文件. mybatis知道连接的数据库和账号密码。 知道映射文件中内。
        Reader reader = Resources.getResourceAsReader("conf.xml");
        //2.获取SqlSessionFactory对象
        SqlSessionFactory sessionFactory=new SqlSessionFactoryBuilder().build(reader);
        //1. 获取SqlSession对象----理解为: Connection对象。类中包含对表的各种操作方法。
        SqlSession session=sessionFactory.openSession();
        //3.SqlSession对象操作数据库表 statement目前要和namespace+id一致  parameter:参数值
        Users user= session.selectOne("com.Users.Users.get_id",3);
        System.out.println(user);

    }

    @Test
    public void test02() throws Exception{
        //1.读取配置文件---解析配置文件. mybatis知道连接的数据库和账号密码。 知道映射文件中内。
        Reader reader = Resources.getResourceAsReader("conf.xml");
        //2.获取SqlSessionFactory对象
        SqlSessionFactory sessionFactory=new SqlSessionFactoryBuilder().build(reader);
        //1. 获取SqlSession对象----理解为: Connection对象。类中包含对表的各种操作方法。
        SqlSession session=sessionFactory.openSession();
        //3.SqlSession对象操作数据库表 statement目前要和namespace+id一致  parameter:参数值
        List<Users> user= session.selectList("com.dao.UsersDao.get_all");
        System.out.println(user);
    }

    @Test
    public void test03() throws Exception{
        //1.读取配置文件---解析配置文件. mybatis知道连接的数据库和账号密码。 知道映射文件中内。
        Reader reader = Resources.getResourceAsReader("conf.xml");
        //2.获取SqlSessionFactory对象
        SqlSessionFactory sessionFactory=new SqlSessionFactoryBuilder().build(reader);
        //1. 获取SqlSession对象----理解为: Connection对象。类中包含对表的各种操作方法。
        SqlSession session=sessionFactory.openSession();
        //3.SqlSession对象操作数据库表 statement目前要和namespace+id一致  parameter:参数值
        Users user=new Users();
        user.setName("张三");
        user.setAge(18);
        int row=session.insert("com.dao.UsersDao.insert",user);
        session.commit();//提交---增删改操作
    }

    @Test
    public void test04() throws Exception{
        //1.读取配置文件---解析配置文件. mybatis知道连接的数据库和账号密码。 知道映射文件中内。
        Reader reader = Resources.getResourceAsReader("conf.xml");
        //2.获取SqlSessionFactory对象
        SqlSessionFactory sessionFactory=new SqlSessionFactoryBuilder().build(reader);
        //1. 获取SqlSession对象----理解为: Connection对象。类中包含对表的各种操作方法。
        SqlSession session=sessionFactory.openSession();
        //3.SqlSession对象操作数据库表 statement目前要和namespace+id一致  parameter:参数值

        int row=session.delete("com.dao.UsersDao.delete",5);
        System.out.println(row);
        session.commit();//提交---增删改操作
    }

    @Test
    public void test05() throws Exception{
        //1.读取配置文件---解析配置文件. mybatis知道连接的数据库和账号密码。 知道映射文件中内。
        Reader reader = Resources.getResourceAsReader("conf.xml");
        //2.获取SqlSessionFactory对象
        SqlSessionFactory sessionFactory=new SqlSessionFactoryBuilder().build(reader);
        //1. 获取SqlSession对象----理解为: Connection对象。类中包含对表的各种操作方法。
        SqlSession session=sessionFactory.openSession();
        //3.SqlSession对象操作数据库表 statement目前要和namespace+id一致  parameter:参数值
        Users user=new Users();
        user.setName("李四");
        user.setAge(88);
        user.setId(6);
        int row=session.update("com.dao.UsersDao.update",user);
        System.out.println(row);
        session.commit();//提交---增删改操作
    }
}

