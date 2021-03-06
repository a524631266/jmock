package com.zhangll.jmock.core.random;

import com.zhangll.jmock.core.MockContext;
import com.zhangll.jmock.core.rule.Rule;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import static org.junit.Assert.*;

public class SqlTimeRandomTest {
    private MockContext mockContext;

    @Test
    public void testSqlTimeRandomByDifferentType(){
        SqlTimeSimpleRandomExecutor dateSqlTimeRandom = new SqlTimeSimpleRandomExecutor(Date.class);
        Rule rule = dateSqlTimeRandom.getDefaultRule();
        assertTrue(rule instanceof SqlTimeSimpleRandomExecutor.DefaultDateRule);


        SqlTimeSimpleRandomExecutor timeSqlTimeRandom = new SqlTimeSimpleRandomExecutor(Time.class);
        Rule rule2 = timeSqlTimeRandom.getDefaultRule();
        assertTrue(rule2 instanceof SqlTimeSimpleRandomExecutor.DefaultTimeRule);

        SqlTimeSimpleRandomExecutor timesSqlTimeRandom = new SqlTimeSimpleRandomExecutor(Timestamp.class);
        Rule rule3 = timesSqlTimeRandom.getDefaultRule();
        assertTrue(rule3 instanceof SqlTimeSimpleRandomExecutor.DefaultTimestampRule);
    }

    @Test
    public void testDate(){
        SqlTimeSimpleRandomExecutor dateSqlTimeRandom = new SqlTimeSimpleRandomExecutor(Date.class);
        Rule rule = dateSqlTimeRandom.getDefaultRule();
        if(rule instanceof SqlTimeSimpleRandomExecutor.DefaultDateRule){
            for (int i = 0; i <  100; i++) {
                Date apply = (Date) rule.apply(null, null);
                System.out.println(apply);
            }

        }

    }

    @Test
    public void testTime(){
        SqlTimeSimpleRandomExecutor dateSqlTimeRandom = new SqlTimeSimpleRandomExecutor(Time.class);
        Rule rule = dateSqlTimeRandom.getDefaultRule();
        if(rule instanceof SqlTimeSimpleRandomExecutor.DefaultTimeRule){
            for (int i = 0; i <  100; i++) {
                Time apply = (Time) rule.apply(null, null);
                System.out.println(apply);
            }
        }

    }

    @Test
    public void testTimestamp(){
        SqlTimeSimpleRandomExecutor dateSqlTimeRandom = new SqlTimeSimpleRandomExecutor(Timestamp.class);
        Rule rule = dateSqlTimeRandom.getDefaultRule();
        if(rule instanceof SqlTimeSimpleRandomExecutor.DefaultTimestampRule){
            for (int i = 0; i <  100; i++) {
                Timestamp apply = (Timestamp) rule.apply(null, null);
                System.out.println(apply);
            }

        }

    }
}