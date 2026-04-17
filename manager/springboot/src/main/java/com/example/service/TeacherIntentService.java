package com.example.service;

import com.example.common.dto.TeacherIntentDTO;
import com.example.entity.Course;
import com.example.entity.Teacher;
import com.example.entity.TeachingPlan;
import com.example.entity.TeacherIntent;
import com.example.exception.DutyHoursExceededException;
import com.example.exception.MaxCourseLimitException;
import com.example.exception.TeachingAccidentPunishmentException;
import com.example.exception.TitleNotSatisfiedException;
import com.example.mapper.CourseMapper;
import com.example.mapper.TeacherMapper;
import com.example.mapper.TeachingPlanMapper;
import com.example.mapper.TeacherIntentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class TeacherIntentService {

    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private TeachingPlanMapper teachingPlanMapper;
    @Resource
    private TeacherIntentMapper teacherIntentMapper;

    /**
     * 教师申报授课核心入口 (包含四大硬性约束校验)
     */
    @Transactional(rollbackFor = Exception.class)
    public void submitIntent(TeacherIntentDTO dto) {
        // 1. 获取关联基础数据
        Teacher teacher = teacherMapper.selectById(dto.getTeacherId());
        TeachingPlan plan = teachingPlanMapper.selectById(dto.getPlanId());
        Course course = courseMapper.selectById(plan.getCourseId());

        // ================= 【硬性约束校验引擎】 =================

        // 约束 1: 事故惩罚 - 本学期发生过“严重教学事故”的教师，直接剥夺主讲资格
        if (teacher.getHasSevereAccident() != null && teacher.getHasSevereAccident() == 1) {
            throw new TeachingAccidentPunishmentException();
        }

        // 约束 2: 职称拦截 - 低于“讲师”职称，严禁承担主课
        if (course.getIsCore() != null && course.getIsCore() == 1) {
            if ("助教".equals(teacher.getTitle())) { 
                throw new TitleNotSatisfiedException();
            }
        }

        // 约束 3: 单人上限 - 判断本学期已经自报通过/待批的课程数量是否 >= 2
        int currentCourseCount = teacherIntentMapper.countByTeacherAndSemester(dto.getTeacherId(), dto.getSemester());
        if (currentCourseCount >= 2) {
            throw new MaxCourseLimitException();
        }

        // 约束 4: 职务拦截 - 中层干部周学时 <= 4
        if ("学院中层干部".equals(teacher.getDuty())) {
            // 获取本学期已申报的主讲课程总周学时
            int allocatedHours = teacherIntentMapper.sumWeeklyHoursByTeacher(dto.getTeacherId(), dto.getSemester());
            // 加上当前正要申请的课程学时，注意考虑空指针异常，此处假设基础数据没问题
            Integer courseWeeklyHours = course.getWeeklyHours() != null ? course.getWeeklyHours() : 0;
            if (allocatedHours + courseWeeklyHours > 4) {
                throw new DutyHoursExceededException();
            }
        }

        // ================= 【校验通过，持久化数据】 =================
        TeacherIntent intent = new TeacherIntent();
        intent.setPlanId(dto.getPlanId());
        intent.setTeacherId(dto.getTeacherId());
        intent.setApprovalStatus("SUBMITTED"); // 初始状态为已提交
        
        teacherIntentMapper.insert(intent);
    }
}