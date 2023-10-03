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
 * @TableName sys_user_apply_info
 */
@TableName(value ="sys_user_apply_info")
@Data
public class UserApplyInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 申请类别
     */
    private String applyType;

    /**
     * 申请角色
     */
    private String applyRole;

    /**
     * 学院代码
     */
    private String majorCode;

    /**
     * 学院名称
     */
    private String majorName;

    /**
     * 专业代码
     */
    private String departmentCode;

    /**
     * 专业名称
     */
    private String departmentName;

    /**
     * 年级代码
     */
    private String gradeCode;

    /**
     * 年级名称
     */
    private String gradeName;

    /**
     * 班级代码
     */
    private String classCode;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 家庭住址
     */
    private String homeAddress;

    /**
     * 民族
     */
    private String national;

    /**
     * 决定人id
     */
    private Long decideUserId;

    /**
     * 决定人姓名
     */
    private String decideUserName;

    /**
     * 0:待审核，1:审核通过 2:审核不通过
     */
    private Integer result;

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