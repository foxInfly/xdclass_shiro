package net.xdclass.xdclass_shiro;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * 单元测试用例执行顺序
 *
 * @author : lipu
 * @since : 2020-08-22 15:43
 */
public class QuickStartTest5_3 {


    @Test
    public void testAuthentication() {
        //创建Securitymanager工厂，通过配置文件ini创建
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:jdbcrealm.ini");

        SecurityManager securityManager = factory.getInstance();

        //将securityManager 设置到当前运行环境中
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();

        //用户输入的账号密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("jack", "123");

        subject.login(usernamePasswordToken);

        System.out.println("认证结果：" + subject.isAuthenticated());

        //是否有某个角色
        boolean hasRole = subject.hasRole("role1");
        System.out.println("是否有role1角色：" + hasRole);
        System.out.println("主体名 ：" + subject.getPrincipal());

        //权限
        subject.checkPermission("video:find");
        System.out.println("是否有video:find的权限：" + subject.isPermitted("video:find"));

        subject.logout();
        System.out.println("认证结果：" + subject.isAuthenticated());

    }

    @Test
    public void test2() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://127.0.0.1:3306/xdclass_shiro?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false");
        ds.setUsername("root");
        ds.setPassword("root");

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setPermissionsLookupEnabled(true);
        jdbcRealm.setDataSource(ds);

        securityManager.setRealm(jdbcRealm);

        //将securityManager 设置到当前运行环境中
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();

        //用户输入的账号密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("jack", "123");

        subject.login(usernamePasswordToken);

        System.out.println("认证结果：" + subject.isAuthenticated());

        //是否有某个角色
        boolean hasRole = subject.hasRole("role1");
        System.out.println("是否有role1角色：" + hasRole);
        System.out.println("主体名 ：" + subject.getPrincipal());

        //权限
        subject.checkPermission("video:find");
        System.out.println("是否有video:find的权限：" + subject.isPermitted("video:find"));

        subject.logout();
        System.out.println("认证结果：" + subject.isAuthenticated());


    }

}
