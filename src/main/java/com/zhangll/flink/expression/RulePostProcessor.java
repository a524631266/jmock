package com.zhangll.flink.expression;

import com.zhangll.flink.type.BasicType;
import com.zhangll.flink.uitl.RandomUtil;

import java.util.Collection;

/**
 * 表达式生成器 以及 @FirstName等方式的应用
 * @First @middle @Last
 * 或者正则表达式生成器
 * /\d{5,10}/ ==> "561659409"
 * /\w\W\s\S\d\D/,  ==>"F)\fp1G",
 * /[a-z][A-Z][0-9]/ ==> "pJ7"
 */
public class RulePostProcessor {
    /**
     * 在compute之后处理的结果
     *
     * @return
     */
    public Object postProcessAfterCompute(Object object) {
        if(BasicType.isCollection(object.getClass())){
            Collection cols = (Collection)object;
            Object[] objects = cols.toArray();
            Collection result = null;
            try {
                result =(Collection) object.getClass().newInstance();
            } catch ( InstantiationException e ) {
                e.printStackTrace();
            } catch ( IllegalAccessException e ) {
                e.printStackTrace();
            }
            for (int i = 0; i < objects.length; i++) {
                result.add(postProcessAfterCompute(objects[i]));
            }
            return result;
        }else {
           return handleOne(object);
        }
    }

    private Object handleOne(Object string) {
        if(string instanceof String){
            String middleStr = ((String) string);
            if (middleStr.startsWith("@")) {

                if (middleStr.toLowerCase().contains("first")) {
                    return RandomUtil.getFirstName();
                } else if (middleStr.toLowerCase().contains("middle")) {
                    return RandomUtil.getMiddleName();
                } else if (middleStr.toLowerCase().contains("last")) {
                    return RandomUtil.getLastName();
                }
            } else if (middleStr.startsWith("/") && middleStr.endsWith("/")) {
                return "正则表达式";
            }
        }
        return string;
    }
}
