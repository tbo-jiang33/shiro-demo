package com.jtb.shiro.service;

import com.jtb.shiro.mapper.UserMapper;
import com.jtb.shiro.model.User;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @auther: jtb
 * @date: 2019/5/12 17:40
 * @description:
 */
@Service
public class UserServiceImp implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
