package com.zhangll.flink;

import com.zhangll.flink.annotation.BasicTokenInfo;
import lombok.ToString;

@ToString
public class Son {
    private String name;
    private int id;
    @BasicTokenInfo(min = "10", max = "20")
    private int age;
    private SonSon son;
}
