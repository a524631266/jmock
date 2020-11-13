package com.zhangll.jmock.core;

import com.zhangll.flink.annotation.BasicTokenInfo;
import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import lombok.ToString;

@ToString
public class SonSon {
    private String name;
    private int id;
    @BasicTokenInfo(min = "10", max = "20")
    private int age;
}
