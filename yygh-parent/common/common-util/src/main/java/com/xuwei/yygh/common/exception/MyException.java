package com.xuwei.yygh.common.exception;

import com.xuwei.yygh.common.result.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description TODO
 * @Date 2021/12/22 8:34
 * @Author yxw
 */
@Data
@ApiModel(value = "自定义全局异常类")
public class MyException extends RuntimeException{

    @ApiModelProperty(value = "异常状态码")
    private Integer code;

    /**
     * 创建异常对象
     * @param message   异常信息
     * @param code      异常状态码
     */
    public MyException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public MyException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
}
