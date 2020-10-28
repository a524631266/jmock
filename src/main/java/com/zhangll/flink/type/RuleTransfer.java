package com.zhangll.flink.type;

import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.random.IntegerRandom.DefaultIntegerRule;
import com.zhangll.flink.random.ListRandom;
import com.zhangll.flink.random.ListRandom.*;
import com.zhangll.flink.rule.Rule;

public class RuleTransfer {
    public static <T> Rule transferToIntRule(Rule defaultListRule) {
        // TODO
        if (defaultListRule instanceof ListRandom.DefaultListRule){
            FieldToken fieldToken = ((DefaultListRule) defaultListRule).getFieldToken();
//            int end = fieldToken.getMin();
//            int start = ((DefaultListRule) defaultListRule).getStart();
//            int count = ((DefaultListRule) defaultListRule).getCount();
//            String value = ((DefaultListRule) defaultListRule).getValue();

            FieldToken subFieldToken = ((DefaultListRule) defaultListRule).getFieldToken();
            return new DefaultIntegerRule(subFieldToken);
        }
        return null;
    }
}
