package com.example.entity;

import java.io.Serializable;

/**
 * 班级信息表
*/
public class Classes extends Account implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    private String name;
    private String content;
    private Integer teacherId;
    private Integer specialtyId;

    private String teacherName;
    private String specialtyName;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}

    public Integer getTeacherId(){return teacherId;}

    public void setTeacherId(Integer teacherId){this.teacherId = teacherId;}

    public Integer getSpecialtyId(){return specialtyId;}

    public void setSpecialtyId(Integer specialtyId){this.specialtyId = specialtyId;}

    public String getTeacherName(){return teacherName;}

    public void setTeacherName(String teacherName){this.teacherName = teacherName;}

    public String getSpecialtyName(){return specialtyName;}

    public void setSpecialtyName(String specialtyName){this.specialtyName = specialtyName;}

}