package com.zhangll.flink.random;

import org.junit.Test;

import static org.junit.Assert.*;

public class RandomFactoryTest {

    @Test
    public void getTypeRandom() {
        RandomType random = RandomFactory.getRandom(Integer.class);
        assertTrue(random instanceof IntegerSimpleRandom);
        RandomType longRandom = RandomFactory.getRandom(Long.class);
        assertTrue(longRandom instanceof LongSimpleRandom);
        RandomType shortRandom = RandomFactory.getRandom(Short.class);
        assertTrue(shortRandom instanceof ShortSimpleRandom);
        RandomType doubleRandom = RandomFactory.getRandom(Double.class);
        assertTrue(doubleRandom instanceof DoubleSimpleRandom);
        RandomType floatRandom = RandomFactory.getRandom(Float.class);
        assertTrue(floatRandom instanceof FloatSimpleRandom);
        RandomType boRandom = RandomFactory.getRandom(Boolean.class);
        assertTrue(boRandom instanceof BooleanSimpleRandom);

    }
}