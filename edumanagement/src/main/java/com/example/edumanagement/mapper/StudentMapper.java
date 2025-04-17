package com.example.edumanagement.mapper;

import com.example.edumanagement.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
@Mapper
public interface StudentMapper {
    @Select("SELECT * FROM student WHERE id = #{id}")
    Student findById(Integer id);

    // 查询指定name的学生（精确匹配）
    @Select("SELECT * FROM student WHERE name = #{name}")
    List<Student> findByName(String name);  // 返回列表（可能有重名学生）

    // 统计指定name的学生数量（精确匹配）
    @Select("SELECT COUNT(*) FROM student WHERE name = #{name}")
    int countByName(String name);
}
