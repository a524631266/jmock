package com.zhangll.jmock.core.random;

import java.util.*;

/**
 * 用于注册用户自定义的Executor规则规则
 */
public class ExecutorStore {
    public Map<Class, RandomType> randomMap = new HashMap<Class, RandomType>();
    public ExecutorStore() {

    }

    /**
     * 根据类型获取指定的executor
     * @param cClass
     * @return
     */
    public synchronized RandomType getExecutor(Class cClass){
        // 1. 获取内部的Executor,内部已经被写死了,不能动态修改
        RandomType random = RandomExecutorFactory.getRandom(cClass);
        if(random!=null){
            return random;
        }

        random = randomMap.get(cClass);

        /**
         * 这里会包含用户自定义的executor
         */
        if(random == null){
            List<RandomType> executors = getRegisteredExecutor();
            random = executors.stream()
                    .filter(randomType -> randomType.isCurrentType(cClass))
                    .findFirst()
                    .get();
        }
        return random;
    }

    public synchronized void register(Class cClass , AbstractRandomExecutor executor){
        randomMap.put(cClass, executor);
    }

    /**
     * 获取所有注册过的Executor
     * @return
     */
    private List<RandomType> getRegisteredExecutor() {
        Iterator<RandomType> iterator = randomMap.values().iterator();
        ArrayList<RandomType> result = new ArrayList<>();
        while (iterator.hasNext()){
            result.add(iterator.next());
        }
        return result;
    }
}
