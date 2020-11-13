package com.zhangll.jmock.core.uitl;

import com.zhangll.flink.annotation.PojoTokenInfo;

import java.lang.reflect.Field;

public class KeyUtil {
    /**
     * 返回一个key，用户来存储在store中，作为唯一标志
     *
     *
     * @param cls Father
     * @param field 其中一个
     * @return com.zhangll.flink.Father#sonsNameList1
     */
    public static String generateKey(Class cls, Field field){
        if (field ==null) {
            return cls.getName();
        }
        return cls.getName() +"#"+ field.getName();
    }
    public static String generateKey(Class cls, PojoTokenInfo pojoTokenInfo){
        if ("".equals(pojoTokenInfo.id())) {
            return cls.getName();
        }
        return cls.getName() +"#"+ pojoTokenInfo.id();
    }

    public static String generateKey(Class cls, PojoTokenInfo pojoTokenInfo, String fieldName){
        String className = "";
        if ((className= pojoTokenInfo.id()).equals("")) {
            className = cls.getName();
        }
        return className + pojoTokenInfo.id() + fieldName;
    }

}
