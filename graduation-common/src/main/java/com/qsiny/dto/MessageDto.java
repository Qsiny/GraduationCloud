package com.qsiny.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MessageDto<T> {


    //这里是传入的一个对象
    private T t;

    /**
     * type: 0：普通消息 1：定时消息
     */
    private Integer type;

    private Date sendTime;
//    private String majorCode;
//
//    private String departmentCode;
//
//    private String classCode;
//
//    private String gradeCode;
//
//    /**
//     * 标题
//     */
//    private String title;
//
//    /**
//     * 正文
//     */
//    private String text;
//
//    /**
//     * 发送时间
//     */
//    private Date sendTime;
//
//    /**
//     * 发送人id
//     */
//    private Long sendUserId;
//
//    /**
//     * 发送人姓名
//     */
//    private String sendUserName;
//
//    /**
//     * 收件人id
//     */
//    private List<Long> receiveIds;


}
