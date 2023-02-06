package com.qsiny.enums;

import lombok.Getter;

import java.util.List;

/**
 * @author Qin
 * @date 2023/2/5 19:07
 */
@Getter
public enum UrlRoleEnum {
    /**
     * 学院号对应的学院名称
     */
    TEST_MESSAGE("/api/demo/test/12","11"),
    SEND_MESSAGE("/portal/sendMessage","sendMessage");

    private final String url;
    private final String role;

    UrlRoleEnum(String url, String role) {
        this.url = url;
        this.role = role;
    }

    /**
     * todo 考虑之后转移到redis
     * 检查访问的URL和权限是否匹配
     * @param url
     * @param role
     * @return
     */
    public static boolean checkRole(String url,String role){
        for (UrlRoleEnum value : UrlRoleEnum.values()) {
            if( value.getUrl().equals(url) && value.getRole().equals(role)){
               return true;
            }
        }
        return false;
    }


    /**
     * 这里判断是否需要校验
     * @param url 请求的URL
     * @return boolean
     */
    public static boolean needCheck(String url){
        for (UrlRoleEnum value : UrlRoleEnum.values()) {
            if(value.getUrl().equals(url)){
                return true;
            }
        }
        return false;
    }

    public static boolean hasRole(String role, List<String> userRoles) {
        return userRoles.contains(role);
    }
}
