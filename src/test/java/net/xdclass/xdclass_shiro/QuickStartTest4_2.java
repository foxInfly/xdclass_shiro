package net.xdclass.xdclass_shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

import javax.sound.midi.Soundbank;

/**
 * 单元测试用例执行顺序
 *
 * @author : lipu
 * @BeforeClass -> @Before -> @Test -> @After -> @AfterClass;
 * @since : 2020-08-22 15:43
 */
public class QuickStartTest4_2 {

    private SimpleAccountRealm accountRealm = new SimpleAccountRealm();
    private DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

    @Before
    public void init() {
        //初始化数据源,为了测试
        accountRealm.addAccount("xdclass","123");
        accountRealm.addAccount("jack","123");

        //构建环境
        defaultSecurityManager.setRealm(accountRealm);
    }


    @Test
    public void testAuthentication() {
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        //当前操作主体，application user
        Subject subject = SecurityUtils.getSubject();

        //用户输入的账号密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("xdclass", "123");

        //执行认证
        subject.login(usernamePasswordToken);

        System.out.println("认证结果："+subject.isAuthenticated());

    }

}
