package org.test4j.spec.inner;

import java.util.List;

/**
 * 场景描述接口类
 * 
 * @author darui.wudr 2013-1-10 下午6:57:56
 */
public interface IScenario {

    /**
     * 返回场景的步骤列表
     * 
     * @return
     */
    List<IScenarioStep> getSteps();

    /**
     * 验证场景
     */
    void validate() throws Throwable;

    /**
     * 返回场景名称
     * 
     * @return
     */
    String getName();

    /**
     * 返回场景序列号
     * 
     * @return
     */
    int getIndex();

    /**
     * 设置场景序列号
     * 
     * @param index
     */
    void setIndex(int index);
}
