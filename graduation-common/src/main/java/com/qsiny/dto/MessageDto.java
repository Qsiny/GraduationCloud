package com.qsiny.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MessageDto {

    private String majorCode;

    private String departmentCode;

    private String classCode;

    private String gradeCode;

    private Integer timing;

    private Date submitTime;

}
