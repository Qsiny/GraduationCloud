package com.qsiny.filter;

import com.qsiny.config.RedisCache;
import com.qsiny.entity.CustomizeException;
import com.qsiny.entity.LoginUser;
import com.qsiny.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class UserInfoFilter extends OncePerRequestFilter {

    @Resource
    private RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("authorization");
        if (!StringUtils.hasText(token)) {
            //401 这里就是需要权限但是没有token，则需要登录给一个token
            filterChain.doFilter(request,response);
            return;
//            return response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        //解析token
        Claims claims;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            throw new CustomizeException("token解析异常");
        }
        String userId = claims.getId();
        if (userId == null) {
            throw new CustomizeException("从token中无法解析用户ID");
        }
        //从redis拿到userInfo
        LoginUser loginUser = redisCache.getCacheObject(userId);
        Collection<? extends GrantedAuthority> authorities = loginUser.getAuthorities();

        //存入context方便后面来进行权限的校验

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(loginUser,null,authorities));
//        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(loginUser,null,authorities));
        filterChain.doFilter(request,response);
    }
}