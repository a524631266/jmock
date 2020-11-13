package com.zhangll.jmock.core.random;

import org.junit.Test;

import static org.junit.Assert.*;

public class RandomExecutorFactoryTest {

    @Test
    public void getTypeRandom() {
        RandomType random = RandomExecutorFactory.getRandom(Integer.class);
        assertTrue(random instanceof IntegerSimpleRandomExecutor);
        RandomType longRandom = RandomExecutorFactory.getRandom(Long.class);
        assertTrue(longRandom instanceof LongSimpleRandomExecutor);
        RandomType shortRandom = RandomExecutorFactory.getRandom(Short.class);
        assertTrue(shortRandom instanceof ShortSimpleRandomExecutor);
        RandomType doubleRandom = RandomExecutorFactory.getRandom(Double.class);
        assertTrue(doubleRandom instanceof DoubleSimpleRandomExecutor);
        RandomType floatRandom = RandomExecutorFactory.getRandom(Float.class);
        assertTrue(floatRandom instanceof FloatSimpleRandomExecutor);
        RandomType boRandom = RandomExecutorFactory.getRandom(Boolean.class);
        assertTrue(boRandom instanceof BooleanSimpleRandomExecutor);

    }
}