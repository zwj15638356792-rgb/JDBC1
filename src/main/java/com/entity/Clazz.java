package com.entity;

import lombok.Data;

import java.util.List;

@Data
public class Clazz { //
    private Integer cid;
    private String cname;
    private Integer teacherId;

    private Teacher teacher;//该班级对应的老师信息。
    private List<Student> students; //该班级下包含的学生
}
