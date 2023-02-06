package com.qsiny.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


/**
 * @author Qin
 */
@Data
@AllArgsConstructor
public class ResponseResult<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息，如果有错误时，前端可以获取该字段进行提示
     */
    private String message;
    /**
     * 查询到的结果数据，
     */
    private T data;

    /**
     * 当前时间
     */
    private Date date;

    public static <T> ResponseResult<T> build(Integer code, String message){
        return new ResponseResult<>(code,message,null,new Date());
    }
    
    public static <T> ResponseResult<T> build(Integer code, String message, T data){
        return new ResponseResult<>(code,message,data,new Date());
    }
}