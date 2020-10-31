package com.zhangll.flink.expression;


import com.zhangll.flink.type.BasicType;
import com.zhangll.flink.uitl.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * 表达式生成器 以及 @FirstName等方式的应用
 * @First @middle @Last
 * 或者正则表达式生成器
 * /\d{5,10}/ ==> "561659409"
 * /\w\W\s\S\d\D/,  ==>"F)\fp1G",
 * /[a-z][A-Z][0-9]/ ==> "pJ7"
 */
public class RulePostProcessor {
     public static Logger LOG = LoggerFactory.getLogger(RulePostProcessor.class);
    /**
     * 在compute之后处理的结果
     *
     * @return
     */
    public Object postProcessAfterCompute(Object object) {
        if(LOG.isDebugEnabled()){
            LOG.debug(object.toString());
        }
        if(BasicType.isCollection(object.getClass())){
            Collection cols = (Collection)object;
            Object[] objects = cols.toArray();
            int size = cols.size();
            cols.clear();
            for (int i = 0; i < size; i++) {
                cols.add(postProcessAfterCompute(objects[i]));
            }
            return cols;
        }else {
           return handleOne(object);
        }
    }

    /**
     * 词法分析 @first 不区分大小写
     *  1. "134 @first " =》 124
     * @param string
     * @return
     */
    private Object handleOne(Object string) {
        if(string instanceof String){
            // 小写
            String middleStr = ((String) string).toLowerCase();
            if (middleStr.contains("@")) {
                middleStr = middleStr.replace("@first", RandomUtil.getFirstName());
                middleStr = middleStr.replace("@middle", RandomUtil.getMiddleName());
                middleStr = middleStr.replace("@last", RandomUtil.getLastName());
                return middleStr;
            } else if (middleStr.startsWith("/") && middleStr.endsWith("/")) {
                return "正则表达式";
            }
        }
        return string;
    }
}
