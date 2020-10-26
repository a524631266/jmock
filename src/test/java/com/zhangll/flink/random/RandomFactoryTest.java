package com.zhangll.flink.random;

import org.junit.Test;

import static org.junit.Assert.*;

public class RandomFactoryTest {

    @Test
    public void getTypeRandom() {
        RandomType random = RandomFactory.getRandom(Integer.class);
        assertTrue(random instanceof  IntegerRandom);
        RandomType longRandom = RandomFactory.getRandom(Long.class);
        assertTrue(longRandom instanceof  LongRandom);
        RandomType shortRandom = RandomFactory.getRandom(Short.class);
        assertTrue(shortRandom instanceof  ShortRandom);
        RandomType doubleRandom = RandomFactory.getRandom(Double.class);
        assertTrue(doubleRandom instanceof  DoubleRandom);
        RandomType floatRandom = RandomFactory.getRandom(Float.class);
        assertTrue(floatRandom instanceof  FloatRandom);
        RandomType boRandom = RandomFactory.getRandom(Boolean.class);
        assertTrue(boRandom instanceof  BooleanRandom);

    }
}