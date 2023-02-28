package com.qsiny.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 班级表
 * @TableName sys_class
 */
@TableName(value ="sys_class")
@Data
public class Class implements Serializable {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 班级名
     */
    private String className;

    /**
     * 代码
     */
    private String code;

    /**
     * 年级代码
     */
    private String gradeCode;

    /**
     * 班长ID
     */
    private Long monitorId;

    /**
     * 班长名称
     */
    private String monitorName;

    /**
     * 创建人
     */
    private String createId;

    /**
     * 创建时间
     */
    private Date createData;

    /**
     * 更新人
     */
    private String updateId;

    /**
     * 更新时间
     */
    private Date updateData;
    /**
     * 年级名
     */
    private String gradeName;
    /**
     * 专业代码
     */
    private String MajorCode;
    /**
     * 专业名称
     */
    private String MajorName;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}