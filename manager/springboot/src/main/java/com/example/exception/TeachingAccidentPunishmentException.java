package com.example.exception;

public class TeachingAccidentPunishmentException extends RuntimeException {
    public TeachingAccidentPunishmentException() {
        super("该教师本学期存在严重教学事故记录，已取消下学期主讲资格！");
    }
}
