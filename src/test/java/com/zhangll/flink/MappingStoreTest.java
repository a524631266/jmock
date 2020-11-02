package com.zhangll.flink;

import org.junit.Before;

import java.lang.reflect.Field;

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
//    @Test
//    public void setRuleMap() {
//        for (Field field : fields) {
//            RandomType random = RandomExecutorFactory.getRandom(field.getType());
//            if(random != null){
//                mappingStore.setRuleMap(testClass, field, random.getRule());
//            }
//        }
//
//    }
//
//    @Test
//    public void getRule() {
//        setRuleMap();
//        for (Field field : fields) {
//            Rule rule = mappingStore.getRule(testClass, field);
//            System.out.println("field:" + field.getType() + "====> rule: " + rule);
//        }
//    }

}