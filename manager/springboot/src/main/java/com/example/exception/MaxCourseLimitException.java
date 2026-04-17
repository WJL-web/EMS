package com.example.exception;

public class MaxCourseLimitException extends RuntimeException {
    public MaxCourseLimitException() {
        super("每位教师主讲课程门数每学期不得超过2门！");
    }
}
