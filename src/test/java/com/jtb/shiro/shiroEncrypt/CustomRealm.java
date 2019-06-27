package com.jtb.shiro.shiroEncrypt;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @auther: jtb
 * @date: 2019/6/26 22:56
 * @description: shiro自定义Realm练习,
 * 因为AuthorizingRealm继承了AuthenticatingRealm，所以我们这里只需继承AuthorizingRealm即可
 */
public class CustomRealm extends AuthorizingRealm {

    private Map<String, String> userMap = new HashMap<>();
    private Map<String, Set<String>> rolesMap = new HashMap<>();
    private Map<String, Set<String>> permissionsMap = new HashMap<>();
    {
        // 模拟数据库设置账户密码
        userMap.put("jtb", "2da641741f04d3beee9d4c4ca1228cf0");
        userMap.put("张三", "2da641741f04d3beee9d4c4ca1228cf0");

        // 角色
        Set<String> roles = new HashSet<>();
        Set<String> roles2 = new HashSet<>();
        rolesMap.put("jtb", roles);
        rolesMap.put("张三", roles2);
        roles.add("admin");
        roles.add("user");
        roles2.add("user");
        // 权限
        Set<String> permissions = new HashSet<>();
        permissions.add("select");
        permissions.add("update");
        permissionsMap.put("admin", permissions);
    }

    /**
     * 授权，目的，把数据库的角色和权限都返回出去
     * @param principals 主体集合，一般是username,uuid,身份证等
     * @return AuthorizationInfo 对象 数据库里的角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        /**
         * 简单的测试一般只有一个主体(注意：这里指一个请求只有一个主题)，高级的咱也不知道应用场景...
         * so...这里principals就代表只有一个了
         */
        String username = (String) principals.fromRealm(this.getClass().getName()).iterator().next();
        // 模拟数据库查Role集合
        Set<String> roles = findRolesByUsername(username);
        Set<String> permissionSet = new HashSet<>();
        for (String role : roles) {
            Set<String> permissions = findPermissionByroleName(role);
            if (permissions != null && permissions.size() != 0) {
                permissionSet.addAll(permissions);
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(permissionSet);
        return info;
    }

    /**
     * 认证
     * @param token 主体传过来的认证信息，可向下转换成UsernamepasswordToken对象，也可直接token.getPrincipal()
     *              获取主体name即username
     * @return  实现了AuthenticationInfo的对象,在这里返回SimpleAuthenticationInfo即可，因为其实现了前者
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取凭证名称
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        String password = findUserByName(username);

        // 在数据库找不到这个用户
        if (password == null) {
            return null;
        }

        /*
         * 构造返回对象，直接返回数据库的username和password
         * 并会执行login操作后在AuthenticatingRealm类的 assertCredentialsMatch(token, info);方法传login的token进去，
         * 然后：assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info)方法进行token和
         * 这里返回的info进行比对
         */
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, this.getClass().getName());
        info.setCredentialsSalt(ByteSource.Util.bytes("jtbSalt"));
        return info;
    }

    /**
     * 模拟数据库查询密码，如用户不存在则返回null
     * @param username 传进来的用户名
     * @return 返回password 如用户不存在则返回null
     */
    private String findUserByName(String username) {
        return userMap.get(username);
    }

    /**
     * 模拟数据库角色，如用户不存在则返回null
     * @param username 传进来的用户名
     * @return 返回角色 如用户不存在则返回null
     */
    private Set<String> findRolesByUsername(String username) {
        return rolesMap.get(username);
    }

    /**
     * 模拟数据库权限，如用户不存在则返回null
     * @param roleName 传进来的用户名
     * @return 返回角色 如用户不存在则返回null
     */
    private Set<String> findPermissionByroleName(String roleName) {
        return permissionsMap.get(roleName);
    }

    public static void main(String[] args) {
        //加密，并加盐
        Md5Hash md5Hash = new Md5Hash("123", "jtbSalt");
        System.out.println(md5Hash.toString());
    }
}
