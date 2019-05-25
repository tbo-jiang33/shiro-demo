package com.jtb.shiro.service;

import com.jtb.shiro.model.User;

import org.springframework.stereotype.Service;


/**
 * @auther: jtb
 * @date: 2019/5/12 17:38
 * @description:
 */
public interface UserService {

    User findByUsername(String username);

}
