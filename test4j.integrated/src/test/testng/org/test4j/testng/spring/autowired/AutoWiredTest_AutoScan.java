package org.test4j.testng.spring.autowired;

import mockit.Mock;
import mockit.Mocked;

import org.test4j.fortest.beans.User;
import org.test4j.module.spring.annotations.SpringBeanByType;
import org.test4j.module.spring.annotations.SpringBeanFrom;
import org.test4j.module.spring.annotations.SpringContext;
import org.test4j.module.spring.testedbeans.autowired.IUserDao;
import org.test4j.module.spring.testedbeans.autowired.IUserService;
import org.test4j.module.spring.testedbeans.autowired.UserDaoImpl;
import org.test4j.testng.Test4J;
import org.testng.annotations.Test;

@Test(groups = "test4j")
@SpringContext({ "org/test4j/module/spring/testedbeans/autowired/autowired-scan.xml" })
public class AutoWiredTest_AutoScan extends Test4J {

    @SpringBeanByType
    IUserService userService;

    @SpringBeanFrom("userDaoImpl")
    @Mocked
    IUserDao     userDao;

    @Test(description = "@AutoWired自动包扫描情况下，使用@SpringBeanFrom来替换spring扫描到的bean")
    public void testAutoWired_AutoScan() {
        new MockUp<UserDaoImpl>() {
            @Mock
            public void insertUser(User user) {
                want.fail("can't be execute");
            }
        };
        new Expectations() {
            {
                userDao.insertUser((User) any);
            }
        };
        userService.insertUser(null);
    }
}
