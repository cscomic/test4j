package org.test4j.testng.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.test4j.module.core.utility.MessageHelper;
import org.test4j.testng.Test4J;
import org.testng.annotations.Test;

public class SpringAroundTest extends Test4J {

	@Test
	public void testSpringAround() {
		Hello target = new Hello();// target class
		ProxyFactory pf = new ProxyFactory();// create the proxy
		pf.addAdvice(new MethodTracer());// add advice
		pf.setTarget(target);// setTarget
		Hello proxy = (Hello) pf.getProxy();
		proxy.greeting();
	}

	public static class MethodTracer implements MethodInterceptor {

		public Object invoke(MethodInvocation invocation) throws Throwable {
			MessageHelper.info("before invoke");
			Object o = invocation.proceed();
			MessageHelper.info("after invoke");
			return o;
		}
	}

	public static class Hello {
		public void greeting() {
			MessageHelper.info("reader");
		}
	}
}
