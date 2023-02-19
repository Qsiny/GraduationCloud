package com.qsiny.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 学院表
 * @TableName sys_department
 */
@TableName(value ="sys_department")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department implements Serializable {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 学院中文名
     */
    private String departmentName;

    /**
     * 学院英文名
     */
    private String departmentEnglishName;

    /**
     * 代码
     */
    private String code;

    /**
     * 院长
     */
    private String dean;

    /**
     * 院长id
     */
    private Long deanId;

    /**
     * 建立日期
     */
    private Date buildTime;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 创建者
     */
    private String createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateId;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}