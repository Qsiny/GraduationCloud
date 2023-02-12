package com.qsiny.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qsiny.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Qin
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2023-02-05 09:48:52
* @Entity com.qsiny.entity.Role
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {



}




