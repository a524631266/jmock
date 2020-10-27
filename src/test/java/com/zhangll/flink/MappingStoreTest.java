package com.zhangll.flink;

import com.zhangll.flink.random.RandomFactory;
import com.zhangll.flink.random.RandomType;
import com.zhangll.flink.rule.Rule;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class MappingStoreTest {
    private Class testClass;
    private MappingStore mappingStore;
    private Field[] fields ;
    @Before
    public void init(){
        testClass = Father.class;
        fields = testClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
        }
        mappingStore = new MappingStore();
    }
    @Test
    public void setRuleMap() {
        for (Field field : fields) {
            RandomType random = RandomFactory.getRandom(field.getType());
            mappingStore.setRuleMap(testClass, field, random.getRule());
        }

    }

    @Test
    public void getRule() {
        setRuleMap();
        for (Field field : fields) {
            Rule rule = mappingStore.getRule(testClass, field);
            System.out.println(rule);
        }
    }

}