package com.jtb.shiro.mapper;

import com.jtb.shiro.model.User;

import org.apache.ibatis.annotations.Param;

/**
 * @auther: jtb
 * @date: 2019/5/12 17:37
 * @description:
 */
public interface UserMapper {

    User findByUsername(@Param("username") String username);

}
