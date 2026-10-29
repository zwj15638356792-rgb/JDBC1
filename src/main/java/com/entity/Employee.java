package com.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer empId;//员工编号
    private String empName;//员工姓名
    private String gender;//性别
    private Integer age;//年龄
    private String dept;//部门
    private String job;//职位
    private BigDecimal salary;//薪资
    private LocalDate hiredate;//入职日期
    private String phone;//手机号
    private String email;//邮箱
}