package com.jdbc.UserTest;

import com.dao.EmployeeDao;
import com.entity.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.Reader;

/**
 * 演示 MyBatis 一级缓存与二级缓存。
 *
 * 一级缓存(默认开启)：SqlSession 级别，同一 SqlSession 内相同 SQL 只查一次库。
 *         注意：两次查询之间有 update/insert/delete 或被 clearCache()，会清空一级缓存。
 * 二级缓存(需显式开启)：namespace 级别，跨 SqlSession 共享。
 *         注意：commit() 后结果才会写入二级缓存；实体类需实现 Serializable。
 */
public class EmployeeCacheTest {

    public SqlSessionFactory getFactory() throws Exception {
        Reader reader = Resources.getResourceAsReader("conf.xml");
        return new SqlSessionFactoryBuilder().build(reader);
    }

    //一级缓存：同一 SqlSession，第二次相同查询直接命中缓存
    @Test
    public void testL1Cache() throws Exception {
        SqlSessionFactory factory = getFactory();
        SqlSession session = factory.openSession(true);
        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);

            Employee e1 = mapper.selectByEmpId(9);
            System.out.println("=== 第一次查询 empId=9：" + e1.getEmpName() + " ===");
            //注意观察日志：第二次不会执行 select 语句，直接命中一级缓存
            Employee e2 = mapper.selectByEmpId(9);
            System.out.println("=== 第二次查询 empId=9（应命中一级缓存，无SQL打印）===");
            System.out.println("e1==e2 ? " + (e1 == e2));
        } finally {
            session.close();
        }
    }

    //一级缓存失效：相同 SqlSession 但调用了增删改，缓存被清空，会再次查库
    @Test
    public void testL1CacheInvalid() throws Exception {
        SqlSessionFactory factory = getFactory();
        SqlSession session = factory.openSession(true);
        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);

            mapper.selectByEmpId(9);
            System.out.println("=== 第一次查询后，执行一次 update ===");
            Employee up = new Employee();
            up.setEmpId(9);
            up.setJob("测试工程师");
            mapper.update(up);
            //增删改会清空一级缓存，这里必须重新查库
            Employee again = mapper.selectByEmpId(9);
            System.out.println("=== update 后再查询（缓存已清空，会再次执行 select）===");
        } finally {
            session.close();
        }
    }

    //二级缓存：跨 SqlSession，第一次 openSession 查询并 commit 后，第二个 session 命中二级缓存
    @Test
    public void testL2Cache() throws Exception {
        SqlSessionFactory factory = getFactory();

        //第一个 SqlSession：查询并 commit，把结果写入二级缓存
        try (SqlSession s1 = factory.openSession(true)) {
            EmployeeDao m1 = s1.getMapper(EmployeeDao.class);
            Employee e = m1.selectByEmpId(9);
            System.out.println("=== session1 查询 empId=9：" + e.getEmpName() + "，commit写入二级缓存 ===");
        }

        //第二个 SqlSession：相同查询，命中二级缓存，不再查库
        try (SqlSession s2 = factory.openSession(true)) {
            EmployeeDao m2 = s2.getMapper(EmployeeDao.class);
            Employee e2 = m2.selectByEmpId(9);
            System.out.println("=== session2 查询 empId=9（应命中二级缓存，无SQL打印）===");
        }
    }
}