package com.qsiny.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 部门表
 * @TableName sys_major
 */
@TableName(value ="sys_major")
@Data
public class Major implements Serializable {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 专业名
     */
    private String majorName;

    /**
     * 专业英文名
     */
    private String majorEnglishName;

    /**
     * 代码
     */
    private String code;

    /**
     * 系代码
     */
    private String departmentCode;

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
     * 辅导员姓名
     */
    private String counselorName;

    /**
     * 年级代码

     */
    private String gradeCode;

    /**
     * 辅导员ID
     */
    private Long counselorId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}