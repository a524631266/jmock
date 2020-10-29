package com.zhangll.flink.random;

import com.zhangll.flink.expression.RulePostProcessor;
import com.zhangll.flink.rule.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * 在Mock体系中属于执行器的范围
 */
public abstract class AbstractRandom implements RandomType {
    public static Logger LOG = LoggerFactory.getLogger(AbstractRandom.class);
    protected RulePostProcessor postProcessor = new RulePostProcessor();

    @Override
    public void updateField(Object o, Field declaredField, Rule rule) throws IllegalAccessException {

        Object compute = compute(declaredField, rule);


        if(LOG.isDebugEnabled()){
            LOG.debug(declaredField.getName());
        }
        declaredField.set(o, postProcessor.postProcessAfterCompute(compute));
    }

    /**
     * 每个继承Random的类，都需要计算这个内容
     *
     * @param declaredField
     * @param rule
     * @return
     */
    public abstract Object compute(Field declaredField, Rule rule);

}
