package com.zhangll.jmock.core.random;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class CharRandomTest {
    /**
     * 如何查找char的边界问题，这个边界
     */
    @Test
    public void testCharBindSlow(){

        int bind = 65000;
        boolean positive = false;
        while (true){
            boolean currentPositive = true;
            // 停止条件
            if(outBindChar(bind)){
                bind--;
                currentPositive = true;

            }else{
                bind++;
                currentPositive = false;
            }
            if(positive!=currentPositive){
                break;
            }
        }
        System.out.println(bind);
    }
    /**
     * 如何查找char的边界问题，这个边界
     */
    @Test
    public void testCharBindFast(){
        // 能够确保时在这个范围之内
        int leftBind = 0;
        int rightBind = 70000;

        while (true){
            int mid = (leftBind + rightBind) / 2;
            // 停止条件
            if(outBindChar(mid)){
                rightBind = mid;
            }else{
                leftBind = mid;
            }
            if((mid == rightBind && mid == leftBind) ||((rightBind- leftBind) == 1)){
                break;
            }else{
                System.out.println(String.format("left: %d, right: %d", leftBind, rightBind));
            }
        }
//        System.out.println(String.format("left: %d, right: %d", leftBind, rightBind));
    }

    private boolean outBindChar(int bind) {
        Map<Character, Integer> map = new HashMap<Character, Integer>(bind);
        for (int i = 0; i < bind ; i++) {
            map.put((char) i, map.getOrDefault((char) i, 0)+1);
        }
        Stream<Integer> result =  map.values().stream().filter(value -> value > 1);
        return result.toArray().length > 1;
    }

    /**
     * 65537
     * 在java中char占用两个字符
     * short 也是占有两个字符，因此可以用integ逻辑复用
     */
    @Test
    public void test(){
        CharBindStrategy charBindStrategy = new CharBindStrategy();
        System.out.println("Char test ====================");
        testBind(charBindStrategy);

        ShortBindStrategy shortBindStrategy = new ShortBindStrategy();
        System.out.println("Short test ====================");
        testBind(shortBindStrategy);
        System.out.println("Float test ====================");
//        FloatBindStrategy floatBindStrategy = new FloatBindStrategy();
//        testBind(floatBindStrategy);
    }


    /**
     * 如何查找char的边界问题，这个边界
     */
    public void testBind(BindStrategy strategy){
        // 能够确保时在这个范围之内
        long leftBind = 0;
        long rightBind = (long) Math.pow(2, 0);
        while (true){
            long mid = (leftBind + rightBind) / 2;
            // 停止条件
            if(strategy.outBind(mid)){
                rightBind = mid;
            }else{
                leftBind = mid;
            }
            if((mid == rightBind && mid == leftBind) ||((rightBind- leftBind) == 1) &&
                    strategy.outBind(leftBind) != strategy.outBind(rightBind)){
                break;
            }else{
                System.out.println(String.format("left: %d, right: %d", leftBind, rightBind));
            }
            // 动态扩充 边界
            if(strategy.outBind(leftBind) == strategy.outBind(rightBind)){
                if(strategy.outBind(leftBind)){
                    leftBind /= 2 ;
                } else {
                    rightBind *= 2;
                }
            }
        }
    }

    private interface BindStrategy {
        boolean outBind(long bind);
    }
    class FloatBindStrategy implements BindStrategy{

        @Override
        public boolean outBind(long bind) {
            Map<Float, Integer> map = new HashMap<Float, Integer>();

            for (long i = 0; i < bind ; i++) {
                map.put((float) i, map.getOrDefault((float) i, 0)+1);
            }
            Stream<Integer> result =  map.values().stream().filter(value -> value > 1);
            return result.toArray().length > 1;
        }
    }

    class CharBindStrategy implements BindStrategy{

        @Override
        public boolean outBind(long bind) {
            Map<Character, Integer> map = new HashMap<Character, Integer>();
            for (long i = 0; i < bind ; i++) {
                map.put((char) i, map.getOrDefault((char) i, 0)+1);
            }
            Stream<Integer> result =  map.values().stream().filter(value -> value > 1);
            return result.toArray().length > 1;
        }
    }

    class ShortBindStrategy implements BindStrategy{

        @Override
        public boolean outBind(long bind) {
            Map<Short, Integer> map = new HashMap<Short, Integer>();
            for (long i = 0; i < bind ; i++) {
                map.put((short) i, map.getOrDefault((short) i, 0)+1);
            }
            Stream<Integer> result =  map.values().stream().filter(value -> value > 1);
            return result.toArray().length > 1;
        }
    }

}