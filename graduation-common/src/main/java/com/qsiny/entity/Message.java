package com.qsiny.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName sys_message
 */
@TableName(value ="sys_message")
@Data
public class Message implements Serializable {
    /**
     * 消息id
     */
    @TableId(type = IdType.AUTO)
    private Long messageId;

    /**
     * 标题
     */
    private String title;

    /**
     * 正文
     */
    private String text;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 发送人id
     */
    private Long sendUserId;

    /**
     * 发送人姓名
     */
    private String sendUserName;

    /**
     * 收件人id
     */
    private Long receiveId;

    /**
     * 收信时间
     */
    private Date receiveTime;

    /**
     * 确认时间
     */
    private Date confirmTime;

    /**
     * 0:消息，1:通知
     */
    private Long type;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人id
     */
    private Long updateId;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}