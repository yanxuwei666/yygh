package com.xuwei.yygh.common.handler;

import com.xuwei.yygh.common.exception.YyghException;
import com.xuwei.yygh.common.result.Result;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author yxw
 * @Date 2022/4/1 10:38
 * @Description 自定义全局异常处理器
 */
@ControllerAdvice // 处理controller级别的异常
public class GlobalExceptionHandler {

    /**
     * 处理Exception及其以下的异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result<T> error(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }

    /**
     * 自定义异常处理方法
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(YyghException.class)
    public Result error(YyghException e) {
        return Result.build(e.getCode(), e.getMessage());
    }
}
