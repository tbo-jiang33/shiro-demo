package com.jtb.shiro.controller;

import com.jtb.shiro.model.User;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @auther: jtb
 * @date: 2019/5/25 17:26
 * @description:
 */
@Controller
public class TestController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
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
            return "login";
        }

    }


}
