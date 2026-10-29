package com.jdbc.UserTest;

import com.dao.EmployeeDao;
import com.entity.Employee;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.Reader;
import java.util.List;

/**
 * 演示 PageHelper 分页插件 + 多参数传参 + 模糊查询 的组合使用。
 */
public class EmployeePageTest {

    public SqlSession getSession() throws Exception {
        Reader reader = Resources.getResourceAsReader("conf.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        return sessionFactory.openSession(true);
    }

    //传参多个参数 + 模糊查询：姓名/部门都可按关键词模糊匹配
    @Test
    public void testFuzzyMultiParam() throws Exception {
        SqlSession session = getSession();
        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);

            //只传姓名关键词
            List<Employee> byName = mapper.selectEmployee("张", null);
            System.out.println("=== 多参数+模糊 empName=张，共" + byName.size() + "条，第一条：" + byName.get(0).getEmpName() + " ===");

            //两个参数都用上
            List<Employee> both = mapper.selectEmployee("张", "研");
            System.out.println("=== 多参数+模糊 empName=工 dept=研，共" + both.size() + "条 ===");
            both.forEach(System.out::println);
        } finally {
            session.close();
        }
    }

    //PageHelper 分页：查询前先 startPage，紧跟着的一条查询会被分页
    @Test
    public void testPage() throws Exception {
        SqlSession session = getSession();
        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);

            //翻到第2页，每页5条
            PageHelper.startPage(2, 5);
            List<Employee> list = mapper.selectEmployee(null, null); //不分条件查全部再分页
            PageInfo<Employee> page = new PageInfo<>(list);

            System.out.println("=== 第" + page.getPageNum() + "页，pageSize=" + page.getPageSize() + "，当前页条数=" + page.getSize() + "，共" + page.getTotal() + "条，共" + page.getPages() + "页 ===");
            list.forEach(System.out::println);
        } finally {
            session.close();
        }
    }

    //分页 + 多参数 + 模糊查询 组合：先条件模糊查询，再分页
    @Test
    public void testPageWithFuzzy() throws Exception {
        SqlSession session = getSession();
        try {
            EmployeeDao mapper = session.getMapper(EmployeeDao.class);

            //第一页，条件：部门含"研" 且 姓名含"工"，模糊匹配后分页
            PageHelper.startPage(1, 3);
            List<Employee> list = mapper.selectEmployee("张", "研");
            PageInfo<Employee> page = new PageInfo<>(list);

            System.out.println("=== 条件(empName含'工',dept含'研') 第" + page.getPageNum() + "页 共" + page.getTotal() + "条 共" + page.getPages() + "页 ===");
            list.forEach(System.out::println);
        } finally {
            session.close();
        }
    }
}