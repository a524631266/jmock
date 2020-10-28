package com.zhangll.flink.random;

import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.rule.Rule;
import org.apache.flink.api.common.typeinfo.SqlTimeTypeInfo;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Random;

import static org.apache.flink.util.Preconditions.checkNotNull;

/**
 * 匹配flink的Date时间
 * 生成逻辑与long相关
 */
public class SqlTimeRandom extends AbstractRandom {
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
    private SqlTimeRandom(){
        this(null);
    }
    public SqlTimeRandom(Class cls) {
        this.innerClass = cls;
    }


    public static <X> SqlTimeTypeInfo<X> getInfoFor(Class<X> type) {
        checkNotNull(type);

        if (type == Date.class) {
            return (SqlTimeTypeInfo<X>) DATE;
        }
        else if (type == Time.class) {
            return (SqlTimeTypeInfo<X>) TIME;
        }
        else if (type == Timestamp.class) {
            return (SqlTimeTypeInfo<X>) TIMESTAMP;
        }
        return null;
    }

    public static void main(String[] args) {
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
    public Object compute(Field declaredField, Rule rule) {
        return rule.apply();
    }

    @Override
    public boolean isCurrentType(Class<?> type) {
        return type == Date.class ||
                type == Time.class ||
                type == Timestamp.class;
    }

    @Override
    public Rule getRule() {
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
            return new DefaultDateRule(fieldToken);
        }else if(innerClass == Time.class){
            return new DefaultDateRule(fieldToken);
        }else if(innerClass == Timestamp.class){
            return new DefaultTimestampRule(fieldToken);
        }
        return null;
    }

    protected static class DefaultDateRule implements Rule<Date> {

        private final FieldToken fieldToken;

        public DefaultDateRule(FieldToken fieldToken) {
            this.fieldToken = fieldToken;
        }

        @Override
        public Date apply() {
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
        public Time apply() {
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
        public Timestamp apply() {
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
