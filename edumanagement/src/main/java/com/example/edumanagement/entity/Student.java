package com.example.edumanagement.entity;

import lombok.Setter;

public class Student {
    @Setter
    private Integer id;
    @Setter
    private String name;
    private String student_number;

    // Getter 和 Setter
    public Integer getId() { return id; }

    public String getName() { return name; }

    public String getStudent_number() { return student_number; }
    public void setStudent_number(String studentNumber) { this.student_number = student_number; }
}
