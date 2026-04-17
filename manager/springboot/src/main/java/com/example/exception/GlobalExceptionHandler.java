package com.example.exception;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.example.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackages="com.example.controller")
public class GlobalExceptionHandler {

    private static final Log log = LogFactory.get();


    //统一异常处理@ExceptionHandler,主要用于Exception
    @ExceptionHandler(Exception.class)
    @ResponseBody//返回json串
    public Result error(HttpServletRequest request, Exception e){
        log.error("异常信息：",e);
        return Result.error();
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody//返回json串
    public Result customError(HttpServletRequest request, CustomException e){
        return Result.error(e.getCode(), e.getMsg());
    }

    // 拦截我们新定义的四种硬性约束异常
    @ExceptionHandler({
            DutyHoursExceededException.class,
            MaxCourseLimitException.class,
            TeachingAccidentPunishmentException.class,
            TitleNotSatisfiedException.class
    })
    @ResponseBody
    public Result handleBusinessExceptions(HttpServletRequest request, RuntimeException e) {
        log.warn("业务拦截：{}", e.getMessage());
        // 返回500，并将异常信息抛给前端展示
        return Result.error("500", e.getMessage());
    }
}
