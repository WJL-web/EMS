package com.example.edumanagement.service;

import com.example.edumanagement.entity.Student;
import com.example.edumanagement.mapper.StudentMapper; // 或 StudentRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class StudentService {
    @Autowired
    private StudentMapper studentMapper; // 如果用 JPA，改为 StudentRepository

    public Student getStudentById(Integer id) {
        return studentMapper.findById(id); // 如果用 JPA，改为 studentRepository.findById(id).orElse(null)
    }

    // 按name查询学生列表（精确匹配）
    public List<Student> getStudentsByName(String name) {
        return studentMapper.findByName(name);
    }

    // 按name统计学生数量（精确匹配）
    public int countStudentsByName(String name) {
        return studentMapper.countByName(name);
    }


}
