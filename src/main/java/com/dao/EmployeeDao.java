package com.dao;

import com.entity.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeDao {
    public List<Employee> selectByCondition(Employee employee);
    public List<Employee> selectByCondition2(Employee employee);
    public List<Employee> selectByCondition3(Employee employee);
    public int update(Employee employee);
    public int batchDelete(@Param("list") List<Integer> ids);
    public int batchAdd(@Param("list") List<Employee> emps);

    // #{}：预编译占位符 ?，值会被转义并加引号，防注入
    public List<Employee> selectByHash(@Param("empName") String empName);
    // ${}：直接字符串拼接，不转义，存在 SQL 注入风险
    public List<Employee> selectByDollar(@Param("empName") String empName);
    // ${}：可用于 order by 等列名/排序场景，此时 #{} 无效
    public List<Employee> selectOrderBy(@Param("column") String column);

    // 传参多个参数 + 模糊查询（#{} + concat拼接%实现like，防注入）。
    // 配合分页插件在测试/service层用 PageHelper.startPage(pageNum,pageSize) 启用分页。
    public List<Employee> selectEmployee(@Param("empName") String empName, @Param("dept") String dept);

    // 用于一级/二级缓存演示：按主键查询
    public Employee selectByEmpId(@Param("empId") Integer empId);
}