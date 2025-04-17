package com.example.edumanagement.entity;

public class Student {
    private Integer id;
    private String name;
    private String student_number;

    // Getter 和 Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStudent_number() { return student_number; }
    public void setStudent_number(String studentNumber) { this.student_number = student_number; }
}