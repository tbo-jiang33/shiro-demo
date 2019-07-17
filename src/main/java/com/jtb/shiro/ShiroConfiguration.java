package com.jtb.shiro;

import com.jtb.shiro.model.User;
import com.jtb.shiro.service.UserService;

import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @auther: jtb
 * @date: 2019/5/20 23:06
 * @description: shiro核心配置类，如果不用springboot则是在xml配置的
 */
@Configuration  // 项目启动时自动配置这里定义的类
public class ShiroConfiguration {

    @Autowired
    private UserService userService;

    /**
     * ShiroFilter的工厂Bean 允许我们定义自己的SecurityManger,
     *  而SecurityManger 以自己期望的方式来验证一个用户，
     *  除此之外定义了我们登陆的url，登陆成功之后的url,和定义任何一个接口该做什么样的权限验证
     * @param securityManager
     * @return
     *
     * 关于拦截器的含义：
     * 在DefaultFilter类中有多个枚举，是Shiro默认使用的所有拦截器
     * 包括Form表单验证，基础的HTTP验证等等，
     * 一般用得比较多的是anon,authc,logout,perms,roles
     * 而此方法用到的完全取决于DefaultFilter的枚举name。
     * 代表符合条件的url使用后面定义的拦截器
     */
    /*@Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        //设置登录的url
        shiroFilter.setLoginUrl("/login");
        //设置登录成功后的url
        shiroFilter.setSuccessUrl("/index");
        //设置没有权限访问跳转的url
        shiroFilter.setUnauthorizedUrl("/unauthorized");

        *//*
         * 配置某些请求怎么进行拦截
         * key: 正则表达式，代表我们访问的请求
         * value: 代表拦截器
         * 如/index界面，必须登录则使用authc拦截器
         * 而/login是不需要登录的，则使用anon拦截器
         *
         * ("/admin", "roles[admin]") 访问admin接口时因为指定只有admin用户才能访问，
         *      所以会调用hasRole方法进行验证，在《跟我学Shiro》中28页有说，并追踪授权代码得出结论
         * 同理，("/edit", "perms[edit]")访问edit接口只有拥有edit资源（权限）的用户才能访问，
         *      所以会调用isPermitted方法进行验证
         *//*
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/index", "authc");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/loginUser", "anon");
        filterChainDefinitionMap.put("/admin", "roles[admin]"); // admin接口需要admin角色才能访问
        filterChainDefinitionMap.put("/edit", "perms[edit]"); // edit接口需要有edit权限的才能访问
        filterChainDefinitionMap.put("/findPerms", "anon"); // edit接口需要有edit权限的才能访问
        List<Map<String, String>> perms =  userService.findPerms();
        for (Map<String, String> permMap : perms) {
            filterChainDefinitionMap.put(permMap.get("url"), "perms[ " + permMap.get("name") + " ]");
        }
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
        filterChainDefinitionMap.put("/**", "user");
        return shiroFilter;
    }*/

    /**
     * 上面那个方法是只有根据数据库加载url配置过滤器链的情况
     * 而此方法将加载url配置过滤器链分离出另一个类，使其能够获取到本类并动态加载
     * @param securityManager
     * @return
     */
    @Bean("shiroFilter")
    public ShiroPermissionFactory shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroPermissionFactory shiroFilter = new ShiroPermissionFactory();
        shiroFilter.setSecurityManager(securityManager);
        //设置登录的url
        shiroFilter.setLoginUrl("/login");
        //设置登录成功后的url
        shiroFilter.setSuccessUrl("/index");
        //设置没有权限访问跳转的url
        shiroFilter.setUnauthorizedUrl("/unauthorized");

        // 设置默认过滤器链，被ini读取
        String filterStr = "/index=authc\n" +
                "/login=anon\n" +
                "/loginUser=anon\n" +
                "/admin=roles[admin]\n" +
                //"/findPerms=perms[edit]\n" +
                "/edit=perms[edit]\n" +
                "/logout=anon";
        // 先给ShiroPermissionFactory实例传递userService实例
        shiroFilter.setUserService(userService);
        // 设置数据库的过滤器链
        shiroFilter.setFilterChainDefinitions(filterStr);
        return shiroFilter;
    }

    /**
     * Shiro的上下文，被ShiroFilter使用
     * @param authRealm
     * @return
     */
    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm) {
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(authRealm);
        return defaultSecurityManager;
    }

    /**
     * 自己定义的Realm 被SecurityManager使用
     * @param credentialMatcher 密码比较器
     * @return 自定义的Realm
     */
    @Bean("authRealm")
    public AuthRealm authRealm(@Qualifier("credentialMatcher") CredentialMatcher credentialMatcher) {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(credentialMatcher);
        return authRealm;
    }

    // 密码比较器 被自定义的Realm使用
    @Bean("credentialMatcher")
    public CredentialMatcher credentialMatcher() {
        return new CredentialMatcher();
    }

    /**
     * 添加Spring AOP权限注解的支持，配置后即可在控制层使用如：@RequiresRoles("admin")等
     * @param securityManager securityManager
     * @return
     * 如若不使用Springboot则在spring-mvc.xml里配置：
     * <aop:config proxy-target-class="true"></aop:config>
     * <bean class="
     * org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
     *     <property name="securityManager" ref="securityManager"/>
     * </bean>
     *
     * 例：访问hello2方法的前提是当前用户有admin角色。
     * //@RequiresRoles("admin")
     * //@RequestMapping("/hello2")
     * //public String hello2() {
     * //    return "success";
     * //}
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * Spring的代理
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

}

