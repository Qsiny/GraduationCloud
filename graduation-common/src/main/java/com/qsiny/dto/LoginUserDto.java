package com.qsiny.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {

    private String account;

    private String voucher;

    private Boolean rememberMe;

    private Integer loginWay;
}
