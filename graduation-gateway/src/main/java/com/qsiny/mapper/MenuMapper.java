package com.qsiny.mapper;

import com.qsiny.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Qin
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2023-02-05 09:48:52
* @Entity com.qsiny.entity.Menu
*/
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(@Param("id") Long id);
}




