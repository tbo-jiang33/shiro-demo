package com.jtb.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @auther: jtb
 * @date: 2019/6/24 21:03
 * @description: shiro的认证测试类
 */
public class ShiroTest {

    // 测试用，因此用最简单的SimpleAccountRealm
    SimpleAccountRealm realm = new SimpleAccountRealm();

    // Junit 用的注解，在执行@Test前调用
    @Before
    public void addUser() {
        realm.addAccount("jtb", "123", "admin", "user");
    }

    @Test
    public void testAuthentictor() {
        // 1.构建securityManage环境，
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        SecurityUtils.setSecurityManager(securityManager); // 设置环境
        securityManager.setRealm(realm); // 设置realm

        // 2.主体提交认证
        UsernamePasswordToken token = new UsernamePasswordToken("jtb", "123");
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        System.out.println("isAuthenticated：" + subject.isAuthenticated());

        subject.checkRoles("admin", "user");

        subject.logout();
        System.out.println("isAuthenticated：" + subject.isAuthenticated());

    }
}
