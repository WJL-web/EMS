package com.example.edumanagement.controller;

import com.example.edumanagement.entity.Student;
import com.example.edumanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Integer id) {
        return studentService.getStudentById(id);
    }

    // 按name精确查询（返回列表）
    @GetMapping("/name/{name}")
    public List<Student> getStudentsByName(@PathVariable String name) {
        return studentService.getStudentsByName(name);
    }

    // 按name统计数量（精确）
    @GetMapping("/count/name/{name}")
    public int countByName(@PathVariable String name) {
        return studentService.countStudentsByName(name);
    }
}
