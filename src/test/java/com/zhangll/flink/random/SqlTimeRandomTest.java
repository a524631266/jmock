package com.zhangll.flink.random;

import com.zhangll.flink.rule.Rule;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import static org.junit.Assert.*;

public class SqlTimeRandomTest {
    @Test
    public void testSqlTimeRandomByDifferentType(){
        SqlTimeSimpleRandom dateSqlTimeRandom = new SqlTimeSimpleRandom(Date.class);
        Rule rule = dateSqlTimeRandom.getRule();
        assertTrue(rule instanceof SqlTimeSimpleRandom.DefaultDateRule);


        SqlTimeSimpleRandom timeSqlTimeRandom = new SqlTimeSimpleRandom(Time.class);
        Rule rule2 = timeSqlTimeRandom.getRule();
        assertTrue(rule2 instanceof SqlTimeSimpleRandom.DefaultTimeRule);

        SqlTimeSimpleRandom timesSqlTimeRandom = new SqlTimeSimpleRandom(Timestamp.class);
        Rule rule3 = timesSqlTimeRandom.getRule();
        assertTrue(rule3 instanceof SqlTimeSimpleRandom.DefaultTimestampRule);
    }

    @Test
    public void testDate(){
        SqlTimeSimpleRandom dateSqlTimeRandom = new SqlTimeSimpleRandom(Date.class);
        Rule rule = dateSqlTimeRandom.getRule();
        if(rule instanceof SqlTimeSimpleRandom.DefaultDateRule){
            for (int i = 0; i <  100; i++) {
                Date apply = (Date) rule.apply();
                System.out.println(apply);
            }

        }

    }

    @Test
    public void testTime(){
        SqlTimeSimpleRandom dateSqlTimeRandom = new SqlTimeSimpleRandom(Time.class);
        Rule rule = dateSqlTimeRandom.getRule();
        if(rule instanceof SqlTimeSimpleRandom.DefaultTimeRule){
            for (int i = 0; i <  100; i++) {
                Time apply = (Time) rule.apply();
                System.out.println(apply);
            }
        }

    }

    @Test
    public void testTimestamp(){
        SqlTimeSimpleRandom dateSqlTimeRandom = new SqlTimeSimpleRandom(Timestamp.class);
        Rule rule = dateSqlTimeRandom.getRule();
        if(rule instanceof SqlTimeSimpleRandom.DefaultTimestampRule){
            for (int i = 0; i <  100; i++) {
                Timestamp apply = (Timestamp) rule.apply();
                System.out.println(apply);
            }

        }

    }
}