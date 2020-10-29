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
        randomMap.put(Set.class, new ListRandom<>());
        randomMap.put(Date.class, new SqlTimeRandom(Date.class));
        randomMap.put(Time.class, new SqlTimeRandom(Time.class));
        randomMap.put(Timestamp.class, new SqlTimeRandom(Timestamp.class));
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
