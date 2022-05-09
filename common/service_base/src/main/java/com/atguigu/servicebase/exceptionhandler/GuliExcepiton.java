package com.atguigu.servicebase.exceptionhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName GuliExcepiton
 * @Description TODO
 * @Author zj
 * @Date 2022/5/6 10:24
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliExcepiton extends RuntimeException{
    @ApiModelProperty(value = "状态码")
    private Integer code;

    private String msg;
}
