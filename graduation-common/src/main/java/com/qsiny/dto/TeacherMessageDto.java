package com.qsiny.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeacherMessageDto {

    private String teacherName;

    private String teacherId;

    private LocalDateTime sendTime;

    private String message;

    private List<String> userIds;

}
