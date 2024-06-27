package com.njz.config;

import com.njz.http.ResponseBean;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
public class GlobalExceptionHandler {
    // 异常拦截

    @ExceptionHandler(Exception.class)
    public ResponseBean<Void> ExceptionHandler(BindException e){
        // 如果拦截的异常是我们定义的异常 则直接return
        String s = ((BindException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseBean.fail(s);
    }
}
