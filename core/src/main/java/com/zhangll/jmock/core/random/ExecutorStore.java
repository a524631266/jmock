package com.zhangll.jmock.core.random;

import java.util.*;

/**
 * 用于注册用户自定义的Executor规则规则
 */
public class ExecutorStore {

    public Map<Class<?>, RandomType> randomMap = new HashMap<>();

    public ExecutorStore() {

    }

    /**
     * 根据类型获取指定的executor
     * @param cClass 传入 class 对象
     * @return  返回 一个 随机类型。
     */
    public synchronized RandomType getExecutor(Class<?> cClass){
        // 1. 获取内部的Executor,内部已经被写死了,不能动态修改
        RandomType random = RandomExecutorFactory.getRandom(cClass);
        if(random!=null){
            return random;
        }

        random = randomMap.get(cClass);

        // 这里会包含用户自定义的executor
        if(random == null){
            List<RandomType> executors = getRegisteredExecutor();
            random = executors.stream()
                    .filter(randomType -> randomType.isCurrentType(cClass))
                    .findFirst()
                    .orElse(null);
        }
        return random;
    }

    /**
     * 注册一个随之执行器
     * @param cClass class 对象
     * @param executor 随机执行器
     */
    public synchronized void register(Class<?> cClass , AbstractRandomExecutor executor){
        randomMap.put(cClass, executor);
    }

    /**
     * 获取所有注册过的Executor
     * @return 翻译所有的注册器
     */
    private List<RandomType> getRegisteredExecutor() {
        Iterator<RandomType> iterator = randomMap.values().iterator();
        ArrayList<RandomType> result = new ArrayList<>();
        while (iterator.hasNext()){
            result.add(iterator.next());
        }
        return result;
    }

    /**
     * 是否是本洗头膏内置的类型。
     * @param cls 类型
     * @return true 位内置的类型
     */
    public boolean isInnerType(Class<?> cls) {
        return RandomExecutorFactory.getRandom(cls)!=null;
    }
}
