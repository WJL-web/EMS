package com.example.mapper;

import com.example.entity.TeacherIntent;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TeacherIntentMapper {
    
    @Insert("insert into teacher_intent(plan_id, teacher_id, approval_status) values(#{planId}, #{teacherId}, #{approvalStatus})")
    int insert(TeacherIntent teacherIntent);

    @Select("select count(*) from teacher_intent ti " +
            "join teaching_plan tp on ti.plan_id = tp.id " +
            "where ti.teacher_id = #{teacherId} and tp.semester = #{semester} " +
            "and ti.approval_status != 'REJECTED'")
    int countByTeacherAndSemester(@Param("teacherId") Integer teacherId, @Param("semester") String semester);

    @Select("select coalesce(sum(c.weekly_hours), 0) from teacher_intent ti " +
            "join teaching_plan tp on ti.plan_id = tp.id " +
            "join course c on tp.course_id = c.id " +
            "where ti.teacher_id = #{teacherId} and tp.semester = #{semester} " +
            "and ti.approval_status != 'REJECTED'")
    int sumWeeklyHoursByTeacher(@Param("teacherId") Integer teacherId, @Param("semester") String semester);
}