package com.example.controller;

import com.example.common.Result;
import com.example.entity.TeachingPlan;
import com.example.mapper.TeachingPlanMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/teachingPlan")
public class TeachingPlanController {

    @Resource
    private TeachingPlanMapper teachingPlanMapper;

    /**
     * 查询所有待安排的教学计划，供教师选择
     */
    @GetMapping("/selectAll")
    public Result selectAll() {
        // 直接使用 mapper 简写查询
        List<TeachingPlan> plans = teachingPlanMapper.selectAllWithCourse();
        return Result.success(plans);
    }
}
