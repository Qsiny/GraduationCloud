package com.qsiny.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {

    private Long userId;
    private String username;

    private String token;

    private String reFlushToken;

    /*
    * 这里的userType先这样：
    * 00普通用户
    * 01学生
    * 02老师
    * 99系统管理员
    *
    * */
    private String userType;

}
