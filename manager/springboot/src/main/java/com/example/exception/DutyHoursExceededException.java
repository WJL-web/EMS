package com.example.exception;

public class DutyHoursExceededException extends RuntimeException {
    public DutyHoursExceededException() {
        super("学院中层干部主讲课时每周不得超过4学时！");
    }
}
