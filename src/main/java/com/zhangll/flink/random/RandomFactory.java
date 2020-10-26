package com.zhangll.flink.random;

import com.zhangll.flink.type.BasicType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RandomFactory {
    private static Map<Class, RandomType> randomMap = new HashMap<Class, RandomType>();
    static {
        randomMap.put(Integer.class, new IntegerRandom());
        randomMap.put(int.class, new IntegerRandom());
        randomMap.put(char.class, new CharRandom());
        randomMap.put(Character.class, new CharRandom());
        randomMap.put(Double.class, new DoubleRandom());
        randomMap.put(double.class, new DoubleRandom());
        randomMap.put(Float.class, new FloatRandom());
        randomMap.put(float.class, new FloatRandom());
        randomMap.put(Long.class, new LongRandom());
        randomMap.put(long.class, new LongRandom());
        randomMap.put(Short.class, new ShortRandom());
        randomMap.put(short.class, new ShortRandom());
        randomMap.put(Boolean.class, new BooleanRandom());
        randomMap.put(boolean.class, new BooleanRandom());
        randomMap.put(String.class, new StringRandom());
        randomMap.put(List.class, new ListRandom<>());
    }

    public static RandomType getRandom(Class type) {
        RandomType randomType = randomMap.get(type);
        if(randomType!= null){
            return randomType;
        }
        if(BasicType.isList(type)){
            return randomMap.get(List.class);
        }
        return randomMap.get(type);
    }
}
