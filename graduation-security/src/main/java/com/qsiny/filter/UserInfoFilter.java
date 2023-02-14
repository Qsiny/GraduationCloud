package com.qsiny.filter;

import com.qsiny.config.RedisCache;
import com.qsiny.constant.RedisConstant;
import com.qsiny.entity.CustomizeException;
import com.qsiny.entity.LoginUser;
import com.qsiny.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Resource
    private RedisTemplate redisTemplate;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
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
            if(e.getCause() instanceof ExpiredJwtException){
                response.setStatus(501);
                return;
            }
            throw new CustomizeException("token解析异常");
        }
        String uuid = claims.getId();
        if (uuid == null) {
            throw new CustomizeException("从token中无法解析ID");
        }
        //从redis拿到userInfo
        LoginUser loginUser = redisCache.getCacheObject(RedisConstant.TOKEN_PRE+uuid);
        Collection<? extends GrantedAuthority> authorities = loginUser.getAuthorities();

        //存入context方便后面来进行权限的校验
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(loginUser,null,authorities));
        filterChain.doFilter(request,response);
    }
}
