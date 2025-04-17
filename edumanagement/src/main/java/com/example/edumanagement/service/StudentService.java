package com.example.edumanagement.service;

import com.example.edumanagement.entity.Student;
import com.example.edumanagement.mapper.StudentMapper; // 或 StudentRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentMapper studentMapper; // 如果用 JPA，改为 StudentRepository

    public Student getStudentById(Integer id) {
        return studentMapper.findById(id); // 如果用 JPA，改为 studentRepository.findById(id).orElse(null)
    }
}