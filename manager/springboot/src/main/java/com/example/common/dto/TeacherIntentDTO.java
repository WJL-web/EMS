package com.example.common.dto;

import java.io.Serializable;

public class TeacherIntentDTO implements Serializable {
    private Integer planId;          // 关联的教学计划ID
    private Integer teacherId;       // 申报教师ID
    private String semester;         // 申请的学期
    
    public Integer getPlanId() { return planId; }
    public void setPlanId(Integer planId) { this.planId = planId; }
    
    public Integer getTeacherId() { return teacherId; }
    public void setTeacherId(Integer teacherId) { this.teacherId = teacherId; }
    
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
}
