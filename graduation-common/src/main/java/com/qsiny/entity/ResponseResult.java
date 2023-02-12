package com.qsiny.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * @author Qin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public static <T> ResponseResult<T> build(Integer code, String message){
        return new ResponseResult<>(code,message,null,new Date());
    }
    
    public static <T> ResponseResult<T> build(Integer code, String message, T data){
        return new ResponseResult<>(code,message,data,new Date());
    }
}