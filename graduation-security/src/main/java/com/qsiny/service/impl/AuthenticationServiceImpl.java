package com.qsiny.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qsiny.entity.Menu;
import com.qsiny.mapper.MenuMapper;
import com.qsiny.service.AuthenticationService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

public class AuthenticationServiceImpl implements AuthenticationService {

    @Resource
    private MenuMapper menuMapper;
    @Override
    public Boolean hasAuthentication(String url) {

        //获取对于url所需要的权限
        Menu menu = menuMapper.selectOne(new QueryWrapper<Menu>().lambda().eq(Menu::getPath, url));
        List<String> menus = Arrays.asList(menu.getPerms().split(","));
        //跟现在所拥有的权限做对比，查看是否有交集，如果有，则通过


        return null;
    }
}
