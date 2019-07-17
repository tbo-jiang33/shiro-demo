package com.jtb.shiro;

import com.jtb.shiro.service.ApplicationContextProvider;

import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.stereotype.Service;

import java.util.Map;

import javax.annotation.Resource;

/**
 * @auther: jtb
 * @date: 2019/7/17 00:59
 * @description:
 */
@Service
public class FilterChainDefinitionsService {

    @Resource
    private ShiroPermissionFactory permissionFactory;

    public void reloadFilterChains() {
        synchronized (permissionFactory) { // 强制同步控制线程安全

            /*
             * ShiroFilterFactoryBean 返回的就是此对象
             * 因为其的方法getObject()代理了getBean()，而返回的对象由其的getObjectType()决定
             * 而getObjectType()是返回SpringShiroFilter，而SpringShiroFilter继承了AbstractShiroFilter
             */
            AbstractShiroFilter shiroFilter;
            try {
                shiroFilter = (AbstractShiroFilter) permissionFactory.getObject();

                PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter
                        .getFilterChainResolver();
                // 过滤管理器
                DefaultFilterChainManager manager = (DefaultFilterChainManager) resolver.getFilterChainManager();
                // 清除权限配置
                manager.getFilterChains().clear();
                permissionFactory.getFilterChainDefinitionMap().clear();
                // 重新设置权限
                permissionFactory.setFilterChainDefinitions(ShiroPermissionFactory.filterChainDefinitions);//传入配置中的filterchains

                Map<String, String> chains = permissionFactory.getFilterChainDefinitionMap();

                //重新生成过滤链
                if (!CollectionUtils.isEmpty(chains)) {
                    for (Map.Entry<String, String> chain : chains.entrySet()) {
                        manager.createChain(chain.getKey(), chain.getValue().replace(" ", ""));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
