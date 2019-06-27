package com.jtb.shiro.shiroEncrypt;

import com.jtb.shiro.CredentialMatcher;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @auther: jtb
 * @date: 2019/6/27 00:32
 * @description:
 */
public class CustomRealmEncryptTest {

    @Test
    public void testAuthentictor() {

        // 1.构建securityManage环境，
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        SecurityUtils.setSecurityManager(securityManager); // 设置环境
        CustomRealm customRealm = new CustomRealm();
        // 加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashIterations(1); // 加密次数
        matcher.setHashAlgorithmName("md5"); // 加密算法

        customRealm.setCredentialsMatcher(matcher);
        securityManager.setRealm(customRealm); // 设置realm

        // 2.主体提交认证
        UsernamePasswordToken token = new UsernamePasswordToken("jtb", "123");
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        System.out.println("isAuthenticated：" + subject.isAuthenticated());
        subject.checkRole("user");
        subject.checkPermissions("select", "update");
    }
}
