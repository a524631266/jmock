package com.zhangll.flink.expression;

import com.zhangll.flink.uitl.RandomUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class RulePostProcessorTest {

    @Test
    public void testFirst() {
        String dataOne = "@First";
        Object o = new RulePostProcessor().postProcessAfterCompute(dataOne);
        System.out.println(o);
        assertTrue(Arrays.asList(RandomUtil.surName).contains(o));
    }

    @Test
    public void testMiddle() {
        String dataOne = "@Middle";
        Object o = new RulePostProcessor().postProcessAfterCompute(dataOne);
        System.out.println(o);
        assertTrue(Arrays.asList(RandomUtil.word).contains(o));
    }

    @Test
    public void testLast() {
        for (int i = 0; i < 100; i++) {

            String dataOne = "@Last";
            Object o = new RulePostProcessor().postProcessAfterCompute(dataOne);
//            System.out.println(o);
            assertTrue(Arrays.asList(RandomUtil.word).contains(o));
        }
    }

    @Test
    public void testMult() {
//        List<String> dataOne = new ArrayList<String>(){
//            {
//                add("@First");
//                add("@Last");
//            }
//        };
        ArrayList<String> dataOne = new ArrayList<String>(){};
        dataOne.add("@First");
        dataOne.add("@last");
        Object o = new RulePostProcessor().postProcessAfterCompute(dataOne);
        System.out.println(o);
        if(o instanceof  String[]){
//            assertTrue(Arrays.asList(RandomUtil.word).contains(o[0]));
//            assertTrue(Arrays.asList(RandomUtil.word).contains(o[0]));
        }
    }

    @Test
    public void testRegrex() {
        String dataOne = "/asddf/";
        Object o = new RulePostProcessor().postProcessAfterCompute(dataOne);
        System.out.println(o);
    }
}