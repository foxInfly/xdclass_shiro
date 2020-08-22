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
import org.junit.Before;
import org.junit.Test;

/**
 * 单元测试用例执行顺序
 *
 * @author : lipu
 * @since : 2020-08-22 15:43
 */
public class QuickStartTest5_4 {

    private CustomRealm customRealm = new CustomRealm();
    private DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

    @Before
    public void init() {
        //构建环境
        defaultSecurityManager.setRealm(customRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
    }

    @Test
    public void testAuthentication() {

        //获取当前操作主体，application user
        Subject subject = SecurityUtils.getSubject();

        //用户输入的账号密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("jack", "123");

        subject.login(usernamePasswordToken);

        System.out.println("认证结果：" + subject.isAuthenticated());
        System.out.println("主体名 ：" + subject.getPrincipal());

        subject.checkRole("role1");
        System.out.println("是否有role1的角色 ：" + subject.hasRole("role1"));

        //权限
        subject.checkPermission("video:find");
        System.out.println("是否有video:find的权限：" + subject.isPermitted("video:find"));

        subject.logout();
        System.out.println("认证结果：" + subject.isAuthenticated());

    }

}
