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
        SqlTimeRandom dateSqlTimeRandom = new SqlTimeRandom(Date.class);
        Rule rule = dateSqlTimeRandom.getRule();
        assertTrue(rule instanceof SqlTimeRandom.DefaultDateRule);


        SqlTimeRandom timeSqlTimeRandom = new SqlTimeRandom(Time.class);
        Rule rule2 = timeSqlTimeRandom.getRule();
        assertTrue(rule2 instanceof SqlTimeRandom.DefaultTimeRule);

        SqlTimeRandom timesSqlTimeRandom = new SqlTimeRandom(Timestamp.class);
        Rule rule3 = timesSqlTimeRandom.getRule();
        assertTrue(rule3 instanceof SqlTimeRandom.DefaultTimestampRule);
    }

    @Test
    public void testDate(){
        SqlTimeRandom dateSqlTimeRandom = new SqlTimeRandom(Date.class);
        Rule rule = dateSqlTimeRandom.getRule();
        if(rule instanceof SqlTimeRandom.DefaultDateRule){
            for (int i = 0; i <  100; i++) {
                Date apply = (Date) rule.apply();
                System.out.println(apply);
            }

        }

    }

    @Test
    public void testTime(){
        SqlTimeRandom dateSqlTimeRandom = new SqlTimeRandom(Time.class);
        Rule rule = dateSqlTimeRandom.getRule();
        if(rule instanceof SqlTimeRandom.DefaultTimeRule){
            for (int i = 0; i <  100; i++) {
                Time apply = (Time) rule.apply();
                System.out.println(apply);
            }
        }

    }

    @Test
    public void testTimestamp(){
        SqlTimeRandom dateSqlTimeRandom = new SqlTimeRandom(Timestamp.class);
        Rule rule = dateSqlTimeRandom.getRule();
        if(rule instanceof SqlTimeRandom.DefaultTimestampRule){
            for (int i = 0; i <  100; i++) {
                Timestamp apply = (Timestamp) rule.apply();
                System.out.println(apply);
            }

        }

    }
}