package com.example.entity;

import java.io.Serializable;
import java.util.Date;

public class TeachingPlan implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String semester;
    private Integer courseId;
    private Integer classesId;
    private Integer totalHours;
    private String status;
    private java.util.Date createTime;
    private java.util.Date updateTime;

    // 非表字段补充
    private String courseName;
    private String courseType;
    private Integer weeklyHours;

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getCourseType() { return courseType; }
    public void setCourseType(String courseType) { this.courseType = courseType; }

    public Integer getWeeklyHours() { return weeklyHours; }
    public void setWeeklyHours(Integer weeklyHours) { this.weeklyHours = weeklyHours; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }

    public Integer getClassesId() { return classesId; }
    public void setClassesId(Integer classesId) { this.classesId = classesId; }

    public Integer getTotalHours() { return totalHours; }
    public void setTotalHours(Integer totalHours) { this.totalHours = totalHours; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public Date getUpdateTime() { return updateTime; }
    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
}