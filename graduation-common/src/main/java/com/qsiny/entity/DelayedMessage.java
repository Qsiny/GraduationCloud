package com.qsiny.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName sys_delayed_message
 */
@TableName(value ="sys_delayed_message")
@Data
public class DelayedMessage implements Serializable {
    /**
     * 消息id
     */
    @TableId
    private Integer id;

    /**
     * 交换机名称
     */
    private String exchangeName;

    /**
     * 路由key
     */
    private String routingKey;

    /**
     * 预计发送时间
     */
    private Date sendTime;

    /**
     * 是否发送(0:为发送,1:已发送)
     */
    private Integer isSend;

    /**
     * 发送实体
     */
    private String messageValues;

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