package com.example.edumanagement.entity;

import java.io.Serializable;

public class Speciality implements Serializable{
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String content;
    private Integer collegeId;
    private Integer score;

    private String collegeName;

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}

    public Integer getCollegeId() {return collegeId;}

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    public Integer getScore() {return score;}

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCollegeName() {return collegeName;}

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
}
