package com.qsiny.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MajorRequestDto extends PageEntity{

    private String majorName;
}
