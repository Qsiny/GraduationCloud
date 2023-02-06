package com.qsiny.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Qin
 * @date 2023/2/5 16:48
 */
@Data
public class LoginUser  {
    private final User user;

    private final List<String> permissions;

    public List<String> getPermissions() {
        return permissions;
    }

    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getUserName();
    }

    //todo 修改这样
    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
