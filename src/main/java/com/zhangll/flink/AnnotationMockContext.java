package com.zhangll.flink;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AnnotationMockContext extends MockContext {
    final static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    static  Map<Class, Boolean> history = new HashMap<>();


}
