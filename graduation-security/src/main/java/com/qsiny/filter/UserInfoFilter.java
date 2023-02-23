package com.qsiny.filter;

import com.qsiny.config.RedisCache;
import com.qsiny.constant.RedisConstant;
import com.qsiny.constant.ResponseStatusCode;
import com.qsiny.entity.CodeLoginUser;
import com.qsiny.entity.CustomizeException;
import com.qsiny.entity.PasswordLoginUser;
import com.qsiny.provider.CustomAuthenticationToken;
import com.qsiny.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
        } catch (ExpiredJwtException e) {
            response.setStatus(ResponseStatusCode.FOUND);
            return;
        }catch (Exception e){
            throw new CustomizeException("token解析异常");
        }
        String uuid = claims.getId();
        if (uuid == null) {
            throw new CustomizeException("从token中无法解析ID");
        }
        //从redis拿到userInfo
        //这里通过type来转化为不同的类吧
        Object o = redisCache.getCacheObject(RedisConstant.TOKEN_PRE + uuid);
        if(o instanceof CodeLoginUser){
            CodeLoginUser codeLoginUser = (CodeLoginUser) o;
            Collection<? extends GrantedAuthority> authorities = codeLoginUser.getAuthorities();
            //存入context方便后面来进行权限的校验
            SecurityContextHolder.getContext().setAuthentication(new CustomAuthenticationToken(codeLoginUser, null, authorities));
        }else{
            PasswordLoginUser passwordLoginUser = (PasswordLoginUser) o;
            Collection<? extends GrantedAuthority> authorities = passwordLoginUser.getAuthorities();

            //存入context方便后面来进行权限的校验
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(passwordLoginUser, null, authorities));
        }
//        String className = (String) loginUser.get("@type");
//        if (PasswordLoginUser.class.getName().equals(className)) {
//            PasswordLoginUser passwordLoginUser = JSONObject.parseObject(loginUser.toJSONString(), PasswordLoginUser.class);
//            //这里能否根据某个特定的字段来判断应该使用自己定义的token还是 用户名密码的token
//            Collection<? extends GrantedAuthority> authorities = passwordLoginUser.getAuthorities();
//
//            //存入context方便后面来进行权限的校验
//            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(passwordLoginUser, null, authorities));
//        }else{
//            CodeLoginUser codeLoginUser = JSONObject.parseObject(loginUser.toJSONString(), CodeLoginUser.class);
//            //这里能否根据某个特定的字段来判断应该使用自己定义的token还是 用户名密码的token
//            Collection<? extends GrantedAuthority> authorities = codeLoginUser.getAuthorities();
//
//            //存入context方便后面来进行权限的校验
//            SecurityContextHolder.getContext().setAuthentication(new CustomAuthenticationToken(codeLoginUser, null, authorities));
//        }

        filterChain.doFilter(request,response);
    }
}
