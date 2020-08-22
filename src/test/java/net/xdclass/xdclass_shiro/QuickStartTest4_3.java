package net.xdclass.xdclass_shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * 单元测试用例执行顺序
 *
 * @author : lipu
 *  :@BeforeClass -> @Before -> @Test -> @After -> @AfterClass;
 * @since : 2020-08-22 15:43
 */
public class QuickStartTest4_3 {

    private SimpleAccountRealm accountRealm = new SimpleAccountRealm();
    private DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

    @Before
    public void init() {
        //初始化数据源,为了测试
        accountRealm.addAccount("xdclass","456","admin","root");
        accountRealm.addAccount("jack","123","user");

        //构建环境
        defaultSecurityManager.setRealm(accountRealm);
    }


    @Test
    public void testAuthentication() {
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        //获取当前操作主体，application user
        Subject subject = SecurityUtils.getSubject();

        //用户输入的账号密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("xdclass", "456");

        subject.login(usernamePasswordToken);

        //执行认证
        System.out.println(" 认证结果："+subject.isAuthenticated());

        //是否有某个角色。授权
        boolean hasRole = subject.hasRole("root");
        System.out.println("是否有root角色："+hasRole);
        System.out.println("主体名 ："+subject.getPrincipal());

        subject.checkRole("root");

        subject.logout();
        System.out.println(" 认证结果："+subject.isAuthenticated());

    }

}
