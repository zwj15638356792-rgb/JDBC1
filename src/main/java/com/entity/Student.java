package com.entity;

import lombok.Data;

@Data
public class Student {
    private Integer sid;
    private String sname;
    private Integer classId;//该学生所属的班级号
}

