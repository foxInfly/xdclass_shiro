package net.xdclass.xdclass_shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author : lipu
 * @since : 2020-08-22 20:40
 */
public class CustomRealm extends AuthorizingRealm {

    private final Map<String,String> userInfonMap = new HashMap<>();
    {
        userInfonMap.put("jack","123");
        userInfonMap.put("xdclass","456");
    }
    private final Map<String,Set<String>> permissionMap = new HashMap<>();
    {
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        set1.add("video:find");
        set1.add("video:buy");

        set2.add("video:add");
        set2.add("video:delete");

        permissionMap.put("jack",set1);
        permissionMap.put("xdclass",set2);
    }

    private final Map<String,Set<String>> roleMap = new HashMap<>();
    {
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        set1.add("role1");
        set1.add("role2");

        set2.add("root");
        set2.add("admin");

        roleMap.put("jack",set1);
        roleMap.put("xdclass",set2);
    }


    //权限 AuthorizationInfo角色权限西
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("开始授权doGetAuthorizationInfo:");
        String name = (String) principals.getPrimaryPrincipal();

        //模拟从数据库获取权限
        Set<String> permissions = getPermissionsByUserNameFormDB(name);

        //模拟从数据库获取角色
        Set<String> roles = getRolesByUserNameFormDB(name);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }

    private Set<String> getRolesByUserNameFormDB(String name) {
        return roleMap.get(name);
    }

    private Set<String> getPermissionsByUserNameFormDB(String name) {
        return permissionMap.get(name);
        
    }

    //登录认证  AuthenticationInfo认证信息
    //UsernamePasswordToken--> HostAuthenticationToken-->AuthenticationToken
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("开始认证doGetAuthenticationInfo:");
        //从token中获取用户信息
        String name = (String) token.getPrincipal();

        //模拟从数据库获取密码
        String pwd = getPwdByUserNameFormDB(name);

        if (pwd == null || "".equals(pwd)) {
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, pwd, this.getName());

        return simpleAuthenticationInfo;
    }

    private String getPwdByUserNameFormDB(String name) {
        return userInfonMap.get(name);
    }
}
