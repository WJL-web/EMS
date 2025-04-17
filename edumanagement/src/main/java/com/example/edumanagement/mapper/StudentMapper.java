package com.example.edumanagement.mapper;

import com.example.edumanagement.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentMapper {
    @Select("SELECT * FROM student WHERE id = #{id}")
    Student findById(Integer id);
}