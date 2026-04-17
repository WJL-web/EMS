package com.example.controller;

import com.example.common.Result;
import com.example.common.dto.TeacherIntentDTO;
import com.example.service.TeacherIntentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 教师意向(授课安排)前端操作接口
 **/
@RestController
@RequestMapping("/teacherIntent")
public class TeacherIntentController {

    @Resource
    private TeacherIntentService teacherIntentService;

    /**
     * 教师申报授课计划
     */
    @PostMapping("/submit")
    public Result submitIntent(@RequestBody TeacherIntentDTO dto) {
        teacherIntentService.submitIntent(dto);
        return Result.success();
    }
}