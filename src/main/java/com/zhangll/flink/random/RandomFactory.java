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
        randomMap.put(Integer.class, new IntegerSimpleRandomExecutor());
        randomMap.put(int.class, new IntegerSimpleRandomExecutor());
        randomMap.put(char.class, new CharSimpleRandomExecutor());
        randomMap.put(Character.class, new CharSimpleRandomExecutor());
        randomMap.put(Double.class, new DoubleSimpleRandomExecutor());
        randomMap.put(double.class, new DoubleSimpleRandomExecutor());
        randomMap.put(Float.class, new FloatSimpleRandomExecutor());
        randomMap.put(float.class, new FloatSimpleRandomExecutor());
        randomMap.put(Long.class, new LongSimpleRandomExecutor());
        randomMap.put(long.class, new LongSimpleRandomExecutor());
        randomMap.put(Short.class, new ShortSimpleRandomExecutor());
        randomMap.put(short.class, new ShortSimpleRandomExecutor());
        randomMap.put(Boolean.class, new BooleanSimpleRandomExecutor());
        randomMap.put(boolean.class, new BooleanSimpleRandomExecutor());
        randomMap.put(String.class, new StringSimpleRandomExecutor());
        randomMap.put(List.class, new ListRandomExecutor<>());
        randomMap.put(Set.class, new ListRandomExecutor<>());
        randomMap.put(Date.class, new SqlTimeSimpleRandomExecutor(Date.class));
        randomMap.put(Time.class, new SqlTimeSimpleRandomExecutor(Time.class));
        randomMap.put(Timestamp.class, new SqlTimeSimpleRandomExecutor(Timestamp.class));
        randomMap.put(Array.class, new ArrayRandomExecutor<>());
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
