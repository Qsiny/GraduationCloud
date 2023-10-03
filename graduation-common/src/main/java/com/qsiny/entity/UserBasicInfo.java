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
 * @TableName sys_user_basic_info
 */
@TableName(value ="sys_user_basic_info")
@Data
public class UserBasicInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

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
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactPhone;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 创建人id
     */
    private String createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人id
     */
    private String updateId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 民族
     */
    private String national;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}