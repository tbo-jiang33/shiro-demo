package com.jtb.shiro.controller;

import com.jtb.shiro.FilterChainDefinitionsService;
import com.jtb.shiro.model.User;
import com.jtb.shiro.service.ApplicationContextProvider;
import com.jtb.shiro.service.UserService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * @auther: jtb
 * @date: 2019/5/25 17:26
 * @description:
 */
@Controller
public class TestController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/findUser")
    public User findUser(@RequestParam("username") String username,
                               @RequestParam("password") String password) {
        User user = userService.findByUsername(username);
        return user;
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/edit")
    public String edit() {
        return "edit";
    }
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return "login";
    }

    @RequestMapping("unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }
    @RequestMapping("/loginUser")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpSession session) {

        //先组成username和password token实例
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            User user = (User) subject.getPrincipal();
            /* 在自定义的AuthRealm的
             * User user = (User) principalCollection.fromRealm(this.getClass().getName()).iterator().next();
             * 是一一对应的，也就是说这里存到session，AuthRelm里通过上面那句话取
             */
            session.setAttribute("user", user);
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("账号或密码错误--");
            return "login";
        }

    }

    @ResponseBody
    @RequestMapping("/findPerms")
    public String findPerms() {
        List<Map<String, String>> perms =  userService.findPerms();
        return  perms.toString();
    }

    @Autowired
    FilterChainDefinitionsService filterChainDefinitionsService;

    @ResponseBody
    @RequestMapping("/perms1")
    public String perms1() {
        filterChainDefinitionsService.reloadFilterChains();
        return  "perms1";
    }

    @ResponseBody
    @RequestMapping("/perms2")
    public String perms2() {
        return "perms2";
    }

    @Autowired
    ApplicationContextProvider ac;

    @ResponseBody
    @RequestMapping("/perms3")
    public String perms3() {
        ApplicationContext ap = ac.getApplicationContext();
        Object o = ac.getBean("shiroFilter");
        return "perms3";
    }



}
