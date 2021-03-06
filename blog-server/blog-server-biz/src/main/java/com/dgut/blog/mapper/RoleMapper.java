package com.dgut.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dgut.blog.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: lishengdian | 932978775@qq.com
 * @version: 1.0.0
 * @description: 用户角色类mapper文件
 * @createDate: 2021/2/25
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getRolesByUserId(Long userId);

}
