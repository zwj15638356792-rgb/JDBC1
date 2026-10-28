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
}
