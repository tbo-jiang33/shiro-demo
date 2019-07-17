package com.jtb.shiro;

import com.jtb.shiro.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * @auther: jtb
 * @date: 2019/7/17 00:07
 * @description: 继承ShiroFilterFactoryBean使其能被调用实现动态加载
 */
public class ShiroPermissionFactory extends ShiroFilterFactoryBean {

    private UserService userService;

    /** 记录配置中的过滤链 */
    public static String filterChainDefinitions = "";//这个要和配置文件中的名称要一样

    public static final String PREMISSION_STRING = "perms[{0}]";

    /**
     * 初始化设置过滤链
     */
    @Override
    public void setFilterChainDefinitions(String definitions) {
        filterChainDefinitions = definitions;// 记录配置的静态过滤链
        List<Map<String, String>> perms =  userService.findPerms();

        Map<String, String> otherChains = new HashMap<>();
        for (Map<String, String> permMap : perms) {
            if (StringUtils.isNotBlank(permMap.get("url")) && StringUtils.isNotBlank(permMap.get("name"))) {
                otherChains.put(permMap.get("url"), MessageFormat.format(PREMISSION_STRING, permMap.get("name")));
            }
        }
        otherChains.put("/**", "authc");

        // 加载配置默认的过滤链
        Ini ini = new Ini();
        ini.load(filterChainDefinitions);
        Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
        if (CollectionUtils.isEmpty(section)) {
            section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        }
        // 加上数据库中过滤链
        section.putAll(otherChains);
        setFilterChainDefinitionMap(section);
    }

    // 传入UserService
    public void setUserService(UserService userServicePar) {
        this.userService = userServicePar;
    }

}
