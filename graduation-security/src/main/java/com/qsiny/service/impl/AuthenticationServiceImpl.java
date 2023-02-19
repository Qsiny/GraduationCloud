package com.qsiny.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qsiny.entity.Menu;
import com.qsiny.mapper.MenuMapper;
import com.qsiny.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Resource
    private MenuMapper menuMapper;
    @Override
    public Boolean hasAuthentication(String url) {

        //获取对于url所需要的权限
        Menu menu = menuMapper.selectOne(new QueryWrapper<Menu>().lambda().eq(Menu::getPath, url));
        //这里如果没有配置的话，那就都不可以进入
        if(menu== null||!StringUtils.hasText(menu.getPerms())){
            log.info("当前url暂未配置");
            return false;
        }
        String perms = menu.getPerms();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(perms);
        //跟现在所拥有的权限做对比，查看是否有交集，如果有，则通过
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().contains(simpleGrantedAuthority);
    }
}
