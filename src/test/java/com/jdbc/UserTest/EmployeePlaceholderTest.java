package com.jdbc.UserTest;

import com.dao.EmployeeDao;
import com.entity.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.Reader;
import java.util.List;

/**
 * 演示 MyBatis 中 #{} 与 ${} 的区别。
 *
 * 运行测试时观察日志中的 PreparedStatement：
 *   #{}  -> 生成预编译的 "?" 占位符，参数通过 setString 传值并自动加引号转义
 *   ${}  -> 直接把参数拼进 SQL 字符串中，不转义
 *
 * 关注点：
 *   1. #{} 防注入，${} 可注入
 *   2. #{} 只能放"值"，${} 可放"任意片段"（列名/表名/order by）
 */
public class EmployeePlaceholderTest {

    public SqlSession getSession() throws Exception {
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        return sessionFactory.openSession();
    }

    //#{}：预编译占位符，正常查询，防注入
    @Test
    public void testHash() throws Exception {
        SqlSession session = getSession();
        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);
            List<Employee> list = mapper.selectByHash("李萌");
            System.out.println("=== #{} 查询 姓名=李萌，共" + list.size() + "条 ===");
            list.forEach(System.out::println);
        } finally {
            session.close();
        }
    }

    //${}：字符串拼接，正常输入时也能查询
    @Test
    public void testDollar() throws Exception {
        SqlSession session = getSession();
        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);
            List<Employee> list = mapper.selectByDollar("李萌");
            System.out.println("=== ${} 查询 姓名=李萌，共" + list.size() + "条 ===");
            list.forEach(System.out::println);
        } finally {
            session.close();
        }
    }

    //关键演示：SQL 注入。同一个非法入参，#{} 查不到数据，${} 却能把整个表查出来
    @Test
    public void testInjection() throws Exception {
        SqlSession session = getSession();
        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);
            //经典注入：' or '1'='1，可绕过条件
            String attack = "' or '1'='1";

            //#{} 被当作普通字符串处理，安全，查不出结果
            List<Employee> byHash = mapper.selectByHash(attack);
            System.out.println("=== 注入参数 -> #{} 查询结果数：" + byHash.size() + " （安全）===");

            //${} 被直接拼进 SQL，变成：select * from employee where emp_name = '' or '1'='1'，查出全表
            List<Employee> byDollar = mapper.selectByDollar(attack);
            System.out.println("=== 注入参数 -> ${} 查询结果数：" + byDollar.size() + " （被注入！）===");
            byDollar.forEach(System.out::println);
        } finally {
            session.close();
        }
    }

    //#{} 无法用于 order by 列名，而 ${} 可以。此类"动态片段"只能用 ${}
    @Test
    public void testOrderBy() throws Exception {
        SqlSession session = getSession();
        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);
            List<Employee> bySalary = mapper.selectOrderBy("salary desc");
            System.out.println("=== ${} order by salary desc，第一名：" + bySalary.get(0).getEmpName() + "/" + bySalary.get(0).getSalary() + " ===");

            List<Employee> byAge = mapper.selectOrderBy("age");
            System.out.println("=== ${} order by age，共" + byAge.size() + "条 ===");
            //注意：入参必须由代码严格控制，否则可注入，例如 order by salary desc; drop table xxx
        } finally {
            session.close();
        }
    }
}