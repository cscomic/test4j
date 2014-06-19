package org.test4j.module.spring;

import mockit.Mocked;

import org.junit.Test;
import org.test4j.junit.Test4J;
import org.test4j.module.spring.annotations.AutoBeanInject;
import org.test4j.module.spring.annotations.SpringBeanByName;
import org.test4j.module.spring.annotations.SpringBeanFrom;
import org.test4j.module.spring.annotations.SpringContext;

@SpringContext
@AutoBeanInject
public class SpringTestedContextTest extends Test4J {
    @SpringBeanByName(init = "init")
    TestedService testedService;

    @SpringBeanFrom
    @Mocked
    TestedDao     testedDao;

    @Test
    public void testSetContext() {
        System.out.println("");
        new NonStrictExpectations() {
            {
                testedDao.sayNo();
                result = "mock";
            }
        };
        String word = this.testedService.sayNo();
        want.string(word).isNull();
    }
}

class TestedService {
    private TestedDao testedDao;

    public TestedDao getTestedDao() {
        return testedDao;
    }

    public void setTestedDao(TestedDao testedDao) {
        this.testedDao = testedDao;
    }

    public String sayNo() {
        return word;
    }

    private String word;

    public void init() {
        try {
            this.word = this.testedDao.sayNo();
            throw new RuntimeException("在before class时，jmockit应该还没有初始化化mock字段(jmockit-0.999.13)！");
        } catch (NullPointerException ne) {
        }
    }
}

class TestedDao {
    public String sayNo() {
        return "no";
    }
}
