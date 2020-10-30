package com.zhangll.flink.random;

import com.zhangll.flink.type.BasicType;

import java.lang.reflect.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RandomFactory {
    private static Map<Class, RandomType> randomMap = new HashMap<Class, RandomType>();
    static {
        randomMap.put(Integer.class, new IntegerSimpleRandom());
        randomMap.put(int.class, new IntegerSimpleRandom());
        randomMap.put(char.class, new CharSimpleRandom());
        randomMap.put(Character.class, new CharSimpleRandom());
        randomMap.put(Double.class, new DoubleSimpleRandom());
        randomMap.put(double.class, new DoubleSimpleRandom());
        randomMap.put(Float.class, new FloatSimpleRandom());
        randomMap.put(float.class, new FloatSimpleRandom());
        randomMap.put(Long.class, new LongSimpleRandom());
        randomMap.put(long.class, new LongSimpleRandom());
        randomMap.put(Short.class, new ShortSimpleRandom());
        randomMap.put(short.class, new ShortSimpleRandom());
        randomMap.put(Boolean.class, new BooleanSimpleRandom());
        randomMap.put(boolean.class, new BooleanSimpleRandom());
        randomMap.put(String.class, new StringSimpleRandom());
        randomMap.put(List.class, new ListRandom<>());
        randomMap.put(Set.class, new ListRandom<>());
        randomMap.put(Date.class, new SqlTimeSimpleRandom(Date.class));
        randomMap.put(Time.class, new SqlTimeSimpleRandom(Time.class));
        randomMap.put(Timestamp.class, new SqlTimeSimpleRandom(Timestamp.class));
        randomMap.put(Array.class, new ArrayRandom<>());
    }

    public static RandomType getRandom(Class type) {
        RandomType randomType = randomMap.get(type);
        if(randomType!= null){
            return randomType;
        }
        if(BasicType.isCollection(type)){
            return randomMap.get(List.class);
        }
        if(type.getComponentType()!= null){
            return randomMap.get(Array.class);
        }
        return randomMap.get(type);
    }
}
