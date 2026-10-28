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

public class EmployeeQueryTest {

    public SqlSession getSession() throws Exception {
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        return sessionFactory.openSession();
    }

    //where + if
    @Test
    public void testSelectByCondition() throws Exception {
        SqlSession session = getSession();
        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);

            //无条件查全部
            List<Employee> all = mapper.selectByCondition(new Employee());
            System.out.println("=== where+if 无条件，共" + all.size() + "条 ===");

            //按部门查询
            Employee byDept = new Employee();
            byDept.setDept("研发部");
            List<Employee> byDeptList = mapper.selectByCondition(byDept);
            System.out.println("=== where+if 部门=研发部，共" + byDeptList.size() + "条 ===");
            byDeptList.forEach(System.out::println);
        } finally {
            session.close();
        }
    }

    //where + choose(when/otherwise)
    @Test
    public void testSelectByCondition2() throws Exception {
        SqlSession session = getSession();
        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);

            Employee byName = new Employee();
            byName.setEmpName("李萌");
            List<Employee> list1 = mapper.selectByCondition2(byName);
            System.out.println("=== where+choose 姓名=李萌，共" + list1.size() + "条 ===");
            list1.forEach(System.out::println);

            List<Employee> list2 = mapper.selectByCondition2(new Employee());
            System.out.println("=== where+choose 无条件(otherwise)，共" + list2.size() + "条 ===");
        } finally {
            session.close();
        }
    }

    //trim + if：prefix 生成 where，prefixOverrides 去掉首个 and/or
    @Test
    public void testSelectByCondition3() throws Exception {
        SqlSession session = getSession();
        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);

            //只传姓名，触发 and emp_name，trim 会把开头的 and 去掉
            Employee byName = new Employee();
            byName.setEmpName("王浩");
            List<Employee> list1 = mapper.selectByCondition3(byName);
            System.out.println("=== trim 姓名=王浩，共" + list1.size() + "条 ===");
            list1.forEach(System.out::println);

            //只传部门，触发 or dept，trim 用 prefixOverrides 同样去掉开头的 or
            Employee byDept = new Employee();
            byDept.setDept("研发部");
            List<Employee> list2 = mapper.selectByCondition3(byDept);
            System.out.println("=== trim 部门=研发部，共" + list2.size() + "条 ===");

            //无条件
            List<Employee> list3 = mapper.selectByCondition3(new Employee());
            System.out.println("=== trim 无条件，共" + list3.size() + "条 ===");
        } finally {
            session.close();
        }
    }
}