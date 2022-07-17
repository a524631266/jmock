package com.zhangll.jmock.core.random;


import com.zhangll.jmock.core.MockContext;
import com.zhangll.jmock.core.model.FieldNode;
import com.zhangll.jmock.core.model.FieldToken;
import com.zhangll.jmock.core.rule.Rule;
import com.zhangll.jmock.core.uitl.DateUtil;
import com.zhangll.jmock.core.uitl.RandomUtil;
import lombok.SneakyThrows;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;


/**
 * 匹配flink的Date时间
 * 生成逻辑与long相关
 */
public class JavaDateRandomExecutor extends AbstractRandomExecutor {


    public static Rule<Date> DATE = new DefaultDateRule(
            new FieldToken.FieldTokenBuilder()
                    .setMin(new Long(System.currentTimeMillis() / 1000 - 24 *60 *60).intValue())
                    .setMax(new Long(System.currentTimeMillis() / 1000 + 24 *60 *60).intValue()).build()
    );

    private final Class innerClass;
    private JavaDateRandomExecutor(){
        this(null);
    }
    public JavaDateRandomExecutor(Class cls) {
        this.innerClass = cls;
    }

    @Override
    public boolean isCurrentType(Class<?> type) {
        return type == Date.class;
    }

    @Override
    public Rule getDefaultRule() {
        if(innerClass == Date.class){
            return DATE;
        }
        return null;
    }

    @Override
    public Rule getRule(FieldToken fieldToken) {
        if(innerClass == Date.class){
            if(fieldToken==null) {
                return DATE;
            }
            return new DefaultDateRule(fieldToken);
        }
        return null;
    }

    /**
     *
     * @param currentTokenInfo
     * @param currentState
     * @return Date Time Timestamp
     */
    @Override
    protected Object doHandleStepUsingMinMaxOrValue(FieldToken currentTokenInfo, FieldNode.StepState currentState) {
        long result = 0;

        try {
            result = handleSqlTimeStep(currentTokenInfo, currentState);
        } catch ( ParseException e ) {
            e.printStackTrace();
            throw new IllegalArgumentException(currentState.toString());
        }

        if (innerClass == Date.class) {
            return new Date(result);
        }
        return null;
    }

    /**
     * 处理共性
     * @param currentTokenInfo
     * @param currentState
     * @return
     */
    private long handleSqlTimeStep(FieldToken currentTokenInfo, FieldNode.StepState currentState) throws ParseException {
        String[] value = currentTokenInfo.getValue();
        // value优先级
        if(value.length > 0){
            int cutgap = currentState.getProgress() % value.length;
            if(currentState.getStep()>0){
                return DateUtil.getFormat(innerClass).parse(value[cutgap]).getTime();
            }
            return DateUtil.getFormat(innerClass).parse(value[(value.length-1) + cutgap]).getTime();
        }else{
            if(currentTokenInfo.getStep() !=  0){
                long preTimeLong10 = 0;
                if(currentState.getPreObject() == null){
                    if(currentTokenInfo.getStep() >0 ){
                        preTimeLong10 = Long.valueOf(currentTokenInfo.getMin()) * 1000;
                    } else {
                        preTimeLong10 = Long.valueOf(currentTokenInfo.getMax()) * 1000;
                    }
                } else {
                    java.util.Date date = (java.util.Date) currentState.getPreObject();
                    preTimeLong10 = verifyOutBound(date.getTime() + DateUtil.getBase(innerClass) * currentTokenInfo.getStep(),currentTokenInfo );

                }
                return preTimeLong10;
            }
        }
        return 0;
    }

    /**
     * bug 当 gap 很小，时候
     * bug 2 当 getMin与getMax相等时候,会垂下按各占哦给异常
     *
     * @param value
     * @param currentTokenInfo
     * @return
     */
    private long verifyOutBound(long value, FieldToken currentTokenInfo) {
        long gap = Long.valueOf(currentTokenInfo.getMax() - currentTokenInfo.getMin()) * 1000;
        if (gap == 0){
            return currentTokenInfo.getMax() * 1000;
        }

        if (value < (Long.valueOf(currentTokenInfo.getMin()) * 1000)) {
            long result = value + gap;
            while (result < (Long.valueOf(currentTokenInfo.getMin()) * 1000)) {
                result += gap;
            }
            return result;
        }

        if (value > (Long.valueOf(currentTokenInfo.getMax()) * 1000)) {
            long result = value - gap;
            while (result > (Long.valueOf(currentTokenInfo.getMax()) * 1000)) {
                result -= gap;
            }
            return result;
        }
        return value;
    }


    @Override
    protected Object doHandleRandomValue(MockContext context, FieldNode fieldNodeContext) {
        String[] value = fieldNodeContext.getCurrentTokenInfo().getValue();
        Integer index = RandomUtil.getMin2Max(0, value.length - 1);
        return value[index];
    }

    @SneakyThrows
    @Override
    protected Object convertToCurrentType(FieldNode fieldNodeContext, Object result) {
        if(result instanceof String) {
            SimpleDateFormat format = DateUtil.getFormat(innerClass);
            long time = format.parse((String) result).getTime();
            if(this.innerClass == Date.class){
                return new Date(time);
            }
        }
        return super.convertToCurrentType(fieldNodeContext, result);
    }


    protected static class DefaultDateRule implements Rule<Date> {

        private final FieldToken fieldToken;

        public DefaultDateRule(FieldToken fieldToken) {
            this.fieldToken = fieldToken;
        }

        @Override
        public Date apply(MockContext mockContext, FieldNode fieldNodeContext) {
            if(fieldToken.getCount() != 0){
                return new Date(Long.valueOf(fieldToken.getCount()) * 1000);
            }
            // 概率
            double p = new Random().nextDouble();
            Double len = (fieldToken.getMax() - fieldToken.getMin()) * p;
            return  new Date((len.longValue() + fieldToken.getMin()) * 1000);
        }
    }
}
