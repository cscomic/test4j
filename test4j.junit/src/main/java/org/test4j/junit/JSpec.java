package org.test4j.junit;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.test4j.junit.annotations.DataFrom;
import org.test4j.module.core.ICoreInitial;
import org.test4j.module.core.TestContext;
import org.test4j.spec.ISpec;
import org.test4j.spec.ISpecExecutorFactory;
import org.test4j.spec.SharedData;
import org.test4j.spec.inner.IScenario;
import org.test4j.spec.inner.ISpecMethod;
import org.test4j.spec.inner.ISpecMethod.SpecMethodID;
import org.test4j.spec.inner.ISpecPrinter;
import org.test4j.tools.datagen.DataProviderIterator;

/**
 * junit版本jspec
 * 
 * @author darui.wudr 2013-1-10 下午4:16:42
 */
@SuppressWarnings({ "unchecked" })
public abstract class JSpec extends Test4J implements ISpec {
    static ISpecExecutorFactory                  specFactory   = ICoreInitial.initSpecExecutorFactory();

    private final Map<SpecMethodID, ISpecMethod> specMethods;

    private Map<String, Object>                  stepsInstances;

    private static ThreadLocal<ISpecPrinter>     threadPrinter = new ThreadLocal<ISpecPrinter>();

    public JSpec() {
        this.specMethods = specFactory.findMethodsInSpec(this.getClass());
    }

    protected SharedData shared;

    /**
     * 用来初始化共享的数据结构<br>
     * 允许子类进行覆盖
     */
    protected void initSharedData() {
    }

    @Override
    public final SharedData getSharedData() {
        if (shared == null) {
            shared = new SharedData.EmptyData();
        }
        return shared;
    }

    @Override
    public final Object getStepsInstance(String stepClazzName) {
        return this.stepsInstances.get(stepClazzName);
    }

    @BeforeClass
    public static void initSpecPrinter() {
        threadPrinter.set(specFactory.newSpecPrinter());
    }

    @AfterClass
    public static void cleanSpecPrinter() {
        getPrinter().printSummary(TestContext.currTestedClazz());
        threadPrinter.remove();
    }

    static ISpecPrinter getPrinter() {
        ISpecPrinter printer = threadPrinter.get();
        if (printer == null) {
            printer = specFactory.newSpecPrinter();
            threadPrinter.set(printer);
        }
        return printer;
    }

    /**
     * 场景测试方法入口，执行多个场景
     * 
     * @param scenario
     * @throws Throwable
     */
    @Test
    @DataFrom("scenariosOfStorySpec")
    public void runScenario(IScenario scenario) throws Throwable {
        this.initSharedData();
        this.stepsInstances = specFactory.newSteps(this);
        specFactory.runScenario(this, scenario, specMethods, getPrinter());
    }

    final DataProviderIterator<IScenario> scenariosOfStorySpec() {
        DataProviderIterator<IScenario> it = specFactory.findScenario(this.getClass());
        return it;
    }
}
