package net.xdclass.xdclass_shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * 单元测试用例执行顺序
 *
 * @author : lipu
 * @since : 2020-08-22 15:43
 */
public class QuickStartTest5_2 {



    @Test
    public void testAuthentication() {
        //创建Securitymanager工厂，通过配置文件ini创建
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");

        SecurityManager securityManager = factory.getInstance();

        //将securityManager 设置到当前运行环境中
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();

        //用户输入的账号密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("xdclass", "123");

        subject.login(usernamePasswordToken);

        System.out.println("认证结果："+subject.isAuthenticated());

        //是否有某个角色
        boolean hasRole = subject.hasRole("user");
        System.out.println("是否有user角色："+hasRole);
        System.out.println("主体名 ："+subject.getPrincipal());

        subject.checkRole("admin");

        //权限
        subject.checkPermission("video:delete");
        System.out.println("是否有video:delete的权限："+subject.isPermitted("video:delete"));

        subject.logout();
        System.out.println("认证结果："+subject.isAuthenticated());
    }

}
