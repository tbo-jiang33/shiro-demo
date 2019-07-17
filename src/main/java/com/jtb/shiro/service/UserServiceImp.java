package com.jtb.shiro.service;

import com.jtb.shiro.mapper.UserMapper;
import com.jtb.shiro.model.User;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * @auther: jtb
 * @date: 2019/5/12 17:40
 * @description:
 */
@Service("userServiceImpl")
public class UserServiceImp implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public List<Map<String, String>> findPerms() {
        return userMapper.findPerms();
    }
}
