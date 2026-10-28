package com.jdbc.UserTest;

import com.dao.EmployeeDao;
import com.entity.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeDmlTest {

    public SqlSession getSession() throws Exception {
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        return sessionFactory.openSession();
    }

    //set+if 动态更新：只更新传入的非空字段
    @Test
    public void testUpdate() throws Exception {
        SqlSession session = getSession();
        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);

            Employee emp = new Employee();
            emp.setEmpId(1);//更新 emp_id=1 这条
            emp.setDept("测试部");//只改部门，其余字段不传(保持原值)

            int rows = mapper.update(emp);
            System.out.println("=== 更新影响行数: " + rows + " ===");
            session.commit();
        } finally {
            session.close();
        }
    }

    //foreach 批量删除：delete from employee where emp_id in (..)
    @Test
    public void testBatchDelete() throws Exception {
        SqlSession session = getSession();
        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);

            List<Integer> ids = Arrays.asList(1, 2, 3);
            int rows = mapper.batchDelete(ids);
            System.out.println("=== 批量删除影响行数: " + rows + " ===");
            session.commit();
        } finally {
            session.close();
        }
    }

    //foreach 批量新增
    @Test
    public void testBatchAdd() throws Exception {
        SqlSession session = getSession();
        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);

            List<Employee> emps = new ArrayList<>();
            emps.add(buildEmp("张三", "男", 26, "研发部", "后端开发",
                    new BigDecimal("10000.00"), LocalDate.of(2023, 1, 10), "13800139001", "zhangsan@company.com"));
            emps.add(buildEmp("李四", "女", 24, "市场部", "销售",
                    new BigDecimal("8000.00"), LocalDate.of(2024, 6, 15), "13800139002", "lisi@company.com"));

            int rows = mapper.batchAdd(emps);
            System.out.println("=== 批量新增影响行数: " + rows + " ===");
            session.commit();
        } finally {
            session.close();
        }
    }

    private Employee buildEmp(String name, String gender, Integer age, String dept,
                              String job, BigDecimal salary, LocalDate hiredate,
                              String phone, String email) {
        Employee emp = new Employee();
        emp.setEmpName(name);
        emp.setGender(gender);
        emp.setAge(age);
        emp.setDept(dept);
        emp.setJob(job);
        emp.setSalary(salary);
        emp.setHiredate(hiredate);
        emp.setPhone(phone);
        emp.setEmail(email);
        return emp;
    }
}