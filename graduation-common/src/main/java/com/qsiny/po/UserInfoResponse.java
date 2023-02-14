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

}
