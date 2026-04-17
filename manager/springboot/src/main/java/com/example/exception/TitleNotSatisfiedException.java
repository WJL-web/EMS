package com.example.exception;

public class TitleNotSatisfiedException extends RuntimeException {
    public TitleNotSatisfiedException() {
        super("该课程为主课，低于“讲师”职称的教师严禁承担主讲任务！");
    }
}
