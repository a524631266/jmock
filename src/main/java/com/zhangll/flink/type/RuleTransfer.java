package com.zhangll.flink.type;

import com.zhangll.flink.random.IntegerRandom.DefaultIntegerRule;
import com.zhangll.flink.random.ListRandom;
import com.zhangll.flink.random.ListRandom.*;
import com.zhangll.flink.rule.Rule;

public class RuleTransfer {
    public static <T> Rule transferToIntRule(Rule defaultListRule) {
        // TODO
        if (defaultListRule instanceof ListRandom.DefaultListRule){
            int end = ((DefaultListRule) defaultListRule).getEnd();
            int start = ((DefaultListRule) defaultListRule).getStart();
            int count = ((DefaultListRule) defaultListRule).getCount();
            String value = ((DefaultListRule) defaultListRule).getValue();
            return new DefaultIntegerRule(start, end, count, value);
        }
        return null;
    }
}
