package com.jtb.shiro.shiroCustomRealm;

import com.alibaba.druid.pool.DruidDataSource;
import com.jtb.shiro.CredentialMatcher;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @auther: jtb
 * @date: 2019/6/27 00:32
 * @description:
 */
public class CustomRealmTest {

    @Test
    public void testAuthentictor() {

        // 1.构建securityManage环境，
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        SecurityUtils.setSecurityManager(securityManager); // 设置环境
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(new CredentialMatcher());
        securityManager.setRealm(customRealm); // 设置realm

        // 2.主体提交认证
        UsernamePasswordToken token = new UsernamePasswordToken("jtb1", "123");
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        System.out.println("isAuthenticated：" + subject.isAuthenticated());
        subject.checkRole("user");
        subject.checkPermissions("select", "update");
    }
}
