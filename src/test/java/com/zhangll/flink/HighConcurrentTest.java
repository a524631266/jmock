package com.zhangll.flink;

import org.junit.Test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 高并发测试
 */
public class HighConcurrentTest {
//    @Test
    public void testNomalHashMap(){
        Map<String, String> map = new HashMap(100000);
        for (int i = 0; i < 100000; i++) {
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        map.put(UUID.randomUUID().toString(), "");
                    }
                };
                thread.start();
        }
    }

//    @Test
    public void testConcurrentHashMap(){
        Map<String, String> map = new ConcurrentHashMap<>(100000);
        for (int i = 0; i < 100000; i++) {
            Thread thread = new Thread(){
                @Override
                public void run() {
                    map.put(UUID.randomUUID().toString(), "");
                }
            };
            thread.start();
        }
    }

//    @Test
    public void testNomalHashTable(){
        Map<String, String> map = new Hashtable(100000);
        for (int i = 0; i < 100000; i++) {
            Thread thread = new Thread(){
                @Override
                public void run() {
                    map.put(UUID.randomUUID().toString(), "");
                }
            };
            thread.start();
        }
    }
}
