package com.zhangll.jmock.core.random;

import java.util.HashMap;
import java.util.Map;

public class ExecutorStore {
    public static Map<Class, RandomType> randomMap = new HashMap<Class, RandomType>();
    public ExecutorStore() {

    }

    public synchronized RandomType getExecutor(Class cClass){
        RandomType random = RandomExecutorFactory.getRandom(cClass);
        if(random == null){
            random = randomMap.get(cClass);
        }
        return random;
    }

    public synchronized void register(Class cClass , AbstractRandomExecutor executor){
        randomMap.put(cClass, executor);
    }
}
