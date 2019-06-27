package com.jtb.shiro;

import com.alibaba.druid.pool.DruidDataSource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @auther: jtb
 * @date: 2019/6/25 12:16
 * @description: shiro默认Realm-JdbcRealm
 */
public class ShiroTestJdbcRealm {

    JdbcRealm jdbcRealm = new JdbcRealm();
    DruidDataSource dataSource = new DruidDataSource();
    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/mytest?characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);
    }

    @Test
    public void testAuthentictor() {
        String findUser = "select password from test_user where username = ?";
        String findUserRole = "select role_name from test_role where username = ?";
        String findRolePermission = "select permission from test_permisson where role_name = ?";
        jdbcRealm.setAuthenticationQuery(findUser);
        jdbcRealm.setUserRolesQuery(findUserRole);
        jdbcRealm.setPermissionsQuery(findRolePermission);

        // 1.构建securityManage环境，
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        SecurityUtils.setSecurityManager(securityManager); // 设置环境
        securityManager.setRealm(jdbcRealm); // 设置realm

        // 2.主体提交认证
        UsernamePasswordToken token = new UsernamePasswordToken("李四", "321");
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        System.out.println("isAuthenticated：" + subject.isAuthenticated());

        subject.checkRoles("user");
        subject.checkPermissions("select","update");
        //System.out.println("isAuthenticated：" + subject.isAuthenticated());
        //subject.logout();

    }
}
