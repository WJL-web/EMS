package com.example.mapper;

import com.example.entity.TeachingPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface TeachingPlanMapper {
    @Select("select * from teaching_plan where id = #{id}")
    TeachingPlan selectById(Integer id);

    @Select("SELECT tp.*, c.name AS courseName, c.type AS courseType, c.weekly_hours AS weeklyHours " +
            "FROM teaching_plan tp LEFT JOIN course c ON tp.course_id = c.id " +
            "WHERE tp.status != 'FINISHED'")
    List<TeachingPlan> selectAllWithCourse();
}