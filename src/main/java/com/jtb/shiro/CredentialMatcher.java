package com.jtb.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @auther: jtb
 * @date: 2019/5/20 23:03
 * @description: 密码校验规则的重写
 */
public class CredentialMatcher extends SimpleCredentialsMatcher {

    /**
     *
     * @param token 登录时的token
     * @param info 数据库的用户名密码
     * @return 是否比对成功
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String password = new String(usernamePasswordToken.getPassword());
        String dbPassword = (String) info.getCredentials();
        return this.equals(password, dbPassword);
    }
}
