package com.zhangll.jmock.core.random;

import com.zhangll.flink.MockContext;
import com.zhangll.flink.error.FieldIllegalArgumentException;
import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.uitl.DateUtil;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.regex.Pattern;


/**
 * 匹配flink的Date时间
 * 生成逻辑与long相关
 */
public class SqlTimeSimpleRandomExecutor extends AbstractRandomExecutor {


    public static Rule<Date> DATE = new DefaultDateRule(
            new FieldToken.FieldTokenBuilder()
                    .setMin(new Long(System.currentTimeMillis() / 1000 - 24 *60 *60).intValue())
                    .setMax(new Long(System.currentTimeMillis() / 1000 + 24 *60 *60).intValue()).build()
    );
    public static Rule<Time> TIME = new DefaultTimeRule(
            new FieldToken.FieldTokenBuilder()
                    .setMin(new Long(System.currentTimeMillis() / 1000 - 24 *60 *60).intValue())
                    .setMax(new Long(System.currentTimeMillis() / 1000 + 24 *60 *60).intValue()).build()
    );

    public static Rule<Timestamp> TIMESTAMP = new DefaultTimestampRule(
            new FieldToken.FieldTokenBuilder()
                    .setMin(new Long(System.currentTimeMillis() / 1000 - 24 *60 *60).intValue())
                    .setMax(new Long(System.currentTimeMillis() / 1000 + 24 *60 *60).intValue()).build()
    );
    private final Class innerClass;
    private SqlTimeSimpleRandomExecutor(){
        this(null);
    }
    public SqlTimeSimpleRandomExecutor(Class cls) {
        this.innerClass = cls;
    }


    public static void main(String[] args) throws ParseException {
        long ti = 1603890014000L;
        //
        Date date = new Date(ti);
        // 2020-10-28
        System.out.println(date);
        Time time = new Time(ti);
        // 21:00:14
        System.out.println(time);
        Timestamp timestamp = new Timestamp(ti);
        // 2020-10-28 21:00:14.0

        System.out.println(new Long(System.currentTimeMillis() / 1000).intValue());
        int count = (int) (System.currentTimeMillis() / 1000);
        System.out.println(count);


    }

    @Override
    public boolean isCurrentType(Class<?> type) {
        return type == Date.class ||
                type == Time.class ||
                type == Timestamp.class;
    }

    @Override
    public Rule getDefaultRule() {
        if(innerClass == Date.class){
            return DATE;
        }else if(innerClass == Time.class){
            return TIME;
        }else if(innerClass == Timestamp.class){
            return TIMESTAMP;
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
        }else if(innerClass == Time.class){
            if(fieldToken==null) {
                return TIME;
            }
            return new DefaultDateRule(fieldToken);
        } else if (innerClass == Timestamp.class) {
            if(fieldToken==null) {
                return TIMESTAMP;
            }
            return new DefaultTimestampRule(fieldToken);
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
    protected Object doHandleStep(FieldToken currentTokenInfo, FieldNode.StepState currentState) {
        long result = 0;

        try {
            result = handleSqlTimeStep(currentTokenInfo, currentState);
        } catch ( ParseException e ) {
            e.printStackTrace();
            throw new IllegalArgumentException(currentState.toString());
        }

        if (innerClass == Date.class) {
            return new Date(result);
        } else if (innerClass == Time.class) {
            return new Time( result );
        } else if (innerClass == Timestamp.class) {
            return new Timestamp(result);
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
     * @param value
     * @param currentTokenInfo
     * @return
     */
    private long verifyOutBound(long value, FieldToken currentTokenInfo) {
        if(value < (Long.valueOf(currentTokenInfo.getMin()) * 1000)){
            long gap = Long.valueOf(currentTokenInfo.getMax() - currentTokenInfo.getMin()) * 1000;
            long result = value + gap;
            while (result < (Long.valueOf(currentTokenInfo.getMin()) * 1000)){
                result += gap;
            }
            return result;
        }
        if(value > (Long.valueOf(currentTokenInfo.getMax()) * 1000)){
            long gap = Long.valueOf(currentTokenInfo.getMax() - currentTokenInfo.getMin()) * 1000;
            long result = value - gap;
            while (result > (Long.valueOf(currentTokenInfo.getMax()) * 1000)){
                result -= gap;
            }
            return  result;
        }
        return value;
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

    protected static class DefaultTimeRule implements Rule<Time> {

        private final FieldToken fieldToken;

        public DefaultTimeRule(FieldToken fieldToken) {
            this.fieldToken = fieldToken;
        }

        @Override
        public Time apply(MockContext mockContext, FieldNode fieldNodeContext) {
            if(fieldToken.getCount() != 0){
                return new Time(Long.valueOf(fieldToken.getCount())* 1000);
            }
            // 概率
            double p = new Random().nextDouble();
            Double len = (fieldToken.getMax() - fieldToken.getMin()) * p;
            return  new Time((len.longValue() + fieldToken.getMin())* 1000);
        }
    }

    protected static class DefaultTimestampRule implements Rule<Timestamp> {

        private final FieldToken fieldToken;

        public DefaultTimestampRule(FieldToken fieldToken) {
            this.fieldToken = fieldToken;
        }

        @Override
        public Timestamp apply(MockContext mockContext, FieldNode fieldNodeContext) {
            if(fieldToken.getCount() != 0){
                return new Timestamp(Long.valueOf(fieldToken.getCount())* 1000);
            }
            // 概率
            double p = new Random().nextDouble();
            Double len = (fieldToken.getMax() - fieldToken.getMin()) * p;
            return  new Timestamp((len.longValue() + fieldToken.getMin())* 1000);
        }
    }
}
