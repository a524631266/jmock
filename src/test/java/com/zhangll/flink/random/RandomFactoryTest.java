package com.zhangll.flink.random;

import org.junit.Test;

import static org.junit.Assert.*;

public class RandomFactoryTest {

    @Test
    public void getTypeRandom() {
        RandomType random = RandomFactory.getRandom(Integer.class);
        assertTrue(random instanceof IntegerSimpleRandomExecutor);
        RandomType longRandom = RandomFactory.getRandom(Long.class);
        assertTrue(longRandom instanceof LongSimpleRandomExecutor);
        RandomType shortRandom = RandomFactory.getRandom(Short.class);
        assertTrue(shortRandom instanceof ShortSimpleRandomExecutor);
        RandomType doubleRandom = RandomFactory.getRandom(Double.class);
        assertTrue(doubleRandom instanceof DoubleSimpleRandomExecutor);
        RandomType floatRandom = RandomFactory.getRandom(Float.class);
        assertTrue(floatRandom instanceof FloatSimpleRandomExecutor);
        RandomType boRandom = RandomFactory.getRandom(Boolean.class);
        assertTrue(boRandom instanceof BooleanSimpleRandomExecutor);

    }
}